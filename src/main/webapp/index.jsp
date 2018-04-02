<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.admission.util.Profile" %>
<%@taglib uri="/admissionweb/tags" prefix="at" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><at:param name="school.name" /></title>
<link rel="stylesheet" type="text/css" href="plugins/easyui/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="plugins/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href='css/my.css'>
<script type="text/javascript" src="plugins/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="plugins/json2.js"></script>
<script type="text/javascript" src="plugins/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="plugins/easyui/datagrid-detailview.js"></script>
<script type="text/javascript" src="plugins/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/global.js"></script>
<script type="text/javascript" src="js/pages/index.js"></script>
</head>
<body>
<jsp:include page="head.jsp"></jsp:include>
<div class="page_body">
	<div class="hmenu">
		<jsp:include page="menu.jsp"></jsp:include>
	</div>
	<div class="content">
		<div class="block1">
			<div class="title"><%=Profile.getInstance().getApplicationYear() %>年<at:param name="school.name" />招生报名安排</div>
			<div class="oflowitem" style="margin-left: 50px;">4月21日-5月7日<br>登记报名</div>
			<div class="oflowrightarrow" ></div>
			<div class="oflowitem"><at:param name="admission.accepttimespace" /><br>发放通知</div>
			<div class="oflowrightarrow" ></div>
			<div class="oflowitem"><at:param name="admission.downloadtimespace" /><br>打印报名表</div>
			<div class="oflowrightarrow" ></div>
			<div class="oflowitem"><at:param name="admission.interviewtimespace" /><br>参加面谈活动</div>
			<div class="oflowend"></div>
			<at:param name="admission.precautions" />
		</div>
		<div class="block2">
			<ul id="newslist">
				<at:listnews var="tnews">
					<li><a href='newsdetail.jsp?id=${tnews.id }'>${tnews.title }</a></li>
				</at:listnews>
			</ul>
		</div>
	</div>
	<div class="end" ></div>
</div>
<jsp:include page="foot.jsp"></jsp:include>
</body>
</html>