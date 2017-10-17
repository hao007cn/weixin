<#macro footer scriptsrc=""  scriptcode="">
							<!-- PAGE CONTENT ENDS -->
						</div><!-- /.col -->
					</div><!-- /.row -->
				</div><!-- /.page-content -->
			</div><!-- /.main-content -->

			<div class="footer">
				<div class="footer-inner">
					<div class="footer-content">
						<span class="bigger-120">
							<span class="blue bolder">Senyint</span>
							掌上医院后台管理系统 &copy; 2013-2014
						</span>
					</div>
				</div>
			</div>

			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->
		
		<!-- basic scripts -->
		<script src="${base}/assets/js/modernizr-2.6.2.min.js"></script>

		<!--[if lte IE 7]>
		<script src="${base}/assets/js/json2.js"></script>
		<!-- <![endif]-->

		<!--[if !IE]> -->
		<script src="${base}/assets/js/jquery-2.1.1.min.js"></script>
		<!-- <![endif]-->

		<!--[if IE]>
		<script src="${base}/assets/js/jquery-1.10.2.min.js"></script>
		<![endif]-->

		<!--[if !IE]> -->
		<script>
			window.jQuery || document.write("<script src='${base}/assets/js/jquery-2.1.1.min.js'>"+"<"+"/script>");
		</script>
		<!-- <![endif]-->

		<!--[if IE]>
		<script type="text/javascript">
		 window.jQuery || document.write("<script src='${base}/assets/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
		</script>
		<![endif]-->
		
		<script>
			if('ontouchstart' in document.documentElement) document.write("<script src='${base}/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="${base}/assets/js/bootstrap.min.js"></script>
		<script src="${base}/assets/js/plugins/bootstrap/bootbox.min.js"></script>
		
		<!-- page specific plugin scripts -->
		<#if scriptsrc!="">
		<#list scriptsrc?split(",") as src>
		<script src="${src?replace('\\s|\n','', 'r')}" ></script>
		</#list>
		</#if><#rt/>

		<!-- senyint scripts -->
		<script src="${base}/assets/js/jquery-ui.custom.min.js"></script>
		<script src="${base}/assets/js/chosen.jquery.min.js"></script>
		<script src="${base}/assets/js/jquery.autosize.min.js"></script>
		<script src="${base}/assets/js/jquery.inputlimiter.1.3.1.min.js"></script>
		<script src="${base}/assets/js/jquery.maskedinput.min.js"></script>
		
		<script src="${base}/assets/js/plugins/marked.js"></script>
		<script src="${base}/assets/js/plugins/jquery-validationEngine/js/jquery.validationEngine-zh_CN.js"></script>
		<script src="${base}/assets/js/plugins/jquery-validationEngine/js/jquery.validationEngine.js"></script>

		<script src="${base}/assets/js/senyint-elements.min.js"></script>
		<script src="${base}/assets/js/senyint.min.js"></script>
		<script src="${base}/assets/js/common.js"></script>

		<!-- inline scripts related to this page -->
		<script>
			var winpath = window.location.href;
			var links = $(".nav-list a");
			for (var i = 0 ; i < links.length; i ++) {
				var link = links[i];
				if (link.href.indexOf("#") == -1) {
					var path = link.pathname.replace(/^([^\/])/,'/$1') + '/';
					if ((winpath + "/").indexOf(path) > 0) {
						// 菜单焦点设置
						$(link).parents(".nav-list li").addClass("active open");
						$(link).closest("li").removeClass("open");
						
						// 标题设置
						var title = "";
						var subTitle = "<h1>";
						$(".nav-list .open").each(function(i){
							title +='<li>' + $(this).find(".menu-text").html().trim() + '</li>';
							if (i == 0) {
								subTitle += $(this).find(".menu-text").html().trim();
							} else {
								subTitle += '<small><i class="ace-icon fa fa-angle-double-right"></i>' + $(this).find(".menu-text").html().trim() + '</small>';
							}
						});
						
						var activeTitle = $(".nav-list .active:last a").text().trim();
						title += "<li>" + activeTitle + '</li>';
						if (subTitle == "<h1>") {
							subTitle += activeTitle + '</h1>';
						} else {
							subTitle += '<small><i class="ace-icon fa fa-angle-double-right"></i>' + activeTitle + '</small></h1>';
						}
						
						$(".breadcrumb").append(title);
						$(".page-header").html(subTitle);
						
						break;
					}
				}
			}
		
		<#if error??>
			var err = '';
			<#list error as e>
				err += '${e.field}' + '${e.defaultMessage}';
			</#list>
			bootbox.alert('<div class="alert senyint-alert alert-warning" role="alert">' + err + '</div>', function(){
			});
			
		</#if><#rt/>
		
		<#if errMsg??>
			bootbox.alert('<div class="alert senyint-alert alert-warning" role="alert">${errMsg}</div>', function(){
			});
		</#if><#rt/>
		
		<#if message??>
			bootbox.message('<div class="alert senyint-alert alert-success" role="alert">${message}</div>', function(){
			});
		</#if><#rt/>
		
		<#if scriptcode!="">
			${scriptcode}
		</#if><#rt/>
		</script>
		
	</body>
</html>
</#macro>