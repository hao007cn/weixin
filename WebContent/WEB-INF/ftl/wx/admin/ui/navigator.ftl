<#macro navigator resources>
<#list resources as res>
<#if res.display>
	<#if res.children?size gt 0>
		<li class="">
		<a href="#" class="dropdown-toggle">
			<i class="menu-icon fa ${res.resClass!}"></i>
			<span class="menu-text">${res.name!}</span>
			<b class="arrow fa fa-angle-down"></b>
		</a>	
		<ul class="submenu">
		<#list res.children as subRes>
			<#if subRes.display>
				<#if subRes.children?size gt 0>
					<li class="">
						<a href="#" class="dropdown-toggle">
							<i class="menu-icon fa ${subRes.resClass!}"></i>
							<span class="menu-text">${subRes.name!}</span>
							<b class="arrow fa fa-angle-down"></b>
						</a>
						<b class="arrow"></b>
			
						<ul class="submenu">
							<#if subRes.children?size gt 0>
								<@navigator resources=subRes.children />
							</#if>
						</ul>
					</li>
				<#else>
					<li class="">
						<a href="${base}${subRes.resUrl!}">
						<i class="menu-icon fa ${subRes.resClass!}}"></i>
							<span class="menu-text"> ${subRes.name!} </span>
						</a>
						<b class="arrow"></b>
					</li>
				</#if>
			</#if>
		</#list>
		</ul>
		</li>
	<#else>	
		<li class="">
			<a href="${base}${res.resUrl!}">
			<i class="menu-icon fa ${res.resClass!}"></i>
				<span class="menu-text"> ${res.name!} </span>
			</a>
			<b class="arrow"></b>
		</li>
	</#if>
</#if>
</#list>
</#macro>
