package com.claymus.data.access;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MemcacheClaymusImpl implements Memcache {

	private static final Logger logger = 
			Logger.getLogger( MemcacheClaymusImpl.class.getName() );

	private final int cacheSize = 128;
	@SuppressWarnings("serial")
	private final Map<Object, Object> cache
			= new LinkedHashMap<Object, Object>( 16, 0.75f, true ) {
	
		@Override
		protected boolean removeEldestEntry( Map.Entry<Object, Object> eldest ) {
			return size() >= cacheSize;
		}		
	
	};

	
	@SuppressWarnings("unchecked")
	public <K, T extends Serializable> T get( K key ) {
		T value = (T) cache.get(key);
		if( value == null )
			logger.log( Level.INFO, "Cache Miss: " + key );
		else
			logger.log( Level.INFO, "Cache Hit: " + key );
		return value;
	}

	public <K, T extends Serializable> void put(
			K key, T value, long expirationDeltaMillis ) {

		cache.put( key, value );
	}

	public <K, T extends Serializable> void put( K key, T value ) {
		cache.put( key, value );
	}

	public <K> void remove( K key ) {
		cache.remove( key );
	}

	public void flush() {
		cache.clear();
	}
	
}
