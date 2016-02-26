/**
 * 
 */
package com.aepan.sysmgr.service.implement.Test.dao;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aepan.sysmgr.dao.VideoDao;
import com.aepan.sysmgr.dao.implement.VideoDaoImpl;

/**
 * @author lanker
 * 2015年8月3日下午2:23:14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class VdieoTest {
	@Autowired
    private JdbcTemplate jdbcTemplate;
	VideoDao vd = new VideoDaoImpl();
	//vd.
}
