package com.honeybuy.shop.util;

import org.springframework.ui.Model;

import com.honeybuy.shop.web.dto.PageMeta;

public class PageMetaUtils {
	
	public static String [] stopWords = {"I, a, about, an, are, as, at, be, by, com, de, en, for, from, how, in, is, it, la, of, on, or, that, the, this, to, was, what, when, where, who, will, with, www."};

	public static String nonAlphanumeric = "!@#$%^&*()_+<>?\":[]-=';,./";
	
	public static String nonAlphanumericPattern = "!@#$%^&*()_+<>?\":\\[\\]\\-=';,./";
	
	public static void addPageMeta(Model model, String title, String keywords, String description) {
		PageMeta meta = new PageMeta();
		meta.setTitle(title);
		meta.setKeywords(keywords);
		meta.setDescription(description);
		model.addAttribute("pageMeta", meta);
	}
}
