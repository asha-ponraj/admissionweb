<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.admission.util.*, java.sql.Timestamp"%>
<%@taglib uri="/admissionweb/tags" prefix="at" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><at:param name="school.name" /> - 报名起止时间设置</title>
<link rel="stylesheet" type="text/css" href="../plugins/easyui/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="../plugins/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../css/admin.css">
<script type="text/javascript" src="../plugins/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="../plugins/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../plugins/easyui/datagrid-detailview.js"></script>
<script type="text/javascript" src="../plugins/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../js/global.js"></script>
<script type="text/javascript" src="../js/pages/admin/timespace.js"></script>
</head>
<body class="easyui-layout">
<%
Timestamp startTime = Profile.getInstance().getStartApplicationTime();
String startTimeStr = TimeUtil.getSQLTimestamp(startTime);
Timestamp endTime = Profile.getInstance().getEndApplicationTime();
String endTimeStr = TimeUtil.getSQLTimestamp(endTime);
Timestamp downloadEndTime = Profile.getInstance().getDownloadEndTime();
String downloadEndTimeStr = TimeUtil.getSQLTimestamp(downloadEndTime);
%>
<div data-options="region:'center',border:false,title:'时间段设置'">
<table align="center" style="margin-top: 10px;" cellspacing="10">
	<tr>
		<th>报名开始时间</th>
		<td><input id="starttime" style="width: 150px;" value="<%=startTimeStr%>"></td>
	</tr>
	<tr>
		<th>报名结束时间</th>
		<td><input id="endtime" style="width: 150px;" value="<%=endTimeStr%>"></td>
	</tr>
	<tr>
		<th>报名下载截止时间</th>
		<td><input id="downloadendtime" style="width: 150px;" value="<%=downloadEndTimeStr%>"></td>
	</tr>
	<tr>
		<td></td>
		<td><a id="submitbtn" href="#" onClick="submitTimespace()" class="easyui-linkbutton" data-options="iconCls:'icon-save'">设置</a></td>
	</tr>
</table>
</div>
</body>
</html>