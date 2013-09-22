package com.hb.core.service;

import java.util.Iterator;
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
import com.hb.core.entity.HTML;
import com.hb.core.exception.CoreServiceException;
import com.hb.core.shared.dto.HtmlDetailDTO;

@Transactional
@Service
public class HtmlService {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private Converter<HtmlDetailDTO, HTML> htmlDetailConverter; 
	
	public HTML saveOrUpdate(HTML html){
		HTML existingHTML = getHTML(html.getName());
		if(null != existingHTML && (existingHTML.getId() != html.getId() || html.getId() < 1)) {
			throw new CoreServiceException("HTML name already exist");
		}
		html = em.merge(html);
		return html;
	}
	
	public void destory(HTML html){
		html =  em.merge(html);
		em.remove(html);
	}
	
	public long getHTMLCount(){
		TypedQuery<Long> query = em.createNamedQuery("countAllHtml", Long.class);
		return query.getSingleResult();
	}

	public ExtDirectStoreReadResult<HTML> queryResult(int start, int max,
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
		
		StringBuffer queryStringPrefix = new StringBuffer("select h from HTML as h ");
		StringBuffer CountStringPrefix = new StringBuffer("select count(h.id) from HTML as h ");
		
		TypedQuery<HTML> query = em.createQuery( queryStringPrefix.append(ql).toString(), HTML.class);
		TypedQuery<Long> count = em.createQuery( CountStringPrefix.append(ql).toString(), Long.class);
		
		
		query.setFirstResult(start);
		query.setMaxResults(max);
		for (Map.Entry<String, String> paramEntry : filters.entrySet()) {
			query.setParameter(paramEntry.getKey(), "%" + paramEntry.getValue() + "%");
			count.setParameter(paramEntry.getKey(), "%" + paramEntry.getValue() + "%");
		}
		return new ExtDirectStoreReadResult<HTML>(count.getSingleResult().intValue(),query.getResultList());
	}
	
	public HTML getHTML(String key){
		HTML html = null;
		
		try {
			TypedQuery<HTML> query = em.createNamedQuery("QueryHtmlByKey",HTML.class);
			query.setParameter("key", key);
			
			html = query.getSingleResult();
		} catch(NoResultException e){
			return null;
		} catch (Exception e) {
			throw new CoreServiceException(e);
		}
		
		return html;
	}
	
	public String getContent(String key){
		HTML html = getHTML(key);
		if(null == html){
			return null;
		}else{
			return html.getContent();
		}
	}

	private HTML getHtmlById(long id) {
		return em.find(HTML.class, id);
	}
	
	public HtmlDetailDTO getHtmlDetailById(long id) {
		HTML html = getHtmlById(id);
		return htmlDetailConverter.convert(html);
	}


	public HtmlDetailDTO saveHTMLDetail(HtmlDetailDTO htmlDetailDTO) {
		HTML existingHTML = getHTML(htmlDetailDTO.getName());
		if(null != existingHTML && (existingHTML.getId() != htmlDetailDTO.getId() || htmlDetailDTO.getId() < 1)) {
			throw new CoreServiceException("HTML name already exist");
		}
		
		HTML html = htmlDetailConverter.transf(htmlDetailDTO);
		html = em.merge(html);
		
		return htmlDetailConverter.convert(html);
	}
}
