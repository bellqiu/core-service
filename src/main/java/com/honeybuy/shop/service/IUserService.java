package com.honeybuy.shop.service;

import com.honeybuy.shop.dto.UserDTO;

public interface IUserService {
	UserDTO getUserByName(String name);
	
	UserDTO newUser(String name, String password);
}
