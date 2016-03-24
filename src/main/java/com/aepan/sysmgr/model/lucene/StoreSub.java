/**
 * 
 */
package com.aepan.sysmgr.model.lucene;

import java.io.Serializable;
import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;

/**
 * @author lanker
 * 2015年11月10日下午2:36:36
 */
public class StoreSub implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String partnerUserId;
	private String name;
	private String desc;
	private String img;
	private String imgMax;
	private String storeType;
	private String pids;
	private float priceMax;
	private float priceMin;
	private int hot;
	private Date updateTime;
	public StoreSub() {
	}
	public StoreSub(int id ,String name ,String desc ,String img,String imgMax,String storeType,String pids,float priceMax,float priceMin,int hot,Date updateTime,String partnerUserId) {
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.img = img;
		this.imgMax = imgMax;
		this.storeType = storeType;
		this.pids = pids;
		this.priceMax = priceMax;
		this.priceMin = priceMin;
		this.hot = hot;
		this.updateTime = updateTime;
		this.partnerUserId = partnerUserId;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * @return the partnerUserId
	 */
	public String getPartnerUserId() {
		return partnerUserId;
	}
	/**
	 * @param partnerUserId the partnerUserId to set
	 */
	public void setPartnerUserId(String partnerUserId) {
		this.partnerUserId = partnerUserId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getImgMax() {
		return imgMax;
	}
	public void setImgMax(String imgMax) {
		this.imgMax = imgMax;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getStoreType() {
		return storeType;
	}
	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}
	public String getPids() {
		return pids;
	}
	public void setPids(String pids) {
		this.pids = pids;
	}
	public float getPriceMax() {
		return priceMax;
	}
	public void setPriceMax(float priceMax) {
		this.priceMax = priceMax;
	}
	public float getPriceMin() {
		return priceMin;
	}
	public void setPriceMin(float priceMin) {
		this.priceMin = priceMin;
	}
	public int getHot() {
		return hot;
	}
	public void setHot(int hot) {
		this.hot = hot;
	}
	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
