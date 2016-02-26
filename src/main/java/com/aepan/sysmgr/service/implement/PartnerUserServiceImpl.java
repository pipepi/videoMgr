/**
 * 
 */
package com.aepan.sysmgr.service.implement;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com._21cn.framework.util.PageList;
import com.aepan.sysmgr.dao.PartnerUserDao;
import com.aepan.sysmgr.model.PartnerUser;
import com.aepan.sysmgr.service.PartnerUserService;

/**
 * @author rakika
 * 2015年9月4日下午10:01:55
 */
@Service
public class PartnerUserServiceImpl implements PartnerUserService {
	
	@Autowired
	PartnerUserDao partnerUserDao;

	@Override
	public PartnerUser getByPartnerUserName(String partnerUserName) {
		return partnerUserDao.getByPartnerUserName(partnerUserName);
	}

	@Override
	public PageList<PartnerUser> getList(Map<String, Object> params, int pageNo,
			int pageSize) {
		return partnerUserDao.getList(params, pageNo, pageSize);
	}



}
