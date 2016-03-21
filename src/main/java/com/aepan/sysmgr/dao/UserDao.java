package com.aepan.sysmgr.dao;


import java.util.List;
import java.util.Map;

import com._21cn.framework.util.PageList;
import com.aepan.sysmgr.model.User;

/**
 * UserDao
 * @author rakika
 *
 */
public interface UserDao {

	public User findUserbyName(int id);
	
	//判断user状态
	public List<User> findUserByNameAndStatus(String name, int status);
	
	//获取用户
	public User findUserByNameAndPassWord(String name, String passWord);
	
	//获取用户列表
	public PageList<User> getList(Map<String, Object> params, int pageNo, int pageSize);
	
	//创建用户
	public boolean create(User user);
	
	//更新用户信息
	public boolean update(User user);
	
	//判断用户名或邮箱是否存在
	public boolean isExist(User user);
	
	//查询对应的第三方 name
	public List<User> findUserByPartnerAccountName(String partnerName);

	/**
	 * @param partnerId
	 * @param accountId
	 * @return
	 */
	User getUserbyPartnerAccount(int partnerId, int accountId);

	/**
	 * @param partnerId
	 * @param partnerAccountId
	 * @return
	 */
	boolean createPartnerUser(int partnerId, int partnerAccountId,String partneraAccountName,String partnerMobile,String email,String qqidKey);

	/**
	 * @param user
	 * @return
	 */
	boolean initPartnerUserStat(User user);

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
	
	public boolean isExistMobile(String mobile);

	public boolean isExistAddress(String mobile, String toAddress);

	/**
	 * @param userId
	 */
	void deleteUser(int userId);

	/**
	 * @param partnerAccountNames
	 * @return
	 */
	List<Integer> getUserIds(String partnerAccountNames);

}
