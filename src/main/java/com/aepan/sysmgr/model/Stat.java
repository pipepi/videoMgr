/**
 * 
 */
package com.aepan.sysmgr.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author rakika
 * 2015年9月12日下午3:58:10
 */
public class Stat implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1881042555346621019L;

	private int id;
	
	private String name;
	
	private int type;
	
	private String paramId;
	
	private String content;
	
	private String ext;
	
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the paramId
	 */
	public String getParamId() {
		return paramId;
	}

	/**
	 * @param paramId the paramId to set
	 */
	public void setParamId(String paramId) {
		this.paramId = paramId;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the ext
	 */
	public String getExt() {
		return ext;
	}

	/**
	 * @param ext the ext to set
	 */
	public void setExt(String ext) {
		this.ext = ext;
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
