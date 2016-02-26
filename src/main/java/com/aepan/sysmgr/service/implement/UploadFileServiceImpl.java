/**
 * 
 */
package com.aepan.sysmgr.service.implement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com._21cn.framework.util.StringUtil;
import com._21cn.framework.web.filter.SpringWebContext;
import com.aepan.sysmgr.dao.CpFileDao;
import com.aepan.sysmgr.model.CpFile;
import com.aepan.sysmgr.service.UploadFileService;
import com.aepan.sysmgr.util.Constants;

/**
 * 上传文件
 * @author rakika
 * 2015年8月10日下午10:25:19
 */
@Service
public class UploadFileServiceImpl implements UploadFileService {
	
	@Autowired
	CpFileDao cpFileDao;

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.UploadFileService#save(org.springframework.web.multipart.MultipartFile, com._aepan.sysmgr.model.File)
	 */
	@Override
	public boolean save(CpFile file) {
		return cpFileDao.save(file);
	}
	
	public int save(MultipartFile file, String cpId, int type) 
    		throws IllegalStateException, IOException{
		
        String path = Constants.FILE_IMG_UPLOAD_PATH;
        String orgFileName = file.getOriginalFilename();
        String uploadId = Long.toString(new Date().getTime());
        String fileName;
        
        if (StringUtil.isEmpty(orgFileName)) {
            fileName = null;
        } else {
            if (orgFileName.lastIndexOf(".") > -1) {
                fileName = uploadId
                        + orgFileName.substring(orgFileName
                                .lastIndexOf("."));
            } else {
                fileName = uploadId + ".no";
            }
            
            File targetFile = new File(path, fileName);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            // 保存
            file.transferTo(targetFile);
            
            //文件信息表保存
            CpFile f = new CpFile();
            f.setFileName(fileName);
            f.setOrgFileName(orgFileName);
            f.setType(type);
            save(f);
            return f.getId();
        }    
        return 0;
    }
    
    /**
     * 获取图片流
     * @param id
     * @return
     * @throws FileNotFoundException
     */
    public FileInputStream getPic(int id) throws FileNotFoundException{
        CpFile cp = cpFileDao.getById(id);
        String path = Constants.FILE_IMG_UPLOAD_PATH;
        File targetFile = new File(path, cp.getFileName());
        return new FileInputStream(targetFile);
    }
    /**
     * 
     * @param id
     * @return
     */
    public String getUrl(int id){
    	CpFile cp = cpFileDao.getById(id);
    	return SpringWebContext.getServletRequest() == null ? 
    			"/" : SpringWebContext.getServletRequest().getContextPath() + "/picMapping/cpUpload/" + cp.getFileName();
    }

}
