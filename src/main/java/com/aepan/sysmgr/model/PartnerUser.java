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
public class PartnerUser implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8553087119712953260L;
	
	private int id;

	private String sellerName;
	
	private String partnerName;
	
	private String email;
	
	private int packageId;
	
	private String packageName;
	//播放器次数
	private int playerNum;
	//视频数
	private int videoNum;
	//产品数
	private int productNum;
	
	/**
	 * 可添加播放器数
	 */
	private int totalPlayerNum;
	/**
	 * 可关联视频数
	 */
	private int totalVideoNum;
	/**
	 * 可关联产品数
	 */
	private int totalProductNum;
	
	private float price;
	
	private int duration;
	
	
	private Date endTime;
	
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
	 * @return the partnerName
	 */
	public String getPartnerName() {
		return partnerName;
	}

	/**
	 * @param partnerName the partnerName to set
	 */
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
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

	/**
	 * @return the playerNum
	 */
	public int getPlayerNum() {
		return playerNum;
	}

	/**
	 * @param playerNum the playerNum to set
	 */
	public void setPlayerNum(int playerNum) {
		this.playerNum = playerNum;
	}

	/**
	 * @return the videoNum
	 */
	public int getVideoNum() {
		return videoNum;
	}

	/**
	 * @param videoNum the videoNum to set
	 */
	public void setVideoNum(int videoNum) {
		this.videoNum = videoNum;
	}

	/**
	 * @return the productNum
	 */
	public int getProductNum() {
		return productNum;
	}

	/**
	 * @param productNum the productNum to set
	 */
	public void setProductNum(int productNum) {
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

	public int getTotalPlayerNum() {
		return totalPlayerNum;
	}

	public int getTotalVideoNum() {
		return totalVideoNum;
	}

	public int getTotalProductNum() {
		return totalProductNum;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setTotalPlayerNum(int totalPlayerNum) {
		this.totalPlayerNum = totalPlayerNum;
	}

	public void setTotalVideoNum(int totalVideoNum) {
		this.totalVideoNum = totalVideoNum;
	}

	public void setTotalProductNum(int totalProductNum) {
		this.totalProductNum = totalProductNum;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
