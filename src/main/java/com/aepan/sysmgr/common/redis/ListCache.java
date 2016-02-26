package com.aepan.sysmgr.common.redis;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisException;

import com.aepan.sysmgr.common.cache.ParamInfoCache;
import com.aepan.sysmgr.common.redis.jedis.JedisCache;
import com.aepan.sysmgr.common.redis.jedis.JedisListCache;
import com.aepan.sysmgr.model.MsgBean;

/**
 * @desc redis的List数据结构设值、取值操作,作为队列的基类
 * @since JDK 1.6
 * @version 1.0
 * @author pet
 * @date 2013/10/30
 */
@Component
public class ListCache {

	private static final Logger logger = LoggerFactory.getLogger(ListCache.class);

	private String queueName;//队列名

	private JedisListCache jedisListCache;
	@Autowired
	private JedisCache jedisCache;
	public ListCache() {}
	
	public ListCache(String queueName) {
		this.queueName = queueName;
	}
	
	@PostConstruct
	public void init() {
		jedisListCache = jedisCache.getJedisListCache(jedisCache.getTranscoder(), queueName);
	}
	
	/**
	 * 入队列异常返回-1，队列满返回2，入队列成功返回1
	 * @param msg
	 * @return
	 */
	public int push(MsgBean msg) {
		if (getQueueSize() > ParamInfoCache.getInteger("redis.queue.maxSize", 100000)) {
			logger.error(msg.getMsgId()+",ListCache push queue error,queue is full" + ",size:"+ getQueueSize());
			return 2;
		}
		try {
			return jedisListCache.rightPush(new Object[] { msg }) > 0 ? 1 : -1;
		} catch (JedisException e) {
			logger.error(msg.getMsgId()+",ListCache push queue JedisException",e);
			return -1;	
		}
	}
	
	/**
	 * 存在返回对象；不存在返回空对象；异常返回null
	 * @return
	 */
	public MsgBean pop() {
		try {
			Object object = jedisListCache.pop();
			if(object == null)return new MsgBean();
			return (MsgBean) object;
		} catch (JedisException e) {
			logger.error("ListCache pop queue JedisException",e);
			return null;
		}
	}

	/**
	 * 返回队列长度
	 * @return
	 */
	public long getQueueSize() {
		try {
			return jedisListCache.size();
		} catch (JedisException e) {
			logger.error("ListCache getQueueSize JedisException",e);
			return -1L;
		}
	}
	
	
	public static void main(String[] args) {
		JedisCache jedisCache = new  JedisCache();
		JedisPoolConfig config = new JedisPoolConfig();
    	config.setMaxActive(100);
    	config.setMaxIdle(20);
    	config.setMaxWait(1000l);
    	//JedisPool pool = new JedisPool(config, "127.0.0.1", 6379);
    	JedisPool pool = new JedisPool(config, "9cooo01.redis.cache.chinacloudapi.cn", 6379);
    	JedisListCache jedisListCache = jedisCache.getJedisListCache(jedisCache.getTranscoder(), "listTest");
    	
    	//long r =  jedisListCache.push(true, true, "9999");
    	long r = jedisListCache.push(true, true, new Object[]{"999"});
    	System.out.println(r);
    	String s = jedisListCache.pop().toString();
    	System.out.println(s);
    	
	}

}
