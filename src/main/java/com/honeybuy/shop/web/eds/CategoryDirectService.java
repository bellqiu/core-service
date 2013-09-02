package com.honeybuy.shop.web.eds;

import java.util.Date;
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

import com.hb.core.entity.Setting;
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
	public CategoryDetailDTO loadDetail(@RequestParam(value = "id") long id ,@RequestParam(value = "parent", required = false) long parent){
		CategoryDetailDTO categoryDetailDTO = new CategoryDetailDTO();
		
		if(id > 0){
			categoryDetailDTO = categoryService.getCategoryDetailDTOById(id);
		}else{
			categoryDetailDTO.setPageTitle("PageTitle");
			categoryDetailDTO.setName("Name");
			categoryDetailDTO.setParentId(parent);
		}
	
		return categoryDetailDTO;
	}
	
	@ExtDirectMethod(value=ExtDirectMethodType.FORM_POST)
	public ExtDirectFormPostResult saveDetail(@Valid CategoryDetailDTO categoryDetailDTO, BindingResult result){
		if (!result.hasErrors()) {
			if (categoryDetailDTO.getId()< 1 && categoryService.getCategoryByName(categoryDetailDTO.getName()) != null) {
				result.rejectValue("name", null, "Category already taken");
			}
		}
		if(!result.hasErrors()){
			categoryDetailDTO = categoryService.saveCategoryDetail(categoryDetailDTO);
		}
		
		ExtDirectFormPostResult directFormPostResult = new ExtDirectFormPostResult(result);
		directFormPostResult.addResultProperty("resultForm", categoryDetailDTO);
		
		return directFormPostResult;
	}
	
	@ExtDirectMethod(value=ExtDirectMethodType.STORE_MODIFY)
	public CategoryTreeDTO update(CategoryTreeDTO categoryTreeDTO) {
		
		
		return null;
	}
	
	@ExtDirectMethod(value=ExtDirectMethodType.STORE_MODIFY)
	public void destory(CategoryTreeDTO categoryTreeDTO) {
	}
	
}
