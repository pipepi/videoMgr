/**
 * 
 */
package com.aepan.sysmgr.service;

import java.util.List;
import java.util.Map;

import com._21cn.framework.util.PageList;
import com.aepan.sysmgr.model.Operation;

/**
 * 
 * @author rakika
 * 2015年7月6日下午5:23:17
 */
public interface OperationService {

	public PageList<Operation> getList(Map<String, Object> params, int pageNo, int pageSize);
	
	public List<String> selectOperationByRoleId(Integer roleId);
	
	public boolean create(Operation operation);
	
	public Operation getById(int id);
	
	public boolean update(Operation operation);
}
