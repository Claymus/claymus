package com.claymus.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.commons.client.InsufficientAccessException;
import com.claymus.commons.client.UnexpectedServerException;
import com.claymus.commons.server.ClaymusHelper;
import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.transfer.Page;
import com.claymus.data.transfer.PageContent;
import com.claymus.data.transfer.PageLayout;
import com.claymus.data.transfer.WebsiteLayout;
import com.claymus.data.transfer.WebsiteWidget;
import com.claymus.module.websitewidget.WebsiteWidgetProcessor;
import com.claymus.module.websitewidget.WebsiteWidgetRegistry;
import com.claymus.module.websitewidget.footer.FooterWidgetFactory;
import com.claymus.module.websitewidget.header.HeaderWidgetFactory;
import com.claymus.module.websitewidget.html.HtmlWidgetFactory;
import com.claymus.module.websitewidget.navigation.NavigationWidgetFactory;
import com.claymus.module.websitewidget.user.UserWidgetFactory;
import com.claymus.pagecontent.PageContentProcessor;
import com.claymus.pagecontent.PageContentRegistry;
import com.claymus.pagecontent.blog.BlogContent;
import com.claymus.pagecontent.blog.BlogContentHelper;
import com.claymus.pagecontent.blogpost.BlogPostContent;
import com.claymus.pagecontent.blogpost.BlogPostContentHelper;
import com.claymus.pagecontent.html.HtmlContent;
import com.claymus.pagecontent.html.HtmlContentHelper;
import com.claymus.pagecontent.roleaccess.RoleAccessContentHelper;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

@SuppressWarnings("serial")
public class ClaymusMain extends HttpServlet {
	
	private static final Logger logger = 
			Logger.getLogger( ClaymusMain.class.getName() );

	public static final PageContentRegistry PAGE_CONTENT_REGISTRY;
	public static final WebsiteWidgetRegistry WEBSITE_WIDGET_REGISTRY;

	public static final Configuration FREEMARKER_CONFIGURATION;
	
