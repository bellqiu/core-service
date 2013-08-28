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

import com.hb.core.entity.Setting;
import com.hb.core.exception.CoreServiceException;

@Transactional
@Service
public class SettingService {
	
	@PersistenceContext
	private EntityManager em;

	public Setting saveOrUpdate(Setting setting){
		if(setting.getId() < 1){
			if(null != getSetting(setting.getName(), setting.getType())){
				throw new CoreServiceException("Setting already exist");
			}
		}
		setting = em.merge(setting);
		return setting;
	}
	
	public void destory(Setting setting){
		setting =  em.merge(setting);
		em.remove(setting);
	}
	
	public long getSettingCount(){
		TypedQuery<Long> query = em.createNamedQuery("countAllSetting", Long.class);
		return query.getSingleResult();
	}
	
	
	public ExtDirectStoreReadResult<Setting> storeQuery(int start, int max, String sort, String dir, Map<String,String> filters){
		StringBuffer ql = new StringBuffer("");
		if(!filters.isEmpty()){
			ql.append(" where ");
			Iterator<String> item = filters.keySet().iterator();
			while(item.hasNext()){
				String param = item.next();
				if("type".equalsIgnoreCase(param)){
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
		
		StringBuffer queryStringPrefix = new StringBuffer("select s from Setting as s ");
		StringBuffer CountStringPrefix = new StringBuffer("select count(s.id) from Setting as s ");
		
		TypedQuery<Setting> query = em.createQuery( queryStringPrefix.append(ql).toString(), Setting.class);
		TypedQuery<Long> count = em.createQuery( CountStringPrefix.append(ql).toString(), Long.class);
		
		
		query.setFirstResult(start);
		query.setMaxResults(max);
		for (Map.Entry<String, String> paramEntry : filters.entrySet()) {
			if("type".equals(paramEntry.getKey())){
				query.setParameter(paramEntry.getKey(), Setting.Type.valueOf(paramEntry.getValue()));
				count.setParameter(paramEntry.getKey(), Setting.Type.valueOf(paramEntry.getValue()));
			}else{
				query.setParameter(paramEntry.getKey(), "%" + paramEntry.getValue() + "%");
				count.setParameter(paramEntry.getKey(), "%" + paramEntry.getValue() + "%");
			}
		}
		return new ExtDirectStoreReadResult<Setting>(count.getSingleResult().intValue(),query.getResultList());
		
	}
	
	public Setting getSetting(String key, Setting.Type type){
		Setting setting = null;
		
		try {
			TypedQuery<Setting> query = em.createNamedQuery("QuerySettingByKeyAndType",Setting.class);
			query.setParameter("key", key);
			query.setParameter("type", type);
			
			setting = query.getSingleResult();
		} catch(NoResultException e){
			return null;
		} catch (Exception e) {
			throw new CoreServiceException(e);
		}
		
		return setting;
	}
	
	public String getStringValue(String key){
		Setting setting = getSetting(key, Setting.Type.STRING);
		if(null == setting){
			return null;
		}else{
			return setting.getValue();
		}
	}
	
	public String getStringValue(String key, String defaultValue){
		Setting setting = getSetting(key, Setting.Type.STRING);
		if(null == setting){
			return defaultValue;
		}else{
			return setting.getValue();
		}
	}
}
