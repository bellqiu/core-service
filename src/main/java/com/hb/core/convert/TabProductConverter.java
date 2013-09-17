package com.hb.core.convert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.hb.core.entity.Product;
import com.hb.core.entity.TabProduct;
import com.hb.core.shared.dto.ProductSummaryDTO;
import com.hb.core.shared.dto.TabProductDTO;

@org.springframework.stereotype.Component
public class TabProductConverter implements Converter<TabProductDTO, TabProduct>{

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private Converter<ProductSummaryDTO, Product> productSummaryConverter;
	
	@Override
	public TabProductDTO convert(TabProduct tp) {
		
		if(null == tp){
			return null;
		}
		
		
		TabProductDTO dto = null;
		
		if(null != tp){
			dto = new TabProductDTO();
			dto.setId(tp.getId());
			dto.setName(tp.getName());
			if(null != tp.getProducts()){
				for (Product p : tp.getProducts()) {
					dto.getProducts().add(productSummaryConverter.convert(p));
				}
			}
		}
		
		return dto;
	}

	@Override
	public TabProduct transf(TabProductDTO d) {
		
		if(null == d){
			return null;
		}
		
		
		TabProduct product = new TabProduct();
		
		if(d.getId() > 0){
			product = em.find(TabProduct.class, d.getId());
		}else{
			if(d.getProducts() != null){
				for (ProductSummaryDTO p : d.getProducts()) {
					product.getProducts().add(productSummaryConverter.transf(p));
				}
			}
		}
		
		product.setName(d.getName());
		
		return product;
	}

}
