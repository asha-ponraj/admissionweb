$(function() {
	
});

function reloadcfg() {
	$.ajax({
		url: '../rest/system/reloadcfg',
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
	     		alert("重新加载配置失败， 错误号:  " + xhr.status + ", 错误信息: " + textStatus);
			}
			else {
			 	alert("重新加载配置失败，错误信息:  " + textStatus);
			}
		},
		success: function(response, textStatus, xhr) {
			if(xhr.status == 200) {
				if(response.result == "ok") {
					alert("重新加载配置成功");
				}
				else {
					alert(response.result);
				}
			} else {
				alert("重新加载配置失败，错误号: " + xhr.status);
			}
		}
	});
}

function resetadmission() {
	$.messager.defaults.ok = '确定';
	$.messager.defaults.cancel = '取消';
	var tip = '重置报名数据会删除所有报名记录，确定要重置吗？'; 
	$.messager.confirm('确认提示', tip,
		function(sure) {
			if(sure) {
				clearApplications();
			}
	});
}

function clearApplications() {
	$.ajax({
		url: '../rest/system/clearapplications',
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
	     		alert("重置报名数据失败， 错误号:  " + xhr.status + ", 错误信息: " + textStatus);
			}
			else {
			 	alert("重置报名数据失败，错误信息:  " + textStatus);
			}
		},
		success: function(response, textStatus, xhr) {
			if(xhr.status == 200) {
				if(response.result == "ok") {
					alert("重置报名数据成功");
				}
				else {
					alert(response.result);
				}
			} else {
				alert("重置报名数据失败，错误号: " + xhr.status);
			}
		}
	});
}