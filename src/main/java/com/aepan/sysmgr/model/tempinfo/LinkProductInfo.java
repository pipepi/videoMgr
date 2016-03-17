/**
 * 
 */
package com.aepan.sysmgr.model.tempinfo;

import java.io.Serializable;

/**
 * 套餐是否有足够的关联商品数量
 * @author lanker
 * 2015年9月15日下午4:30:04
 */
public class LinkProductInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	//是否可完成本次关联
	public boolean can;
	//单个播放器关联的产品数
	public int onceStoreProductNum;
	//单个播放器可以管理的产品数
	public int onceStoreCanLinkProductNum;
	//要关联数量
	public int linkedProductNum;
	//套餐中可关联数量
	public int canlinkProductNum;
	//关联后商品id列表
	public String linkPids;
	//取消关联商品id列表
	public String unLinkPids;
	public LinkProductInfo() {
		linkPids = "";
		unLinkPids = "";
	}
}
