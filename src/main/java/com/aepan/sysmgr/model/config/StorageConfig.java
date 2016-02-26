/**
 * 
 */
package com.aepan.sysmgr.model.config;

/**
 * @author doris。zhou
 * 2015年12月30日下午4:33:54
 */
public class StorageConfig {

	public static int PLATFORM_LOCAL=0;//本地
	public static int PLATFORM_AZURE=1;//微软云
	public static int PLATFORM_KS3=2;//金山云
	
	public int storagePlatform=PLATFORM_KS3;

	public int getStoragePlatform() {
		return storagePlatform;
	}

	public void setStoragePlatform(int storagePlatform) {
		this.storagePlatform = storagePlatform;
	}
	
}
