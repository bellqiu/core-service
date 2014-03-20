package com.honeybuy.shop.util;

import org.springframework.ui.Model;

import com.honeybuy.shop.web.dto.PageMeta;

public class PageMetaUtils {

	public static void addPageMeta(Model model, String title, String keywords, String description) {
		PageMeta meta = new PageMeta();
		meta.setTitle(title);
		meta.setKeywords(keywords);
		meta.setDescription(description);
		model.addAttribute("pageMeta", meta);
	}
}
