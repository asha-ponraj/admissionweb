<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.admission.util.*, java.sql.Timestamp, java.util.Date"%>
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
Date minBirthday = Profile.getInstance().getMinBirthday();
String minBirthdayStr = TimeUtil.getSQLDate(minBirthday);
Date maxBirthday = Profile.getInstance().getMaxBirthday();
String maxBirthdayStr = TimeUtil.getSQLDate(maxBirthday);
%>
<div data-options="region:'center',border:false,title:'报名条件设置'">
<table align="left" style="margin-top: 20px;" cellspacing="10">
	<tr>
		<th colspan="2">报名时间段设置</th>
	</tr>
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
	<tr>
		<td colspan="2"></td>
	</tr>
	<tr>
		<th colspan="2">报名年龄段设置</th>
	</tr>
	<tr>
		<th>报名起始生日</th>
		<td><input id="minbirthday" style="width: 150px;" value="<%=minBirthdayStr%>"></td>
	</tr>
	<tr>
		<th>报名截止生日</th>
		<td><input id="maxbirthday" style="width: 150px;" value="<%=maxBirthdayStr%>"></td>
	</tr>
	<tr>
		<td></td>
		<td><a id="submitbtn2" href="#" onClick="submitAgespace()" class="easyui-linkbutton" data-options="iconCls:'icon-save'">设置</a></td>
	</tr>
</table>
</div>
</body>
</html>