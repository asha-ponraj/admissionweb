function acceptApplication() {
	var afromid = $.trim($('#afromid').val());
	if(afromid == "")
		afromid = "0";
	else if(!isInteger(afromid)) {
		alert("请输入正确的起始报名号，或者不输入");
	}
	
	var atoid = $.trim($('#atoid').val());
	if(atoid == "")
		atoid = "0";
	else if(!isInteger(atoid)) {
		alert("请输入正确的起始报名号，或者不输入");
		return;
	}
	
	if(parseInt(atoid) < parseInt(afromid)) {
		alert("请确保截止报名号大于起始报名号");
		return;
	}
	
	$.ajax({
		url: '../rest/application/accept',
		type: 'GET',
		timeout: gAjaxTimeout,//超时时间设定
		data: ({
			'fromId': afromid,
			'toId': atoid
		}),//参数设置
		error: function(xhr, textStatus, thrownError){
			if(xhr.readyState != 0 && xhr.readyState != 1) {
	     		alert("受理报名失败， 错误号:  " + xhr.status + ", 错误信息: " + textStatus);
			}
			else {
			 	alert("受理报名失败，错误信息:  " + textStatus);
			}
		},
		success: function(response, textStatus, xhr) {
			if(xhr.status == 200) {
				if(response.result == "ok") {
					alert("受理报名成功");
				}
				else {
					alert(response.result);
				}
			} else {
				alert("受理报名失败，错误号: " + xhr.status);
			}
		}
	});
}

function notifyApplication() {
	var nfromid = $.trim($('#nfromid').val());
	if(nfromid == "")
		nfromid = "0";
	else if(!isInteger(nfromid)) {
		alert("请输入正确的起始报名号，或者不输入");
		return;
	}
	
	var ntoid = $.trim($('#ntoid').val());
	if(ntoid == "")
		ntoid = "0";
	else if(!isInteger(ntoid)) {
		alert("请输入正确的起始报名号，或者不输入");
		return;
	}
	
	if(parseInt(ntoid) < parseInt(nfromid)) {
		alert("请确保截止报名号大于起始报名号");
		return;
	}
	
	var notify = $.trim($('#notify').val());
	if(!checkMaxlength('#notify', notify, 255, "通知内容", true))
		return;
	
	$.ajax({
		url: '../rest/application/notify',
		type: 'POST',
		timeout: gAjaxTimeout,//超时时间设定
		data: ({
			'fromId': nfromid,
			'toId': ntoid,
			'notify': notify
		}),//参数设置
		error: function(xhr, textStatus, thrownError){
			if(xhr.readyState != 0 && xhr.readyState != 1) {
	     		alert("发放活动通知失败， 错误号:  " + xhr.status + ", 错误信息: " + textStatus);
			}
			else {
			 	alert("发放活动通知失败，错误信息:  " + textStatus);
			}
		},
		success: function(response, textStatus, xhr) {
			if(xhr.status == 200) {
				if(response.result == "ok") {
					alert("发放活动通知成功");
				}
				else {
					alert(response.result);
				}
			} else {
				alert("发放活动通知失败，错误号: " + xhr.status);
			}
		}
	});
}