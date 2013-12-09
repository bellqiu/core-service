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
import com.hb.core.entity.Order;
import com.hb.core.entity.OrderItem;
import com.hb.core.entity.Product;
import com.hb.core.entity.SelectedOpts;
import com.hb.core.exception.CoreServiceException;
import com.hb.core.shared.dto.OrderDetailDTO;
import com.hb.core.shared.dto.OrderSummaryDTO;
import com.hb.core.shared.dto.ProductChangeDTO;


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
			logger.debug("No existing Shopping cart found");
			order = new Order();
			order.setCreateDate(new Date());
			//order.setOrderSN(UUID.randomUUID().toString());
			order.setTrackingId(trackingId);
			order.setCurrency(currencyCode);
			if(!StringUtils.isEmpty(userEmail)){
				order.setUser(userService.getUser(userEmail));
			}
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
		
		order.setUpdateDate(new Date());
		
		order = em.merge(order);
		
		if(StringUtils.isEmpty(order.getOrderSN())){
			String prefix = settingService.getStringValue("ORDERSN_PREFIX", "ORDER");
			String orderDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
			order.setOrderSN(prefix+"-"+orderDate+"-"+order.getId());
		}
		
		em.persist(order);
		em.flush();
		return orderDetailConverter.convert(order);
	
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
		
		em.persist(order);
		em.flush();
		
		return orderDetailConverter.convert(order);
	}
	
	public OrderDetailDTO getShoppingCart(String trackingId, String userEmail){
		Order order = getCartOnShoppingOrder(trackingId, userEmail);
		return orderDetailConverter.convert(order);
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
		}
		return null;
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
	
	public OrderDetailDTO getOrder(String OrderSN){
		return null;
	}
	
	public OrderDetailDTO applyCoupon(String OrderSN, String couponCode){
		return null;
	}
	
	public OrderDetailDTO checkout(String OrderSN, String userEmail, String billingAddress, String shippingAddress, String shippingCountryCode, String extMessage){
		return null;
	}
	
	public ExtDirectStoreReadResult<OrderSummaryDTO> queryResult(int start, int max,
			String sort, String direction, Map<String, String> filters) {
		StringBuffer ql = new StringBuffer("");
		if(!filters.isEmpty()){
			ql.append(" where ");
			Iterator<String> item = filters.keySet().iterator();
			while(item.hasNext()){
				String param = item.next();
				ql.append(param +" like :"+param +" ");
				if(item.hasNext()){
					ql.append(" and ");
				}
			}
		}
		
		ql.append(" order by " + sort + " " + direction);
		
		StringBuffer queryStringPrefix = new StringBuffer("select o from Order as o ");
		StringBuffer CountStringPrefix = new StringBuffer("select count(o.id) from Order as o ");
		
		TypedQuery<Order> query = em.createQuery( queryStringPrefix.append(ql).toString(), Order.class);
		TypedQuery<Long> count = em.createQuery( CountStringPrefix.append(ql).toString(), Long.class);
		
		
		query.setFirstResult(start);
		query.setMaxResults(max);
		for (Map.Entry<String, String> paramEntry : filters.entrySet()) {
			query.setParameter(paramEntry.getKey(), "%" + paramEntry.getValue() + "%");
			count.setParameter(paramEntry.getKey(), "%" + paramEntry.getValue() + "%");
		}
		int total = count.getSingleResult().intValue();
		List<Order> orders = query.getResultList();
		ArrayList<OrderSummaryDTO> dtoList = new ArrayList<OrderSummaryDTO>();
		for(Order order : orders) {
			dtoList.add(orderSummaryConverter.convert(order));
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

	public OrderDetailDTO updateOrderDetail(OrderDetailDTO orderDetailDTO) {
		long id = orderDetailDTO.getId();
		Order order = null;
		if(id > 0 && (order = em.find(Order.class, id)) != null) {
			order.setTraceInfo(orderDetailDTO.getTraceInfo());
			order.setOrderStatus(orderDetailDTO.getOrderStatus());
			return orderDetailConverter.convert(em.merge(order));
		} else {
			throw new CoreServiceException("Order is not existing");
		}
	}

}
