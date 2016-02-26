/**
 * 
 */
package com.aepan.sysmgr.model;

import java.io.Serializable;

/**
 * @author rakika
 * 2015年9月13日下午9:42:11
 */
public class SKUArray implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1592832062799064593L;
	
	private PartnerProductSt data;

	/**
	 * @return the data
	 */
	public PartnerProductSt getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(PartnerProductSt data) {
		this.data = data;
	}

}
