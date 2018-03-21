var gAjaxTimeout = 30000;

/**
 * 用于检测输入字符串长度
 * @param jQueryID   输入框对应的ID
 * @param maxLength  最大允许长度 
 * @param des			描述
 * @param  isRequired 标识是否本内容可以为空
 * @author jxm
 */
function checkMaxlength(jQueryID,content,maxLength,des,isRequired){
	//如果必填项为空
	if(isRequired && (!content || content == "")) {
		$(jQueryID).focus();
		alert("请输入"+des);
		return false;
	}else if(content != ""){
		//得到输入的字节数
		var curLength = content.replace(/[^\x00-\xff]/g, "**").length;
		if(curLength > maxLength){
			var length = parseInt(maxLength / 2);
			alert(des+"长度不能大于"+maxLength+"个字节（"+length+"个汉字或"+maxLength+"个字母）");
			$(jQueryID).focus();
			return false;
		}
		//过滤特殊字符
//		var reg = /\\|\'|\/|\:|\*|\?|\"|\<|\>|\|/;
//		if(content.match(reg)){
//			alert(des+"不能含有 \\  \ '  \/ \: \* \? \" \< \> \|等特殊字符!");
//			return;
//		}
	}
	return true;
}

function isInteger(str) {
	var ex = /^\d+$/;
	return ex.test(str);
}
