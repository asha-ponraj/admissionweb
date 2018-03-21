$(function() {
	$('#menu_index').removeClass('item');
	$('#menu_index').addClass('focusitem');
	
});

function loadNewsList() {
	
	var paramData = JSON.stringify({
		'pageNumber' : 1,
		'pageSize' : 0
	});
	
	$.ajax({
		url: 'rest/news/find',
		type: 'POST',
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
		data: paramData,//参数设置
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
					if(response.data != null && response.data.rows != null) {
						var txt = "";
						for(var ip=0; ip<response.data.rows.length; ip++) {
							txt += "<li><a href='newsdetail.jsp?id=" + response.data.rows[ip].id + "'>"
								+ response.data.rows[ip].title + "</a></li>";
						}
						
						$('#newslist').html(txt);
					}
				}
				else {
					$.messager.alert('错误',"获取新闻失败，错误信息: " + response.result,'error');
				}
			} else {
				$.messager.alert('错误',"获取新闻失败，错误号: " + xhr.status,'error');
			}
		}
	});
}