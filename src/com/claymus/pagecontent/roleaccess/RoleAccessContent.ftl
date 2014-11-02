<!-- PageContent :: RoleAccess :: Start -->

<div class="container">

	<h1>Role Access</h1>

	<table class="table table-hover">
		<thead>
			<tr>
				<th>Module</th>
				<th>Access</th>
				<#list roleIdList as roleId >
					<th>Role: ${ roleId }</th>
				</#list>
			</tr>
		</thead>
		<tbody>
			<#assign count=0>
			<#list pageContentHelperList as pageContentHelper >
				<#list pageContentHelper.getAccessList() as access >
					<tr>
						<td>${ pageContentHelper.getModuleName() }</td>
						<td>${ access.getDescription() }</td>
						<#list roleIdList as roleId >
							<#assign hasAccess=( dataAccessor.getRoleAccess( roleId, access.getId() ).hasAccess() ) ! access.getDefault()>
							<td id="accessId-${ count }" roleId="${ roleId }" accessId="${ access.getId() }" hasAccess="${ hasAccess?c }">
								<#if hasAccess>
									<span class="glyphicon glyphicon-ok text-success"></span>
								<#else>
									<span class="glyphicon glyphicon-remove text-danger"></span>
								</#if>
							</td>
							<#assign count=count + 1>
						</#list>
					</tr>
				</#list>
			</#list>
		</tbody>
	</table>

</div>


<#if showUpdateOptions>
	<script type="text/javascript" language="javascript" src="/pagecontent.roleaccess/pagecontent.roleaccess.nocache.js" defer></script>
</#if>

<!-- PageContent :: RoleAccess :: End -->