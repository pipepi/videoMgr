/**
 * 
 */
package com.aepan.sysmgr.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.im4java.core.ConvertCmd;
import org.im4java.core.GMOperation;
import org.im4java.core.IMOperation;
import org.im4java.process.Pipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aepan.sysmgr.model.config.FileConfig;
import com.aepan.sysmgr.web.api.pay.MD5Util;
import com.sun.imageio.plugins.bmp.BMPImageReader;
import com.sun.imageio.plugins.gif.GIFImageReader;
import com.sun.imageio.plugins.jpeg.JPEGImageReader;
import com.sun.imageio.plugins.png.PNGImageReader;


/**
 * @author lanker
 * 2015年8月4日下午12:36:54
 */
public class FileUtil {
	public static Logger logger = LoggerFactory.getLogger(FileUtil.class);
	@SuppressWarnings("unchecked")
	public static File initVideo(HttpServletRequest req,FileConfig fileConfig){
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(fileConfig.MAX_VIDEO_SIZE);
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("utf-8");
		upload.setSizeMax(fileConfig.MAX_VIDEO_SIZE);
		try{
			List<FileItem> items = upload.parseRequest(req);
			Iterator<FileItem> iter = items.iterator();
			while(iter.hasNext()){
				FileItem item = iter.next();
				if(item.isFormField()){
					logger.warn("ignore form item:" + item.getName());
				}else{
						String fileName = item.getName();
						//判断是否为ie浏览器上传
						int lastIndex = fileName.lastIndexOf("\\");
						if(lastIndex!=-1){
							// windows client/IE
							fileName = fileName.substring(lastIndex + 1, fileName
									.length());
						}
						logger.info("processing uploaded video:" + item.getName());
						String typeStr = fileName.substring(fileName.lastIndexOf('.'),fileName.length());
						fileName = MD5Util.MD5Encode(fileName, "UTF-8")+typeStr;
						InputStream is=item.getInputStream();
						BufferedInputStream bis = new BufferedInputStream(is, 100*1000);
						File target = new File(fileConfig.FILE_LOCAL_DIR+"video/"+fileName);
						File dir = target.getParentFile();
						if(dir!=null&&!dir.exists()){
							dir.mkdirs();
						}
						if(!target.exists()){
							target.createNewFile();
						}
						logger.info("processing uploaded video target path:" + target.getAbsolutePath());
						FileOutputStream fos = new FileOutputStream(target);
						BufferedOutputStream bos = new BufferedOutputStream(fos, 100*1000);
						byte input[] = new byte[50*1000];
						while (bis.read(input)!=-1) {
							bos.write(input);
						}
						bos.close();
						fos.close();
						bis.close();
						is.close();
						
						return target;
					}
			}
		}catch(IOException e){
			logger.warn("ignore form item:" + e.getMessage());
		} catch (FileUploadException e) {
			logger.warn("ignore form item:" + e.getMessage());
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public static File initImg(HttpServletRequest req,FileConfig fileConfig){
		//将文件存储到磁盘
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(fileConfig.MAX_IMGS_SIZE);
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("utf-8");
		upload.setSizeMax(fileConfig.MAX_IMGS_SIZE);
		try {
			List<FileItem> items = upload.parseRequest(req);
			Iterator<FileItem> iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (item.isFormField()) {
					logger.warn("ignore form item:" + item.getName());
				}else{
					String fileName = item.getName();
					int lastIndex = fileName.lastIndexOf('\\');
					if (lastIndex != -1) {
						// windows client/IE
						fileName = fileName.substring(lastIndex + 1, fileName
								.length());
					}
					logger.info("processing uploaded file:" + item.getName());
					String typeStr = fileName.substring(fileName.lastIndexOf('.'),fileName.length());
					fileName = MD5Util.MD5Encode(fileName, "UTF-8")+typeStr;
					InputStream is=item.getInputStream();
					BufferedInputStream bis = new BufferedInputStream(is, 100*1000);
					File target = new File(fileConfig.FILE_LOCAL_DIR+"img/"+fileName);
					File dir = target.getParentFile();
					if(dir!=null&&!dir.exists()){
						dir.mkdirs();
					}
					if(!target.exists()){
						target.createNewFile();
					}
					logger.info("processing uploaded file target path:" + target.getAbsolutePath());
					FileOutputStream fos = new FileOutputStream(target);
					BufferedOutputStream bos = new BufferedOutputStream(fos, 100*1000);
					byte input[] = new byte[50*1000];
					while (bis.read(input)!=-1) {
						bos.write(input);
					}
					bos.close();
					fos.close();
					bis.close();
					is.close();
					
					return target;
				}
			}
		} catch (FileUploadException fe) {
			logger.warn("ignore form item:" + fe.getMessage());
		} catch (IOException e){
		    logger.warn("ignore form item:" + e.getMessage());
	    }
		return null;
	}
	public static byte[] file2Byte(File f){
		byte[] buffer = null;
		try {
			FileInputStream fis = new FileInputStream(f);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while((n = fis.read(b))!=-1){
				bos.write(b,0,n);
			}
			bos.close();
			fis.close();
			buffer = bos.toByteArray();
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
		return buffer;
	}
	public static File bytes2File(byte[] content,String name,FileConfig fileConfig){
		File target = new File(fileConfig.FILE_LOCAL_DIR+"img/"+name);
		File dir = target.getParentFile();
		if(dir!=null&&!dir.exists()){
			dir.mkdirs();
		}
		try {
			if(!target.exists()){
					target.createNewFile();
			}
			logger.info("processing uploaded file target path:" + target.getAbsolutePath());
			FileOutputStream fos = new FileOutputStream(target);
			BufferedOutputStream bos = new BufferedOutputStream(fos, 100*1000);
			bos.write(content);
			bos.close();
			fos.close();
		} catch (IOException e) {
			logger.info(e.getMessage(),e);
		}
		return target;
	}
	public static boolean remove(String url){
		if(url!=null){
			File target = new File(url);
			if(target!=null&&target.exists()&&target.isFile()){
				target.delete();
				return true;
			}
		}
		return false;
	}
	public static String getImageType(File f) {
		String type = "";
		ByteArrayInputStream bais = null;
		MemoryCacheImageInputStream mcis = null;
		try {
			mcis = new MemoryCacheImageInputStream(new FileInputStream(f));
			Iterator<ImageReader> itr = ImageIO.getImageReaders(mcis);
			while (itr.hasNext()) {
				ImageReader reader = (ImageReader) itr.next();
				if (reader instanceof GIFImageReader)
					type = "gif";
				else if (reader instanceof JPEGImageReader)
					type = "jpg";
				else if (reader instanceof PNGImageReader)
					type = "png";
				else if (reader instanceof BMPImageReader)
					type = "bmp";
			}
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(),e);
		} finally {
			if (bais != null) {
				try {
					bais.close();
				} catch (IOException ioe) {
				}
			}
			if (mcis != null)
				try {
					mcis.close();
				} catch (IOException ioe) {
				}
		}
		return type;
	}	
	/**
	 * 裁剪图片
	 * @param srcImage
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param imageType
	 * @return
	 * @throws Exception
	 */
	public static byte[] cropImage(BufferedImage srcImage, int x, int y,
			int width, int height, String imageType,FileConfig fileConfig) throws Exception {
		GMOperation op = new GMOperation();
		op.addImage();

		op.crop(Integer.valueOf(width), Integer.valueOf(height),
				Integer.valueOf(x), Integer.valueOf(y));

		String raw = "";

		raw = width + "x" + height;
		op.addRawArgs(new String[] { "-resize", raw });
		op.addRawArgs(new String[] { "-quality", "95" });
		op.addRawArgs(new String[] { "+profile", "*" });
		op.addImage(new String[] { "jpg:-" });

		ConvertCmd convert = new ConvertCmd(true);
		//logger.debug("ConfigManager.getInstance().fileConfig.IM4JAVA_TOOLPATH = "+ConfigManager.getInstance().fileConfig.IM4JAVA_TOOLPATH);
		//convert.setSearchPath(fileConfig.IM4JAVA_TOOLPATH);
		ByteArrayOutputStream os2 = new ByteArrayOutputStream();
		Pipe pipeOut = new Pipe(null, os2);
		convert.setOutputConsumer(pipeOut);

		convert.run(op, new Object[] { srcImage });

		return os2.toByteArray();
	}
	/**
	 *按指定宽度缩放图片 
	 * @param srcImage
	 * @param cutWidth
	 * @param imageType
	 * @return
	 * @throws Exception
	 */
	public static byte[] scaleImage(BufferedImage srcImage, int cutWidth,
			String imageType,FileConfig fileConfig) throws Exception {
		int width = srcImage.getWidth();
		int height = srcImage.getHeight();
		height = (cutWidth < width) ? (int) (height
				* Double.valueOf(cutWidth).doubleValue() / Double
				.valueOf(width).doubleValue()) : height;

		width = (cutWidth < width) ? cutWidth : width;

		IMOperation op = new IMOperation();
		op.addImage();
		String raw = "";

		raw = width + "x" + height;
		op.addRawArgs(new String[] { "-resize", raw });
		op.addRawArgs(new String[] { "-quality", "95" });
		op.addRawArgs(new String[] { "+profile", "*" });
		op.addImage(new String[] { imageType + ":-" });

		ConvertCmd convert = new ConvertCmd(true);
		//logger.debug("ConfigManager.getInstance().fileConfig.IM4JAVA_TOOLPATH = "+ConfigManager.getInstance().fileConfig.IM4JAVA_TOOLPATH);
		//convert.setSearchPath(fileConfig.IM4JAVA_TOOLPATH);
		ByteArrayOutputStream os2 = new ByteArrayOutputStream();
		Pipe pipeOut = new Pipe(null, os2);
		convert.setOutputConsumer(pipeOut);

		convert.run(op, new Object[] { srcImage });

		return os2.toByteArray();
	}
}
