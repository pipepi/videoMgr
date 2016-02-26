/**
 * 
 */
package com.aepan.sysmgr.model.config;

import java.io.Serializable;

/**
 * 流量统计配置
 * @author lanker
 * 2015年12月23日上午10:35:03
 */
public class FlowConfig implements Serializable {

	private static final long serialVersionUID = 1L;
	public boolean open;
	public String authKey;
	public boolean isOpen() {
		return open;
	}
	public boolean getOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public String getAuthKey() {
		return authKey;
	}
	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}
	
}
