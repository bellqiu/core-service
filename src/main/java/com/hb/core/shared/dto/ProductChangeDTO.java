package com.hb.core.shared.dto;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.honeybuy.shop.util.ParamValueUtils;

public class ProductChangeDTO {
	
	private float priceChange;
	private String productUrlChange;
	private Map<String, String> propertiesChanges = new HashMap<String, String>();
	private List<String> selectedOpts = new ArrayList<String>();
	private String optionParam="";

	public float getPriceChange() {
		return priceChange;
	}
	public void setPriceChange(float priceChange) {
		this.priceChange = priceChange;
	}
	
	public String getProductUrlChange() {
		return productUrlChange;
	}
	public void setProductUrlChange(String productUrlChange) {
		this.productUrlChange = productUrlChange;
	}
	public Map<String, String> getPropertiesChanges() {
		return propertiesChanges;
	}
	public void setPropertiesChanges(Map<String, String> propertiesChanges) {
		this.propertiesChanges = propertiesChanges;
	}
	public List<String> getSelectedOpts() {
		return selectedOpts;
	}
	public void setSelectedOpts(List<String> selectedOpts) {
		this.selectedOpts = selectedOpts;
	}
	public String getOptionParam() {
		return optionParam;
	}
	public void setOptionParam(String optionParam) {
		this.optionParam = optionParam;
	}
	public String getStrPriceChange() {
		NumberFormat numberFormat = new DecimalFormat("###,###,###,###,##0.00");
		
		return numberFormat.format(priceChange);
	}
	public Map<String,String> getSelectedOptOriginal() {
		Map<String,String> paramMap = ParamValueUtils.parseParamString(optionParam);
		Map<Integer,String> opts= new HashMap<Integer,String>();
		
		for (String key : paramMap.keySet()) {
			int id = 0 ; 
			try {
				id = Integer.valueOf(key);
			} catch (NumberFormatException e) {
			}
			if(id > 0){
				opts.put(id, paramMap.get(key));
			}
		}
		
		return paramMap;
	}
	
}
