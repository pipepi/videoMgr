package com.aepan.sysmgr.common.redis.jedis;

import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisPool;

/**
 * 创建连接池
 * @since JDK 1.6
 * @version 1.0
 * @author pet
 * @date 2014/1/10
 */
@Component
public class JedisCache {
	
//	private static final Logger logger = LoggerFactory.getLogger(JedisCache.class);

    protected JedisPool pool;
    
    protected Transcoder<Object> transcoder = StringTranscoder.getDefaultinstance();
    
    public JedisCache(){
    }
    
    public JedisCache( JedisPool pool ){
    	this.pool = pool;
    }
    
    public JedisCache( JedisPool pool, Transcoder<Object> transcoder){
    	this.pool = pool;
    	this.transcoder = transcoder;
    }
    
    /**
     * 获取JedisHashCache实例
     * @param transcoder
     * @param key
     * @return
     */
    public JedisHashCache getJedisHashCache( Transcoder<Object> transcoder, String key ){
    	return new JedisHashCache( pool, transcoder, key );
    }
    public JedisHashCache getJedisHashCache( String key ){
    	return new JedisHashCache( pool, key );
    }

    /**
     * 获取JedisListCache实例
     * @param transcoder
     * @param key
     * @return
     */
    public JedisListCache getJedisListCache( Transcoder<Object> transcoder, String key ){
    	return new JedisListCache( pool, transcoder, key );
    }
    
    public JedisListCache getJedisListCache(String key){
    	return new JedisListCache( pool, key );
    }
    
    public void destroy() {
        pool.destroy();
    }

    public JedisPool getPool() {
        return pool;
    }

    public void setPool(JedisPool pool) {
        this.pool = pool;
    }

    public Transcoder<Object> getTranscoder() {
    	return transcoder;
    }

    public void setTranscoder( Transcoder<Object> transcoder ) {
    	this.transcoder = transcoder;
    }

}