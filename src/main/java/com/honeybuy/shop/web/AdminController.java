/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.honeybuy.shop.web;

import java.io.IOException;

import net.sf.ehcache.CacheManager;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * @author <link href="wan-shan.zhu@hp.com">Spark Zhu</link>
 * @version 1.0
 */
@Controller
@RequestMapping("/admin")
@Secured("ADMIN")
public class AdminController {

	@RequestMapping("/console")
	public String console() {

		return "admin_console";
	}

	@RequestMapping("/cache/list")
	public String listCache(Model model) throws IOException {
		model.addAttribute("cache", CacheManager.getInstance().getCacheNames());
		
		return "admin_cache";
	}

	@RequestMapping("/cache/remove")
	public String removeCacheCache(Model model, @RequestParam("el") String cache)
			throws IOException {
		CacheManager.getInstance().getCache(cache).removeAll();
		return "redirect:/admin/cache/list";
	}
}
