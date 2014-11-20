<ul class="nav nav-tabs nav-justified" role="tablist">
	<#list getLinks() as link>
		<li><a href="${ link[1] }">${ link[0] }</a></li>
	</#list>
</ul>
