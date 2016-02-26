/**
 * 
 */
package com.aepan.sysmgr.model.json;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 * 2015年9月28日上午11:44:11
 */
public class Data implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Json> json;
	private TableData tableData;
	public List<Json> getJson() {
		return json;
	}
	public void setJson(List<Json> json) {
		this.json = json;
	}
	public TableData getTableData() {
		return tableData;
	}
	public void setTableData(TableData tableData) {
		this.tableData = tableData;
	}
	
}
