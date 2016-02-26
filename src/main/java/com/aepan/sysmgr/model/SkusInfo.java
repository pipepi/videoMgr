/**
 * 
 */
package com.aepan.sysmgr.model;

import java.io.Serializable;

/**
 * @author rakika
 * 2015年10月4日下午11:33:01
 */
public class SkusInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4441029530841923453L;

	private String Color;
	
	private float CostPrice;
	
	private String Id;
	
	private String ProductId;
	
	private String ProductName;
	
	private float SalePrice;
	
	private String Size;
	
	private String Sku;
	
	private String Stock;
	
	private String Version;

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return ProductName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		ProductName = productName;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return Color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		Color = color;
	}

	/**
	 * @return the costPrice
	 */
	public float getCostPrice() {
		return CostPrice;
	}

	/**
	 * @param costPrice the costPrice to set
	 */
	public void setCostPrice(float costPrice) {
		CostPrice = costPrice;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return Id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		Id = id;
	}

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return ProductId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		ProductId = productId;
	}

	/**
	 * @return the salePrice
	 */
	public float getSalePrice() {
		return SalePrice;
	}

	/**
	 * @param salePrice the salePrice to set
	 */
	public void setSalePrice(float salePrice) {
		SalePrice = salePrice;
	}

	/**
	 * @return the size
	 */
	public String getSize() {
		return Size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(String size) {
		Size = size;
	}

	/**
	 * @return the sku
	 */
	public String getSku() {
		return Sku;
	}

	/**
	 * @param sku the sku to set
	 */
	public void setSku(String sku) {
		Sku = sku;
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

	/**
	 * @return the version
	 */
	public String getVersion() {
		return Version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		Version = version;
	}
}
