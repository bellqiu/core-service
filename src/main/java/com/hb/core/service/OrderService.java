package com.hb.core.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadResult;

import com.hb.core.convert.Converter;
import com.hb.core.entity.Address;
import com.hb.core.entity.Country;
import com.hb.core.entity.Coupon;
import com.hb.core.entity.Order;
import com.hb.core.entity.OrderItem;
import com.hb.core.entity.Product;
import com.hb.core.entity.SelectedOpts;
import com.hb.core.entity.User;
import com.hb.core.exception.CoreServiceException;
import com.hb.core.shared.dto.OrderDetailDTO;
import com.hb.core.shared.dto.OrderSummaryDTO;
import com.hb.core.shared.dto.ProductChangeDTO;
import com.hb.core.shared.dto.UserDTO;
import com.hb.core.util.Constants;
import com.honeybuy.shop.util.EncodingUtils;


@Transactional
@Service
public class OrderService {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private Converter<OrderSummaryDTO, Order> orderSummaryConverter;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SettingService settingService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CountryService countryService;
	
	@Autowired
	private CouponService couponService;
	
	@Autowired
	private Converter<OrderDetailDTO, Order> orderDetailConverter;
	
	public OrderDetailDTO add2Cart(String productName, String optParams, String trackingId, String userEmail, int quantity, String currencyCode){
		
		logger.debug("Add to card, productName={}, trackingId={},  userEmail={}, quantity={}, currencyCode={}", new Object[]{
				productName , optParams, trackingId, userEmail, quantity, currencyCode
				
		});
		
		if(!productService.exist(productName)){
			return null;
		}
		
		Order order = getCartOnShoppingOrder(trackingId, userEmail);
		
		if(null == order){
			order = newCart(trackingId, userEmail, currencyCode);
		}
		
		ProductChangeDTO changeDTO = productService.compupterProductChangeByOpts(productName, optParams);
		boolean existing = false;
		for (OrderItem orderItem : order.getItems()) {
			if((changeDTO.getSelectedOpts().keySet().size() == orderItem.getSelectedOpts().size() )
					&& orderItem.getProduct().getName().equals(productName)){
				boolean match = true ; 
				for (SelectedOpts selectedOpts : orderItem.getSelectedOpts()) {
					if(!changeDTO.getSelectedOpts().keySet().contains(selectedOpts.getValue())){
						match = false;
					}
				}
				
				if(match){
					existing = true;
					logger.debug("Existing orderItem found");
					orderItem.setQuantity(orderItem.getQuantity() + quantity);
					orderItem.setUpdateDate(new Date());
					break;
				}
				
			}
		}
		
		Product product = productService.getProductByName(productName);
		
		if(!existing){
			logger.debug("New orderItem ");
			OrderItem orderItem = new OrderItem();
			orderItem.setCreateDate(new Date());
			orderItem.setProduct(product);
			orderItem.setQuantity(quantity);
			
			for (String optString : changeDTO.getSelectedOpts().keySet()) {
				SelectedOpts selectedOpts = new SelectedOpts();
				selectedOpts.setValue(optString);
				selectedOpts.setPriceChange(changeDTO.getSelectedOpts().get(optString));
				selectedOpts.setCreateDate(new Date());
				selectedOpts.setUpdateDate(new Date());
				
				orderItem.getSelectedOpts().add(selectedOpts);
			}
			
			orderItem.setFinalPrice((float)(product.getActualPrice() + changeDTO.getPriceChange()));
			order.getItems().add(orderItem);
		}
		
		if(order.getCouponCode() != null) {
			checkCoupon(order);
		}
		
		order.setUpdateDate(new Date());
		
		order = em.merge(order);
		
		em.persist(order);
		em.flush();
		return orderDetailConverter.convert(order);
	
	}
	
