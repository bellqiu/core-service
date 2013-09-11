package com.honeybuy.shop.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hb.core.shared.dto.UserDTO;
@Service
@Transactional
public class UserServiceDev implements IUserService{
	
	private static final Map<String, UserDTO> users = new HashMap<String, UserDTO>();
	
	public UserServiceDev() {
		for (int i = 0; i < 3; i++) {
			String userName = "R" + i + "@hb.com";
			UserDTO userDTO = new UserDTO();
			userDTO.setEmail(userName);
			
			userDTO.setPassword("admin");
			userDTO.setId(i);
			
			if(i != 2){
				userDTO.setEnabled(true);
			}
			
			if(i == 0){
				userDTO.getRoles().add(com.hb.core.entity.User.Type.USER.toString());
			}else{
				userDTO.getRoles().add(com.hb.core.entity.User.Type.ADMIN.toString());
			}
			
			users.put(userName, userDTO);
		}
		
	}

	@Override
	public UserDTO getUserByName(String name) {
		return users.get(name);
	}

	@Override
	public UserDTO newUser(String username, String password) {
		
		UserDTO userDTO = new UserDTO();
		
		userDTO.setEmail(username);
		
		userDTO.setPassword(password);
		//userDTO.setUid(username);
		userDTO.getRoles().add(com.hb.core.entity.User.Type.USER.toString());
		userDTO.setEnabled(true);
		
		users.put(username, userDTO);
		
		
		return userDTO;
	}

}
