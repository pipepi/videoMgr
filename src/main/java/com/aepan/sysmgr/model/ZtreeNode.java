/**
 * 
 */
package com.aepan.sysmgr.model;

import java.io.Serializable;

/**
 * 树model
 * @author rakika
 * 2015年6月20日上午11:17:04
 */
public class ZtreeNode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8357226651224524388L;
	
	private Integer id = 0;	//当前节点id
	
	private Integer pId = 0;	//父类id
	
	private String name;	//资源名称
	
	private String url;	//连接地址
	
	private String target = "iframepage";	//iframe的name属性
	
	private boolean open = true;	//折叠或打开(默认打开)
	
	private boolean isParent = false;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the pId
	 */
	public Integer getpId() {
		return pId;
	}

	/**
	 * @param pId the pId to set
	 */
	public void setpId(Integer pId) {
		this.pId = pId;
		if(pId == 0){
			this.isParent = true;
		}
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
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * @return the open
	 */
	public boolean isOpen() {
		return open;
	}

	/**
	 * @param open the open to set
	 */
	public void setOpen(boolean open) {
		this.open = open;
	}

	/**
	 * @return the isParent
	 */
	public boolean isParent() {
		return isParent;
	}

	/**
	 * @param isParent the isParent to set
	 */
	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}
}
