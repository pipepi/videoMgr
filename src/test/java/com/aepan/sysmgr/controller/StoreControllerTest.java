/**
 * 
 */
package com.aepan.sysmgr.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.aepan.sysmgr.web.controller.Product3Controller;
import com.aepan.sysmgr.web.controller.StoreController;

/**
 * @author Administrator
 * 2016年4月11日上午11:12:08
 */
public class StoreControllerTest  extends BasicWebTest{
	@Autowired
	StoreController storeController;
	@Autowired
	Product3Controller product3Controller;
	@Test
	public void testGetProducts4Link() throws Exception{
		MvcResult mr =mvc.perform(MockMvcRequestBuilders.get("/product3/list4player").param("storeId", "112"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		MockHttpServletResponse response = mr.getResponse();
		MockHttpServletRequest request = mr.getRequest();
	}
	

}
