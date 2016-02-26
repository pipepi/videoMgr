/**
 * 
 */
package com.aepan.sysmgr.service;

/**
 * 统计service
 * @author rakika
 * 2015年9月12日下午2:44:45
 */
public interface StatsService {
	//video统计
	public boolean statsVideo(String videoId, int type, String sharePlat);
	
	//product统计
	public boolean statsProduct(String productId, int type, String sharePlat);
}
