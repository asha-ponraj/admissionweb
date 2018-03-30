$(function(){
	$('#menu_application').removeClass('item');
	$('#menu_application').addClass('focusitem');
	
	$('#grade').combobox({
		required: true,
		editable: false
	});
		
	$('#candidatetype').combobox({
		required: true,
		editable: false
	});
	
	$('#gender').combobox({
		required: true,
		editable: false
	});
	
	$('#onlychild').combobox({
		required: true,
		editable: false
	});
	
	$('#kindergartened').combobox({
		required: true,
		editable: false,
		onSelect: function(rec) {
			updateNurseryComponents();
		}
	});
	
	$('#pidtype').combobox({
		required: true,
		editable: false
	});
	
	$('#hasbirthcert').combobox({
		required: true,
		editable: false
	});
	
	$('#allergic').combobox({
		required: true,
		editable: false
	});
	
	$('#immunitycert').combobox({
		required: true,
		editable: false
	});
	
	$('#junlie').combobox({
		required: true,
		editable: false
	});
	
	$('#budui').combobox({
		required: true,
		editable: false
	});
	
	$('#youfu').combobox({
		required: true,
		editable: false
	});
	
	$('#dibao').combobox({
		required: true,
		editable: false
	});
	
	$('#suiqian').combobox({
		required: true,
		editable: false
	});
	
	$('#liushou').combobox({
		required: true,
		editable: false
	});
	
	$('#junlie').combobox('setValue', '否');
	$('#budui').combobox('setValue', '否');
	$('#youfu').combobox('setValue', '否');
	$('#dibao').combobox('setValue', '否');
	$('#suiqian').combobox('setValue', '否');
	$('#liushou').combobox('setValue', '否');
	
	$('#hknature').combobox({
		required: true,
		editable: false
	});
	
	$('#hktype').combobox({
		required: true,
		editable: false
	});
	
	$('#propertynature').combobox({
		required: true,
		editable: false,
		onSelect: function(rec) {
			updatePropertyComponents();
		}
	});
	
	$('#propertytype').combobox({
		required: true,
		editable: false
	});
	
	$('#birthday').datebox({
		required: true,
		editable: false
	});
	
	$('#hkregdate').datebox({
		required: false,
		editable: false
	});
	
	$('#propertyregdate').datebox({
		required: false,
		editable: false
	});
	
	$('#resultdlg').dialog({
		title: '信息提示',
		width: 400,
		height: 150,
		closable: false,
		closed: true,
		modal: true,
		buttons: [{
			iconCls:'icon-ok',
			text: '关闭',
			handler: function() {
				$('#resultdlg').dialog('close');
				location = "viewapplication.jsp";
			}
		}]
	});

	$('#resultdlg').dialog('dialog').attr('tabIndex','-1').bind('keydown',function(e){
		if (e.keyCode == 27){
			$('#resultdlg').dialog('close');
		}
	});
	
	updateNurseryComponents();
	updatePropertyComponents();
});

function updateNurseryComponents() {
	var kindergartened = $.trim($('#kindergartened').combobox('getValue'));
	
	if(kindergartened && kindergartened == "true") {
		$('#nurserylabel').show();
		$('#nursery').show();
	} else {
		$('#nurserylabel').hide();
		$('#nursery').hide();
	}
}

function updatePropertyComponents() {
	var propertynature = $.trim($('#propertynature').combobox('getValue'));
	
	if(propertynature && propertynature == "1") {
		$('#propertylabel1').html("产证编号");
		$('#propertylabel1').show();
		$('#propertynumber').show();
		$('#propertylabel2').show();
		$('#propertyregdatespan').show();
		$('#propertyother').hide();
	} else if(propertynature && propertynature == "5") {
		$('#propertylabel1').html("其他");
		$('#propertylabel1').show();
		$('#propertynumber').hide();
		$('#propertylabel2').hide();
		$('#propertyregdatespan').hide();
		$('#propertyother').show();
	} else {
		$('#propertylabel1').hide();
		$('#propertynumber').hide();
		$('#propertylabel2').hide();
		$('#propertyregdatespan').hide();
		$('#propertyother').hide();
	}
}

function viewApplication() {
	location="viewapplication.jsp";
}

