package com.hb.core.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.codec.binary.Base64;
import org.apache.cxf.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadResult;

import com.hb.core.convert.Converter;
import com.hb.core.entity.Address;
import com.hb.core.entity.User;
import com.hb.core.exception.CoreServiceException;
import com.hb.core.shared.dto.UserDTO;

@Service
@Transactional
public class UserService {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	private Converter<UserDTO, User> userConverter; 
	
	public UserDTO getUserByName(String name) {
		UserDTO userDTO = userConverter.convert(getUser(name));
		/*if(userDTO == null) {
			// TODO need to remove when before code is release
			// This is for default login user
			for (int i = 0; i < 3; i++) {
				String userPreDefinedName = "R" + i + "@hb.com";
				if(userPreDefinedName.equals(name)) {
					userDTO = new UserDTO();
					userDTO.setEmail(userPreDefinedName);
					
					userDTO.setPassword("admin");
					userDTO.setId(i);
					
					if(i != 2){
						userDTO.setEnabled(true);
					}
					
					if(i == 0){
						userDTO.getRoles().add(com.hb.core.entity.User.Type.USER.toString());
					}else{
						userDTO.getRoles().add(com.hb.core.entity.User.Type.ADMIN.toString());
					}
					break;
				}
			}
		}*/
		return userDTO;
	}

	public UserDTO newUser(String username, String password) {
		if(getUser(username) != null) {
			throw new CoreServiceException("User already exist");
		}
		
		User user = new User();
		user.setEmail(username);
		user.setPassword(password);
		user.setType(User.Type.USER);
		Date currentDate = new Date();
		user.setCreateDate(currentDate);
		user.setUpdateDate(currentDate);
		user = em.merge(user);
		
		return userConverter.convert(user);
	}
	
	public UserDTO saveOrUpdate(UserDTO userDTO){
		User existingUser = getUser(userDTO.getEmail());
		if (userDTO.getId() < 1) {
			if (null != existingUser) {
				throw new CoreServiceException("User already exist");
			}
			userDTO.setEnabled(true);
		} else if (null != existingUser
				&& existingUser.getId() != userDTO.getId()) {
			throw new CoreServiceException("This email has been registered");
		}
		User user = userConverter.transf(userDTO);
		user = em.merge(user);
		return userConverter.convert(user);
	}
	
	public void destory(UserDTO userDTO){
		User user = userConverter.transf(userDTO);
		user = em.merge(user);
		em.remove(user);
	}
	
	public long getUserCount(){
		TypedQuery<Long> query = em.createNamedQuery("countAllUser", Long.class);
		return query.getSingleResult();
	}
	
	
	public ExtDirectStoreReadResult<UserDTO> storeQuery(int start, int max, String sort, String dir, Map<String,String> filters){
		StringBuffer ql = new StringBuffer("");
		if(!filters.isEmpty()){
			ql.append(" where ");
			Iterator<String> item = filters.keySet().iterator();
			while(item.hasNext()){
				String param = item.next();
				if("roles".equalsIgnoreCase(param)){
					ql.append(" type = :type ");
				}else{
					ql.append(param +" like :"+param +" ");
				}
				if(item.hasNext()){
					ql.append(" and ");
				}
			}
		}
		
		ql.append(" order by " + sort + " " + dir);
		
		StringBuffer queryStringPrefix = new StringBuffer("select s from User as s ");
		StringBuffer countStringPrefix = new StringBuffer("select count(s.id) from User as s ");
		
		TypedQuery<User> query = em.createQuery( queryStringPrefix.append(ql).toString(), User.class);
		TypedQuery<Long> count = em.createQuery( countStringPrefix.append(ql).toString(), Long.class);
		
		
		query.setFirstResult(start);
		query.setMaxResults(max);
		for (Map.Entry<String, String> paramEntry : filters.entrySet()) {
			if("roles".equals(paramEntry.getKey())){
				query.setParameter("type", User.Type.valueOf(paramEntry.getValue()));
				count.setParameter("type", User.Type.valueOf(paramEntry.getValue()));
			}else{
				query.setParameter(paramEntry.getKey(), "%" + paramEntry.getValue() + "%");
				count.setParameter(paramEntry.getKey(), "%" + paramEntry.getValue() + "%");
			}
		}
		int total = count.getSingleResult().intValue();
		List<User> resultList = query.getResultList();
		List<UserDTO> userDTOList = new ArrayList<UserDTO>(resultList.size());
		for(User user : resultList) {
			userDTOList.add(userConverter.convert(user));
		}
		return new ExtDirectStoreReadResult<UserDTO>(total, userDTOList);
	}
	
