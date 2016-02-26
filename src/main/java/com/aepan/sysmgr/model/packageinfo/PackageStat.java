/**
 * 
 */
package com.aepan.sysmgr.model.packageinfo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Doris 2015年9月8日下午3:37:25
 */
public class PackageStat implements Serializable {
	
	public static final int SEND_NOTIFY_PROPERTY_STAT_LESS_THAN_30D=1;
	
	public static final int SEND_NOTIFY_PROPERTY_FLOW_LESS_THAN_200M=2;
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 流水号
	 */
	private int id;
	/**
	 * 用户id
	 */
	private int userId;
	/**
	 * 套餐id
	 */
	private int packageId;
	/**
	 * 播放器数量
	 */
	private int playerNum;
	/**
	 * 视频数量
	 */
	private int videoNum;
	/**
	 * 产品数量
	 */
	private int productNum;
	/**
	 * 使用流量
	 */
	private float usedFlowNum;
	
	/**
	 * 开通流量数
	 */
	private float flowNum;
	/**
	 * 时长
	 */
	private int duration;
	/**
	 * 发送通知情况
	 */
	private int sendNotify;
	/**
	 * 开始时间
	 */
	private Date startime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;

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

	public int getPackageId() {
		return packageId;
	}

	public void setPackageId(int packageId) {
		this.packageId = packageId;
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

	/**
	 * @return the flowNum
	 */
	public float getFlowNum() {
		return flowNum;
	}

	/**
	 * @param flowNum the flowNum to set
	 */
	public void setFlowNum(float flowNum) {
		this.flowNum = flowNum;
	}

	/**
	 * @return the usedFlowNum
	 */
	public float getUsedFlowNum() {
		return usedFlowNum;
	}

	/**
	 * @param usedFlowNum the usedFlowNum to set
	 */
	public void setUsedFlowNum(float usedFlowNum) {
		this.usedFlowNum = usedFlowNum;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * @return the sendNotify
	 */
	public int getSendNotify() {
		return sendNotify;
	}

	/**
	 * @param sendNotify the sendNotify to set
	 */
	public void setSendNotify(int sendNotify) {
		this.sendNotify = sendNotify;
	}

	public Date getStartime() {
		return startime;
	}

	public void setStartime(Date startime) {
		this.startime = startime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

	@Override
	public String toString() {
		return "PackageStat [id=" + id + ", userId=" + userId + ", packageId="
				+ packageId + ", playerNum=" + playerNum + ", videoNum="
				+ videoNum + ", productNum=" + productNum + ", usedFlowNum="
				+ usedFlowNum + ", flowNum=" + flowNum + ", duration="
				+ duration + ", sendNotify=" + sendNotify + ", startime="
				+ startime + ", endTime=" + endTime + ", createTime="
				+ createTime + ", updateTime=" + updateTime + "]";
	}

}
