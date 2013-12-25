package com.honeybuy.shop.web.eds;

import java.util.ArrayList;
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

import com.hb.core.entity.Option;
import com.hb.core.service.ProductService;
import com.hb.core.shared.dto.ProductDetailDTO;
import com.hb.core.shared.dto.ProductSummaryDTO;
import com.hb.core.util.Constants;
import com.honeybuy.shop.util.RegexUtils;

@Service
@Transactional
@Secured("USER")
public class ProductDirectService {
	
	@Autowired
	private ProductService productService;
	
	@ExtDirectMethod(value=ExtDirectMethodType.FORM_LOAD)
	@Secured("ADMIN")
	public ProductDetailDTO loadProduct(@RequestParam("id") long id, @RequestParam(value="isCopy", required=false, defaultValue="false") boolean isCopy){
		
		ProductDetailDTO detailDTO = productService.getProductDetail(id);
		
		if(null == detailDTO){
			detailDTO = new ProductDetailDTO();
		} else if(isCopy) {
			detailDTO.setId(0);
			ArrayList<Option> options = new ArrayList<Option>();
			for(Option option : detailDTO.getOptions()) {
				options.add(productService.copyAndSaveOption(option));
			}
			detailDTO.setOptions(options);
		}
		
		return detailDTO;
	}
	
	@ExtDirectMethod(value=ExtDirectMethodType.STORE_READ)
	@Transactional(readOnly=true)
	public ExtDirectStoreReadResult<ProductSummaryDTO> list(ExtDirectStoreReadRequest storeRequest) {
		
		int start = storeRequest.getStart() == null ? 0 : storeRequest.getStart();
		int max = storeRequest.getLimit() == null ? 25 : storeRequest.getLimit();
		List<SortInfo> sorts = storeRequest.getSorters();
		String sort = "id";
		String dir = "DESCENDING";
		if(!sorts.isEmpty()){
			SortInfo sortInfo = sorts.get(0);
			String sortProperty = sortInfo.getProperty();
			if(!"categoryBreadcrumb".equalsIgnoreCase(sortProperty)) {
				sort = sortProperty;
				dir = sortInfo.getDirection().toString();
			}
		}
		
		Map<String,String> filters = new HashMap<String, String>();
		
		List<Filter> storeFilters = storeRequest.getFilters();
		
		for (Filter filter : storeFilters) {
			if(filter instanceof StringFilter){
				StringFilter stringFilter = (StringFilter) filter;
				filters.put(stringFilter.getField(), stringFilter.getValue());
			}
		}
		
		return productService.storeQuery(start, max, sort, "DESCENDING".equals(dir) ? "DESC" : "ASC" , filters);
	}
	
	@ExtDirectMethod(value=ExtDirectMethodType.STORE_MODIFY)
	@Secured("ADMIN")
	public ProductSummaryDTO update(ProductSummaryDTO productSummaryDTO) {
		productSummaryDTO.setName(RegexUtils.replaceSpecialChar(productSummaryDTO.getName(), Constants.SPECIAL_CHAR_REPLACEMENT));
		productSummaryDTO = productService.update(productSummaryDTO);
		
		return productSummaryDTO;
	}
	
	@ExtDirectMethod(value=ExtDirectMethodType.SIMPLE)
	@Secured("ADMIN")
	public ProductDetailDTO saveDetail(@Valid ProductDetailDTO product){
		product.setName(RegexUtils.replaceSpecialChar(product.getName(), Constants.SPECIAL_CHAR_REPLACEMENT));
		return productService.saveProductDetail(product);
	}
	
	@ExtDirectMethod(value=ExtDirectMethodType.SIMPLE)
	@Secured("ADMIN")
	public Option saveOption(@Valid Option option){
		
		return productService.saveOption(option);
	}
	
	@ExtDirectMethod(value=ExtDirectMethodType.STORE_MODIFY)
	@Secured("ADMIN")
	public ProductSummaryDTO setProductAsDelete(ProductSummaryDTO productSummaryDTO) {
		productSummaryDTO = productService.setProductAsDelete(productSummaryDTO);
		
		return productSummaryDTO;
	}
}
