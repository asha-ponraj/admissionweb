<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="/admissionweb/tags" prefix="at" %>
<div class="page_foot">
	学校地址: <at:param name="school.address" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	联系电话: <at:param name="school.phone" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	官方网址: <a href="<at:param name="school.website" />"><at:param name="school.website" /></a>
	<at:param name="school.statisticscode" />
</div>
