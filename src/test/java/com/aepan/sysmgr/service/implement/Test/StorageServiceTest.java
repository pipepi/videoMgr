/**
 * 
 */
package com.aepan.sysmgr.service.implement.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aepan.sysmgr.azure.UploadFileInput;
import com.aepan.sysmgr.azure.UploadFileRps;
import com.aepan.sysmgr.service.StorageService;
import com.aepan.sysmgr.util.FileUtil;


/**
 * @author Administrator
 * 2016年3月29日上午10:57:24
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/applicationContext.xml","classpath*:/spring-mvc.xml"})
public class StorageServiceTest {
	private StorageService storageService;
	@Test
	public void uploadImg() throws IOException{
		String srcImgId = "srcImgId";
		File f = new File(storageService.localImgUrl(srcImgId));
		byte[] maxImgContent =  FileUtil.file2Byte(f);
		String maxImg = "max_"+srcImgId;
		//保存本地替换成保存微软云
		UploadFileRps maxrs = storageService.uploadImg(new UploadFileInput(maxImg, maxImgContent,false));
	}
}
