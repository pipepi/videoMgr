/**
 * 
 */
package com.aepan.sysmgr.service;

import java.util.List;
import java.util.Map;

import com._21cn.framework.util.PageList;
import com.aepan.sysmgr.model.Resource;

/**
 * 菜单资源
 * @author rakika
 * 2015年6月20日上午11:06:07
 * @param <T>
 */
public interface ResourceService{
	
	public List<Resource> selectResourceByName(String userName);
	
	public PageList<Resource> getList(Map<String, Object> params, int pageNo, int pageSize, boolean doCount);

	public PageList<Resource> getList(Map<String, Object> params, int pageNo, int pageSize);

	public void insert(Resource resource);
	
	public Resource getById(int id);
	
	public void update(Resource resource);
	
	public void deleteResourceByMap(Map<String, Object> params);
	
	public List<Resource> selectResourceNotExist(int roleId);
	
	public List<Integer> selectResourceByRoleId(Integer roleId);
}
