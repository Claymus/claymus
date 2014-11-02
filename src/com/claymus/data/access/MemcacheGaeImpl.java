package com.claymus.data.access;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheManager;

import com.google.appengine.api.memcache.InvalidValueException;
import com.google.appengine.api.memcache.stdimpl.GCacheFactory;

public class MemcacheGaeImpl implements Memcache {

	private static final Logger logger =
			Logger.getLogger( MemcacheGaeImpl.class.getName() );

	
	@SuppressWarnings("unchecked")
	public <K, T extends Serializable> T get( K key ) {
		try {
			Cache cache = CacheManager.getInstance().getCacheFactory()
					.createCache( Collections.emptyMap() );
			T value = (T) cache.get(key);
			if( value == null )
				logger.log( Level.INFO, "Cache Miss: " + key );
			else
				logger.log( Level.INFO, "Cache Hit: " + key );
			return value;
		} catch( InvalidValueException e ) {
			logger.log( Level.SEVERE, "Failed to typecaste cached value to required type.", e );
			return null;
		} catch( CacheException e ) {
			logger.log( Level.SEVERE, "Failed to create cache instance.", e );
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <K, T extends Serializable> void put(
			K key, T value, long expirationDeltaMillis ) {

		if( expirationDeltaMillis <= 0 )
			return;

		Map props = new HashMap();
		props.put( GCacheFactory.EXPIRATION_DELTA_MILLIS, expirationDeltaMillis );

		try {
			Cache cache = CacheManager.getInstance().getCacheFactory()
					.createCache(props);
			cache.put( key, value );
		} catch( CacheException ex ) {
			logger.log( Level.SEVERE, "Failed to create cache instance.", ex);
		}
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <K, T extends Serializable> void put( K key, T value ) {
		Map props = Collections.emptyMap();

		try {
			Cache cache = CacheManager.getInstance().getCacheFactory()
					.createCache( props );
			cache.put(key, value);
		} catch (CacheException ex) {
			logger.log( Level.SEVERE, "Failed to create cache instance.", ex);
		}
	}

	public <K> void remove( K key ) {
		try {
			Cache cache = CacheManager.getInstance().getCacheFactory()
					.createCache( Collections.emptyMap() );
			cache.remove( key );
		} catch (CacheException ex) {
			logger.log( Level.SEVERE, "Failed to create cache instance.", ex);
		}
	}

	public void flush() {
		try {
			Cache cache = CacheManager.getInstance().getCacheFactory()
					.createCache( Collections.emptyMap() );
			cache.clear();
		} catch( CacheException ex ) {
			logger.log( Level.SEVERE, "Failed to create cache instance.", ex);
		}
	}

}
