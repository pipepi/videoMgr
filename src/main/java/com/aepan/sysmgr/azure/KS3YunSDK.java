/**
 * 
 */
package com.aepan.sysmgr.azure;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aepan.sysmgr.model.config.Ks3Config;
import com.aepan.sysmgr.util.UuidUtil;
import com.ksyun.ks3.dto.Adp;
import com.ksyun.ks3.dto.AdpTask;
import com.ksyun.ks3.dto.CannedAccessControlList;
import com.ksyun.ks3.dto.ObjectMetadata;
import com.ksyun.ks3.dto.PutObjectResult;
import com.ksyun.ks3.dto.ResponseHeaderOverrides;
import com.ksyun.ks3.http.HttpClientConfig;
import com.ksyun.ks3.service.Ks3Client;
import com.ksyun.ks3.service.Ks3ClientConfig;
import com.ksyun.ks3.service.Ks3ClientConfig.PROTOCOL;
import com.ksyun.ks3.service.request.PutAdpRequest;
import com.ksyun.ks3.service.request.PutBucketACLRequest;
import com.ksyun.ks3.service.request.PutObjectRequest;

/**金山云SDK
 * @author Doris.zhou
 * 2015年12月16日上午9:40:50
 */
public class KS3YunSDK {
	
	
	private static final String TASK_STATUS_PROCESS_FAILED = "4";

	private static final String TASK_STATUS_PROCESS_SUCCESS = "3";
	
	private static final String DEFAULT_ENCODE_KEY="_1161_30_636x356.mp4";


	private static final Logger logger = LoggerFactory.getLogger(KS3YunSDK.class);

	
	protected static Ks3Client client;
	
	protected static String videoBucketName;
	protected static String picBucketName;
	protected static String notifyUrl;
	
	
	public static void main(String[] args) throws FileNotFoundException {
		//51:40  -- 30:30
		
//		File file = new File("D:\\ffmpeg\\input\\mutlunumber.mp4");
		File file = new File("D:\\ffmpeg\\input\\Jellyfish.jpg");
		
		System.out.println(file.getName());
		
		Ks3Config ks3Config = new Ks3Config();
		init(ks3Config);
		
		
		long length = client.getObject(videoBucketName, "019b89ae67db499aa1f48359a98ae8c1.mp4").getObject().getObjectMetadata().getContentLength();
		System.out.println(length);

//		UploadFileRps uploadRsp = uploadVideo(file);
		
//		deleteImage("e0696da3cb9b4f1aa1f1cde3460cb750.png");
//		FileInputStream input = new FileInputStream(file);
//		
//		UploadFileRps uploadRsp=uploadImage(file.getName(),input);
//		
//		System.out.println(uploadRsp);
		
//		InputStream inputStream =  new FileInputStream(file);
//		uploadFile("picmall", file.getName(), inputStream );
		
//		String taskId=encodeMp4File(uploadRsp.getUploadAssetId());
//		 getAdpTaskInfo(taskId);
//		taskId = encodeFlvFile("videomall", "野生动物-转码.3gp");
//		 taskId = "00PYKPEFfif6";
//		 getAdpTaskInfo(taskId);
		
		
		Date date = new Date(1451467365000l);
		System.out.println(date);
	}
	
	public static void init(Ks3Config ks3Config){
		
		client = new Ks3Client(ks3Config.accesskeyid,ks3Config.accesskeysecret);
		
		videoBucketName=ks3Config.videoBucketName;
		picBucketName = ks3Config.picBucketName;
		notifyUrl=ks3Config.notifyUrl;
		Ks3ClientConfig config = new Ks3ClientConfig();
		
		config.setEndpoint("kssws.ks-cdn.com");
		config.setProtocol(PROTOCOL.http);
		config.setPathStyleAccess(false);
		HttpClientConfig hconfig = new HttpClientConfig();
		//在HttpClientConfig中可以设置httpclient的相关属性，比如代理，超时，重试等。
		config.setHttpClientConfig(hconfig);
		client.setKs3config(config);
	}
	
	
	public static UploadFileRps uploadVideo(File file){
		UploadFileRps uploadFileRps = uploadFile(videoBucketName, file,false);
		String key = uploadFileRps.getUploadAssetId();
		String taskId = encodeMp4File(key);
		uploadFileRps.setEncodeAssetId(taskId);
		//0,"task is create fail"、1,"task is create success"、2,"task is processing"、3,"task is process success"、4,"task is process fail"
		while(true){
			AdpTask task = client.getAdpTask(taskId);
			String processStatus = task.getProcessstatus();
			 if (TASK_STATUS_PROCESS_SUCCESS.equals(processStatus)) {   
				uploadFileRps.success = true;
			    break;                                                                                                
			 }
			 
			 if(TASK_STATUS_PROCESS_FAILED.equals(processStatus)){
				 uploadFileRps.success = false;
				 break;
			 }
			 try {
					Thread.sleep(1000);
			 } catch (InterruptedException e) {
				//ignore
			 }
		}
		
		client.deleteObject(videoBucketName,key);
		
		long length = client.getObject(videoBucketName, key+DEFAULT_ENCODE_KEY).getObject().getObjectMetadata().getContentLength();
		
		String videoUrl = getPrivateVideoUrl(key+DEFAULT_ENCODE_KEY);
		
		uploadFileRps.setH5VedioUrl(videoUrl);
		uploadFileRps.setVedioUrl(videoUrl);
		uploadFileRps.setH5VedioFileSize(length);
		uploadFileRps.setVedioFileSize(length);
		return uploadFileRps;
		
	}
	
	
	
