$(function() {
	$('#originpwd').validatebox({
		required: true,
		validType: 'string'
	});
	
	$('#newpwd').validatebox({
		required: true,
		validType: 'string'
	});
	
	$('#confirmpwd').validatebox({
		required: true,
		validType: 'string'
	});
});

function submitPassword() {
	var originpwd = $.trim($('#originpwd').val());
	if(!checkMaxlength('#originpwd', originpwd, 32, "原密码", true))
		return;
	
	var newpwd = $.trim($('#newpwd').val());
	if(!checkMaxlength('#newpwd', newpwd, 32, "新密码", true))
		return;
	
	var confirmpwd = $.trim($('#confirmpwd').val());
	if(!checkMaxlength('#confirmpwd', confirmpwd, 32, "确认新密码", true))
		return;
	
	if(confirmpwd != newpwd) {
		alert("新密码和确认密码不一样");
		$('#newpwd').val('');
		$('#confirmpwd').val('');
		return;
	}
	
	if(originpwd == newpwd) {
		alert("新密码和原密码一样");
		$('#newpwd').val('');
		$('#confirmpwd').val('');
		return;
	}
	
	$.ajax({
		url: '../rest/system/password',
		type: 'POST',
		timeout: gAjaxTimeout,//超时时间设定
		data: ({
			'originPwd': originpwd,
			'newPwd': newpwd
		}),//参数设置
		error: function(xhr, textStatus, thrownError){
			if(xhr.readyState != 0 && xhr.readyState != 1) {
	     		alert("修改密码失败， 错误号:  " + xhr.status + ", 错误信息: " + textStatus);
			}
			else {
			 	alert("修改密码失败，错误信息:  " + textStatus);
			}
		},
		success: function(response, textStatus, xhr) {
			if(xhr.status == 200) {
				if(response.result == "ok") {
					$('#originpwd').val('');
					$('#newpwd').val('');
					$('#confirmpwd').val('');
					
					alert("修改密码成功");
				}
				else {
					alert(response.result);
				}
			} else {
				alert("修改密码失败，错误号: " + xhr.status);
			}
		}
	});
}