$(function() {
	initDialog();
	initOptionItemTreeGrid();
});

function initDialog() {
	$('#optionitemdlg').dialog({
		title: '候选项添加与编辑',
		width: 680,
		height: 220,
		closable: true,
		closed: true,
		modal: true,
		buttons: [{
			iconCls:'icon-ok',
			text: '保存',
			handler: function() {
				if($('#actiontype').val() == "create") {
					onDlgCreateOptionItem();
				} else if($('#actiontype').val() == "edit") {
					onDlgEditOptionItem();
				}
			}
		}, {
			iconCls:'icon-cancel',
			text: '取消',
			handler: function() {
				$('#optionitemdlg').dialog('close');
			}
		}]
	});

	$('#optionitemdlg').dialog('dialog').attr('tabIndex','-1').bind('keydown',function(e){
		if (e.keyCode == 27){
			$('#optionitemdlg').dialog('close');
		}
	});
}

function initOptionItemTreeGrid() {
	$('#optionitemtreegrid').treegrid({
		title: "候选项列表",
		fit: true,
		iconCls:'icon-sum',
		singleSelect : true,
		loadMsg : '正在加载候选项列表,请稍候......',
		nowrap: false,
		striped: true,
		url:'../rest/component/findoptionitems',
		sortName: null,
		sortOrder: null,
		remoteSort: false,
		idField: 'id',
		treeField: 'itemText',
		toolbar: [{
			id: 'createrootoptionitembtn',
			iconCls: 'icon-add',
			text: '添加根选项',
			handler: function() {
				showCreateRootOptionItemDlg();
			}
		},'-', {
			id: 'createchildoptionitembtn',
			iconCls: 'icon-add',
			text: '添加子选项',
			handler: function() {
				showCreateChildOptionItemDlg();
			}
		},'-', {
			id: 'editoptionitembtn',
			iconCls: 'icon-edit',
			text: '编辑选项',
			handler: function() {
				showEditOptionItemDlg();
			}
		},'-', {
			id: 'deleteoptionitembtn',
			iconCls: 'icon-remove',
			text: '删除选项',
			handler: function() {
				showDeleteOptionItemDlg();
			}
		},'-', {
			id: 'refreshoptionitembtn',
			iconCls: 'icon-reload',
			text: '刷新',
			handler: function() {
				$('#optionitemtreegrid').treegrid('reload');
			}
		}],
		columns : [ [ 
		  {field : 'comKey',title : 'Component Key', width : 150, sortable : false},
		  {field : 'itemText',title : 'Item Text', width : 300, sortable : false},
		  {field : 'itemValue',title : 'Item Value', width : 80, sortable : false},
		  {field : 'itemSeq',title : 'Item Sequence',	width : 80, sortable : false},
		  {field : 'itemSelected',title : 'Item Selected',	width : 80, sortable : false},
		  {field : 'validator',title : 'Validator',	width : 500, sortable : false}
		 ] ],
		fitColumns: false,
		rownumbers:true,
		onLoadSuccess : function(data) {
			$(this).treegrid('unselectAll');
			$('#createchildoptionitembtn').linkbutton('disable');
			$('#editoptionitembtn').linkbutton('disable');
			$('#deleteoptionitembtn').linkbutton('disable');
		},
		onSelect : function(rowIndex, rowData) {
			$('#createchildoptionitembtn').linkbutton('enable');
			$('#editoptionitembtn').linkbutton('enable');
			$('#deleteoptionitembtn').linkbutton('enable');
		}
	});
	$('#createchildoptionitembtn').linkbutton('disable');
	$('#editoptionitembtn').linkbutton('disable');
	$('#deleteoptionitembtn').linkbutton('disable');
}

function showCreateRootOptionItemDlg() {
	$('#actiontype').val('create');
	$('#optionitemid').val('0');
	$('#parentoptionitemid').val('0');
	$('#comkey').val('');
	$('#itemvalue').val('');
	$('#itemtext').val('');
	$('#itemseq').val('');
	$('#itemselected').prop('checked', false);
	$('#validator').val('');

	$('#comkey').validatebox('validate');
	$('#itemvalue').validatebox('validate');
	$('#itemtext').validatebox('validate');
	$('#itemseq').validatebox('validate');
	
	$('#optionitemdlg').dialog('setTitle', '添加根选项');
	$('#optionitemdlg').dialog('open');
}

function showCreateChildOptionItemDlg() {
	var node = $('#optionitemtreegrid').treegrid('getSelected');
	
	if(node) {
		$('#actiontype').val('create');
		$('#optionitemid').val('0');
		$('#parentoptionitemid').val(node.id);
		$('#comkey').val('');
		$('#itemvalue').val('');
		$('#itemtext').val('');
		$('#itemseq').val('');
		$('#itemselected').prop('checked', false);
		$('#validator').val('');

		$('#comkey').validatebox('validate');
		$('#itemvalue').validatebox('validate');
		$('#itemtext').validatebox('validate');
		$('#itemseq').validatebox('validate');
		
		$('#optionitemdlg').dialog('setTitle', '添加子选项');
		$('#optionitemdlg').dialog('open');
	}
}

function showEditOptionItemDlg() {
	var node = $('#optionitemtreegrid').treegrid('getSelected');
	if(node){
		$('#actiontype').val('edit');
		$('#optionitemid').val(node.id);
		$('#parentoptionitemid').val(node.parentId);
		$('#comkey').val(node.comKey);
		$('#itemvalue').val(node.itemValue);
		$('#itemtext').val(node.itemText);
		$('#itemseq').val(node.itemSeq);
		$('#itemselected').prop('checked', node.itemSelected);
		$('#validator').val(node.validator);

		$('#comkey').validatebox('validate');
		$('#itemvalue').validatebox('validate');
		$('#itemtext').validatebox('validate');
		$('#itemseq').validatebox('validate');
		
		$('#optionitemdlg').dialog('setTitle', '编辑选项');
		$('#optionitemdlg').dialog('open');
	}
}

