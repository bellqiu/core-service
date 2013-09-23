package com.honeybuy.shop.web.eds;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadResult;
import ch.ralscha.extdirectspring.bean.SortInfo;
import ch.ralscha.extdirectspring.filter.Filter;
import ch.ralscha.extdirectspring.filter.StringFilter;

import com.hb.core.service.TabProductService;
import com.hb.core.shared.dto.TabProductDTO;

@Service
@Transactional
@Secured("ADMIN")
public class TabProductDirectService {
	
	@Autowired
	private TabProductService tabProductService;
	
	@ExtDirectMethod(value=ExtDirectMethodType.STORE_READ)
	@Transactional(readOnly=true)
	public ExtDirectStoreReadResult<TabProductDTO> list(ExtDirectStoreReadRequest storeRequest) {
		
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
		
		return tabProductService.queryResult(start, max, sort, "DESCENDING".equals(dir) ? "DESC" : "ASC" , filters);
 		
	}
	
	@ExtDirectMethod(value=ExtDirectMethodType.STORE_MODIFY)
	public TabProductDTO update(TabProductDTO tabProductDTO) {
		if(tabProductDTO.getId() > 0 ){
			TabProductDTO tabExisting = tabProductService.getTabProductDTOById(tabProductDTO.getId());
			tabExisting.setName(tabProductDTO.getName());
			tabProductDTO = tabProductService.saveOrUpdate(tabExisting);
		} else {
			tabProductDTO = tabProductService.saveOrUpdate(tabProductDTO);
		}
		
		return tabProductDTO;
	}
	
	@ExtDirectMethod(value=ExtDirectMethodType.STORE_MODIFY)
	public void destory(TabProductDTO tabProductDTO) {
		 tabProductService.destory(tabProductDTO);
	}
	
	@ExtDirectMethod(value=ExtDirectMethodType.FORM_LOAD)
	public TabProductDTO loadTabProduct(@RequestParam("id") long id){
		
		TabProductDTO tabProductDTO = tabProductService.getTabProductDTOById(id);
		
		if(null == tabProductDTO){
			tabProductDTO = new TabProductDTO();
		}
		
		return tabProductDTO;
	}
	@ExtDirectMethod(value=ExtDirectMethodType.SIMPLE)
	public TabProductDTO saveTabProductDetail(@Valid TabProductDTO tabProductDTO){
		tabProductDTO = tabProductService.saveOrUpdate(tabProductDTO);
		return tabProductDTO;
	}
	
}
