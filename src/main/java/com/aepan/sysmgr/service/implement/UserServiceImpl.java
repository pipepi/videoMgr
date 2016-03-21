/**
 * 
 */
package com.aepan.sysmgr.service.implement;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com._21cn.framework.util.PageList;
import com.aepan.sysmgr.dao.UserDao;
import com.aepan.sysmgr.model.User;
import com.aepan.sysmgr.model._enum.CacheObject;
import com.aepan.sysmgr.service.CacheService;
import com.aepan.sysmgr.service.UserService;

/**
 * user service 实现类
 * @author rakika
 * 2015年6月9日下午5:40:14
 * 
 */
@Service
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;
    @Autowired
    private CacheService cacheService;
	@Override
	public User partnerLogin(int partnerId,int partnerAccountId) {
		User user = userDao.getUserbyPartnerAccount(partnerId,partnerAccountId);
		return user;
	}
	
	
	
	@Override
	public User getByUserId(int userId) {
		return userDao.getByUserId(userId);
	}

	
	@Override
	public User get(int id) {
		User user = userDao.findUserbyName(id);
		if(user != null){
			return user;
		}else{
			return null;
		}
	}


	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.UserService#findUserByNameAndStatus(java.lang.String, int)
	 */
	@Override
	public List<User> findUserByNameAndStatus(String name, int status) {
		return userDao.findUserByNameAndStatus(name, status);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.UserService#findUserByNameAndPassWord(java.lang.String, java.lang.String)
	 */
	@Override
	public User findUserByNameAndPassWord(String name, String passWord) {
		return userDao.findUserByNameAndPassWord(name, passWord);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.UserService#getList(java.util.Map, int, int)
	 */
	@Override
	public PageList<User> getList(Map<String, Object> params, int pageNo,
			int pageSize) {
		// TODO Auto-generated method stub
		return userDao.getList(params, pageNo, pageSize);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.UserService#create(com._aepan.sysmgr.model.User)
	 */
	@Override
	public boolean create(User user) {
		if(userDao.isExist(user)){
			logger.info("该用户或邮箱已被注册！");
			return false;
		}else{
			return userDao.create(user);
		}
		
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.UserService#update(com._aepan.sysmgr.model.User)
	 */
	@Override
	public boolean update(User user) {
		// TODO Auto-generated method stub
		return userDao.update(user);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.UserService#isExist(com._aepan.sysmgr.model.User)
	 */
	@Override
	public boolean isExist(User user) {
		// TODO Auto-generated method stub
		return userDao.isExist(user);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.UserService#findUserByPatnerName(java.lang.String)
	 */
	@Override
	public List<User> findUserByPartnerName(String partnerName) {
		// TODO Auto-generated method stub
		return userDao.findUserByPartnerAccountName(partnerName);
	}


	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.UserService#partnerRegister(int, int)
	 */
	@Override
	public User partnerRegister(int partnerId, int partnerAccountId,String partneraAccountName,String partnerMobile,String email,String qqidKey) {
		
		if(userDao.createPartnerUser(partnerId,partnerAccountId,partneraAccountName,partnerMobile,email,qqidKey)){
			User user = userDao.getUserbyPartnerAccount(partnerId, partnerAccountId);
			if(user != null){
				userDao.initPartnerUserStat(user);
				return user;
			}
		}
		return null;
	}
	@Override
	public int[] getUserIdsByPartnerAccountName(String pan){
		return userDao.getUserIdsByPartnerAccountName(pan);
	}



	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.UserService#isExistMobile(java.lang.String)
	 */
	@Override
	public boolean isExistMobile(String mobile) {
		// TODO Auto-generated method stub
		return userDao.isExistMobile(mobile);
	}



	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.UserService#isExistAddress(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isExistAddress(String mobile, String toAddress) {
		// TODO Auto-generated method stub
		return userDao.isExistAddress(mobile, toAddress);
	}
	@Override
	public void deleteUser(List<Integer> userIds){
		if(userIds!=null&&userIds.size()>0){
			for (Integer userId : userIds) {
				//清除缓存
				cacheService.deleteByUserId(CacheObject.STOREINFO, userId);
				//清除数据库
				userDao.deleteUser(userId);
			}
		}
	}

	@Override
	public List<Integer> getUserIds(String partnerAccountNames) {
		return userDao.getUserIds(partnerAccountNames);
	}
	
	

}
