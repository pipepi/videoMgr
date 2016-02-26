/**
 * 
 */
package com.aepan.sysmgr.dao;

import java.util.List;
import java.util.Map;

import com._21cn.framework.util.PageList;
import com.aepan.sysmgr.model.MgrPrivilege;

/**
 * 权限Dao
 * @author rakika
 * 2015年6月20日上午11:27:50
 */
public interface PrivilegeDao {

	public List<MgrPrivilege> selectPermissionByUsername(String userName);
	
	public PageList<MgrPrivilege> getList(Map<String, Object> params, int pageNo, int pageSize);
	
	public void batchUpdatePrivilege(int batchSize, List<MgrPrivilege> batchList, Integer roleId);
	
	public void deleteByRoleId(Integer roleId);
}
