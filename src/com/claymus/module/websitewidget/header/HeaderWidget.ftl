<nav class="navbar navbar-default navbar-static-top" role="navigation">

	<div class="container-fluid">
		
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#WebsiteWidget-Header">
				<span class="sr-only">Toggle Navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="/">${ getBrand() }</a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse" id="WebsiteWidget-Header">

			<#if getLeftNavItems()?? >
				<ul class="nav navbar-nav">
				
					<#list getLeftNavItems() as navItem>
						<#if navItem[2]?? >
							<li class="dropdown">
								<a href="#" class="dropdown-toggle" data-toggle="dropdown">${ navItem[0] }<span class="caret"></span></a>
								<ul class="dropdown-menu" role="menu">
									<#list navItem[2] as navSubItem>
										<li><a href="${ navSubItem[1] }">${ navSubItem[0] }</a></li>
									</#list>
								</ul>
							</li>
						<#else>
							<li><a href="${ navItem[1] }">${ navItem[0] }</a></li>
						</#if>
					</#list>
				
				</ul>
			</#if>
			
			<#if getRightNavItems()?? >
				<ul class="nav navbar-nav navbar-right">
	
					<#list getRightNavItems() as navItem>
						<#if navItem[2]??>
							<li class="dropdown">
								<a href="#" class="dropdown-toggle" data-toggle="dropdown">${ navItem[0] }<span class="caret"></span></a>
								<ul class="dropdown-menu" role="menu">
									<#list navItem[2] as navSubItem>
										<li><a href="${ navSubItem[1] }">${ navSubItem[0] }</a></li>
									</#list>
								</ul>
							</li>
						<#else>
							<li><a href="${ navItem[1] }">${ navItem[0] }</a></li>
						</#if>
					</#list>
	
				</ul>
			</#if>
			
		</div><!-- /.navbar-collapse -->
	
	</div><!-- /.container-fluid -->

</nav>