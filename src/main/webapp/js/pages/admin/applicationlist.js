$(function() {
	$('#gender').combobox({
		required: true,
		editable: false
	});
	
	$('#grade').combobox({
		required: true,
		editable: false
	});
	
	$('#status').combobox({
		required: true,
		editable: false
	});
	
	$('#sortname').combobox({
		required: true,
		editable: false
	});
	
	$('#sortorder').combobox({
		required: true,
		editable: false,
		value: 'desc'
	});
	
	initDenyDialog();
	initApplicationTable();
	
});

function initDenyDialog() {
	$('#reasondlg').dialog({
		title: '拒绝报名申请',
		width: 680,
		height: 380,
		closable: true,
		closed: true,
		modal: true,
		buttons: [{
			iconCls:'icon-ok',
			text: '确定',
			handler: function() {
				onDlgDenyApplication();
			}
		}, {
			iconCls:'icon-cancel',
			text: '取消',
			handler: function() {
				$('#reasondlg').dialog('close');
			}
		}]
	});
}

function initApplicationTable() {
	$('#applicationTable').datagrid({
		title: "报名信息查询列表",
		fit: true,
		iconCls:'icon-sum',
		singleSelect : true,
		loadMsg : '正在查询报名表,请稍候......',
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
			id: 'resetquerypasswordbtn',
			iconCls: 'icon-edit',
			text: '重置查询密码',
			handler: function() {
				$.messager.defaults.ok = '确定';
				$.messager.defaults.cancel = '取消';
				var tip = '确定要重置密码吗？'; 
				$.messager.confirm('确认提示', tip,
					function(sure) {
						if(sure) {
							resetQueryPassword();
						}
				});
			}
		}, {
			id: 'deleteapplicationbtn',
			iconCls: 'icon-remove',
			text: '删除报名表',
			handler: function() {
				$.messager.defaults.ok = '确定';
				$.messager.defaults.cancel = '取消';
				var tip = '确定要删除报名表吗？'; 
				$.messager.confirm('确认提示', tip,
					function(sure) {
						if(sure) {
							deleteApplication();
						}
				});
			}
		}, {
			id: 'denyapplicationbtn',
			iconCls: 'icon-cancel',
			text: '拒绝报名',
			handler: function() {
				showDenyDlg();
			}
		}, {
			id: 'resetapplicationbtn',
			iconCls: 'icon-cancel',
			text: '恢复报名',
			handler: function() {
				$.messager.defaults.ok = '确定';
				$.messager.defaults.cancel = '取消';
				var tip = '确定要恢复报名吗？'; 
				$.messager.confirm('确认提示', tip,
					function(sure) {
						if(sure) {
							resetApplication();
						}
				});
			}
		}, {
			id: 'downloadapplicationbtn',
			iconCls: 'icon-down',
			text: '下载报名表',
			handler: function() {
				$.messager.defaults.ok = '确定';
				$.messager.defaults.cancel = '取消';
				var tip = '确定要下载报名表吗？'; 
				$.messager.confirm('确认提示', tip,
					function(sure) {
						if(sure) {
							downloadApplication();
						}
				});
			}
		}],
		columns : [ [ 
		  {field : 'id',title : '报名号', width : 60, sortable : false},
		  {field : 'username',title : '用户名', width : 100, sortable : false},
		  {field : 'statusStr',title : '状态',	width : 100,sortable : false},
		  {field : 'gradeStr',title : '班级',	width : 100,sortable : false},
		  {field : 'candidateTypeStr',title : '招生对象属性', width : 85, sortable : false},
		  {field : 'name',title : '姓名', width : 100, sortable : false},
		  {field : 'gender',title : '性别', width : 40,sortable : false},
		  {field : 'birthdayStr',title : '出生日期',	width : 100,sortable : false},
		  {field : 'nation',title : '国籍',	width : 100, sortable : false},
		  {field : 'pidTypeStr',title : '身份证件类别',	width : 150,sortable : false},
		  {field : 'pidNumber',title : '身份证件号码',	width : 150,sortable : false},
		  {field : 'createTimeStr',title : '报名时间',	width : 150,sortable : false},
		  {field : 'downloadTimeStr',title : '下载报名表时间',	width : 150,sortable : false},
		  {field : 'checkinTimeStr',title : '签到时间',	width : 150,sortable : false},
		  {field : 'recheckinStr',title : '重复签到',	width : 100,sortable : false},
		  {field : 'barcode',title : '条形码', width : 100, sortable : false}
		 ] ],
		fitColumns: false,
		pagination:true,
		rownumbers:false,
		view: detailview,
		detailFormatter:function(index, row){
			return '<div class="ddv" style="padding:5px 0"></div>';
		},
		onExpandRow: function(index,row){
			var ddv = $(this).datagrid('getRowDetail',index).find('div.ddv');
			ddv.panel({
				height:80,
				border:false,
				cache:false,
				href:'../rest/application/detail?id='+row.id,
				onLoad:function(){
					$('#applicationTable').datagrid('fixDetailRowHeight',index);
				}
			});
			$('#applicationTable').datagrid('fixDetailRowHeight',index);
		},
		onLoadSuccess : function(data) {
			$(this).datagrid('unselectAll');
			$('#resetquerypasswordbtn').linkbutton('disable');
			$('#deleteapplicationbtn').linkbutton('disable');
			$('#denyapplicationbtn').linkbutton('disable');
			$('#resetapplicationbtn').linkbutton('disable');
			$('#downloadapplicationbtn').linkbutton('disable');
		},
		onSelect : function(rowIndex, rowData) {
			$('#resetquerypasswordbtn').linkbutton('enable');
			$('#downloadapplicationbtn').linkbutton('enable');
			$('#deleteapplicationbtn').linkbutton('enable');
			if(rowData.status == 1) {
				$('#denyapplicationbtn').linkbutton('enable');
			} else {
				$('#denyapplicationbtn').linkbutton('disable');
			}
			if(rowData.status == 10) {
				$('#resetapplicationbtn').linkbutton('enable');
			} else {
				$('#resetapplicationbtn').linkbutton('disable');
			}
		}
	});
	$('#resetquerypasswordbtn').linkbutton('disable');
	$('#deleteapplicationbtn').linkbutton('disable');
	$('#denyapplicationbtn').linkbutton('disable');
	$('#resetapplicationbtn').linkbutton('disable');
	$('#downloadapplicationbtn').linkbutton('disable');
	
	//初始化分页信息
	var pageInfo = $('#applicationTable').datagrid('getPager');
	if (pageInfo){
		$(pageInfo).pagination({
			onBeforeRefresh:function(pageNumber, pageSize){
				reloadApplicationTable(pageNumber);
				return false;
			},
			onSelectPage : function(pageNumber, pageSize) {
				pageInfo.pagination('options').pageNumber = pageNumber;
				pageInfo.pagination('options').pageSize = pageSize;
				$('#applicationTable').datagrid('options').pageNumber = pageNumber;
				$('#applicationTable').datagrid('options').pageSize = pageSize;
				reloadApplicationTable(pageNumber);
			}
		});
	}
}

