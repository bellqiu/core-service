package com.hb.core.shared.dto;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.honeybuy.shop.util.ParamValueUtils;
@JsonAutoDetect
public class ProductChangeDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7229801382939214276L;
	private float priceChange;
	private float finalPriceSale;
	private float finalPriceOrigin;
	private String productUrlChange;
	private Map<String, String> propertiesChanges = new HashMap<String, String>();
	private Map<String, Float> selectedOpts = new HashMap<String, Float>();
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
	public Map<String, Float> getSelectedOpts() {
		return selectedOpts;
	}
	public void setSelectedOpts(Map<String, Float>selectedOpts) {
		this.selectedOpts = selectedOpts;
	}
	public String getOptionParam() {
		return optionParam;
	}
	public void setOptionParam(String optionParam) {
		this.optionParam = optionParam;
	}

	public float getFinalPriceOrigin() {
		return finalPriceOrigin;
	}

	public void setFinalPriceOrigin(float finalPriceOrigin) {
		this.finalPriceOrigin = finalPriceOrigin;
	}

	public float getFinalPriceSale() {
		return finalPriceSale;
	}

	public void setFinalPriceSale(float finalPriceSale) {
		this.finalPriceSale = finalPriceSale;
	}

	public String getStrPriceChange() {
		NumberFormat numberFormat = new DecimalFormat("###,###,###,###,##0.00");
		
		return numberFormat.format(priceChange);
	}

	public String getStrPriceOriginFinal() {
		NumberFormat numberFormat = new DecimalFormat("###,###,###,###,##0.00");

		return numberFormat.format(finalPriceOrigin);
	}

	public String getStrPriceSaleFinal() {
		NumberFormat numberFormat = new DecimalFormat("###,###,###,###,##0.00");

		return numberFormat.format(finalPriceSale);
	}
	public Map<String,String> getSelectedOptOriginal() {
		Map<String,String> paramMap = ParamValueUtils.parseParamString(optionParam);
		return paramMap;
	}

}
