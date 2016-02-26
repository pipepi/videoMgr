/**
 * 
 */
package com.aepan.sysmgr.model;

import java.io.Serializable;

/**
 * @author rakika
 * 2015年9月13日下午8:59:53
 */
public class PartnerProductTableData implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -2149021248083285314L;
	
	private PartnerIndexValue cost;
	
    private PartnerIndexValue mallPrice;
    
    private String productId;
    
    private PartnerIndexValue sku;
    
    private PartnerIndexValue stock;

	/**
	 * @return the cost
	 */
	public PartnerIndexValue getCost() {
		return cost;
	}

	/**
	 * @param cost the cost to set
	 */
	public void setCost(PartnerIndexValue cost) {
		this.cost = cost;
	}

	/**
	 * @return the mallPrice
	 */
	public PartnerIndexValue getMallPrice() {
		return mallPrice;
	}

	/**
	 * @param mallPrice the mallPrice to set
	 */
	public void setMallPrice(PartnerIndexValue mallPrice) {
		this.mallPrice = mallPrice;
	}

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the sku
	 */
	public PartnerIndexValue getSku() {
		return sku;
	}

	/**
	 * @param sku the sku to set
	 */
	public void setSku(PartnerIndexValue sku) {
		this.sku = sku;
	}

	/**
	 * @return the stock
	 */
	public PartnerIndexValue getStock() {
		return stock;
	}

	/**
	 * @param stock the stock to set
	 */
	public void setStock(PartnerIndexValue stock) {
		this.stock = stock;
	}
}
