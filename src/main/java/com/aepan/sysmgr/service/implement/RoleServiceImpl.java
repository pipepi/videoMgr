/**
 * 
 */
package com.aepan.sysmgr.service.implement;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com._21cn.framework.util.PageList;
import com.aepan.sysmgr.dao.RoleDao;
import com.aepan.sysmgr.model.Role;
import com.aepan.sysmgr.service.RoleService;

/**
 * 
 * @author rakika
 * 2015年7月11日上午10:30:16
 */
@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	RoleDao roleDao;

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.RoleService#getList(java.util.Map, int, int)
	 */
	@Override
	public PageList<Role> getList(Map<String, Object> params, int pageNo,
			int pageSize) {
		// TODO Auto-generated method stub
		return roleDao.getList(params, pageNo, pageSize);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.RoleService#getById(int)
	 */
	@Override
	public Role getById(int id) {
		// TODO Auto-generated method stub
		return roleDao.getById(id);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.RoleService#create(com._aepan.sysmgr.model.Role)
	 */
	@Override
	public boolean create(Role role) {
		// TODO Auto-generated method stub
		return roleDao.create(role);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.RoleService#update(com._aepan.sysmgr.model.Role)
	 */
	@Override
	public boolean update(Role role) {
		// TODO Auto-generated method stub
		return roleDao.update(role);
	}


}
