package com.hb.core.entity;

import java.util.Date;

public class Coupon extends Component{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7687544509171356012L;

	private String code;
	private float value;
	private long usedCount;
	private Date startDate;
	private Date endDate;
	private float minCost;
	private int maxUsedCount;
	private String name;
	private String desc;
	
	public static enum Type{
		PERCENT,
		SPECIFIC_NUM,
		FREE
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public long getUsedCount() {
		return usedCount;
	}

	public void setUsedCount(long usedCount) {
		this.usedCount = usedCount;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public int getMaxUsedCount() {
		return maxUsedCount;
	}

	public void setMaxUsedCount(int maxUsedCount) {
		this.maxUsedCount = maxUsedCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public float getMinCost() {
		return minCost;
	}

	public void setMinCost(float minCost) {
		this.minCost = minCost;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
