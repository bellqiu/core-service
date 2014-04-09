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
		if(code == null) {
			return null;
		}
		return currencyService.getCurrencyByCode(code);
	}
	
	public Currency getDefaultSettingCurrency(){
		List<Currency> allCurrency = getAllCurrency();
		for(Currency c : allCurrency) {
			if(c.isDefaultCurrency()) {
				return c;
			}
		}
		return getCurrencyByCode("USD");
	}
}