function showDeleteOptionItemDlg() {
	var node = $('#optionitemtreegrid').treegrid('getSelected');
	if(node){
		$.messager.defaults.ok = '确定';
		$.messager.defaults.cancel = '取消';
		var tip = '确定要删除所选候选项吗?'; 
		$.messager.confirm('温馨提示', tip,
			function(sure) {
				if(sure) {
					$.ajax({
						url: '../rest/component/deleteoptionitem/' + node.id,
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
					     		alert("删除候选项失败， 错误号:  " + xhr.status + ", 错误信息: " + textStatus);
							}else{
							 	alert("删除候选项失败，错误信息:  " + textStatus);
							}
						},
						success: function(response, textStatus, xhr) {
							if(xhr.status == 200) {
								if(response.result == "ok") {
									$('#optionitemtreegrid').treegrid('remove', node.id);
								}
								else {
									alert(response.result);
								}
							} else {
								alert("删除候选项失败，错误号: " + xhr.status);
							}
						}
					});
				}
			}
		);
	}
}

function onDlgCreateOptionItem() {
	var optionitemid = $('#optionitemid').val();
	var parentid = $('#parentoptionitemid').val();
	var comkey = $('#comkey').val();
	var itemvalue = $('#itemvalue').val();
	var itemtext = $('#itemtext').val();
	var itemseq = $('#itemseq').val();
	var itemselected = $('#itemselected').is(":checked");
	var validator = $('#validator').val();
	
	if(!checkMaxlength('#comkey', comkey, 120, "component key", true))
		return;
	if(!checkMaxlength('#itemvalue', itemvalue, 120, "item value", true))
		return;
	if(!checkMaxlength('#itemtext', itemtext, 120, "item text", true))
		return;
	if(!checkMaxlength('#itemseq', itemseq, 12, "item sequence", true))
		return;
	if(!checkMaxlength('#validator', validator, 120, "validator", false))
		return;
	
	
	var optionItemData = JSON.stringify({
		"id": optionitemid,
		"parentId": parentid,
		"comKey": comkey,
		"itemValue": itemvalue,
		"itemText": itemtext,
		"itemSeq": itemseq,
		"itemSelected": itemselected,
		"validator": validator
	});
	
	$.ajax({
		url: '../rest/component/createoptionitem',
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
		data: optionItemData,
		error: function(xhr, textStatus, thrownError){
			if(xhr.readyState != 0 && xhr.readyState != 1) {
	     alert("添加候选项失败， 错误号:  " + xhr.status + ", 错误信息: " + textStatus);
			}else{
			 	alert("添加候选项失败，错误信息:  " + textStatus);
			}
		},
		success: function(response, textStatus, xhr) {
			if(xhr.status == 200) {
				if(response.result == "ok") {
					if(response.data.parentId == '0') {
						$('#optionitemtreegrid').treegrid('append', {
						     "parent": '',
						     "data": [response.data]
							});
					} else {
						$('#optionitemtreegrid').treegrid('append', {
						     "parent": response.data.parentId,
						     "data": [response.data]
							});
					}
					$('#optionitemdlg').dialog('close');
				}
				else {
					alert(response.result);
				}
			} else {
				alert("添加候选项失败，错误号: " + xhr.status);
			}
		}
	});
}

function onDlgEditOptionItem() {
	var optionitemid = $('#optionitemid').val();
	var parentid = $('#parentoptionitemid').val();
	var comkey = $('#comkey').val();
	var itemvalue = $('#itemvalue').val();
	var itemtext = $('#itemtext').val();
	var itemseq = $('#itemseq').val();
	var itemselected = $('#itemselected').is(":checked");
	var validator = $('#validator').val();
	
	if(!checkMaxlength('#comkey', comkey, 120, "component key", true))
		return;
	if(!checkMaxlength('#itemvalue', itemvalue, 120, "item value", true))
		return;
	if(!checkMaxlength('#itemtext', itemtext, 120, "item text", true))
		return;
	if(!checkMaxlength('#itemseq', itemseq, 12, "item sequence", true))
		return;
	if(!checkMaxlength('#validator', validator, 120, "validator", false))
		return;
	
	
	var optionItemData = JSON.stringify({
		"id": optionitemid,
		"parentId": parentid,
		"comKey": comkey,
		"itemValue": itemvalue,
		"itemText": itemtext,
		"itemSeq": itemseq,
		"itemSelected": itemselected,
		"validator": validator
	});
	
	$.ajax({
		url: '../rest/component/updateoptionitem',
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
		data: optionItemData,
		error: function(xhr, textStatus, thrownError){
			if(xhr.readyState != 0 && xhr.readyState != 1) {
	     alert("编辑侯选项失败， 错误号:  " + xhr.status + ", 错误信息: " + textStatus);
			}else{
			 	alert("编辑侯选项失败，错误信息:  " + textStatus);
			}
		},
		success: function(response, textStatus, xhr) {
			if(xhr.status == 200) {
				if(response.result == "ok") {
					var node = $('#optionitemtreegrid').treegrid('getSelected');
					var index = $('#optionitemtreegrid').treegrid('getRowIndex', node);
					$('#optionitemtreegrid').treegrid('update', {'id' : response.data.id, 'row' : response.data});
					$('#optionitemdlg').dialog('close');
				}
				else {
					alert(response.result);
				}
			} else {
				alert("编辑侯选项失败，错误号: " + xhr.status);
			}
		}
	});
}

