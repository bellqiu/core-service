package com.hb.core.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
	private ProductService productService;
	
	@Autowired
	private Converter<OrderDetailDTO, Order> orderDetailConverter;
	
	public OrderDetailDTO add2Cart(String productName, String optParams, String trackingId, String userEmail, int quantity, String currencyCode){
		
		if(!productService.exist(productName)){
			return null;
		}
		
		Order order = getCartOnShoppingOrder(trackingId, userEmail);
		
		if(null == order){
			order = new Order();
			order.setOrderSN(UUID.randomUUID().toString());
			order.setTrackingId(trackingId);
			order.setCurrency(currencyCode);
			if(!StringUtils.isEmpty(userEmail)){
				order.setUser(userService.getUser(userEmail));
			}
		}
		
		ProductChangeDTO changeDTO = productService.compupterProductChangeByOpts(productName, optParams);
		boolean existingOpts = false;
		for (OrderItem orderItem : order.getItems()) {
			if((changeDTO.getSelectedOpts().keySet().size() == orderItem.getSelectedOpts().size() )
					&& orderItem.getProduct().getName().equals(productName)){
				boolean match = true;
				for (SelectedOpts selectedOpts : orderItem.getSelectedOpts()) {
					if(!changeDTO.getSelectedOpts().keySet().contains(selectedOpts.getValue())){
						match = false;
					}
				}
				
				if(match){
					existingOpts = true;
				}
				
			}
		}
		
		return orderDetailConverter.convert(order);
	
	}
	
	public OrderDetailDTO modifyCart(long productId, String trackingId, String userEmail,  int quantity){
		return null;
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

}
