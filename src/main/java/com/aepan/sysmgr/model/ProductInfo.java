/**
 * 
 */
package com.aepan.sysmgr.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品model
 * @author rakika
 * 2015年7月14日下午12:18:14
 */
public class ProductInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2074019549725752899L;

	private int id;
	
	private String partnerProductId;
	
	private String name;
	
	private int userId;
	
	private String photoUrl1 = "";
	
	private String photoUrl2 = "";
	
	private String photoUrl3 = "";
	
	private String photoUrl4 = "";
	
	private String photoUrl5 = "";
	
	private int type = 0;
	
	private int price = 0;
	
	private int weight = 0;
	
	private int reserve = 0;
	
	private int status = 1;
	
	private String ext = "";
	
	private Date createTime;

	/**
	 * @return the partnerProductId
	 */
	public String getPartnerProductId() {
		return partnerProductId;
	}

	/**
	 * @param partnerProductId the partnerProductId to set
	 */
	public void setPartnerProductId(String partnerProductId) {
		this.partnerProductId = partnerProductId;
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
	 * @return the photoUrl1
	 */
	public String getPhotoUrl1() {
		return photoUrl1;
	}

	/**
	 * @param photoUrl1 the photoUrl1 to set
	 */
	public void setPhotoUrl1(String photoUrl1) {
		this.photoUrl1 = photoUrl1;
	}

	/**
	 * @return the photoUrl2
	 */
	public String getPhotoUrl2() {
		return photoUrl2;
	}

	/**
	 * @param photoUrl2 the photoUrl2 to set
	 */
	public void setPhotoUrl2(String photoUrl2) {
		this.photoUrl2 = photoUrl2;
	}

	/**
	 * @return the photoUrl3
	 */
	public String getPhotoUrl3() {
		return photoUrl3;
	}

	/**
	 * @param photoUrl3 the photoUrl3 to set
	 */
	public void setPhotoUrl3(String photoUrl3) {
		this.photoUrl3 = photoUrl3;
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
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * @return the reserve
	 */
	public int getReserve() {
		return reserve;
	}

	/**
	 * @param reserve the reserve to set
	 */
	public void setReserve(int reserve) {
		this.reserve = reserve;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the ext
	 */
	public String getExt() {
		return ext;
	}

	/**
	 * @param ext the ext to set
	 */
	public void setExt(String ext) {
		this.ext = ext;
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

	/**
	 * @return the photoUrl4
	 */
	public String getPhotoUrl4() {
		return photoUrl4;
	}

	/**
	 * @param photoUrl4 the photoUrl4 to set
	 */
	public void setPhotoUrl4(String photoUrl4) {
		this.photoUrl4 = photoUrl4;
	}

	/**
	 * @return the photoUrl5
	 */
	public String getPhotoUrl5() {
		return photoUrl5;
	}

	/**
	 * @param photoUrl5 the photoUrl5 to set
	 */
	public void setPhotoUrl5(String photoUrl5) {
		this.photoUrl5 = photoUrl5;
	}
}