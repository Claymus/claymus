package com.claymus.servlet;

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

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.server.FreeMarkerUtil;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.transfer.Page;
import com.claymus.data.transfer.PageContent;
import com.claymus.data.transfer.PageLayout;
import com.claymus.data.transfer.WebsiteLayout;
import com.claymus.data.transfer.WebsiteWidget;
import com.claymus.pagecontent.PageContentProcessor;
import com.claymus.pagecontent.PageContentRegistry;
import com.claymus.pagecontent.blog.BlogContentHelper;
import com.claymus.pagecontent.blogpost.BlogPostContent;
import com.claymus.pagecontent.blogpost.BlogPostContentHelper;
import com.claymus.pagecontent.html.HtmlContentHelper;
import com.claymus.pagecontent.pages.PagesContentHelper;
import com.claymus.pagecontent.roleaccess.RoleAccessContentHelper;
import com.claymus.pagecontent.user.UserContentHelper;
import com.claymus.websitewidget.WebsiteWidgetProcessor;
import com.claymus.websitewidget.WebsiteWidgetRegistry;
import com.claymus.websitewidget.footer.FooterWidgetHelper;
import com.claymus.websitewidget.header.HeaderWidgetHelper;
import com.claymus.websitewidget.html.HtmlWidgetHelper;
import com.claymus.websitewidget.navigation.NavigationWidgetHelper;

@SuppressWarnings("serial")
public class ClaymusMain extends HttpServlet {
	
	private static final Logger logger = 
			Logger.getLogger( ClaymusMain.class.getName() );

	
	static {
		PageContentRegistry.register( HtmlContentHelper.class );
		PageContentRegistry.register( BlogContentHelper.class );
		PageContentRegistry.register( BlogPostContentHelper.class );
		PageContentRegistry.register( UserContentHelper.class );
		PageContentRegistry.register( PagesContentHelper.class );		// 5.0
		PageContentRegistry.register( RoleAccessContentHelper.class );
		
		WebsiteWidgetRegistry.register( HtmlWidgetHelper.class );
		WebsiteWidgetRegistry.register( HeaderWidgetHelper.class );
		WebsiteWidgetRegistry.register( FooterWidgetHelper.class );
		WebsiteWidgetRegistry.register( NavigationWidgetHelper.class );
	}

	
	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public void doGet(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {

		ClaymusHelper claymusHelper = ClaymusHelper.get( request );
		
		Page page = getPage( request );
		List<PageContent> pageContentList = getPageContentList( request );
		List<WebsiteWidget> websiteWidgetList = claymusHelper.isModeEmbed()
				? new LinkedList<WebsiteWidget>()
				: getWebsiteWidgetList( request );
		PageLayout pageLayout = getPageLayout();
		WebsiteLayout websiteLayout = getWebsiteLayout();
		
		response.setCharacterEncoding( "UTF-8" );
		PrintWriter out = response.getWriter();


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
					WebsiteWidgetRegistry.getWebsiteWidgetProcessor( websiteWidget.getClass() );
			try {
				String websiteWidgetHtml = websiteWidgetProcessor.generateHtml( websiteWidget, request );
				websiteWidgetHtmlList.add( websiteWidgetHtml );
			} catch( InsufficientAccessException e ) {
				// Do nothing
			} catch( UnexpectedServerException e ) {
				logger.log( Level.SEVERE, "Failed to generate website widget html.", e );
				response.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
			}
		}
		
		
		List<String> pageContentHtmlList = new LinkedList<>();
		for( PageContent pageContent : pageContentList ) {
			@SuppressWarnings("rawtypes")
			PageContentProcessor pageContentProcessor =
					PageContentRegistry.getPageContentProcessor( pageContent.getClass() );
			try {
				String pageContentHtml = pageContentProcessor.getHtml( pageContent, request );
				pageContentHtmlList.add( pageContentHtml );
				if( page.getTitle() == null && page.getPrimaryContentId() != null && pageContent.getId().equals( page.getPrimaryContentId() ) )
					page.setTitle( pageContentProcessor.getTitle( pageContent, request ) );
			} catch( InvalidArgumentException | InsufficientAccessException e ) {
				// Do nothing
			} catch( UnexpectedServerException e ) {
				logger.log( Level.SEVERE, "Failed to generate page content html.", e );
				response.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
			}
		}
		
		
		if( pageContentHtmlList.size() == 0 ) {
			response.setStatus( HttpServletResponse.SC_NOT_FOUND );
			page.setTitle( "Page Not Found" );
			pageContentHtmlList.add( "Page Not Found !" );
		}

		
		Map<String, Object> input = new HashMap<String, Object>();
		input.put( "page", page );
		input.put( "websiteWidgetHtmlListMap", websiteWidgetHtmlListMap );
		input.put( "pageContentHtmlList", pageContentHtmlList );
		input.put( "request", request );
		input.put( "basicMode", claymusHelper.isModeBasic() );
		
		try {
			FreeMarkerUtil.processTemplate( input, getTemplateName( request ), out );
		} catch( UnexpectedServerException e ) {
			response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
		}
		
		out.close();
	}
	
