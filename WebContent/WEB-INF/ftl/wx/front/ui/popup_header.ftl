<#macro popup_header  scriptsrc=""  scriptcode="" csshref="" csscode="" pagetitle="">
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<head>
<link rel="stylesheet" type="text/css" href="${base}/plug-in/view/login/css/index-base.css" />
<link rel="stylesheet" type="text/css" href="${base}/plug-in/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${base}/plug-in/jquery-easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${base}/plug-in/common/common.css">
<link rel="stylesheet" type="text/css" href="${base}/plug-in/layer/skin/layer.css"/>
<script type="text/javascript" src="${base}/plug-in/common/json2.js"></script>
<script type="text/javascript" src="${base}/plug-in/jquery/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${base}/plug-in/layer/layer.min.js"></script>
<script type="text/javascript" src="${base}/plug-in/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${base}/plug-in/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${base}/plug-in/common/common.js?basePath=${base}""></script>
<script type="text/javascript" src="${base}/plug-in/common/common_pop.js?basePath=${base}""></script>
<script type="text/javascript" src="${base}/plug-in/common/popup.js"></script>
<script type="text/javascript" src="${base}/plug-in/My97DatePicker/WdatePicker.js"></script>
<#if scriptsrc!=""><script type="text/javascript" src="${scriptsrc}"></script></#if><#rt/>	
<#if csshref!=""> <link rel="stylesheet" type="text/css" href="${csshref}" /></#if><#rt/>
<script type="text/javascript">
 	$.ajaxSetup({
         complete:function(xhr,status){
             var authHandle=xhr.getResponseHeader("authHandle"); //通过XMLHttpRequest取得响应头，sessionstatus，
             var sessionHandle=xhr.getResponseHeader("sessionHandle");
             if(authHandle=='noauth'){
                  alert("没有权限！");
             }
             if(sessionHandle=='nosession'){
             	  var baseurl=$('#contentDiv').attr('baseUrl');//请求路径
                  alert("访问超时!");
                  window.parent.location = baseurl + "/";
             }
         }
   });
   var baseUrl = '${base}';
</script>
<style type="text/css">
	<#if csscode!="">${csscode}</#if><#rt/>
</style>
<title>
<#if pagetitle!="">${pagetitle}</#if><#rt/>
</title>
</head>
<body>
<div baseUrl="${base}" id="contentDiv">
</#macro>