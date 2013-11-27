package com.hb.core.test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hb.core.entity.Order;
import com.hb.core.entity.OrderItem;
import com.hb.core.entity.Product;
import com.hb.core.entity.SelectedOpts;
import com.hb.core.entity.User;
import com.hb.core.service.OrderService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext-test.xml"})
public class TestJPA {
	
	@Autowired
	private OrderService orderService;
	
	
	@PersistenceContext
	private EntityManager em;
	
/*	@Test
	public void test(){
		Order order = new Order();
		order.setBillingAddress("billingAddress");
		order.setCouponCode("couponCode");
		order.setCouponCutOff(0.1f);
		order.setCurrency("USD");
		order.setCustomerMsg("customerMsg");
		order.setDeliveryPrice(0.2f);
		order.setUser(em.find(User.class, 1l));
		
		for (int i = 0; i < 2; i++) {
			
			OrderItem item = new OrderItem();
			item.setProduct(em.find(Product.class, Long.valueOf(6 + 1)));
			
			SelectedOpts selectedOpts = new SelectedOpts();
			selectedOpts.setValue("test" + i);
			
			item.getSelectedOpts().add(selectedOpts);
			
			order.getItems().add(item);
		}
		
		orderService.saveOrUpdate(order);
	}
	*/
	
	
}
