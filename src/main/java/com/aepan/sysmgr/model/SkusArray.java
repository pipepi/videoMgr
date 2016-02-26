/**
 * 
 */
package com.aepan.sysmgr.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author rakika
 * 2015年10月4日下午11:37:40
 */
public class SkusArray implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5425977541694453115L;
	
	private List<SkusInfo> Skus;

	/**
	 * @return the skus
	 */
	public List<SkusInfo> getSkus() {
		return Skus;
	}

	/**
	 * @param skus the skus to set
	 */
	public void setSkus(List<SkusInfo> skus) {
		this.Skus = skus;
	}
}
