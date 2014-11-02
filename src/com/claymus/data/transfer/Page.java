package com.claymus.data.transfer;

import java.io.Serializable;
import java.util.Date;

public interface Page extends Serializable {

	Long getId();

	String getType();

	void setType( String type );

	String getUri();

	void setUri( String uri );

	String getUriAlias();

	void setUriAlias( String uri );

	String getTitle();

	void setTitle( String title );

	Long getPrimaryContentId();

	void setPrimaryContentId( Long pageContentId );

	Long getLayoutId();
	
	void setLayout( Long layoutId );
	
	Date getCreationDate();

	void setCreationDate( Date creationDate );

}