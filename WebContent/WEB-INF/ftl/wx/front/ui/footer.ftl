<#macro footer scriptsrc=""> 
			</div>
			<!-- 页脚 -->
		<span id="showHide" disload="true" style="width: 25px;height:62px;bottom: 0px;position: fixed;z-index:99999;padding-top:20px;padding-left:4px;" onclick="spanHide();"><span id="enid" style="font-size: 15px;color: #888888;" class="glyphicon glyphicon-chevron-left"></span><span id="djdk" style="display: none;color: #FFF;background-color: #21B7C6" class="badge">点我<br/>打开</span></span>
		<div id="uifoot"  class="ui-footer ui-bar-a ui-footer-fixed slideup">
			<!-- 底部导航 -->
			<div  class="ui-navbar">
				<ul class="ui-grid-b">
						<li class="ui-block-a"><a href="${base}/index" class="ui-link ui-btn"><span style=" font-size: 20px;color:#888888;margin-top:3px" class="glyphicon glyphicon-home"><abbr class="center-block" style="font-family:微软雅黑;font-size:14px;margin-top:6px">主页</abbr></span></a></li>
						<li class="ui-block-b"><a href="${base}/persona?rd=persona" class="ui-link ui-btn"><span style=" font-size: 20px;color: #888888;margin-top:3px" class="glyphicon glyphicon-user"><abbr class="center-block" style="font-family:微软雅黑;font-size:14px;margin-top:6px">个人中心</abbr></span></a></li>
						<li class="ui-block-c"><a href="#menu" disload="true" class="ui-link ui-btn"><span style=" font-size: 20px;color: #888888;margin-top:3px" class="glyphicon glyphicon-th-large"><abbr class="center-block" style="font-family:微软雅黑;font-size:14px;margin-top:6px">更多</abbr></span></a></li>
				</ul><!-- /navbar ul -->
				</div><!-- /navbar -->
			</div><!-- /footer -->
		</div>
	</div><!-- /page -->
	<!-- 页面左菜单 -->
	<nav id="menu">
		<ul>
			<li><a class="mm-subopen" href="${base}/activities" ></a><span onclick="href('${base}/activities')"><img
					class="animation delaytime1" src="${base}/static/image/icona.png" />
					新闻活动</span></li>
			<!--<li><a class="mm-subopen" ></a><span ><img
					class="animation delaytime1" src="${base}/static/image/iconb.png" />
					科室专家介绍</span></li>-->
			<li><a class="mm-subopen" href="${base}/vintroduction?rd=vintroduction"></a><span onclick="href('${base}/vintroduction?rd=vintroduction')"><img
					class="animation delaytime4" src="${base}/static/image/iconc.png" />
					预约挂号</span></li>
			<li><a class="mm-subopen" href="${base}/scheduleIndex?rd=scheduleIndex"></a><span onclick="href('${base}/scheduleIndex?rd=scheduleIndex')"><img
					class="animation delaytime5" src="${base}/static/image/icond.png" />
					排班查询</span></li>
			<li><a class="mm-subopen" href="${base}/persona?rd=persona"></a><span onclick="href('${base}/persona?rd=persona')"><img
					class="animation delaytime6" src="${base}/static/image/icone.png" />
					个人中心</span></li>
			<li><a class="mm-subopen" href="${base}/healthy" ></a><span onclick="href('${base}/healthy')"><img
					class="animation delaytime7" src="${base}/static/image/iconf.png" />
					健康百科</span></li>
		</ul>
	</nav>
	<!-- 相关js文件 -->
	<!--<script src="${base}/static/mobile/jquery.mobile-1.4.5.min.js"></script>-->
	<script src="${base}/static/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base}/static/mmenu/js/jquery.mmenu.min.all.js"></script>
	<script src="${base}/static/bootstrap/js/jquery.bootstrap.min.js"></script>
	<script src="${base}/static/js/showbody.js" defer="defer"></script>	
	
	<#if scriptsrc!="">
	<#list scriptsrc?split(",") as src>
	<script src="${src?replace('\\s|\n','', 'r')}" ></script>
	</#list>
	</#if><#rt/>
		
	<#if errMsg??>
		<script>
			S.alert('error','${errMsg}');
		</script>
	</#if><#rt/>
	
	<#if message??>
		<script>
			S.alert('info','${message}');
		</script>
	</#if><#rt/>
<script type="text/javascript">
	function spanHide() {
	$("#uifoot").hide("fast", function() {
		$("#djdk").show();
		$("#enid").hide();
		$("#showHide").attr("onclick", "spanShow();");
		$("#indexMargin").removeClass("marginbott");
	});
}
	function spanShow() {
	$("#uifoot").show("fast", function() {

		$("#djdk").hide();
		$("#enid").show();
		$("#showHide").attr("onclick", "spanHide();");
		$("#indexMargin").addClass("marginbott");
	});
}
</script>
</body>
</html>
</#macro>