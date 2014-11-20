package com.claymus.websitewidget;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.claymus.data.transfer.WebsiteWidget;
import com.claymus.service.shared.data.WebsiteWidgetData;

public final class WebsiteWidgetRegistry {

	private static final Logger logger = 
			Logger.getLogger( WebsiteWidgetRegistry.class.getName() );

	
	@SuppressWarnings("rawtypes")
	private static final List<WebsiteWidgetHelper> helperList = new LinkedList<>();
	
	@SuppressWarnings("rawtypes")
	private static final Map<
			Class<? extends WebsiteWidgetData>,
			WebsiteWidgetHelper > mapWidgetDataToHelper = new HashMap<>();

	private static final Map<
			Class<? extends WebsiteWidget>,
			WebsiteWidgetProcessor<? extends WebsiteWidget> > mapWidgetToProcessor = new HashMap<>();


	public static <
			P extends WebsiteWidget,
			Q extends WebsiteWidgetData,
			R extends WebsiteWidgetProcessor<P>,
			S extends WebsiteWidgetHelper<P, Q, R> > void register( Class<S> websiteWidgetFactoryClass ) {
		
		ParameterizedType parameterizedType =
				(ParameterizedType) websiteWidgetFactoryClass.getGenericSuperclass();
		
		@SuppressWarnings("unchecked")
		Class<P> websiteWidgetClass =
				(Class<P>) parameterizedType.getActualTypeArguments()[0];
		@SuppressWarnings("unchecked")
		Class<Q> websiteWidgetDataClass =
				(Class<Q>) parameterizedType.getActualTypeArguments()[1];
		@SuppressWarnings("unchecked")
		Class<R> websiteWidgetProcessorClass = 
				(Class<R>) parameterizedType.getActualTypeArguments()[2];
		
		try {
			R websiteWidgetProcessor = websiteWidgetProcessorClass.newInstance();
			S websiteWidgetHelper = websiteWidgetFactoryClass.newInstance();
			helperList.add( websiteWidgetHelper );
			mapWidgetDataToHelper.put( websiteWidgetDataClass, websiteWidgetHelper );
			mapWidgetToProcessor.put( websiteWidgetClass, websiteWidgetProcessor );
		} catch ( InstantiationException | IllegalAccessException e ) {
			logger.log( Level.SEVERE, "Module registration failed.", e );
		}
	}
	
	
	@SuppressWarnings("rawtypes")
	public static List<WebsiteWidgetHelper> getWebsiteWidgetHelperList() {
		return helperList;
	}
	
	@SuppressWarnings("rawtypes")
	public static <
			Q extends WebsiteWidgetData> WebsiteWidgetHelper getWebsiteWidgetHelper(
					Class<Q> websiteWidgetDataClass ) {
		
		return mapWidgetDataToHelper.get( websiteWidgetDataClass );
	}
	
	@SuppressWarnings("unchecked")
	public static <P extends WebsiteWidget> WebsiteWidgetProcessor<P> getWebsiteWidgetProcessor(
			Class<P> websiteWidgetClass ) {
		
		if( websiteWidgetClass.isInterface() )
			return (WebsiteWidgetProcessor<P>) mapWidgetToProcessor.get( websiteWidgetClass );
		else
			return (WebsiteWidgetProcessor<P>) mapWidgetToProcessor.get( websiteWidgetClass.getInterfaces()[0] );
	}
	
}
