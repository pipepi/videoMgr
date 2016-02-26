/**
 * 
 */
package com.aepan.sysmgr.service.implement;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aepan.sysmgr.model.config.PartnerConfig;
import com.aepan.sysmgr.model.hm.PartnerProductPage;
import com.aepan.sysmgr.service.ConfigService;
import com.aepan.sysmgr.service.PartnerDataService;
import com.aepan.sysmgr.util.ConfigManager;
import com.google.gson.Gson;

/**
 * @author Administrator
 * 2016年1月18日下午5:20:03
 */
@Service
public class PartnerDataServiceImpl implements PartnerDataService {
	private static final Logger logger = LoggerFactory.getLogger(PartnerDataServiceImpl.class);
	@Autowired
	private ConfigService configService;
	@Override
	public PartnerProductPage getProducts(int partnerUserId,int page,int pagesize,String orderBy,String orderType) {
		PartnerConfig partnerConfig = ConfigManager.getInstance().getPartnerConfig(configService);
		String productPageUrl = partnerConfig.GET_PARTNER_PRODUCT_PAGE_URL;
		StringBuffer param = new StringBuffer();
		param.append(partnerUserId).append("/1/2/").append(page).append("/").append(pagesize).append("/"+orderBy+"/"+orderType);
		GetMethod method = new GetMethod(productPageUrl+param.toString());
		HttpClient client = new HttpClient();
		logger.info(method.toString());
		String ret = "";
		try {
			client.executeMethod(method);
			ret = method.getResponseBodyAsString();
			logger.debug("ret:"+ret);
			
	        if(method.getStatusCode() == 200 ){
	        	Gson gson = new Gson();
	        	PartnerProductPage hp = gson.fromJson(ret, PartnerProductPage.class);
	        	return hp;
	        }
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
		return null;
	}

	@Override
	public PartnerProductPage getProducts(String productIds) {
		PartnerConfig partnerConfig = ConfigManager.getInstance().getPartnerConfig(configService);
		String url = partnerConfig.ROOT_PATH_KAIMAI8+"/api/product/"+productIds;
		GetMethod method = new GetMethod(url);
		HttpClient client = new HttpClient();
		logger.info(method.toString());
		String ret = "";
		try {
			client.executeMethod(method);
			ret = method.getResponseBodyAsString();
			logger.debug("ret:"+ret);
			
	        if(method.getStatusCode() == 200 ){
	        	Gson gson = new Gson();
	        	PartnerProductPage hp = gson.fromJson(ret, PartnerProductPage.class);
	        	return hp;
	        }
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
		return null;
	}

}
