package com.hb.core.service;

import java.util.Iterator;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadResult;

import com.hb.core.entity.Currency;
import com.hb.core.exception.CoreServiceException;

@Transactional
@Service
public class CurrencyService {
	
	@PersistenceContext
	private EntityManager em;

	public Currency saveOrUpdate(Currency currency){
		if(currency.getId() < 1){
			if(null != getCurrencyByName(currency.getName())){
				throw new CoreServiceException("Setting already exist");
			}
		}
		currency = em.merge(currency);
		return currency;
	}
	
	public void destory(Currency currency){
		currency =  em.merge(currency);
		em.remove(currency);
	}
	
	public long getCurrencyCount(){
		TypedQuery<Long> query = em.createNamedQuery("countAllCurrency", Long.class);
		return query.getSingleResult();
	}
	
	
	public ExtDirectStoreReadResult<Currency> storeQuery(int start, int max, String sort, String dir, Map<String,String> filters){
		StringBuffer ql = new StringBuffer("");
		if(!filters.isEmpty()){
			ql.append(" where ");
			Iterator<String> item = filters.keySet().iterator();
			while(item.hasNext()){
				String param = item.next();
				if("exchangeRateBaseOnDefault".equalsIgnoreCase(param)){
					ql.append(param +" = :" + param + " ");
				}else{
					ql.append(param +" like :"+param +" ");
				}
				if(item.hasNext()){
					ql.append(" and ");
				}
			}
		}
		
		ql.append(" order by " + sort + " " + dir);
		
		StringBuffer queryStringPrefix = new StringBuffer("select c from Currency as c ");
		StringBuffer CountStringPrefix = new StringBuffer("select count(c.id) from Currency as c ");
		
		TypedQuery<Currency> query = em.createQuery( queryStringPrefix.append(ql).toString(), Currency.class);
		TypedQuery<Long> count = em.createQuery( CountStringPrefix.append(ql).toString(), Long.class);
		
		
		query.setFirstResult(start);
		query.setMaxResults(max);
		for (Map.Entry<String, String> paramEntry : filters.entrySet()) {
			if("exchangeRateBaseOnDefault".equals(paramEntry.getKey())){
				query.setParameter(paramEntry.getKey(), paramEntry.getValue());
				count.setParameter(paramEntry.getKey(), paramEntry.getValue());
			}else{
				query.setParameter(paramEntry.getKey(), "%" + paramEntry.getValue() + "%");
				count.setParameter(paramEntry.getKey(), "%" + paramEntry.getValue() + "%");
			}
		}
		return new ExtDirectStoreReadResult<Currency>(count.getSingleResult().intValue(),query.getResultList());
		
	}
	
	public Currency getCurrencyByName(String name){
		Currency currency = null;
		
		try {
			TypedQuery<Currency> query = em.createNamedQuery("QueryCurrencyByName",Currency.class);
			query.setParameter("name", name);
			
			currency = query.getSingleResult();
		} catch(NoResultException e){
			return null;
		} catch (Exception e) {
			throw new CoreServiceException(e);
		}
		
		return currency;
	}
	
	public String getStringCode(String name){
		Currency currency = getCurrencyByName(name);
		if(null == currency){
			return null;
		}else{
			return currency.getCode();
		}
	}
	
	public String getStringCode(String name, String defaultValue){
		Currency currency = getCurrencyByName(name);
		if(null == currency){
			return defaultValue;
		}else{
			return currency.getCode();
		}
	}
}
