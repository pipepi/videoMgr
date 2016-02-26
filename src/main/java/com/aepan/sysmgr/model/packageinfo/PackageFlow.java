/**
 * 
 */
package com.aepan.sysmgr.model.packageinfo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Doris 2015年10月6日下午2:06:13
 */
public class PackageFlow implements Serializable {

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
	 * 流量数
	 */
	private float flowNum;
	/**
	 * 开始时间
	 */
	private Date startTime;

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

	public float getFlowNum() {
		return flowNum;
	}

	public void setFlowNum(float flowNum) {
		this.flowNum = flowNum;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
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
		return "PackageFlow [id=" + id + ", userId=" + userId + ", flowNum="
				+ flowNum + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", createTime=" + createTime + ", updateTime=" + updateTime
				+ "]";
	}

}
