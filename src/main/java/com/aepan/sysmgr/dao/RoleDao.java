/**
 * 
 */
package com.aepan.sysmgr.dao;

import java.util.Map;

import com._21cn.framework.util.PageList;
import com.aepan.sysmgr.model.Role;

/**
 * 角色Dao
 * @author rakika
 * 2015年6月21日下午8:01:56
 */
public interface RoleDao {

	public PageList<Role> getList(Map<String, Object> params, int pageNo, int pageSize);
	
	public Role getById(int id);
	//创建角色
    public boolean create(Role role);
    
    public boolean update(Role role);
}
