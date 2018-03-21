$(function(){
	$('#menu_viewapplication').removeClass('item');
	$('#menu_viewapplication').addClass('focusitem');
	$('#resultpanel').hide();
});

function createApplication() {
	location = "application.jsp";
}

function searchApplication() {
	var username = $.trim($('#queryusername').val());
	if(!checkMaxlength('#queryusername', username, 32, "用户名", true))
		return;
	
	var password = $.trim($('#querypassword').val());
	if(!checkMaxlength('#querypassword', password, 64, "密码", true))
		return;
	
	$.ajax({
		url: 'rest/application/get',
		type: 'GET',
		timeout: gAjaxTimeout,//超时时间设定
		data: ({
			'username': username,
			'password': password
		}),//参数设置
		error: function(xhr, textStatus, thrownError){
			if(xhr.readyState != 0 && xhr.readyState != 1) {
	     		alert("获取报名信息失败， 错误号:  " + xhr.status + ", 错误信息: " + textStatus);
			}
			else {
			 	alert("获取报名信息失败，错误信息:  " + textStatus);
			}
		},
		success: function(response, textStatus, xhr) {
			if(xhr.status == 200) {
				if(response.result == "ok") {
					if(response.data != null) {
						displayApplication(response.data);
					}
				}
				else {
					alert("获取报名信息失败: " + response.result);
				}
			} else {
				alert("获取报名信息失败，错误号: " + xhr.status);
			}
		}
	});
}

function displayDownload() {
	$('#download').html("( <a href='rest/application/download'><span style='color:red; font-size: 16px;'><b>点击下载报名表</b></span></a> )");
}
function hideDownload(content) {
	$('#download').html(content);
}

function displayApplication(app) {
	$('#resultpanel').show();
	
	var rowcount = $('#resulttable tr').length;
	for(var ip=rowcount-1; ip>0; ip--) {
		$('#resulttable tr:eq(' + ip + ')').remove();
	}
	
	$('#number').html(app.id);
	$('#name').html(app.name);
	$('#gender').html(app.gender);
	$('#birthday').html(app.birthdayStr);
	$('#nation').html(app.nation);
	$('#idnumber').html(app.pidNumber);
	
	if(app.status >= 1) {
		var row="<tr><td>"+app.createTimeStr+"</td><td>报名已提交</td></tr>";
		$('#resulttable').append(row);
	}
	if(app.status == 10) {
		var row="<tr><td>"+app.notifyTimeStr+"</td><td>报名被拒绝, 拒绝理由: "+app.notify+"</td></tr>";
		$('#resulttable').append(row);
	} else {
		if(app.status >= 2) {
			var row="<tr><td>"+app.acceptTimeStr+"</td><td>学校已受理</td></tr>";
			$('#resulttable').append(row);
		}
		if(app.status >= 3) {
			var row="<tr><td>"+app.notifyTimeStr+"</td><td>面谈通知已发放，内容如下：<hr/>"
				+ app.notify + "<hr />"
				+ "<a id='downloadbtn' href='rest/application/download'>点这里下载报名表</a><br/>&nbsp;请使用Word打开报名表并打印，不要使用其他工具(如: WPS，写字板)</td></tr>";
			$('#resulttable').append(row);
			$('#downloadbtn').linkbutton({
				iconCls: 'icon-save'
			});
		}
		if(app.status >= 4) {
			var row="<tr><td>"+app.downloadTimeStr+"</td><td>报名表已下载</td></tr>";
			$('#resulttable').append(row);
		}
		if(app.status >=5) {
			var row="<tr><td>"+app.checkinTimeStr+"</td><td>面谈活动已签到</td></tr>";
			$('#resulttable').append(row);
		}
		
		if(app.status == 1) {
			var row="<tr><td colspan='2' align='left' style='color:red;'>等待学校受理</td></tr>";
			$('#resulttable').append(row);
		}
		if(app.status == 2) {
			var row="<tr><td colspan='2' align='left' style='color:red;'>等待学校发放面谈活动通知</td></tr>";
			$('#resulttable').append(row);
		}
		if(app.status == 3) {
			var row="<tr><td colspan='2' align='left' style='color:red;'>等待家长下载报名表</td></tr>";
			$('#resulttable').append(row);
		}
		if(app.status == 4) {
			var row="<tr><td colspan='2' align='left' style='color:red;'>等待家长和孩子参加面谈活动</td></tr>";
			$('#resulttable').append(row);
		}
		if(app.status == 5) {
			var row="<tr><td colspan='2' align='left' style='color:red;'>报名过程已完成</td></tr>";
			$('#resulttable').append(row);
		}
	}
}
