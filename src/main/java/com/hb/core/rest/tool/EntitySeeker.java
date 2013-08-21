package com.hb.core.rest.tool;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.camel.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class EntitySeeker {

	private static Logger logger = LoggerFactory.getLogger(EntitySeeker.class);

	
	private EntityManager entityManager;
	
	@Handler
	public Object fetchById(String clazz, long id) {
		Object o = null;
		try {
			Class<?> clz = Class.forName(clazz);
			o = entityManager.find(clz, id);
		} catch (Exception e) {
			logger.error("Cannot create POJO {} ", new Object[] { clazz }, e);
		}

		return o;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	
	
	

}
