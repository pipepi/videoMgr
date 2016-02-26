package com.aepan.sysmgr.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 对象序列化和反序列化的工具类
 *
 */
public class Serializer {

	private static final Logger logger = LoggerFactory.getLogger(Serializer.class);

	
	/**
	 * 将对象序列化后转化为16进制的字符串
	 * @param obj 可以序列化的对象
	 * @return 16进制的字符串
	 */
	public static String object2HexString( Object obj ) {
		return StringUtil.toHex( object2Byte( obj ) );
	}
	
	
	/**
	 * 将对象序列化为字节数组
	 * @param obj 可以序列化的对象
	 * @return 字节数组
	 */
	public static byte[] object2Byte( Object obj ) {
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
    	object2Stream( obj, out );
    	return out.toByteArray();
	}

	/**
	 * 将对象序列化，并保存到文件中
	 * @param obj 可以序列化的对象
	 * @param fileName 保存的文件名全路径
	 */
	public static void object2File( Object obj, String fileName ) {
		object2File( obj, new File( fileName ) );
	}
	
	/**
	 * 将对象序列化，并保存到文件中
	 * @param obj 可以序列化的对象
	 * @param file 保存的文件
	 */
	public static void object2File( Object obj, File file ) {
		OutputStream out = null;
		try {
			if( file.getParentFile() != null  && !file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			out = new BufferedOutputStream( new FileOutputStream( file ) );
			object2Stream( obj, out );
		} catch (FileNotFoundException e) {
    		logger.error( "file no found:"+  file  );
			return;
		}
		finally {
			close( out );
		}
	}
	
	/**
	 * 将对象序列化，并保存到输出流中
	 * @param obj 可以序列化的对象
	 * @param out 保存的输出流
	 */
	public static void object2Stream( Object obj, OutputStream out ) {
		if (obj instanceof Serializable) {
            try {
                ObjectOutputStream oos = new ObjectOutputStream( out );
                //oos.writeUnshared(obj);
                oos.writeObject(obj);
            }catch (Exception e) {
        		logger.error( "encode obj:"+  obj + ",e:" + e );
            }
            return;
        }
		logger.error( "obj is not Serializable instance:"+  obj );
	}
	
	/**
	 * 将16进制字串反序列化为对象
	 * @param hexString 16进制字串
	 * @return 对应的对象
	 */
	public static Object hexString2Object( String hexString ) {
		if( StringUtil.isEmpty( hexString ) ) return null;
		return bytes2Object( StringUtil.hex2Bytes( hexString ) );
	}
	
	/**
	 * 将字节数组反序列化为对象
	 * @param buf 字节数组
	 * @return 对应的对象
	 */
	public static Object bytes2Object( byte[] buf ) {
		return bytes2Object( buf, 0, buf.length );
	}
	
	public static Object bytes2Object( byte[] buf, int offset, int length ) {
    	ByteArrayInputStream in = new ByteArrayInputStream(buf, offset, length);
        try {
            return stream2Object( in );
        } finally {
			close( in );
        }		
	}
	
	/**
	 * 从文件中反序列化为对象
	 * @param fileName 数据源文件名全路径
	 * @return 对应的对象
	 */
	public static Object file2Object( String fileName ) {
		return file2Object( new File( fileName ) );
	}
	
	/**
	 * 从文件中反序列化为对象
	 * @param file 数据源文件
	 * @return 对应的对象
	 */
	public static Object file2Object( File file ) {
		InputStream in = null;
        try {
        	in = new BufferedInputStream( new FileInputStream( file ) );
            return stream2Object( in );
        } catch (FileNotFoundException e) {
    		logger.error( "file no found:"+  file ,e );
    		return null;
		} finally {
			close( in );
        }		
	}	
	
	/**
	 * 从输入流中反序列化为对象
	 * @param in 数据源输入流
	 * @return 对应的对象
	 */
	public static Object stream2Object( InputStream in ) {
		ObjectInputStream is = null;
        try {
            is = new ObjectInputStream( in );
            Object obj = is.readObject();
            return obj;
        } catch (EOFException e) {
    		logger.error( "decode error:"+  e  );
        	return null;
        } catch (StreamCorruptedException e) {
    		logger.error( "decode error:"+  e  );
        	return null;
        } catch (IOException e) {
    		logger.error( "decode error:"+  e  );
        	return null;
		} catch (ClassNotFoundException e) {
    		logger.error( "decode error:"+  e  );
        	return null;
		} 	
	}
	
	/**
	 * Compress the given array of bytes.
	 */
	public static byte[] compress( byte[] in, int offset, int length ) {
		if( in == null ) {
			throw new NullPointerException( "Can't compress null" );
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		GZIPOutputStream gz = null;
		try {
			gz = new GZIPOutputStream( bos );
			gz.write( in, offset, length );
		}
		catch( IOException e ) {
			throw new RuntimeException( "IO exception compressing data", e );
		}
		finally {
			close( gz );
			close( bos );
		}
		byte[] rv = bos.toByteArray();
		logger.debug( "Compressed " + in.length + " bytes to " + rv.length );
		return rv;
	}

	/**
	 * Decompress the given array of bytes.
	 * 
	 * @return null if the bytes cannot be decompressed
	 */
	public static byte[] decompress( byte[] in, int offset, int length ) {
		ByteArrayOutputStream bos = null;
		if( in != null ) {
			ByteArrayInputStream bis = new ByteArrayInputStream( in, offset, length );
			bos = new ByteArrayOutputStream();
			GZIPInputStream gis = null;
			try {
				gis = new GZIPInputStream( bis );

				byte[] buf = new byte[8192];
				int r = -1;
				while( (r = gis.read( buf )) > 0 ) {
					bos.write( buf, 0, r );
				}
			}
			catch( IOException e ) {
				logger.warn( "Failed to decompress data", e );
			}
			finally {
				close( gis );
				close( bis );
				close( bos );
			}
		}
		return bos == null ? null : bos.toByteArray();
	}

	public static void close( Closeable closeable ) {
		if( closeable != null ) {
			try {
				closeable.close();
			}
			catch( Exception e ) {
				logger.info( "Unable to close " + closeable, e );
			}
		}
	}
}
