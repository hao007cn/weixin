<#macro header  scriptsrc=""  scriptcode="" csshref="" csscode="" pagetitle="">
<!DOCTYPE html>
<html>
<head lang="zh-cn">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1, maximum-scale=1">
	<link href="${base}/static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="${base}/static/mobile/jquery.mobile-1.4.5.min.css" rel="stylesheet" type="text/css" />
	<link href="${base}/static/mmenu/css/jquery.mmenu.all.css" rel="stylesheet" type="text/css" />
	<link href="${base}/static/css/stylesenyint.css" rel="stylesheet" type="text/css" />
	<#if csshref!="">
	<#list csshref?split(",") as href>
	<link rel="stylesheet" href="${href?replace('\\s|\n','', 'r')}" />
	</#list>
	</#if><#rt/>
	<!-- inline styles related to this page -->
	<#if csscode!="">
	<style type="text/css">
		${csscode}
	</style>
	</#if><#rt/>
	<script src="${base}/static/js/jquery.min.js"></script>
	<!--<script src="${base}/static/mmenu/js/hammer.min.js"></script>-->
	<script src="${base}/static/js/common.js"></script>
	<title>大连市医科大学附属第一医院</title>
</head>
<body>
<div class="spinner">
  <div class="spinner-container container1">
    <div class="circle1"></div>
    <div class="circle2"></div>
    <div class="circle3"></div>
    <div class="circle4"></div>
  </div>
  <div class="spinner-container container2">
    <div class="circle1"></div>
    <div class="circle2"></div>
    <div class="circle3"></div>
    <div class="circle4"></div>
  </div>
  <div class="spinner-container container3">
    <div class="circle1"></div>
    <div class="circle2"></div>
    <div class="circle3"></div>
    <div class="circle4"></div>
  </div>
</div>
	<!-- 主页面一 -->
	<div id="indexPage">
		<div id="indexMargin" class="marginbott">
</#macro>