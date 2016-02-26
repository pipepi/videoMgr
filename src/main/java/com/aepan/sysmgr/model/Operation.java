/**
 * 
 */
package com.aepan.sysmgr.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作model
 * @author rakika
 * 2015年7月6日下午5:35:07
 */
public class Operation implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1810691307018332720L;

	private int id;
	
	private int shortcut;
	
	private String name;
	
	private Date createTime;

	/**
	 * @return the shortcut
	 */
	public int getShortcut() {
		return shortcut;
	}

	/**
	 * @param shortcut the shortcut to set
	 */
	public void setShortcut(int shortcut) {
		this.shortcut = shortcut;
	}
	
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
