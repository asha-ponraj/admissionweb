$(function() {
	refreshStatistics();
});

function refreshStatistics() {
	$('#statusbar').html("正在获取统计数据......");
	
	$.ajax({
		url: '../rest/application/getstatistics',
		type: 'GET',
		timeout: gAjaxTimeout,//超时时间设定
		data: ({
		}),//参数设置
		error: function(xhr, textStatus, thrownError){
			$('#statusbar').html("");
			if(xhr.readyState != 0 && xhr.readyState != 1) {
	     		alert("获取统计信息失败, 错误号:  " + xhr.status + ", 错误信息: " + textStatus);
			}
			else {
			 	alert("获取统计信息失败, 错误信息:  " + textStatus);
			}
		},
		success: function(response, textStatus, xhr) {
			$('#statusbar').html("");
			
			if(xhr.status == 200) {
				if(response.result == "ok") {
					$('#so_all').html(response.data.so_all);
					$('#so_submitted').html(response.data.so_submitted);
					$('#so_accepted').html(response.data.so_accepted);
					$('#so_notified').html(response.data.so_notified);
					$('#so_downloaded').html(response.data.so_downloaded);
					$('#so_checkin').html(response.data.so_checkin);
					$('#so_denied').html(response.data.so_denied);
				}
				else {
					alert(response.result);
				}
			} else {
				alert("获取统计信息失败, 错误号: " + xhr.status);
			}
		}
	});
}