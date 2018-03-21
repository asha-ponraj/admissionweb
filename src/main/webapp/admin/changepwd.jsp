<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.admission.util.*, java.sql.Timestamp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报名起止时间设置</title>
<link rel="stylesheet" type="text/css" href="../plugins/easyui/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="../plugins/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../css/admin.css">
<script type="text/javascript" src="../plugins/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="../plugins/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../plugins/easyui/datagrid-detailview.js"></script>
<script type="text/javascript" src="../plugins/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../js/global.js"></script>
<script type="text/javascript" src="../js/pages/admin/changepwd.js"></script>
</head>
<body class="easyui-layout">
<div data-options="region:'center',border:false,title:'修改密码'">
<table align="center" style="margin-top: 10px;" cellspacing="10">
	<tr>
		<td colspan="2" class="tip">修改密码，密码不能为空</td>
	</tr>
	<tr>
		<th>原密码</th>
		<td><input id="originpwd" style="width: 150px;" type="password" ></td>
	</tr>
	<tr>
		<th>新密码</th>
		<td><input id="newpwd" style="width: 150px;" type="password" ></td>
	</tr>
	<tr>
		<th>确认新密码</th>
		<td><input id="confirmpwd" style="width: 150px;" type="password" ></td>
	</tr>
	<tr>
		<td></td>
		<td><a id="submitbtn" href="#" onClick="submitPassword()" class="easyui-linkbutton" data-options="iconCls:'icon-save'">设置</a></td>
	</tr>
</table>
</div>
</body>
</html>