/**
 * 
 */
package com.aepan.sysmgr.service.implement;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com._21cn.framework.util.PageList;
import com.aepan.sysmgr.dao.OperationDao;
import com.aepan.sysmgr.model.Operation;
import com.aepan.sysmgr.service.OperationService;

/**
 * 
 * @author rakika
 * 2015年7月11日上午9:16:46
 */
@Service
public class OperationServiceImpl implements OperationService {
	
	@Autowired
	OperationDao operationDao;

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.OperationService#getList(java.util.Map, int, int)
	 */
	@Override
	public PageList<Operation> getList(Map<String, Object> params, int pageNo,
			int pageSize) {
		return operationDao.selectMapByMap(params, pageNo, pageSize);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.OperationService#selectOperationByRoleId(java.lang.Integer)
	 */
	@Override
	public List<String> selectOperationByRoleId(Integer roleId) {
		// TODO Auto-generated method stub
		return operationDao.selectOperationByRoleId(roleId);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.OperationService#create(com._aepan.sysmgr.model.Operation)
	 */
	@Override
	public boolean create(Operation operation) {
		// TODO Auto-generated method stub
		return operationDao.create(operation);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.OperationService#getById(int)
	 */
	@Override
	public Operation getById(int id) {
		return operationDao.getById(id);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.OperationService#update(com._aepan.sysmgr.model.Operation)
	 */
	@Override
	public boolean update(Operation operation) {
		// TODO Auto-generated method stub
		return operationDao.update(operation);
	}

}
