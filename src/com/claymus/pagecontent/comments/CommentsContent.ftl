<!-- PageContent :: Comment List :: Start -->

<div class="container">
	<div id="PageContent-Comments" data-refId="${ refId }" data-cursor="${ cursor }">
		<#list commentList as comment>
			<h3 class="pageContent-Comment-User">
				${ comment.getUserId() }
			</h3>
			<div class="pageContent-Comment-Content">
				${ comment.getContent() }
			</div>
			<div class="pageContent-Comment-Content">
				${ comment.getCreationDate() }
			</div>
		</#list>
	</div>
</div>

<#if showEditOptions>
	<script type="text/javascript" language="javascript" src="/pagecontent.comments/pagecontent.comments.nocache.js" defer></script>
</#if>

<!-- PageContent :: Comment List :: End -->