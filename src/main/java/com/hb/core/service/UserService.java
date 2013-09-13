package com.hb.core.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadResult;

import com.hb.core.entity.Component;
import com.hb.core.entity.User;
import com.hb.core.exception.CoreServiceException;
import com.hb.core.shared.dto.UserDTO;

@Service
@Transactional
public class UserService {
	
	@PersistenceContext
	private EntityManager em;
	
	public UserDTO getUserByName(String name) {
		UserDTO userDTO = convertUser2DTO(getUser(name));
		if(userDTO == null) {
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
		}
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
		
		return convertUser2DTO(user);
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
		User user = convertDTO2User(userDTO);
		user = em.merge(user);
		return convertUser2DTO(user);
	}
	
	public void destory(UserDTO userDTO){
		User user = convertDTO2User(userDTO);
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
			userDTOList.add(convertUser2DTO(user));
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
	
	public User getUserById(long id) {
		User user = null;
		user = em.find(User.class, id);
		return user;
	}
	
	public UserDTO getUserDTOById(long id) {
		User user = getUserById(id);
		return convertUser2DTO(user);
	}
	
	private UserDTO convertUser2DTO(User user) {
		if(user == null) {
			return null;
		}
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setEmail(user.getEmail());
		userDTO.setPassword(user.getPassword());
		userDTO.setEnabled(user.getStatus() == Component.Status.ACTIVE);
		List<String> roles = userDTO.getRoles();
		String role = user.getType() == null ? User.Type.USER.toString() : user.getType().toString();
		if(!roles.contains(role)) {
			roles.add(role);
		}
		return userDTO;
	}
	
	private User convertDTO2User(UserDTO userDTO) {
		if(userDTO == null) {
			return null;
		}
		User user = new User();
		if(userDTO.getId() > 0) {
			user = getUserById(userDTO.getId());
		}
		
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		List<String> roles = userDTO.getRoles();
		if(roles.size() > 0) {
			user.setType(User.Type.valueOf(roles.get(0)));
		}
		user.setStatus(userDTO.isEnabled() ? Component.Status.ACTIVE : Component.Status.DISABLED);
		user.setUpdateDate(new Date());
		user.setCreateDate(user.getCreateDate() == null ? new Date() : user.getCreateDate());
		return user;
	}
}