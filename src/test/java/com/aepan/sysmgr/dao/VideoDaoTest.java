/**
 * 
 */
package com.aepan.sysmgr.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aepan.sysmgr.model.Video;
import com.aepan.sysmgr.service.ConfigService;
import com.aepan.sysmgr.util.ConfigManager;

/**
 * @author lanker
 * 2016年4月7日上午11:33:56
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/applicationContext.xml","classpath*:/spring-mvc.xml"})
public class VideoDaoTest {
	@Autowired
	VideoDao videoDao;
	@Autowired
	ConfigService configService;
	@Test
	public void queryVideos4Link(){
		ConfigManager.getInstance().loadCache(configService);
		List<Video> vs= videoDao.videos4Link(113, 83, "cnum", "desc", 1, 4);
		System.out.println(vs.size());
	}
}
