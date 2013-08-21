package com.honeybuy.shop.sercurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.honeybuy.shop.dto.UserDTO;
import com.honeybuy.shop.service.IUserService;

public class HBUserDetailService implements UserDetailsService{
	
	@Autowired
	private IUserService userService;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		UserDTO userDTO = userService.getUserByName(username);
		
		if(null == userDTO){
			throw new UsernameNotFoundException("User cannot find by name = [" + username + "]" );
		}
		
		return new HBUserDetail(userDTO);
	}

}
