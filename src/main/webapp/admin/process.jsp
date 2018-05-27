<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/admissionweb/tags" prefix="at" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><at:param name="school.name" /> - 受理与通知</title>
<link rel="stylesheet" type="text/css" href="../plugins/easyui/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="../plugins/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../css/admin.css">
<script type="text/javascript" src="../plugins/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="../plugins/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../plugins/easyui/datagrid-detailview.js"></script>
<script type="text/javascript" src="../plugins/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../js/global.js"></script>
<script type="text/javascript" src="../js/pages/admin/process.js"></script>
</head>
<body class="easyui-layout">
<div data-options="region:'center',border:false,title:'通知招生活动'">
<table border="0" cellspacing="10" cellpadding="0" align="center">
<tr>
	<td></td>
	<td colspan="3" class="tip">不输入报名号范围表示全部通知，输入相同号码表示只给改号码发送通知</td>
</tr>
<tr>
	<th>报名号从</th>
	<td>
		<input id="nfromid" class="easyui-validatebox" style="width: 200px;" type="text"></input>
	</td>
	<th>到</th>
	<td>
		<input id="ntoid" class="easyui-validatebox" style="width: 200px;" type="text"></input>
	</td>
</tr>
<tr>
	<th>通知内容</th>
	<td colspan="3">
		<textarea id="notify" rows="8" cols="50" class="easyui-validatebox" required="true" style="width: 440px;"></textarea>
	</td>
</tr>
<tr>
	<td></td>
	<td colspan="3">
		<a id="processtbtn" href="#" onClick="notifyApplication()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">发放活动通知</a>
	</td>
</tr>
</table>
</div>
</body>
</html>