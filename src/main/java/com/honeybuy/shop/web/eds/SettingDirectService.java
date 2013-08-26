package com.honeybuy.shop.web.eds;

import java.util.Date;
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

import com.hb.core.entity.Setting;
import com.hb.core.service.SettingService;

@Service
@Transactional
@Secured("ADMIN")
public class SettingDirectService {
	
	@Autowired
	private SettingService service;
	
	@ExtDirectMethod(value=ExtDirectMethodType.STORE_READ)
	@Transactional(readOnly=true)
	public ExtDirectStoreReadResult<Setting> list(ExtDirectStoreReadRequest storeRequest) {
		
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
	
	/*@ExtDirectMethod(value=ExtDirectMethodType.STORE_MODIFY)
	public List<Setting> update(List<Setting> settings) {
		List<Setting> rs = new ArrayList<Setting>();
		if(null != rs){
			for (Setting setting : rs) {
				setting.setUpdateDate(new Date());
				setting = service.saveOrUpdate(setting);
				rs.add(setting);
			}
		}
		
		
		return rs;
	}
	*/
	@ExtDirectMethod(value=ExtDirectMethodType.STORE_MODIFY)
	public Setting update(Setting setting) {
		setting.setUpdateDate(new Date());
		setting = service.saveOrUpdate(setting);
		
		return setting;
	}
	
	@ExtDirectMethod(value=ExtDirectMethodType.STORE_MODIFY)
	public void destory(Setting setting) {
		 service.destory(setting);
	}
}
