/**
 * 
 */
package com.aepan.sysmgr.model;

import java.io.Serializable;

/**
 * @author rakika
 * 2015年9月13日下午5:06:34
 */
public class PartnerProduct implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7338240529797480064L;

	private int Id;
	
	private String AuditState;
	
	private int BrandId;
	
	private String BrandName;
	
	private int CategoryId;
	
	private String CategoryName;
	
	private String Image;
	
	private boolean IsLimitTimeBuy;
	
	private String Name;
	
	private float Price;
	
	private String ProductCode;
	
	private String PublicTime;
	
	private int SaleCount;
	
	private int SaleState;

	private int Stock;
	
	private String Uid;
	
	private String Unit;
	
	private String Url;
	
	private PartnerProductSt sku;

	/**
	 * @return the id
	 */
	public int getId() {
		return Id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		Id = id;
	}

	/**
	 * @return the auditState
	 */
	public String getAuditState() {
		return AuditState;
	}

	/**
	 * @param auditState the auditState to set
	 */
	public void setAuditState(String auditState) {
		AuditState = auditState;
	}

	/**
	 * @return the brandId
	 */
	public int getBrandId() {
		return BrandId;
	}

	/**
	 * @param brandId the brandId to set
	 */
	public void setBrandId(int brandId) {
		BrandId = brandId;
	}

	/**
	 * @return the brandName
	 */
	public String getBrandName() {
		return BrandName;
	}

	/**
	 * @param brandName the brandName to set
	 */
	public void setBrandName(String brandName) {
		BrandName = brandName;
	}

	/**
	 * @return the categoryId
	 */
	public int getCategoryId() {
		return CategoryId;
	}

	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(int categoryId) {
		CategoryId = categoryId;
	}

	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return CategoryName;
	}

	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		CategoryName = categoryName;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return Image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		Image = image;
	}

	/**
	 * @return the isLimitTimeBuy
	 */
	public boolean isIsLimitTimeBuy() {
		return IsLimitTimeBuy;
	}

	/**
	 * @param isLimitTimeBuy the isLimitTimeBuy to set
	 */
	public void setIsLimitTimeBuy(boolean isLimitTimeBuy) {
		IsLimitTimeBuy = isLimitTimeBuy;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return Name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		Name = name;
	}

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
	 * @return the productCode
	 */
	public String getProductCode() {
		return ProductCode;
	}

	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(String productCode) {
		ProductCode = productCode;
	}

	/**
	 * @return the publicTime
	 */
	public String getPublicTime() {
		return PublicTime;
	}

	/**
	 * @param publicTime the publicTime to set
	 */
	public void setPublicTime(String publicTime) {
		PublicTime = publicTime;
	}

	/**
	 * @return the saleCount
	 */
	public int getSaleCount() {
		return SaleCount;
	}

	/**
	 * @param saleCount the saleCount to set
	 */
	public void setSaleCount(int saleCount) {
		SaleCount = saleCount;
	}

	/**
	 * @return the saleState
	 */
	public int getSaleState() {
		return SaleState;
	}

	/**
	 * @param saleState the saleState to set
	 */
	public void setSaleState(int saleState) {
		SaleState = saleState;
	}

	/**
	 * @return the stock
	 */
	public int getStock() {
		return Stock;
	}

	/**
	 * @param stock the stock to set
	 */
	public void setStock(int stock) {
		Stock = stock;
	}

	/**
	 * @return the uid
	 */
	public String getUid() {
		return Uid;
	}

	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		Uid = uid;
	}

	/**
	 * @return the unit
	 */
	public String getUnit() {
		return Unit;
	}

	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		Unit = unit;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return Url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		Url = url;
	}

	/**
	 * @return the sku
	 */
	public PartnerProductSt getSku() {
		return sku;
	}

	/**
	 * @param sku the sku to set
	 */
	public void setSku(PartnerProductSt sku) {
		this.sku = sku;
	}
}
