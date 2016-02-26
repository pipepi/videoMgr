/**
 * 
 */
package com.aepan.sysmgr.model.hm;

import java.io.Serializable;

/**
 * @author Doris.zhou
 * 2015年9月29日下午12:35:48
 */
public class PartnerStoreProduct implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 产品id
	 */
	private int id;
	/**
	 * 目录id
	 */
	private int categoryId;
	/**
	 * 产品名
	 */
	private String name;
	/**
	 * 图片地址
	 */
	private String image;
	/**
	 * 价格
	 */
	private float price;
	/**
	 * 库存，目前数据不对
	 */
	private int stock;
	/**
	 * 发布时间
	 */
	private String publishTime;
	/**
	 * 计量单位
	 */
	private String unit;
	
	/**
	 * 详细产品信息
	 */
	private String detailJson;
	
	
	
	/**
	 * @param id
	 * @param name
	 * @param image
	 * @param price
	 * @param stock
	 * @param publishTime
	 * @param unit
	 */
	public PartnerStoreProduct(int id,int categoryId, String name, String image, float price,
			int stock, String publishTime, String unit) {
		super();
		this.id = id;
		this.categoryId=categoryId;
		this.name = name;
		this.image = image;
		this.price = price;
		this.stock = stock;
		this.publishTime = publishTime;
		this.unit = unit;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	/**
	 * @return the categoryId
	 */
	public int getCategoryId() {
		return categoryId;
	}



	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getImage() {
		return image;
	}



	public void setImage(String image) {
		this.image = image;
	}



	public float getPrice() {
		return price;
	}



	public void setPrice(float price) {
		this.price = price;
	}



	public int getStock() {
		return stock;
	}



	public void setStock(int stock) {
		this.stock = stock;
	}



	public String getPublishTime() {
		return publishTime;
	}



	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}



	public String getUnit() {
		return unit;
	}



	public void setUnit(String unit) {
		this.unit = unit;
	}



	/**
	 * @return the detailJson
	 */
	public String getDetailJson() {
		return detailJson;
	}



	/**
	 * @param detailJson the detailJson to set
	 */
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}



	@Override
	public String toString() {
		return "StoreProduct [id=" + id + ", name=" + name + ", image=" + image
				+ ", price=" + price + ", stock=" + stock + ", publishTime="
				+ publishTime + ", unit=" + unit + "]";
	}
	

}
