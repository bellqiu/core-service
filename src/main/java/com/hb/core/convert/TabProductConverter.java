package com.hb.core.convert;

import java.util.ArrayList;

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
	public TabProduct transf(TabProductDTO tabProductDTO) {
		
		if(null == tabProductDTO){
			return null;
		}
		
		
		TabProduct tabProduct = new TabProduct();
		
		if(tabProductDTO.getId() > 0){
			tabProduct = em.find(TabProduct.class, tabProductDTO.getId());
		}
		
		tabProduct.setProducts(new ArrayList<Product>());

		if (tabProductDTO.getProducts() != null) {
			for (ProductSummaryDTO p : tabProductDTO.getProducts()) {
				tabProduct.getProducts().add(productSummaryConverter.transf(p));
			}
		}
		
		tabProduct.setName(tabProductDTO.getName());
		
		return tabProduct;
	}

}
