package com.aepan.sysmgr.common.redis.jedis;

import java.io.UnsupportedEncodingException;
import java.util.Date;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aepan.sysmgr.util.Serializer;
import com.aepan.sysmgr.util.StringUtil;

import redis.clients.util.SafeEncoder;

/**
 * Handles old whalin encoding: data type and flags is in the first byte of the
 * value.
 * @author Administrator
 * 
 */
public class WhalinV1Transcoder implements Transcoder<Object> {
	private Logger log = LoggerFactory.getLogger(WhalinV1Transcoder.class);

	public static final int SPECIAL_BYTE = 1;
	public static final int SPECIAL_BOOLEAN = 2;
	public static final int SPECIAL_INTEGER = 3;
	public static final int SPECIAL_LONG = 4;
	public static final int SPECIAL_CHARACTER = 5;
	public static final int SPECIAL_STRING = 6;
	public static final int SPECIAL_STRINGBUFFER = 7;
	public static final int SPECIAL_FLOAT = 8;
	public static final int SPECIAL_SHORT = 9;
	public static final int SPECIAL_DOUBLE = 10;
	public static final int SPECIAL_DATE = 11;
	public static final int SPECIAL_STRINGBUILDER = 12;

	public static final int COMPRESSED = 64;
	public static final int SERIALIZED = 32;

	public static final int MAX_SIZE = 20 * 1024 * 1024;

	/**
	 * Default compression threshold value.
	 */
	public static final int DEFAULT_COMPRESSION_THRESHOLD = 16384;

	public static final String DEFAULT_CHARSET = "UTF-8";
	
	public static final WhalinV1Transcoder defaultInstance = new WhalinV1Transcoder();

	protected int compressionThreshold = DEFAULT_COMPRESSION_THRESHOLD;

	protected String charset = DEFAULT_CHARSET;

	private int maxSize = MAX_SIZE;

	//对int,long,float,double等变量的0进行压缩
	private boolean packZeros = true;
	
	@Override
	public boolean asyncDecode( byte[] cachedData ) {
		return ((cachedData[0] & COMPRESSED) != 0 || (cachedData[0] & SERIALIZED) != 0);
	}
	  
	@Override
	public byte[] encode( Object o ) {
		if( o == null  ){
			log.warn( "invalid Object: null" );
			return null;
		}
		byte[] b = null;
		int flags = 0;
		if( o instanceof String ) {
			b = encodeW1String( (String)o );
		}
		else if( o instanceof Long ) {
			b = encodeLong( (Long)o );
		}
		else if( o instanceof Integer ) {
			b = encodeInteger( (Integer)o );
		}
		else if( o instanceof Short ) {
			b = encodeShort( (Short)o );
		}
		else if( o instanceof Float ) {
			b = encodeFloat( (Float)o );
		}
		else if( o instanceof Double ) {
			b = encodeDouble( (Double)o );
		}
		else if( o instanceof Boolean ) {
			b = encodeBoolean( (Boolean)o );
		}
		else if( o instanceof Date ) {
			b = encodeLong( ((Date)o).getTime(), SPECIAL_DATE );
		}
		else if( o instanceof Byte ) {
			b = encodeByte( (Byte)o );
		}
		else if( o instanceof Character ) {
			b = encodeCharacter( (Character)o );
		}
		else if( o instanceof StringBuffer ) {
			b = encodeStringBuffer( (StringBuffer)o );
		}
		else if( o instanceof StringBuilder ) {
			b = encodeStringbuilder( (StringBuilder)o );
		}
		else {
			b = Serializer.object2Byte( o );
			byte[] ret = new byte[b.length + 1];
			ret[0] = 0;
			System.arraycopy( b, 0, ret, 1, b.length );
			b = ret;
			flags |= SERIALIZED;
		}
		assert b != null;
		if( b.length > compressionThreshold ) {
			byte[] compressed = Serializer.compress( b, 1, b.length - 1 );
			if( compressed.length < b.length - 1 ) {
				log.debug( "Compressed " + o.getClass().getName() + " from " + b.length + " to " + compressed.length );
				byte[] ret = new byte[compressed.length + 1];
				ret[0] = b[0];
				System.arraycopy( compressed, 0, ret, 1, compressed.length );
				b = ret;
				flags |= COMPRESSED;
			}
			else {
				log.debug( "Compression increased the size of " + o.getClass().getName() + " from " + b.length + " to "
				        + compressed.length );
			}
		}
		b[0] |= flags;
		if( b.length > maxSize ) {
			throw new IllegalArgumentException( "Cannot cache data larger than " + maxSize + " bytes (you tried to cache a "
			        + b.length + " byte object)" );
		}
		return b;
	}

