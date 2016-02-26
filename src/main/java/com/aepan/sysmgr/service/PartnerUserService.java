/**
 * 
 */
package com.aepan.sysmgr.service;

import java.util.Map;

import com._21cn.framework.util.PageList;
import com.aepan.sysmgr.model.PartnerUser;

/**
 * partner service
 * @author rakika
 * 2015年9月4日下午10:00:16
 */
public interface PartnerUserService {

	//获取partnerUser
	public PartnerUser getByPartnerUserName(String partnerUserName);
	
	public PageList<PartnerUser> getList(Map<String, Object> params, int pageNo,
			int pageSize);
	
	
}
