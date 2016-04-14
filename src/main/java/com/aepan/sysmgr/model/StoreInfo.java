/**
 * 
 */
package com.aepan.sysmgr.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.aepan.sysmgr.model.config.FlowConfig;

/**
 * 播放器信息
 * @author doris
 * 2015年10月8日下午11:02:49
 */
public class StoreInfo implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private int shopId;
	
	private int userId;
	
	private String name;
	
	private String description;
	
	private String shareContent;
	
	private String innerCode;
	
	private String maxLogoUrl_414;
	
	private String qqidKey;
	
	private Date createTime;
	
	private List<Video> videoList;
	
	private List<Integer> productIdList;
	
	private String productList;
	
	private FlowConfig flowConfig;
	
	public FlowConfig getFlowConfig() {
		return flowConfig;
	}

	public void setFlowConfig(FlowConfig flowConfig) {
		this.flowConfig = flowConfig;
	}

	public int getId() {
		return id;
	}

	/**
	 * @return the shopId
	 */
	public int getShopId() {
		return shopId;
	}

	/**
	 * @param shopId the shopId to set
	 */
	public void setShopId(int shopId) {
		this.shopId = shopId;
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

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getShareContent() {
		return shareContent;
	}

	/**
	 * @return the maxLogoUrl_414
	 */
	public String getMaxLogoUrl_414() {
		return maxLogoUrl_414;
	}

	/**
	 * @param maxLogoUrl_414 the maxLogoUrl_414 to set
	 */
	public void setMaxLogoUrl_414(String maxLogoUrl_414) {
		this.maxLogoUrl_414 = maxLogoUrl_414;
	}

	public String getInnerCode() {
		return innerCode;
	}

	/**
	 * @return the qqidKey
	 */
	public String getQqidKey() {
		return qqidKey;
	}

	/**
	 * @param qqidKey the qqidKey to set
	 */
	public void setQqidKey(String qqidKey) {
		this.qqidKey = qqidKey;
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

	public List<Video> getVideoList() {
		return videoList;
	}
	
	public List<Integer> getProductIdList() {
		return productIdList;
	}

	public void setProductIdList(List<Integer> productIdList) {
		this.productIdList = productIdList;
	}

	public String getProductList() {
		return productList;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setShareContent(String shareContent) {
		this.shareContent = shareContent;
	}

	public void setInnerCode(String innerCode) {
		this.innerCode = innerCode;
	}

	public void setVideoList(List<Video> videoList) {
		this.videoList = videoList;
	}

	public void setProductList(String productList) {
		this.productList = productList;
	}

	@Override
	public String toString() {
		return "StoreInfo [id=" + id + ", name=" + name + ", description="
				+ description + ", shareContent=" + shareContent
				+ ", innerCode=" + innerCode + ", videoList=" + videoList
				+ ", productList=" + productList + "]";
	}
	

}