	@Override
	public Object decode( byte[] cachedData ) {
		if( cachedData == null || cachedData.length <1 ){
			log.debug( "invalid cachedData: " + cachedData );
			//throw new NullPointerException("invalid cachedData: " + cachedData);
			return null;
		}
		byte[] data = cachedData;
		int flag = data[0];
		Object rv = null;

		if( (flag & COMPRESSED) != 0 ) {
			byte[] temp = Serializer.decompress( data, 1, data.length - 1 );
			byte[] ret = new byte[temp.length + 1];
			ret[0] = data[0];
			System.arraycopy( temp, 0, ret, 1, temp.length );
			data = ret;
		}
		if( (flag & SERIALIZED) != 0 ) {
			return Serializer.bytes2Object( data, 1, data.length - 1 );
		}
		switch( flag&0x0f ) {
			case SPECIAL_BOOLEAN:
				rv = decodeBoolean( data );
				break;
			case SPECIAL_INTEGER:
				rv = decodeInteger( data );
				break;
			case SPECIAL_SHORT:
				rv = decodeShort( data );
				break;
			case SPECIAL_LONG:
				rv = decodeLong( data );
				break;
			case SPECIAL_DATE:
				rv = new Date( decodeLong( data ) );
				break;
			case SPECIAL_BYTE:
				rv = decodeByte( data );
				break;
			case SPECIAL_FLOAT:
				rv = decodeFloat( data );
				break;
			case SPECIAL_DOUBLE:
				rv = decodeDouble( data );
				break;
			case SPECIAL_STRING:
				rv = decodeW1String( data );
				break;
			case SPECIAL_STRINGBUFFER:
				rv = new StringBuffer( decodeW1String( data ) );
				break;
			case SPECIAL_STRINGBUILDER:
				rv = new StringBuilder( decodeW1String( data ) );
				break;
			case SPECIAL_CHARACTER:
				rv = decodeCharacter( data );
				break;
			default:
				log.warn( "handle data as string with flag: " + flag );
				rv = SafeEncoder.encode(cachedData);
		}
		return rv;
	}

	private Short decodeShort( byte[] data ) {
		return Short.valueOf( (short)decodeInteger( data ).intValue() );
	}

	private Byte decodeByte( byte[] in ) {
		assert in.length == 2 : "Wrong length for a byte";
		byte value = in[1];
		return Byte.valueOf( value );

	}

	private Integer decodeInteger( byte[] in ) {
		assert in.length <= 5 : "Wrong length for an int";
		return Integer.valueOf( (int)decodeLong( in ).longValue() );

	}

	private Float decodeFloat( byte[] in ) {
		assert in.length <= 5 : "Wrong length for a float";
		Integer l = decodeInteger( in );
		return Float.valueOf( Float.intBitsToFloat( l.intValue() ) );
	}

	private Double decodeDouble( byte[] in ) {
		assert in.length <= 9 : "Wrong length for a double";
		Long l = decodeLong( in );
		return Double.valueOf( Double.longBitsToDouble( l.longValue() ) );
	}

	private Boolean decodeBoolean( byte[] in ) {
		assert in.length == 2 : "Wrong length for a boolean";
		return Boolean.valueOf( in[1] == 1 );
	}

	private Long decodeLong( byte[] in ) {
		long rv = 0L;
		for( int idx = 1; idx < in.length; idx++ ) {
			byte i = in[idx];
			rv = (rv << 8) | (i < 0 ? 256 + i : i);
		}
		return Long.valueOf( rv );
	}

	private Character decodeCharacter( byte[] b ) {
		return Character.valueOf( (char)decodeInteger( b ).intValue() );
	}

	private String decodeW1String( byte[] b ) {
		try {
			return new String( b, 1, b.length - 1, charset );
		}
		catch( UnsupportedEncodingException e ) {
			throw new RuntimeException( e );
		}
	}

	private byte[] encodeByte( Byte value ) {
		byte[] b = new byte[2];
		b[0] = SPECIAL_BYTE;
		b[1] = value.byteValue();
		return b;
	}

	private byte[] encodeBoolean( Boolean value ) {
		byte[] b = new byte[2];
		b[0] = SPECIAL_BOOLEAN;
		b[1] = (byte)(value.booleanValue() ? 1 : 0);
		return b;
	}

	private byte[] encodeInteger( Integer value ) {
		byte[] b = encodeNum( value, 4 );
		b[0] = SPECIAL_INTEGER;
		return b;
	}

