/**
 * 
 */
package com.aepan.sysmgr.model.hm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Doris.zhou 2015年9月28日下午6:46:00
 */
public class PartnerProductPage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 总产品数
	 */
	private int total;
	/**
	 * 本页产品数
	 */
	private List<PartnerProduct> rows= new ArrayList<PartnerProduct>();

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<PartnerProduct> getRows() {
		return rows;
	}

	public void setRows(List<PartnerProduct> rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "PartnerProductPage [total=" + total + ", rows=" + rows + "]";
	}

}
