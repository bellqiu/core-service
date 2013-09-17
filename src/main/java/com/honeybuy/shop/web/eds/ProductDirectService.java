package com.honeybuy.shop.web.eds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;

import com.hb.core.service.ProductService;
import com.hb.core.shared.dto.ProductDetailDTO;

@Service
@Transactional
@Secured("USER")
public class ProductDirectService {
	
	@Autowired
	private ProductService productService;
	
	@ExtDirectMethod(value=ExtDirectMethodType.FORM_LOAD)
	@Secured("ADMIN")
	public ProductDetailDTO loadProduct(@RequestParam("id") long id){
		return productService.getProductDetail(id);
	}
}
