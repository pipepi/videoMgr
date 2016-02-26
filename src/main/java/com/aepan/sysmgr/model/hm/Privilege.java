/**
 * 
 */
package com.aepan.sysmgr.model.hm;

import java.io.Serializable;

/**
 * @author Doris.zhou 2015年11月17日下午5:49:55
 */
public class Privilege implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int PrivilegeCode;

	private String PrivilegeName;

	private boolean Auth;

	public int getPrivilegeCode() {
		return PrivilegeCode;
	}

	public String getPrivilegeName() {
		return PrivilegeName;
	}

	public boolean isAuth() {
		return Auth;
	}

	public void setPrivilegeCode(int privilegeCode) {
		PrivilegeCode = privilegeCode;
	}

	public void setPrivilegeName(String privilegeName) {
		PrivilegeName = privilegeName;
	}

	public void setAuth(boolean auth) {
		Auth = auth;
	}

	@Override
	public String toString() {
		return "Privilege [PrivilegeCode=" + PrivilegeCode + ", PrivilegeName="
				+ PrivilegeName + ", Auth=" + Auth + "]";
	}

}
