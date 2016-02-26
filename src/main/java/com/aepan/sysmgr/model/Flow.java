/**
 * 
 */
package com.aepan.sysmgr.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lanker
 * 2015年12月23日下午12:08:08
 */
public class Flow implements Serializable {

	private static final long serialVersionUID = 1L;
	public int id;
	public int userId;
	public int storeId;
	public int videoId;
	public float flow;
	public Date createTime;
	public Flow() {
	}
	public Flow(int userId,int storeId,int videoId,float flow) {
		this.userId = userId;
		this.storeId = storeId;
		this.videoId = videoId;
		this.flow = flow;
	}
}
