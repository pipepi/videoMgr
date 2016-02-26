/**
 * 
 */
package com.aepan.sysmgr.model.json;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 * 2015年9月28日上午11:41:17
 */
public class Json implements Serializable {

	private static final long serialVersionUID = 1L;
	private String Color;
	private int SpecId;
	private List<Value> Values;
	public String getColor() {
		return Color;
	}
	public void setColor(String color) {
		Color = color;
	}
	public int getSpecId() {
		return SpecId;
	}
	public void setSpecId(int specId) {
		SpecId = specId;
	}
	public List<Value> getValues() {
		return Values;
	}
	public void setValues(List<Value> values) {
		Values = values;
	}
	
}
