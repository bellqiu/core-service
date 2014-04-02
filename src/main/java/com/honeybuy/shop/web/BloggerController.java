/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.honeybuy.shop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hb.core.entity.Blogger;
import com.hb.core.service.BloggerService;
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
	private BloggerService bloggerService;
	
	@RequestMapping(value="/admin/blogger/upload", produces="application/json")
	@ResponseBody
	public BloggerUploadResult uploadImage(@RequestParam("file") MultipartFile file){
		BloggerUploadResult rs = new BloggerUploadResult();
		
		String filename = file.getOriginalFilename();
		
		try {
			Blogger blogger = bloggerService.newBlogger(file.getBytes(), filename);
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

}
