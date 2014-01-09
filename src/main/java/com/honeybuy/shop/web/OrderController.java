/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.honeybuy.shop.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hb.core.service.OrderService;
import com.hb.core.shared.dto.OrderDetailDTO;
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
public class OrderController {
	
	private final static int USER_ORDER_PER_PAGE = 10;
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/myOrder")
	public String myOder(Model model, 
			@SessionAttribute(value=Constants.LOGINUSER_SESSION_ATTR)UserDetails details,
			@RequestParam(value = "page", required = false, defaultValue="0") final String currentpage){
		int page = 0;
		try {
			page = Integer.parseInt(currentpage);
		} catch(NumberFormatException e) {
			return "redirect:/od/myOrder";
		}
		int totalCount = orderService.getUserOrderCountByUsername(details.getUsername());
		if(totalCount > 0) {
			int max = USER_ORDER_PER_PAGE;
			int totalPage;
			if(totalCount % max == 0) {
				totalPage = totalCount / max;
			} else {
				totalPage = totalCount / max + 1;
			}
			int start = page * max;
			if(start >= totalCount) {
				// if page is a number more or greater than totalPage, it will go to last page
				page = totalPage - 1;
				start = page * max;
			}
			List<OrderSummaryDTO> orders = orderService.getUserOrderByUsername(details.getUsername(), start, max);
			
			if(orders != null && orders.size() > 0) {
				List<Integer> pageIds = new ArrayList<Integer>();
				if(totalPage <= 7) {
					for(int i = 0; i < totalPage; i++) {
						pageIds.add(i);
					}
				} else {
					if(page < 3) {
						for(int i = 0; i < 5; i++) {
							pageIds.add(i);
						}
						pageIds.add(-1);
						pageIds.add(totalPage - 1);
					} else if(page >= (totalPage - 3)) {
						pageIds.add(0);
						pageIds.add(-1);
						for(int i = totalPage - 5; i < totalPage; i++) {
							pageIds.add(i);
						}
					} else {
						pageIds.add(0);
						pageIds.add(-1);
						for(int i = -1; i <= 1; i++) {
							pageIds.add(page + i);
						}
						pageIds.add(-1);
						pageIds.add(totalPage - 1);
					}
				}
				model.addAttribute("resultStart", start + 1);
				model.addAttribute("resultEnd", start + orders.size());
				model.addAttribute("resultTotal", totalCount);
				model.addAttribute("totalPage", totalPage);
				model.addAttribute("pageIds", pageIds);
			}
			model.addAttribute("orders", orders);
			model.addAttribute("currentPageIndex", page);
		}
		model.addAttribute("page", "order");
		return "myOrder";
	}
	
	@RequestMapping("/orderDetail")
	public String myOderDetail(Model model, 
			@SessionAttribute(value=Constants.LOGINUSER_SESSION_ATTR)UserDetails details,
			@RequestParam(value = "orderId", required = false) final String orderId){
		if(!StringUtils.isEmpty(orderId)) {
			long id = 0;
			try {
				id = Long.valueOf(orderId);
			} catch(NumberFormatException e) {
			}
			OrderDetailDTO orderDetail = orderService.getOrderDetailById(id);
			if(orderDetail != null && details.getUsername().equals(orderDetail.getUseremail())) {
				model.addAttribute("orderDetail", orderDetail);
			} 
		}
		model.addAttribute("page", "order");
		return "orderDetail";
	}
	
}
