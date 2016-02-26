package com.aepan.sysmgr.azure;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aepan.sysmgr.model.config.AzureConfig;
import com.aepan.sysmgr.util.UuidUtil;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.windowsazure.Configuration;
import com.microsoft.windowsazure.exception.ServiceException;
import com.microsoft.windowsazure.services.blob.models.BlockList;
import com.microsoft.windowsazure.services.media.MediaConfiguration;
import com.microsoft.windowsazure.services.media.MediaContract;
import com.microsoft.windowsazure.services.media.MediaService;
import com.microsoft.windowsazure.services.media.WritableBlobContainerContract;
import com.microsoft.windowsazure.services.media.entityoperations.EntityCreateOperation;
import com.microsoft.windowsazure.services.media.models.AccessPolicy;
import com.microsoft.windowsazure.services.media.models.AccessPolicyInfo;
import com.microsoft.windowsazure.services.media.models.AccessPolicyPermission;
import com.microsoft.windowsazure.services.media.models.Asset;
import com.microsoft.windowsazure.services.media.models.AssetFile;
import com.microsoft.windowsazure.services.media.models.AssetFileInfo;
import com.microsoft.windowsazure.services.media.models.AssetInfo;
import com.microsoft.windowsazure.services.media.models.Job;
import com.microsoft.windowsazure.services.media.models.JobInfo;
import com.microsoft.windowsazure.services.media.models.JobState;
import com.microsoft.windowsazure.services.media.models.ListResult;
import com.microsoft.windowsazure.services.media.models.Locator;
import com.microsoft.windowsazure.services.media.models.LocatorInfo;
import com.microsoft.windowsazure.services.media.models.LocatorType;
import com.microsoft.windowsazure.services.media.models.MediaProcessor;
import com.microsoft.windowsazure.services.media.models.MediaProcessorInfo;
import com.microsoft.windowsazure.services.media.models.Task;
import com.microsoft.windowsazure.services.media.models.TaskOption;
/**
 * 
 * @author Doris
 * 2015年8月10日下午6:54:38
 */
public class MediaServiceSDK {
	
	private static final Logger logger = LoggerFactory.getLogger(MediaServiceSDK.class);
	
	private static MediaContract mediaService;
	
