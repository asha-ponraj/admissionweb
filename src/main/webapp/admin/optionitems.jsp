<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/admissionweb/tags" prefix="at" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><at:param name="school.name" /> - 候选项管理</title>
<link rel="stylesheet" type="text/css" href="../plugins/easyui/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="../plugins/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../css/admin.css">
<script type="text/javascript" src="../plugins/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="../plugins/json2.js"></script>
<script type="text/javascript" src="../plugins/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../plugins/easyui/datagrid-detailview.js"></script>
<script type="text/javascript" src="../plugins/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../js/global.js"></script>
<script type="text/javascript" src="../plugins/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="../plugins/ckeditor/lang/zh-cn.js"></script>
<script type="text/javascript" src="../plugins/ckeditor/adapters/jquery.js"></script>
<script type="text/javascript" src="../js/pages/admin/optionitems.js"></script>
</head>
<body class="easyui-layout">
<div data-options="region:'center',border:false">
<table id="optionitemtreegrid" style="width:990px;height:380px">
</table>
</div>
<div id="optionitemdlg">
	<input id="optionitemid" type="hidden" /><input id="actiontype" type="hidden" />
	<input id="parentoptionitemid" type="hidden" />
	<table>
		<tr>
			<th>Component Key</th>
			<td><input id="comkey" class="easyui-validatebox" type="text" style="width: 550px;"
			data-options="required:true"></td>
		</tr>
		<tr>
			<th>Item Value</th>
			<td><input id="itemvalue" class="easyui-validatebox" type="text" style="width: 550px;"
			data-options="required:true"></td>
		</tr>
		<tr>
			<td>Item Text</td>
			<td><input id="itemtext" class="easyui-validatebox" type="text" style="width: 550px;"
			data-options="required:true"></td>
		</tr>
		<tr>
			<td>Item Sequence</td>
			<td><input id="itemseq" class="easyui-validatebox" type="text" style="width: 550px;"
			data-options="required:true"></td>
		</tr>
		<tr>
			<td>Item Selected</td>
			<td><label><input id="itemselected" type="checkbox">默认选中</label></td>
		</tr>
		<tr>
			<td>Validator</td>
			<td><input id="validator" class="easyui-validatebox" type="text" style="width: 550px;"></td>
		</tr>
	</table>
</div>
</body>
</html>