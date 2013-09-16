package com.hb.core.convert;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hb.core.entity.HTML;
import com.hb.core.shared.dto.HtmlDetailDTO;

@Service("htmlDetailConverter")
@Transactional(readOnly = true)
public class HtmlDetailConverter implements
		Converter<HtmlDetailDTO, HTML> {
	
	public HtmlDetailConverter() {
	}

	@PersistenceContext
	private EntityManager em;

	@Override
	public HtmlDetailDTO convert(HTML html) {
		HtmlDetailDTO HtmlDetailDTO = new HtmlDetailDTO();
		HtmlDetailDTO.setContent(html.getContent());
		HtmlDetailDTO.setName(html.getName());
		HtmlDetailDTO.setId(html.getId());
		return HtmlDetailDTO;
	}

	@Override
	public HTML transf(HtmlDetailDTO HtmlDetailDTO) {
		HTML html = new HTML();

		if (HtmlDetailDTO.getId() > 0) {
			html = em.find(HTML.class, HtmlDetailDTO.getId());
		}

		html.setCreateDate(html.getCreateDate() == null ? new Date()
				: html.getCreateDate());
		html.setUpdateDate(new Date());
		html.setContent(HtmlDetailDTO.getContent());
		html.setName(HtmlDetailDTO.getName());
		return html;
	}

}
