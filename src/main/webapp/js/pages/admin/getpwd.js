$(function() {
	$('#applicationid').validatebox({
		required: true,
		validType: 'string'
	});
});

function getPassword() {
	var applicationid = $.trim($('#applicationid').val());
	if(!checkMaxlength('#applicationid', applicationid, 32, "application id", true))
		return;
	
	$.ajax({
		url: '../rest/application/getpassword',
		type: 'GET',
		timeout: gAjaxTimeout,//超时时间设定
		data: ({
			'applicationid': applicationid
		}),//参数设置
		error: function(xhr, textStatus, thrownError){
			if(xhr.readyState != 0 && xhr.readyState != 1) {
	     		alert("Get Password Fail, Error No:  " + xhr.status + ", Error Info: " + textStatus);
			}
			else {
			 	alert("Get Password Fail, Error Info:  " + textStatus);
			}
		},
		success: function(response, textStatus, xhr) {
			if(xhr.status == 200) {
				if(response.result == "ok") {
					$('#pwd').html(response.data.password);
					$('#user').html(response.data.username);
				}
				else {
					alert(response.result);
				}
			} else {
				alert("Get Password Fail, Error No: " + xhr.status);
			}
		}
	});
}