package com.honeybuy.shop.sercurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hb.core.service.UserService;
import com.hb.core.shared.dto.UserDTO;

public class HBUserDetailService implements UserDetailsService{
	
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		UserDTO userDTO = userService.getUserByName(username);
		
		if(null == userDTO){
			/*userDTO = new UserDTO();
			userDTO.setEmail("ANONYMOUS");
			userDTO.getRoles().add("ANONYMOUS");*/
			throw new UsernameNotFoundException("User cannot find by name = [" + username + "]" );
		}
		
		return new HBUserDetail(userDTO);
	}

}
