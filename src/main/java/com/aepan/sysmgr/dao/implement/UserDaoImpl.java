package com.aepan.sysmgr.dao.implement;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com._21cn.framework.util.PageList;
import com._21cn.framework.util.PageTurn;
import com.aepan.sysmgr.dao.UserDao;
import com.aepan.sysmgr.dao.rowmapper.UserRowMapper;
import com.aepan.sysmgr.model.User;

/**
 * 用户实现类
 * @author rakika
 *
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
	
    /*
     * 
     * (non-Javadoc)
     * @see com._aepan.sysmgr.dao.UserDao#findUserbyName(int)
     */
	@Override
	public User findUserbyName(int id) {
		return jdbcTemplate.queryForObject("select t.id, t.username,t.partner_id,t.partner_account_id, t.partner_account_name, t.qqid_key,t.password, " 
		        + " t.partner_mobile,t.email, t.role_id, t.status, r.name as rolename, t.package_id, "
		        + " p.name as packagename, t.create_time from t_sysmgr_user t, "
		        + " t_sysmgr_role r, t_sysmgr_package p where t.role_id = r.id "
		        + " and p.id = t.package_id and t.id = ? ", 
					new Object[] {id}, new int[]{java.sql.Types.INTEGER}, new UserRowMapper());
	}
	

	
	@Override
	public User getByUserId(int userId) {
		
		String sql = "select * from t_sysmgr_user where id=?";
		
		 List<User> userList = jdbcTemplate.query(sql, new Object[] {userId},new UserRowMapper());
		 
		 if(userList.size()>0){
			 return userList.get(0);
		 }
		 return null;
		
	}
	
	
	
	@Override
	public User getUserbyPartnerAccount(int partnerId,int accountId) {
		
		String sql = "select * from t_sysmgr_user where partner_id=? and partner_account_id=?";
		
		 List<User> userList = jdbcTemplate.query(sql, new Object[] {partnerId,accountId},new UserRowMapper());
		 
		 if(userList.size()>0){
			 return userList.get(0);
		 }
		 return null;
		
	}

	/* 
	 * 
	 * (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.UserDao#findUserByNameAndStatus(java.lang.String, int)
	 */
	@Override
	public List<User> findUserByNameAndStatus(String name, int status) {
		return jdbcTemplate.query("select t.id, t.username,t.partner_id,t.partner_account_id, t.partner_account_name, t.qqid_key, t.password, " 
		        + " t.partner_mobile,t.email, t.role_id, r.name as rolename, t.package_id, p.name as packagename, "
		        + " t.status, t.create_time from t_sysmgr_user t, t_sysmgr_role r, "
		        + " t_sysmgr_package p where username = ? and status = ? and p.id = t.package_id "
		        + " and r.id = t.role_id ", 
					new Object[] {name, status}, new UserRowMapper());				
	}

	/* 
	 * 
	 * (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.UserDao#findUserByNameAndPassWord(java.lang.String, java.lang.String)
	 */
	@Override
	public User findUserByNameAndPassWord(String name, String passWord) {
		return jdbcTemplate.queryForObject("select t.id, t.username,t.partner_id,t.partner_account_id, t.partner_account_name, t.qqid_key, t.password, " 
		        + " t.partner_mobile,t.email, t.role_id, r.name as rolename, t.package_id, p.name as packagename, "
		        + " t.status, t.create_time from t_sysmgr_user t , t_sysmgr_role r, t_sysmgr_package p "
		        + " where username = ? and password = ? and p.id = t.package_id and r.id = t.role_id ", 
					new Object[] {name, passWord}, new UserRowMapper());
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.UserDao#getList(java.util.Map, int, int)
	 */
	@Override
	public PageList<User> getList(Map<String, Object> params, int pageNo,
			int pageSize) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select t.id, t.username, t.partner_account_name,t.partner_id,t.partner_account_id, t.qqid_key, t.password, t.partner_mobile,t.email, t.role_id, ");
		sb.append(" t.status, r.name as rolename, t.package_id, p.name as packagename, ");
		sb.append(" t.create_time from t_sysmgr_user t, t_sysmgr_role r, t_sysmgr_package p ");
		sb.append(" where t.role_id = r.id and p.id = t.package_id ");

        //params userName
        if(params.get("eqUsername") != null){
        	sb.append(" and t.username like '%");
        	sb.append(params.get("eqUsername") + "%' ");
        }
        //params eqEmail
        if(params.get("eqEmail") != null){
        	sb.append(" and t.email like '%");
        	sb.append(params.get("eqEmail") + "%' ");
        }
		
		//params sortName
		if(params.get("sortName") != null){
			sb.append(" order by " + params.get("sortName"));
			sb.append(" " + params.get("sortOrder"));
		}
		List<User> list = jdbcTemplate.query(sb.toString(), new UserRowMapper());
		PageList<User> pageList = new PageList<User>();
		PageTurn pageTurn = new PageTurn(pageNo, pageSize,
				params.get("iDisplayStart") == null ? 1 :
				Integer.parseInt(params.get("iDisplayStart").toString()));
		for(int i = 0; i < list.size(); i++){
		    pageList.add(list.get(i));	
		}
		pageList.setPageTurn(pageTurn);
		return pageList;
	}

	/* 
	 * 
	 * (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.UserDao#create(com._aepan.sysmgr.model.User)
	 */
	@Override
	public boolean create(User user) {
		StringBuffer sb = new StringBuffer(" insert into t_sysmgr_user(username, ");
		sb.append(" password, email, role_id, package_id, status) values(?, ?, ?, ?, ?, ?) ");
		
		int ret = jdbcTemplate.update(sb.toString(), new Object[]{
			user.getUserName(),
			user.getPassWord(),
			user.getEmail(),
			user.getRoleId(),
			user.getPackageId(),
			user.getStatus()
		});
		return ret > 0;
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.UserDao#update(com._aepan.sysmgr.model.User)
	 */
	@Override
	public boolean update(User user) {
		StringBuffer sb = new StringBuffer(" update t_sysmgr_user set username = ? ,partner_mobile=?, ");
        sb.append(" email = ?, role_id = ?, package_id = ?, status = ?, qqid_key=? where id = ? ");
		
		int ret = jdbcTemplate.update(sb.toString(), new Object[]{
			user.getUserName(),
			user.getPartnerMobile(),
			user.getEmail(),
			user.getRoleId(),
			user.getPackageId(),
			user.getStatus(),
			user.getQqidKey(),
			user.getId()
		});
		return ret > 0;
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.UserDao#isExist(com._aepan.sysmgr.model.User)
	 */
	@Override
	public boolean isExist(User user) {
		return jdbcTemplate.query("select t.id, t.username, t.partner_account_name, t.qqid_key, t.password, " 
		        + " t.partner_mobile,t.email, t.role_id, r.name as rolename, t.status, t.package_id, p.name as packagename, "
		        + " t.create_time from t_sysmgr_user t, t_sysmgr_role r, t_sysmgr_package p "
		        + " where t.username = ? or t.email = ? and r.id = t.role_id and p.id = t.package_id ", 
					new Object[] {user.getUserName(), user.getEmail()}, new UserRowMapper()).size() > 0;			
	}

	@Override
	public List<User> findUserByPartnerAccountName(String partnerAccountName) {
		jdbcTemplate.queryForObject("select t.id, t.username, t.partner_account_name, t.qqid_key, t.password, " 
		        + " t.partner_mobile,t.email, t.role_id, t.status, r.name as rolename, t.package_id, "
		        + " p.name as packagename, t.create_time from t_sysmgr_user t, "
		        + " t_sysmgr_role r, t_sysmgr_package p where t.role_id = r.id "
		        + " and p.id = t.package_id and t.partner_account_name = ? ", 
					new Object[] { partnerAccountName }, 
					new UserRowMapper());
		return null;
	}
	
	
	@Override
	public boolean createPartnerUser(int partnerId,int partnerAccountId,String partneraAccountName,String partnerMobile,String email,String qqidKey) {
		StringBuffer sb = new StringBuffer(" insert into t_sysmgr_user(partner_id, partner_account_id,partner_account_name,partner_mobile,email,qqid_key ) values(?,?,?,?,?,?)");
		//随机生成用户
		int ret = jdbcTemplate.update(sb.toString(), new Object[]{
			partnerId,
			partnerAccountId,
			partneraAccountName,
			partnerMobile,
			email,
			qqidKey});
		return ret > 0;
	}

	@Override
	public boolean initPartnerUserStat(User user) {
		StringBuffer sb = new StringBuffer(" insert into t_sysmgr_package_stat (user_id, package_id) ");
		sb.append(" VALUES(?, ?) ");
				
		int ret = jdbcTemplate.update(sb.toString(), new Object[]{
			user.getId(),
			user.getPackageId()});
		return ret > 0;
	}
	@Override
	public int[] getUserIdsByPartnerAccountName(String pan){
		int[] rs = {};
		if(pan==null||pan.isEmpty()) return rs;
		String sql = "select id from t_sysmgr_user where partner_account_name like concat('%',?,'%')";
		List<Integer> list = jdbcTemplate.queryForList(sql, new Object[]{pan},Integer.class);
		if(list!=null&&!list.isEmpty()){
			rs = new int[list.size()];
			for(int i = 0;i<list.size();i++){
				rs[i] = list.get(i);
			}
		}
		return rs;
	}



	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.UserDao#isExistMobile(java.lang.String)
	 */
	@Override
	public boolean isExistMobile(String mobile) {
		return jdbcTemplate.query("select t.id, t.username, t.partner_account_name, t.qqid_key, t.password, " 
		        + " t.partner_mobile,t.email, t.role_id, r.name as rolename, t.status, t.package_id, p.name as packagename, "
		        + " t.create_time from t_sysmgr_user t, t_sysmgr_role r, t_sysmgr_package p "
		        + " where t.partner_mobile = ? and r.id = t.role_id and p.id = t.package_id ", 
					new Object[] { mobile }, new UserRowMapper()).size() > 0;			
	}



	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.UserDao#isExistAddress(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isExistAddress(String mobile, String toAddress) {
		return jdbcTemplate.query("select t.id, t.username, t.partner_account_name, t.qqid_key, t.password, " 
		        + " t.email, t.role_id, r.name as rolename, t.status, t.package_id, p.name as packagename, "
		        + " t.create_time from t_sysmgr_user t, t_sysmgr_role r, t_sysmgr_package p, t_sysmgr_address a "
		        + " where t.partner_mobile = ? and r.id = t.role_id and p.id = t.package_id "
		        + " and t.id = a.user_id and a.to_address = ? ", 
					new Object[] { mobile, toAddress }, new UserRowMapper()).size() > 0;		
	}

}
