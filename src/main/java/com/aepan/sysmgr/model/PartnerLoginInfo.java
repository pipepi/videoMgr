/**
 * 
 */
package com.aepan.sysmgr.model;

import java.io.Serializable;

/**
 * @author Doris 2015年9月12日下午6:20:58
 */
public class PartnerLoginInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	private int userId;
	/**
	 * 套餐id
	 */
	private int packageId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getPackageId() {
		return packageId;
	}

	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}

}
