package com.claymus.module.pagecontent;

import org.junit.Assert;
import org.junit.Test;

import com.claymus.data.transfer.PageContent;
import com.claymus.pagecontent.PageContentProcessor;
import com.claymus.pagecontent.PageContentRegistry;
import com.claymus.pagecontent.html.HtmlContent;
import com.claymus.pagecontent.html.HtmlContentHelper;
import com.claymus.pagecontent.html.HtmlContentProcessor;

public class PageContentRegistryTest {

    @Test
    public void testRegister() {
    	
    	PageContentRegistry.register( HtmlContentHelper.class );
    	
    	PageContentProcessor<? extends PageContent> pageContentProcessor =
    			PageContentRegistry.getPageContentProcessor( HtmlContent.class );
    	
    	Assert.assertNotNull( pageContentProcessor );
    	Assert.assertEquals( HtmlContentProcessor.class, pageContentProcessor.getClass() );
    	
    }
    
}
