package com.aepan.sysmgr.service.implement.Test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aepan.sysmgr.dao.rowmapper.UserRowMapper;
import com.aepan.sysmgr.model.User;
import com.aepan.sysmgr.service.UserService;

/**
 * 
 * @author rakika
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/applicationContext.xml","classpath*:/spring-mvc.xml"})
public class UserServiceTest {

	//@Autowired
    //private JdbcTemplate jdbcTemplate;
	@Autowired
	private UserService userService;
	
	//@Test
	public void testGet(){
		
		/*User user =(User)jdbcTemplate.queryForObject("select t.id, t.username, t.password, " 
		        + "t.email, t.role_id, t.status, t.create_time from t_sysmgr_user t where id = ? ", 
					new Object[] {"admin"}, new int[]{java.sql.Types.INTEGER}, new UserRowMapper());
		System.out.println(user.getUserName());*/
//		User user = userService.get("admin");
//		if(user != null){
//			System.out.println("------OK!!------");
//		}else{
//			System.out.println("------Sorry!!------");
//		}
	}
	@Test
	public void queryUserIds(){
		List<Integer> userIds=userService.getUserIds("doris");
		if(userIds!=null&&userIds.size()>0){
			for (Integer userId : userIds) {
				System.out.println(userId);
			}
			userService.deleteUser(userIds);
			
		}
	}
}
