package com.aepan.sysmgr.util;

import java.io.Serializable;
import java.util.List;

import com._21cn.framework.util.PageList;

/**
 * 
 * @author rakika
 * 2015年6月28日下午5:22:19
 */
public class DataTableReturnObject implements Serializable {
	
	private static final long serialVersionUID = 8727312473506976637L;
	
	public int iDisplayStart;
	public int iDisplayLength;
	
	public long iTotalRecords;
	public long iTotalDisplayRecords;
	public String sEcho;
	public List<?> aaData;

	public long getiTotalRecords() {
		return iTotalRecords;
	}
	public void setiTotalRecords(long iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}
	public long getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}
	public void setiTotalDisplayRecords(long iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}
	public String getsEcho() {
		return sEcho;
	}
	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}
	public List<?> getAaData() {
		return aaData;
	}
	public void setAaData(List<?> aaData) {
		this.aaData = aaData;
	}
	/**
	 * @return the iDisplayStart
	 */
	public int getiDisplayStart() {
		return iDisplayStart;
	}
	/**
	 * @param iDisplayStart the iDisplayStart to set
	 */
	public void setiDisplayStart(int iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}
	/**
	 * @return the iDisplayLength
	 */
	public int getiDisplayLength() {
		return iDisplayLength;
	}
	/**
	 * @param iDisplayLength the iDisplayLength to set
	 */
	public void setiDisplayLength(int iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}
	public void setResult(PageList<?> pageList, String sEcho){
		setsEcho(sEcho);
		setAaData(pageList);
		System.out.println("size:" + pageList.size());
		setiTotalRecords(pageList.size());
		System.out.println("rowCount:" + pageList.getPageTurn().getRowCount());
		setiTotalDisplayRecords(pageList.getPageTurn().getRowCount());
	}
}
