package com.aepan.sysmgr.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件
 * @author rakika
 * 2015年8月10日下午7:21:00
 */
public class CpFile  implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -309613820241354220L;
	
	private int id;
	
	private String fileName;
	
    private String orgFileName;
    
	private int type;
	
	private Date createTime;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the orgFileName
	 */
	public String getOrgFileName() {
		return orgFileName;
	}

	/**
	 * @param orgFileName the orgFileName to set
	 */
	public void setOrgFileName(String orgFileName) {
		this.orgFileName = orgFileName;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
