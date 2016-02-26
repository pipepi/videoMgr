/**
 * 
 */
package com.aepan.sysmgr.model.hm;

import java.io.Serializable;

/**
 * 第三方 用户中心 用户浏览记录  通过播放器ids获取播放器名称和图片
 * @author lanker
 * 2015年11月23日上午11:08:57
 */
public class StoreSubInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private int userId;
	private int partnerUserId;
	private int clickNum;
	private String name;
	private String img;
	private String img301;
	private String imgMax;
	private String imgMax414;
	public StoreSubInfo() {
	}
	public StoreSubInfo(int id,int userId,String name,String img) {
		this.id = id;
		this.name = name;
		this.img = img;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
	public String getImg301() {
		return img301;
	}
	public void setImg301(String img301) {
		this.img301 = img301;
	}
	public String getImgMax414() {
		return imgMax414;
	}
	public void setImgMax414(String imgMax414) {
		this.imgMax414 = imgMax414;
	}
	/**
	 * @return the imgMax
	 */
	public String getImgMax() {
		return imgMax;
	}
	/**
	 * @param imgMax the imgMax to set
	 */
	public void setImgMax(String imgMax) {
		this.imgMax = imgMax;
	}
	/**
	 * @return the clickNum
	 */
	public int getClickNum() {
		return clickNum;
	}
	/**
	 * @param clickNum the clickNum to set
	 */
	public void setClickNum(int clickNum) {
		this.clickNum = clickNum;
	}
	/**
	 * @return the partnerUserId
	 */
	public int getPartnerUserId() {
		return partnerUserId;
	}
	/**
	 * @param partnerUserId the partnerUserId to set
	 */
	public void setPartnerUserId(int partnerUserId) {
		this.partnerUserId = partnerUserId;
	}
	
}
