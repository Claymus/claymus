package com.claymus.data.access;

import java.io.Serializable;

public interface Memcache {

	<K, T extends Serializable> T get( K key );

	<K, T extends Serializable> void put( K key, T value, long expirationDeltaMillis );

	<K, T extends Serializable> void put( K key, T value );

	<K> void remove( K key );

	void flush();

}
