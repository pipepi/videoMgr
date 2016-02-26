/**
 * 
 */
package com.aepan.sysmgr.web.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com._21cn.framework.web.HttpRequestInfo;
import com.aepan.sysmgr.common.resp.JsonResp;
import com.aepan.sysmgr.model.Store;
import com.aepan.sysmgr.model.StoreInfo;
import com.aepan.sysmgr.model.User;
import com.aepan.sysmgr.model.Video;
import com.aepan.sysmgr.model._enum.CacheObject;
import com.aepan.sysmgr.model.config.PartnerConfig;
import com.aepan.sysmgr.model.hm.StoreSubInfo;
import com.aepan.sysmgr.service.CacheService;
import com.aepan.sysmgr.service.ConfigService;
import com.aepan.sysmgr.service.ProductService;
import com.aepan.sysmgr.service.StoreService;
import com.aepan.sysmgr.service.UserService;
import com.aepan.sysmgr.service.VideoService;
import com.aepan.sysmgr.util.ConfigManager;
import com.aepan.sysmgr.util.JSONUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 商铺Rest
 * @author rakika
 * 2015年8月5日下午3:59:36
 */
@Controller
@RequestMapping( value = "/rest/store" )
public class StoreRest {
	
	private static final Logger logger = LoggerFactory.getLogger(StoreRest.class);	
	@Autowired
	StoreService storeService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	VideoService videoService;
	
	@Autowired
	ConfigService configService;
	
	
	@Autowired
	UserService userService;
	@Autowired
	CacheService cacheService;
	
	@RequestMapping(value = "/subinfos",produces = MediaType.APPLICATION_JSON_VALUE )
	@ResponseBody
	public String getStoresByIDs( HttpServletRequest request, HttpServletResponse response){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		String idstr = reqInfo.getParameter("ids", "");
		if(idstr!=null&&!idstr.trim().isEmpty()){
			String[] ids = idstr.split(",");
			if(ids!=null&&ids.length>0){
				List<Integer> idList = new ArrayList<Integer>();
				for (String id : ids) {
					idList.add(Integer.parseInt(id));
				}
				List<StoreSubInfo> list = storeService.getStoreSubInfoByIds(idList);
				return JSONUtil.toJson(list);
			}
		}
		return "[]";
	}
	@RequestMapping(value="/deleteCache" , method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteCache( HttpServletRequest request, HttpServletResponse response ){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
        int storeId = reqInfo.getIntParameter("storeId", -1);
        int productId = reqInfo.getIntParameter("productId", -1);
        String productIds = reqInfo.getParameter("productIds", "");
        if(storeId>0){
        	cacheService.delete(CacheObject.STOREINFO, storeId);
        }
        if(productId>0){
        	cacheService.deleteByProductId(CacheObject.STOREINFO, productId);
        }
        if(productIds!=null&&!productIds.trim().isEmpty()){
        	String[] ids = productIds.split(",");
        	if(ids!=null&&ids.length>0){
				for (String pid : ids) {
					cacheService.deleteByProductId(CacheObject.STOREINFO, Integer.parseInt(pid));
				}
			}
        }
	}
	//获取所有在线商铺详细
	@RequestMapping(value = "/showStore", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE )
	@ResponseBody
	public String showStore( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
        int storeId = reqInfo.getIntParameter("storeId", -1);
        if(storeId>0){
        	String storeInfoCache = cacheService.get(CacheObject.STOREINFO, storeId);
        	if(storeInfoCache!=null){
        		logger.debug("\n  /rest/store/showStore \nload from redis storeId="+storeId+"\n");
        		return storeInfoCache;
        	}
        }
        
        StoreInfo storeInfo = new StoreInfo();
        Store store =null;
		try {
        	 store  = storeService.getById(storeId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return responseLog(JsonResp.CODE_GET_STORE_FAILED , "Get store error id:"+storeId);
		}
      
		
		storeInfo.setId(store.getId());
		storeInfo.setShareContent(store.getShareContent());
		storeInfo.setName(store.getName());
		storeInfo.setDescription(store.getDescription());
		storeInfo.setUserId(store.getUserId());
        int userId = store.getUserId();
        User user = userService.getByUserId(userId);
        if(user!=null){
        	storeInfo.setQqidKey(user.getQqidKey());
        }
        List<Integer> productIdList = productService.getStoreProductIdList(storeId);
        StringBuffer idListStrBuf = new StringBuffer();
        
        for (Integer productId : productIdList) {
        	idListStrBuf.append(productId).append(",");
		}
        
        String idListStr = idListStrBuf.toString();
        List<Video> videoList = videoService.getListByStoreId(storeId, userId);
        storeInfo.setVideoList(videoList);
        String videoIds = getVideoIds(videoList);
        if(!idListStr.isEmpty()){
        	idListStr=idListStr.substring(0, idListStr.length()-1);
        	int page = reqInfo.getIntAttribute("page",1);
    		int pagesize = reqInfo.getIntAttribute("pagesize",50);
    		
    		PartnerConfig partnerConfig = ConfigManager.getInstance().getPartnerConfig(configService);
    		String productDetailUrl = partnerConfig.GET_PARTNER_PRODUCT_DETAIL_URL;
//    		StringBuffer param = new StringBuffer("?saleStatus=1&auditStatus=2&page=").append(""+page).append("&rows="+pagesize).append("&ids="+idListStr);
    		StringBuffer param = new StringBuffer();
    		param.append(idListStr).append("/").append(videoIds).append("/1/2/").append(page).append("/").append(pagesize);


    		PostMethod method = new PostMethod(productDetailUrl+param.toString());


    		HttpClient client = new HttpClient();

    		logger.info(method.toString());
    		try {
    			client.executeMethod(method);
    			String ret = method.getResponseBodyAsString();
    			logger.debug("ret:"+ret);
    			
    	        if(method.getStatusCode() == 200 ){
    	        	storeInfo.setProductList(ret);
    	        }else{
    				return responseLog(JsonResp.CODE_GET_PRODUCT_FAILED , "Get store error id:"+storeId);
    	        }
    		} catch (IOException e) {
    			logger.error(e.getMessage(), e);
    		}
        	
        }
    	//流量统计配置
        storeInfo.setFlowConfig(ConfigManager.getInstance().getFlowConfig());
        cacheService.add(CacheObject.STOREINFO, storeInfo);
        logger.debug("\n  /rest/store/showStore \nadd to  redis storeId="+storeId+"\n");
    	return JSONUtil.toJson(storeInfo);
	}
	private String getVideoIds(List<Video> list){
		if(list!=null&&list.size()>0){
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<list.size();i++){
				sb.append(list.get(i).id);
				if(i<list.size()-1){
					sb.append(",");
				}
			}
			return sb.toString();
		}
		return "0";
	}
	
	/**
	 * 响应日志
	 * @param code
	 * @param msgName
	 * @return
	 */
	private String responseLog(int code, String msgName) {
	    Gson gson = new GsonBuilder().setDateFormat( "yyyy-MM-dd HH:mm:ss" ).create();
	    JsonResp resp = new JsonResp();
	    
	    resp.setCode(code);
	    resp.setMsg(msgName);
        
        String json = gson.toJson(resp);
	    logger.info(json);
	    return json;
	}
}
