package com.hb.core.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadResult;

import com.hb.core.convert.Converter;
import com.hb.core.entity.Order;
import com.hb.core.shared.dto.OrderDetailDTO;
import com.hb.core.shared.dto.OrderSummaryDTO;


@Transactional
@Service
public class OrderService {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private Converter<OrderSummaryDTO, Order> orderSummaryConverter;
	
	@Autowired
	private Converter<OrderDetailDTO, Order> orderDetailConverter;
	
	public OrderDetailDTO add2Cart(long productId, Map<String,String> opts, String trackingId, String userEmail, int quantity){
		return null;
	}
	
	public OrderDetailDTO modifyCart(long productId, String trackingId, String userEmail,  int quantity){
		return null;
	}
	
	public OrderDetailDTO getCart(String trackingId, String userEmail){
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
