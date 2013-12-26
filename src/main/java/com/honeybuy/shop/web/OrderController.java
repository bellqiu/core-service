/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.honeybuy.shop.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hb.core.service.OrderService;
import com.hb.core.shared.dto.OrderSummaryDTO;
import com.hb.core.util.Constants;
import com.honeybuy.shop.web.interceptor.SessionAttribute;

/**
 * 
 * @author <link href="wan-shan.zhu@hp.com">Spark Zhu</link>
 * @version 1.0
 */
@Controller
@RequestMapping("/od")
@Secured("USER")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/myOrder")
	public String myOder(Model model, 
			@SessionAttribute(value=Constants.LOGINUSER_SESSION_ATTR)UserDetails details){
		int start = 0;
		int max = 10;
		List<OrderSummaryDTO> orders = orderService.getUserOrderByUsername(details.getUsername(), start, max);
		model.addAttribute("orders", orders);
		model.addAttribute("page", "order");
		return "myOrder";
	}
	
}
