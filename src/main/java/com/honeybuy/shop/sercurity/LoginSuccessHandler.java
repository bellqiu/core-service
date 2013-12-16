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
import com.hb.core.util.Constants;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler implements Constants{
	
	
	@Autowired
	private OrderService orderService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		HBUserDetail hbUserDetail = null;
		
		if(authentication.getPrincipal() instanceof HBUserDetail){
			hbUserDetail = (HBUserDetail) authentication.getPrincipal();
			request.getSession().setAttribute(LOGINUSER_SESSION_ATTR, hbUserDetail);
		}
		
		String trackingId = null;
		
		Cookie[] cookies = request.getCookies();
		
		if(null != cookies){
			for (Cookie cookie : cookies) {
				if(Constants.TRACKING_COOKE_ID_NAME.equals(cookie.getName())){
					trackingId = cookie.getValue();
				}
			}
		}
		if(!StringUtils.isEmpty(trackingId)){
			OrderDetailDTO detailDTO = orderService.getCart(trackingId, null);
			if(null != detailDTO){
				orderService.assign(trackingId, hbUserDetail.getUsername());
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