	public static void main(String[] args) throws AzureMediaSDKException, FileNotFoundException, ServiceException {
//		MediaServiceSDK.init("video9cooo", "Gs5S6xkFnLhFsIKV0X/lRgyfOUc5A6qPDAiFd0I9wK8=");
		AzureConfig config = new AzureConfig();
		init(config);
		logger.debug("init....");
		String fileName="极限变身原本果子.mp4";
		
		String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		System.out.println(suffix);
		
		InputStream inputSteam = new FileInputStream(new File("D:\\ffmpeg\\input\\"+fileName));
		UploadFileRps rps = uploadVideo(fileName, inputSteam);
		System.out.println(rps);
//		mediaService.delete(Asset.delete("nb:cid:UUID:f87d415d-1500-80c3-991f-f1e540c4a3df"));
//		deleteVideo("f87d415d-1500-80c3-38ac-f1e53cebf49c","f87d415d-1500-80c3-fc32-f1e53cebec9a");
//		
//		String path="https://video9cooo.blob.core.chinacloudapi.cn/asset-5e8b415d-1500-80c4-ac26-f1e56e64725d?sv=2012-02-12&sr=c&si=87bab468-e6cd-4db6-9039-536a6abc8e12&sig=tRdB4IUqHcy%2FUSfP3W9Id%2F%2FIDI8jpQT3wUX9sZMeXiw%3D&se=2026-10-19T03%3A42%3A29Z";
//		String fileName="/cc08b2c71c3145eebefeca449fcf444c.mp4";
//		int paramStartIndex = path.indexOf("?");
//		StringBuffer videoUrlSb= new StringBuffer();
//		
//		videoUrlSb.append(path.substring(0, paramStartIndex));
//		videoUrlSb.append(fileName);
//		videoUrlSb.append(path.substring(paramStartIndex, path.length()));
//		
//		System.out.println(videoUrlSb);
		
		
		
	}
	/**
	 * 初始化
	 * @param accountName
	 * @param key
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
			
		} catch (Exception e) {
			throw new AzureMediaSDKException(
					AzureMediaSDKException.TRUSTMANAGER_ERROR,e.getMessage(), e);
		}
	
		Configuration configuration = MediaConfiguration.configureWithOAuthAuthentication(
	   		     "https://wamsshaclus001rest-hs.chinacloudapp.cn/API/",
	   		     "https://wamsprodglobal001acs.accesscontrol.chinacloudapi.cn/v2/OAuth2-13",
	   		     config.mediaAccount,config.mediaKey,
	   		     "urn:WindowsAzureMediaServices");
	        // Create the MediaContract object using the specified configuration.
	        mediaService = MediaService.create(configuration);
		
	}
	
	
	/**
	 * 上传编码视频
	 * @param fileName
	 * @param inputSteam
	 * @return
	 * @throws AzureMediaSDKException
	 */
	public static UploadFileRps uploadVideo(String fileName,InputStream inputSteam) throws AzureMediaSDKException{
		
		UploadFileRps rps= new UploadFileRps();
		String uuid = UuidUtil.get32UUID();
		String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		fileName=uuid+suffix;
		AssetInfo assetToUpload=null;
		try {
			
			assetToUpload = mediaService.create(Asset.create().setName(fileName).setAlternateId("altId"));
			
			rps.setUploadAssetId(assetToUpload.getId());
			
			logger.debug("Created asset with id: " + assetToUpload.getId());

	        // Create an access policy that provides Write access for 15 minutes.
	       EntityCreateOperation<AccessPolicyInfo> ecopertion = AccessPolicy.create("uploadAccessPolicy", 
	                1800.0, 
	                EnumSet.of(AccessPolicyPermission.WRITE));
	        logger.debug("access made");
	        AccessPolicyInfo uploadAccessPolicy = mediaService.create(ecopertion);
	        
	        
	        logger.debug("Created upload access policy with id: " + uploadAccessPolicy.getId());
	        // Create a locator using the access policy and asset.
	        // This will provide the location information needed to add files to the asset.
	        LocatorInfo assetBlobSorageLocator = mediaService.create(Locator.create(uploadAccessPolicy.getId(),
	                assetToUpload.getId(), LocatorType.SAS));
	        logger.debug("Created upload locator with id: " + assetBlobSorageLocator.getId());

	        // Create the blob writer using the locator.
	        WritableBlobContainerContract blobWriter = mediaService.createBlobWriter(assetBlobSorageLocator);


	        // Upload the local file to the asset.
//	        blobWriter.createBlockBlob(fileName, inputSteam);
	        
	        blobWriter.createBlockBlob(fileName, null);
	        
	        String blockId;
	        byte[] buffer = new byte[1024000];
	        BlockList blockList = new BlockList();
	        int bytesRead;

	        ByteArrayInputStream byteArrayInputStream;
	        while ((bytesRead = inputSteam.read(buffer)) > 0)
	        {
	            blockId = UUID.randomUUID().toString();
	            byteArrayInputStream = new ByteArrayInputStream(buffer, 0, bytesRead);
	            blobWriter.createBlobBlock(fileName, blockId, byteArrayInputStream);
	            blockList.addUncommittedEntry(blockId);
	            logger.debug("blockID:"+blockId);
	        }
	 
	        blobWriter.commitBlobBlocks(fileName, blockList);
	 

	        // AssetInfo preparedAsset;
	        logger.debug("File uploaded.");
	        // Inform Media Services about the uploaded files.
	        mediaService.action(AssetFile.createFileInfos(assetToUpload.getId()));
	        
	        
	    
	        mediaService.delete(Locator.delete(assetBlobSorageLocator.getId()));
	        mediaService.delete(AccessPolicy.delete(uploadAccessPolicy.getId()));
			
		} catch (Exception e) {
			throw new AzureMediaSDKException(
					AzureMediaSDKException.UPDATE_VEDIO_FAILD,
					e.getMessage(),e);
		}
		
		
		try {
			encode(assetToUpload,rps,suffix);
			
		} catch (ServiceException e) {
			throw new AzureMediaSDKException(
					AzureMediaSDKException.UPDATE_VEDIO_FAILD,
					e.getMessage(),e);
		}
		return rps;
	}
	
	
	public static void encode(AssetInfo assetInfo,UploadFileRps rps,String fileSuffix) throws ServiceException {
		  
			String assetId=assetInfo.getId();
			String encodingPreset=  "H264 Adaptive Bitrate MP4 Set SD 16x9";
			
			String mediaProcessName="Azure Media Encoder";
			
//	    	String encodingPreset = "H264 Smooth Streaming SD 16x9";   
//			String encodingPreset = "H264 Smooth Streaming 720p";
//	    	String encodingPreset = "H264 Broadband 720p"; 
//			String encodingPreset="H264 Multiple Bitrate 720p";
			
			if(".wmv".equalsIgnoreCase(fileSuffix)||".mkv".equalsIgnoreCase(fileSuffix)){
				encodingPreset="H264 Multiple Bitrate 720p";
				mediaProcessName= "Media Encoder Standard";
			}
	    	                                                                                                              
	    	ListResult < MediaProcessorInfo > processors = mediaService.list(MediaProcessor.list());                     
	    	MediaProcessorInfo latestWameMediaProcessor = null;  

	    	for (MediaProcessorInfo info : processors) {           
	    		logger.debug("MediaProcessorInfo:"+info.getName()+",id:"+info.getId());
	    	    if (info.getName().equals(mediaProcessName)) {                                            
	    	        if (latestWameMediaProcessor == null ||                                                               
	    	                info.getVersion().compareTo(latestWameMediaProcessor.getVersion()) > 0) {                     
	    	            latestWameMediaProcessor = info;                                                                  
	    	        }                                                                                                     

	    	    } 
	    	    
	    	}      
	    	
	    	logger.debug("latestWameMediaProcessor id is :"+latestWameMediaProcessor.getId());

	    	JobInfo job = mediaService.create(Job.create().setName("Encoding ").addInputMediaAsset(assetId)                                                            
	    	        .addTaskCreator(Task.create(latestWameMediaProcessor.getId(),                                         
	    	                "<taskBody>" +                                                                        
	    	                        "<inputAsset>JobInputAsset(0)</inputAsset >" +                         
	    	                        "<outputAsset>JobOutputAsset(0)</outputAsset>" +                       
	    	                        "</taskBody>")                                                                  
	    	                .setConfiguration(encodingPreset)                                                             
	    	                .setOptions(TaskOption.None)                                                                  
	    	                .setName("Encoding")                                                                          
	    	        )                                                                                 
	    	);                                                                                                            
	    	          
	    	logger.debug("job id is :"+job.getId());
	    	
	    	checkJobState(job);                                                                                                             
	    	                                                                                                              
	    	
	    	ListResult<AssetInfo> outputAssets = mediaService.list(Asset.list(job.getOutputAssetsLink()));
	    	Iterator<AssetInfo> iter = outputAssets.iterator();
	    	while(iter.hasNext()){
	    		AssetInfo inf = iter.next();
	    		logger.debug("outputAssetid:"+inf.getId()+",name:"+inf.getName());
	    	}
	    	AssetInfo outPutAsset = outputAssets.get(0);
	    	
	    	releaseFileUrl(outPutAsset,rps);
	    	rps.setEncodeAssetId(outPutAsset.getId());
	        logger.debug("updateMediaServiceRps:"+rps);
	    }



