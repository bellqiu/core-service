package com.hb.core.convert;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.hb.core.entity.Order;
import com.hb.core.entity.OrderItem;
import com.hb.core.entity.Product;
import com.hb.core.entity.SelectedOpts;
import com.hb.core.shared.dto.OrderDetailDTO;
import com.hb.core.shared.dto.OrderItemDTO;
import com.hb.core.shared.dto.ProductSummaryDTO;

@org.springframework.stereotype.Component
public class OrderDetailConverter implements
		Converter<OrderDetailDTO, Order> {
	
	public OrderDetailConverter() {
	}

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private Converter<ProductSummaryDTO, Product> productSummaryConverter;

	@Override
	public OrderDetailDTO convert(Order order) {
		if(null == order){
			return null;
		}
		OrderDetailDTO detailDTO = new OrderDetailDTO();
		detailDTO.setId(order.getId());
		detailDTO.setOrderSN(order.getOrderSN());
		detailDTO.setBillingAddress(order.getBillingAddress());
		detailDTO.setShippingAddress(order.getShippingAddress());
		detailDTO.setShippingMethod(order.getShippingMethod());
		detailDTO.setCouponCutOff(order.getCouponCutOff());
		detailDTO.setCurrency(order.getCurrency());
		detailDTO.setCustomerMsg(order.getCustomerMsg());
		detailDTO.setDeliveryPrice(order.getDeliveryPrice());
		detailDTO.setOrderStatus(order.getOrderStatus());
		detailDTO.setSourceId(order.getSourceId());
		detailDTO.setTraceInfo(order.getTraceInfo());
		detailDTO.setTrackingId(order.getTrackingId());
		
		List<OrderItemDTO> items = new ArrayList<OrderItemDTO>();
		List<OrderItem> orderItems = order.getItems();
		for(OrderItem orderItem : orderItems) {
			OrderItemDTO orderItemDTO = new OrderItemDTO();
			orderItemDTO.setFinalPrice(orderItem.getFinalPrice());
			orderItemDTO.setQuantity(orderItem.getQuantity());
			orderItemDTO.setProductSummary(productSummaryConverter.convert(orderItem.getProduct()));
			List<SelectedOpts> selectedOpts = orderItem.getSelectedOpts();
			List<String> options = new ArrayList<String>();
			for(SelectedOpts selectOpt : selectedOpts) {
				options.add(selectOpt.getValue());
			}
			orderItemDTO.setSelectedOpts(options);
			items.add(orderItemDTO);
		}
		detailDTO.setItems(items);
		
		return detailDTO;
	}

	@Override
	public Order transf(OrderDetailDTO detailDTO) {
		throw new UnsupportedOperationException("Transf method is not support in OrderDetailConvert");
	}


}
