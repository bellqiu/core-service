package com.hb.core.convert;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hb.core.entity.Component;
import com.hb.core.entity.User;
import com.hb.core.shared.dto.UserDTO;

@Service
@Transactional(readOnly = true)
public class UserConverter implements
		Converter<UserDTO, User> {
	
	public UserConverter() {
	}

	@PersistenceContext
	private EntityManager em;

	@Override
	public UserDTO convert(User user) {
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

	@Override
	public User transf(UserDTO userDTO) {
		User user = new User();
		if(userDTO.getId() > 0) {
			user = em.find(User.class, userDTO.getId());
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