	protected static UploadFileRps uploadImage(File file){
		return uploadFile(picBucketName, file,true);
	}
	
	public static UploadFileRps uploadImage(String fileName,InputStream inputStream){
		UploadFileRps rsp = uploadFile(picBucketName, fileName,inputStream,true);
		return rsp;
	}
	
	
	protected static UploadFileRps uploadFile(String bucketName,File file,boolean isPublic){
		String fileName = file.getName();
		String uuid = UuidUtil.get32UUID();
		String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		fileName=uuid+suffix;
		 PutObjectRequest request = new PutObjectRequest(bucketName,fileName, file);
		 if(isPublic){
			 //上传一个公开文件
			 request.setCannedAcl(CannedAccessControlList.PublicRead);
		 }
	     PutObjectResult result = client.putObject(request);
	     
	     UploadFileRps rps = new UploadFileRps();
	     rps.setUploadAssetId(fileName);
	     
	    logger.info(result.toString());
	    
		return rps;
	}
	
	
	protected static UploadFileRps uploadFile(String bucketName,String key,InputStream inputStream,boolean isPublic){
		String uuid = UuidUtil.get32UUID();
		String suffix = key.substring(key.lastIndexOf("."), key.length());
		key=uuid+suffix;
	    ObjectMetadata meta = new ObjectMetadata();
	    PutObjectRequest request = new PutObjectRequest(bucketName,
	    		key, inputStream,meta);
	    if(isPublic){
			 //上传一个公开文件
			 request.setCannedAcl(CannedAccessControlList.PublicRead);
		 }
	    client.putObject(request);
	    
	    UploadFileRps rps = new UploadFileRps();
	     rps.imgUrl=key;
	     rps.success=true;
	    // 可以指定内容的长度，否则程序会把整个输入流缓存起来，可能导致jvm内存溢出
	    logger.info("rps:"+rps);
	    return rps;
	}
	
	
	public static String encodeMp4File(String key){
		PutAdpRequest request = new PutAdpRequest(videoBucketName,key);
	    List<Adp> adps = new ArrayList<Adp>();
	    
	    //高清
	    Adp adp1161 = new Adp();
	    adp1161.setCommand("tag=avop&f=mp4&res=854x480&vbr=1100k&vfr=30");
	    adp1161.setKey(key+DEFAULT_ENCODE_KEY);
	    adps.add(adp1161);
	    
	    request.setAdps(adps);
	    //处理结果接受服务，具体查看API文档，异步数据处理
	    request.setNotifyURL(notifyUrl);
	    //TODO测试notify链接目前不可用
	    
	    //任务id,用于查询任务处理状态
	    String taskId = client.putAdpTask(request).getTaskId();
	    
	    logger.debug(taskId);
	    
	    return taskId;
	}
	
	
	
