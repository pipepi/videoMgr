package com.aepan.sysmgr.azure;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aepan.sysmgr.model.config.AzureConfig;
import com.aepan.sysmgr.util.UuidUtil;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageCredentials;
import com.microsoft.azure.storage.StorageCredentialsAccountAndKey;
import com.microsoft.azure.storage.blob.BlobContainerPermissions;
import com.microsoft.azure.storage.blob.BlobContainerPublicAccessType;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

/**
 * 
 * @author Doris
 * 2015年8月10日下午6:54:11
 */
public class StorageServiceSDK {

	
	private static final Logger logger = LoggerFactory.getLogger(StorageServiceSDK.class);

	private static CloudBlobClient bclient = null;
	
	public static void main(String[] args) throws FileNotFoundException, AzureMediaSDKException {
		logger.error("dddddd");
		AzureConfig config = new AzureConfig();
		init(config);
		File file = new File("D:\\ffmpeg\\input\\云鹏正曜LOGO-02.png");
//		File file = new File("E:\\iq\\img\\car.jpg");
		FileInputStream inputStream = new FileInputStream(file);
		uploadFile("testupload", file.getName(), inputStream , file.length());
//		deleteImage("mycontainer", "Chrysanthemum.jpg");
	}
	
	/**
	 * 初始化储存服
	 * @param storageAccountName 
	 * @param storageKey  
	 * @throws AzureMediaSDKException
	 */
	public static void init(AzureConfig config) throws AzureMediaSDKException{
		 try {
	         TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager(){
	         public X509Certificate[] getAcceptedIssuers(){return null;}
	         public void checkClientTrusted(X509Certificate[] certs, String authType){}
	         public void checkServerTrusted(X509Certificate[] certs, String authType){}}};
	         SSLContext sc = SSLContext.getInstance("TLS");
	         sc.init(null, trustAllCerts, new SecureRandom());
	         HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	         URI blobEndpoint = new URI(config.blobEndpoint);
	 		 URI queueEndpoint = new URI(config.queueEndpoint);
	 		 URI tableEndpoint = new URI(config.tableEndpoint);
	 		 StorageCredentials credentials = new StorageCredentialsAccountAndKey(
	 				config.storageAccount,config.storeKey);
	 		 CloudStorageAccount storageAccount = new CloudStorageAccount(
	 				credentials, blobEndpoint, queueEndpoint, tableEndpoint);
	 		 bclient = storageAccount.createCloudBlobClient();
	      
		}
		catch (Exception e)
		{
			throw new AzureMediaSDKException(
					AzureMediaSDKException.INIT_STORAGE_SERVICE_ERROR,
					e.getMessage(),e);
		}
	}
    
	/**
	 * 
	 * @param containerName
	 * @param fileName
	 * @param inputStream 流
	 * @param length  文件大小
	 * @return
	 * @throws AzureMediaSDKException
	 */
	public static UploadFileRps uploadFile(String containerName,String fileName,InputStream inputStream,long length) throws AzureMediaSDKException{
		
		CloudBlobContainer container = getContainer(containerName);
		CloudBlockBlob blob = null;
		UploadFileRps rps = new UploadFileRps();
		String uuid = UuidUtil.get32UUID();
		String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		String newFileName=uuid+suffix;
		rps.imgName = newFileName;
		try {
			blob = container.getBlockBlobReference(newFileName);
			if(blob.exists()){
				throw new AzureMediaSDKException(
						AzureMediaSDKException.FILE_AREADY_EXISTS,
						"blockBlob file aready exist.");
			}
			blob.upload(inputStream,length);
		}catch (Exception e) {
			throw new AzureMediaSDKException(
					AzureMediaSDKException.UPLOAD_FILE_FAILED,
					e.getMessage(),e);
		}
		String url = blob.getUri().getPath();
		
		rps.imgUrl = url;
		logger.debug("url:"+rps);
		return rps;
	}
	
	

	
	/**
	 * 删除文件
	 * @param containerName
	 * @param fileName
	 * @throws AzureMediaSDKException
	 */
	public static void deleteFile(String containerName,String fileName) throws AzureMediaSDKException{
		  CloudBlobContainer container = getContainer(containerName);
		try {
			CloudBlockBlob blobd = container.getBlockBlobReference(fileName);
			blobd.deleteIfExists();
		} catch (Exception e) {
			throw new AzureMediaSDKException(
					AzureMediaSDKException.DELETE_IMAGE_FAILED,
					e.getMessage(),e);
		}
          
	}
	
	
	
	public  static CloudBlobContainer getContainer(String containerName) throws AzureMediaSDKException {

		CloudBlobContainer container =null;
		try {
			container = bclient.getContainerReference(containerName);
			if(!container.exists()){
				
				  container.createIfNotExists();
				  BlobContainerPermissions containerPermissions = new BlobContainerPermissions();
		          // Include public access in the permissions object.
		          containerPermissions.setPublicAccess(BlobContainerPublicAccessType.CONTAINER);
		          // Set the permissions on the container.
		          container.uploadPermissions(containerPermissions);
			}
			
		} catch (Exception e) {
			throw new AzureMediaSDKException(
					AzureMediaSDKException.GET_CONTAINER_FAILED,
					e.getMessage(),e);
		} 
		
		return container;
	}
	
}