	private Order newCart(String trackingId, String userEmail,
			String currencyCode) {
		Order order;
		logger.debug("No existing Shopping cart found");
		order = new Order();
		order.setCreateDate(new Date());
		//order.setOrderSN(UUID.randomUUID().toString());
		order.setTrackingId(trackingId);
		order.setCurrency(currencyCode);
		if(!StringUtils.isEmpty(userEmail)){
			order.setUser(userService.getUser(userEmail));
		}
		return order;
	}
	
	public OrderDetailDTO modifyCart(String trackingId, String userEmail, String orderItemId, String changes){
		
		Order order = getCartOnShoppingOrder(trackingId, userEmail);
		List<OrderItem> removedItems = new ArrayList<OrderItem>();
		if(null != order){
			for (OrderItem orderItem : order.getItems()) {
				if((""+orderItem.getId()).equals(orderItemId)){
					if("ALL".equalsIgnoreCase(changes)){
						removedItems.add(orderItem);
					}else{
						int quantity = 0;
						try {
							quantity = Integer.parseInt(changes);
						} catch (Exception e) {
						}
						quantity = orderItem.getQuantity() + quantity;
						if(quantity < 1){
							removedItems.add(orderItem);
						}else{
							orderItem.setQuantity(quantity);
						}
					}
				}
			}
		}
		
		for (OrderItem orderItem : removedItems) {
			order.getItems().remove(orderItem);
			em.remove(orderItem);
		}
		
		if(order.getCouponCode() != null) {
			checkCoupon(order);
		}
		
		em.persist(order);
		em.flush();
		
		return orderDetailConverter.convert(order);
	}
	
	public OrderDetailDTO getShoppingCart(String trackingId, String userEmail){
		Order order = getCartOnShoppingOrder(trackingId, userEmail);
		return orderDetailConverter.convert(order);
	}
	
	
	public int getCartItemCount(String trackingId, String userEmail){
		Order order = getCartOnShoppingOrder(trackingId, userEmail);
		int count = 0;
		if(null != order){
			for (OrderItem item : order.getItems()) {
				count = count + item.getQuantity();
			}
		}
		
		return count;
	}
	
	private Order getCartOnShoppingOrder(String trackingId, String userEmail){
		List<Order> orders = null;
		
		if (!StringUtils.isEmpty(userEmail)) {

			TypedQuery<Order> query = em.createNamedQuery(
					"QueryOnShoppingOrderByUserEmail", Order.class);

			query.setParameter("email", userEmail);

			orders = query.getResultList();

		} else if (!StringUtils.isEmpty(trackingId)) {
			TypedQuery<Order> query = em.createNamedQuery(
					"QueryOnShoppingOrderByTrackingId", Order.class);

			query.setParameter("trackingId", trackingId);

			orders = query.getResultList();
		}
		
		if(null != orders && !orders.isEmpty()){
			return orders.get(0);
		}else{
			return newCart(trackingId, userEmail, null);
		}
		
	}
	
	@Transactional(readOnly=true)
	public OrderDetailDTO getCart(String trackingId, String userEmail){
		
		logger.debug("Retrieve cart, trackingId={}, userEmail={}", new Object[]{trackingId,userEmail});
		
		Order order = getCartOnShoppingOrder(trackingId, userEmail);
		
		if(null != order ){
			return orderDetailConverter.convert(order);
		}
		
		return null;
	}
	
