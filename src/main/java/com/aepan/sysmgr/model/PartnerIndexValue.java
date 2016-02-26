/**
 * 
 */
package com.aepan.sysmgr.model;

import java.io.Serializable;

/**
 * 
 * @author rakika
 * 2015年9月26日下午6:19:59
 */
public class PartnerIndexValue implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5681146261916303837L;

	private String index;
	
	private String[] ValueSet;

	/**
	 * @return the index
	 */
	public String getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(String index) {
		this.index = index;
	}

	/**
	 * @return the valueSet
	 */
	public String[] getValueSet() {
		return ValueSet;
	}

	/**
	 * @param valueSet the valueSet to set
	 */
	public void setValueSet(String[] valueSet) {
		ValueSet = valueSet;
	}
}