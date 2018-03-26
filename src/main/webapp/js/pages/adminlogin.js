$(function(){
	$('#username').focus();
	$('#username').click();
});

function login() {
	var username = $('#username').val();
	var password = $('#password').val();
	
	if(username == "") {
		alert("请输入帐号");
		return;
	}
	
	if(password == "") {
		alert("请输入密码");
		return;
	}
	
	$.ajax({
		url: 'rest/system/login',
		headers: { 
	        'Accept': 'application/json',
	        'Content-Type': 'application/json' 
	    },
		type: 'POST',
		'dataType': 'json',
		timeout: gAjaxTimeout,//超时时间设定
		data: JSON.stringify({
			'username': username,
			'password': password
		}),//参数设置
		error: function(xhr, textStatus, thrownError){
			if(xhr.readyState != 0 && xhr.readyState != 1) {
	     		alert("登录失败， 错误号:  " + xhr.status + ", 错误信息: " + textStatus);
			}
			else {
			 	alert("登录失败，错误信息:  " + textStatus);
			}
		},
		success: function(response, textStatus, xhr) {
			if(xhr.status == 200) {
				if(response.result == "ok") {
					location = "admin/index.jsp";
				}
				else {
					alert(response.result);
				}
			} else {
				alert("登录失败，错误号: " + xhr.status);
			}
		}
	});
	
	return false;
}