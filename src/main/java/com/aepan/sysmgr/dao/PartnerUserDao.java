/**
 * 
 */
package com.aepan.sysmgr.dao;

import java.util.Map;

import com._21cn.framework.util.PageList;
import com.aepan.sysmgr.model.PartnerUser;
import com.aepan.sysmgr.model.User;

/**
 * 
 * @author rakika
 * 2015年9月4日下午10:02:49
 */
public interface PartnerUserDao {

	public PartnerUser getByPartnerUserName(String partnerUserName);
	
	public PageList<PartnerUser> getList(Map<String, Object> params, int pageNo,
			int pageSize);
	
	
	public User getByPartnerUser(String partnerUserName);
}