function deleteApplication() {
	var node = $('#applicationTable').datagrid('getSelected');
	if(node){
		$.ajax({
			url: '../rest/application/delete/' + node.id,
			headers: { 
		        'Accept': 'application/json',
		        'Content-Type': 'application/json' 
		    },
			'dataType': 'json',
			type: 'GET',
			timeout: gAjaxTimeout,//超时时间设定
			data: ({
			}),//参数设置
			error: function(xhr, textStatus, thrownError){
				if(xhr.readyState != 0 && xhr.readyState != 1) {
					$.messager.alert('错误',"删除报名表失败， 错误号:  " + xhr.status + ", 错误信息: " + textStatus,'error');
				}
				else {
					$.messager.alert('错误',"删除报名表失败，错误信息:  " + textStatus,'error');
				}
			},
			success: function(response, textStatus, xhr) {
				if(xhr.status == 200) {
					if(response.result == "ok") {
						var index = $('#applicationTable').datagrid('getRowIndex', node);
						$('#applicationTable').datagrid('deleteRow', index);
						$.messager.alert('提示',"删除报名表成功!",'info');
					}
					else {
						$.messager.alert('错误',response.result,'error');
					}
				} else {
					$.messager.alert('错误',"删除报名表失败，错误号: " + xhr.status,'error');
				}
			}
		});
	}
}

