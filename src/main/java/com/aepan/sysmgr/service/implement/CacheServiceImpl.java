/**
 * 
 */
package com.aepan.sysmgr.service.implement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import com.aepan.sysmgr.dao.ProductDao;
import com.aepan.sysmgr.dao.VideoDao;
import com.aepan.sysmgr.model.Store;
import com.aepan.sysmgr.model.StoreInfo;
import com.aepan.sysmgr.model.StoreProduct;
import com.aepan.sysmgr.model.StoreVideo;
import com.aepan.sysmgr.model._enum.CacheObject;
import com.aepan.sysmgr.service.CacheService;
import com.aepan.sysmgr.service.ConfigService;
import com.aepan.sysmgr.service.SearchService;
import com.aepan.sysmgr.service.StoreService;
import com.aepan.sysmgr.util.JSONUtil;
import com.aepan.sysmgr.util.lucene.SearchHelper;

/**
 * @author lanker
 * 2016年1月11日上午11:42:42
 * @param <K>
 * @param <V>
 */
@Service
public class CacheServiceImpl<K, V> implements CacheService {
	private static final Logger logger = LoggerFactory.getLogger(CacheServiceImpl.class);	
	@Autowired
	private RedisTemplate<K, V> redisTemplate;
	@Autowired
	private VideoDao videoDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ConfigService configService;
	@Autowired 
	private StoreService storeService;
	@Autowired
	private SearchService searchService;
	@Override
	public void add(CacheObject cacheObject, Object obj) {
		String k  = "";
		String v = "";
		if(cacheObject==CacheObject.STOREINFO){
			StoreInfo storeInfo = (StoreInfo)obj;
			k = cacheObject.toString()+storeInfo.getId();
			v = JSONUtil.toJson(storeInfo);
		}
		addCache(k, v);
	}
	private void addCache(String k,String v){
		if(k.trim().length()>0&&v.trim().length()>0){
			final String fk = k;
			final String fv = v;
			redisTemplate.execute(new RedisCallback<Boolean>() {
				@Override
				public Boolean doInRedis(RedisConnection conn)
						throws DataAccessException {
					@SuppressWarnings("unchecked")
					RedisSerializer<String> serializer = (RedisSerializer<String>) redisTemplate.getValueSerializer();  
					byte[] key  = serializer.serialize(fk);  
					byte[] name = serializer.serialize(fv);  
					return conn.setNX(key, name);
				}
			});
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public void delete(CacheObject cacheObject, int id) {
		String k  = "";
		if(cacheObject==CacheObject.STOREINFO){
			k = cacheObject.toString()+id;
		}
		redisTemplate.delete((K)k);
		logger.debug("\n\n deleteByStoreId storeId= "+id);
	}
	@SuppressWarnings("unchecked")
	@Override
	public void delete(CacheObject cacheObject, int[] ids) {
		if(ids!=null&&ids.length>0){
			List<K> list = new ArrayList<K>();
			String[] k  = new String[ids.length];
			if(cacheObject==CacheObject.STOREINFO){
				for (int i = 0; i < k.length; i++) {
					k[i] = cacheObject.toString()+ids[i];
					list.add((K)k[i]);
				}
			}
			redisTemplate.delete(list);
		}
	}
	@Override
	public void deleteByUserId(CacheObject cacheObject, int userId){
		//删除搜索引擎索引
		List<Store> storeList = storeService.getListByUserId(userId);
		if(storeList!=null&&!storeList.isEmpty()){
			for (Store store : storeList) {
				//SearchHelper.delete(configService, store.getId()+"");
				try {
					searchService.delete(store.getId()+"");
				} catch (SolrServerException e) {
					logger.debug("delete store search index error . store id ="+store.getId()+"\n"+e.getMessage());
				} catch (IOException e) {
					logger.debug("delete store search index error . store id ="+store.getId()+"\n"+e.getMessage());
				}
			}
		}
		//to do 删除redis缓存  待定 
	}
	@Override
	public void addByUserId(CacheObject cacheObject, int userId){
		//添加搜索引擎索引
		List<Store> storeList = storeService.getListByUserId(userId);
		if(storeList!=null&&!storeList.isEmpty()){
			for (Store store : storeList) {
				storeService.addSolr(configService, store);
				//storeService.addLucene(configService, store);
			}
		}
	}
	@Override
	public void deleteByVideoId(CacheObject cacheObject, int vid) {
		if(vid>0){
			if(cacheObject==CacheObject.STOREINFO){
				List<StoreVideo> sv= videoDao.getStoreVideoListByVideoId(vid);
				if(sv!=null&&sv.size()>0){
					int[] storeIds = new int[sv.size()];
					for (int i = 0; i < sv.size(); i++) {
						storeIds[i] = sv.get(i).storeId;
						try {
							searchService.delete(storeIds[i]+"");
						} catch (SolrServerException e) {
							logger.debug("delete store search index error . store id ="+storeIds[i]+"\n"+e.getMessage());
						} catch (IOException e) {
							logger.debug("delete store search index error . store id ="+storeIds[i]+"\n"+e.getMessage());
						}
					}
					delete(cacheObject, storeIds);
					logger.debug("\n\n deleteByVideoId vid= "+vid);
				}
			}
		}
	}
	@Override
	public void deleteByProductId(CacheObject cacheObject, int pid) {
		if(pid>0){
			if(cacheObject==CacheObject.STOREINFO){
				List<StoreProduct> sp= productDao.getStoreProductListByProductId(pid);
				if(sp!=null&&sp.size()>0){
					int[] storeIds = new int[sp.size()];
					for (int i = 0; i < sp.size(); i++) {
						storeIds[i] = sp.get(i).storeId;
						// do delete search index of lucene
						logger.debug("\n\n do delete search index of lucene storeId= "+storeIds[i]);
						storeService.addLucene(configService, storeIds[i],pid);
					}
					delete(cacheObject, storeIds);
					logger.debug("\n\n deleteByProductId pid= "+pid);
				}
			}
		}
	}
	@Override
	public String get(CacheObject cacheObject, int id){
		String k  = "";
		if(cacheObject==CacheObject.STOREINFO){
			k = cacheObject.toString()+id;
			return getCache(k);
		}
		return null;
		
	}
	private String getCache(String k){
		final String fk = k;
		String rs = redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection conn)
					throws DataAccessException {
				RedisSerializer<String> serializer =  (RedisSerializer<String>) redisTemplate.getValueSerializer();  
				byte[] key = serializer.serialize(fk);  
				byte[] value = conn.get(key);  
				if (value == null) {  
					return null;  
				}  
				return serializer.deserialize(value);  
			}
		});
		return rs;
	}
}
