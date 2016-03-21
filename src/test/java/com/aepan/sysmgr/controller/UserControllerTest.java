/**
 * 
 */
package com.aepan.sysmgr.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.aepan.sysmgr.web.controller.ConfigController;
import com.aepan.sysmgr.web.controller.UserController;

/**
 * @author lanker
 * 2016年3月21日下午4:25:19
 */
public class UserControllerTest extends BasicWebTest{
	@Autowired
	private UserController userController;
	@Autowired
	private ConfigController configController;
	@Test@Rollback(false)
	public void deleteUser() throws Exception{
		MvcResult mr =mvc.perform(MockMvcRequestBuilders.get("/config/deleteAccount").param("accountName", "Hairry"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		MockHttpServletResponse response = mr.getResponse();
		MockHttpServletRequest request = mr.getRequest();
	}
}
