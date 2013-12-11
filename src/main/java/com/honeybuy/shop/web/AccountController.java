/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.honeybuy.shop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hb.core.service.UserService;
import com.hb.core.shared.dto.UserDTO;

/**
 * 
 * @author <link href="wan-shan.zhu@hp.com">Spark Zhu</link>
 * @version 1.0
 */
@Controller
@RequestMapping("/ac")
public class AccountController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/login")
	public String login(){
		
		return "loginRequired";
	}
	
	@RequestMapping(value="/newAccount" , method=RequestMethod.GET)
	public String newAccount(){
		
		return "loginRequired";
	}
	
	@RequestMapping(value="/newAccount", method=RequestMethod.POST)
	public String newAccountPost(Model model, @RequestParam("regUsername") String username, @RequestParam("regPassword")String password){
		UserDTO user = userService.newUser(username, password);
		
		model.addAttribute("createdUser", user);
		
		return "loginRequired";
	}
	
	@RequestMapping(value="/json/forgotpassword", method=RequestMethod.GET)
	@ResponseBody
	public String forgotPassword(Model model, @RequestParam("email") String username){
		UserDTO user = userService.forgotPassword(username);
		
		if(user == null) {
			return "User is not existing";
		} else {
			return "Email is sent. Please check it";
			
		}
	}
	
	
}