function showDenyDlg() {
	var node = $('#applicationTable').datagrid('getSelected');
	if(node){
		$('#reasondlgappid').val(node.id);
		$('#reasondlg').dialog('open');
	}
}

function onDlgDenyApplication() {
	var appid = $('#reasondlgappid').val();
	var reason = $.trim($('#reasondlgreason').val());
	if(!reason || reason=="") {
		alert("请输入拒绝理由");
		return;
	}
	
	$.ajax({
		url: '../rest/application/deny',
		headers: { 
	        'Accept': 'application/json',
	        'Content-Type': 'application/json' 
	    },
		'dataType': 'json',
		type: 'POST',
		timeout: gAjaxTimeout,//超时时间设定
		data: JSON.stringify({
			"id" : appid,
			"reason" : reason
		}),//参数设置
		error: function(xhr, textStatus, thrownError){
			if(xhr.readyState != 0 && xhr.readyState != 1) {
				$.messager.alert('错误',"拒绝报名失败， 错误号:  " + xhr.status + ", 错误信息: " + textStatus,'error');
			}
			else {
				$.messager.alert('错误',"拒绝报名失败，错误信息:  " + textStatus,'error');
			}
		},
		success: function(response, textStatus, xhr) {
			if(xhr.status == 200) {
				if(response.result == "ok") {
					var node = $('#applicationTable').datagrid('getSelected');
					var index = $('#applicationTable').datagrid('getRowIndex', node);
					$('#applicationTable').datagrid('updateRow', {'index' : index, 'row' : response.data});
					$('#reasondlg').dialog('close');
					$('#denyapplicationbtn').linkbutton('disable');
					$('#resetapplicationbtn').linkbutton('enable');
					$.messager.alert('提示',"拒绝报名成功!",'info');
				}
				else {
					$.messager.alert('错误',response.result,'error');
				}
			} else {
				$.messager.alert('错误',"拒绝报名失败，错误号: " + xhr.status,'error');
			}
		}
	});
}

function resetApplication() {
	var node = $('#applicationTable').datagrid('getSelected');
	if(node){
		$.ajax({
			url: '../rest/application/reset/' + node.id,
			headers: { 
		        'Accept': 'application/json',
		        'Content-Type': 'application/json' 
		    },
			'dataType': 'json',
			type: 'GET',
			timeout: gAjaxTimeout,//超时时间设定
			data: JSON.stringify({
			}),//参数设置
			error: function(xhr, textStatus, thrownError){
				if(xhr.readyState != 0 && xhr.readyState != 1) {
					$.messager.alert('错误',"恢复报名失败， 错误号:  " + xhr.status + ", 错误信息: " + textStatus,'error');
				}
				else {
					$.messager.alert('错误',"恢复报名失败，错误信息:  " + textStatus,'error');
				}
			},
			success: function(response, textStatus, xhr) {
				if(xhr.status == 200) {
					if(response.result == "ok") {
						var node = $('#applicationTable').datagrid('getSelected');
						var index = $('#applicationTable').datagrid('getRowIndex', node);
						$('#applicationTable').datagrid('updateRow', {'index' : index, 'row' : response.data});
						$('#denyapplicationbtn').linkbutton('enable');
						$('#resetapplicationbtn').linkbutton('disable');
						$.messager.alert('提示',"恢复报名成功!",'info');
					}
					else {
						$.messager.alert('错误',response.result,'error');
					}
				} else {
					$.messager.alert('错误',"恢复报名失败，错误号: " + xhr.status,'error');
				}
			}
		});
	}
}

