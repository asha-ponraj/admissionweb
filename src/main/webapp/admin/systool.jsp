<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统工具</title>
<link rel="stylesheet" type="text/css" href="../plugins/easyui/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="../plugins/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../css/admin.css">
<script type="text/javascript" src="../plugins/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="../plugins/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../plugins/easyui/datagrid-detailview.js"></script>
<script type="text/javascript" src="../plugins/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../js/global.js"></script>
<script type="text/javascript" src="../js/pages/admin/systool.js"></script>
</head>
<body class="easyui-layout">
<div data-options="region:'center',border:false,title:'系统工具'">
<table align="center" style="margin-top: 10px;" cellspacing="10">
	<tr>
		<td></td>
		<td><a id="reloadcfgbtn" href="#" onClick="reloadcfg()" class="easyui-linkbutton" data-options="iconCls:'icon-save'">重新加载配置</a></td>
	</tr>
</table>
</div>
</body>
</html>