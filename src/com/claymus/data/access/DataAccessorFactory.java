package com.claymus.data.access;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.ClaymusHelper;

public class DataAccessorFactory {
	
	private static final String GOOGLE_CLOUD_STORAGE_BUCKET =
			ClaymusHelper.getSystemProperty( "blobservice.gcs.bucket" );

	protected static final Memcache cacheL1 = new MemcacheClaymusImpl();
	protected static final Memcache cacheL2 = new MemcacheGaeImpl();
	private static BlobAccessor blobAccessor = new BlobAccessorGcsImpl( GOOGLE_CLOUD_STORAGE_BUCKET );
	

	public static Memcache getL1CacheAccessor() {
		return cacheL1;
	}

	public static Memcache getL2CacheAccessor() {
		return cacheL2;
	}
	
	public static DataAccessor getDataAccessor( HttpServletRequest request ) {
		DataAccessor dataAccessor = cacheL1.get( "DataAccessor-" + request.hashCode() );
		if( dataAccessor == null ) {
			dataAccessor = new DataAccessorGaeImpl();
			dataAccessor = new DataAccessorWithMemcache( dataAccessor, cacheL2 );
			dataAccessor = new DataAccessorWithMemcache( dataAccessor, new MemcacheClaymusImpl() );
			cacheL1.put( "DataAccessor-" + request.hashCode(), dataAccessor );
		}
		return dataAccessor;
	}
	
	public static BlobAccessor getBlobAccessor() {
		return new BlobAccessorWithMemcache( blobAccessor, cacheL2 );
	}
	
}