function submitApplication() {
	var username = $.trim($('#username').val());
	if(username == "") {
		$('#username').focus();
		alert("请输入用户名");
		return;
	}
	var password = $('#password').val();
	var confirmpassword = $('#confirmpassword').val();
	if(password == "") {
		$('#password').focus();
		alert("请输入查询密码");
		return;
	}
	if(password != confirmpassword) {
		$('#confirmpassword').focus();
		alert("查询密码和确认密码不一致");
		return;
	}
	var grade = $.trim($('#grade').combobox('getValue'));
	if(!grade || grade == "") {
		alert("请选择申请班级");
		return;
	}
	var candidatetype = $.trim($('#candidatetype').combobox('getValue'));
	if(!candidatetype || candidatetype == "") {
		alert("请选择招生对象属性");
		return;
	}
	var name = $.trim($('#name').val());
	if(!checkMaxlength('#name', name, 32, "姓名", true))
		return;
	var formername = $.trim($('#formername').val());
	if(!checkMaxlength('#formername', formername, 32, "曾用名", false))
		return;
	var gender = $.trim($('#gender').combobox('getValue'));
	if(!checkMaxlength('#gender', gender, 8, "性别", true))
		return;
	var jiguan = $.trim($('#jiguan').val());
	if(!checkMaxlength('#jiguan', jiguan, 32, "籍贯", false))
		return;
	var ethnicity = $.trim($('#ethnicity').val());
	if(!checkMaxlength('#ethnicity', ethnicity, 32, "民族", false))
		return;
	var nation = $.trim($('#nation').val());
	if(!checkMaxlength('#nation', nation, 32, "国别", true))
		return;
	var birthday = $.trim($('#birthday').datebox('getValue'));
	if(!checkMaxlength('#birthday', birthday, 12, "出生日期", true))
		return;
	var onlychild = $.trim($('#onlychild').combobox('getValue'));
	if(!onlychild || onlychild == "") {
		alert("请选择是否为独生子女");
		return;
	}
	var hasbirthcert = $.trim($('#hasbirthcert').combobox('getValue'));
	if(!hasbirthcert || hasbirthcert == "") {
		alert("请选择是否为有出生证");
		return;
	}
	var birthplace = $.trim($('#birthplace').val());
	if(!checkMaxlength('#birthplace', birthplace, 32, "出生地", true))
		return;
	
	var kindergartened = $('#kindergartened').combobox('getValue');
	if(!kindergartened || kindergartened == "") {
		alert("请选择是否上过幼儿园");
		return;
	}
	var nursery = $.trim($('#nursery').val());
	if(kindergartened=="true") {
		if(!checkMaxlength('#nursery', nursery, 64, "幼儿园名称", true))
			return;
	}
	
	var pidtype = $.trim($('#pidtype').combobox('getValue'));
	if(!pidtype || pidtype == "") {
		alert("请选择身份证件类别");
		return;
	}
	
	var pidnumber = $.trim($('#pidnumber').val());
	if(!checkMaxlength('#pidnumber', pidnumber, 20, "身份证件编号", true))
		return;
	
	var health = $.trim($('#health').val());
	if(!checkMaxlength('#health', health, 32, "健康状况", true))
		return;
	
	var allergic = $('#allergic').combobox('getValue');
	if(!allergic || allergic == "") {
		alert("请选择有无过敏史");
		return;
	}
	
	var immunitycert = $('#immunitycert').combobox('getValue');
	if(!immunitycert || immunitycert == "") {
		alert("请选择有无计划免疫证");
		return;
	}
	
	var specificdisease = $.trim($('#specificdisease').val());
	if(!checkMaxlength('#specificdisease', specificdisease, 32, "特殊病史", false))
		return;
	
	var hknature = $('#hknature').combobox('getValue');
	if(!hknature || hknature=="") {
		alert("请选择户口性质");
		return;
	}
	
	var hktype = $('#hktype').combobox('getValue');
	if(!hktype || hktype=="") {
		alert("请选择户籍类别");
		return;
	}
	
	var hkregdate = $.trim($('#hkregdate').datebox('getValue'));
	if(!checkMaxlength('#hkregdate', hkregdate, 12, "户籍登记日期", false))
		return;
	if(!hkregdate || hkregdate=="")
		hkregdate = "1800-01-01";
	
	var propertynature = $('#propertynature').combobox('getValue');
	if(!propertynature || propertynature=="") {
		alert("请选择住房性质");
		return;
	}
	
	var propertytype = $('#propertytype').combobox('getValue');
	if(!propertytype || propertytype=="") {
		alert("请选择产证类别");
		return;
	}
	
	var junlie = $('#junlie').combobox('getValue');
	if(!junlie || junlie=="") {
		alert("请选择是否军烈子女");
		return;
	}
	
	var budui = $('#budui').combobox('getValue');
	if(!budui || budui=="") {
		alert("请选择是否部队子女");
		return;
	}
	
	var youfu = $('#youfu').combobox('getValue');
	if(!youfu || youfu=="") {
		alert("请选择是否优抚子女");
		return;
	}
	
	var dibao = $('#dibao').combobox('getValue');
	if(!dibao || dibao=="") {
		alert("请选择是否低保");
		return;
	}
	
	var suiqian = $('#suiqian').combobox('getValue');
	if(!suiqian || suiqian=="") {
		alert("请选择是否进程务工人员随迁子女");
		return;
	}
	
	var liushou = $('#liushou').combobox('getValue');
	if(!liushou || liushou=="") {
		alert("请选择是否留守儿童");
		return;
	}

	var propertynumber = $.trim($('#propertynumber').val());
	if(!checkMaxlength('#propertynumber', propertynumber, 64, "产证编号", false))
		return;
	
	var propertyregdate = $.trim($('#propertyregdate').datebox('getValue'));
	if(!checkMaxlength('#propertyregdate', propertyregdate, 12, "产证登记日期", false))
		return;
	if(!propertyregdate || propertyregdate=="")
		propertyregdate = "1800-01-01";
	
	var hkaddress = $.trim($('#hkaddress').val());
	if(!checkMaxlength('#hkaddress', hkaddress, 128, "户籍地址", false))
		return;
	var hktown = $.trim($('#hktown').val());
	if(!checkMaxlength('#hktown', hktown, 64, "户籍地址所属镇", false))
		return;
	var hkresidentcouncil = $.trim($('#hkresidentcouncil').val());
	if(!checkMaxlength('#hkresidentcouncil', hkresidentcouncil, 32, "户籍地址所属居委会", false))
		return;
	var hkpostcode = $.trim($('#hkpostcode').val());
	if(!checkMaxlength('#hkpostcode', hkpostcode, 8, "户籍地址邮编", false))
		return;
	
	var praddress = $.trim($('#praddress').val());
	if(!checkMaxlength('#praddress', praddress, 128, "产权地址", false))
		return;
	var prtown = $.trim($('#prtown').val());
	if(!checkMaxlength('#prtown', prtown, 64, "产权地址所属镇", false))
		return;
	var prresidentcouncil = $.trim($('#prresidentcouncil').val());
	if(!checkMaxlength('#prresidentcouncil', prresidentcouncil, 32, "产权地址所属居委会", false))
		return;
	var prpostcode = $.trim($('#prpostcode').val());
	if(!checkMaxlength('#prpostcode', prpostcode, 8, "产权地址邮编", false))
		return;
	
	var lvaddress = $.trim($('#lvaddress').val());
	if(!checkMaxlength('#lvaddress', lvaddress, 128, "现住地址", true))
		return;
	var lvtown = $.trim($('#lvtown').val());
	if(!checkMaxlength('#lvtown', lvtown, 64, "现住地址所属镇", false))
		return;
	var lvresidentcouncil = $.trim($('#lvresidentcouncil').val());
	if(!checkMaxlength('#lvresidentcouncil', lvresidentcouncil, 32, "现住地址所属居委会", false))
		return;
	var lvpostcode = $.trim($('#lvpostcode').val());
	if(!checkMaxlength('#lvpostcode', lvpostcode, 8, "现住地址邮编", false))
		return;

	var name1 = $.trim($('#name1').val());
	if(!checkMaxlength('#name1', name1, 32, "父亲的姓名", false))
		return;
	var idnumber1 = $.trim($('#idnumber1').val());
	if(!checkMaxlength('#idnumber1', idnumber1, 32, "父亲的身份证", false))
		return;
	var company1 = $.trim($('#company1').val());
	if(!checkMaxlength('#company1', company1, 64, "父亲的工作单位", false))
		return;
	var residentpermit1 = $.trim($('#residentpermit1').val());
	if(!checkMaxlength('#residentpermit1', residentpermit1, 32, "父亲的居住证类别及编号", false))
		return;
	var phone1 = $.trim($('#phone1').val());
	if(!checkMaxlength('#phone1', phone1, 64, "父亲的联系电话", false))
		return;
	var mobile1 = $.trim($('#mobile1').val());
	if(!checkMaxlength('#mobile1', mobile1, 64, "父亲的手机号码", false))
		return;
	
	var name2 = $.trim($('#name2').val());
	if(!checkMaxlength('#name2', name2, 32, "母亲的姓名", false))
		return;
	var idnumber2 = $.trim($('#idnumber2').val());
	if(!checkMaxlength('#idnumber2', idnumber2, 32, "母亲的身份证", false))
		return;
	var company2 = $.trim($('#company2').val());
	if(!checkMaxlength('#company2', company2, 64, "母亲的工作单位", false))
		return;
	var residentpermit2 = $.trim($('#residentpermit2').val());
	if(!checkMaxlength('#residentpermit2', residentpermit2, 32, "母亲的居住证类别及编号", false))
		return;
	var phone2 = $.trim($('#phone2').val());
	if(!checkMaxlength('#phone2', phone2, 64, "母亲的联系电话", false))
		return;
	var mobile2 = $.trim($('#mobile2').val());
	if(!checkMaxlength('#mobile2', mobile2, 64, "母亲的手机号码", false))
		return;
	
	
	if((!phone1 || phone1 == "") && (!phone2 || phone2 == "") && (!mobile1 || mobile1 == "")
			&& (!mobile2 || mobile2 == "")) {
		alert("请至少填写一个家长的联系电话或者手机号码");
		return;
	}
	
	var remark = $.trim($('#remark').val());
	if(!checkMaxlength('#remark', remark, 400, "家长备注说明", false))
		return;
	
	var applicationData = JSON.stringify({
		"username" : username,
		"password": password,
		"grade" : grade,
		"candidateType": candidatetype,
		"name": name,
		"formerName" : formername,
		"gender": gender,
		"jiguan": jiguan,
		"ethnicity" : ethnicity,
		"nation": nation,
		"birthday": birthday,
		"hasBirthCert" : hasbirthcert,
		"birthPlace": birthplace,
		"onlyChild": onlychild,
		"kindergartened": kindergartened,
		"nursery": nursery,
		"pidType": pidtype,
		"pidNumber": pidnumber,
		"health": health,
		"allergic": allergic,
		"immunityCert": immunitycert,
		"junlie": junlie,
		"budui": budui,
		"youfu": youfu,
		"dibao": dibao,
		"suiqian": suiqian,
		"liushou": liushou,
		"specificDisease": specificdisease,
		"hkNature" : hknature,
		"hkType" : hktype,
		"hkRegDate" : hkregdate,
		"propertyNature": propertynature,
		"propertyType": propertytype,
		"propertyNumber": propertynumber,
		"propertyRegDate": propertyregdate,
		"remark" : remark,
		"addresses": [{
			"type": 1,
			"content": hkaddress,
			"town": hktown,
			"residentCouncil": hkresidentcouncil,
			"postcode": hkpostcode
		},{
			"type": 2,
			"content": praddress,
			"town": prtown,
			"residentCouncil": prresidentcouncil,
			"postcode": prpostcode
		},{
			"type": 3,
			"content": lvaddress,
			"town": lvtown,
			"residentCouncil": lvresidentcouncil,
			"postcode": lvpostcode
		}],
		"members": [{
			"type": 1,
			"name": name1,
			"idNumber": idnumber1,
			"company": company1,
			"residentPermit": residentpermit1,
			"phone": phone1,
			"mobile": mobile1
		},{
			"type": 2,
			"name": name2,
			"idNumber": idnumber2,
			"company": company2,
			"residentPermit": residentpermit2,
			"phone": phone2,
			"mobile": mobile2
		}]
	});
	
	$.messager.defaults.ok = '确定';
	$.messager.defaults.cancel = '取消';
	var tip = '</span><br/><span style="font-size:14px;font-weight:bold;color:red;">请确认输入了完整而正确的信息<br/>一旦提交将不能再次修改<br/>确定要提交报名信息吗?</span>'; 
	$.messager.confirm('温馨提示', tip,
		function(sure) {
			if(sure) {
				$.ajax({
					url: 'rest/application/create',
					headers: { 
				        'Accept': 'application/json',
				        'Content-Type': 'application/json' 
				    },
					type: 'POST',
					dataType: "json",
					contentType: 'application/json;charset=UTF-8',
					beforeSend: function(x) {
			            if (x && x.overrideMimeType) {
			              x.overrideMimeType("application/j-son;charset=UTF-8");
			            }
			            //跨域使用
			            x.setRequestHeader("Accept", "application/json");
			        },
					timeout: gAjaxTimeout,
					data: applicationData,
					error: function(xhr, textStatus, thrownError){
						if(xhr.readyState != 0 && xhr.readyState != 1) {
				     alert("报名登记失败， 错误号:  " + xhr.status + ", 错误信息: " + textStatus);
						}else{
						 	alert("报名登记失败，错误信息:  " + textStatus);
						}
					},
					success: function(response, textStatus, xhr) {
						if(xhr.status == 200) {
							if(response.result == "ok") {
								$('#applicationid').html(response.data.id);
								$('#resultdlg').dialog('open');
							}
							else {
								alert(response.result);
							}
						} else if(xhr.status == 204) {
							$(districtControlId).combobox('loadData', "[]");
						} else {
							alert("报名登记失败，错误号: " + xhr.status);
						}
					}
				});
			}
		}
	);
}
