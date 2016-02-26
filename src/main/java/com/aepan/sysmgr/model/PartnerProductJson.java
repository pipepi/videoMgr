/**
 * 
 */
package com.aepan.sysmgr.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author rakika
 * 2015年9月13日下午8:59:53
 */
public class PartnerProductJson implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8306057060137101029L;

	private String Name;
	
    private String SpecId;
    
    private List<PartnerProductValue> Values;

	/**
	 * @return the name
	 */
	public String getName() {
		return Name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		Name = name;
	}

	/**
	 * @return the specId
	 */
	public String getSpecId() {
		return SpecId;
	}

	/**
	 * @param specId the specId to set
	 */
	public void setSpecId(String specId) {
		SpecId = specId;
	}

	/**
	 * @return the values
	 */
	public List<PartnerProductValue> getValues() {
		return Values;
	}

	/**
	 * @param values the values to set
	 */
	public void setValues(List<PartnerProductValue> values) {
		Values = values;
	}
}