	static {
		PAGE_CONTENT_REGISTRY = new PageContentRegistry();
		WEBSITE_WIDGET_REGISTRY = new WebsiteWidgetRegistry();
		
		PageContentRegistry.register( HtmlContentHelper.class );
		PageContentRegistry.register( BlogContentHelper.class );
		PageContentRegistry.register( BlogPostContentHelper.class );
		PageContentRegistry.register( RoleAccessContentHelper.class );
		
		WEBSITE_WIDGET_REGISTRY.register( HeaderWidgetFactory.class );
		WEBSITE_WIDGET_REGISTRY.register( FooterWidgetFactory.class );
		WEBSITE_WIDGET_REGISTRY.register( NavigationWidgetFactory.class );
		WEBSITE_WIDGET_REGISTRY.register( UserWidgetFactory.class );
		WEBSITE_WIDGET_REGISTRY.register( HtmlWidgetFactory.class );
		
		FREEMARKER_CONFIGURATION = new Configuration();
		try {
			FREEMARKER_CONFIGURATION.setDirectoryForTemplateLoading(
					new File( System.getProperty("user.dir") + "/WEB-INF/classes/" ) );
		} catch ( IOException e ) {
			logger.log(
					Level.SEVERE,
					"Failed to set directory for FreeMarker template loading.",
					e );
		}
		FREEMARKER_CONFIGURATION.setObjectWrapper( new DefaultObjectWrapper() );
		FREEMARKER_CONFIGURATION.setDefaultEncoding( "UTF-8" );
		FREEMARKER_CONFIGURATION.setTemplateExceptionHandler( TemplateExceptionHandler.RETHROW_HANDLER );
		FREEMARKER_CONFIGURATION.setIncompatibleImprovements( new Version(2, 3, 20) ); // FreeMarker 2.3.20
	}

	
	@Override
	public void doGet(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {

		Page page = getPage( request );
		List<PageContent> pageContentList = getPageContentList( request );
		List<WebsiteWidget> websiteWidgetList = getWebsiteWidgetList( request );
		PageLayout pageLayout = getPageLayout();
		WebsiteLayout websiteLayout = getWebsiteLayout();
		
		response.setCharacterEncoding( "UTF-8" );

		if( pageContentList.size() == 0 ) {
			response.setStatus( HttpServletResponse.SC_NOT_FOUND );
			HtmlContent htmlContent = HtmlContentHelper.newHtmlContent();
			htmlContent.setContent( "Page not found !" );
			pageContentList.add( htmlContent );
		}
		
		PrintWriter out = response.getWriter();
		renderPage( page, pageContentList, websiteWidgetList, pageLayout, websiteLayout, out, request, response );
		out.close();
	}
	
	protected String getTemplateName() {
		return null;
	}
	
	private void renderPage(
			Page page,
			List<PageContent> pageContentList,
			List<WebsiteWidget> websiteWidgetList,
			PageLayout pageLayout,
			WebsiteLayout websiteLayout,
			PrintWriter out,
			HttpServletRequest request,
			HttpServletResponse response
			) throws IOException {

		Map<String, List<String>> websiteWidgetHtmlListMap = new HashMap<>();
		for( WebsiteWidget websiteWidget : websiteWidgetList ) {
			List<String> websiteWidgetHtmlList
					= websiteWidgetHtmlListMap.get( websiteWidget.getPosition() );

			if( websiteWidgetHtmlList == null ) {
				websiteWidgetHtmlList = new LinkedList<>();
				websiteWidgetHtmlListMap.put(
						websiteWidget.getPosition(),
						websiteWidgetHtmlList );
			}
			
			@SuppressWarnings("rawtypes")
			WebsiteWidgetProcessor websiteWidgetProcessor = 
					WEBSITE_WIDGET_REGISTRY.getWebsiteWidgetProcessor( websiteWidget.getClass() );
			@SuppressWarnings("unchecked")
			String websiteWidgetHtml = websiteWidgetProcessor.getHtml( websiteWidget );
			websiteWidgetHtmlList.add( websiteWidgetHtml );
		}
		
		List<String> pageContentHtmlList = new LinkedList<>();
		for( PageContent pageContent : pageContentList ) {
			@SuppressWarnings("rawtypes")
			PageContentProcessor pageContentProcessor =
					PAGE_CONTENT_REGISTRY.getPageContentProcessor( pageContent.getClass() );
			try {
				@SuppressWarnings("unchecked")
				String pageContentHtml = pageContentProcessor.getHtml( pageContent, request );
				pageContentHtmlList.add( pageContentHtml );
			} catch( InsufficientAccessException e ) {
				// TODO: add 405 messaage
			} catch( UnexpectedServerException e ) {
				logger.log( Level.SEVERE, "Failed to generate page content html.", e );
				response.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
			}
		}
		
		Map<String, Object> input = new HashMap<String, Object>();
		input.put( "page", page );
		input.put( "websiteWidgetHtmlListMap", websiteWidgetHtmlListMap );
		input.put( "pageContentHtmlList", pageContentHtmlList );
		input.put( "domain", ClaymusHelper.getSystemProperty( "domain" ) );
		
		try {
			Template template = FREEMARKER_CONFIGURATION
					.getTemplate( getTemplateName() );
			template.process( input, out);
		} catch ( IOException | TemplateException e ) {
			e.printStackTrace();
		}
	}
	
	protected Page getPage( HttpServletRequest request ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Page page = dataAccessor.newPage();

		String requestUri = request.getRequestURI();
		
		if( requestUri.equals( "/author-interviews" ) )
			page.setTitle( "Author Interviews" );

		else if( requestUri.equals( "/author-interview/new" ) )
			page.setTitle( "New Author Interview" );

		else if( requestUri.startsWith( "/author-interview/" ) ) {
			Long blogId = Long.parseLong( requestUri.substring( 18 ) );
			BlogPostContent blogPostContent =
					(BlogPostContent) dataAccessor.getPageContent( blogId );
			page.setTitle( blogPostContent.getTitle() );
			dataAccessor.destroy();

		} else if( requestUri.equals( "/roleaccess" ) )
			page.setTitle( "Access" );
		
		dataAccessor.destroy();
		
		return page;
		
	}
	
	protected List<PageContent> getPageContentList(
			HttpServletRequest request ) throws IOException {

		List<PageContent> pageContentList = new LinkedList<>();
		
		String requestUri = request.getRequestURI();

		if( requestUri.equals( "/author-interviews" ) ) {
			BlogContent blogContent = BlogContentHelper.newPostContent();
			blogContent.setCursor( null );
			blogContent.setPostCount( 5 );
			blogContent.setLastUpdated( new Date() );
			pageContentList.add( blogContent );
		
		} else if( requestUri.equals( "/author-interview/new" ) ) {
			DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
			BlogPostContent blogPost = BlogPostContentHelper.newBlogPostContent();
			blogPost.setTitle( "New Author Interview" );
			blogPost.setContent( "Interview content goes here ..." );
			blogPost.setLastUpdated( new Date() );
			dataAccessor.destroy();
			pageContentList.add( blogPost );
		
		} else if( requestUri.startsWith( "/author-interview/" ) ) {
			Long blogId = Long.parseLong( requestUri.substring( 18 ) );
			DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
			pageContentList.add( dataAccessor.getPageContent( blogId ) );
			dataAccessor.destroy();

		} else if( requestUri.equals( "/roleaccess" ) ) {
			pageContentList.add( RoleAccessContentHelper.newRoleAccessContent() );

		}
		
		return pageContentList;
	}

	protected List<WebsiteWidget> getWebsiteWidgetList(
			HttpServletRequest request ) throws IOException {
		
		return new LinkedList<>();
	}

	private PageLayout getPageLayout() {
		
		return new PageLayout() {

			@Override
			public Long getId() {
				return null;
			}

			@Override
			public String getName() {
				return null;
			}

			@Override
			public void setName( String name ) { }

			@Override
			public String getTemplate() {
				return "<#list pageContentList as pageContent>"
						+ "${pageContent}"
						+ "</#list>";
			}

			@Override
			public void setTemplate( String template ) { }
			
		};
		
	}
	
	private WebsiteLayout getWebsiteLayout() {
		
		return new WebsiteLayout() {

			@Override
			public Long getId() {
				return null;
			}

			@Override
			public String getName() {
				return null;
			}

			@Override
			public void setName( String name ) { }

			@Override
			public String getTemplate() {
				return "<#list websiteWidgetList as websiteWidget>"
						+ "${websiteWidget}"
						+ "</#list>";
			}

			@Override
			public void setTemplate( String template ) { }
			
		};
		
	}

}
