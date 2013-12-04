package com.hb.core.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadResult;

import com.hb.core.convert.Converter;
import com.hb.core.entity.Category;
import com.hb.core.entity.Component;
import com.hb.core.entity.HTML;
import com.hb.core.entity.Image;
import com.hb.core.entity.Option;
import com.hb.core.entity.OptionItem;
import com.hb.core.entity.Product;
import com.hb.core.entity.Property;
import com.hb.core.exception.CoreServiceException;
import com.hb.core.shared.dto.ProductChangeDTO;
import com.hb.core.shared.dto.ProductDetailDTO;
import com.hb.core.shared.dto.ProductSummaryDTO;
import com.honeybuy.shop.util.ParamValueUtils;

@Transactional
@Service
public class ProductService {
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private Converter<ProductDetailDTO, Product> productDetailConverter;
	
	@Autowired
	private Converter<ProductSummaryDTO, Product> productSummaryConverter;
	
	
	public ProductChangeDTO compupterProductChangeByOpts(String productName, String optParams){
		
		ProductDetailDTO productDetailDTO = getProductDetailByName(productName);
		
		Map<String, String> params = ParamValueUtils.parseParamString(optParams);
		
		ProductChangeDTO changeDTO = new ProductChangeDTO();
		
		List<Option> opts = productDetailDTO.getOptions();
		
		Map<String, String> overridePropsMap = new HashMap<String, String>();
		
		Map<String, Float> selectedOpts = new HashMap<String, Float>();
		
		if(null != opts){
			for (Option option : opts) {
				if(params.containsKey(String.valueOf(option.getId())) || !StringUtils.isEmpty(option.getDefaultValue())){
					
					String paramValue = params.get(String.valueOf(option.getId()));
					
					if(StringUtils.isEmpty(paramValue)){
						paramValue = option.getDefaultValue();
					}
					
					List<OptionItem> optItems = option.getItems();
					if(null != optItems){
						for (OptionItem optionItem : optItems) {
							if(!StringUtils.isEmpty(paramValue) && paramValue.equals(optionItem.getValue())){
								selectedOpts.put(option.getName()+" : "+ optionItem.getDisplayName(), optionItem.getPriceChange());
								List<Property> overrideProps = optionItem.getOverrideProps();
								if(null != overrideProps){
									for (Property property : overrideProps) {
										if("_PRODUCT_URL".equals(property.getName())){
											changeDTO.setProductUrlChange(property.getValue());
										}else{
											overridePropsMap.put(property.getName(), property.getValue());
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		changeDTO.setOptionParam(optParams);
		
		for (Float priceChange : selectedOpts.values()) {
			changeDTO.setPriceChange(changeDTO.getPriceChange()+priceChange);
		}
		
		changeDTO.setSelectedOpts(selectedOpts);
		changeDTO.setPropertiesChanges(overridePropsMap);
		
		
		return changeDTO;
	}
	
	public ProductDetailDTO getProductDetail(long productId){
		
		ProductDetailDTO productDetailDTO = null;
		
		if(productId > 0){
			Product product = em.find(Product.class, productId);
			
			productDetailDTO = productDetailConverter.convert(product);
		}
		
		return productDetailDTO;
	}
	
	public ExtDirectStoreReadResult<ProductSummaryDTO> storeQuery(int start, int max, String sort, String dir, Map<String,String> filters){
		StringBuffer ql = new StringBuffer("");
		String categoryName = null;
		if(filters.containsKey("categoryName")) {
			categoryName = filters.get("categoryName");
			filters.remove("categoryName");
		}
		if(!filters.isEmpty()){
			ql.append(" where ");
			Iterator<String> item = filters.keySet().iterator();
			while(item.hasNext()){
				String param = item.next();
				if ("price".equalsIgnoreCase(param)
						|| "actualPrice".equalsIgnoreCase(param)) {
					ql.append(param +" = :" + param + " ");
				} else if("active".equalsIgnoreCase(param)) {
					boolean active = Boolean.valueOf(filters.get(param));
					if(active) {
						ql.append("status = :status ");
					} else {
						ql.append("status != :status ");
					}
				} else{
					ql.append(param +" like :"+param +" ");
				}
				if(item.hasNext()){
					ql.append(" and ");
				}
			}
		}
		
		ql.append(" order by " + sort + " " + dir);
		
		StringBuffer queryStringPrefix = new StringBuffer("select p from Product as p ");
		StringBuffer countStringPrefix = new StringBuffer("select count(p.id) from Product as p ");
		
		TypedQuery<Product> query = em.createQuery( queryStringPrefix.append(ql).toString(), Product.class);
		TypedQuery<Long> count = em.createQuery( countStringPrefix.append(ql).toString(), Long.class);
		
		query.setFirstResult(start);
		query.setMaxResults(max);
		for (Map.Entry<String, String> paramEntry : filters.entrySet()) {
			String key = paramEntry.getKey();
			if ("price".equalsIgnoreCase(key)
					|| "actualPrice".equalsIgnoreCase(key)) {
				query.setParameter(key, Double.valueOf(paramEntry.getValue()));
				count.setParameter(key, Double.valueOf((paramEntry.getValue())));
			} else if("active".equalsIgnoreCase(key)) {
				query.setParameter("status", Component.Status.ACTIVE);
				count.setParameter("status", Component.Status.ACTIVE);
			} else {
				query.setParameter(key, "%" + paramEntry.getValue() + "%");
				count.setParameter(key, "%" + paramEntry.getValue() + "%");
			}
		}
		int totalCount = count.getSingleResult().intValue();
		List<Product> resultList = query.getResultList();
		List<ProductSummaryDTO> productDTOList = new ArrayList<ProductSummaryDTO>(resultList.size());
		for(Product product : resultList) {
			if(categoryName != null) {
				List<Category> categories = product.getCategories();
				if(categories != null && categories.size() > 0) {
					for(Category category : categories) {
						if(categoryName.equalsIgnoreCase(category.getName())) {
							productDTOList.add(productSummaryConverter.convert(product));
							break;
						}
					}
				}
			} else {
				productDTOList.add(productSummaryConverter.convert(product));
			}
		}
		return new ExtDirectStoreReadResult<ProductSummaryDTO>(totalCount, productDTOList);
	}
	
	// only allow to update existing product, which id is productSummaryDTO.id and productSummaryDTO.name is not existing for other product
	public ProductSummaryDTO update(ProductSummaryDTO productSummaryDTO){
		Product product;
		if(productSummaryDTO.getId() < 1 || (product = getProductById(productSummaryDTO.getId())) == null){
			throw new CoreServiceException("Product does not exist");
		}
		Product productByName = getProductByName(productSummaryDTO.getName());
		if(null != productByName && productByName.getId() != product.getId()){
			throw new CoreServiceException("Product already exist with name is " + productSummaryDTO.getName());
		}
		
		product = em.merge(productSummaryConverter.transf(productSummaryDTO));
		return productSummaryConverter.convert(product);
	}
	
	public ProductDetailDTO saveProductDetail(ProductDetailDTO detailDTO){
		Product existing = getProductByName(detailDTO.getName());
		if ((null != existing && detailDTO.getId() < 1 ) || (null != existing && existing.getId() != detailDTO.getId())) {
			throw new CoreServiceException("Product name already exist");
		}
		
		Product product = productDetailConverter.transf(detailDTO);
		
		product = em.merge(product);
		
		return productDetailConverter.convert(product);
	}
	
	public Option saveOption(Option option){
		Option existing = null;
		if(option.getId() > 0) {
			existing = mergePropertyInOption(option);
		} else {
			existing = option;
		}
		
		existing = em.merge(existing);
		
		return existing;
	}

	private Option mergePropertyInOption(Option option) {
		Option existingOption = em.find(Option.class, option.getId());
		if(existingOption == null) {
			existingOption = new Option();
		}
		existingOption.setName(option.getName());
		existingOption.setType(option.getType() == null ? Option.Type.TEXT : option.getType());
		existingOption.setDefaultValue(option.getDefaultValue());
		existingOption.setDesc(option.getDesc());
		List<OptionItem> items = option.getItems();
		if(items != null && items.size() > 0) {
			List<OptionItem> existingOptionItems = existingOption.getItems();
			Map<Long, OptionItem> itemMap = new HashMap<Long, OptionItem>();
			for(OptionItem optionItem : existingOptionItems) {
				itemMap.put(optionItem.getId(), optionItem);
			}
			existingOptionItems.clear();
			Set<Long> existingIdSet = itemMap.keySet();
			for(OptionItem optionItem : items) {
				if(existingIdSet.contains(optionItem.getId())) {
					OptionItem existingOptionItem = itemMap.get(optionItem.getId());
					existingOptionItem.setDisplayName(optionItem.getDisplayName());
					existingOptionItem.setIconUrl(optionItem.getIconUrl());
					existingOptionItem.setPriceChange(optionItem.getPriceChange());
					existingOptionItem.setValue(optionItem.getValue());
					existingOptionItem.setUpdateDate(new Date());
					List<Property> overrideProps = optionItem.getOverrideProps();
					if(overrideProps != null && overrideProps.size() > 0) {
						List<Property> existingOverridePropsList = existingOptionItem.getOverrideProps();
						Map<Long, Property> propertyMap = new HashMap<Long, Property>();
						for(Property property : existingOverridePropsList) {
							propertyMap.put(property.getId(), property);
						}
						existingOverridePropsList.clear();
						Set<Long> existingPropertyIdSet = propertyMap.keySet();
						for(Property property : overrideProps) {
							if(existingPropertyIdSet.contains(property.getId())) {
								Property existingProperty = propertyMap.get(property.getId());
								existingProperty.setName(property.getName());
								existingProperty.setValue(property.getValue());
								existingProperty.setDesc(property.getDesc());
								existingProperty.setUpdateDate(new Date());
								existingOverridePropsList.add(existingProperty);
							} else {
								property.setId(0);
								property.setCreateDate(new Date());
								property.setUpdateDate(new Date());
								existingOverridePropsList.add(property);
							}
						}
						existingOptionItem.setOverrideProps(existingOverridePropsList);
					} else {
						existingOptionItem.getOverrideProps().clear();
					}
					existingOptionItems.add(existingOptionItem);
				} else {
					optionItem.setId(0);
					optionItem.setCreateDate(new Date());
					optionItem.setUpdateDate(new Date());
					existingOptionItems.add(optionItem);
				}
			}
			existingOption.setItems(existingOptionItems);
		} else {
			existingOption.getItems().clear();
		}
		
		return existingOption;
	}
	
	public boolean exist(String productName){
		return null == getProductByName(productName)? false: true;
	}
	
	public ProductDetailDTO getProductDetailByName(String name){
		Product product = getProductByName(name);
		if(null != product){
			return productDetailConverter.convert(product);
		}
		
		return null;
	}

	private Product getProductByName(String name) {
		Product product = null;
		
		try {
			TypedQuery<Product> query = em.createNamedQuery("QueryProductByName",Product.class);
			query.setParameter("name", name);
			
			product = query.getSingleResult();
		} catch(NoResultException e){
			return null;
		} catch (Exception e) {
			throw new CoreServiceException(e);
		}
		
		return product;
	}
	
	private Product getProductById(long id) {
		return em.find(Product.class, id);
	}

	public void test() {
		Image image1 = new Image();
		image1.setAltTitle("test");
		image1.setCreateDate(new Date());

		Image image2 = new Image();
		image2.setAltTitle("test");
		image2.setCreateDate(new Date());
		
		HTML html1 = em.find(HTML.class, 1L);
		HTML html2 = em.find(HTML.class, 2L);

		Category category1 = em.find(Category.class, 1L);
		Category category2 = em.find(Category.class, 2L);

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
		em.merge(product);
	}
	
	
	public ProductSummaryDTO setProductAsDelete(ProductSummaryDTO productSummaryDTO){
		Product product;
		if(productSummaryDTO.getId() < 1 || (product = getProductById(productSummaryDTO.getId())) == null){
			throw new CoreServiceException("Product does not exist");
		}
		Product productByName = getProductByName(productSummaryDTO.getName());
		if(null != productByName && productByName.getId() != product.getId()){
			throw new CoreServiceException("Product already exist with name is " + productSummaryDTO.getName());
		}
		Product transf = productSummaryConverter.transf(productSummaryDTO);
		transf.setStatus(Component.Status.DELETED);
		product = em.merge(transf);
		return productSummaryConverter.convert(product);
	}
	
	public List<ProductSummaryDTO> getAllProductByCategoryId(long categoryId, int start, int max) {
		String queryString = "select p from Product p, Category c where c.id = :id and p.status = 'ACTIVE' and c member of p.categories";
		TypedQuery<Product> query = em.createQuery(queryString, Product.class);
		query.setParameter("id", categoryId);
		query.setFirstResult(start);
		query.setMaxResults(max);
		List<Product> resultList = query.getResultList();
		List<ProductSummaryDTO> productSummaryList = new ArrayList<ProductSummaryDTO>(resultList.size());
		for(Product p : resultList) {
			productSummaryList.add(productSummaryConverter.convert(p));
		}
		return productSummaryList;
	}

	public int getProductCountByCategoryId(long categoryId) {
		String queryString = "select count(p.id) from Product p, Category c where c.id = :id and p.status = 'ACTIVE' and c member of p.categories";
		TypedQuery<Long> count = em.createQuery(queryString, Long.class);
		count.setParameter("id", categoryId);
		return count.getSingleResult().intValue();
	}
	
	public double getLowestPriceByCategoryId(long categoryId) {
		String queryString = "select min(p.actualPrice) from Product p, Category c where c.id = :id and c member of p.categories";
		TypedQuery<Double> query = em.createQuery(queryString, Double.class);
		query.setParameter("id", categoryId);
		Double result  = query.getSingleResult();
		return result == null ? 0.0 : result;
	}
	
	public double getHighestPriceByCategoryId(long categoryId) {
		String queryString = "select max(p.actualPrice) from Product p, Category c where c.id = :id and c member of p.categories";
		TypedQuery<Double> query = em.createQuery(queryString, Double.class);
		query.setParameter("id", categoryId);
		Double result  = query.getSingleResult();
		return result == null ? 0.0 : result;
	}

	public List<String> getProductName(long categoryId, String columnName, String value) {
		String queryString = null;
		if("keywords".equalsIgnoreCase(columnName)) {
			queryString = "select p.name from Product p, Category c where c.id = :id and lower(p.keywords) like :key and c member of p.categories";
		} else if("tags".equalsIgnoreCase(columnName)) {
			queryString = "select p.name from Product p, Category c where c.id = :id and lower(p.tags) like :key and c member of p.categories";
		}
		if(queryString == null) {
			return null;
		}
		TypedQuery<String> query = em.createQuery(queryString, String.class);
		query.setParameter("id", categoryId);
		query.setParameter("key", "%" + value.toLowerCase() + "%" );
		return query.getResultList();
	}
	
	public List<String> searchProductByCriteria(long categoryId, double minPrice, double maxPrice, String tag, String keyword) {
		StringBuffer query = new StringBuffer();
		query.append("select p.name from Product p, Category c where c.id = :id and c member of p.categories and p.actualPrice between :minPrice and :maxPrice ");
		if(!StringUtils.isEmpty(tag)) {
			query.append("and p.tags like :tag ");
		} 
		if(!StringUtils.isEmpty(keyword)) {
			query.append("and p.tags like :tag ");
		} 
		if("keywords".equalsIgnoreCase(tag)) {
			//queryString = "select p.name from Product p, Category c where c.id = :id and lower(p.keywords) like :key and c member of p.categories";
		} else if("tags".equalsIgnoreCase(keyword)) {
		}
		TypedQuery<String> result = em.createQuery(query.toString(), String.class);
		result.setParameter("id", "");
		result.setParameter("key", "%" + keyword.toLowerCase() + "%" );
		return result.getResultList();
	}
	
	public int searchCountByKey(String key) {
		int count = 0;
		try {
			long id = Long.valueOf(key);
			Product productById = getProductById(id);
			if(productById != null) {
				count ++;
			}
		} catch(NumberFormatException e) {
		}
		String query = "select count(p.id) from Product p where lower(p.keywords) like :keyword or lower(p.tags) like :tags ";
		TypedQuery<Long> result = em.createQuery(query, Long.class);
		result.setParameter("keyword", "%" + key.toLowerCase() + "%");
		result.setParameter("tags", "%" + key.toLowerCase() + "%");
		count += result.getSingleResult().intValue();
		return count;
	}

	public List<ProductSummaryDTO> searchProductByKey(String key) {
		List<ProductSummaryDTO> list = new ArrayList<ProductSummaryDTO>();
		try {
			long id = Long.valueOf(key);
			Product productById = getProductById(id);
			if(productById != null) {
				list.add(productSummaryConverter.convert(productById));
			}
		} catch(NumberFormatException e) {
		}
		String query = "select p from Product p where lower(p.keywords) like :keyword or lower(p.tags) like :tags ";
		TypedQuery<Product> result = em.createQuery(query, Product.class);
		result.setParameter("keyword", "%" + key.toLowerCase() + "%");
		result.setParameter("tags", "%" + key.toLowerCase() + "%");
		List<Product> resultList = result.getResultList();
		for(Product p : resultList) {
			list.add(productSummaryConverter.convert(p));
		}
		return list;
	}
}
