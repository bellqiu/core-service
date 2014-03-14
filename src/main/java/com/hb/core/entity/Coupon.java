package com.hb.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@JsonAutoDetect
@NamedQueries(value=
	{
		@NamedQuery(name="QueryCouponByCode", query="select c from Coupon as c where code=:code"),
		@NamedQuery(name="countAllCoupon", query="select count(c.id) from Coupon as c")
	})
public class Coupon extends Component{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7687544509171356012L;

	@Column(name="code")
	private String code;
	@Column(name="value")
	private float value;
	@Column(name="used_count")
	private long usedCount;
	@Column(name="start_date")
	private Date startDate;
	@Column(name="end_date")
	private Date endDate;
	@Column(name="min_cost")
	private float minCost;
	@Column(name="max_used_count")
	private int maxUsedCount;
	@Column(name="name")
	private String name;
	@Column(name="description")
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
