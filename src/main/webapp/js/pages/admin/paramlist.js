$(function() {
	initDialog();
	initParamTable();
	reloadParamTable(1);
});

function initDialog() {
	$('#paramdlg').dialog({
		title: '参数添加与编辑',
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
					onDlgCreateParam();
				} else if($('#actiontype').val() == "edit") {
					onDlgEditParam();
				}
			}
		}, {
			iconCls:'icon-cancel',
			text: '取消',
			handler: function() {
				$('#paramdlg').dialog('close');
			}
		}]
	});

	$('#paramdlg').dialog('dialog').attr('tabIndex','-1').bind('keydown',function(e){
		if (e.keyCode == 27){
			$('#paramdlg').dialog('close');
		}
	});
}

function initParamTable() {
	$('#paramTable').datagrid({
		title: "参数列表",
		fit: true,
		iconCls:'icon-sum',
		singleSelect : true,
		loadMsg : '正在加载参数列表,请稍候......',
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
			id: 'createparambtn',
			iconCls: 'icon-add',
			text: '添加',
			handler: function() {
				showCreateParamDlg();
			}
		},'-', {
			id: 'editparambtn',
			iconCls: 'icon-edit',
			text: '编辑',
			handler: function() {
				showEditParamDlg();
			}
		},'-', {
			id: 'deleteparambtn',
			iconCls: 'icon-remove',
			text: '删除',
			handler: function() {
				showDeleteParamDlg();
			}
		}],
		columns : [ [ 
		  {field : 'name', title : '名字', width : 200, sortable : false},
		  {field : 'encodedValue', title : '内容', width : 400, sortable : false},
		  {field : 'description', title : '描述', width : 200, sortable : false}
		 ] ],
		fitColumns: false,
		pagination:true,
		rownumbers:true,
		onLoadSuccess : function(data) {
			$(this).datagrid('unselectAll');
			$('#editparambtn').linkbutton('disable');
			$('#deleteparambtn').linkbutton('disable');
		},
		onSelect : function(rowIndex, rowData) {
			$('#editparambtn').linkbutton('enable');
			$('#deleteparambtn').linkbutton('enable');
		}
	});
	$('#editparambtn').linkbutton('disable');
	$('#deleteparambtn').linkbutton('disable');
	//初始化分页信息
	var pageInfo = $('#paramTable').datagrid('getPager');
	if (pageInfo){
		$(pageInfo).pagination({
			onBeforeRefresh:function(pageNumber, pageSize){
				reloadParamTable(pageNumber);
				return false;
			},
			onSelectPage : function(pageNumber, pageSize) {
				pageInfo.pagination('options').pageNumber = pageNumber;
				pageInfo.pagination('options').pageSize = pageSize;
				$('#paramTable').datagrid('options').pageNumber = pageNumber;
				$('#paramTable').datagrid('options').pageSize = pageSize;
				reloadParamTable(pageNumber);
			}
		});
	}
}

function reloadParamTable(pageNumber) {
	$('#paramTable').datagrid('loading');
	
	var pageSize = $('#paramTable').datagrid('options').pageSize;
	$('#paramTable').datagrid('getPager').pagination('options').pageNumber = pageNumber;
	
	var paramData = JSON.stringify({
		'pageNumber' : pageNumber,
		'pageSize' : pageSize
	});
	
	$.ajax({
		url: '../rest/parameter/find',
		headers: { 
	        'Accept': 'application/json',
	        'Content-Type': 'application/json' 
	    },
		'dataType': 'json',
		type: 'POST',
		timeout: gAjaxTimeout,//超时时间设定
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
			$('#paramTable').datagrid('loaded');
			if(xhr.readyState != 0 && xhr.readyState != 1) {
				$.messager.alert('错误',"获取参数失败， 错误号:  " + xhr.status + ", 错误信息: " + textStatus,'error');
			}
			else {
				$.messager.alert('错误',"获取参数失败，错误信息:  " + textStatus,'error');
			}
		},
		success: function(response, textStatus, xhr) {
			$('#paramTable').datagrid('loaded');
			if(xhr.status == 200) {
				if(response.result == "ok") {
					if(response.data != null) {
						$('#paramTable').datagrid('loadData', response.data);
					} else {
						$.messager.alert('提示',response.result,'info');
					}
				}
				else {
					$.messager.alert('错误',"获取参数失败，错误信息: " + response.result,'error');
				}
			} else {
				$.messager.alert('错误',"获取参数失败，错误号: " + xhr.status,'error');
			}
		}
	});
}

function clearParamTable(){
	$('#paramTable').datagrid('unselectAll');
	$('#paramTable').datagrid('loadData',{total:0,rows:[]});
}

function showCreateParamDlg() {
	$('#actiontype').val('create');
	$('#paramid').val('0');
	$('#paramname').val('');
	$('#paramdesc').val('');
	$('#paramvalue').val('');
	
	$('#paramdlg').dialog('setTitle', '添加参数');
	$('#paramdlg').dialog('open');
}

