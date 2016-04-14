/**
 * 
 */
package com.aepan.sysmgr.model.config;

/**
 * 外网cdn，浏览器缓存；js，css，swf播放器等资源，版本发布时不及时刷新。解决方法：请求添加版本号。
 * @author lanker
 * 2016年3月29日下午2:10:25
 */
public class VersionConfig {
	public String version;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
}