	public static String passEncodeMp4File(String key){
		PutAdpRequest request = new PutAdpRequest(videoBucketName,key);
	    List<Adp> adps = new ArrayList<Adp>();
	    //蓝光
	    Adp adp2263 = new Adp();
	    //具体command详见：http://ks3.ksyun.com/doc/video/avop.html
	    adp2263.setCommand("tag=avop&f=mp4&res=848x476&vbr=2263k&vfr=30");
	    //处理完成后存储的key，相当于文档中说的tag=saveas&object=....
	    adp2263.setKey(key+"_2263_30_848x476.mp4");
	    adps.add(adp2263);
	    
	    
	    //超清
	    Adp adp1620 = new Adp();
	    adp1620.setCommand("tag=avop&f=mp4&res=848x476&vbr=1620k&vfr=30");
	    adp1620.setKey(key+"_1620_30_848x476.mp4");
	    adps.add(adp1620);
	    
	    
	    //标清
	    Adp adp900 = new Adp();
	    adp900.setCommand("tag=avop&f=mp4&res=636x356&vbr=900k&vfr=30");
	    adp900.setKey(key+"_900_30_636x356.mp4");
	    adps.add(adp900);
	    
	    //流畅
	    Adp adp648 = new Adp();
	    adp648.setCommand("tag=avop&f=mp4&res=424x240&vbr=648k&vfr=30");
	    adp648.setKey(key+"_648_30_424x240.mp4");
	    adps.add(adp648);

	    request.setAdps(adps);
	    //处理结果接受服务，具体查看API文档，异步数据处理
	    request.setNotifyURL(notifyUrl);
	    //TODO测试notify链接目前不可用
	    
	    //任务id,用于查询任务处理状态
	    String taskId = client.putAdpTask(request).getTaskId();
	    
	    logger.debug(taskId);
	    
	    return taskId;
	}
	
	
	protected static String encodeFlvFile(String bucketName,String key){
		PutAdpRequest request = new PutAdpRequest(bucketName,key);
	    List<Adp> adps = new ArrayList<Adp>();
	    //蓝光
	    Adp adp2263 = new Adp();
	    //具体command详见：http://ks3.ksyun.com/doc/video/avop.html
	    adp2263.setCommand("tag=avop&f=flv&res=848x476&vbr=2263k&vfr=30");
	    //处理完成后存储的key，相当于文档中说的tag=saveas&object=....
	    adp2263.setKey(key+"_2263_30_848x476.flv");
	    adps.add(adp2263);
	    
	    
	    //超清
	    Adp adp1620 = new Adp();
	    adp1620.setCommand("tag=avop&f=flv&res=848x476&vbr=1620k&vfr=30");
	    adp1620.setKey(key+"_1620_30_848x476.flv");
	    adps.add(adp1620);
	    
	    //高清
	    Adp adp1161 = new Adp();
	    adp1161.setCommand("tag=avop&f=flv&res=636x356&vbr=1161k&vfr=30");
	    adp1161.setKey(key+"_1161_30_636x356.flv");
	    adps.add(adp1161);
	    
	    //标清
	    Adp adp900 = new Adp();
	    adp900.setCommand("tag=avop&f=flv&res=636x356&vbr=900k&vfr=30");
	    adp900.setKey(key+"_900_30_636x356.flv");
	    adps.add(adp900);
	    
	    //流畅
	    Adp adp648 = new Adp();
	    adp648.setCommand("tag=avop&f=flv&res=424x240&vbr=648k&vfr=30");
	    adp648.setKey(key+"_648_30_424x240.flv");
	    adps.add(adp648);

	    request.setAdps(adps);
	    //处理结果接受服务，具体查看API文档，异步数据处理
	    request.setNotifyURL(notifyUrl);
	    //TODO测试notify链接目前不可用
	    
	    //任务id,用于查询任务处理状态
	    String taskId = client.putAdpTask(request).getTaskId();
	    
	    logger.debug(taskId);
	    return taskId;
	}
	
	
	
	/**
	*通过预设的ACL设置object的访问权限，预设的ACL包括:private:私有。public-read:为所有用户授予read权限。
	*/
	public void putBucketAclWithCannedAcl(){
	    PutBucketACLRequest request = new PutBucketACLRequest("<bucket名称>");
	    //设为私有
	    request.setCannedAcl(CannedAccessControlList.Private);
	    //设为公开读 
	    //request.setCannedAcl(CannedAccessControlList.PublicRead);
	    client.putBucketACL(request);
	}

	
	protected static String getPrivateVideoUrl(String key){
		int validTime=720000000;
		ResponseHeaderOverrides overrides = new ResponseHeaderOverrides();
		//overrides.setContentType("text/html");
		String url = client.generatePresignedUrl(videoBucketName,key,validTime,overrides);
		logger.debug(url);
		return url;
	}
	
	
	protected static  AdpTask getAdpTaskInfo(String taskId){
	    AdpTask task = client.getAdpTask(taskId);
	    //0,"task is create fail"、1,"task is create success"、2,"task is processing"、3,"task is process success"、4,"task is process fail"
	    task.getProcessstatus();
	    //0,"task is not notify"、1,"task is notify success"、2,"task is notify fail"
	    task.getNotifystatus();
	    task.getProcessdesc();
	    //查询每条命令的具体执行结果，包括是否执行成功，以及执行成功后存储的key
	    task.getAdpInfos();
	    
	    logger.debug(task.toString());
	    
	    return task;
	}
	
	
	
	public static  void deleteVideo(String key){
		try {
			client.deleteObject(videoBucketName,key);
			client.deleteObject(videoBucketName,key+DEFAULT_ENCODE_KEY);

		} catch (Exception e) {
			logger.warn("deletevideo failed:"+key);
			//ignore
		}
	}
	
	
	public static  void deleteImage(String key){
		try {
			client.deleteObject(picBucketName,key);
		} catch (Exception e) {
			logger.warn("deleteImage failed:"+key);
			// ignore
		}
	}
	

}
