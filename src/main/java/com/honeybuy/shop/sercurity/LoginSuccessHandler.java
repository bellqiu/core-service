package com.honeybuy.shop.sercurity;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.util.StringUtils;

import com.hb.core.service.OrderService;
import com.hb.core.shared.dto.OrderDetailDTO;
import com.honeybuy.shop.util.UserUtils;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{
	
	@Autowired
	private OrderService orderService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		String trackingId = null;
		
		Cookie[] cookies = request.getCookies();
		
		if(null != cookies){
			for (Cookie cookie : cookies) {
				if("trackingId".equals(cookie.getName())){
					trackingId = cookie.getValue();
				}
			}
		}
		
		if(!StringUtils.isEmpty(trackingId)){
			OrderDetailDTO detailDTO = orderService.getCart(trackingId, null);
			if(null != detailDTO){
				orderService.assign(trackingId, UserUtils.getCurrentUser().getUsername());
			}
		}
		
		super.onAuthenticationSuccess(request,response,authentication);
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

}
