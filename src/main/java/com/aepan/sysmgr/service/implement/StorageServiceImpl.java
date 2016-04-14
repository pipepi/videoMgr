/**
 * 
 */
package com.aepan.sysmgr.service.implement;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aepan.sysmgr.azure.AzureSDK;
import com.aepan.sysmgr.azure.KS3YunSDK;
import com.aepan.sysmgr.azure.UploadFileInput;
import com.aepan.sysmgr.azure.UploadFileRps;
import com.aepan.sysmgr.model.config.AzureConfig;
import com.aepan.sysmgr.model.config.FileConfig;
import com.aepan.sysmgr.model.config.Ks3Config;
import com.aepan.sysmgr.model.config.StorageConfig;
import com.aepan.sysmgr.service.ConfigService;
import com.aepan.sysmgr.service.StorageService;
import com.aepan.sysmgr.util.ConfigManager;
import com.aepan.sysmgr.util.FileUtil;
import com.aepan.sysmgr.util.FileUtilAzure;

/**
 * @author lanker
 * 2015年12月31日上午10:14:20
 */
@Service
public class StorageServiceImpl implements StorageService {
	private static final Logger logger = LoggerFactory.getLogger(StorageServiceImpl.class);
	
	private ConfigManager configManager;
	@Autowired
	private ConfigService configService;
	private FileConfig fileConfig;
	private StorageConfig storageConfig;
	private AzureConfig azureConfig;
	private Ks3Config ks3Config;
	@Override
	public UploadFileRps uploadVideo(UploadFileInput in) {
		init();
		UploadFileRps rs = new UploadFileRps();
		if(in.isLocalVideo){
			rs.file = FileUtil.initVideo(in.req,fileConfig);
		}else{
			File file = new File(localVideoUrl(in.uploadAssetId));
			if(file!=null&&file.isFile()&&file.canRead()){
				if(storageConfig.storagePlatform==StorageConfig.PLATFORM_AZURE){
					FileInputStream fis = null ;
					try {
						fis= new FileInputStream(file);
						rs = AzureSDK.upVideo(in.uploadAssetId,fis);
					} catch (IOException e) {
						logger.info("upload video to azure fail,file("+file.getAbsolutePath()+") not found");
					}finally{
						if(fis!=null){
							try {
								fis.close();
							} catch (IOException e) {
								logger.info("upload video to azure fail,close inputstream error");
							}
						}
					}
				}else if(storageConfig.storagePlatform==StorageConfig.PLATFORM_KS3){
					rs = KS3YunSDK.uploadVideo(file);
				}
				if(rs.success){
					file.deleteOnExit();
				}
			}
		}
		return rs;
	}

	@Override
	public UploadFileRps uploadImg(UploadFileInput in) {
		init();
		UploadFileRps rs = new UploadFileRps();
		if(in.isLocalImg){
			 if(in.req!=null){
				 rs.file = FileUtil.initImg(in.req,fileConfig);
			 }else{
				 rs.file = FileUtil.bytes2File(in.imgContent, in.imgName,fileConfig);
			 }
		}else{
			if(storageConfig.storagePlatform==StorageConfig.PLATFORM_AZURE){
				rs =  FileUtilAzure.uploadImg(in.imgContent, in.imgName,fileConfig);
			}else if(storageConfig.storagePlatform==StorageConfig.PLATFORM_KS3){
				rs = KS3YunSDK.uploadImage(in.imgName, new ByteArrayInputStream(in.imgContent));
			}
		}
		return rs;
	}

	@Override
	public boolean deleteVideo(UploadFileInput in) {
		init();
		boolean rs = false;
		if(in.isLocalVideo){
			rs = FileUtil.remove(localVideoUrl(in.uploadAssetId));
		}else{
			if(storageConfig.storagePlatform==StorageConfig.PLATFORM_AZURE){
				rs = FileUtilAzure.delVideo(in.uploadAssetId,in.encodeAssetId);
			}else if(storageConfig.storagePlatform==StorageConfig.PLATFORM_KS3){
				KS3YunSDK.deleteVideo(in.uploadAssetId);
				rs = true;
			}
		}
		return rs;
	}

	@Override
	public boolean deleteImg(UploadFileInput in) {
		init();
		boolean rs = false;
		if(in.isLocalImg){
			if(in.imgMax!=null&&in.imgMax.trim().length()>0){
				FileUtil.remove(localImgUrl(in.imgMax));
			}
			if(in.imgMin!=null&&in.imgMin.trim().length()>0){
				FileUtil.remove(localImgUrl(in.imgMin));
			}
		}else{
			if(storageConfig.storagePlatform==StorageConfig.PLATFORM_AZURE){
				if(in.imgMax!=null&&in.imgMax.trim().length()>0){
					rs = FileUtilAzure.delImg(fileConfig, in.imgMax);
				}
				if(in.imgMin!=null&&in.imgMin.trim().length()>0){
					rs = FileUtilAzure.delImg(fileConfig, in.imgMin);
				}
			}else if(storageConfig.storagePlatform==StorageConfig.PLATFORM_KS3){
				if(in.imgMax!=null&&in.imgMax.trim().length()>0){
					KS3YunSDK.deleteImage(in.imgMax);
				}
				if(in.imgMin!=null&&in.imgMin.trim().length()>0){
					KS3YunSDK.deleteImage(in.imgMin);
				}
				rs = true;
			}
		}
		return rs;
	}
	
	@Override
	public String localVideoUrl(String videoName) {
		init();
		return fileConfig.FILE_LOCAL_DIR+"video/"+videoName;
	}

	@Override
	public String localImgUrl(String imgName) {
		init();
		return fileConfig.FILE_LOCAL_DIR+"img/"+imgName;
	}

	@Override
	public int storage() {
		init();
		return storageConfig.storagePlatform;
	}

	private void init(){
		if(configManager==null){
			configManager = ConfigManager.getInstance(); 
		}
		if(fileConfig==null){
			fileConfig = configManager.getFileConfig(configService);
		}
		if(storageConfig==null){
			storageConfig = configManager.getStorageConfig();
		}
		if(azureConfig==null){
			azureConfig = configManager.getAzureConfig(configService);
		}
		if(ks3Config==null){
			ks3Config = configManager.getKs3Config(configService);
		}
	}
}
