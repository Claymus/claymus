<!-- PageContent :: User :: Start -->

<div class="container well" style="margin-top:20px;">
	<#list userDataList as userData>
		${ userData.getName() }, ${ userData.getEmail() }, ${ userData.getStatus() } <br/>
	</#list>
</div>

<!-- PageContent :: User :: End -->