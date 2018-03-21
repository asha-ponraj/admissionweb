<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/admissionweb/tags" prefix="at" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上海闵行区启英幼儿园</title>
<link rel="stylesheet" type="text/css" href="plugins/easyui/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="plugins/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="css/my.css">
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
			<div class="title">2016年启英幼儿园招生报名安排</div>
			<div class="oflowitem" style="margin-left: 50px;">4月25日-5月10日<br>在线登记报名</div>
			<div class="oflowrightarrow" ></div>
			<div class="oflowitem">5月11日-5月12日<br>受理发放通知</div>
			<div class="oflowrightarrow" ></div>
			<div class="oflowitem">5月13日-5月15日<br>下载打印报名表</div>
			<div class="oflowrightarrow" ></div>
			<div class="oflowitem">5月21日-5月22日<br>面谈活动</div>
			<div class="oflowend"></div>
			注意事项：<br>
			1. 登记时请牢记报名登记时设定的用户名和查询密码，将用于查询报名与招生过程的状态。<br>
			2. 参加我园面谈活动时，请携带从我园招生网站下载打印的报名表(收到面谈活动通知后方可下载)。
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