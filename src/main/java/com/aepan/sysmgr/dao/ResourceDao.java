/**
 * 
 */
package com.aepan.sysmgr.dao;

import java.util.List;
import java.util.Map;

import com._21cn.framework.util.PageList;
import com.aepan.sysmgr.model.Resource;


/**
 * 资源DAO
 * @author rakika
 * 2015年6月20日上午9:39:29
 */
public interface ResourceDao {

	public List<Resource> selectResourceByName(String userName);
	
	public PageList<Resource> selectMapByMap(Map<String, Object> params, int pageNo, int pageSize, boolean doCount);
	
	public PageList<Resource> selectMapByMap(Map<String, Object> params, int pageNo, int pageSize);
	
	public void insert(Resource resource);
	
	public Resource getById(int id);
	
	public void update(Resource resource);
	
	public void deleteResourceByMap(Map<String, Object> params);
	
	public List<Resource> selectResourceNotExist(int roleId);
	
	public List<Integer> selectResourceByRoleId(Integer roleId);
}
