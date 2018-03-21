$(function() {
	var settings = {
		    url: "../rest/regionalism/updateRegionalism",
		    method: "POST",
		    allowedTypes:"xls",
		    fileName: "regionalismfile",
		    multiple: false,
		    onSuccess:function(files,data,xhr)
		    {
				if(data.result == "ok") {
					$('#regionalismFile').val('');
					alert("更新行政区域列表成功");
				}
				else {
					alert("更新行政区域列表失败: " + res.result);
				}
		    },
		    onError: function(files,status,errMsg)
		    {       
		    	alert("更新行政区域列表失败，错误信息:  " + status);
		    }
		};
		 
		$("#regfile").uploadFile(settings);
});