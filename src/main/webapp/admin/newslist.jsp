<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/admissionweb/tags" prefix="at" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><at:param name="school.name" /> - 新闻管理</title>
<link rel="stylesheet" type="text/css" href="../plugins/easyui/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="../plugins/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../css/admin.css">
<script type="text/javascript" src="../plugins/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="../plugins/json2.js"></script>
<script type="text/javascript" src="../plugins/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../plugins/easyui/datagrid-detailview.js"></script>
<script type="text/javascript" src="../plugins/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../js/global.js"></script>
<script type="text/javascript" src="../plugins/ckeditor5/ckeditor.js"></script>
<script type="text/javascript" src="../plugins/ckeditor5/translations/zh-cn.js"></script>
<script type="text/javascript" src="../js/pages/admin/newslist.js"></script>
<style type="text/css">
.document-editor {
    border: 1px solid var(--ck-color-base-border);
    border-radius: var(--ck-border-radius);

    /* Set vertical boundaries for the document editor. */
    max-height: 400px;

    /* This element is a flex container for easier rendering. */
    display: flex;
    flex-flow: column nowrap;
    overflow: hidden;
}
.toolbar-container {
    /* Make sure the toolbar container is always above the editable. */
    z-index: 1;

    /* Create the illusion of the toolbar floating over the editable. */
    box-shadow: 0 0 5px hsla( 0,0%,0%,.2 );

    /* Use the CKEditor CSS variables to keep the UI consistent. */
    border-bottom: 1px solid var(--ck-color-toolbar-border);
}

/* Adjust the look of the toolbar inside the container. */
.toolbar-container .ck-toolbar {
    border: 0;
    border-radius: 0;
}
.editable-container {
    padding: calc( 2 * var(--ck-spacing-large) );
    background: var(--ck-color-base-foreground);

    /* Make it possible to scroll the "page" of the edited content. */
    overflow-y: scroll;
}

.editable-container .ck-editor__editable {
    /* Set the dimensions of the "page". */
    width: 15.8cm;
    min-height: 21cm;

    /* Keep the "page" off the boundaries of the container. */
    padding: 1cm 2cm 2cm;

    border: 1px hsl( 0,0%,82.7% ) solid;
    border-radius: var(--ck-border-radius);
    background: white;

    /* The "page" should cast a slight shadow (3D illusion). */
    box-shadow: 0 0 5px hsla( 0,0%,0%,.1 );

    /* Center the "page". */
    margin: 0 auto;
}
.document-editor .ck-content,
.document-editor .ck-heading-dropdown .ck-list {
    font: 16px/1.6 "Helvetica Neue", Helvetica, Arial, sans-serif;
}
</style>
</head>
<body class="easyui-layout">
<div data-options="region:'center',border:false">
<table id="newsTable" style="width:990px;height:480px">
</table>
</div>
<div id="newsdlg">
	<input id="newsid" type="hidden" /><input id="actiontype" type="hidden" />
	<table>
		<tr>
			<th>标题</th>
			<td><input id="newstitle" class="easyui-validatebox" type="text" style="width: 640px;"
			data-options="required:true">&nbsp;&nbsp;<input type="button" id="sourcemodebtn" value="source"/></td>
		</tr>
		<tr>
			<th>内容</th>
			<td>
			<div id="newscontenteditor" class="document-editor">
				<div class="toolbar-container"></div>
				<div class="editable-container"></div>
			</div>
			<textarea id="newscontent" style="width: 700px; height: 400px; display:none;"></textarea>
			</td>
		</tr>
		<tr>
			<td></td>
			<td><label><input id="topchk" type="checkbox"> 是否置顶</label></td>
		</tr>
	</table>
</div>
</body>
</html>