	public OrderDetailDTO getOrderBySN(String orderSN){
		TypedQuery<Order> query = em.createNamedQuery(
				"QueryOrderBySN", Order.class);

		query.setParameter("orderSN", orderSN);
		
		Order order = null;
		try {
			order = query.getSingleResult();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		if(null == order){
			return null;
		}
		
		return orderDetailConverter.convert(order);
	}
	
	
	public ExtDirectStoreReadResult<OrderSummaryDTO> queryResult(int start, int max,
			String sort, String direction, Map<String, String> filters) {
		StringBuffer ql = new StringBuffer("");
		boolean filterByStatus = false;
		if(!filters.isEmpty()){
			Iterator<String> item = filters.keySet().iterator();
			while(item.hasNext()){
				String param = item.next();
				if ("status".equalsIgnoreCase(param)) {
					filterByStatus = true;
					//ql.append(" orderStatus = :" + param + " ");
				} else {
					ql.append(param +" like :"+param +" ");
				}
				if(item.hasNext()){
					ql.append(" and ");
				}
			}
		}
		
		ql.append(" order by " + sort + " " + direction);
		
		StringBuffer queryStringPrefix = new StringBuffer("select o from Order as o where o.orderStatus ");
		StringBuffer countStringPrefix = new StringBuffer("select count(o.id) from Order as o where o.orderStatus ");
		if(filterByStatus) {
			queryStringPrefix.append(" = :status ");
			countStringPrefix.append(" = :status ");
		} else {
			queryStringPrefix.append(" != 'ONSHOPPING' ");
			countStringPrefix.append(" != 'ONSHOPPING' ");
		}
		
		TypedQuery<Order> query = em.createQuery( queryStringPrefix.append(ql).toString(), Order.class);
		TypedQuery<Long> count = em.createQuery( countStringPrefix.append(ql).toString(), Long.class);
		
		query.setFirstResult(start);
		query.setMaxResults(max);
		for (Map.Entry<String, String> paramEntry : filters.entrySet()) {
			String key = paramEntry.getKey();
			String value = paramEntry.getValue();
			if ("status".equalsIgnoreCase(key)) {
				Order.Status status = Order.Status.valueOf(value);
				query.setParameter(key, status);
				count.setParameter(key, status);
			} else {
				query.setParameter(key, "%" + value + "%");
				count.setParameter(key, "%" + value + "%");
			}
		}
		int total = count.getSingleResult().intValue();
		List<Order> orders = query.getResultList();
		ArrayList<OrderSummaryDTO> dtoList = new ArrayList<OrderSummaryDTO>();
		for(Order order : orders) {
			OrderSummaryDTO orderSummaryDTO = orderSummaryConverter.convert(order);
			try {
				User user = order.getUser();
				orderSummaryDTO.setToken(EncodingUtils.hmac256(user.getEmail(), user.getPassword()));
			} catch (Exception e) {
				logger.error("Token set error for order id: ", order.getId());
				orderSummaryDTO.setToken("");
			}
			dtoList.add(orderSummaryDTO);
		}
		return new ExtDirectStoreReadResult<OrderSummaryDTO>(total, dtoList);
	}

	public OrderDetailDTO getOrderDetailById(long id) {
		OrderDetailDTO detail = null;
		if(id > 0) {
			Order order = em.find(Order.class, id);
			detail = orderDetailConverter.convert(order);
		}
		return detail;
	}

	public OrderDetailDTO assign(String trackingId, String username) {
		Order userOrder  =  getCartOnShoppingOrder(trackingId, username);
		
		
		if(null != trackingId && null != username){
			Order trackingOrder = getCartOnShoppingOrder(trackingId, null);
			if(null == userOrder && null!=trackingOrder){
				userOrder = newCart(trackingId, username, trackingOrder.getCouponCode());
			}
			List<OrderItem> newOrderItem = new ArrayList<OrderItem>();
			if(null != trackingOrder && null != userOrder){
				
				for (OrderItem orderItemTracking : trackingOrder.getItems()) {
					boolean existingItem = false;
					for (OrderItem orderItemUser : userOrder.getItems()) {
						if(orderItemTracking.getProduct().getId() == orderItemUser.getProduct().getId() && orderItemTracking.getSelectedOpts().size() == orderItemUser.getSelectedOpts().size()){
							boolean optsMatches = true;
							for (SelectedOpts optsTracking : orderItemTracking.getSelectedOpts()) {
								boolean optsItemMatches = false;
								for (SelectedOpts optsUser : orderItemTracking.getSelectedOpts()) {
									if(optsTracking.getValue().equals(optsUser.getValue())){
										optsItemMatches = true;
									}
								}
								if(!optsItemMatches){
									optsMatches = false;
								}
							}
							if(optsMatches){
								existingItem = true;
								continue;
							}
							//orderItemUser.setQuantity(orderItemTracking.getQuantity()+ orderItemUser.getQuantity());
						}
					}
					
					if(!existingItem){
						OrderItem item= new OrderItem();
						item.setCreateDate(new Date());
						item.setUpdateDate(new Date());
						item.setFinalPrice(orderItemTracking.getFinalPrice());
						item.setProduct(orderItemTracking.getProduct());
						item.setQuantity(orderItemTracking.getQuantity());
						List<SelectedOpts> selectedOpts = new ArrayList<SelectedOpts>();
						
						for (SelectedOpts opts : orderItemTracking.getSelectedOpts()) {
							SelectedOpts newOpts = new SelectedOpts();
							newOpts.setCreateDate(new Date());
							newOpts.setPriceChange(opts.getPriceChange());
							newOpts.setValue(opts.getValue());
							newOpts.setUpdateDate(new Date());
						}
						
						item.setSelectedOpts(selectedOpts);
						newOrderItem.add(item);
					}
				}
				
				
				userOrder.getItems().addAll(newOrderItem);
				
				em.merge(userOrder);
				em.persist(userOrder);
				em.flush();
				
			}
		
		}
		
		return orderDetailConverter.convert(userOrder);
	
	}

	public OrderDetailDTO updateOrderInfo(long orderId,String traceInfo, Order.Status status, String currencyCode) {
		Order order = null;
		if(orderId > 0 && (order = em.find(Order.class, orderId)) != null) {
			order.setTraceInfo(traceInfo);
			order.setOrderStatus(status);
			order.setUpdateDate(new Date());
			order.setCurrency(currencyCode);
			
			return orderDetailConverter.convert(em.merge(order));
		} else {
			throw new CoreServiceException("Order is not existing");
		}
	}
	
	private Order getOrderByOrderIdAndUserId(String userEmail, long orderId){
		Order order = em.find(Order.class, orderId);
		if(null == order){
			return null;
		}
		
		if(null == order.getUser() || !userEmail.equals(order.getUser().getEmail())){
			return null;
		}
		
		return order;
	}
	
	public OrderDetailDTO updateOrderShippingAddress(String userEmail ,Address address, long orderId){
		
		Order order = getOrderByOrderIdAndUserId(userEmail, orderId);
		
		if(null == order){
			return null;
		}
		
		order.setShippingAddress(address.toString());
		order.setShippingAddRef(address.getId());
		order.setShippingCode(address.getCountryCode());
		
		updateShippingPrice(address.getCountryCode(), order);
		
		if(StringUtils.isEmpty(order.getOrderSN())){
			String prefix = settingService.getStringValue("ORDERSN_PREFIX", "ORDER");
			String orderDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
			order.setOrderSN(prefix+"-"+orderDate+"-"+order.getId());
		}
		
		em.merge(order);
		em.persist(order);
		em.flush();
		
		return orderDetailConverter.convert(order);
	}
	
	public float getDeliverPrice(String shippingCode, float price){
		
		float normalFreeDeliver = Float.valueOf(settingService.getStringValue(Constants.FREE_SHIPPING_PRICE_NORMAL_CONF_KEY, Constants.FREE_SHIPPING_PRICE_NORMAL_CONF_DEFAULT));
		float normalDeliver = Float.valueOf(settingService.getStringValue(Constants.SHIPPING_PRICE_NORMAL_CONF_KEY, Constants.SHIPPING_PRICE_NORMAL_CONF_DEFAULT));
		
		Country country = countryService.getCountry(shippingCode);
		if(null!=country){
			normalFreeDeliver = country.getFreeDeliveryPrice();
			normalDeliver = country.getNormalDeliveryPrice();
		}
		
		if(price < normalFreeDeliver){
			return normalDeliver;
		}
		return 0;
	
	}
	
	public float getExpeditedDeliverPrice(String shippingCode, float price){
		float advanceFreeDeliver = Float.valueOf(settingService.getStringValue(Constants.FREE_SHIPPING_PRICE_EXPEDITED_CONF_KEY, Constants.FREE_SHIPPING_PRICE_EXPEDITED_CONF_DEFAULT));
		float advanceDeliver = Float.valueOf(settingService.getStringValue(Constants.SHIPPING_PRICE_EXPEDITED_CONF_KEY, Constants.SHIPPING_PRICE_EXPEDITED_CONF_DEFAULT));
		
		Country country = countryService.getCountry(shippingCode);
		if(null!=country){
			advanceFreeDeliver = country.getFreeAdvanceDeliveryPrice();
			advanceDeliver = country.getAdvanceDeliveryPrice();
		}
		
		if(price < advanceFreeDeliver){
			return advanceDeliver;
			
		}
		return 0;
	}

	private void updateShippingPrice(String countryCode, Order order) {
		Country country = countryService.getCountry(countryCode);
		
		
		float normalFreeDeliver = Float.valueOf(settingService.getStringValue(Constants.FREE_SHIPPING_PRICE_NORMAL_CONF_KEY, Constants.FREE_SHIPPING_PRICE_NORMAL_CONF_DEFAULT));
		float advanceFreeDeliver = Float.valueOf(settingService.getStringValue(Constants.FREE_SHIPPING_PRICE_EXPEDITED_CONF_KEY, Constants.FREE_SHIPPING_PRICE_EXPEDITED_CONF_DEFAULT));
		float normalDeliver = Float.valueOf(settingService.getStringValue(Constants.SHIPPING_PRICE_NORMAL_CONF_KEY, Constants.SHIPPING_PRICE_NORMAL_CONF_DEFAULT));
		float advanceDeliver = Float.valueOf(settingService.getStringValue(Constants.SHIPPING_PRICE_EXPEDITED_CONF_KEY, Constants.SHIPPING_PRICE_EXPEDITED_CONF_DEFAULT));
		
		if(null!=country){
			normalFreeDeliver = country.getFreeDeliveryPrice();
			advanceFreeDeliver = country.getFreeAdvanceDeliveryPrice();
			normalDeliver = country.getNormalDeliveryPrice();
			advanceDeliver = country.getAdvanceDeliveryPrice();
		}
		
		
		if("EXPEDITED".equals(order.getShippingMethod())){
			if(order.getTotalProductPrice() < advanceFreeDeliver){
				order.setDeliveryPrice(advanceDeliver);
			}else{
				order.setDeliveryPrice(0);
			}
			order.setShippingMethod("EXPEDITED");
		}else{
			order.setShippingMethod("NORMAL");
			if(order.getTotalProductPrice() < normalFreeDeliver){
				order.setDeliveryPrice(normalDeliver);
			}else{
				order.setDeliveryPrice(0);
			}
		}
	}
	
	public OrderDetailDTO updateShippingMethod(String userEmail ,long orderId, String ShippingMethod){

		Order order = getOrderByOrderIdAndUserId(userEmail, orderId);
		
		if(null == order || (order.getShippingCode() == null)){
			return null;
		}
		order.setShippingMethod(ShippingMethod);
		updateShippingPrice(order.getShippingCode(), order);
		
		em.merge(order);
		em.persist(order);
		em.flush();
		
		return orderDetailConverter.convert(order);
		
	}
	
	public OrderDetailDTO applMessage(String userEmail ,long orderId, String message){
		
		Order order = getOrderByOrderIdAndUserId(userEmail, orderId);
		
		if(null == order){
			return null;
		}
		
		order.setCustomerMsg(message);
		
		em.merge(order);
		em.persist(order);
		em.flush();
		
		return orderDetailConverter.convert(order);
	}
	
	
	public OrderDetailDTO applyCoupon(long orderId, String couponCode){
		
		Order order = em.find(Order.class, orderId);
		
		
		if(null == order){
			return null;
		}
		
		Coupon coupon = couponService.getCouponByCode(couponCode);
		
		if(null != coupon && coupon.getEndDate().after(new Date()) && coupon.getMinCost() < order.getTotalProductPrice() && coupon.getUsedCount() < coupon.getMaxUsedCount()){
			order.setCouponCode(couponCode);
			
			float couponCutOff = 0;
			
			if(coupon.getValue() < 1){
				couponCutOff = order.getTotalProductPrice() * coupon.getValue();
			}else{
				couponCutOff = coupon.getValue();
			}
			
			order.setCouponCutOff(couponCutOff);
		}
		
		return orderDetailConverter.convert(order);
	}
	
	// check if order has coupon, modify the coupon if it has
	private void checkCoupon(Order order){
		String couponCode = null;
		if(order == null || (couponCode = order.getCouponCode()) == null) {
			return;
		}
		
		Coupon coupon = couponService.getCouponByCode(couponCode);
		
		if(null != coupon && coupon.getEndDate().after(new Date()) && coupon.getMinCost() < order.getTotalProductPrice() && coupon.getUsedCount() < coupon.getMaxUsedCount()){

			float couponCutOff = 0;
			
			if(coupon.getValue() < 1){
				couponCutOff = order.getTotalProductPrice() * coupon.getValue();
			} else {
				couponCutOff = coupon.getValue();
			}
			order.setCouponCutOff(couponCutOff);
		} else {
			order.setCouponCode(null);
			order.setCouponCutOff(0.0f);
		}
	}
	
	public OrderDetailDTO updateOrderBillingAddress(String userEmail ,Address address, long orderId){
		
		Order order = getOrderByOrderIdAndUserId(userEmail, orderId);
		
		if(null == order){
			return null;
		}
		
		order.setBillingAddress(address.toString());
		order.setBillingAddRef(address.getId());
		
		em.merge(order);
		em.persist(order);
		em.flush();
		
		return orderDetailConverter.convert(order);
	}
	
	/**
	 * @param detailDTO
	 * @param address
	 * @param user
	 */
	public void directCheckout(OrderDetailDTO detailDTO, Address address,
			UserDTO user, float shippingAmt , String shippingMth) {
		
		User usr = em.find(User.class, user.getId());
		
		Order order = em.find(Order.class, detailDTO.getId());
		order.setOrderStatus(Order.Status.PAID);
		order.setUser(usr);
		order.setShippingMethod(shippingMth);
		order.setDeliveryPrice(shippingAmt);
		
		if(userService.getUserAddresses(usr.getEmail()).size() < 1){
			userService.saveAddress(usr.getEmail(), address);
		}
		
		order.setBillingAddress("Paypal provider");
		order.setShippingAddress(address.toString());
		
		String prefix = settingService.getStringValue("ORDERSN_PREFIX", "ORDER");
		String orderDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		order.setOrderSN(prefix+"-"+orderDate+"-"+order.getId());
		
		em.merge(order);
		em.persist(order);
		em.flush();
	}


	public CountryService getCountryService() {
		return countryService;
	}

	public void setCountryService(CountryService countryService) {
		this.countryService = countryService;
	}

	public CouponService getCouponService() {
		return couponService;
	}

	public void setCouponService(CouponService couponService) {
		this.couponService = couponService;
	}

	public List<OrderSummaryDTO> getUserOrderByUsername(String username, int start, int max) {
		if(!StringUtils.isEmpty(username)) {
			TypedQuery<Order> query = em.createNamedQuery(
					"QueryOnUserOrderByUsername", Order.class);
			query.setParameter("email", username);
			query.setFirstResult(start);
			query.setMaxResults(max);

			List<Order> orders = query.getResultList();
			List<OrderSummaryDTO> orderDetailDTOs = new ArrayList<OrderSummaryDTO>();
			for(Order order : orders) {
				orderDetailDTOs.add(orderSummaryConverter.convert(order));
			}
			return orderDetailDTOs;
		}
		return null;
	}

	public int getUserOrderCountByUsername(String username) {
		if(!StringUtils.isEmpty(username)) {
			TypedQuery<Long> query = em.createNamedQuery(
					"QueryOnUserOrderCountByUsername", Long.class);
			query.setParameter("email", username);

			return query.getSingleResult().intValue();
		}
		return 0;
	}

}
