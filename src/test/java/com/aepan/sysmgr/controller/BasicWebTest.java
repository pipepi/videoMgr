/**
 * 
 */
package com.aepan.sysmgr.controller;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * 
 * @author lanker
 * 2016年3月21日下午4:37:40
 */
@WebAppConfiguration
@ContextConfiguration({"classpath*:/applicationContext.xml","classpath*:/spring-mvc.xml"})
public class BasicWebTest extends AbstractTransactionalJUnit4SpringContextTests{
	@Autowired
	protected WebApplicationContext wac;
	protected MockMvc mvc;
	@Before
	public void setUp(){
		this.mvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
}
