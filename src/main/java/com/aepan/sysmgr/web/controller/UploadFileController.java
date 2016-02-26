package com.aepan.sysmgr.web.controller;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com._21cn.framework.util.StringUtil;
import com._21cn.framework.web.HttpRequestInfo;
import com.aepan.sysmgr.model.CpFile;
import com.aepan.sysmgr.service.UploadFileService;
import com.aepan.sysmgr.util.AjaxResponseUtil;
import com.sun.imageio.plugins.bmp.BMPImageReader;
import com.sun.imageio.plugins.gif.GIFImageReader;
import com.sun.imageio.plugins.jpeg.JPEGImageReader;
import com.sun.imageio.plugins.png.PNGImageReader;


/**
 * 文件上传
 * @author rakika
 * 2015年8月10日下午6:57:59
 */
@Controller
public class UploadFileController extends DataTableController {

	private static final Logger logger = LoggerFactory.getLogger(UploadFileController.class);
	
    @Autowired
    private UploadFileService uploadFileService;

    /**
     * 
     * @param myfile
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/admin/ajaxUploadFile")
    public String handleFormUpload(@RequestParam("myfile") MultipartFile myfile,
                                   HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html;charset=utf-8");
        HttpRequestInfo reqInfo = new HttpRequestInfo(request);
        int type = reqInfo.getIntParameter("type", -1);
        if (type == -1 ){
        	AjaxResponseUtil.returnData(response, "-1");
            return null;
        }
        try {
            // 没有传批开附件或者长度为0
            if (myfile == null || myfile.getSize() <= 0 || StringUtil.isEmpty(getContentType(myfile.getBytes()))) {
            	AjaxResponseUtil.returnData(response, "-2");
                return null;
            }
            
            // 长度大于200
            if (myfile.getSize() >= 200*1024) {
            	AjaxResponseUtil.returnData(response, "-3");
                return null;
            }
            CpFile cpFile = new CpFile();
            cpFile.setFileName(myfile.getName());
            cpFile.setOrgFileName(myfile.getOriginalFilename());
            cpFile.setType(type);
            boolean ret = uploadFileService.save(cpFile);
            AjaxResponseUtil.returnData(response, ret + "");
        } catch (Exception e) {
            
            AjaxResponseUtil.returnData(response, "-1");
        }
        return null;
    }

    /**
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/ajaxShowPicture")
    public String getPic(HttpServletRequest request, HttpServletResponse response) {
        try {
        	HttpRequestInfo reqInfo = new HttpRequestInfo(request);
            int id = reqInfo.getIntParameter("id", -1);
            if (id == -1) {
                return null;
            }
            response.setContentType("image/jpeg");
            ServletOutputStream out = response.getOutputStream();
            FileInputStream is = uploadFileService.getPic(id);
            int i = 0;
            byte[] buffer = new byte[4096];
            while ((i = is.read(buffer)) != -1) {
                out.write(buffer, 0, i);
            }
            out.flush();
            is.close();
            out.close();
        } catch (Exception e) {
        	logger.error(e.getMessage());
        }
        return null;
    }
    
    /**
     * 获取图片文件类型
     * 
     * @param mapObj
     * @return
     */
    private  String getContentType(byte[] mapObj) {
        String type = "";
        ByteArrayInputStream bais = null;
        MemoryCacheImageInputStream mcis = null;
        try {
            bais = new ByteArrayInputStream(mapObj);
            mcis = new MemoryCacheImageInputStream(bais);
            @SuppressWarnings("rawtypes")
            Iterator itr = ImageIO.getImageReaders(mcis);
            while (itr.hasNext()) {
                ImageReader reader = (ImageReader) itr.next();
                if (reader instanceof GIFImageReader) {
                    type = "gif";
                } else if (reader instanceof JPEGImageReader) {
                    type = "jpeg";
                } else if (reader instanceof PNGImageReader) {
                    type = "png";
                } else if (reader instanceof BMPImageReader) {
                    type = "application/x-bmp";
                }
            }
        }catch(Exception e){
            logger.error(e.getMessage());
        }finally {
            if (bais != null) {
                try {
                    bais.close();
                } catch (IOException ioe) {
                    
                }
            }
            if (mcis != null) {
                try {
                    mcis.close();
                } catch (IOException ioe) {

                }
            }
        }
        return type;
    }
}
