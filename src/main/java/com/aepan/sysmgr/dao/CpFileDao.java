/**
 * 
 */
package com.aepan.sysmgr.dao;

import com.aepan.sysmgr.model.CpFile;

/**
 * 文件Dao
 * @author rakika
 * 2015年8月10日下午9:53:19
 */
public interface CpFileDao {
	//保存
    public boolean save(CpFile file);
    //根据ID获取file
    public CpFile getById(int id);
}