function reloadApplicationTable(pageNumber) {
	$('#applicationTable').datagrid('loading');
	
	var applicationid = $.trim($('#applicationid').val());
	if(!applicationid || applicationid == "")
		applicationid = "0";
	else {
		if(!isInteger(applicationid)) {
			$('#applicationTable').datagrid('loaded');
			$.messager.alert('提示','请输入正确的报名编号(整数)!','info');
			return;
		}
	}
	var pidnumber = $.trim($('#pidnumber').val());
	var name = $.trim($('#name').val());
	var grade = $.trim($('#grade').combobox('getValue'));
	var gender = $.trim($('#gender').combobox('getValue'));
	var nation = $.trim($('#nation').val());
	var status = $.trim($('#status').combobox('getValue'));
	var sortname = $.trim($('#sortname').combobox('getValue'));
	var sortorder = $.trim($('#sortorder').combobox('getValue'));
	var blur = $("#blur").is(":checked");
	var pageSize = $('#applicationTable').datagrid('options').pageSize;
	$('#applicationTable').datagrid('getPager').pagination('options').pageNumber = pageNumber;
	
	var paramData = JSON.stringify({
		'applicationId': applicationid,
		'pidnumber': pidnumber,
		'grade': grade,
		'name': name,
		'gender': gender,
		'nation': nation,
		'status': status,
		'blur': blur,
		'pageNumber' : pageNumber,
		'pageSize' : pageSize,
		'sortName' : sortname,
		'sortOrder' : sortorder
	});
	
	$.ajax({
		url: '../rest/application/find',
		headers: { 
	        'Accept': 'application/json',
	        'Content-Type': 'application/json' 
	    },
		'dataType': 'json',
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
			$('#applicationTable').datagrid('loaded');
			if(xhr.readyState != 0 && xhr.readyState != 1) {
				$.messager.alert('错误',"获取报名表失败， 错误号:  " + xhr.status + ", 错误信息: " + textStatus,'error');
			}
			else {
				$.messager.alert('错误',"获取报名表失败，错误信息:  " + textStatus,'error');
			}
		},
		success: function(response, textStatus, xhr) {
			$('#applicationTable').datagrid('loaded');
			if(xhr.status == 200) {
				if(response.result == "ok") {
					if(response.data != null) {
						$('#applicationTable').datagrid('loadData', response.data);
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
	$('#applicationTable').datagrid('unselectAll');
	$('#applicationTable').datagrid('loadData',{total:0,rows:[]});
}

function searchApplication() {
	reloadApplicationTable(1);
}

function exportApplication() {
	$('#downloadframe').attr({
		src: "../rest/application/downloadall"
	});
}

function resetQueryPassword() {
	var node = $('#applicationTable').datagrid('getSelected');
	if(node){
		$.ajax({
			url: '../rest/application/resetquerypassword/' + node.id,
			headers: { 
		        'Accept': 'application/json',
		        'Content-Type': 'application/json' 
		    },
			'dataType': 'json',
			type: 'GET',
			timeout: gAjaxTimeout,//超时时间设定
			data: JSON.stringify({
			}),//参数设置
			error: function(xhr, textStatus, thrownError){
				if(xhr.readyState != 0 && xhr.readyState != 1) {
					$.messager.alert('错误',"重置查询密码失败， 错误号:  " + xhr.status + ", 错误信息: " + textStatus,'error');
				}
				else {
					$.messager.alert('错误',"重置查询密码失败，错误信息:  " + textStatus,'error');
				}
			},
			success: function(response, textStatus, xhr) {
				if(xhr.status == 200) {
					if(response.result == "ok") {
						$.messager.alert('提示',"重置查询密码成功, 新密码: <b>" + response.data + "</b>",'info');
					}
					else {
						$.messager.alert('错误',response.result,'error');
					}
				} else {
					$.messager.alert('错误',"重置查询密码失败，错误号: " + xhr.status,'error');
				}
			}
		});
	}
}


function downloadApplication() {
	var node = $('#applicationTable').datagrid('getSelected');
	if(node){
		var file_path = '../rest/application/downloadapp/' + node.id;
		var a = document.createElement('A');
		a.href = file_path;
		a.download = file_path.substr(file_path.lastIndexOf('/') + 1);
		document.body.appendChild(a);
		a.click();
		document.body.removeChild(a);
	}
}
