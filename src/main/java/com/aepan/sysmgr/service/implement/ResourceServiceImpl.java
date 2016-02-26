/**
 * 
 */
package com.aepan.sysmgr.service.implement;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com._21cn.framework.util.PageList;
import com.aepan.sysmgr.dao.ResourceDao;
import com.aepan.sysmgr.model.Resource;
import com.aepan.sysmgr.service.ResourceService;

/**
 * 资源实现类
 * @author rakika
 * 2015年6月20日上午11:07:44
 */
@Service
public class ResourceServiceImpl implements ResourceService{

	@Autowired
	private ResourceDao resourceDao;
	
	/* 
	 * 根据名字选择资源
	 * (non-Javadoc)
	 * @see com._aepan.sysmgr.service.ResourceService#selectResourceByName(java.lang.String)
	 */
	@Override
	public List<Resource> selectResourceByName(String userName) {
		return resourceDao.selectResourceByName(userName);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.ResourceService#getList(java.util.Map, int, int, boolean)
	 */
	@Override
	public PageList<Resource> getList(Map<String, Object> params, int pageNo,
			int pageSize, boolean doCount) {
		// TODO Auto-generated method stub
		return null;
	}

	/* 
	 * 
	 * (non-Javadoc)
	 * @see com._aepan.sysmgr.service.ResourceService#getList(java.util.Map, int, int)
	 */
	@Override
	public PageList<Resource> getList(Map<String, Object> params, int pageNo,
			int pageSize) {
		// TODO Auto-generated method stub
		return resourceDao.selectMapByMap(params, pageNo, pageSize);
	}

	/* 
	 * 
	 * (non-Javadoc)
	 * @see com._aepan.sysmgr.service.ResourceService#insert(com._aepan.sysmgr.model.Resource)
	 */
	@Override
	public void insert(Resource resource) {
		// TODO Auto-generated method stub
		resourceDao.insert(resource);
	}

	/* 
	 * get by id
	 * (non-Javadoc)
	 * @see com._aepan.sysmgr.service.ResourceService#getById(int)
	 */
	@Override
	public Resource getById(int id) {
		return resourceDao.getById(id);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.ResourceService#update(com._aepan.sysmgr.model.Resource)
	 */
	@Override
	public void update(Resource resource) {
		// TODO Auto-generated method stub
		resourceDao.update(resource);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.ResourceService#deleteResourceByMap(java.util.Map)
	 */
	@Override
	public void deleteResourceByMap(Map<String, Object> params) {
		// TODO Auto-generated method stub
		resourceDao.deleteResourceByMap(params);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.ResourceService#selectResourceNotExist(int)
	 */
	@Override
	public List<Resource> selectResourceNotExist(int roleId) {
		// TODO Auto-generated method stub
		return resourceDao.selectResourceNotExist(roleId);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.ResourceService#selectResourceByRoleId(java.lang.Integer)
	 */
	@Override
	public List<Integer> selectResourceByRoleId(Integer roleId) {
		// TODO Auto-generated method stub
		return resourceDao.selectResourceByRoleId(roleId);
	}

}
