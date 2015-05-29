package com.claymus.data.access.gae;

import java.util.Date;

import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.claymus.data.transfer.WebsiteWidget;

@SuppressWarnings("serial")
@PersistenceCapable( table = "WEBSITE_WIDGET" )
@Discriminator( column = "TYPE", strategy = DiscriminatorStrategy.CLASS_NAME )
public abstract class WebsiteWidgetEntity implements WebsiteWidget {

	@PrimaryKey
	@Persistent( column = "WEBSITE_WIDGET_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;
	
	@Persistent( column = "HOME" )
	private String home;
	
	@Persistent( column = "PAGE_TYPE_LIST" )
	private String[] pageTypeList;

	@Persistent( column = "PAGE_TYPE_LIST_INCLUSIVE" )
	private Boolean pageTypeListInclusive;

	@Persistent( column = "POSITION" )
	private String position;
	
	@Persistent( column = "ORDER" )
	private Integer order;
	
	@Persistent( column = "CREATION_DATE" )
	private Date creationDate;
	
	@Persistent( column = "LAST_UPDATED" )
	private Date lastUpdated;

	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getHome() {
		return home;
	}

	@Override
	public void setHome( String home ) {
		this.home = home;
	}

	@Override
	public String[] getPageTypeList() {
		return pageTypeList == null ? new String[0] : pageTypeList;
	}
	
	@Override
	public boolean isPageTypeListInclusive() {
		return pageTypeListInclusive == null ? false : pageTypeListInclusive;
	}
	
	@Override
	public void setPageTypeList( String[] pageTypeList ) {
		setPageTypeList( pageTypeList, true );
	}
	
	@Override
	public void setPageTypeList( String[] pageTypeList, boolean inclusive ) {
		this.pageTypeList = pageTypeList;
		this.pageTypeListInclusive = inclusive;
	}

	@Override
	public String getPosition() {
		return position;
	}

	@Override
	public void setPosition( String position ) {
		this.position = position;
	}

	@Override
	public Integer getOrder() {
		return order;
	}

	@Override
	public void setOrder( Integer order ) {
		this.order = order;
	}

	@Override
	public Date getCreationDate() {
		return creationDate;
	}

	@Override
	public void setCreationDate( Date creationDate ) {
		this.creationDate = creationDate;
	}

	@Override
	public Date getLastUpdated() {
		return lastUpdated;
	}

	@Override
	public void setLastUpdated( Date lastUpdated ) {
		this.lastUpdated = lastUpdated;
	}

}
