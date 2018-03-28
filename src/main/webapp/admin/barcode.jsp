<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.admission.util.*, java.sql.Timestamp"%>
<%@taglib uri="/admissionweb/tags" prefix="at" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><at:param name="school.name" /> - 条码查询与签到</title>
<link rel="stylesheet" type="text/css" href="../plugins/easyui/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="../plugins/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../css/admin.css">
<script type="text/javascript" src="../plugins/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="../plugins/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../plugins/easyui/datagrid-detailview.js"></script>
<script type="text/javascript" src="../plugins/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../js/global.js"></script>
<script type="text/javascript" src="../js/pages/admin/barcode.js"></script>
</head>
<body class="easyui-layout">
<div data-options="region:'center',border:false,title:'条形码查询与签到'">
<table align="center" style="margin-top: 10px;" cellspacing="10">
	<tr>
		<th width="100"><label><input id="checkin" type="checkbox"> 查询并签到</label></th>
		<th width="70">条码/报名号</th>
		<td width="350"><input id="barcode" style="width: 350px;"></td>
		<td width="200"><a id="submitbtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a></td>
	</tr>
	<tr>
		<td colspan="4" align="left">
			<table cellpadding="5" border="1" style="margin-top: 30px; border-collapse: collapse;">
				<tr>
					<th>报名号</th>
					<td id="t_number"> </td>
					<th>条形码</th>
					<td id="t_barcode" colspan="3"> </td>
				</tr>
				<tr>
					<th width="60">姓名</th>
					<td width="160" id="t_name"> </td>
					<th width="60">性别</th>
					<td width="80" id="t_gender"> </td>
					<th width="60">生日</th>
					<td width="160" id="t_birthday"> </td>
				</tr>
				<tr>
					<th>身份证</th>
					<td colspan="3" id="t_idnumber"> </td>
					<th>国别</th>
					<td id="t_nation"> </td>
				</tr>
				<tr>
					<th>签到时间</th>
					<td colspan="3" id="t_checkintime"> </td>
					<th>签到状态</th>
					<td id="t_recheckin"> </td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</div>
</body>
</html>