function showEditParamDlg() {
	var node = $('#paramTable').datagrid('getSelected');
	if(node){
		$.ajax({
			url: '../rest/parameter/get/' + node.id,
			headers: { 
		        'Accept': 'application/json',
		        'Content-Type': 'application/json' 
		    },
			'dataType': 'json',
			type: 'GET',
			timeout: gAjaxTimeout,//超时时间设定
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
				$('#paramTable').datagrid('loaded');
				if(xhr.readyState != 0 && xhr.readyState != 1) {
					$.messager.alert('错误',"获取参数失败， 错误号:  " + xhr.status + ", 错误信息: " + textStatus,'error');
				}
				else {
					$.messager.alert('错误',"获取参数失败，错误信息:  " + textStatus,'error');
				}
			},
			success: function(response, textStatus, xhr) {
				if(xhr.status == 200) {
					if(response.result == "ok") {
						$('#actiontype').val('edit');
						$('#paramid').val(node.id);
						$('#paramname').val(response.data.name);
						$('#paramdesc').val(response.data.description);
						$('#paramvalue').val(response.data.value);
						
						$('#paramdlg').dialog('setTitle', '查看/编辑参数');
						$('#paramdlg').dialog('open');
					}
					else {
						$.messager.alert('错误',"获取参数失败，错误信息: " + response.result,'error');
					}
				} else {
					$.messager.alert('错误',"获取参数失败，错误号: " + xhr.status,'error');
				}
			}
		});
	}
}

function showDeleteParamDlg() {
	var node = $('#paramTable').datagrid('getSelected');
	if(node){
		$.messager.defaults.ok = '确定';
		$.messager.defaults.cancel = '取消';
		var tip = '确定要删除所选参数吗?'; 
		$.messager.confirm('温馨提示', tip,
			function(sure) {
				if(sure) {
					$.ajax({
						url: '../rest/parameter/delete/' + node.id,
						headers: { 
					        'Accept': 'application/json',
					        'Content-Type': 'application/json' 
					    },
						dataType: 'json',
						type: 'GET',
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
					     		alert("删除参数失败， 错误号:  " + xhr.status + ", 错误信息: " + textStatus);
							}else{
							 	alert("删除参数失败，错误信息:  " + textStatus);
							}
						},
						success: function(response, textStatus, xhr) {
							if(xhr.status == 200) {
								if(response.result == "ok") {
									var index = $('#paramTable').datagrid('getRowIndex', node);
									$('#paramTable').datagrid('deleteRow', index);
								}
								else {
									alert(response.result);
								}
							} else {
								alert("删除参数失败，错误号: " + xhr.status);
							}
						}
					});
				}
			}
		);
	}
}

function onDlgCreateParam() {
	var paramid = $('#paramid').val();
	var paramname = $.trim($('#paramname').val());
	if(!checkMaxlength('#paramname', paramname, 120, "参数名字", true))
		return;

	var paramdesc = $.trim($('#paramdesc').val());
	if(!checkMaxlength('#paramdesc', paramdesc, 120, "参数描述", false))
		return;
	
	var paramvalue = $.trim($('#paramvalue').val());
	if(!checkMaxlength('#paramvalue', paramvalue, 12000, "参数内容", true))
		return;
	
	var paramData = JSON.stringify({
		"id": paramid,
		"name": paramname,
		"description": paramdesc,
		"value": paramvalue
	});
	
	$.ajax({
		url: '../rest/parameter/create',
		headers: { 
	        'Accept': 'application/json',
	        'Content-Type': 'application/json' 
	    },
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
		data: paramData,
		error: function(xhr, textStatus, thrownError){
			if(xhr.readyState != 0 && xhr.readyState != 1) {
	     alert("添加参数失败， 错误号:  " + xhr.status + ", 错误信息: " + textStatus);
			}else{
			 	alert("添加参数失败，错误信息:  " + textStatus);
			}
		},
		success: function(response, textStatus, xhr) {
			if(xhr.status == 200) {
				if(response.result == "ok") {
					$('#paramTable').datagrid('appendRow', response.data);
					$('#paramdlg').dialog('close');
				}
				else {
					alert(response.result);
				}
			} else {
				alert("添加参数失败，错误号: " + xhr.status);
			}
		}
	});
}

function onDlgEditParam() {
	var paramid = $('#paramid').val();
	var paramname = $.trim($('#paramname').val());
	if(!checkMaxlength('#paramname', paramname, 120, "参数名字", true))
		return;

	var paramdesc = $.trim($('#paramdesc').val());
	if(!checkMaxlength('#paramdesc', paramdesc, 120, "参数描述", false))
		return;
	
	var paramvalue = $.trim($('#paramvalue').val());
	if(!checkMaxlength('#paramvalue', paramvalue, 12000, "参数内容", true))
		return;
	
	var paramData = JSON.stringify({
		"id": paramid,
		"name": paramname,
		"description": paramdesc,
		"value": paramvalue
	});
	
	$.ajax({
		url: '../rest/parameter/update',
		headers: { 
	        'Accept': 'application/json',
	        'Content-Type': 'application/json' 
	    },
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
		data: paramData,
		error: function(xhr, textStatus, thrownError){
			if(xhr.readyState != 0 && xhr.readyState != 1) {
	     alert("编辑参数失败， 错误号:  " + xhr.status + ", 错误信息: " + textStatus);
			}else{
			 	alert("编辑参数失败，错误信息:  " + textStatus);
			}
		},
		success: function(response, textStatus, xhr) {
			if(xhr.status == 200) {
				if(response.result == "ok") {
					var node = $('#paramTable').datagrid('getSelected');
					var index = $('#paramTable').datagrid('getRowIndex', node);
					$('#paramTable').datagrid('updateRow', {'index' : index, 'row' : response.data});
					$('#paramdlg').dialog('close');
				}
				else {
					alert(response.result);
				}
			} else {
				alert("编辑参数失败，错误号: " + xhr.status);
			}
		}
	});
}

