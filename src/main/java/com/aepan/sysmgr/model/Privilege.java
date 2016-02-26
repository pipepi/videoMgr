/**
 * 
 */
package com.aepan.sysmgr.model;

import java.io.Serializable;
import java.util.List;

/**
 * 资源model
 * @author rakika
 * 2015年6月20日上午11:32:35
 */
public class Privilege implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7301159751475761428L;
	
	private Integer resourceId = 0;
	
	private List<Integer> operationIds;

	/**
	 * @return the resourceId
	 */
	public Integer getResourceId() {
		return resourceId;
	}

	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * @return the operationIds
	 */
	public List<Integer> getOperationIds() {
		return operationIds;
	}

	/**
	 * @param operationIds the operationIds to set
	 */
	public void setOperationIds(List<Integer> operationIds) {
		this.operationIds = operationIds;
	}

	/* 
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{resourceId:" + resourceId + ", "
				+ "operationIds:" + operationIds + "}";
	}
	
}
