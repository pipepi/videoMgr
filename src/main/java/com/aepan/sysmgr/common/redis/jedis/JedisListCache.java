package com.aepan.sysmgr.common.redis.jedis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

/**
 * list数据结构的操作
 * @since JDK 1.6
 * @version 1.0
 * @author pet
 * @date 2014/1/10
 */
public class JedisListCache extends JedisCache  {
    
	private static final Logger logger = LoggerFactory.getLogger(JedisListCache.class);
	private String key = null;
	
	JedisListCache( JedisPool pool, String key ){
		super( pool );
		this.key = key;
	}
	
	JedisListCache( JedisPool pool, Transcoder<Object> transcoder, String key ){
		super( pool,transcoder);
		this.key = key;
	}
	
    public long push( Object... values) {
		return push( false, true, values );
    }
    
    public long rightPush( Object... values) {
		return push( true, true, values );
    }

    public long push( boolean right, boolean createIfNotExist, Object... values) {
		return push( key, right, createIfNotExist, values);
	}
	
    /**
     * 入队列
     * @param key
     * @param right
     * @param createIfNotExist
     * @param values
     * @return
     */
    public long push(String key, boolean right, boolean createIfNotExist, Object... values) {
    	Jedis jedis = null;
        try {
        	jedis = pool.getResource();
        	if( values == null || values.length == 0 )
        		return 0L;
        	byte[] keyBytes = transcoder.getKeyBytes( key );
        	if( createIfNotExist ){
            	byte[][] bv = new byte[values.length][];
            	int i=0;
            	for(Object v: values ){
            		bv[i++] = transcoder.encode( v );
            	}
	        	if( right ){
	        		return jedis.rpush( keyBytes, bv );
	        	}
	        	return jedis.lpush( keyBytes, bv );
        	}
        	byte[] valueByte = transcoder.encode( values[0] );
        	if( right )
        		return jedis.rpushx( keyBytes, valueByte );
        	return jedis.lpushx( keyBytes, valueByte );
        } catch (Exception e) {
        	e.printStackTrace();
        	pool.returnBrokenResource(jedis);
         	throw new JedisException(e.getMessage());
        } finally {
            pool.returnResource(jedis);
        }
    }

    public Object pop() {
    	return pop( key, false );
    }
    
    public Object rightPop() {
    	return pop( key, true );
    }

    /**
     * 存在返回；不存在返回空对象；异常抛出异常
     * @param key
     * @param right
     * @return
     */
    public Object pop(String key, boolean right ) {
        Jedis jedis = null;
        try {
        	jedis = pool.getResource();
        	byte[] keyBytes = transcoder.getKeyBytes( key );
        	if( right ){
        		return  transcoder.decode( jedis.rpop( keyBytes ));
        	}
        	else{
        		return transcoder.decode( jedis.lpop( keyBytes ));
        	}
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
        	pool.returnBrokenResource(jedis);
        	throw new JedisException(e.getMessage());
        } finally {
            pool.returnResource(jedis);
        }
    }

    /**
     * 返回key的长度
     * @return
     */
    public long size(){
    	return size( key );
    }
    
    /**
     * 返回key的长度
     * @param key
     * @return
     */
    public long size( String key ){
        Jedis jedis = null;
        try {
        	jedis = pool.getResource();
        	byte[] keyBytes = transcoder.getKeyBytes( key );
        	return jedis.llen( keyBytes );
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
        	pool.returnBrokenResource(jedis);
         	throw new JedisException(e.getMessage());
        } finally {
            pool.returnResource(jedis);
        }    	
    }

    public static void main(String[] args) {
    	
//    	JedisPoolConfig config = new JedisPoolConfig();
//    	config.setMaxActive(100);
//    	config.setMaxIdle(20);
//    	config.setMaxWait(1000l);
//    	JedisPool pool = new JedisPool(config, "127.0.0.1", 6379);
//    	JedisListCache jedisListCache = new JedisListCache(pool, "listTest2");
//    	List<String> list = new ArrayList<String>();
////    	list.add("11");list.add("22");
//    	Map m = new HashMap();
//        System.out.println(m.getClass().getSimpleName());
    	
	}
    
    
}
