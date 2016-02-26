/**
 * 
 */
package com.aepan.sysmgr.model;

import java.io.Serializable;

/**
 * @author rakika
 * 2015年9月13日下午8:59:53
 */
public class PartnerProductSKU implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8306057060137101029L;

	private float Price;
	
	private String SKUId;
	
	private String Stock;

	/**
	 * @return the price
	 */
	public float getPrice() {
		return Price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(float price) {
		Price = price;
	}

	/**
	 * @return the sKUId
	 */
	public String getSKUId() {
		return SKUId;
	}

	/**
	 * @param sKUId the sKUId to set
	 */
	public void setSKUId(String sKUId) {
		SKUId = sKUId;
	}

	/**
	 * @return the stock
	 */
	public String getStock() {
		return Stock;
	}

	/**
	 * @param stock the stock to set
	 */
	public void setStock(String stock) {
		Stock = stock;
	}
}
