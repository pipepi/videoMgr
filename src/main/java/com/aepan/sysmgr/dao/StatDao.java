/**
 * 
 */
package com.aepan.sysmgr.dao;

/**
 * @author rakika
 * 2015年9月12日下午4:03:05
 */
public interface StatDao {
	//video统计
	public boolean statsVideo(String videoId, int type, String sharePlat);
	
	//product统计
	public boolean statsProduct(String productId, int type, String sharePlat);
}
