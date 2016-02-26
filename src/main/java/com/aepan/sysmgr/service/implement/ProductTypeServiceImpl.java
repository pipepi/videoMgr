/**
 * 
 */
package com.aepan.sysmgr.service.implement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com._21cn.framework.util.PageList;
import com.aepan.sysmgr.dao.ProductDao;
import com.aepan.sysmgr.model.ProductType;
import com.aepan.sysmgr.service.ProductTypeService;

/**
 * @author rakika
 * 2015年7月31日上午10:50:27
 */
@Service
public class ProductTypeServiceImpl implements ProductTypeService {
	private static final Logger logger = LoggerFactory.getLogger(ProductTypeServiceImpl.class);
	@Autowired
	private ProductDao productTypeDao;
	
	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.ProductTypeService#getTypeList(java.util.Map, int, int)
	 */
	@Override
	public PageList<ProductType> getTypeList(Map<String, Object> params,
			int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		return productTypeDao.getTypeList(params, pageNo, pageSize);
	}
	@Override
	public void synchronous(String url){
		List<ProductType> productTypeList = new ArrayList<ProductType>();
		Date now = new Date();
		String rs = getCategory(url);
		try {
			JSONArray array = new JSONArray(rs);
			for(int i = 0 ;i<array.length();i++){
				org.json.JSONObject  obj = array.getJSONObject(i);
				ProductType pt = new ProductType();
				pt.setId(obj.getInt("Id"));
				pt.setName(obj.getString("Name"));
				pt.setCreateTime(now);
				productTypeList.add(pt);
			}
		} catch (JSONException e) {
			logger.error(e.getMessage(),e);
		}
		if(!productTypeList.isEmpty()){
			productTypeDao.reInit(productTypeList);
		}
	}
	private  String getCategory(String url){
		 HttpClient hc = new DefaultHttpClient();
		 String rs = "";
		try {
			 HttpResponse hr = hc.execute(new HttpGet(url));
			 if(hr.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
				 InputStream is = hr.getEntity().getContent();
				 BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
				 String readline = null;
				 while((readline = br.readLine())!=null){
					 rs = rs +readline;
				 }
				 br.close();
				 is.close();
			 }
		}catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
		logger.info(rs);
		return rs;
	}
	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.ProductTypeService#saveType(com._aepan.sysmgr.model.ProductType)
	 */
	@Override
	public boolean save(ProductType productType) {
		// TODO Auto-generated method stub
		return productTypeDao.saveType(productType);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.ProductTypeService#getById(int)
	 */
	@Override
	public ProductType getById(int id) {
		// TODO Auto-generated method stub
		return productTypeDao.getById(id);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.ProductTypeService#update(com._aepan.sysmgr.model.ProductType)
	 */
	@Override
	public boolean update(ProductType productType) {
		// TODO Auto-generated method stub
		return productTypeDao.updateType(productType);
	}

	
}
