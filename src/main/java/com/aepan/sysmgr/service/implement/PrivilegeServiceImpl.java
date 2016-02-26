/**
 * 
 */
package com.aepan.sysmgr.service.implement;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com._21cn.framework.util.PageList;
import com.aepan.sysmgr.dao.PrivilegeDao;
import com.aepan.sysmgr.model.MgrPrivilege;
import com.aepan.sysmgr.service.PrivilegeService;

/**
 * 权限实现类
 * @author rakika
 * 2015年6月20日下午1:54:44
 */
@Service
public class PrivilegeServiceImpl implements PrivilegeService {

	@Autowired
	private PrivilegeDao privilegeDao;

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.PrivilegeService#selectPermissionByUsername(java.lang.String)
	 */
	@Override
	public List<MgrPrivilege> selectPermissionByUsername(
			String userName) {
		return privilegeDao.selectPermissionByUsername(userName);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.PrivilegeService#getList(java.util.Map, int, int)
	 */
	@Override
	public PageList<MgrPrivilege> getList(Map<String, Object> params,
			int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		return privilegeDao.getList(params, pageNo, pageSize);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.PrivilegeService#batchUpdatePrivilege(int, java.util.List, java.lang.Integer)
	 */
	@Override
	public void batchUpdatePrivilege(int batchSize,
			List<MgrPrivilege> batchList, Integer roleId) {
		//先删除roleId 数据，再插入
		//先删除roleId的数据，再插入数据库
    	privilegeDao.deleteByRoleId(roleId);
		privilegeDao.batchUpdatePrivilege(batchSize, batchList, roleId);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.PrivilegeService#batchInsert(int, java.util.List)
	 */
	@Override
	public void batchInsert(int batchSize, List<MgrPrivilege> batchList, Integer roleId) {
		// TODO Auto-generated method stub
		privilegeDao.batchUpdatePrivilege(batchSize, batchList, roleId);
	}
}
