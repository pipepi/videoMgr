/**
 * 
 */
package com.aepan.sysmgr.model;

import java.io.Serializable;

/**
 * @author rakika
 * 2015年9月13日下午8:59:53
 */
public class PartnerProductValue implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -984409811629436105L;

	private String Id;
	
    private String isPlatform;
    
	private String Name;
	
	private String Selected;

	/**
	 * @return the id
	 */
	public String getId() {
		return Id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		Id = id;
	}

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
	 * @return the selected
	 */
	public String getSelected() {
		return Selected;
	}

	/**
	 * @param selected the selected to set
	 */
	public void setSelected(String selected) {
		Selected = selected;
	}

	/**
	 * @return the isPlatform
	 */
	public String getIsPlatform() {
		return isPlatform;
	}

	/**
	 * @param isPlatform the isPlatform to set
	 */
	public void setIsPlatform(String isPlatform) {
		this.isPlatform = isPlatform;
	}

}
