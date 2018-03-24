$(function() {
	
});

function reloadcfg() {
	$.ajax({
		url: '../rest/system/reloadcfg',
		type: 'GET',
		timeout: gAjaxTimeout,//超时时间设定
		data: ({
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
