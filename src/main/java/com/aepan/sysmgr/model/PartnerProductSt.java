/**
 * 
 */
package com.aepan.sysmgr.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author rakika
 * 2015年9月15日下午7:41:02
 */
public class PartnerProductSt implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -786422651354212258L;

	private List<PartnerProductJson> json;
	
//	private PartnerProductTableData tableData;

	/**
	 * @return the json
	 */
	public List<PartnerProductJson> getJson() {
		return json;
	}

	/**
	 * @param json the json to set
	 */
	public void setJson(List<PartnerProductJson> json) {
		this.json = json;
	}

	/**
	 * @return the tableData
	 */
/*	public PartnerProductTableData getTableData() {
		return tableData;
	}

	*//**
	 * @param tableData the tableData to set
	 *//*
	public void setTableData(PartnerProductTableData tableData) {
		this.tableData = tableData;
	}*/	
}
