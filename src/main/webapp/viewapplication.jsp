<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.admission.util.*, java.sql.Timestamp"%>
<%@taglib uri="/admissionweb/tags" prefix="at" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><at:param name="school.name" /></title>
<link rel="stylesheet" type="text/css" href="plugins/easyui/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="plugins/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="css/my.css">
<script type="text/javascript" src="plugins/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="plugins/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="plugins/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/global.js"></script>
<script type="text/javascript">
<%
Timestamp downloadEndTime = Profile.getInstance().getDownloadEndTime();
Timestamp curTime = TimeUtil.getCurTime();
Timestamp startTime = Profile.getInstance().getStartApplicationTime();
Timestamp endTime = Profile.getInstance().getEndApplicationTime();

if(curTime.after(downloadEndTime)) {
%>
var isDownloadAvaliable = false;
<%
} else {
%>
var isDownloadAvaliable = true;
<%
}
%>
var selectedNotifyTip = "<%=Profile.getInstance().getSelectedNotifyTip() %>";
</script>
<script type="text/javascript" src="js/pages/viewapplication.js"></script>
</head>
<body>
<jsp:include page="head.jsp"></jsp:include>
<div class="page_body">
<div class="hmenu"><jsp:include page="menu.jsp"></jsp:include></div>
<div class="board">
<div class="title">招生报名查询</div>
<div class="block">
<table>
<tr>
<th>用户名:</th>
<td>
 <input id="queryusername" class="easyui-validatebox" type="text" style="width: 200px;"
	data-options="required:true"></input>
</td>
<td width="30"></td>
<th>查询密码:</th>
<td>
 <input id="querypassword" class="easyui-validatebox" type="password" style="width: 150px;"
	data-options="required:true"></input>
</td>
<td width="30"></td>
<td>
<a id="searchbtn" href="#" onClick="searchApplication()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询报名状态</a>
</td>
</tr>
</table>
</div>
<br />
<div id="resultpanel" class="resulttable">
<table border="0" cellspacing="1" cellpadding="5" width="100%">
	<caption></caption>
	<tr>
		<th>报名登记号</th>
		<td id="number"></td>
		<th>班级</th>
		<td id="grade"></td>
		<th width="80">证件号码</th>
		<td colspan="3" id="idnumber"></td>
	</tr>
	<tr>
		<th width="80">姓名</th>
		<td width="120" id="name"></td>
		<th width="40">性别</th>
		<td width="80" id="gender"></td>
		<th width="70">出生年月</th>
		<td width="180" id="birthday"></td>
		<th width="50">国别</th>
		<td id="nation"></td>
	</tr>
</table><br />
<table id="resulttable" border="0" cellspacing="1" cellpadding="5" width="100%">
	<tr height="30">
		<th width="180">时间</th>
		<th align="center">状态</th>
	</tr>
</table><br />
</div>
</div>
</div>
<jsp:include page="foot.jsp"></jsp:include>
</body>
</html>