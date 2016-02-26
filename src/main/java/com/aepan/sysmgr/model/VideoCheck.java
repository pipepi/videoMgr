/**
 * 
 */
package com.aepan.sysmgr.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lanker
 * 2015年8月31日上午9:55:54
 */
public class VideoCheck implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int state_转码中 = 0;
	public static int state_待审核 = 1;
	public static int state_上线 = 2;
	public static int state_未通过 = 3;
	public static int state_下线 = 4;
	
	public int id;
	public int state;
	public String msg;
	public Date createTime;
	
	public VideoCheck(){
		
	}
	public VideoCheck(int id,int state,String msg){
		this.id = id;
		this.state = state;
		this.msg = msg;
		this.createTime = new Date();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
