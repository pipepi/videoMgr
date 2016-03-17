/**
 * 
 */
package com.aepan.sysmgr.model.packageinfo;

import java.io.Serializable;
import java.util.Date;

import com.aepan.sysmgr.util.StringUtil;

/**
 * Doris 2015年9月4日下午5:01:47
 */
public class PackageInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int PACKAGE_TYPE_ALL=0;
	
	public static final int PACKAGE_TYPE_PACKAGE=1;
	
	public static final int PACKAGE_TYPE_FLOW=2;
	/**
	 * 流水号
	 */
	private int id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 
	 */
	private int packageType;
	/**
	 * 播放器数量
	 */
	private int playerNum;
	/**
	 * 每个播放器可关联视频数量
	 */
	private int videoNum;
	/**
	 * 产品数量
	 */
	private int productNum;
	/**
	 * 每月套餐价格
	 */
	//private float price;
	
	/**
	 * 流量
	 */
	private float flowNum;
	/**
	 * 每月流量价格
	 */
	//private float monthFlowPrice;
	/**
	 * 默认显示时长
	 */
	private int duration;
	
	/**
	 * 可选择的时长
	 */
	private String canChooseDuration;
	
	/**
	 * 总价格
	 */
	private float totalPrice;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;

	public int getCanHaveVideoNum(){
		return videoNum*playerNum;
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPackageType() {
		return packageType;
	}

	public void setPackageType(int packageType) {
		this.packageType = packageType;
	}

	public int getPlayerNum() {
		return playerNum;
	}

	public void setPlayerNum(int playerNum) {
		this.playerNum = playerNum;
	}

	public int getVideoNum() {
		return videoNum;
	}

	public void setVideoNum(int videoNum) {
		this.videoNum = videoNum;
	}

	public int getProductNum() {
		return productNum;
	}

	public void setProductNum(int productNum) {
		this.productNum = productNum;
	}

	public float getFlowNum() {
		return flowNum;
	}

	public void setFlowNum(float flowNum) {
		this.flowNum = flowNum;
	}

	/*public float getMonthFlowPrice() {
		return monthFlowPrice;
	}

	public void setMonthFlowPrice(float monthFlowPrice) {
		this.monthFlowPrice = monthFlowPrice;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}*/

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * @return the canChooseDuration
	 */
	public String getCanChooseDuration() {
		return canChooseDuration;
	}

	/**
	 * @param canChooseDuration the canChooseDuration to set
	 */
	public void setCanChooseDuration(String canChooseDuration) {
		this.canChooseDuration = canChooseDuration;
	}

	/**
	 * @return the totalPrice
	 */
	public float getTotalPrice() {
		return totalPrice;
	}

	/**
	 * @param totalPrice the totalPrice to set
	 */
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}


	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	
	public final PackageInfo harmSensitiveWord(){
		this.name = StringUtil.harmSensitiveWord(this.name);
		return this;
	}

	@Override
	public String toString() {
		return "PackageInfo [id=" + id + ", name=" + name + ", packageType="
				+ packageType + ", playerNum=" + playerNum + ", videoNum="
				+ videoNum + ", productNum=" + productNum //+ ", price=" + price
				+ ", flowNum=" + flowNum //+ ", monthFlowPrice=" + monthFlowPrice
				+ ", duration=" + duration + ", canChooseDuration="
				+ canChooseDuration + ", totalPrice=" + totalPrice
				+ ", createTime=" + createTime + ", updateTime=" + updateTime
				+ "]";
	}

}
