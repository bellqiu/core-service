package com.honeybuy.shop.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


public class UserUtils {
	public static UserDetails getCurrentUser(){
		
		Object userDetail = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(userDetail instanceof UserDetails){
			return (UserDetails)userDetail;
		}
		
		return null;
	}
}
