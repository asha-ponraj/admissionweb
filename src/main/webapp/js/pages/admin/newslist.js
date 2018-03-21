var ckeditor;
$(function() {
	ckeditor = $('#newscontent').ckeditor();
	initDialog();
	initNewsTable();
	reloadNewsTable(1);
});

function initDialog() {
	$('#newsdlg').dialog({
		title: '新闻添加与编辑',
		width: 700,
		height: 450,
		closable: true,
		closed: true,
		modal: true,
		buttons: [{
			iconCls:'icon-ok',
			text: '保存',
			handler: function() {
				if($('#actiontype').val() == "create") {
					onDlgCreateNews();
				} else if($('#actiontype').val() == "edit") {
					onDlgEditNews();
				}
			}
		}, {
			iconCls:'icon-cancel',
			text: '取消',
			handler: function() {
				$('#newsdlg').dialog('close');
			}
		}]
	});

	$('#newsdlg').dialog('dialog').attr('tabIndex','-1').bind('keydown',function(e){
		if (e.keyCode == 27){
			$('#newsdlg').dialog('close');
		}
	});
}

function initNewsTable() {
	$('#newsTable').datagrid({
		title: "新闻列表",
		fit: true,
		iconCls:'icon-sum',
		singleSelect : true,
		loadMsg : '正在加载新闻列表,请稍候......',
		nowrap: false,
		striped: true,
		url:'',
		sortName: null,
		sortOrder: null,
		remoteSort: false,
		idField:'id',
		pageSize : 50,
		showFooter:false,
		toolbar: [{
			id: 'createnewsbtn',
			iconCls: 'icon-add',
			text: '添加',
			handler: function() {
				showCreateNewsDlg();
			}
		},'-', {
			id: 'editnewsbtn',
			iconCls: 'icon-edit',
			text: '编辑',
			handler: function() {
				showEditNewsDlg();
			}
		},'-', {
			id: 'deletenewsbtn',
			iconCls: 'icon-remove',
			text: '删除',
			handler: function() {
				showDeleteNewsDlg();
			}
		}],
		columns : [ [ 
		  {field : 'title',title : '新闻标题', width : 500, sortable : false},
		  {field : 'createTimeStr',title : '发布时间', width : 150, sortable : false},
		  {field : 'topStr',title : '置顶', width : 40, sortable : false},
		  {field : 'topTimeStr',title : '置顶时间',	width : 150, sortable : false}
		 ] ],
		fitColumns: false,
		pagination:true,
		rownumbers:true,
		onLoadSuccess : function(data) {
			$(this).datagrid('unselectAll');
			$('#editnewsbtn').linkbutton('disable');
			$('#deletenewsbtn').linkbutton('disable');
		},
		onSelect : function(rowIndex, rowData) {
			$('#editnewsbtn').linkbutton('enable');
			$('#deletenewsbtn').linkbutton('enable');
		}
	});
	$('#editnewsbtn').linkbutton('disable');
	$('#deletenewsbtn').linkbutton('disable');
	//初始化分页信息
	var pageInfo = $('#newsTable').datagrid('getPager');
	if (pageInfo){
		$(pageInfo).pagination({
			onBeforeRefresh:function(pageNumber, pageSize){
				reloadNewsTable(pageNumber);
				return false;
			},
			onSelectPage : function(pageNumber, pageSize) {
				pageInfo.pagination('options').pageNumber = pageNumber;
				pageInfo.pagination('options').pageSize = pageSize;
				$('#newsTable').datagrid('options').pageNumber = pageNumber;
				$('#newsTable').datagrid('options').pageSize = pageSize;
				reloadNewsTable(pageNumber);
			}
		});
	}
}

