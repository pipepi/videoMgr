/**
 * 
 */
package com.aepan.sysmgr.model.json;

import java.io.Serializable;

/**
 * @author Administrator
 * 2015年9月28日上午11:37:48
 */
public class Value implements Serializable {

	private static final long serialVersionUID = 1L;
	private String Id;
	private boolean isPlatform;
	private String Name;
	private boolean Selected;
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public boolean isPlatform() {
		return isPlatform;
	}
	public void setPlatform(boolean isPlatform) {
		this.isPlatform = isPlatform;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public boolean isSelected() {
		return Selected;
	}
	public void setSelected(boolean selected) {
		Selected = selected;
	}
	
}
