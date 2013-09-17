package com.hb.core.convert;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.hb.core.entity.HTML;
import com.hb.core.shared.dto.HtmlDetailDTO;

@org.springframework.stereotype.Component
public class HtmlDetailConverter implements
		Converter<HtmlDetailDTO, HTML> {
	
	public HtmlDetailConverter() {
	}

	@PersistenceContext
	private EntityManager em;

	@Override
	public HtmlDetailDTO convert(HTML html) {
		
		if(null == html){
			return null;
		}
		
		
		HtmlDetailDTO HtmlDetailDTO = new HtmlDetailDTO();
		HtmlDetailDTO.setContent(html.getContent());
		HtmlDetailDTO.setName(html.getName());
		HtmlDetailDTO.setId(html.getId());
		return HtmlDetailDTO;
	}

	@Override
	public HTML transf(HtmlDetailDTO htmlDetailDTO) {
		
		if(null == htmlDetailDTO){
			return null;
		}
		
		
		HTML html = new HTML();

		if (htmlDetailDTO.getId() > 0) {
			html = em.find(HTML.class, htmlDetailDTO.getId());
		}

		html.setCreateDate(html.getCreateDate() == null ? new Date()
				: html.getCreateDate());
		html.setUpdateDate(new Date());
		html.setContent(htmlDetailDTO.getContent());
		html.setName(htmlDetailDTO.getName());
		return html;
	}

}
