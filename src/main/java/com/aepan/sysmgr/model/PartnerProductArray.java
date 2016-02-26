/**
 * 
 */
package com.aepan.sysmgr.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author rakika
 * 2015年9月28日上午10:29:10
 */
public class PartnerProductArray implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<PartnerProduct> rows;

	/**
	 * @return the rows
	 */
	public List<PartnerProduct> getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(List<PartnerProduct> rows) {
		this.rows = rows;
	}

}