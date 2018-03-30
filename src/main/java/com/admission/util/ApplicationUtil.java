package com.admission.util;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.admission.entity.Address;
import com.admission.entity.Application;
import com.admission.entity.FamilyMember;

public class ApplicationUtil {
	public static enum KEYS {
		KEY_APPID("报名号"),
		KEY_CANDIDATETYPE("招生对象属性"),
		KEY_NAME("学生姓名"),
		KEY_GENDER("性别"),
		KEY_NATION("国籍"),
		KEY_PIDTYPE("身份证件类别"),
		KEY_PIDNUMBER("身份证件号码"),
		KEY_BIRTHDAY("出生日期"),
		KEY_KINDERGARTENED("上过幼儿园"),
		KEY_HKADDR("户籍地址"),
		KEY_PROADDR("房产地址"),
		KEY_LVADDR("居住地址"),
		KEY_FATHERNAME("父亲姓名"),
		KEY_FATHERCOMPANY("父亲公司"),
		KEY_FATHERPHONE("父亲电话"),
		KEY_FATHERMOBILE("父亲手机"),
		KEY_MOTHERNAME("母亲姓名"),
		KEY_MOTHERCOMPANY("母亲公司"),
		KEY_MOTHERPHONE("母亲电话"),
		KEY_MOTHERMOBILE("母亲手机"),
		KEY_STATUS("报名状态"),
		KEY_CHECKINSTATUS("签到状态"),
		KEY_BARCODE("条码");
		
		private String name;
		public String getName() {
			return name;
		}
		
		KEYS(String name) {
			this.name = name;
		}
	}
	
	public static String getFieldValue(KEYS key, Application app) {
		Map<Integer, Address> addressMap = app.addressMap();
		Address hk = addressMap.get(Address.TYPE_HUKOU);
		Address pr = addressMap.get(Address.TYPE_PROPERTY);
		Address lv = addressMap.get(Address.TYPE_RESIDENCE);
		FamilyMember ff = app.memberMap().get(FamilyMember.TYPE_FATHER);
		FamilyMember mf = app.memberMap().get(FamilyMember.TYPE_MOTHER);

		switch(key) {
		case KEY_APPID:
			return String.valueOf(app.getId());
		case KEY_CANDIDATETYPE:
			return Application.candidateTypeDesc(app.getCandidateType());
		case KEY_NAME:
			return app.getName();
		case KEY_GENDER:
			return app.getGender();
		case KEY_NATION:
			return app.getNation();
		case KEY_PIDTYPE:
			return Application.pidTypeDesc(app.getPidType());
		case KEY_PIDNUMBER:
			return app.getPidNumber();
		case KEY_BIRTHDAY:
			return app.getBirthdayStr();
		case KEY_KINDERGARTENED:
			return app.isKindergartened()?"是":"否";
		case KEY_HKADDR:
			return hk == null?"":hk.getContent();
		case KEY_PROADDR:
			return pr == null?"":pr.getContent();
		case KEY_LVADDR:
			return lv == null?"":lv.getContent();
		case KEY_FATHERNAME:
			return ff == null?"":ff.getName();
		case KEY_FATHERCOMPANY:
			return ff == null?"":ff.getCompany();
		case KEY_FATHERPHONE:
			return ff == null?"":ff.getPhone();
		case KEY_FATHERMOBILE:
			return ff == null?"":ff.getMobile();
		case KEY_MOTHERNAME:
			return mf == null?"":mf.getName();
		case KEY_MOTHERCOMPANY:
			return mf == null?"":mf.getCompany();
		case KEY_MOTHERPHONE:
			return mf == null?"":mf.getPhone();
		case KEY_MOTHERMOBILE:
			return mf == null?"":mf.getMobile();
		case KEY_STATUS:
			return app.getStatusStr();
		case KEY_CHECKINSTATUS:
			if(app.getCheckinTime() == null)
				return "未签到";
			else if(app.getRecheckin() != 0)
				return "重复签到";
			else
				return app.getCheckinTimeStr();
		case KEY_BARCODE:
			return app.getBarcode();
		}
		
		return "";
	}
	
	public static HSSFWorkbook buildWorkbook(List<Application> apps) throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("报名统计表");
		HSSFRow row = sheet.createRow(0);
		
		KEYS[] keys = KEYS.values();
		for(int i=0; i<keys.length; i++) {
			row.createCell(i).setCellValue(keys[i].getName());
		}
		
		if(apps != null) {
			int index = 1;
			for(Application ta : apps) {
				row = sheet.createRow(index++);
				for(int i=0; i<keys.length; i++) {
					row.createCell(i).setCellValue(getFieldValue(keys[i], ta));
				}
			}
		}
		
		return wb;
	}
}
