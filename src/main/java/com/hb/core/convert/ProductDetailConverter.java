package com.hb.core.convert;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hb.core.entity.Product;
import com.hb.core.shared.dto.ProductDetailDTO;

@Service
@Transactional(readOnly=true)
public class ProductDetailConverter implements Converter<ProductDetailDTO, Product>{
	@Override
	public ProductDetailDTO convert(Product e) {
		return null;
	}

	@Override
	public Product transf(ProductDetailDTO d) {
		return null;
	}
	
}
