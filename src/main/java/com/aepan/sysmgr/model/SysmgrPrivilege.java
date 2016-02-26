/**
 * 
 */
package com.aepan.sysmgr.model;

import java.io.Serializable;
import java.util.Date;


/**
 * @author rakika
 * 2015年8月6日上午2:30:34
 */
public class SysmgrPrivilege implements Serializable {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 4067358995808400971L;

	/**
     * This field corresponds to the database column t_sysmgr_privilege.id
     * 权限id
     */
    private Integer id = 0;

    /**
     * This field corresponds to the database column t_sysmgr_privilege.resource_id
     * 资源id
     */
    private Integer resourceId = 0;
    
    private String resourceName;	//资源名称

    /**
     * This field corresponds to the database column t_sysmgr_privilege.operation_id
     * 操作id
     */
    private Integer operationId = 0;
    
    private String operationName;	//操作名称

    /**
     * This field corresponds to the database column t_sysmgr_privilege.role_id
     * 角色id
     */
    private Integer roleId = 0;
    
    private String roleName;	//角色名称

    /**
     * This field corresponds to the database column t_sysmgr_privilege.create_time
     * 创建时间
     */
    private Date createTime = new Date();
    
    private String url;	//资源路径
    	
    private String shortcut;	//操作编码

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
	 * @return the resourceId
	 */
	public Integer getResourceId() {
		return resourceId;
	}

	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * @return the operationId
	 */
	public Integer getOperationId() {
		return operationId;
	}

	/**
	 * @param operationId the operationId to set
	 */
	public void setOperationId(Integer operationId) {
		this.operationId = operationId;
	}

	/**
	 * @return the operationName
	 */
	public String getOperationName() {
		return operationName;
	}

	/**
	 * @param operationName the operationName to set
	 */
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	/**
	 * @return the roleId
	 */
	public Integer getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
	 * @return the shortcut
	 */
	public String getShortcut() {
		return shortcut;
	}

	/**
	 * @param shortcut the shortcut to set
	 */
	public void setShortcut(String shortcut) {
		this.shortcut = shortcut;
	}
}
