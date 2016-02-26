/**
 * 
 */
package com.aepan.sysmgr.dao;

import java.util.List;
import java.util.Map;

import com._21cn.framework.util.PageList;
import com.aepan.sysmgr.model.Operation;

/**
 * @author rakika
 * 2015年7月11日上午9:21:39
 */
public interface OperationDao {

	public PageList<Operation> selectMapByMap(Map<String, Object> params, int pageNo, int pageSize);
	
	public List<String> selectOperationByRoleId(Integer roleId);
	
	public boolean create(Operation operation);
	
	public Operation getById(int id);
	
	public boolean update(Operation operation);
}
