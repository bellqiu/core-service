package com.hb.core.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hb.core.entity.Order;

@Transactional
@Service
public class OrderService {
	
	@PersistenceContext
	private EntityManager em;
	
	/*public Order saveOrUpdate(Order order){
		order = em.merge(order);
		return order;
	}
	*/
}
