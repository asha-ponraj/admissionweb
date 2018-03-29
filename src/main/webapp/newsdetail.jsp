<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/admissionweb/tags" prefix="at" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><at:param name="school.name" /></title>
<link rel="stylesheet" type="text/css" href="plugins/easyui/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="plugins/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="css/<at:param name="style.name" defaultValue="my" />.css">
<script type="text/javascript" src="plugins/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="plugins/json2.js"></script>
<script type="text/javascript" src="plugins/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="plugins/easyui/datagrid-detailview.js"></script>
<script type="text/javascript" src="plugins/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/global.js"></script>
<script type="text/javascript" src="js/pages/newsdetail.js"></script>
</head>
<body>
<input type="hidden" id="id" value="${param.id}" />
<jsp:include page="head.jsp"></jsp:include>
<div class="page_body">
	<div class="hmenu">
		<jsp:include page="menu.jsp"></jsp:include>
	</div>
	<div class="content">
		<div class="block1">
			<div id="title" style="text-align: center; font-size: 18px;"></div>
			<div id="createtime" style="text-align: center; font-size: 12px;"></div>
		</div>
		<div class="block2">
			<div id="content"></div>
		</div>
	</div>
	<div class="end" ></div>
</div>
<jsp:include page="foot.jsp"></jsp:include>
</body>
</html>