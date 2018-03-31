package com.admission.dto;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.admission.entity.Application;
import com.admission.util.StrUtil;
import com.admission.util.TimeUtil;

@XmlRootElement
public class AppData {
	private String username;
	private String password;
	private String grade; //年级
	private String candidateType; //招生对象属性
	private String name; //姓名
	private String formerName; //曾用名
	private String gender; //性别
	private String jiguan; //籍贯
	private String ethnicity; //民族
	private String nation; //国别
	private String birthday; //生日
	private String hasBirthCert; //有无出生证
	private String birthPlace; //出生地
	private String onlyChild; //是否独生子女
	private String kindergartened; //上过幼儿园
	private String nursery; //幼儿园名称
	private String pidType; //证件类型
	private String pidNumber; //证件编号
	private String hkNature; //户口性质
	private String hkType;  //户籍类别
	private String hkRegDate; //户籍登记日
	private String propertyNature; //住房性质
	private String propertyNumber; //产权证编号 
	private String propertyRegDate; //产证登记日期
	private String propertyOther; //其他住房性质
	private String propertyType; //产证类别
	private String health; //健康状况
	private String allergic; //有无过敏史
	private String specificDisease; //特殊病史
	private String immunityCert; //计划免疫证
	private String junlie; //军列子女
	private String budui; //部队子女
	private String youfu; //优抚子女
	private String dibao; //低保
	private String suiqian; //进城务工人员随迁子女
	private String liushou; //留守儿童
	private String remark; //家长需要特别说明的情况
	
	private List<AddressData> addresses = new LinkedList<AddressData>();
	private List<FamilyMemberData> members = new LinkedList<FamilyMemberData>();
	
	public AppData() {
		
	}
	
