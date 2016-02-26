/**
 * 
 */
package com.aepan.sysmgr.service;

import com.aepan.sysmgr.azure.UploadFileInput;
import com.aepan.sysmgr.azure.UploadFileRps;


/**
 * @author lanker
 * 2015年12月31日上午9:58:40
 */
public interface StorageService {
	public UploadFileRps uploadVideo(UploadFileInput in);
	public UploadFileRps uploadImg(UploadFileInput in);
	
	public boolean deleteVideo(UploadFileInput in);
	public boolean deleteImg(UploadFileInput in);
	
	public String localVideoUrl(String videoName);
	public String localImgUrl(String imgName);
	public int storage();
	
}
