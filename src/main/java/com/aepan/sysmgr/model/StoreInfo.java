/**
 * 
 */
package com.aepan.sysmgr.model;

import java.io.Serializable;
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
	
	private int userId;
	
	private String name;
	
	private String description;
	
	private String shareContent;
	
	private String innerCode;
	
	private String qqidKey;
	
	private List<Video> videoList;
	
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

	public List<Video> getVideoList() {
		return videoList;
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
