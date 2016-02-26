/**
 * 
 */
package com.aepan.sysmgr.model.packageinfo;

import java.io.Serializable;

/**
 * @author Doris
 * 2015年10月6日下午2:06:13
 */
public class FlowInfo implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	
	private int flowNum;
	
	private int cost;
	
	private int duration;
	
	

	/**
	 * @param id
	 * @param flowNum
	 * @param cost
	 * @param duration
	 */
	public FlowInfo(int id, int flowNum, int cost, int duration) {
		super();
		this.id = id;
		this.flowNum = flowNum;
		this.cost = cost;
		this.duration = duration;
	}
	
	
	public FlowInfo(){
		
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFlowNum() {
		return flowNum;
	}

	public void setFlowNum(int flowNum) {
		this.flowNum = flowNum;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "FlowInfo [id=" + id + ", flowNum=" + flowNum + ", cost=" + cost
				+ ", duration=" + duration + "]";
	}
	
}
