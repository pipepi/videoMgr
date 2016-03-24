/**
 * 
 */
package com.aepan.sysmgr.model.config;

import java.io.Serializable;

/**
 * @author lanker
 * 2015年10月9日下午4:24:02
 */
public class PartnerConfig implements Serializable {

	private static final long serialVersionUID = 1L;
	public String rootPath;//开卖8根路径，用于关联产品图片展示
	
	public String getRootPath() {
		return rootPath;
	}
	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}
	
}
