/**
 * 
 */
package com.aepan.sysmgr.model.tempinfo;

import java.io.Serializable;

/**
 * 套餐是否有足够的关联视频数量
 * @author lanker
 * 2015年9月15日下午4:30:04
 */
public class LinkVideoInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	//是否可完成本次关联
	public boolean can;
	
	//单个播放器关联的视频数
	public int onceStoreVideoNum;
	//单个播放器可以管理的视频数
	public int onceStoreCanLinkVideoNum;
	
	//要关联数量
	public int linkedVideoNum;
	//套餐中可关联数量
	public int canlinkVideoNum;
}
