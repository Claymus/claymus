package com.claymus.pagecontent;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.claymus.data.transfer.PageContent;
import com.claymus.service.shared.data.PageContentData;

public class PageContentRegistry {

	private static final Logger logger = 
			Logger.getLogger( PageContentRegistry.class.getName() );

	
	@SuppressWarnings("rawtypes")
	private static final List<PageContentHelper> helperList = new LinkedList<>();
	
	@SuppressWarnings("rawtypes")
	private static final Map<
			Class<? extends PageContentData>,
			PageContentHelper > mapContentDataToHelper = new HashMap<>();

	private static final Map<
			Class<? extends PageContent>,
			PageContentProcessor<? extends PageContent> > mapContentToProcessor = new HashMap<>();


	public static <
			P extends PageContent,
			Q extends PageContentData,
			R extends PageContentProcessor<P>,
			S extends PageContentHelper<P, Q, R> > void register( Class<S> pageContentFactoryClass ) {
		
		ParameterizedType parameterizedType =
				(ParameterizedType) pageContentFactoryClass.getGenericSuperclass();
		
		@SuppressWarnings("unchecked")
		Class<P> pageContentClass =
				(Class<P>) parameterizedType.getActualTypeArguments()[0];
		@SuppressWarnings("unchecked")
		Class<Q> pageContentDataClass =
				(Class<Q>) parameterizedType.getActualTypeArguments()[1];
		@SuppressWarnings("unchecked")
		Class<R> pageContentProcessorClass = 
				(Class<R>) parameterizedType.getActualTypeArguments()[2];
		
		try {
			R pageContentProcessor = pageContentProcessorClass.newInstance();
			S pageContentHelper = pageContentFactoryClass.newInstance();
			helperList.add( pageContentHelper );
			mapContentDataToHelper.put( pageContentDataClass, pageContentHelper );
			mapContentToProcessor.put( pageContentClass, pageContentProcessor );
		} catch ( InstantiationException | IllegalAccessException e ) {
			logger.log( Level.SEVERE, "Module registration failed.", e );
		}
	}
	

	@SuppressWarnings("rawtypes")
	public static List<PageContentHelper> getPageContentHelperList() {
		return helperList;
	}
	
	@SuppressWarnings("rawtypes")
	public static <
			Q extends PageContentData> PageContentHelper getPageContentHelper(
					Class<Q> pageContentDataClass ) {
		
		return mapContentDataToHelper.get( pageContentDataClass );
	}
	
	@SuppressWarnings("unchecked")
	public static <P extends PageContent> PageContentProcessor<P> getPageContentProcessor(
			Class<P> pageContentClass ) {
		
		if( pageContentClass.isInterface() )
			return (PageContentProcessor<P>) mapContentToProcessor.get( pageContentClass );
		else
			return (PageContentProcessor<P>) mapContentToProcessor.get( pageContentClass.getInterfaces()[0] );
	}
	
}
