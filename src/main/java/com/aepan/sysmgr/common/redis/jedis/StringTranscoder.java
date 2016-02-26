package com.aepan.sysmgr.common.redis.jedis;

import java.util.Date;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.util.SafeEncoder;

import com._21cn.framework.util.DateUtil;
import com._21cn.framework.util.JsonEncoder;
import com._21cn.framework.util.StringUtil;

/**
 * 基本类型int,short,long,float,double,String是按普通的字符串编码保存到redis，只有这样才可以用redis的sort,inc,dec等特性
 * 
 * @author Administrator
 * 
 */
public class StringTranscoder implements Transcoder<Object> {
	private Logger log = LoggerFactory.getLogger(StringTranscoder.class);

	public static final int MAX_SIZE = 20 * 1024 * 1024;

	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	private static final StringTranscoder defaultInstance = new StringTranscoder();

	private int maxSize = MAX_SIZE;
	private String dateFormat = DEFAULT_DATE_FORMAT;

	@Override
	public boolean asyncDecode( byte[] cachedData ) {
		return false;
	}
	  
	@Override
	public byte[] encode( Object o ) {
		if( o == null  ){
			log.warn( "invalid Object: null" );
			return null;
		}
		byte[] b = null;
		if( o instanceof String ) {
			b = SafeEncoder.encode( (String)o );
		}
		else if( o instanceof Long ) {
			b = SafeEncoder.encode( String.valueOf((Long)o) );
		}
		else if( o instanceof Integer ) {
			b = SafeEncoder.encode( String.valueOf((Integer)o) );
		}
		else if( o instanceof Short ) {
			b = SafeEncoder.encode( String.valueOf((Short)o) );
		}
		else if( o instanceof Float ) {
			b = SafeEncoder.encode( String.valueOf((Float)o) );
		}
		else if( o instanceof Double ) {
			b = SafeEncoder.encode( String.valueOf((Double)o) );
		}
		else if( o instanceof Boolean ) {
			b = SafeEncoder.encode( String.valueOf((Boolean)o) );
		}
		else if( o instanceof Date ) {
			b = SafeEncoder.encode( DateUtil.formatDate( (Date)o, dateFormat) );
		}
		else {
			b = SafeEncoder.encode( JsonEncoder.encode( o, dateFormat ) );
		}
		if( b.length > maxSize ) {
			throw new IllegalArgumentException( "Cannot cache data larger than " + maxSize + " bytes (you tried to cache a "
			        + b.length + " byte object)" );
		}
		return b;
	}

	@Override
	public String decode( byte[] cachedData ) {
		if( cachedData == null ){
			log.debug( " cachedData is null " );
			return null;
		}
		return SafeEncoder.encode(cachedData);

	}

	@Override
    public byte[] getKeyBytes( String key ) {
		if( StringUtil.isEmpty( key ) )
			throw new IllegalArgumentException( "Key is empty" );
		
		byte[] keyBytes = SafeEncoder.encode( key );
		
		if( keyBytes.length > 250 ) {
			throw new IllegalArgumentException( "Key is too long (maxlen = 250)" );
		}
		
		for( byte b : keyBytes )
			if( (b == 10) || (b == 13) || (b == 0) ) //(b == 32) || 
				throw new IllegalArgumentException( new StringBuilder().append( "Key contains invalid characters:'" )
				        .append( key ).append( "'" ).toString() );
		return keyBytes;
	}
	
	@Override
    public byte[][] getKeyBytes( String... keys ) {
		byte[][] many = new byte[keys.length][];
		int i = 0;
		for( String s : keys ) {
			many[i++] = getKeyBytes( s );
		}
		return many;
	}
	
	@Override
	public String getKeyString( byte[] key ) {
		return SafeEncoder.encode( key );
	}

	public int getMaxSize() {
    	return maxSize;
    }

	public void setMaxSize( int maxSize ) {
    	this.maxSize = maxSize;
    }

	public String getDateFormat() {
    	return dateFormat;
    }

	public void setDateFormat( String dateFormat ) {
    	this.dateFormat = dateFormat;
    }

	public static StringTranscoder getDefaultinstance() {
	    return defaultInstance;
    }
	
	public static void main(String args[] ){
		StringTranscoder t = new StringTranscoder();
		byte[] d = t.encode( 132 );
		System.out.println( StringUtil.toHex( d ) );
		System.out.println( t.decode( d ) );
	}
}
