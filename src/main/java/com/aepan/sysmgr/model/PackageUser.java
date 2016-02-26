/**
 * 
 */
package com.aepan.sysmgr.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户套餐
 * @author rakika
 * 2015年8月10日下午3:36:12
 */
public class PackageUser implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7145340221748839489L;

	private int id;
	//用户id
	private int userId;
	
	private String name;
	
	private int packageId;
	
	private int packageRuleId;
	
	private int type;
	
	private int productType;
	
	private int time;
	
	private Date createTime;

	/**
	 * @return the productType
	 */
	public int getProductType() {
		return productType;
	}

	/**
	 * @param productType the productType to set
	 */
	public void setProductType(int productType) {
		this.productType = productType;
	}

	/**
	 * @return the packageRuleId
	 */
	public int getPackageRuleId() {
		return packageRuleId;
	}

	/**
	 * @param packageRuleId the packageRuleId to set
	 */
	public void setPackageRuleId(int packageRuleId) {
		this.packageRuleId = packageRuleId;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the packageId
	 */
	public int getPackageId() {
		return packageId;
	}

	/**
	 * @param packageId the packageId to set
	 */
	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the time
	 */
	public int getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(int time) {
		this.time = time;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
