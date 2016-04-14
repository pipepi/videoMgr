/**
 * 
 */
package com.aepan.sysmgr.service;

import com.aepan.sysmgr.model.Store;

/**
 * 播放器状态服务接口
 * @author lanker
 * 2016年4月14日上午10:14:59
 */
public interface StoreStatusService {
	public void linkVideo(Store store ,boolean haveV);
	public void unActiveVideo(int videoId);
	public void offLineVideo(int videoId);
	
	public void linkProduct(Store store,boolean haveP,boolean havePOff);
	public void resetStatusByProducts(int[] productIds);
}
