package com.hb.core.service;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadResult;

import com.hb.core.entity.Coupon;
import com.hb.core.exception.CoreServiceException;
import com.hb.core.util.Constants;

@Transactional
@Service
public class CouponService {
	
	@PersistenceContext
	private EntityManager em;
	
	public Coupon saveOrUpdate(Coupon coupon){
		Coupon existingCoupon = getCouponByCode(coupon.getCode());
		if(null != existingCoupon && (existingCoupon.getId() != coupon.getId() || coupon.getId() < 1)) {
			throw new CoreServiceException("Coupon code already exist");
		} else if (null == existingCoupon) {
			coupon.setCreateDate(new Date()); 
		} else {
			coupon.setCreateDate(existingCoupon.getCreateDate());
		}
		coupon.setUpdateDate(new Date());
		if(coupon.getCreateDate() == null) {
			coupon.setCreateDate(new Date()); 
		}
		coupon = em.merge(coupon);
		return coupon;
	}
	
	public void destory(Coupon coupon){
		coupon =  em.merge(coupon);
		em.remove(coupon);
	}
	
	public long getCouponCount(){
		TypedQuery<Long> query = em.createNamedQuery("countAllCoupon", Long.class);
		return query.getSingleResult();
	}

	public ExtDirectStoreReadResult<Coupon> queryResult(int start, int max,
			String sort, String direction, Map<String, String> filters) {
		StringBuffer ql = new StringBuffer("");
		if(!filters.isEmpty()){
			ql.append(" where ");
			Iterator<String> item = filters.keySet().iterator();
			while(item.hasNext()){
				String param = item.next();
				if("value".equalsIgnoreCase(param)) {
					ql.append(param +" > :" + param + "_low and ");
					ql.append(param +" < :" + param + "_up ");
				} else {
					ql.append(param +" like :"+param +" ");
				}
				if(item.hasNext()){
					ql.append(" and ");
				}
			}
		}
		
		ql.append(" order by " + sort + " " + direction);
		
		StringBuffer queryStringPrefix = new StringBuffer("select c from Coupon as c ");
		StringBuffer CountStringPrefix = new StringBuffer("select count(c.id) from Coupon as c ");
		
		TypedQuery<Coupon> query = em.createQuery( queryStringPrefix.append(ql).toString(), Coupon.class);
		TypedQuery<Long> count = em.createQuery( CountStringPrefix.append(ql).toString(), Long.class);
		
		
		query.setFirstResult(start);
		query.setMaxResults(max);
		for (Map.Entry<String, String> paramEntry : filters.entrySet()) {
			String key = paramEntry.getKey();
			if("value".equalsIgnoreCase(key)) {
				float value = Float.valueOf(paramEntry.getValue());
				query.setParameter(key + "_low", value - Constants.FLOAT_COMPARE);
				query.setParameter(key + "_up", value + Constants.FLOAT_COMPARE);
				count.setParameter(key + "_low",  value - Constants.FLOAT_COMPARE);
				count.setParameter(key + "_up",  value + Constants.FLOAT_COMPARE);
			} else {
				query.setParameter(paramEntry.getKey(), "%" + paramEntry.getValue() + "%");
				count.setParameter(paramEntry.getKey(), "%" + paramEntry.getValue() + "%");
			}
		}
		return new ExtDirectStoreReadResult<Coupon>(count.getSingleResult().intValue(),query.getResultList());
	}
	
	public Coupon getCouponByCode(String code){
		Coupon coupon = null;
		
		try {
			TypedQuery<Coupon> query = em.createNamedQuery("QueryCouponByCode", Coupon.class);
			query.setParameter("code", code);
			
			coupon = query.getSingleResult();
		} catch(NoResultException e){
			return null;
		} catch (Exception e) {
			throw new CoreServiceException(e);
		}
		
		return coupon;
	}
	

	public Coupon getCouponById(long id) {
		return em.find(Coupon.class, id);
	}
	
}
