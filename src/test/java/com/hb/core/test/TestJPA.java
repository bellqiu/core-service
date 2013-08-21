package com.hb.core.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hb.core.entity.User;
import com.hb.core.service.AccountService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:conf/applicationContext.xml"})
public class TestJPA {
	
	@Autowired
	private AccountService userService;
	
	@Test
	public void test(){
		User user = new User();
		user.setPassword("1111");
		user.setEmail("1@1.com");
		userService.newUser(user);
	}
	

	@Test
	public void test2(){
		User user = userService.getUserById(1L);
		System.out.println(user.getId()+"###########################################");
	}
}