	protected String getTemplateName( HttpServletRequest reqest ) {
		return this.getClass().getName().replaceAll( "[.]", "/" ).replace( "Main", "Template.ftl" );
	}
	
	protected Page getPage( HttpServletRequest request ) {
		
		String requestUri = request.getRequestURI();
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Page page = dataAccessor.getPage( requestUri );
		
		if( page == null )
			page = dataAccessor.newPage();
		
		if( requestUri.equals( "/blog/new" ) )
			page.setTitle( "New Blog Post" );

		else if( requestUri.startsWith( "/author-interview/" ) ) {
			Long blogId = Long.parseLong( requestUri.substring( 18 ) );
			BlogPostContent blogPostContent =
					(BlogPostContent) dataAccessor.getPageContent( blogId );
			page.setTitle( blogPostContent.getTitle() );

		} else if( requestUri.startsWith( "/blog/" ) ) {
			Long blogId = Long.parseLong( requestUri.substring( 6 ) );
			BlogPostContent blogPostContent =
					(BlogPostContent) dataAccessor.getPageContent( blogId );
			page.setTitle( blogPostContent.getTitle() );

		} else if( requestUri.equals( "/roleaccess" ) )
			page.setTitle( "Access" );
		
		else if( requestUri.equals( "/pages" ) )
			page.setTitle( "Pages" );
		
		return page;
		
	}
	
	protected List<PageContent> getPageContentList( HttpServletRequest request ) {

		String requestUri = request.getRequestURI();
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );

		Page page = dataAccessor.getPage( requestUri );
		List<PageContent> pageContentList = page == null
				? new LinkedList<PageContent>()
				: (List<PageContent>) dataAccessor.getPageContentList( page.getId() );

		if( requestUri.equals( "/blog/new" ) ) {
			BlogPostContent blogPost = BlogPostContentHelper.newBlogPostContent();
			blogPost.setTitle( "New Post Blog" );
			blogPost.setContent( "Blog post content goes here ..." );
			blogPost.setLastUpdated( new Date() );
			pageContentList.add( blogPost );
			
		} else if( requestUri.startsWith( "/author-interview/" ) ) {
			Long blogPostId = Long.parseLong( requestUri.substring( 18 ) );
			pageContentList.add( dataAccessor.getPageContent( blogPostId ) );

		} else if( requestUri.startsWith( "/blog/" ) ) {
			Long blogPostId = Long.parseLong( requestUri.substring( 6 ) );
			pageContentList.add( dataAccessor.getPageContent( blogPostId ) );

		} else if( requestUri.equals( "/roleaccess" ) ) {
			pageContentList.add( RoleAccessContentHelper.newRoleAccessContent() );

		} else if( requestUri.equals( "/users" ) ) {
			pageContentList.add( UserContentHelper.newUserContent() );
		
		} else if( requestUri.equals( "/pages" ) ) {
			pageContentList.add( PagesContentHelper.newPagesContent() );
		}

		return pageContentList;
	}

	protected List<WebsiteWidget> getWebsiteWidgetList( HttpServletRequest request ) {
		
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
