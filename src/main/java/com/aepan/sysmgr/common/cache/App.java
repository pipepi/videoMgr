/**
 * 
 */
package com.aepan.sysmgr.common.cache;

/**
 * @author AZT
 * 2015年11月3日上午10:38:30
 */
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

/* Make sure your turn on non SSL port in Azure Redis using the Configuration section in the Azure portal */
public class App
{
  public static void main( String[] args )
  {

	  
    /* In this line, replace <name> with your cache name: */
    JedisShardInfo shardInfo = new JedisShardInfo("9cooo01.redis.cache.chinacloudapi.cn", 6379);
    shardInfo.setPassword("E/1fSlGidWfpk/LUngEQ2BQnqyJ0cdgwY/cIrlF6pJw="); /* Use your access key. */
    Jedis jedis = new Jedis(shardInfo);
    jedis.set("foo", "bar");
    String value = jedis.get("foo");
    jedis.set("foo", "bar1");
    jedis.set("foo", "bar2");
    String value1 = jedis.get("foo");
    System.out.println(value+","+value1);
    
    
  }
} 
