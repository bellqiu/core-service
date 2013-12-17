/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.honeybuy.shop.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hb.core.exception.CoreServiceException;
import com.hb.core.service.UserService;
import com.hb.core.shared.dto.UserDTO;
import com.honeybuy.shop.sercurity.LoginSuccessHandler;

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
	
	@Autowired
	LoginSuccessHandler loginSuccessHandler;
	
	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;
	
	@RequestMapping("/login")
	public String login(@RequestParam(value="type", defaultValue="default", required=false) String loginType){
		if("facebook".equalsIgnoreCase(loginType)) {
			
		}
		return "loginRequired";
	}
	
	@RequestMapping(value="/newAccount" , method=RequestMethod.GET)
	public String newAccount(){
		
		return "loginRequired";
	}
	
	@RequestMapping(value="/newAccount", method=RequestMethod.POST)
	public String newAccountPost(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam("regUsername") String username, @RequestParam("regPassword")String password) throws IOException, ServletException{
		// TODO facebook
		try {
			if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
				UserDTO user = userService.newUser(username, password);

				model.addAttribute("createdUser", user);
				
				
		}
		} catch(CoreServiceException e) {
			model.addAttribute("isSignUpPage", true);
			model.addAttribute("isSignUpFail", true);
			return "forward:/ac/login";
		}
		
		handleLogin(request, response, username, password);
		
		return null;
	}
	
	@RequestMapping(value="/json/forgotpassword", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, String> forgotPassword(Model model, @RequestParam("email") String username){
		UserDTO user = userService.forgotPassword(username);
		
		Map<String, String> messageMap = new HashMap<String, String>();
		if(user == null) {
			messageMap.put("status", "false");
			messageMap.put("message", "User is not existing. Please check it.");
		} else {
			messageMap.put("status", "true");
			messageMap.put("message", "Email with new password is sent. Please check it.");
		}
		return messageMap;
	}
	
	private void handleLogin(HttpServletRequest request, HttpServletResponse response, String username, String password) throws IOException, ServletException {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		loginSuccessHandler.onAuthenticationSuccess(request, response, authentication);
	}
	
	
}
