package com.honeybuy.shop.web.eds;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectFormPostResult;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadResult;
import ch.ralscha.extdirectspring.bean.SortInfo;
import ch.ralscha.extdirectspring.filter.Filter;
import ch.ralscha.extdirectspring.filter.StringFilter;

import com.hb.core.entity.Coupon;
import com.hb.core.service.CouponService;

@Service
@Transactional
@Secured("ADMIN")
public class CouponDirectService {
	
	@Autowired
	private CouponService couponService;
	
	@ExtDirectMethod(value=ExtDirectMethodType.STORE_READ)
	@Transactional(readOnly=true)
	public ExtDirectStoreReadResult<Coupon> list(ExtDirectStoreReadRequest storeRequest) {
		
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
		
		return couponService.queryResult(start, max, sort, "DESCENDING".equals(dir) ? "DESC" : "ASC" , filters);
 		
	}
	
	@ExtDirectMethod(value=ExtDirectMethodType.STORE_MODIFY)
	public Coupon update(Coupon coupon) {
		coupon = couponService.saveOrUpdate(coupon);
		return coupon;
	}
	
	@ExtDirectMethod(value=ExtDirectMethodType.STORE_MODIFY)
	public void destory(Coupon coupon) {
		couponService.destory(coupon);
	}
	
	@ExtDirectMethod(value=ExtDirectMethodType.FORM_LOAD)
	public Coupon loadCoupon(@RequestParam(value = "id") long id){
		Coupon coupon = new Coupon();
		
		if(id > 0){
			coupon = couponService.getCouponById(id);
		}
	
		return coupon;
	}
	
	@ExtDirectMethod(value=ExtDirectMethodType.FORM_POST)
	public ExtDirectFormPostResult saveCoupon(@Valid Coupon coupon, BindingResult result){
		if (!result.hasErrors()) {
			if (coupon.getId()< 1 && couponService.getCouponByCode(coupon.getCode()) != null) {
				result.rejectValue("code", null, "Coupon already taken");
			}
		}
		if(!result.hasErrors()){
			coupon = couponService.saveOrUpdate(coupon);
		}
		
		ExtDirectFormPostResult directFormPostResult = new ExtDirectFormPostResult(result);
		directFormPostResult.addResultProperty("resultForm", coupon);
		
		return directFormPostResult;
	}

}