	public Application buildApplication() throws Exception {
		Application app = new Application();
		
		app.setUsername(username);
		app.setPassword(password);
		try {
			app.setGrade(Integer.parseInt(grade));
		} catch(Exception e){}
		try {
			app.setCandidateType(Integer.parseInt(candidateType));
		} catch(Exception e){}
		app.setName(name);
		app.setFormerName(formerName);
		app.setGender(gender);
		app.setJiguan(jiguan);
		app.setEthnicity(ethnicity);
		app.setNation(nation);
		try {
			app.setBirthday(TimeUtil.sqlStringToDate(birthday));
		} catch(Exception e){}
		app.setHasBirthCert(Boolean.parseBoolean(hasBirthCert));
		app.setBirthPlace(birthPlace);
		app.setOnlyChild(Boolean.parseBoolean(onlyChild));
		app.setKindergartened(Boolean.parseBoolean(kindergartened));
		app.setNursery(nursery);
		try {
			app.setPidType(Integer.parseInt(pidType));
		} catch(Exception e){}
		app.setPidNumber(pidNumber);
		try {
			app.setHkNature(Integer.parseInt(hkNature));
		} catch(Exception e){}
		try {
			app.setHkType(Integer.parseInt(hkType));
		} catch(Exception e){}
		try {
			app.setHkRegDate(new Timestamp(TimeUtil.sqlStringToDate(hkRegDate).getTime()));
		} catch(Exception e){}
		try {
			app.setPropertyNature(Integer.parseInt(propertyNature));
		} catch(Exception e){}
		app.setPropertyNumber(propertyNumber);
		try {
			app.setPropertyRegDate(new Timestamp(TimeUtil.sqlStringToDate(propertyRegDate).getTime()));
		} catch(Exception e){}
		app.setPropertyOther(propertyOther);
		try {
			app.setPropertyType(Integer.parseInt(propertyType));
		} catch(Exception e){}
		app.setHealth(health);
		app.setAllergic(Boolean.parseBoolean(allergic));
		app.setSpecificDisease(specificDisease);
		app.setImmunityCert(Boolean.parseBoolean(immunityCert));
		try {
			app.setJunlie(Integer.parseInt(junlie));
		} catch(Exception e){}
		try {
			app.setBudui(Integer.parseInt(budui));
		} catch(Exception e){}
		try {
			app.setYoufu(Integer.parseInt(youfu));
		} catch(Exception e){}
		try {
			app.setDibao(Integer.parseInt(dibao));
		} catch(Exception e){}
		try {
			app.setSuiqian(Integer.parseInt(suiqian));
		} catch(Exception e){}
		try {
			app.setLiushou(Integer.parseInt(liushou));
		} catch(Exception e){}
		app.setRemark(remark);

		for(AddressData tad : addresses) {
			app.getAddresses().add(tad.buildAddress());
		}
		
		for(FamilyMemberData tfmd : members) {
			app.getMembers().add(tfmd.buildFamilyMember());
		}
		
		return app;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = StrUtil.trimString(username);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	public String getCandidateType() {
		return candidateType;
	}
	
	public void setCandidateType(String candidateType) {
		this.candidateType = candidateType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFormerName() {
		return formerName;
	}

	public void setFormerName(String formerName) {
		this.formerName = formerName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getJiguan() {
		return jiguan;
	}

	public void setJiguan(String jiguan) {
		this.jiguan = jiguan;
	}

	public String getEthnicity() {
		return ethnicity;
	}

	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String isHasBirthCert() {
		return hasBirthCert;
	}

	public void setHasBirthCert(String hasBirthCert) {
		this.hasBirthCert = hasBirthCert;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public String isOnlyChild() {
		return onlyChild;
	}

	public void setOnlyChild(String onlyChild) {
		this.onlyChild = onlyChild;
	}

	public String isKindergartened() {
		return kindergartened;
	}

	public void setKindergartened(String kindergartened) {
		this.kindergartened = kindergartened;
	}
	
	public String getNursery() {
		return nursery;
	}
	
	public void setNursery(String nursery) {
		this.nursery = nursery;
	}

	public String getPidType() {
		return pidType;
	}

	public void setPidType(String pidType) {
		this.pidType = pidType;
	}

	public String getPidNumber() {
		return pidNumber;
	}

	public void setPidNumber(String pidNumber) {
		this.pidNumber = pidNumber;
	}

	public String getHkNature() {
		return hkNature;
	}

	public void setHkNature(String hkNature) {
		this.hkNature = hkNature;
	}

	public String getHkType() {
		return hkType;
	}

	public void setHkType(String hkType) {
		this.hkType = hkType;
	}

	public String getHkRegDate() {
		return hkRegDate;
	}

	public void setHkRegDate(String hkRegDate) {
		this.hkRegDate = hkRegDate;
	}

	public String getPropertyNature() {
		return propertyNature;
	}

	public void setPropertyNature(String propertyNature) {
		this.propertyNature = propertyNature;
	}

	public String getPropertyNumber() {
		return propertyNumber;
	}

	public void setPropertyNumber(String propertyNumber) {
		this.propertyNumber = propertyNumber;
	}

	public String getPropertyRegDate() {
		return propertyRegDate;
	}

	public void setPropertyRegDate(String propertyRegDate) {
		this.propertyRegDate = propertyRegDate;
	}

	public String getPropertyOther() {
		return propertyOther;
	}

	public void setPropertyOther(String propertyOther) {
		this.propertyOther = propertyOther;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getHealth() {
		return health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	public String isAllergic() {
		return allergic;
	}

	public void setAllergic(String allergic) {
		this.allergic = allergic;
	}

	public String getSpecificDisease() {
		return specificDisease;
	}

	public void setSpecificDisease(String specificDisease) {
		this.specificDisease = specificDisease;
	}

	public String isImmunityCert() {
		return immunityCert;
	}

	public void setImmunityCert(String immunityCert) {
		this.immunityCert = immunityCert;
	}
	
	public String getJunlie() {
		return junlie;
	}
	
	public void setJunlie(String junlie) {
		this.junlie = junlie;
	}
	
	public String getBudui() {
		return budui;
	}
	
	public void setBudui(String budui) {
		this.budui = budui;
	}
	
	public String getYoufu() {
		return youfu;
	}
	
	public void setYoufu(String youfu) {
		this.youfu = youfu;
	}
	
	public String getDibao() {
		return dibao;
	}
	
	public void setDibao(String dibao) {
		this.dibao = dibao;
	}

	public String getSuiqian() {
		return suiqian;
	}
	
	public void setSuiqian(String suiqian) {
		this.suiqian = suiqian;
	}
	
	public String getLiushou() {
		return liushou;
	}
	
	public void setLiushou(String liushou) {
		this.liushou = liushou;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<AddressData> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressData> addresses) {
		this.addresses = addresses;
	}

	public List<FamilyMemberData> getMembers() {
		return members;
	}

	public void setMembers(List<FamilyMemberData> members) {
		this.members = members;
	}

}
