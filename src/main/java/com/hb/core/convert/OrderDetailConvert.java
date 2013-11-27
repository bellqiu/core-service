package com.hb.core.convert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Order;

import com.hb.core.shared.dto.OrderDetailDTO;

@org.springframework.stereotype.Component
public class OrderDetailConvert implements
		Converter<OrderDetailDTO, Order> {
	
	public OrderDetailConvert() {
	}

	@PersistenceContext
	private EntityManager em;

	@Override
	public OrderDetailDTO convert(Order e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order transf(OrderDetailDTO d) {
		// TODO Auto-generated method stub
		return null;
	}


}
