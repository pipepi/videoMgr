package com.aepan.sysmgr.common.cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aepan.sysmgr.util.ConfigUtil;


/**
 * @Desc   加载参数信息到缓存
 * @author laihh
 * @date   2014-01-13
 * @version: 
 */
public class ParamInfoCache {
	
	
	private static final Logger logger = LoggerFactory.getLogger(ParamInfoCache.class);

	
	private static Map<String, Object> mapParamInfoCache = new ConcurrentHashMap<String, Object>(); 
	
	
	public  static Map<Integer,ReentrantLock> controllerLockMap = new ConcurrentHashMap<Integer, ReentrantLock>();
	
	public static final int LOCK_SAVE_PLAYER=1;
	
	public void initMapParamInfo() {
		logger.info("ParamInfoCache loading : initMapCache"  );	
		controllerLockMap.put(LOCK_SAVE_PLAYER, new ReentrantLock());
		
      
	}

	
	
	private Map<String,Object> loadParamInfo(){ 
		Map<String,Object> map = new HashMap<String, Object>();
		try{
			
			Properties props = ConfigUtil.getInstance().getProps();		
			if(props != null && props.size() > 0 ){
			    
			    mapParamInfoCache.clear();
				Set<?> keyValue = props.keySet();              
				for (Iterator<?> it = keyValue.iterator(); it.hasNext();) {
					String key = (String)it.next();
					map.put(key, props.getProperty(key));	
				}
				mapParamInfoCache.putAll(map);
				logger.info(" ParamInfoCache initMapCache() Sync [paramInfoNum = " + mapParamInfoCache.size() +" ] data to paramInfoCache.");
				return map;
			}else{
				logger.info("ParamInfoCache loadParamInfo() Not ParamInfo data could be sync to mapParamInfoCache.");
				return Collections.emptyMap();
			}
		}catch(Exception e){
			logger.error(" ParamInfoCache loadParamInfo() Exception " + e.getMessage(),e);
			return null;
		}
	}
	
	/**
	 * 获取参数值
	 * @param key
	 * @return
	 */
	public static String get(String key){
		if(mapParamInfoCache.containsKey(key)){
			return (String)mapParamInfoCache.get(key);
		}
		return null;  
	}
	
	/**
	 * 获取参数值
	 * @param key 
	 * @param defaultVal 默认值
	 * @return
	 */
	public static String get(String key,String defaultVal){
		try{
			if(mapParamInfoCache.containsKey(key)){
				return (String)mapParamInfoCache.get(key);
			}
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
		return defaultVal;  
	}
	
	/**
	 * 获取参数值
	 * @param key 
	 * @param defaultVal 默认值
	 * @return
	 */
	public static long getLong(String key,long defaultVal){
		try{
			if(mapParamInfoCache.containsKey(key)){
				return new Long((String)mapParamInfoCache.get(key));
			}
		}catch(Exception e){
			logger.info(e.getMessage(),e);
		}
		return defaultVal;  
	}
	
	/**
	 * 获取参数值
	 * @param key 
	 * @param defaultVal 默认值
	 * @return
	 */
	public static long getInteger(String key,Integer defaultVal){
		try{
			if(mapParamInfoCache.containsKey(key)){
				return new Integer((String)mapParamInfoCache.get(key));
			}
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
		return defaultVal;  
	}
	

	/**
	 * 获取缓存大小
	 * @return
	 */
	public static int size(){
		return mapParamInfoCache.size();
	}
	
	
}
