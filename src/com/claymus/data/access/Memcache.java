package com.claymus.data.access;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public interface Memcache {

	<K, T extends Serializable> T get( K key );

	<K, T extends Serializable> Map<K, T> getAll( Collection<K> keys );

	<K, T extends Serializable> void put( K key, T value, long expirationDeltaMillis );

	<K, T extends Serializable> void put( K key, T value );

	<K, T extends Serializable> void putAll( Map<K, T> keyValueMap );

	<K> void remove( K key );

	void flush();

}
