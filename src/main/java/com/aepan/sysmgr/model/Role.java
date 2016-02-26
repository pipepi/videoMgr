/**
 * 
 */
package com.aepan.sysmgr.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.stereotype.Repository;

/**
 * 角色model
 * @author rakika
 * 2015年6月21日下午8:07:51
 */
@Repository
public class Role implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8887981836275090914L;
	
	private int id;
	//角色名字
	private String name; 
	//角色类型
	private int type;
	
	private Date createTime;

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

}