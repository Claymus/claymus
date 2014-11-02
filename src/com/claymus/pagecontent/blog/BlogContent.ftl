<#import "../../../../com/claymus/commons/client/ui/Social.ftl" as social>

<!-- PageContent :: Blog :: Start -->

<#if showEditOptions>
	<div class="container alert alert-info" role="alert" style="text-align:center; margin-top:20px; margin-bottom:0px;">
		<b>Click <a href="/blog/new?blogId=${ blogId?c }">here</a> to create new post.</b>
	</div>
</#if>

<div id="PageContent-Blog" data-blogId="${ blogId?c }" data-cursor="${ cursor }">
	<#list blogPostHtmlList as blogPostHtml>
		${ blogPostHtml }
	</#list>
</div>

<script type="text/javascript" language="javascript" src="/pagecontent.blog/pagecontent.blog.nocache.js" defer></script>

<!-- PageContent :: Blog :: End -->