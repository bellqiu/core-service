package com.honeybuy.shop.web.eds;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectFormPostResult;

import com.hb.core.service.CategoryService;
import com.hb.core.shared.dto.CategoryDetailDTO;
import com.hb.core.shared.dto.CategoryTreeDTO;

@Service
@Transactional
@Secured("ADMIN")
public class CategoryDirectService {
	
	@Autowired
	private CategoryService categoryService;
	
	@ExtDirectMethod(value=ExtDirectMethodType.TREE_LOAD)
	@Transactional(readOnly=true)
	public List<CategoryTreeDTO> list(@RequestParam(value = "id", required = false) String node) {
		int id = 0;
		
		if(null != node && node.matches("\\d+")){
			id = Integer.valueOf(node); 
		}
		
		return categoryService.getCategoryTree(id);
	}
	
/*	@ExtDirectMethod(value=ExtDirectMethodType.STORE_MODIFY)
	public CategoryDetailDTO saveDetail(CategoryDetailDTO categoryDetailDTO){
		categoryDetailDTO = new CategoryDetailDTO();
		categoryDetailDTO.setPageTitle("saved");
		System.out.println("#################Save CategoryDetailDTO");
		return categoryDetailDTO;
	}*/
	
	@ExtDirectMethod(value=ExtDirectMethodType.FORM_LOAD)
	public CategoryDetailDTO loadDetail(@RequestParam(value = "id") long id, @RequestParam(value = "parent", required = false) long parent){
		CategoryDetailDTO categoryDetailDTO = new CategoryDetailDTO();
		categoryDetailDTO.setPageTitle("saved");
		categoryDetailDTO.setName("Test");
		categoryDetailDTO.setParentId(parent);
		System.out.println("#################load CategoryDetailDTO");
		return categoryDetailDTO;
	}
	
	@ExtDirectMethod(value=ExtDirectMethodType.FORM_POST)
	public ExtDirectFormPostResult saveDetail(@Valid CategoryDetailDTO categoryDetailDTO, BindingResult result){
		if (!result.hasErrors()) {
			if (categoryService.getCategoryByName(categoryDetailDTO.getName()) != null) {
				result.rejectValue("name", null, "Category already taken");
			}
		}
		
		return new ExtDirectFormPostResult(result);
	}
	
}
