/**
 * 
 */
package com.aepan.sysmgr.model;

import java.io.Serializable;

/**
 * @author rakika
 * 2015年10月6日下午5:43:54
 */
public class LogisticsData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9018264067953841462L;

	private String time;
	
	private String location;
	
	private String context;
	
	private String ftime;

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the context
	 */
	public String getContext() {
		return context;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(String context) {
		this.context = context;
	}

	/**
	 * @return the ftime
	 */
	public String getFtime() {
		return ftime;
	}

	/**
	 * @param ftime the ftime to set
	 */
	public void setFtime(String ftime) {
		this.ftime = ftime;
	}
}
