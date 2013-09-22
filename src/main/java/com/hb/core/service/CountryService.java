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

import com.hb.core.entity.Country;
import com.hb.core.exception.CoreServiceException;

@Transactional
@Service
public class CountryService {
	
	@PersistenceContext
	private EntityManager em;

	public Country saveOrUpdate(Country country){
		Country existingCountry = getCountry(country.getCode());
		if(existingCountry != null && (country.getId() < 1 || existingCountry.getId() == country.getId())) {
			throw new CoreServiceException("Country name already exist");
		}
		country = em.merge(country);
		return country;
	}
	
	public void destory(Country country){
		country =  em.merge(country);
		em.remove(country);
	}
	
	public long getCountryCount(){
		TypedQuery<Long> query = em.createNamedQuery("countAllCountry", Long.class);
		return query.getSingleResult();
	}
	
	
	public ExtDirectStoreReadResult<Country> storeQuery(int start, int max, String sort, String dir, Map<String,String> filters){
		StringBuffer ql = new StringBuffer("");
		if(!filters.isEmpty()){
			ql.append(" where ");
			Iterator<String> item = filters.keySet().iterator();
			while(item.hasNext()){
				String param = item.next();
				if ("normalDeliveryPrice".equalsIgnoreCase(param)
						|| "advanceDeliveryPrice".equalsIgnoreCase(param)
						|| "freeDeliveryPrice".equalsIgnoreCase(param)
						|| "freeAdvanceDeliveryPrice".equalsIgnoreCase(param)) {
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
		
		StringBuffer queryStringPrefix = new StringBuffer("select c from Country as c ");
		StringBuffer CountStringPrefix = new StringBuffer("select count(c.id) from Country as c ");
		
		TypedQuery<Country> query = em.createQuery( queryStringPrefix.append(ql).toString(), Country.class);
		TypedQuery<Long> count = em.createQuery( CountStringPrefix.append(ql).toString(), Long.class);
		
		
		query.setFirstResult(start);
		query.setMaxResults(max);
		for (Map.Entry<String, String> paramEntry : filters.entrySet()) {
			String key = paramEntry.getKey();
			if ("normalDeliveryPrice".equalsIgnoreCase(key)
					|| "advanceDeliveryPrice".equalsIgnoreCase(key)
					|| "freeDeliveryPrice".equalsIgnoreCase(key)
					|| "freeAdvanceDeliveryPrice".equalsIgnoreCase(key)) {
				query.setParameter(key, Float.valueOf(paramEntry.getValue()));
				count.setParameter(key, Float.valueOf((paramEntry.getValue())));
			}else{
				query.setParameter(key, "%" + paramEntry.getValue() + "%");
				count.setParameter(key, "%" + paramEntry.getValue() + "%");
			}
		}
		return new ExtDirectStoreReadResult<Country>(count.getSingleResult().intValue(),query.getResultList());
		
	}
	
	public Country getCountry(String key){
		Country country = null;
		
		try {
			TypedQuery<Country> query = em.createNamedQuery("QueryCountryByKey",Country.class);
			query.setParameter("key", key);
			
			country = query.getSingleResult();
		} catch(NoResultException e){
			return null;
		} catch (Exception e) {
			throw new CoreServiceException(e);
		}
		
		return country;
	}
	
	public String getStringAbbrCode(String key){
		Country country = getCountry(key);
		if(null == country){
			return null;
		}else{
			return country.getAbbrCode();
		}
	}
	
}
