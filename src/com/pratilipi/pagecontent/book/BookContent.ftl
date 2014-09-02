<div class="container-fluid">
	
	<div class="row">
		<div class="col-sm-2">
			<a href="${ bookHomeUrl }">
				<img class="img-responsive img-thumbnail" src="${ bookCoverUrl }">
			</a>
			<#if showEditOptions>
				<div id="PageContent-Book-CoverImage-EditOptions"></div>
			</#if>
		</div>
		<div class="col-sm-10">
			<h3>
				<a href="${ bookHomeUrl }">${ book.getTitle() }</a>
			</h3>
			<h4>
				<a href="${ authorHomeUrl }">${ author.getFirstName() } ${ author.getLastName() }</a>
			</h4>
			<div>
				<div id="PageContent-Book-Summary">
					<#if book.getSummary()?? >
						${ book.getSummary() }
					</#if>
				</div>
				<#if showEditOptions>
					<div id="PageContent-Book-Summary-EditOptions"></div>
				</#if>
			</div>
		</div>
	</div>

	<#if showAddReviewOption>
		<div id="PageContent-Book-Review"></div>
		<div id="PageContent-Book-Review-AddOptions"></div>
	</#if>

	<div id="PageContent-Book-ReviewList">
		<#list reviewList as review >
			<div class="panel panel-default">
				<div class="panel-body">
					${ review.getReview() }
				</div>
			</div>
		</#list>
	</div>
	
</div>

<script type="text/javascript" language="javascript" src="/pagecontent.book/pagecontent.book.nocache.js" defer></script>
<#if showEditOptions>
	<script type="text/javascript" language="javascript" src="/pagecontent.book.editoptions/pagecontent.book.editoptions.nocache.js" defer></script>
</#if>