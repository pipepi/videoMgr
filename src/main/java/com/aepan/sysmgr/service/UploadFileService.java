package com.aepan.sysmgr.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.aepan.sysmgr.model.CpFile;

/**
 * 上传
 * @author rakika
 * 2015年8月10日下午7:02:50
 */
public interface UploadFileService{
    
    public boolean save(CpFile file);
    
    public FileInputStream getPic(int id) throws FileNotFoundException;
    
    public String getUrl(int id);
}
