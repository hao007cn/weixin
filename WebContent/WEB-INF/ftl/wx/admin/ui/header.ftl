<#macro header scriptsrc="" scriptcode="" csshref="" csscode="" pagetitle="">
<!DOCTYPE html>
<!--[if lt IE 7]>      <html lang="zh" class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html lang="zh" class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html lang="zh" class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html lang="zh" class="no-js"> <!--<![endif]-->
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" >
		<meta charset="utf-8" >
		<title>掌上医院后台管理系统-v1.0</title>

		<meta name="description" content="掌上医院后台管理系统" >
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" >
		<link rel="shortcut icon" href="${base}/favicon.ico" type="image/x-icon" />

		<!-- bootstrap & fontawesome -->
		<link rel="stylesheet" href="${base}/assets/css/bootstrap.min.css" >
		<link rel="stylesheet" href="${base}/assets/css/font-awesome/4.2/css/font-awesome.min.css" >

		<!-- page specific plugin styles -->
		<#if csshref!="">
		<#list csshref?split(",") as href>
		<link rel="stylesheet" href="${href?replace('\\s|\n','', 'r')}" />
		</#list>
		</#if><#rt/>

		<!-- text fonts -->
	

		<!-- senyint styles -->
		<link rel="stylesheet" href="${base}/assets/js/plugins/jquery-validationEngine/css/validationEngine.jquery.css" >
		<link rel="stylesheet" href="${base}/assets/css/senyint.min.css" >

		<!--[if lte IE 9]>
			<link rel="stylesheet" href="${base}/assets/css/senyint-part2.min.css" >
		<![endif]-->
		<link rel="stylesheet" href="${base}/assets/css/senyint-rtl.min.css" >

		<!--[if lte IE 9]>
			<link rel="stylesheet" href="${base}/assets/css/senyint-ie.min.css" >
		<![endif]-->

		<!-- inline styles related to this page -->
		<#if csscode!="">
		<style type="text/css">
			${csscode}
		</style>
		</#if><#rt/>

		<!-- senyint settings handler -->
		<script src="${base}/assets/js/senyint-extra.min.js"></script>

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
		<!--[if lt IE 9]>
		<script src="${base}/assets/js/html5shiv/html5shiv.js"></script>
		<script src="${base}/assets/js/respond/respond.min.js"></script>
		<![endif]-->
		<script src="${base}/assets/js/modernizr-2.6.2.min.js"></script>
	</head>
	<body class="no-skin">
<div id="rocket-to-top">
    <div class="onhover"></div>
    <div class="anim"></div>
</div>
		<div id="navbar" class="navbar navbar-default">
			<script type="text/javascript">
				try{ace.settings.check('navbar' , 'fixed')}catch(e){}
			</script>

			<div class="navbar-container" id="navbar-container">
				<button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler">
					<span class="sr-only">展开/关闭</span>

					<span class="icon-bar"></span>

					<span class="icon-bar"></span>

					<span class="icon-bar"></span>
				</button>

				<div class="navbar-header pull-left">
					<a href="${base}/admin/index" class="navbar-brand">
						<small>
							<img class="hidden-xs" src="${base}/assets/images/logolg.png" width="200px">
							<img class="visible-xs" src="${base}/assets/images/logosm.png" width="40px">
						</small>
					</a>
				</div>

				<div class="navbar-buttons navbar-header pull-right" role="navigation">
					<ul class="nav ace-nav">
						<li class="light-blue">
							<a data-toggle="dropdown" href="#" class="dropdown-toggle">
								<img class="nav-user-photo" src="${base}/assets/avatars/user.jpg" alt="用户头像" />
								<span class="user-info">
									<small>欢迎,</small>
									${loginuserinfo.name}
								</span>

								<i class="ace-icon fa fa-caret-down"></i>
							</a>

							<ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
								
								<li>
									<a href="${base}/admin/usersetting">
										<i class="ace-icon fa fa-user"></i>
										用户资料
									</a>
								</li>

								<li class="divider"></li>

								<li>
									<a href="${base}/admin/logout">
										<i class="ace-icon fa fa-power-off"></i>
										退出
									</a>
								</li>
							</ul>
						</li>
					</ul>
				</div>
			</div><!-- /.navbar-container -->
		</div>

		<div class="main-container" id="main-container">
			<script>
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>

			<div id="sidebar" class="sidebar responsive">
				<script>
					try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
				</script>

				<div class="sidebar-shortcuts" id="sidebar-shortcuts">
					<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
						<button class="btn btn-success">
							<i class="ace-icon fa fa-signal"></i>
						</button>

						<button class="btn btn-info">
							<i class="ace-icon fa fa-pencil"></i>
						</button>

						<button class="btn btn-warning">
							<i class="ace-icon fa fa-users"></i>
						</button>

						<button class="btn btn-danger">
							<i class="ace-icon fa fa-cogs"></i>
						</button>
					</div>

					<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
						<span class="btn btn-success"></span>

						<span class="btn btn-info"></span>

						<span class="btn btn-warning"></span>

						<span class="btn btn-danger"></span>
					</div>
				</div><!-- /.sidebar-shortcuts -->
				<ul class="nav nav-list">
				<#if admin_nav_session??>
					<@p.navigator resources=admin_nav_session.children />
				</#if>
				</ul>
				
				
				<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
					<i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
				</div>

				<script>
					try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
				</script>
			</div>

			<div class="main-content">
				<div class="breadcrumbs" id="breadcrumbs">
					<script type="text/javascript">
						try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
					</script>

					<ul class="breadcrumb">
						<li>
							<i class="ace-icon fa fa-home home-icon"></i>
							<a href="${base}/admin/index">首页</a>
						</li>

					</ul><!-- /.breadcrumb -->
				</div>

				<div class="page-content">
					<div class="page-header">
					</div><!-- /.page-header -->

					<div class="row">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
</#macro>