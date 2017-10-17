<#macro sysmenu resources>
<ol class="dd-list">
	<#list resources as res>
		<li class="dd-item" data-poid="${res.poid!}" >
			<div class="dd-handle dd2-handle">
				<i class="normal-icon ace-icon fa ${res.resClass!} bigger-130"></i>
	
				<i class="drag-icon ace-icon fa fa-arrows bigger-125"></i>
			</div>
			<div class="dd2-content row">
				<span class="name">${res.name!}</span>
				<div class="pull-right action-buttons">
					<a class="blue" href="javascript:void(0)" onclick="addSub(${res.poid!})">
						<i class="ace-icon fa fa-plus bigger-130"></i>
					</a>
					
					<a class="blue" href="javascript:void(0)" onclick="edit(${res.poid!})">
						<i class="ace-icon fa fa-pencil bigger-130"></i>
					</a>
	
					<a class="red" href="javascript:void(0)" onclick="del(${res.poid!})">
						<i class="ace-icon fa fa-trash-o bigger-130"></i>
					</a>
				</div>
			</div>
			
		<#if res.children?size gt 0>
			<@sysmenu resources=res.children />
		</#if>
		</li>
	</#list>
</ol>
</#macro>
