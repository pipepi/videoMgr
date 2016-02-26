package com.aepan.sysmgr.common.redis.jedis;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

/**
 * hash数据结构的操作
 * @since JDK 1.6
 * @version 1.0
 * @author pet
 * @date 2014/1/10
 */
public class JedisHashCache extends JedisCache  {
	private static final Logger logger = LoggerFactory.getLogger(JedisHashCache.class);
	private String key = null;
	
	public JedisHashCache() {
		super();
	}
	JedisHashCache( JedisPool pool, String key ){
		super( pool );
		this.key = key;
	}
	JedisHashCache( JedisPool pool, Transcoder<Object> transcoder, String key ){
		super( pool,transcoder);
		this.key = key;
	}
	
    public long setValue( String field, Object value,boolean forceNewNotExist) {
    	return setValue( key, field, value, forceNewNotExist );
    }
    
    public long setValue(String key, String field, Object value, boolean forceNewNotExist) {
        Jedis jedis = null;
        try {
        	jedis = pool.getResource();
        	byte[] keyBytes = transcoder.getKeyBytes( key );
        	byte[] fieldBytes = transcoder.getKeyBytes( field );
        	byte[] valueBytes = transcoder.encode( value );
        	if( forceNewNotExist ){
        		return jedis.hsetnx( keyBytes, fieldBytes, valueBytes );//存在返回0，不存在set值，返回1
        	}
            return jedis.hset( keyBytes, fieldBytes, valueBytes );//存在，覆盖值，返回0，不存在set值,返回1
        } catch (Exception e) {
           logger.error(e.getMessage(),e);
            pool.returnBrokenResource(jedis);
        	throw new JedisException(e.getMessage());
        } finally {
            pool.returnResource(jedis);
        }
    }
	
    public long remove(String[] fields) {
    	return remove( key, fields );
    }
    
    /**
     * 删除成功返回1，存在异常返回-1
     * @param key
     * @param fields
     * @return
     */
    public long remove(String key, String[] fields) {
        Jedis jedis = null;
        try {
        	jedis = pool.getResource();
       		return jedis.hdel( key, fields );
        } catch (Exception e) {
        	logger.error(e.getMessage(),e);
        	 pool.returnBrokenResource(jedis);
         	 throw new JedisException(e.getMessage());
        } finally {
            pool.returnResource(jedis);
        }
    }

    public Object getValue( String field ) {
    	return getValue( key, field );
    }
    
    /**
     * 存在返回;不存在返回null；异常抛出异常
     * @param key
     * @param field
     * @return
     */
    public Object getValue(String key, String field ) {
        Jedis jedis = null;
        try {
        	jedis = pool.getResource();
        	return transcoder.decode( jedis.hget( transcoder.getKeyBytes( key ), transcoder.getKeyBytes( field ) ));
        } catch (Exception e) {
           logger.error(e.getMessage(),e);
            pool.returnBrokenResource(jedis);
        	throw new JedisException(e.getMessage());
        } finally {
            pool.returnResource(jedis);
        }
    }
    
    public long incrValue( String field, long value ) {
    	return incrValue( key, field, value);
    }
    
    
    /**
     * Increment the number stored at field in the hash at key by value. If key does not exist, a new key holding a hash is created. If field does not exist or holds a string, the value is set to 0 before applying the operation. Since the value argument is signed you can use this command to perform both increments and decrements.
     * The range of values supported by HINCRBY is limited to 64 bit signed integers.
     * @param key
     * @param field
     * @param value
     * @return
     */
    public long incrValue(String key, String field, long value ) {
        Jedis jedis = null;
        try {
        	jedis = pool.getResource();
       		return jedis.hincrBy( key, field, value );
        } catch (Exception e) {
        	logger.error(e.getMessage(),e);
        	 pool.returnBrokenResource(jedis);
         	throw new JedisException(e.getMessage());
        } finally {
            pool.returnResource(jedis);
        }
    }
    
    public Map<String,?> hgetAll() {
    	return hgetAll( key );
    }
    
    
    public Map<String, Object> hgetAll(String key ) {
        Jedis jedis = pool.getResource();
        try {
            Map<byte[],byte[]> bytesMap = jedis.hgetAll( transcoder.getKeyBytes( key ) );
            return convertByteMap( bytesMap );
        } catch (Exception e) {
           logger.error(e.getMessage(),e);
            pool.returnBrokenResource(jedis);
            throw new JedisException(e.getMessage());
        } finally {
            pool.returnResource(jedis);
        }
    }
    
    private Map<String,Object> convertByteMap( Map<byte[],byte[]> members ){
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		for(Map.Entry<byte[],byte[]> entry: members.entrySet() ){
			result.put( transcoder.getKeyString( entry.getKey() ), transcoder.getKeyString( entry.getValue() ) );
		}
		return result;
	}
    public long size(){
    	return size( key );
    }
    
    /**
     * Return the number of items in a hash.
     * @param key
     * @return
     */
    public long size( String key ){
        Jedis jedis = null;
        try {
        	jedis = pool.getResource();
        	return jedis.hlen( key );//
        } catch (Exception e) {
        	logger.error(e.getMessage(),e);
        	pool.returnBrokenResource(jedis);
         	throw new JedisException(e.getMessage());
        } finally {
            pool.returnResource(jedis);
        }    	
    }
    
    /**
     * 返回hash表的所有 域
     * @return
     */
    public Set<String> getFieldSet( ) {
    	return getFieldSet(key);
    }
    
    /**
     * 返回hash表的所有 域
     * @param key
     * @return
     */
    public Set<String> getFieldSet(String key ) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.hkeys( key );
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
        	pool.returnBrokenResource(jedis);
         	throw new JedisException(e.getMessage());
        } finally {
            pool.returnResource(jedis);
        }
    }
    
    public long remove(String key) {
        Jedis jedis = (Jedis)pool.getResource();
        try
        {
            long delResult = jedis.del(new String[] { key });
            return delResult;
        } catch(Exception e) {
            logger.error(e.getMessage(),e);
            pool.returnResource(jedis);
            throw new JedisException(e.getMessage());
        }finally{
        	pool.returnResource(jedis);
        }
    }

    public static void main(String[] args) throws Exception {
//    	JedisPoolConfig config = new JedisPoolConfig();
//    	config.setMaxActive(100);
//    	config.setMaxIdle(20);
//    	config.setMaxWait(1000l);
//    	JedisPool pool = new JedisPool(config, "127.0.0.1", 6379);
//    	JedisHashCache jedisHashCache = new JedisHashCache(pool, "myhash2");
//    	long remove = jedisHashCache.remove("myhash2");
//    	System.out.println("result:"+remove);
//    	String [] arr = new String[]{"testIncr"};
//    	long r1 = jedisHashCache.setValue("test1", "dd", false);
//    	System.out.println("r1="+r1);
//    	long r2 = jedisHashCache.remove(arr);
//    	System.out.println("r2="+r2);
//    	String rs1 = (String) jedisHashCache.getValue("test1");
//    	System.out.println("rs1="+rs1);
//    	String r1 = jedisHashCache.incrValue("testIncr", 3);
//    	System.out.println("r1="+r1);
//    	long r2 = jedisHashCache.remove(arr);
//    	System.out.println("r2="+r2);
//    	String rs1 = (String) jedisHashCache.getValue("testIncr");
//    	System.out.println("rs1="+rs1);
//    	long r1 = jedisHashCache.size();
//    	System.out.println("r1="+r1);
    	
    	
	}
    
    
}


