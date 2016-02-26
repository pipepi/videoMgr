/**
 * 
 */
package com.aepan.sysmgr.util;

import java.io.ByteArrayInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aepan.sysmgr.azure.AzureSDK;
import com.aepan.sysmgr.azure.UploadFileRps;
import com.aepan.sysmgr.model.config.FileConfig;


/**
 * @author lanker
 * 2015年8月4日下午12:36:54
 */
public class FileUtilAzure {
	public static Logger logger = LoggerFactory.getLogger(FileUtilAzure.class);
	/*@SuppressWarnings("unchecked")
	public static UploadFileRps uploadVideo(HttpServletRequest req,FileConfig fileConfig){
		//将文件存储到磁盘
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(fileConfig.MAX_VIDEO_SIZE);
		ProgressListenerImpl getBarListener = new ProgressListenerImpl(req);
		ServletFileUpload upload = new ServletFileUpload(factory);
		//upload.setProgressListener(getBarListener);
		upload.setHeaderEncoding("utf-8");
		upload.setSizeMax(fileConfig.MAX_VIDEO_SIZE);
		try {
			List<FileItem> items = upload.parseRequest(req);
			Iterator<FileItem> iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (item.isFormField()) {
					logger.warn("ignore form item:" + item.getName());
				}else{
					long totalSize = item.getSize();
					int random = new Random(5).nextInt(5);
					random += 5;
					long readSize = totalSize/100*random;
					getBarListener.update(readSize, totalSize, 1);
					String fileName = item.getName();
					int lastIndex = fileName.lastIndexOf('\\');
					if (lastIndex != -1) {
						// windows client/IE
						fileName = fileName.substring(lastIndex + 1, fileName
								.length());
					}
					logger.info("processing uploaded file:" + item.getName());
					InputStream is=item.getInputStream();
					UploadFileRps rs = AzureSDK.upVideo(fileName, is);
					getBarListener.update(totalSize, totalSize, 1);
					is.close();
					return rs;
				}
			}
		} catch (FileUploadException | IOException e) {
			logger.warn("ignore form item:" + e.getMessage());
		}
		return null;
	}*/
	public static boolean delVideo(String uploadAssetId,String encodeAssetId){
		return AzureSDK.delVideo(uploadAssetId, encodeAssetId);
	}
	public static UploadFileRps uploadImg(byte[] content,String name,FileConfig fileConfig){
		UploadFileRps rs = AzureSDK.upImg(fileConfig.IMG_AZURE_DIR, name, new ByteArrayInputStream(content) , content.length);
		return rs;
	}
	public static boolean delImg(FileConfig c,String fileName){
		return AzureSDK.delImg(c.IMG_AZURE_DIR, fileName);
	}
}
