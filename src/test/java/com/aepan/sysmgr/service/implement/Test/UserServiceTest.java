package com.aepan.sysmgr.service.implement.Test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aepan.sysmgr.dao.rowmapper.UserRowMapper;
import com.aepan.sysmgr.model.User;

/**
 * 
 * @author rakika
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class UserServiceTest {

	@Autowired
    private JdbcTemplate jdbcTemplate;
//	private UserService userService;
	
	@Test
	public void testGet(){
		
		User user =(User)jdbcTemplate.queryForObject("select t.id, t.username, t.password, " 
		        + "t.email, t.role_id, t.status, t.create_time from t_sysmgr_user t where id = ? ", 
					new Object[] {"admin"}, new int[]{java.sql.Types.INTEGER}, new UserRowMapper());
		System.out.println(user.getUserName());
//		User user = userService.get("admin");
//		if(user != null){
//			System.out.println("------OK!!------");
//		}else{
//			System.out.println("------Sorry!!------");
//		}
	}
}