	public User getUser(String email){
		User user = null;
		
		try {
			TypedQuery<User> query = em.createNamedQuery("QueryUserByEmail", User.class);
			query.setParameter("email", email);
			
			user = query.getSingleResult();
		} catch(NoResultException e){
			return null;
		} catch (Exception e) {
			throw new CoreServiceException(e);
		}
		
		return user;
	}
	
	private User getUserById(long id) {
		return em.find(User.class, id);
	}
	
	public UserDTO getUserDTOById(long id) {
		User user = getUserById(id);
		return userConverter.convert(user);
	}

	public UserDTO forgotPassword(String username) {
		// TODO send email
		if(StringUtils.isEmpty(username)) {
			return null;
		}
		User user = getUser(username);
		if(user != null) {
			String randomString = new String(Base64.encodeBase64URLSafe(UUID.randomUUID().toString().getBytes()));
			final String newPassword = randomString.substring(0, 8);
			final String email = user.getEmail(); 
			user.setPassword(newPassword);
			user = em.merge(user);
			new Thread(){
                public void run() {
                    try{
						emailService.sendRecoveryMail(email, newPassword);
                    } catch (Exception e){
                    }
                };
            }.start();
		}
		return userConverter.convert(user);
	}
	
	public UserDTO registerUser(final String username, final String password) {
		if(getUser(username) != null) {
			throw new CoreServiceException("User already exist");
		}
		
		User user = new User();
		user.setEmail(username);
		user.setPassword(password);
		user.setType(User.Type.USER);
		Date currentDate = new Date();
		user.setCreateDate(currentDate);
		user.setUpdateDate(currentDate);
		user = em.merge(user);
		
		if(user != null) {
			new Thread(){
                public void run() {
                    try{
						emailService.sendRegisterMail(username, password);
                    } catch (Exception e){
                    }
                };
            }.start();
		}
		
		return userConverter.convert(user);
	}
	
	public UserDTO newThirdPartyUserIfNotExisting(String username, String type) {
		String newUsername = username + "/" + type;
		User user = getUser(newUsername);
		if(user == null) {
			return newUser(newUsername, UUID.randomUUID().toString());
		} else {
			return userConverter.convert(user); 
		}
	}

	public Map<String, String> changePassord(String username,
			String oldPassword, String newPassword) {
		Map<String, String> messageMap = new HashMap<String, String>();
		User user = getUser(username);
		if(user == null) {
			messageMap.put("status", "false");
			messageMap.put("message", "User is not existing. Please check it.");
		} else {
			if(user.getPassword().equals(oldPassword)) {
				user.setPassword(newPassword);
				em.merge(user);
				messageMap.put("status", "true");
				messageMap.put("message", "Password is changed successfully.");
			} else {
				messageMap.put("status", "false");
				messageMap.put("message", "Input password is not correct.");
			}
		}
		return messageMap;
	}
	
	public List<Address> getUserAddresses(String email){
		User user = getUser(email);
		if(null != user){
			return user.getAddresses();
		}
		return null;
	}
	
	public Address saveAddress(String userEmail, Address address){
		User user = getUser(userEmail);
		if(null != user){
			boolean nonExistingAdd = true;
			for (Address add : user.getAddresses()) {
				if(add.getId() == address.getId()){
					add.setAddress1(address.getAddress1());
					add.setAddress2(address.getAddress2());
					add.setCity(address.getCity());
					add.setCountryCode(address.getCountryCode());
					add.setFirstName(address.getFirstName());
					add.setLastName(address.getLastName());
					add.setPhone(address.getPhone());
					add.setPostalCode(address.getPostalCode());
					add.setStateProvince(address.getStateProvince());
					add.setCreateDate(new Date());
					add.setUpdateDate(new Date());
					nonExistingAdd = false;
				}
			}
			if(nonExistingAdd){
				address = em.merge(address);
				user.getAddresses().add(address);
			}
			
			em.merge(user);
			em.persist(user);
			em.flush();
			
			return address;
		}
		return null;
	}

	public Address getUserAddressById(long addressId, String useremail) {
		
		User user = getUser(useremail);
		
		if(null == user || user.getAddresses()==null || user.getAddresses().size() < 1){
			return null;
		}
		
		Address address = null;
		
		for (Address add : user.getAddresses()) {
			if(add.getId() == addressId){
				address = add;
				break;
			}
		}
		
		return address;
	}
	
	public Address removeUserAddressById(long addressId, String useremail) {
		
		User user = getUser(useremail);
		
		if(null == user || user.getAddresses()==null || user.getAddresses().size() < 1){
			return null;
		}
		
		Address address = null;
		for (int i = 0, length = user.getAddresses().size(); i< length; i++) {
			Address add = user.getAddresses().get(i);
			if(add.getId() == addressId){
				address = add;
				user.getAddresses().remove(i);
				break;
			}
		}
		em.merge(user);
		
		return address;
	}
	
}
