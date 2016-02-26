/**
 * 
 */
package com.aepan.sysmgr.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author rakika
 * 2015年8月14日下午3:16:21
 */
public class StoreProducts implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2731084275321206218L;

	private int StoreId;
	
	private List<ProductInfo> productInfoList;

	/**
	 * @return the storeId
	 */
	public int getStoreId() {
		return StoreId;
	}

	/**
	 * @param storeId the storeId to set
	 */
	public void setStoreId(int storeId) {
		StoreId = storeId;
	}

	/**
	 * @return the productInfoList
	 */
	public List<ProductInfo> getProductInfoList() {
		return productInfoList;
	}

	/**
	 * @param productInfoList the productInfoList to set
	 */
	public void setProductInfoList(List<ProductInfo> productInfoList) {
		this.productInfoList = productInfoList;
	} 
}
