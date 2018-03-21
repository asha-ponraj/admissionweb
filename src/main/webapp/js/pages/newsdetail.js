$(function() {
	$('#menu_index').removeClass('item');
	$('#menu_index').addClass('focusitem');
	
	loadNews();
});

function loadNews() {

	var id = $('#id').val();
	if(!id || id=="" || !isInteger(id))
		return;
	
	$.ajax({
		url: 'rest/news/get/' + id,
		type: 'GET',
		timeout: gAjaxTimeout,//超时时间设定
		dataType: "json",
		contentType: 'application/json;charset=UTF-8',
		beforeSend: function(x) {
            if (x && x.overrideMimeType) {
              x.overrideMimeType("application/j-son;charset=UTF-8");
            }
            //跨域使用
            x.setRequestHeader("Accept", "application/json");
        },
		data: {},//参数设置
		error: function(xhr, textStatus, thrownError){
			if(xhr.readyState != 0 && xhr.readyState != 1) {
				$.messager.alert('错误',"获取新闻失败， 错误号:  " + xhr.status + ", 错误信息: " + textStatus,'error');
			}
			else {
				$.messager.alert('错误',"获取新闻失败，错误信息:  " + textStatus,'error');
			}
		},
		success: function(response, textStatus, xhr) {
			if(xhr.status == 200) {
				if(response.result == "ok") {
					if(response.data != null) {
						$('#title').html(response.data.title);
						$('#createtime').html(response.data.createTimeStr);
						$('#content').html(response.data.content);
					}
				}
				else {
					$.messager.alert('错误',"获取报名表失败，错误信息: " + response.result,'error');
				}
			} else {
				$.messager.alert('错误',"获取报名表失败，错误号: " + xhr.status,'error');
			}
		}
	});
}