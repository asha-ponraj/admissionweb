<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.admission.util.*, java.sql.Timestamp"%>
<%@taglib uri="/admissionweb/tags" prefix="at" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><at:param name="school.name" /> - 数据统计</title>
<link rel="stylesheet" type="text/css" href="../plugins/easyui/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="../plugins/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../css/admin.css">
<script type="text/javascript" src="../plugins/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="../plugins/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../plugins/easyui/datagrid-detailview.js"></script>
<script type="text/javascript" src="../plugins/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../js/global.js"></script>
<script type="text/javascript" src="../js/pages/admin/statistics.js"></script>
</head>
<body class="easyui-layout">
<div data-options="region:'center',border:false,title:'数据统计'">
<table align="center" style="margin: 10px; background-color: gray; width: 300px" cellspacing="1" cellpadding="5">
	<tr style="background-color: white;">
		<th>报名总数</th>
		<th id="so_all"></th>
	</tr>
	<tr style="background-color: white;">
		<th>报名表提已交数量</th>
		<th id="so_submitted"></th>
	</tr>
	<tr style="background-color: white;">
		<th>报名表已受理数量</th>
		<th id="so_accepted"></th>
	</tr>
	<tr style="background-color: white;">
		<th>通知已发送数量</th>
		<th id="so_notified"></th>
	</tr>
	<tr style="background-color: white;">
		<th>报名表已下载数量</th>
		<th id="so_downloaded"></th>
	</tr>
	<tr style="background-color: white;">
		<th>已签到数量</th>
		<th id="so_checkin"></th>
	</tr>
	<tr style="background-color: white;">
		<th>已拒绝报名数量</th>
		<th id="so_denied"></th>
	</tr>
	<tr style="background-color: white;">
		<td></td>
		<td align="right"><a id="submitbtn" href="#" onClick="refreshStatistics()" class="easyui-linkbutton" data-options="iconCls:'icon-reload'">刷新</a></td>
	</tr>
</table>
</div>
</body>
</html>