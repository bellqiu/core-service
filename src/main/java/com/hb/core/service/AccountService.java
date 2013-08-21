package com.hb.core.service;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hb.core.entity.User;

@Transactional
@Service
public class AccountService {
	
	@PersistenceContext
	private EntityManager em;
	
	public void newUser(User user){
		em.persist(user);
	}
	
	public User getUserById(Serializable id){
		return em.find(User.class, id);
	}

}
