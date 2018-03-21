function setSelectedNotifyTip() {
	var selectednotifytip = $.trim($('#selectednotifytip').val());
	if(!selectednotifytip || selectednotifytip == "") {
		alert("是否入围提示信息不能为空");
		return;
	}
	
	$.ajax({
		url: '../rest/application/setselectednotifytip',
		type: 'POST',
		timeout: gAjaxTimeout,//超时时间设定
		data: ({
			'selectedNotifyTip': selectednotifytip
		}),//参数设置
		error: function(xhr, textStatus, thrownError){
			if(xhr.readyState != 0 && xhr.readyState != 1) {
	     		alert("设置入围提示信息失败， 错误号:  " + xhr.status + ", 错误信息: " + textStatus);
			}
			else {
			 	alert("设置入围提示信息失败，错误信息:  " + textStatus);
			}
		},
		success: function(response, textStatus, xhr) {
			if(xhr.status == 200) {
				if(response.result == "ok") {
					alert("设置入围提示信息成功");
				}
				else {
					alert(response.result);
				}
			} else {
				alert("设置入围提示信息失败，错误号: " + xhr.status);
			}
		}
	});
}

function selectedNotifyApplication() {
	var applicationid = $.trim($('#applicationid').val());
	if(applicationid == "" || !isInteger(applicationid)) {
		alert("请输入正确的报名号");
		return;
	}
	
	var selectednotify = $.trim($('#selectednotify').val());
	if(!checkMaxlength('#selectednotify', selectednotify, 255, "入围通知内容", true))
		return;
	
	$.ajax({
		url: '../rest/application/selectednotify',
		type: 'POST',
		timeout: gAjaxTimeout,//超时时间设定
		data: ({
			'applicationId': applicationid,
			'selectedNotify': selectednotify
		}),//参数设置
		error: function(xhr, textStatus, thrownError){
			if(xhr.readyState != 0 && xhr.readyState != 1) {
	     		alert("发放入围通知失败， 错误号:  " + xhr.status + ", 错误信息: " + textStatus);
			}
			else {
			 	alert("发放入围通知失败，错误信息:  " + textStatus);
			}
		},
		success: function(response, textStatus, xhr) {
			if(xhr.status == 200) {
				if(response.result == "ok") {
					alert("发放入围通知成功");
				}
				else {
					alert(response.result);
				}
			} else {
				alert("发放入围通知失败，错误号: " + xhr.status);
			}
		}
	});
}