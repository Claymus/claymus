<div class="websitewidget-footer">
	&nbsp;
	<#list getLinks() as link>
		<a href="${ link[1] }">${ link[0] }</a>
		<#if link_has_next>&nbsp; . &nbsp;</#if>
	</#list>
	<br/>
	<span> ${ getCopyrightNote() } </span>
</div>