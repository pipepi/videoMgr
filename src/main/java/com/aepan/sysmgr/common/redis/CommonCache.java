package com.aepan.sysmgr.common.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

import com.aepan.sysmgr.common.redis.jedis.JedisCache;

/**
 * @desc redis的List数据结构设值、取值操作,作为队列的基类
 * @since JDK 1.6
 * @version 1.0
 * @author pet
 * @date 2013/10/30
 */
@Component
public class CommonCache {
	private static final Logger logger = LoggerFactory.getLogger(CommonCache.class);

    @Autowired
    private JedisCache jedisCache;

    public void set(String key, Object value) {
        Jedis jedis = null;
        try {
            jedis = jedisCache.getPool().getResource();
            byte[] keyBytes = jedisCache.getTranscoder().getKeyBytes( key );
            byte[] valueBytes = jedisCache.getTranscoder().encode( value );
            jedis.set(keyBytes, valueBytes);
        } catch (Exception e) {
        	logger.error(e.getMessage(),e);
            jedisCache.getPool().returnBrokenResource(jedis);
            throw new JedisException(e.getMessage());
        } finally {
            jedisCache.getPool().returnResource(jedis);
        }
    }
    
	public void set(String key, Object value, int seconds) {
	    Jedis jedis = null;
        try {
            jedis = jedisCache.getPool().getResource();
            byte[] keyBytes = jedisCache.getTranscoder().getKeyBytes( key );
            byte[] valueBytes = jedisCache.getTranscoder().encode( value );
            jedis.set(keyBytes, valueBytes);
            jedis.expire(keyBytes, seconds);
        } catch (Exception e) {
        	logger.error(e.getMessage(),e);
            jedisCache.getPool().returnBrokenResource(jedis);
            throw new JedisException(e.getMessage());
        } finally {
            jedisCache.getPool().returnResource(jedis);
        }
	}
	
	public Object get(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisCache.getPool().getResource();
            byte[] keyBytes = jedisCache.getTranscoder().getKeyBytes( key );
            return jedisCache.getTranscoder().decode(jedis.get(keyBytes));
        } catch (Exception e) {
        	logger.error(e.getMessage(),e);
            jedisCache.getPool().returnBrokenResource(jedis);
            throw new JedisException(e.getMessage());
        } finally {
            jedisCache.getPool().returnResource(jedis);
        }
    }
	
	public void delete(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisCache.getPool().getResource();
            byte[] keyBytes = jedisCache.getTranscoder().getKeyBytes( key );
            jedis.del(keyBytes);
        } catch (Exception e) {
        	logger.error(e.getMessage(),e);
            jedisCache.getPool().returnBrokenResource(jedis);
            throw new JedisException(e.getMessage());
        } finally {
            jedisCache.getPool().returnResource(jedis);
        }
    }
	
	public static void main(String[] args) {
	    new CommonCache().delete("11");
	}

}
