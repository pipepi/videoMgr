package com.aepan.sysmgr.common.redis.jedis;

public interface Transcoder<T> {

	/**
	 * Should the transcoder be run asyncronously.
	 * 
	 * @return True if the cachedData should be decoded Asyncronously
	 */
	boolean asyncDecode( byte[] cachedData );

	/**
	 * Encode the given object for storage.
	 * 
	 * @param o the object
	 * @return the CachedData representing what should be sent
	 */
	byte[] encode( T o );

	/**
	 * Decode the cached object into the object it represents.
	 * 
	 * @param d the data
	 * @return the return value
	 */
	T decode( byte[] cachedData );

	/**
	 * Get the maximum size of objects handled by this transcoder.
	 */
	int getMaxSize();

	public byte[][] getKeyBytes( String... keys );

	public byte[] getKeyBytes( String key );
	
	public String getKeyString( byte[] key );

}
