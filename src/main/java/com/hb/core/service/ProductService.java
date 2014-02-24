package com.hb.core.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

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
import com.hb.core.util.Constants;
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
			if(StringUtils.isEmpty(categoryName)) {
				categoryName = null;
			}
			filters.remove("categoryName");
		}
		if(!filters.isEmpty()){
			if(filters.containsKey("id")) {
				ql.append(" where id = :id ");
				categoryName = null;
			} else {
				if(categoryName == null) {
					ql.append(" where ");
				} else {
					ql.append(" and ");
				}
				Iterator<String> item = filters.keySet().iterator();
				while(item.hasNext()){
					String param = item.next();
					if ("price".equalsIgnoreCase(param)
							|| "actualPrice".equalsIgnoreCase(param)) {
						ql.append(" p." +param +" = :" + param);
					} else if("active".equalsIgnoreCase(param)) {
						boolean active = Boolean.valueOf(filters.get(param));
						if(active) {
							ql.append(" p.status = :status ");
						} else {
							ql.append(" p.status != :status ");
						}
					} else{
						ql.append(" p." + param +" like :"+param);
					}
					if(item.hasNext()){
						ql.append(" and ");
					}
				}
			}
		}
		
		ql.append(" order by p." + sort + " " + dir);
		
		StringBuffer queryStringPrefix;
		StringBuffer countStringPrefix;
		if(categoryName != null) {
			queryStringPrefix = new StringBuffer("select p from Product p, Category c where c member of p.categories and c.name like :categoryName ");
			countStringPrefix = new StringBuffer("select count(p.id) from Product p, Category c where c member of p.categories and c.name like :categoryName ");
		} else {
			queryStringPrefix = new StringBuffer("select p from Product as p ");
			countStringPrefix = new StringBuffer("select count(p.id) from Product as p ");
		}
		
		TypedQuery<Product> query = em.createQuery( queryStringPrefix.append(ql).toString(), Product.class);
		TypedQuery<Long> count = em.createQuery( countStringPrefix.append(ql).toString(), Long.class);
		
		query.setFirstResult(start);
		query.setMaxResults(max);
		if(filters.containsKey("id")) {
			String idString = filters.get("id");
			long idNumber = 0;
			try {
				idNumber= Long.valueOf(idString);
			} catch(NumberFormatException e) {
			}
			query.setParameter("id", idNumber);
			count.setParameter("id", idNumber);
		} else {
			if(categoryName != null) { 
				query.setParameter("categoryName", categoryName);
				count.setParameter("categoryName", categoryName);
			}
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
		}
		int totalCount = count.getSingleResult().intValue();
		List<Product> resultList = query.getResultList();
		List<ProductSummaryDTO> productDTOList = new ArrayList<ProductSummaryDTO>(resultList.size());
		for(Product product : resultList) {
			productDTOList.add(productSummaryConverter.convert(product));
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
	
	public Option copyAndSaveOption(Option option){
		if(option == null) {
			return null;
		}
		Option copy = new Option();
		copy.setName(option.getName());
		copy.setType(option.getType() == null ? Option.Type.TEXT : option.getType());
		copy.setDefaultValue(option.getDefaultValue());
		copy.setDesc(option.getDesc());
		copy.setCustomize(option.isCustomize());
		copy.setHtmlKey(option.getHtmlKey());
		Date date = new Date();
		copy.setCreateDate(date);
		copy.setUpdateDate(date);
		
		List<OptionItem> items = option.getItems();
		List<OptionItem> copyOptionItems = new ArrayList<OptionItem>();
		if(items != null && items.size() > 0) {
			for(OptionItem optionItem : items) {
				OptionItem copyOptionItem = new OptionItem();
				copyOptionItem.setDisplayName(optionItem.getDisplayName());
				copyOptionItem.setValue(optionItem.getValue());
				copyOptionItem.setPriceChange(optionItem.getPriceChange());
				copyOptionItem.setIconUrl(optionItem.getIconUrl());
				copyOptionItem.setCreateDate(date);
				copyOptionItem.setUpdateDate(date);
				
				List<Property> overrideProps = optionItem.getOverrideProps();
				if(overrideProps != null && overrideProps.size() > 0) {
					for(Property property : overrideProps) {
						Property copyProperty = new Property();
						copyProperty.setName(property.getName());
						copyProperty.setValue(property.getValue());
						copyProperty.setDesc(property.getDesc());
						copyProperty.setCreateDate(date);
						copyProperty.setUpdateDate(date);
						copyProperty = em.merge(copyProperty);
						copyOptionItem.getOverrideProps().add(copyProperty);
					}
				}
				copyOptionItem = em.merge(copyOptionItem);
				copyOptionItems.add(copyOptionItem);
			}
		} 
		copy.setItems(copyOptionItems);
		
		copy = em.merge(copy);
		
		return copy;
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
		existingOption.setCustomize(option.isCustomize());
		existingOption.setHtmlKey(option.getHtmlKey());
		List<OptionItem> items = option.getItems();
		if(items != null && items.size() > 0) {
			List<OptionItem> existingOptionItems = existingOption.getItems();
			existingOptionItems.clear();
			for(OptionItem optionItem : items) {
				optionItem.setId(0);
				List<Property> overrideProps = optionItem.getOverrideProps();
				if(overrideProps != null && overrideProps.size() > 0) {
					for(Property property : overrideProps) {
						property.setId(0);
					}
				}
				existingOptionItems.add(optionItem);
			}
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

	protected Product getProductByName(String name) {
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
	
	public ProductSummaryDTO setProductAsActive(ProductSummaryDTO productSummaryDTO){
		Product product;
		if(productSummaryDTO.getId() < 1 || (product = getProductById(productSummaryDTO.getId())) == null){
			throw new CoreServiceException("Product does not exist");
		}
		Product productByName = getProductByName(productSummaryDTO.getName());
		if(null != productByName && productByName.getId() != product.getId()){
			throw new CoreServiceException("Product already exist with name is " + productSummaryDTO.getName());
		}
		Product transf = productSummaryConverter.transf(productSummaryDTO);
		transf.setStatus(Component.Status.ACTIVE);
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
	
	public List<ProductSummaryDTO> getAllProductByCategoryIds(List<Long> categoryIds, int start, int max) {
		String queryString = "select p from Product p, Category c where c.id in :id and p.status = 'ACTIVE' and c member of p.categories order by p.updateDate desc";
		TypedQuery<Product> query = em.createQuery(queryString, Product.class);
		List<Long> list = categoryIds;
		System.out.println(list);
		query.setParameter("id", list);
		query.setFirstResult(start);
		query.setMaxResults(max);
		List<Product> resultList = query.getResultList();
		List<ProductSummaryDTO> productSummaryList = new ArrayList<ProductSummaryDTO>(resultList.size());
		for(Product p : resultList) {
			productSummaryList.add(productSummaryConverter.convert(p));
		}
		return productSummaryList;
	}

	public int getProductCountByCategoryIds(List<Long> categoryIds) {
		//String queryString = "select count(p.id) from Product p, Category c where c.id IN (:idList) and p.status = 'ACTIVE' and c member of p.categories";
		String queryString = "select count(p.id) from Product p, Category c where c.id in :id and p.status = 'ACTIVE' and c member of p.categories";
						      //select count(p.id) from Product p, Category c where c.id in :id and p.status = 'ACTIVE' and c member of p.categories
		TypedQuery<Long> count = em.createQuery(queryString, Long.class);
		//List<Long> list = Arrays.asList(49L,108L);
		List<Long> list = categoryIds;
		System.out.println(list);
		count.setParameter("id", list);
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
	
	public int searchCountByKey(String [] keys) {
		
		if(keys == null || keys.length == 0) {
			return 0;
		}
		StringBuffer query = new StringBuffer();
		query.append("select count(p.id) from Product p where p.status = 'ACTIVE' ");
		int i = 0;
		int length = Math.min(keys.length, 5);
		for(i=0; i < length; i++) {
			if(i == 0) {
				query.append(" and ( ");
			} else {
				query.append(" or ");
			}
			query.append(" p.keywords like :key").append(i).append(" or p.tags like :tag").append(i);
		}
		query.append(" )");
		
		TypedQuery<Long> result = em.createQuery(query.toString(), Long.class);
		i = 0;
		for(i=0; i < length; i++) {
			String key = keys[i];
			result.setParameter("key" + i, "%" + key + "%");
			result.setParameter("tag" + i, "%" + key + "%");
			i++;
		}
		return result.getSingleResult().intValue();
	}

	public List<ProductSummaryDTO> searchProductByKey(String [] keys, int start, int max) {
		List<ProductSummaryDTO> list = new ArrayList<ProductSummaryDTO>();
		if(keys == null || keys.length == 0) {
			return list;
		}
		StringBuffer query = new StringBuffer();
		query.append("select p from Product p where p.status = 'ACTIVE' ");
		int i = 0;
		int length = Math.min(keys.length, 5);
		for(i=0; i < length; i++) {
			if(i == 0) {
				query.append(" and ( ");
			} else {
				query.append(" or ");
			}
			query.append(" p.keywords like :key").append(i).append(" or p.tags like :tag").append(i);
		}
		query.append(" )");
		
		TypedQuery<Product> result = em.createQuery(query.toString(), Product.class);
		i = 0;
		for(i=0; i < length; i++) {
			String key = keys[i];
			result.setParameter("key" + i, "%" + key + "%");
			result.setParameter("tag" + i, "%" + key + "%");
			i++;
		}
		result.setFirstResult(start);
		result.setMaxResults(max);
		List<Product> resultList = result.getResultList();
		for(Product p : resultList) {
			list.add(productSummaryConverter.convert(p));
		}
		return list;
	}

	public TreeMap<String, Set<Long>> getAllTagsMap() {
		String query = "select p from Product p where p.status = 'ACTIVE' ";
		
		TypedQuery<Product> result = em.createQuery(query, Product.class);
		List<Product> resultList = result.getResultList();
		TreeMap<String, Set<Long>> tagProductMap = new TreeMap<String, Set<Long>>(); 
		for(Product p : resultList) {
			String tags = p.getTags();
			if(!StringUtils.isEmpty(tags)) {
				String[] tagsArray = tags.split(Constants.TAGS_SPLIT);
				for(String tag : tagsArray) {
					String tagName = tag.trim();
					Set<Long> productIdList = tagProductMap.get(tagName);
					if(productIdList != null) {
						productIdList.add(p.getId());
					} else {
						productIdList = new TreeSet<Long>();
						productIdList.add(p.getId());
						tagProductMap.put(tagName, productIdList);
					}
				}
			}
		}
		return tagProductMap;
	}
}
