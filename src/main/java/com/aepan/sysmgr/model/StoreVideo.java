/**
 * 
 */
package com.aepan.sysmgr.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 店铺产品关系
 * @author rakika
 * 2015年8月14日上午1:06:22
 */
public class StoreVideo implements Serializable{

	/**
	 * 
	 */
	public static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	/**
	 * 
	 */
	public StoreVideo() {
	}
	public StoreVideo(int userId,int storeId,int videoId) {
		this.userId = userId;
		this.storeId = storeId;
		this.videoId = videoId;
	}
	public int id;
	
	public int userId;
	
	public int storeId;
	
	public int videoId;
	
	public Date createTime;

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
	 * @return the storeId
	 */
	public int getStoreId() {
		return storeId;
	}

	/**
	 * @param storeId the storeId to set
	 */
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	
	public int getVideoId() {
		return videoId;
	}

	public void setVideoId(int videoId) {
		this.videoId = videoId;
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
}
