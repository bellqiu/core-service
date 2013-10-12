package com.hb.core.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hb.core.entity.Option;
import com.hb.core.entity.OptionItem;
import com.hb.core.entity.Setting;
import com.hb.core.entity.User;
import com.hb.core.service.AccountService;
import com.hb.core.service.SettingService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:conf/applicationContext.xml"})
public class TestJPA {
	
	@Autowired
	private AccountService userService;
	
	@Autowired
	private SettingService settingService;
	
	@PersistenceContext
	private EntityManager em;
	
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
	
	@Test
	public void test3(){
		Setting setting = settingService.getSetting("XXXX", Setting.Type.STRING);
		
		Assert.assertEquals(setting.getValue(), "dddd");
	}
	
	@Test
	public void save() {
		Option option = em.find(Option.class, 1L);
		System.out.println(option.getName());
		List<OptionItem> items = new ArrayList<OptionItem>();
		option.setItems(items);
	}
	
	
	
}
