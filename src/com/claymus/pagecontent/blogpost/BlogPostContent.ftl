<#import "../../../../com/claymus/commons/client/ui/Social.ftl" as social>

<!-- PageContent :: BlogPost :: Start -->

<#assign shareUrl="http://${ domain }${ pageUrl }">

<#if blogPostContent.preview()>

	<div class="container">
		<h1><a href="${ pageUrl }">${ blogPostContent.getTitle() }</a></h1>
		<div style="margin-top:10px; margin-bottom:10px;"><@social.toolbar shareUrl=shareUrl/></div>
		<div class="pageContent-BlogPost-Content">${ blogPostContent.getContent() }</div>
	</div>
	<br/>
	<br/>
	
<#else>

	<div class="container">
		<h1 id="PageContent-BlogPost-Title" data-pageContentId="${ (blogPostContent.getId()?c)! }">${ blogPostContent.getTitle() }</h1>
		<div style="margin-top:10px; margin-bottom:10px;"><@social.toolbar shareUrl=shareUrl/></div>
		<div id="PageContent-BlogPost-Content" class="pageContent-BlogPost-Content">${ blogPostContent.getContent() }</div>
		<#if showEditOptions>
			<div id="PageContent-BlogPost-EditOptions" style="margin-top:10px; text-align:right;"></div>
		</#if>
	</div>

	<#if showEditOptions>
		<script type="text/javascript" language="javascript" src="/pagecontent.blogpost/pagecontent.blogpost.nocache.js" defer></script>
	</#if>

	<br/>
	<br/>

</#if>


<!-- PageContent :: BlogPost :: End -->