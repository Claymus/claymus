package com.claymus.data.access.gae;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.claymus.data.transfer.PageLayout;
import com.google.appengine.api.datastore.Text;

@SuppressWarnings("serial")
@PersistenceCapable( table = "PAGE_LAYOUT" )
public class PageLayoutEntity implements PageLayout {
	
	@PrimaryKey
	@Persistent( column = "PAGE_LAYOUT_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;
	
	@Persistent( column = "NAME" )
	private String name;

	@Persistent( column = "TEMPLATE" )
	private Text template;
	
	
	@Override
	public Long getId() {
		return id;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName( String name ) {
		this.name = name;
	}
	
	@Override
	public String getTemplate() {
		return template == null ? null : template.getValue();
	}
	
	@Override
	public void setTemplate( String template ) {
		this.template = new Text( template );
	}
	
}