	private byte[] encodeLong( Long value, int type ) {
		byte[] b = encodeNum( value, 8 );
		b[0] = (byte)type;
		return b;
	}

	private byte[] encodeLong( Long value ) {
		return encodeLong( value, SPECIAL_LONG );
	}

	private byte[] encodeShort( Short value ) {
		byte[] b = encodeInteger( (int)value.shortValue() );
		b[0] = SPECIAL_SHORT;
		return b;
	}

	private byte[] encodeFloat( Float value ) {
		byte[] b = encodeInteger( Float.floatToIntBits( value ) );
		b[0] = SPECIAL_FLOAT;
		return b;
	}

	private byte[] encodeDouble( Double value ) {
		byte[] b = encodeLong( Double.doubleToLongBits( value ) );
		b[0] = SPECIAL_DOUBLE;
		return b;
	}

	private byte[] encodeCharacter( Character value ) {
		byte[] result = encodeInteger( (int)value.charValue() );
		result[0] = SPECIAL_CHARACTER;
		return result;
	}

	private byte[] encodeStringBuffer( StringBuffer value ) {
		byte[] b = encodeW1String( value.toString() );
		b[0] = SPECIAL_STRINGBUFFER;
		return b;
	}

	private byte[] encodeStringbuilder( StringBuilder value ) {
		byte[] b = encodeW1String( value.toString() );
		b[0] = SPECIAL_STRINGBUILDER;
		return b;
	}

	private byte[] encodeW1String( String value ) {
		byte[] svalue = null;
		try {
			svalue = value.getBytes( charset );
		}
		catch( UnsupportedEncodingException e ) {
			throw new RuntimeException( e );
		}
		byte[] result = new byte[svalue.length + 1];
		System.arraycopy( svalue, 0, result, 1, svalue.length );
		result[0] = SPECIAL_STRING;
		return result;
	}

	private byte[] encodeNum( long l, int maxBytes ) {
		byte[] rv = new byte[maxBytes+1];
		for( int i = 0; i < rv.length-1; i++ ) {
			int pos = rv.length - i - 1;
			rv[pos] = (byte)((l >> (8 * i)) & 0xff);
		}
		if( packZeros ) {
			int firstNon0 = 1;
			// Just looking for what we can reduce
			while( firstNon0 < rv.length && rv[firstNon0] == 0 ) {
				firstNon0++;
			}
			if( firstNon0 > 1 ) {
				byte[] tmp = new byte[rv.length - firstNon0+1];
				System.arraycopy( rv, firstNon0, tmp, 1, rv.length - firstNon0 );
				rv = tmp;
			}
		}
		return rv;
	}
	
	@Override
    public byte[] getKeyBytes( String key ) {
		if( StringUtil.isEmpty( key ) )
			throw new IllegalArgumentException( "Key is empty" );
		
		byte[] keyBytes = null;
		try {
			keyBytes = key.getBytes( charset );
		}
		catch( UnsupportedEncodingException e ) {
			keyBytes = key.getBytes();
		}
		if( keyBytes.length > 250 ) {
			throw new IllegalArgumentException( "Key is too long (maxlen = 250)" );
		}

		for( byte b : keyBytes )
			if( (b == 10) || (b == 13) || (b == 0) ) // (b == 32) || 
				throw new IllegalArgumentException( new StringBuilder().append( "Key contains invalid characters:  '" )
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
		try {
			return new String( key, charset );
		}
		catch( UnsupportedEncodingException e ) {
		}
		return null;
	}

	public int getCompressionThreshold() {
    	return compressionThreshold;
    }

	public void setCompressionThreshold( int compressionThreshold ) {
    	this.compressionThreshold = compressionThreshold;
    }

	public String getCharset() {
    	return charset;
    }

	public void setCharset( String charset ) {
    	this.charset = charset;
    }

	public int getMaxSize() {
    	return maxSize;
    }

	public void setMaxSize( int maxSize ) {
    	this.maxSize = maxSize;
    }

	/**
	 * ��Ϊtrueʱ����int,long,float,double�ȱ�����0����ѹ��.
	 * Ĭ����true
	 * @return
	 */
	public boolean isPackZeros() {
    	return packZeros;
    }

	public void setPackZeros( boolean packZeros ) {
    	this.packZeros = packZeros;
    }

	public static void main(String args[] ){
		WhalinV1Transcoder t = new WhalinV1Transcoder();
		t.setPackZeros( true );
		byte[] d = t.encode( 132 );
		System.out.println( StringUtil.toHex( d ) );
		System.out.println( t.decode( d ) );
	}
}
