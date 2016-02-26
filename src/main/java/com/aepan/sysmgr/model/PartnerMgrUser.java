/**
 * 
 */
package com.aepan.sysmgr.model;

import java.io.Serializable;
import java.util.Date;

/**
 *  User
 * @author rakika
 * 2015年9月4日下午7:28:59
 */
public class PartnerMgrUser implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 371700100484935598L;

	private int id;

	private String sellerName;
	
	private String partnerName;
	
	private String email;
	
	private int packageId;
	
	private String packageName;
	//播放器次数
	private String playerNum;
	//视频数
	private String videoNum;
	//产品数
	private String productNum;
	
	private float price;
	
	private int duration;
	
	private Date createTime;
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	 * @return the sellerName
	 */
	public String getSellerName() {
		return sellerName;
	}

	/**
	 * @param sellerName the sellerName to set
	 */
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	/**
	 * @return the PartnerName
	 */
	public String getPartnerName() {
		return partnerName;
	}

	/**
	 * @param PartnerName the PartnerName to set
	 */
	public void setPartnerName(String PartnerName) {
		this.partnerName = PartnerName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * @return the packageName
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * @param packageName the packageName to set
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getPlayerNum() {
		return playerNum;
	}

	public void setPlayerNum(String playerNum) {
		this.playerNum = playerNum;
	}

	public String getVideoNum() {
		return videoNum;
	}

	public void setVideoNum(String videoNum) {
		this.videoNum = videoNum;
	}

	public String getProductNum() {
		return productNum;
	}

	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}

	/**
	 * @return the price
	 */
	public float getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(float price) {
		this.price = price;
	}

	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}
}
