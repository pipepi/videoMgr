/**
 * 
 */
package com.aepan.sysmgr.model;

import java.io.Serializable;
import java.util.List;

/**
 * 资源集合model
 * @author rakika
 * 2015年6月20日上午11:43:07
 */
public class Privileges implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2761235952134070415L;

	private Integer roleId = 0;
	
	private List<Privilege> privilegeList;

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
	 * @return the privilegeList
	 */
	public List<Privilege> getPrivilegeList() {
		return privilegeList;
	}

	/**
	 * @param privilegeList the privilegeList to set
	 */
	public void setPrivilegeList(List<Privilege> privilegeList) {
		this.privilegeList = privilegeList;
	}

	/* 
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{role=" + roleId + ", privilegeList=" + privilegeList + "}";
	}
}