	private static void releaseFileUrl(AssetInfo assetInfo,UploadFileRps rps)
			throws ServiceException {
		
		String assetId = assetInfo.getId();
		String streamURL="";

		AccessPolicyInfo downloadAccessPolicy = mediaService.create(AccessPolicy.create(
				"Download", 5800000.0, EnumSet.of(AccessPolicyPermission.READ)));
		logger.debug("Created download access policy with id: "+ downloadAccessPolicy.getId());

		LocatorInfo downloadLocatorOrigin = mediaService.create(Locator.create(downloadAccessPolicy.getId(), assetId,LocatorType.OnDemandOrigin));
		logger.debug("Created download OnDemandOrigin locator with id: "+ downloadLocatorOrigin.getId());

		
		LocatorInfo downloadLocator = null;
		downloadLocator = mediaService.create(Locator.create(downloadAccessPolicy.getId(), assetInfo.getId(),LocatorType.SAS));
		logger.debug("Created download SAS locator with id: "+ downloadLocator.getId());

		String locatorPathSas = downloadLocator.getPath();
		int startOfSas = locatorPathSas.indexOf("?");
		logger.debug("locatorPathSas:" + locatorPathSas + ",startofSas:"+ startOfSas);
		
		
		String locatorPathOrigin = downloadLocatorOrigin.getPath();
		logger.debug("locatorPathOrigin:" + locatorPathOrigin );
		long totalFileSize=0;
		String largestMp4FileUrl="";
		long largestMp4FileSize=0l;
		// Iterate through the files associated with the encoded asset.
		for (AssetFileInfo assetFile : mediaService.list(AssetFile.list(assetInfo.getAssetFilesLink()))) {
			String fileName = assetFile.getName();
			
			String blobPath = locatorPathSas + fileName;
			if (startOfSas >= 0) {
				blobPath = locatorPathSas.substring(0, startOfSas) + "/"
						+ fileName + locatorPathSas.substring(startOfSas);
			}
			long fileSize = assetFile.getContentFileSize();
			totalFileSize=totalFileSize+fileSize;
			logger.debug("Downloading file: " + fileName + ",fileSize:"+fileSize+",blobPath"+blobPath);
			if(fileName.toLowerCase().endsWith(".mp4")){
				
				if(fileSize>largestMp4FileSize){
					largestMp4FileSize=fileSize;
					largestMp4FileUrl=blobPath;
				}
				
			}
			
			if(fileName.toLowerCase().endsWith(".ism")){
				streamURL=locatorPathOrigin+fileName+"/Manifest";
			}
		}
		rps.setH5VedioFileSize(largestMp4FileSize);
		rps.setH5VedioUrl(largestMp4FileUrl);
		rps.setVedioFileSize(totalFileSize);
		rps.setVedioUrl(streamURL);
	}
	
	
	
	
	public static void download(AssetInfo encodedAsset) throws Exception  {

		AccessPolicyInfo downloadAccessPolicy = null;

		downloadAccessPolicy = mediaService.create(AccessPolicy.create(
				"Download", 15.0, EnumSet.of(AccessPolicyPermission.READ)));
		logger.info("Created download access policy with id: "
				+ downloadAccessPolicy.getId());

		LocatorInfo downloadLocator = null;
		downloadLocator = mediaService.create(Locator.create(
				downloadAccessPolicy.getId(), encodedAsset.getId(),
				LocatorType.SAS));
		logger.info("Created download locator with id: "
				+ downloadLocator.getId());

		logger.info("Accessing the output files of the encoded asset.");
		String locatorPath = downloadLocator.getPath();
		int startOfSas = locatorPath.indexOf("?");
		logger.info("locatorPath:" + locatorPath + ",startofSas:"
				+ startOfSas);
		// Iterate through the files associated with the encoded asset.
		for (AssetFileInfo assetFile : mediaService.list(AssetFile
				.list(encodedAsset.getAssetFilesLink()))) {
			String fileName = assetFile.getName();

			System.out.print("Downloading file: " + fileName + ". ");

			String blobPath = locatorPath + fileName;
			if (startOfSas >= 0) {
				blobPath = locatorPath.substring(0, startOfSas) + "/"
						+ fileName + locatorPath.substring(startOfSas);
			}
			logger.info("blobPath:" + blobPath);
			URI baseuri = new URI(blobPath);
			CloudBlobClient blobClient;
			blobClient = new CloudBlobClient(baseuri);

			// Ensure that you have a c:\output folder, or modify the path
			// specified in the following statement.
			String localFileName = "D:/output/" + fileName;

			CloudBlockBlob sasBlob = new CloudBlockBlob(baseuri, blobClient);
			File fileTarget = new File(localFileName);

			sasBlob.download(new FileOutputStream(fileTarget));
			logger.info("Download complete.");

		}

		logger.info("Deleting download locator and access policy.");
		// mediaService.delete(Locator.delete(downloadLocator.getId()));
		// mediaService.delete(AccessPolicy.delete(downloadAccessPolicy.getId()));

	}


	private static void checkJobState(JobInfo job) throws ServiceException {
//		int count=1;
		while (true) {                                                                                                
		    JobInfo currentJob = mediaService.get(Job.get(job.getId()));                                                   
		    JobState state = currentJob.getState();   
//		    logger.debug("job state is :"+state+",count:"+count);
//		    count++;
		    try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				//ignore
			}
		    if (state == JobState.Finished || state == JobState.Canceled ||                                           
		            state == JobState.Error) {                                                                        
		        break;                                                                                                
		    }                                                                                                         
		}
	}
	    
	
	public static  void deleteVideo(String uploadAssetId,String encodeAssetId) throws AzureMediaSDKException {
	
		try {
			logger.debug(uploadAssetId+"   ,  "+encodeAssetId);
		
			mediaService.delete(Asset.delete(uploadAssetId));
			mediaService.delete(Asset.delete(encodeAssetId));
		} catch (ServiceException e) {
			//ignore
		}     
	}

}
