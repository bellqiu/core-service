package com.hb.core.convert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.hb.core.entity.Order;
import com.hb.core.shared.dto.OrderDetailDTO;

@org.springframework.stereotype.Component
public class OrderDetailConverter implements
		Converter<OrderDetailDTO, Order> {
	
	public OrderDetailConverter() {
	}

	@PersistenceContext
	private EntityManager em;

	@Override
	public OrderDetailDTO convert(Order order) {
		if(null == order){
			return null;
		}
		OrderDetailDTO detailDTO = new OrderDetailDTO();
		detailDTO.setId(order.getId());
		detailDTO.setOrderSN(order.getOrderSN());
		detailDTO.setBillingAddress(order.getBillingAddress());
		detailDTO.setCouponCutOff(order.getCouponCutOff());
		detailDTO.setCurrency(order.getCurrency());
		detailDTO.setCustomerMsg(order.getCustomerMsg());
		detailDTO.setDeliveryPrice(order.getDeliveryPrice());
		detailDTO.setOrderStatus(order.getOrderStatus());
		//detailDTO.setItems(items)
		return null;
	}

	@Override
	public Order transf(OrderDetailDTO detailDTO) {
		throw new UnsupportedOperationException("Transf method is not support in OrderDetailConvert");
	}


}
