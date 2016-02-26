/**
 * 
 */
package com.aepan.sysmgr.service;

import java.util.List;
import java.util.Map;

import com._21cn.framework.util.PageList;
import com.aepan.sysmgr.model.User;

/**
 * 用户Service
 * @author rakika
 * 2015年6月9日下午5:36:35
 */
public interface UserService {

	public User get(int id);
	
	public List<User> findUserByNameAndStatus(String name, int status);
	
	public User findUserByNameAndPassWord(String name, String passWord);
	
	public PageList<User> getList(Map<String, Object> params, int pageNo, int pageSize);
	
	public boolean create(User user);
	
	public boolean update(User user);
	//判断用户名或邮箱是否存在
    public boolean isExist(User user);
    
    public List<User> findUserByPartnerName(String partnerName);

	/**
	 * @param partnerId
	 * @param partAccountId
	 * @return
	 */
	User partnerLogin(int partnerId, int partAccountId);

	/**
	 * @param partnerId
	 * @param partnerAccountId
	 * @return 
	 */
	public User partnerRegister(int partnerId, int partnerAccountId,String partneraAccountName,String partnerMobile,String email,String qqidKey);

	/**
	 * @param userId
	 * @return
	 */
	User getByUserId(int userId);

	/**
	 * 通过合作方帐号名称匹配userId
	 * @param pan
	 * @return
	 */
	int[] getUserIdsByPartnerAccountName(String pan);
    
	/**
	 * 判断手机号是否已注册过
	 * @param mobile
	 * @return
	 */
	public boolean isExistMobile(String mobile);
	
	/**
	 * 判断手机号该地址是否已经保存过
	 * @param mobile
	 * @param toAddress
	 * @return
	 */
	public boolean isExistAddress(String mobile, String toAddress);
	
}
