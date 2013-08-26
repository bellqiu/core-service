/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.honeybuy.shop.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hb.core.service.ImageResourceService;
import com.honeybuy.shop.web.dto.ImageUploadResult;

/**
 * 
 * @author <link href="wan-shan.zhu@hp.com">Spark Zhu</link>
 * @version 1.0
 */
@Controller
@RequestMapping("/admin")
@Secured("ADMIN")
public class ImageProcessorController {
	
	@Autowired
	private ImageResourceService imageResourceService;
	
	@RequestMapping(value="/image/upload",produces="application/json")
	@ResponseBody
	public ImageUploadResult uploadImage(@RequestParam("file") MultipartFile file){
		ImageUploadResult rs = new ImageUploadResult();
		
		String filename = file.getOriginalFilename();
		
		try {
			imageResourceService.newImage(file.getBytes(), filename);
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
