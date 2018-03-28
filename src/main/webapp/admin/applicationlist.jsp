<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/admissionweb/tags" prefix="at" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><at:param name="school.name" /> - 申请管理</title>
<link rel="stylesheet" type="text/css" href="../plugins/easyui/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="../plugins/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../css/admin.css">
<script type="text/javascript" src="../plugins/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="../plugins/json2.js"></script>
<script type="text/javascript" src="../plugins/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../plugins/easyui/datagrid-detailview.js"></script>
<script type="text/javascript" src="../plugins/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../js/global.js"></script>
<script type="text/javascript" src="../js/pages/admin/applicationlist.js"></script>
</head>
<body class="easyui-layout">
<div data-options="region:'north',title:'查询',iconCls:'icon-search',border:true" style="height:103px;padding:6px;background-color:#eee;">
<table>
	<tr>
		<th>报名号</th>
		<td><input id="applicationid" class="easyui-validatebox" type="text" style="width: 80px;"
			data-options="required:false"></input></td>
		<th>证件号码</th>
		<td><input id="pidnumber" class="easyui-validatebox" type="text" style="width: 170px;"
			data-options="required:false"></input></td>
		<th>姓名</th>
		<td colspan="2"><input id="name" class="easyui-validatebox" type="text" style="width: 140px;"
			data-options="required:false"></td>
		<th>性别</th>
		<td>
			<select id="gender" style="width:100px">
				<option value="" selected>不限</option>
				<option value="男">男</option>
				<option value="女">女</option>
			</select>
		</td>
		<td colspan="2"><input id="blur" type="checkbox" value="" checked>模糊查询</input></td>
		<td>
			<iframe id="downloadframe" style="visibility:hidden;display:none" ></iframe>
		</td>
	</tr>
	<tr>
		<th>国籍</th>
		<td><input id="nation" class="easyui-validatebox" type="text" style="width: 80px;"
			data-options="required:false"></input></td>
		<th></th>
		<td></td>
		<th>排序</th>
		<td>
			<select id="sortname" style="width:80px">
				<option value="id" selected>报名号</option>
				<option value="pidNumber">身份证</option>
				<option value="name">姓名</option>
				<option value="status">状态</option>
			</select>
		</td>
		<td>
			<select id="sortorder" style="width:60px">
				<option value="asc">顺序</option>
				<option value="desc" selected>逆序</option>
			</select>
		</td>
		<th>状态</th>
		<td>
			<select id="status" style="width:100px">
				<option value="0" selected>不限</option>
				<option value="1">报名已提交</option>
				<option value="2">学校已受理</option>
				<option value="3">面谈通知已发放</option>
				<option value="4">报名表已下载</option>
				<option value="5">面谈活动已签到</option>
				<option value="10">报名被拒绝</option>
			</select>
		</td>
		<td><a id="searchbtn" href="#" onClick="searchApplication()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a></td>
		<td><a id="exportbtn" href="#" onClick="exportApplication()" class="easyui-linkbutton" data-options="iconCls:'icon-save'">下载</a></td>
		<td></td>
	</tr>
</table>
</div>
<div data-options="region:'center',border:false">
<table id="applicationTable" style="width:990px;height:480px">
</table>
</div>
<div id="reasondlg">
	<input id="reasondlgappid" type="hidden" />
	<table>
		<tr>
			<th>拒绝理由</th>
		</tr>
		<tr>
			<td><textarea id="reasondlgreason" rows="20" cols="30" style="width: 640px; height: 260px;"></textarea></td>
		</tr>
	</table>
</div>
</body>
</html>