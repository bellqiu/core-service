package com.honeybuy.shop.service;

import com.hb.core.shared.dto.UserDTO;

public interface IUserService {
	UserDTO getUserByName(String name);
	
	UserDTO newUser(String name, String password);
}
