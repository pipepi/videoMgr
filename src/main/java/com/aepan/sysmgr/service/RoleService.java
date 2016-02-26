/**
 * 
 */
package com.aepan.sysmgr.service;

import java.util.Map;

import com._21cn.framework.util.PageList;
import com.aepan.sysmgr.model.Role;

/**
 * @author rakika
 * 2015年7月11日上午10:29:17
 */
public interface RoleService {

	public PageList<Role> getList(Map<String, Object> params, int pageNo, int pageSize);
	
	public Role getById(int id);
	//创建角色
    public boolean create(Role role);
    
    public boolean update(Role role);
}
