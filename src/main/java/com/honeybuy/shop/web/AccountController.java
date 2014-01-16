/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.honeybuy.shop.web;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.cxf.common.util.StringUtils;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hb.core.entity.Address;
import com.hb.core.exception.CoreServiceException;
import com.hb.core.service.UserService;
import com.hb.core.shared.dto.UserDTO;
import com.hb.core.util.Constants;
import com.honeybuy.shop.util.EncodingUtils;
import com.honeybuy.shop.util.JsonUtil;
import com.honeybuy.shop.web.cache.SettingServiceCacheWrapper;
import com.honeybuy.shop.web.dto.ResponseResult;
import com.honeybuy.shop.web.eds.SiteDirectService;
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
	private SettingServiceCacheWrapper settingService;

	
	@RequestMapping("/login")
	public String login(
			Model model, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="userId", required=false) String userId,
			@RequestParam(value="token", required=false) String token,
			@RequestParam(value="type", defaultValue="default", required=false) String loginType,
			HttpSession session){
		if(Constants.FACEBOOK_TYPE.equalsIgnoreCase(loginType)) {
			if (!StringUtils.isEmpty(userId) && !StringUtils.isEmpty(token)) {
				try {
					String secretKey = settingService.getStringValue(Constants.SETTING_FACEBOOK_SECRET_KEY, Constants.DEFAULT_FACEBOOK_SECRET_KEY);
					String secretProof = EncodingUtils.hmac256(secretKey, token);  
					URL url = new URL(String.format(Constants.FACEBOOK_VALIDAT_URL, userId, token, secretProof));
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
		} else if(Constants.GOOGLE_TYPE.equalsIgnoreCase(loginType)) {
			if (!StringUtils.isEmpty(token)) {
				try {
					URL url = new URL(String.format(Constants.GOOGLE_VALIDAT_URL, token));
					Object value = JsonUtil.getJsonFromURL(url);
					if(value instanceof Map) {
						Map<?,?> valueMap = (Map<?,?>) value;
						String email = (String)valueMap.get("email");
						UserDTO userDTO = userService.newThirdPartyUserIfNotExisting(email, Constants.GOOGLE_TYPE);
						
						model.addAttribute("username", userDTO.getEmail());
						model.addAttribute("password", userDTO.getPassword());
						return "loging";
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else if(Constants.LINKIN_TYPE.equalsIgnoreCase(loginType)) {
			if (!StringUtils.isEmpty(token)) {
				try {
					URL url = new URL(String.format(Constants.LINKIN_VALIDAT_URL, token));
					Object value = JsonUtil.getJsonFromURL(url);
					if(value instanceof String) {
						String email = (String)value;
						UserDTO userDTO = userService.newThirdPartyUserIfNotExisting(email, Constants.LINKIN_TYPE);
						
						model.addAttribute("username", userDTO.getEmail());
						model.addAttribute("password", userDTO.getPassword());
						return "loging";
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} /*else if(Constants.TWITTER_TYPE.equalsIgnoreCase(loginType)) {
			try {
				String callBack = settingService.getStringValue(SiteDirectService.DOMAIN_SERVER, "http://localhost") + "/ac/twitter-login";
				String apiKey = settingService.getStringValue(Constants.SETTING_TWITTER_API_KEY, Constants.DEFAULT_TWITTER_API_KEY);
				String apiSecret = settingService.getStringValue(Constants.SETTING_TWITTER_SECRET_KEY, Constants.DEFAULT_TWITTER_SECRET_KEY);
				OAuthService service = new ServiceBuilder()
                	.provider(TwitterApi.class)
                	.apiKey(apiKey)
                	.apiSecret(apiSecret)
                	.callback(callBack)
                	.build();
				Token requestToken = service.getRequestToken();
				session.setAttribute(Constants.TOKEN_SESSION_KEY, requestToken);
				return "redirect:" + service.getAuthorizationUrl(requestToken);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/
		return "loginRequired";
	}
	
	// Be removed as twitter does not pass email to app
	//@RequestMapping(value="/twitter-login" , method=RequestMethod.GET)
	@Deprecated
	public String twitterLogin(
			@RequestParam(value = "oauth_token", required = false) String oauth_token ,
			@RequestParam(value = "oauth_verifier", required = false) String oauth_verifier,
			Model model, HttpSession session){
		Token requestToken = (Token)session.getAttribute(Constants.TOKEN_SESSION_KEY);
		if(requestToken != null && requestToken.getToken().equals(oauth_token) && !StringUtils.isEmpty(oauth_verifier)) {
			try {
				String callBack = settingService.getStringValue(SiteDirectService.DOMAIN_SERVER, "http://localhost") + "/ac/twitter-login";
				String apiKey = settingService.getStringValue(Constants.SETTING_TWITTER_API_KEY, Constants.DEFAULT_TWITTER_API_KEY);
				String apiSecret = settingService.getStringValue(Constants.SETTING_TWITTER_SECRET_KEY, Constants.DEFAULT_TWITTER_SECRET_KEY);
				OAuthService service = new ServiceBuilder()
	            	.provider(TwitterApi.class)
	            	.apiKey(apiKey)
	            	.apiSecret(apiSecret)
	            	.callback(callBack)
	            	.build();
				Verifier verifier = new Verifier(oauth_verifier);
				Token accessToken = service.getAccessToken(requestToken, verifier);
				OAuthRequest request = new OAuthRequest(Verb.GET, Constants.TWITTER_VALIDAT_URL_PREFIX);
			    service.signRequest(accessToken, request);
			    Response response = request.send();
			    if(response.getCode() == 200) {
			    	String body = response.getBody();
			    	Object value = JsonUtil.getJsonFromString(body);
			    	if(value instanceof Map) {
						Map<?,?> valueMap = (Map<?,?>) value;
						String email = (String)valueMap.get("name");
						UserDTO userDTO = userService.newThirdPartyUserIfNotExisting(email, Constants.TWITTER_TYPE);
						model.addAttribute("username", userDTO.getEmail());
						model.addAttribute("password", userDTO.getPassword());
						return "loging";
			    	}
			    }
			    System.out.println(response.getBody());
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return "loginRequired";
		//return "loging";
	}
	
	@RequestMapping(value="/newAccount" , method=RequestMethod.GET)
	public String newAccount(){
		
		return "loginRequired";
	}
	
	@RequestMapping(value="/newAccount", method=RequestMethod.POST)
	public String newAccountPost(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam("regUsername") String username, @RequestParam("regPassword")String password) throws IOException, ServletException{
		try {
			if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
				UserDTO user = userService.registerUser(username, password);
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
	
	@Deprecated
	public String getUserProfile(Model model,
			@SessionAttribute(value=Constants.LOGINUSER_SESSION_ATTR)UserDetails details){
		model.addAttribute("page", "profile");
		model.addAttribute("email", details.getUsername());
		return "profile";
	}
	
	@RequestMapping(value="/changePwd", method=RequestMethod.GET)
	public String changePwdPage(Model model){
		model.addAttribute("page", "password");
		return "changePwd";
	}
	
	@ResponseBody
	@RequestMapping(value="/changePwd/json/change" , method=RequestMethod.POST)
	public Map<String, String> changePwd(
			Model model, 
			@RequestParam("oldPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword,
			@SessionAttribute(required=false, value=Constants.LOGINUSER_SESSION_ATTR)UserDetails details) {
		String username = details.getUsername();
		return userService.changePassord(username, oldPassword, newPassword);
	}
	
	@RequestMapping(value="/address", method={RequestMethod.GET})
	public String getUserAddresses(Model model, 
			@SessionAttribute(value=Constants.LOGINUSER_SESSION_ATTR)UserDetails details){
		List<Address> userAddresses = userService.getUserAddresses(details.getUsername());
		if(userAddresses == null) {
			userAddresses = new ArrayList<Address>();
		}
		model.addAttribute("addresses", userAddresses);
		
		model.addAttribute("page", "address");
		return "address";
	}
	
	@ResponseBody
	@RequestMapping(value="/address/{addressId:\\d+}", method={RequestMethod.GET})
	public ResponseResult<Address> getUserAddressById(@SessionAttribute(value=Constants.LOGINUSER_SESSION_ATTR)UserDetails details,
			@PathVariable("addressId") long addressId){
		
		Address address =  userService.getUserAddressById(addressId, details.getUsername());
		 
		if(null == address){
			ResponseResult<Address> addressResponse = new ResponseResult<Address>(false,null);
			addressResponse.getMessageMap().put("Error", "Address not found");
			
			return addressResponse;
		}
		
		 return  new ResponseResult<Address>(true, address);
	}
	
	@ResponseBody
	@RequestMapping(value="/address/save", method={RequestMethod.POST}, consumes="application/json")
	public Address saveUserAddress(@SessionAttribute(value=Constants.LOGINUSER_SESSION_ATTR)UserDetails details, @Valid @RequestBody Address address){
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		
		 Set<ConstraintViolation<Address>>  violations =  validator.validate(address);
		 
		 if(!violations.isEmpty()){
			 return null;
		 }
		
		return userService.saveAddress(details.getUsername(), address);
	}
	
	
	@RequestMapping(value="/address/fragment", method={RequestMethod.GET})
	public String userAddressFragment(@SessionAttribute(value=Constants.LOGINUSER_SESSION_ATTR)UserDetails details,
			@RequestParam(value="addressId", required=false, defaultValue="0") long addressId, Model model){
		
		Address address = null;
		
		if(addressId > 0){
			address = userService.getUserAddressById(addressId, details.getUsername());
		}
		
		
		if(null == address){
			List<Address> addresses =userService.getUserAddresses(details.getUsername());
			if(null != addresses && addresses.size() > 0){
				Address addForCopy =  addresses.get(0);
				address = new Address();
				address.setAddress1(addForCopy.getAddress1());
				address.setAddress2(addForCopy.getAddress2());
				address.setCity(addForCopy.getCity());
				address.setCountryCode(addForCopy.getCountryCode());
				address.setFirstName(addForCopy.getFirstName());
				address.setLastName(addForCopy.getLastName());
				address.setPhone(addForCopy.getPhone());
				address.setPostalCode(addForCopy.getPostalCode());
				address.setStateProvince(addForCopy.getStateProvince());
			}
		}
		
		if(null == address){
			address= new Address();
			address.setCountryCode("US");
		}
		
		
		model.addAttribute("address", address);
		
		return "addressFragment";
	}
	
	@ResponseBody
	@RequestMapping(value="/address/delete/{addressId:\\d+}", method={RequestMethod.POST})
	public ResponseResult<Address> deleteUserAddressById(@SessionAttribute(value=Constants.LOGINUSER_SESSION_ATTR)UserDetails details,
			@PathVariable("addressId") long addressId){
		
		Address address =  userService.removeUserAddressById(addressId, details.getUsername());
		if(null == address){
			ResponseResult<Address> addressResponse = new ResponseResult<Address>(false,null);
			addressResponse.getMessageMap().put("Error", "Address not found");
			
			return addressResponse;
		}
		return  new ResponseResult<Address>(true, address);
	}
	
}
