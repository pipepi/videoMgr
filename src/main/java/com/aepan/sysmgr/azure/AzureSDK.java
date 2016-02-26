/**
 * 
 */
package com.aepan.sysmgr.azure;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lanker
 * 2015年8月10日下午6:52:07
 */
public class AzureSDK {
	
	private static final Logger logger = LoggerFactory.getLogger(AzureSDK.class);

	
	public static UploadFileRps upVideo(String fileName,InputStream inputSteam){
		UploadFileRps rs = new UploadFileRps();
		try {
			rs = MediaServiceSDK.uploadVideo(fileName, inputSteam);
			rs.videoName = fileName;
			rs.success = true;
		} catch (AzureMediaSDKException e) {
			logger.error(e.getMessage(), e);
			rs.success = false;
		}
		return rs;
	}
	public static UploadFileRps upImg(String containerName,String blockBlobName,InputStream inputStream,long length){
		UploadFileRps rs = new UploadFileRps();
		try {
			rs = StorageServiceSDK.uploadFile(containerName, blockBlobName, inputStream, length);
			rs.success = true;
		} catch (AzureMediaSDKException e) {
			logger.error(e.getMessage(), e);
			rs.success = false;
		}
		return rs;
	}
	public static boolean delVideo(String uploadAssetId,String encodeAssetId){
		try {
			MediaServiceSDK.deleteVideo(uploadAssetId, encodeAssetId);
			return true;
		} catch (AzureMediaSDKException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}
	public static boolean delImg(String containerName,String fileName){
		try {
			StorageServiceSDK.deleteFile(containerName, fileName);
			return true;
		} catch (AzureMediaSDKException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}
}
