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

import com.hb.core.entity.HTML;
import com.hb.core.service.HtmlService;

@Service
@Transactional
@Secured("ADMIN")
public class HtmlDirectService {
	
	@Autowired
	private HtmlService htmlService;
	
	@ExtDirectMethod(value=ExtDirectMethodType.STORE_READ)
	@Transactional(readOnly=true)
	public ExtDirectStoreReadResult<HTML> list(ExtDirectStoreReadRequest storeRequest) {
		
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
		
		return htmlService.queryResult(start, max, sort, "DESCENDING".equals(dir) ? "DESC" : "ASC" , filters);
 		
	}
	
	@ExtDirectMethod(value=ExtDirectMethodType.STORE_MODIFY)
	public HTML update(HTML html) {
		html.setUpdateDate(new Date());
		html = htmlService.saveOrUpdate(html);
		
		return html;
	}
	
	@ExtDirectMethod(value=ExtDirectMethodType.STORE_MODIFY)
	public void destory(HTML html) {
		htmlService.destory(html);
	}

}
