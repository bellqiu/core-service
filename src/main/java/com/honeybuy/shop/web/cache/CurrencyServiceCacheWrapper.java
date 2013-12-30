package com.honeybuy.shop.web.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.ehcache.annotations.Cacheable;
import com.hb.core.entity.Currency;
import com.hb.core.service.CurrencyService;

@Service
@Transactional(readOnly=true)
public class CurrencyServiceCacheWrapper {
	
	@Autowired
	private CurrencyService currencyService;
	
	@Cacheable(cacheName="allCurrency")
	public List<Currency> getAllCurrency(){
		return currencyService.getAllCurrency();
	}
	
	@Cacheable(cacheName="GetCurrencyByCode")
	public Currency getCurrencyByCode(String code){
		return currencyService.getCurrencyByCode(code);
	}
}