function reloadNewsTable(pageNumber) {
	$('#newsTable').datagrid('loading');
	
	var pageSize = $('#newsTable').datagrid('options').pageSize;
	$('#newsTable').datagrid('getPager').pagination('options').pageNumber = pageNumber;
	
	var paramData = JSON.stringify({
		'pageNumber' : pageNumber,
		'pageSize' : pageSize
	});
	
	$.ajax({
		url: '../rest/news/find',
		type: 'POST',
		timeout: gAjaxTimeout,//超时时间设定
		dataType: "json",
		contentType: 'application/json;charset=UTF-8',
		beforeSend: function(x) {
            if (x && x.overrideMimeType) {
              x.overrideMimeType("application/j-son;charset=UTF-8");
            }
            //跨域使用
            x.setRequestHeader("Accept", "application/json");
        },
		data: paramData,//参数设置
		error: function(xhr, textStatus, thrownError){
			$('#newsTable').datagrid('loaded');
			if(xhr.readyState != 0 && xhr.readyState != 1) {
				$.messager.alert('错误',"获取报名表失败， 错误号:  " + xhr.status + ", 错误信息: " + textStatus,'error');
			}
			else {
				$.messager.alert('错误',"获取报名表失败，错误信息:  " + textStatus,'error');
			}
		},
		success: function(response, textStatus, xhr) {
			$('#newsTable').datagrid('loaded');
			if(xhr.status == 200) {
				if(response.result == "ok") {
					if(response.data != null) {
						$('#newsTable').datagrid('loadData', response.data);
					} else {
						$.messager.alert('提示',response.result,'info');
					}
				}
				else {
					$.messager.alert('错误',"获取报名表失败，错误信息: " + response.result,'error');
				}
			} else {
				$.messager.alert('错误',"获取报名表失败，错误号: " + xhr.status,'error');
			}
		}
	});
}

function clearApplicationTable(){
	$('#newsTable').datagrid('unselectAll');
	$('#newsTable').datagrid('loadData',{total:0,rows:[]});
}

function showCreateNewsDlg() {
	$('#actiontype').val('create');
	$('#newsid').val('0');
	$('#newstitle').val('');
	ckeditor.val('');
	$('#topchk').prop('checked', false);
	
	$('#newsdlg').dialog('setTitle', '添加新闻');
	$('#newsdlg').dialog('open');
}

function showEditNewsDlg() {
	var node = $('#newsTable').datagrid('getSelected');
	if(node){
		$.ajax({
			url: '../rest/news/get/' + node.id,
			type: 'GET',
			timeout: gAjaxTimeout,//超时时间设定
			dataType: "json",
			contentType: 'application/json;charset=UTF-8',
			beforeSend: function(x) {
	            if (x && x.overrideMimeType) {
	              x.overrideMimeType("application/j-son;charset=UTF-8");
	            }
	            //跨域使用
	            x.setRequestHeader("Accept", "application/json");
	        },
			data: {},//参数设置
			error: function(xhr, textStatus, thrownError){
				$('#newsTable').datagrid('loaded');
				if(xhr.readyState != 0 && xhr.readyState != 1) {
					$.messager.alert('错误',"获取新闻失败， 错误号:  " + xhr.status + ", 错误信息: " + textStatus,'error');
				}
				else {
					$.messager.alert('错误',"获取新闻失败，错误信息:  " + textStatus,'error');
				}
			},
			success: function(response, textStatus, xhr) {
				if(xhr.status == 200) {
					if(response.result == "ok") {
						$('#actiontype').val('edit');
						$('#newsid').val(node.id);
						$('#newstitle').val(response.data.title);
						ckeditor.val(response.data.content);
						$('#topchk').prop('checked', response.data.top);
						
						$('#newsdlg').dialog('setTitle', '查看/编辑新闻');
						$('#newsdlg').dialog('open');
					}
					else {
						$.messager.alert('错误',"获取新闻失败，错误信息: " + response.result,'error');
					}
				} else {
					$.messager.alert('错误',"获取新闻失败，错误号: " + xhr.status,'error');
				}
			}
		});
	}
}

function showDeleteNewsDlg() {
	var node = $('#newsTable').datagrid('getSelected');
	if(node){
		$.messager.defaults.ok = '确定';
		$.messager.defaults.cancel = '取消';
		var tip = '确定要删除所选新闻吗?'; 
		$.messager.confirm('温馨提示', tip,
			function(sure) {
				if(sure) {
					$.ajax({
						url: '../rest/news/delete/' + node.id,
						type: 'GET',
						dataType: "json",
						contentType: 'application/json;charset=UTF-8',
						beforeSend: function(x) {
				            if (x && x.overrideMimeType) {
				              x.overrideMimeType("application/j-son;charset=UTF-8");
				            }
				            //跨域使用
				            x.setRequestHeader("Accept", "application/json");
				        },
						timeout: gAjaxTimeout,
						data: {},
						error: function(xhr, textStatus, thrownError){
							if(xhr.readyState != 0 && xhr.readyState != 1) {
					     		alert("删除新闻失败， 错误号:  " + xhr.status + ", 错误信息: " + textStatus);
							}else{
							 	alert("删除新闻失败，错误信息:  " + textStatus);
							}
						},
						success: function(response, textStatus, xhr) {
							if(xhr.status == 200) {
								if(response.result == "ok") {
									var index = $('#newsTable').datagrid('getRowIndex', node);
									$('#newsTable').datagrid('deleteRow', index);
								}
								else {
									alert(response.result);
								}
							} else {
								alert("删除新闻失败，错误号: " + xhr.status);
							}
						}
					});
				}
			}
		);
	}
}

