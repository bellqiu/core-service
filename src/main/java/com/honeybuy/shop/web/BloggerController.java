/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.honeybuy.shop.web;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hb.core.entity.Blogger;
import com.hb.core.service.BloggerService;
import com.honeybuy.shop.web.cache.BloggerServiceCacheWrapper;
import com.honeybuy.shop.web.dto.BloggerUploadResult;

/**
 * 
 * @author <link href="wan-shan.zhu@hp.com">Spark Zhu</link>
 * @version 1.0
 */
@Controller
@RequestMapping("")
public class BloggerController {
	
	@Autowired
	private BloggerService blogService;
	
	@Autowired
	private BloggerServiceCacheWrapper blogCacheService;
	
	private final static int BLOG_PER_PAGE = 50; 
	
	@RequestMapping(value="/admin/blog/upload", produces="application/json")
	@ResponseBody
	public BloggerUploadResult uploadImage(@RequestParam("file") MultipartFile file){
		BloggerUploadResult rs = new BloggerUploadResult();
		
		String filename = file.getOriginalFilename();
		
		try {
			Blogger blogger = blogService.newBlogger(file.getBytes(), filename);
			rs.setBlogger(blogger);
		} catch (Exception e) {
			rs.setMessage(e.getMessage());
			rs.setSuccess(false);
			return rs;
		}
		
		rs.setMessage("successful");
		rs.setSuccess(true);
		
		return rs;
	}
	
	@RequestMapping("/blog/list")
	public String bloggerList(){
		return "forward:/blog/list/0";
	}
	
	@RequestMapping("/blog/list/{page:\\d+}")
	public String bloggerList(
			@PathVariable("page") int page,
			Model model){
		
		int totalCount = blogCacheService.getActiveBloggerCount();
		int max = BLOG_PER_PAGE;
		
		int totalPage;
		if(totalCount % max == 0) {
			totalPage = totalCount / max;
		} else {
			totalPage = totalCount / max + 1;
		}
		int start = page * max;
		if(start >= totalCount) {
			page = 0;
			start = 0;
		}
		List<Blogger> bloggerList = blogCacheService.getAllActiveBlogger(start, max);
		
		if(bloggerList.size() > 0) {
			List<Integer> pageIds = new ArrayList<Integer>();
			if(totalPage <= 7) {
				for(int i = 0; i < totalPage; i++) {
					pageIds.add(i);
				}
			} else {
				if(page < 3) {
					for(int i = 0; i < 5; i++) {
						pageIds.add(i);
					}
					pageIds.add(-1);
					pageIds.add(totalPage - 1);
				} else if(page >= (totalPage - 3)) {
					pageIds.add(0);
					pageIds.add(-1);
					for(int i = totalPage - 5; i < totalPage; i++) {
						pageIds.add(i);
					}
				} else {
					pageIds.add(0);
					pageIds.add(-1);
					for(int i = -1; i <= 1; i++) {
						pageIds.add(page + i);
					}
					pageIds.add(-1);
					pageIds.add(totalPage - 1);
				}
			}
			model.addAttribute("resultStart", start + 1);
			model.addAttribute("resultEnd", start + bloggerList.size());
			model.addAttribute("resultTotal", totalCount);
			model.addAttribute("totalPage", totalPage);
			model.addAttribute("pageIds", pageIds);
			model.addAttribute("bloggers", bloggerList);
			model.addAttribute("currentPageIndex", page);
		}
		return "bloggerlist";
	}
	
	@RequestMapping("/blogger/{pageName}.pdf")
	@ResponseBody
	@Produces("application/pdf")
	public byte[] pdfPage(@PathVariable("pageName") String pageName, Model model) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream in = FreedomPageController.class.getResourceAsStream("/t1.pdf");
		BufferedInputStream inBuffer = new BufferedInputStream(in);
		int readSize = 0;
		byte[] buffer = new byte[4096];
		while((readSize = inBuffer.read(buffer)) != -1) {
			baos.write(buffer, 0, readSize);
		}
		in.close();
		return baos.toByteArray();
	}

}
