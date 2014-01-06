package com.honeybuy.shop.web.eds;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadResult;
import ch.ralscha.extdirectspring.bean.SortInfo;
import ch.ralscha.extdirectspring.filter.Filter;
import ch.ralscha.extdirectspring.filter.StringFilter;

import com.hb.core.exception.CoreServiceException;
import com.hb.core.service.OrderService;
import com.hb.core.shared.dto.OrderDetailDTO;
import com.hb.core.shared.dto.OrderSummaryDTO;

@Service
@Transactional
@Secured("ADMIN")
public class OrderDirectService {
	
	@Autowired
	private OrderService orderService;
	
	@ExtDirectMethod(value=ExtDirectMethodType.STORE_READ)
	@Transactional(readOnly=true)
	public ExtDirectStoreReadResult<OrderSummaryDTO> list(ExtDirectStoreReadRequest storeRequest) {
		
		int start = storeRequest.getStart() == null ? 0 : storeRequest.getStart();
		int max = storeRequest.getLimit() == null ? 25 : storeRequest.getLimit();
		List<SortInfo> sorts = storeRequest.getSorters();
		String sort = "id";
		String dir = "DESCENDING";
		if(!sorts.isEmpty()){
			SortInfo sortInfo = sorts.get(0);
			sort = sortInfo.getProperty();
			dir = sortInfo.getDirection().toString();
		}
		
		Map<String,String> filters = new HashMap<String, String>();
		
		List<Filter> storeFilters = storeRequest.getFilters();
		
		for (Filter filter : storeFilters) {
			if(filter instanceof StringFilter){
				StringFilter stringFilter = (StringFilter) filter;
				filters.put(stringFilter.getField(), stringFilter.getValue());
			}
		}
		
		return orderService.queryResult(start, max, sort, "DESCENDING".equals(dir) ? "DESC" : "ASC" , filters);
 		
	}
	
	@ExtDirectMethod(value=ExtDirectMethodType.FORM_LOAD)
	@Transactional(readOnly=true)
	public OrderDetailDTO loadOrderDetail(@RequestParam(value = "id") long id){
		if(id > 0){
			return orderService.getOrderDetailById(id);
		} else {
			throw new CoreServiceException("Order is not existing");
		}
	
	}
	
	@ExtDirectMethod(value=ExtDirectMethodType.SIMPLE)
	public OrderDetailDTO saveOrderInfo(@Valid OrderDetailDTO orderDetailDTO){
		
		return orderService.updateOrderInfo(orderDetailDTO.getId(), orderDetailDTO.getTraceInfo(), orderDetailDTO.getOrderStatus(), orderDetailDTO.getCurrency());
	}

}