function onDlgCreateNews() {
	var newsid = $('#newsid').val();
	var newstitle = $.trim($('#newstitle').val());
	if(!checkMaxlength('#newstitle', newstitle, 120, "新闻标题", true))
		return;
	
	var newscontent = $.trim(ckeditor.val());
	if(!checkMaxlength('#newscontent', newscontent, 12000, "新闻内容", true))
		return;

	var top = $("#topchk").is(":checked");
	
	var newsData = JSON.stringify({
		"id": newsid,
		"title": newstitle,
		"content": newscontent,
		"top": top
	});
	
	$.ajax({
		url: '../rest/news/create',
		type: 'POST',
		dataType: "json",
		contentType: 'application/json;charset=UTF-8',
		beforeSend: function(x) {
            if (x && x.overrideMimeType) {
              x.overrideMimeType("application/j-son;charset=UTF-8");
            }
            //跨域使用
            x.setRequestHeader("Accept", "application/json");
        },
		timeout: gAjaxTimeout,
		data: newsData,
		error: function(xhr, textStatus, thrownError){
			if(xhr.readyState != 0 && xhr.readyState != 1) {
	     alert("添加新闻失败， 错误号:  " + xhr.status + ", 错误信息: " + textStatus);
			}else{
			 	alert("添加新闻失败，错误信息:  " + textStatus);
			}
		},
		success: function(response, textStatus, xhr) {
			if(xhr.status == 200) {
				if(response.result == "ok") {
					$('#newsTable').datagrid('appendRow', response.data);
					$('#newsdlg').dialog('close');
				}
				else {
					alert(response.result);
				}
			} else {
				alert("添加新闻失败，错误号: " + xhr.status);
			}
		}
	});
}

function onDlgEditNews() {
	var newsid = $('#newsid').val();
	var newstitle = $.trim($('#newstitle').val());
	if(!checkMaxlength('#newstitle', newstitle, 120, "新闻标题", true))
		return;
	
	var newscontent = $.trim(ckeditor.val());
	if(!checkMaxlength('#newscontent', newscontent, 12000, "新闻内容", true))
		return;

	var top = $("#topchk").is(":checked");
	
	
	var newsData = JSON.stringify({
		"id": newsid,
		"title": newstitle,
		"content": newscontent,
		"top": top
	});
	
	$.ajax({
		url: '../rest/news/update',
		type: 'POST',
		dataType: "json",
		contentType: 'application/json;charset=UTF-8',
		beforeSend: function(x) {
            if (x && x.overrideMimeType) {
              x.overrideMimeType("application/j-son;charset=UTF-8");
            }
            //跨域使用
            x.setRequestHeader("Accept", "application/json");
        },
		timeout: gAjaxTimeout,
		data: newsData,
		error: function(xhr, textStatus, thrownError){
			if(xhr.readyState != 0 && xhr.readyState != 1) {
	     alert("编辑新闻失败， 错误号:  " + xhr.status + ", 错误信息: " + textStatus);
			}else{
			 	alert("编辑新闻失败，错误信息:  " + textStatus);
			}
		},
		success: function(response, textStatus, xhr) {
			if(xhr.status == 200) {
				if(response.result == "ok") {
					var node = $('#newsTable').datagrid('getSelected');
					var index = $('#newsTable').datagrid('getRowIndex', node);
					$('#newsTable').datagrid('updateRow', {'index' : index, 'row' : response.data});
					$('#newsdlg').dialog('close');
				}
				else {
					alert(response.result);
				}
			} else {
				alert("编辑新闻失败，错误号: " + xhr.status);
			}
		}
	});
}

