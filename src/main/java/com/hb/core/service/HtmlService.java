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

import com.hb.core.entity.HTML;
import com.hb.core.exception.CoreServiceException;
import com.hb.core.shared.dto.HtmlDetailDTO;

@Transactional
@Service
public class HtmlService {
	
	@PersistenceContext
	private EntityManager em;
	
	public HTML saveOrUpdate(HTML html){
		if(html.getId() < 1){
			if(null != getHTML(html.getName())){
				throw new CoreServiceException("HTML already exist");
			}
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
		return convertHTML2DTO(html);
	}


	public HtmlDetailDTO saveHTMLDetail(HtmlDetailDTO htmlDetailDTO) {
		HTML existingHTML = getHTML(htmlDetailDTO.getName());
		if (htmlDetailDTO.getId() < 1) {
			if (null != existingHTML) {
				throw new CoreServiceException("HTML already exist");
			}
		}else if (null!= existingHTML && existingHTML.getId() != htmlDetailDTO.getId()){
				throw new CoreServiceException("This name is not available");
		}
		
		HTML html = convertDTO2HTML(htmlDetailDTO);
		html = em.merge(html);
		
		return convertHTML2DTO(html);
	}
	
	private HtmlDetailDTO convertHTML2DTO(HTML html) {
		if(html == null) {
			return null;
		}
		HtmlDetailDTO htmlDetailDTO = new HtmlDetailDTO();
		htmlDetailDTO.setId(html.getId());
		htmlDetailDTO.setName(html.getName());
		htmlDetailDTO.setContent(html.getContent());
		return htmlDetailDTO;
	}
	
	private HTML convertDTO2HTML(HtmlDetailDTO htmlDetailDTO) {
		if(htmlDetailDTO == null) {
			return null;
		}
		HTML html = new HTML();
		if(htmlDetailDTO.getId() > 0) {
			html = getHtmlById(htmlDetailDTO.getId());
		}
		
		html.setName(htmlDetailDTO.getName());
		html.setContent(htmlDetailDTO.getContent());
		html.setUpdateDate(new Date());
		html.setCreateDate(html.getCreateDate() == null ? new Date() : html.getCreateDate());
		return html;
	}
}
