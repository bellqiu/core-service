package com.hb.core.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadResult;

import com.hb.core.convert.Converter;
import com.hb.core.entity.TabProduct;
import com.hb.core.exception.CoreServiceException;
import com.hb.core.shared.dto.TabProductDTO;

@Transactional
@Service
public class TabProductService {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private Converter<TabProductDTO, TabProduct> tabProductConverter; 
	
	public TabProductDTO saveOrUpdate(TabProductDTO tabProductDTO){
		TabProduct existingTabProduct = getTabProductByName(tabProductDTO.getName());
		if(null != existingTabProduct && (existingTabProduct.getId() != tabProductDTO.getId() || tabProductDTO.getId() < 1)){
			throw new CoreServiceException("TabProduct name already exist");
		}
		
		TabProduct tabProduct = tabProductConverter.transf(tabProductDTO);
		tabProduct = em.merge(tabProduct);
		
		return tabProductConverter.convert(tabProduct);
	}
	
	public void destory(TabProductDTO tabProductDTO){
		TabProduct tabProduct = tabProductConverter.transf(tabProductDTO);
		tabProduct = em.merge(tabProduct);
		em.remove(tabProduct);
	}
	
	public void destory(long id){
		TabProduct tabProduct =  em.find(TabProduct.class, id);
		if(tabProduct != null) {
			em.remove(tabProduct);
		}
	}
	
	public long getTabProductCount(){
		TypedQuery<Long> query = em.createNamedQuery("countAllTabProduct", Long.class);
		return query.getSingleResult();
	}

	public ExtDirectStoreReadResult<TabProductDTO> queryResult(int start, int max,
			String sort, String direction, Map<String, String> filters) {
		StringBuffer ql = new StringBuffer("");
		if(!filters.isEmpty()){
			ql.append(" where ");
			Iterator<String> item = filters.keySet().iterator();
			while(item.hasNext()){
				String param = item.next();
				ql.append(param +" like :"+param +" ");
				if(item.hasNext()){
					ql.append(" and ");
				}
			}
		}
		
		ql.append(" order by " + sort + " " + direction);
		
		StringBuffer queryStringPrefix = new StringBuffer("select tp from TabProduct as tp ");
		StringBuffer countStringPrefix = new StringBuffer("select count(tp.id) from TabProduct as tp ");
		
		TypedQuery<TabProduct> query = em.createQuery( queryStringPrefix.append(ql).toString(), TabProduct.class);
		TypedQuery<Long> count = em.createQuery( countStringPrefix.append(ql).toString(), Long.class);
		
		query.setFirstResult(start);
		query.setMaxResults(max);
		for (Map.Entry<String, String> paramEntry : filters.entrySet()) {
			query.setParameter(paramEntry.getKey(), "%" + paramEntry.getValue() + "%");
			count.setParameter(paramEntry.getKey(), "%" + paramEntry.getValue() + "%");
		}
		List<TabProduct> resultList = query.getResultList();
		List<TabProductDTO> tabProductDTOList = new ArrayList<TabProductDTO>(resultList.size());
		for(TabProduct tp : resultList) {
			tabProductDTOList.add(tabProductConverter.convert(tp));
		}
		int total = count.getSingleResult().intValue();
		return new ExtDirectStoreReadResult<TabProductDTO>(total, tabProductDTOList);
	}
	
	public TabProduct getTabProductByName(String name){
		TabProduct tabProduct = null;
		
		try {
			TypedQuery<TabProduct> query = em.createNamedQuery("QueryTabProductByName",TabProduct.class);
			query.setParameter("name", name);
			
			tabProduct = query.getSingleResult();
		} catch(NoResultException e){
			return null;
		} catch (Exception e) {
			throw new CoreServiceException(e);
		}
		return tabProduct;
	}
	
	public TabProduct getTabProductById(long id) {
		return em.find(TabProduct.class, id);
	}
	
	public TabProductDTO getTabProductDTOById(long id) {
		if(id > 0) {
			TabProduct tabproduct = em.find(TabProduct.class, id);
			if(tabproduct != null) {
				return tabProductConverter.convert(tabproduct);
			}
		}
		return null;
	}
	
}
