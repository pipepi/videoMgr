/**
 * 
 */
package com.aepan.sysmgr.model.config;

import java.io.Serializable;

import com.aepan.sysmgr.util.JSONUtil;

/**
 * @author Doris.zhou
 * 2015年11月24日下午4:04:25
 */
public class AzureConfig implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String storageAccount="testenv";
	public String storeKey="3bBPS+FGznrAnuZdYWSCM91XET/Dm6XWf9c0Azu4Z7BAqpAPlhEVLtuLOH3USSxDslLVd16ywHjUfX1U2Ld3cg==";
	
	
	public String blobEndpoint ="https://testenv.blob.core.chinacloudapi.cn/";
	public String queueEndpoint = "https://testenv.queue.core.chinacloudapi.cn/";
	public String tableEndpoint = "https://testenv.table.core.chinacloudapi.cn/";
	
	

	public String mediaAccount="testenv";
	public String mediaKey="cmor5klJKBhatdLTH6gJFuS7+PWsXtuI2XcjOIJzLzc=";
	public static void main(String[] args) {
		AzureConfig c = new AzureConfig();
		System.out.println(JSONUtil.toJson(c));
	}
	public String getStorageAccount() {
		return storageAccount;
	}
	public void setStorageAccount(String storageAccount) {
		this.storageAccount = storageAccount;
	}
	public String getStoreKey() {
		return storeKey;
	}
	public void setStoreKey(String storeKey) {
		this.storeKey = storeKey;
	}
	public String getBlobEndpoint() {
		return blobEndpoint;
	}
	public void setBlobEndpoint(String blobEndpoint) {
		this.blobEndpoint = blobEndpoint;
	}
	public String getQueueEndpoint() {
		return queueEndpoint;
	}
	public void setQueueEndpoint(String queueEndpoint) {
		this.queueEndpoint = queueEndpoint;
	}
	public String getTableEndpoint() {
		return tableEndpoint;
	}
	public void setTableEndpoint(String tableEndpoint) {
		this.tableEndpoint = tableEndpoint;
	}
	public String getMediaAccount() {
		return mediaAccount;
	}
	public void setMediaAccount(String mediaAccount) {
		this.mediaAccount = mediaAccount;
	}
	public String getMediaKey() {
		return mediaKey;
	}
	public void setMediaKey(String mediaKey) {
		this.mediaKey = mediaKey;
	}
	
}
