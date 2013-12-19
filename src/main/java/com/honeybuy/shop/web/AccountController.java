/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.honeybuy.shop.web;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hb.core.exception.CoreServiceException;
import com.hb.core.service.UserService;
import com.hb.core.shared.dto.UserDTO;
import com.hb.core.util.Constants;
import com.honeybuy.shop.util.EncodingUtils;
import com.honeybuy.shop.util.JsonUtil;
import com.honeybuy.shop.web.cache.SettingServiceCacheWrapper;
import com.honeybuy.shop.web.interceptor.SessionAttribute;

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
	SettingServiceCacheWrapper settingService;
	
	@RequestMapping("/login")
	public String login(
			Model model, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="userId", required=false) String userId,
			@RequestParam(value="token", required=false) String token,
			@RequestParam(value="type", defaultValue="default", required=false) String loginType){
		if(Constants.FACEBOOK_TYPE.equalsIgnoreCase(loginType)) {
			if (!StringUtils.isEmpty(userId) && !StringUtils.isEmpty(token)) {
				try {
					String secretKey = settingService.getStringValue(Constants.SETTING_FACEBOOK_SECRET_KEY, Constants.DEFAULT_FACEBOOK_SECRET_KEY);
					String secretProof = EncodingUtils.hmac256(secretKey, token);  
					URL url = new URL(Constants.FACEBOOK_VALIDAT_URL_PREFIX + "&access_token=" + token + "&appsecret_proof=" + secretProof);
					Object value = JsonUtil.getJsonFromURL(url);
					if(value instanceof Map) {
						Map<?,?> valueMap = (Map<?,?>) value;
						String email = (String)valueMap.get("email");
						UserDTO userDTO = userService.newThirdPartyUserIfNotExisting(email, Constants.FACEBOOK_TYPE);
						
						model.addAttribute("username", userDTO.getEmail());
						model.addAttribute("password", userDTO.getPassword());
						return "loging";
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return "loginRequired";
	}
	
	@RequestMapping(value="/newAccount" , method=RequestMethod.GET)
	public String newAccount(){
		
		return "loginRequired";
	}
	
	@RequestMapping(value="/newAccount", method=RequestMethod.POST)
	public String newAccountPost(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam("regUsername") String username, @RequestParam("regPassword")String password) throws IOException, ServletException{
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
		
		model.addAttribute("username", username);
		model.addAttribute("password", password);
		
		return "loging";
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
	
	@RequestMapping(value="/profile" , method=RequestMethod.GET)
	public String getUserProfile(){
		return "userprofile";
	}
	
	@ResponseBody
	@RequestMapping(value="/profile/json/changepassword" , method=RequestMethod.POST)
	public Map<String, String> changePassword(
			Model model, 
			@RequestParam("oldPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword,
			@SessionAttribute(required=false, value=Constants.LOGINUSER_SESSION_ATTR)UserDetails details) {
		String username = details.getUsername();
		return userService.changePassord(username, oldPassword, newPassword);
	}
	
}
