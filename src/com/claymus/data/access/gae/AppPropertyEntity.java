package com.claymus.data.access.gae;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.claymus.data.transfer.AppProperty;

@PersistenceCapable( table = "APP_PROPERTY" )
public class AppPropertyEntity implements AppProperty {

	private static final long serialVersionUID = 9031802007755452031L;

	@PrimaryKey
	@Persistent( column = "APP_PROPERTY_ID" )
	private String id;
	
	@Persistent( column = "VALUE", serialized = "true", defaultFetchGroup = "true" )
	private Object value;


	@Override
	public String getId() {
		return id;
	}

	public void setId( String id ) {
		this.id = id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getValue() {
		return (T) value;
	}

	@Override
	public <T> void setValue( T value ) {
		this.value = value;
	}
	
}
