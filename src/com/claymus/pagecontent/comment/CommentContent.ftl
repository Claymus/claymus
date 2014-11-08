<!-- PageContent :: Comment :: Start -->

<div class="container">
	<div id="PageContent-Comment" data-blogId="${ blogId?c }" data-cursor="${ cursor }">
		<#list commentList as comment>
			<h3 id="PageContent-Comment-User" class="pageContent-Comment-User">
				${ comment.getUserId() }
			</h3>
			<div style="margin-top:10px; margin-bottom:10px;"><@social.toolbar shareUrl=shareUrl/></div>
			<div id="PageContent-Comment-Content" class="pageContent-Comment-Content">
				${ comment.getContent() }
			</div>
			<div id="PageContent-Comment-PublishedDate" class="pageContent-Comment-Content">
				${ comment.getCreationDate() }
			</div>
		</#list>
	</div>
</div>
<#if showEditOptions>
	<script type="text/javascript" language="javascript" src="/pagecontent.comment/pagecontent.comment.nocache.js" defer></script>
</#if>

<!-- PageContent :: Comment :: End -->