<!-- PageContent :: HTML :: Start -->

<div class="container">
	<#if getTitle()??>
		<h1>${ getTitle() }</h1>
	</#if>
	<div>${ getContent() }</div>
</div>

<!-- PageContent :: HTML :: End -->