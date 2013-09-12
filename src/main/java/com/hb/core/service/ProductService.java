package com.hb.core.service;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hb.core.entity.Category;
import com.hb.core.entity.HTML;
import com.hb.core.entity.Image;
import com.hb.core.entity.Option;
import com.hb.core.entity.OptionItem;
import com.hb.core.entity.Product;
import com.hb.core.entity.Property;
import com.hb.core.shared.dto.ProductDetailDTO;

@Transactional
@Service
public class ProductService {
	@PersistenceContext
	private EntityManager entityManager;
	
	public ProductDetailDTO getProductDetail(long productId){
		
		ProductDetailDTO productDetailDTO = null;
		
		Product product = entityManager.find(Product.class, productId);
		
		if(null != product){
			
		}
		
		return null;
	}

	public void test() {
		Image image1 = new Image();
		image1.setAltTitle("test");
		image1.setCreateDate(new Date());

		Image image2 = new Image();
		image2.setAltTitle("test");
		image2.setCreateDate(new Date());
		
		HTML html1 = entityManager.find(HTML.class, 1L);
		HTML html2 = entityManager.find(HTML.class, 2L);

		Category category1 = entityManager.find(Category.class, 1L);
		Category category2 = entityManager.find(Category.class, 2L);

		Property prop1 = new Property();
		prop1.setName("p1");
		prop1.setValue("V1");

		Property prop2 = new Property();
		prop2.setName("prop2");
		prop2.setValue("Vprop21");

		Property prop3 = new Property();
		prop3.setName("prop3");
		prop3.setValue("Vprop31");

		Property prop4 = new Property();
		prop4.setName("prop4");
		prop4.setValue("Vprop41");

		Property prop5 = new Property();
		prop5.setName("prop5");
		prop5.setValue("Vprop51");

		OptionItem optionItem = new OptionItem();
		optionItem.getOverrideProps().add(prop1);
		optionItem.getOverrideProps().add(prop2);
		optionItem.setDisplayName("ddd");

		OptionItem optionItem2 = new OptionItem();
		optionItem2.getOverrideProps().add(prop3);
		optionItem2.setDisplayName("ddd2");

		Option option = new Option();
		option.getItems().add(optionItem2);
		option.getItems().add(optionItem);

		Product product = new Product();

		product.setName("ddddd1122");
		product.getCategories().add(category2);
		product.getCategories().add(category1);
		product.getImages().add(image2);
		product.getImages().add(image1);
		product.getProps().add(prop4);
		product.getProps().add(prop5);
		product.getOptions().add(option);
		product.getManuals().put("A11", html1);
		product.getManuals().put("B22", html2);
		entityManager.merge(product);
	}
}
