package com.honeybuy.shop.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class UserController {
	@RequestMapping("/loginOrSignUp")
	public String registerOrLogin(Model model){
		return "registerAndLogin";
	}
}
