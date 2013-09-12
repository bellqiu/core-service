package com.honeybuy.shop.web.eds;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadResult;
import ch.ralscha.extdirectspring.bean.SortInfo;
import ch.ralscha.extdirectspring.filter.Filter;
import ch.ralscha.extdirectspring.filter.StringFilter;

import com.hb.core.service.UserService;
import com.hb.core.shared.dto.UserDTO;

@Service
@Transactional
@Secured("ADMIN")
public class UserDirectService {
	
	private static final Map<String, UserDTO> users = new HashMap<String, UserDTO>();
	
	
	@Autowired
	private UserService service;
	
	
	public UserDirectService() {
		for (int i = 0; i < 3; i++) {
			String userName = "R" + i + "@hb.com";
			UserDTO userDTO = new UserDTO();
			userDTO.setEmail(userName);
			
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
			
			users.put(userName, userDTO);
		}
	}

	@ExtDirectMethod(value=ExtDirectMethodType.STORE_READ)
	@Transactional(readOnly=true)
	public ExtDirectStoreReadResult<UserDTO> list(ExtDirectStoreReadRequest storeRequest) {
		
		int start = storeRequest.getStart() == null ? 0 : storeRequest.getStart();
		int max = storeRequest.getLimit() == null ? 25 : storeRequest.getLimit();
		List<SortInfo> sorts = storeRequest.getSorters();
		String sort = "id";
		String dir = "DESCENDING";
		if(!sorts.isEmpty()){
			SortInfo sortInfo = sorts.get(0);
			sort = sortInfo.getProperty();
			dir = sortInfo.getDirection().toString();
		}
		
		Map<String,String> filters = new HashMap<String, String>();
		
		List<Filter> storeFilters = storeRequest.getFilters();
		
		for (Filter filter : storeFilters) {
			if(filter instanceof StringFilter){
				StringFilter stringFilter = (StringFilter) filter;
				filters.put(stringFilter.getField(), stringFilter.getValue());
			}
		}
		
		return service.storeQuery(start, max, sort, "DESCENDING".equals(dir) ? "DESC" : "ASC" , filters);
 		
	}
	
	@ExtDirectMethod(value=ExtDirectMethodType.STORE_MODIFY)
	public UserDTO update(UserDTO userDTO) {
		userDTO = service.saveOrUpdate(userDTO);
		return userDTO;
	}
	
	@ExtDirectMethod(value=ExtDirectMethodType.STORE_MODIFY)
	public void destory(UserDTO userDTO) {
		 service.destory(userDTO);
	}
	
	
	public UserDTO getUserByName(String name) {
		return users.get(name);
	}

	public UserDTO newUser(String username, String password) {
		
		UserDTO userDTO = new UserDTO();
		
		userDTO.setEmail(username);
		
		userDTO.setPassword(password);
		//userDTO.setUid(username);
		userDTO.getRoles().add(com.hb.core.entity.User.Type.USER.toString());
		userDTO.setEnabled(true);
		
		users.put(username, userDTO);
		
		
		return userDTO;
	}
}
