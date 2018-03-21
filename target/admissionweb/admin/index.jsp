<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上海闵行区启英幼儿园招生报名管理系统</title>
<link rel="stylesheet" type="text/css" href="../plugins/easyui/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="../plugins/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../css/admin.css">
<script type="text/javascript" src="../plugins/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="../plugins/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../plugins/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../js/global.js"></script>
<script type="text/javascript" src="../js/pages/admin/menu.js"></script>
<script type="text/javascript" src="../js/pages/admin/index.js"></script>
<style type="text/css">
a:link {
 text-decoration: none;
}
a:visited {
 text-decoration: none;
}
a:hover {
 text-decoration: underline;
}
a:active {
 text-decoration: none;
}
.cs-north {
	height:60px;background:#B3DFDA;
}
.cs-north-bg {
	width: 100%;
	height: 100%;
	background: url(../themes/gray/images/header_bg.png) repeat-x;
}
.cs-north-logo {
	height: 40px;
	padding: 15px 0px 0px 5px;
	color:#fff;font-size:22px;font-weight:bold;text-decoration:none
}
.cs-west {
	width:200px;padding:0px;border-left:1px solid #99BBE8;
}
.cs-south {
	height:25px;background:url('../themes/gray/images/panel_title.gif') repeat-x;padding:0px;text-align:center;
}
.cs-navi-tab {
	padding: 5px;
}
.cs-tab-menu {
	width:120px;
}
.cs-home-remark {
	padding: 10px;
}
</style>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false"	style="height: 65px; background: #fe8642; padding: 0px">
		<div  style="background: url(../images/qy_logo_64.gif) left top no-repeat;padding:0px; margin:0px;font-size: 16px; float:left;height:64px;width:100%;">
			<div style="float:left;font-size:22px;padding-top:18px;padding-left:70px;color:#676767;text-shadow:0px -1px #bbb,0 1px #fff;font-weight:bold;font-family:微软雅黑,Tahoma,Verdana,新宋体;">上海闵行区启英幼儿园招生报名管理系统</div></div>
		</div>
	</div>
	<div data-options="region:'west',split:true,title:'功能主菜单'"
		style="width: 160px; padding: 0px;">
		<div id="mainmenu" class="easyui-accordion" data-options="fit:true">
			<div title="报名管理" data-options="iconCls:'icon-produce',selected:true" style="overflow: auto; padding: 5px;">
				<ul id="menuadmission"></ul>
			</div>
			<div title="系统管理" data-options="iconCls:'icon-produce',selected:false" style="overflow: auto; padding: 5px;">
				<ul id="menusystem"></ul>
			</div>
		</div>
	</div>
	<div data-options="region:'center'">
		<div id="tabs" class="easyui-tabs"  data-options="fit:true,border:false" >
			<div title="主页">
				<div style="text-align: center; margin-top: 50px;">
					欢迎使用
				</div>
			</div>
        </div>
	</div>
	<div id="mm" class="easyui-menu cs-tab-menu">
		<div id="mm-tabupdate">刷新</div>
		<div class="menu-sep"></div>
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseother">关闭其他</div>
		<div id="mm-tabcloseall">关闭全部</div>
	</div>
</body>
</html>