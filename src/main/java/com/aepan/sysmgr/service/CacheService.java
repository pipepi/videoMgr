/**
 * 
 */
package com.aepan.sysmgr.service;

import com.aepan.sysmgr.model._enum.CacheObject;

/**
 * @author lanker
 * 2016年1月11日上午11:32:00
 */
public interface CacheService {
	public void add(CacheObject cacheObject,Object obj);
	public void delete(CacheObject cacheObject,int id);
	public void deleteByVideoId(CacheObject cacheObject,int vid);
	public void deleteByProductId(CacheObject cacheObject,int pid);
	public void delete(CacheObject cacheObject, int[] id);
	public String get(CacheObject cacheObject, int id);
}
