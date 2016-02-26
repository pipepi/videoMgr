/**
 * 
 */
package com.aepan.sysmgr.model.json;

import java.io.Serializable;

/**
 * @author Administrator
 * 2015年9月28日上午11:55:19
 */
public class Root implements Serializable {

	private static final long serialVersionUID = 1L;
	private Data data;
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	
}
