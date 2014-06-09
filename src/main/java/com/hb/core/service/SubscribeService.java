package com.hb.core.service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hb.core.entity.Component;
import com.hb.core.entity.Subscriber;
import com.hb.core.exception.CoreServiceException;

@Transactional
@Service
public class SubscribeService {
	
	private static final Logger logger = LoggerFactory.getLogger(SubscribeService.class);

	@Autowired
	private SettingService settingService;

	@PersistenceContext
	private EntityManager em;
	
	public Subscriber getSubscriberByEmail(String email){
		Subscriber subscriber = null;
		
		try {
			TypedQuery<Subscriber> query = em.createNamedQuery("querySubscriberByName",Subscriber.class);
			query.setParameter("email", email);
			
			subscriber = query.getSingleResult();
		} catch(NoResultException e){
			return null;
		} catch (Exception e) {
			throw new CoreServiceException(e);
		}
		return subscriber;
	}

	public boolean saveSubscriber(String email) {
		if(EmailService.validateEmail(email)) {
			Subscriber subscriber = getSubscriberByEmail(email);
			if(subscriber == null) {
				subscriber = new Subscriber();
				subscriber.setEmail(email);
				em.persist(subscriber);
			} else if(subscriber.getStatus() != Component.Status.ACTIVE){
				subscriber.setStatus(Component.Status.ACTIVE);
				em.persist(subscriber);
			}
			return true;
		} 
		return false;
	}
	
	public void disableSubscriber(String email) {
		Subscriber subscriber = getSubscriberByEmail(email);
		if(subscriber != null && subscriber.getStatus() != Component.Status.DISABLED) {
			subscriber.setStatus(Component.Status.DISABLED);
			em.persist(subscriber);
		}
	}
	
	public void changeSubscriber(String oldEmail, String newEmail) {
		if(oldEmail != null && oldEmail.equals(newEmail)) {
			return;
		}
		Subscriber subscriber = getSubscriberByEmail(oldEmail);
		Subscriber newSubscriber = getSubscriberByEmail(newEmail);
		if(newSubscriber == null) {
			if(subscriber != null) {
				subscriber.setEmail(newEmail);
			} else {
				subscriber = new Subscriber();
				subscriber.setEmail(newEmail);
			}
			em.persist(subscriber);
		} else {
			if(newSubscriber.getStatus() == Component.Status.DISABLED) {
				newSubscriber.setStatus(Component.Status.ACTIVE);
				em.persist(newSubscriber);
			}
			if(subscriber != null && subscriber.getStatus() != Component.Status.DISABLED) {
				subscriber.setStatus(Component.Status.DISABLED);
				em.persist(subscriber);
			}
		}
	}
	
	public int increaseSubscriberCount(String email) {
		Subscriber subscriber = getSubscriberByEmail(email);
		if(subscriber != null) {
			int newCount = subscriber.getCount() + 1;
			subscriber.setCount(newCount);
			em.persist(subscriber);
			return newCount;
		}
		return 0;
	}
}
