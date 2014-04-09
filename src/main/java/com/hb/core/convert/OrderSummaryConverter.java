package com.hb.core.convert;

import com.hb.core.entity.Order;
import com.hb.core.entity.User;
import com.hb.core.shared.dto.OrderSummaryDTO;

@org.springframework.stereotype.Component
public class OrderSummaryConverter implements Converter<OrderSummaryDTO, Order>{

	@Override
	public OrderSummaryDTO convert(Order order) {
		
		if(null == order){
			return null;
		}
		
		OrderSummaryDTO summaryDTO = new OrderSummaryDTO();
		summaryDTO.setId(order.getId());
		summaryDTO.setAmount(order.getAmount());
		summaryDTO.setOrderSN(order.getOrderSN());
		summaryDTO.setSourceId(order.getSourceId());
		summaryDTO.setStatus(order.getOrderStatus().toString());
		summaryDTO.setTrackingId(order.getTrackingId());
		
		summaryDTO.setOrderCurrencyCode(order.getCurrency());
		summaryDTO.setCreateDate(order.getCreateDate());
		summaryDTO.setUpdateDate(order.getUpdateDate());
		
		User user = order.getUser();
		if(user != null) {
			summaryDTO.setUseremail(user.getEmail());
		}
		
		return summaryDTO;
	}

	@Override
	public Order transf(OrderSummaryDTO dto) {
		throw new UnsupportedOperationException("Transf method is not support in OrderSummaryConverter");
	}

}
