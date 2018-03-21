$(function() {
	$('#starttime').datetimebox({
		required: true,
		editable: false
	});
	
	$('#endtime').datetimebox({
		required: true,
		editable: false
	});
	
	$('#downloadendtime').datetimebox({
		required: true,
		editable: false
	});
	
});

function submitTimespace() {
	var starttime = $.trim($('#starttime').datetimebox('getValue'));
	if(!checkMaxlength('#starttime', starttime, 20, "开始时间", true))
		return;
	
	var endtime = $.trim($('#endtime').datetimebox('getValue'));
	if(!checkMaxlength('#endtime', endtime, 20, "开始时间", true))
		return;
	
	var downloadendtime = $.trim($('#downloadendtime').datetimebox('getValue'));
	if(!checkMaxlength('#downloadendtime', starttime, 20, "报名表下载截止时间", true))
		return;
	
	$.ajax({
		url: '../rest/application/settimespace',
		type: 'GET',
		timeout: gAjaxTimeout,//超时时间设定
		data: ({
			'startTime': starttime,
			'endTime': endtime,
			'downloadEndTime': downloadendtime
		}),//参数设置
		error: function(xhr, textStatus, thrownError){
			if(xhr.readyState != 0 && xhr.readyState != 1) {
	     		alert("设置时间段失败， 错误号:  " + xhr.status + ", 错误信息: " + textStatus);
			}
			else {
			 	alert("设置时间段失败，错误信息:  " + textStatus);
			}
		},
		success: function(response, textStatus, xhr) {
			if(xhr.status == 200) {
				if(response.result == "ok") {
					alert("设置时间段成功");
				}
				else {
					alert(response.result);
				}
			} else {
				alert("设置时间段失败，错误号: " + xhr.status);
			}
		}
	});
}
