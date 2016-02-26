/**
 * 
 */
package com.aepan.sysmgr.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author rakika
 * 2015年10月6日下午5:40:01
 */
public class Logistics implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1102405055570454909L;

	private String nu;
	
	private String message;
	
	private String companytype;
	
	private String ischeck;
	
	private String com;
	
	private String updatetime;
	
	private String status;
	
	private String condition;
	
	private String codenumber;
	
	private List<LogisticsData> data;
	
	private String state;

	/**
	 * @return the nu
	 */
	public String getNu() {
		return nu;
	}

	/**
	 * @param nu the nu to set
	 */
	public void setNu(String nu) {
		this.nu = nu;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the companytype
	 */
	public String getCompanytype() {
		return companytype;
	}

	/**
	 * @param companytype the companytype to set
	 */
	public void setCompanytype(String companytype) {
		this.companytype = companytype;
	}

	/**
	 * @return the ischeck
	 */
	public String getIscheck() {
		return ischeck;
	}

	/**
	 * @param ischeck the ischeck to set
	 */
	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}

	/**
	 * @return the com
	 */
	public String getCom() {
		return com;
	}

	/**
	 * @param com the com to set
	 */
	public void setCom(String com) {
		this.com = com;
	}

	/**
	 * @return the updatetime
	 */
	public String getUpdatetime() {
		return updatetime;
	}

	/**
	 * @param updatetime the updatetime to set
	 */
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the condition
	 */
	public String getCondition() {
		return condition;
	}

	/**
	 * @param condition the condition to set
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}

	/**
	 * @return the codenumber
	 */
	public String getCodenumber() {
		return codenumber;
	}

	/**
	 * @param codenumber the codenumber to set
	 */
	public void setCodenumber(String codenumber) {
		this.codenumber = codenumber;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the data
	 */
	public List<LogisticsData> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(List<LogisticsData> data) {
		this.data = data;
	}
}
