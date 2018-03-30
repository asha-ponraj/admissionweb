package com.admission.dto;

import javax.xml.bind.annotation.XmlRootElement;

import com.admission.entity.FamilyMember;

@XmlRootElement
public class FamilyMemberData {
	private String type;
	private String name;
	private String idNumber;
	private String company;
	private String residentPermit;
	private String phone;
	private String mobile;
	
	public FamilyMemberData() {
		
	}
	
	public FamilyMember buildFamilyMember() {
		FamilyMember fm = new FamilyMember();
		
		try {
			fm.setType(Integer.parseInt(type));
		} catch (Exception e){}
		fm.setName(name);
		fm.setIdNumber(idNumber);
		fm.setCompany(company);
		fm.setResidentPermit(residentPermit);
		fm.setPhone(phone);
		fm.setMobile(mobile);
		
		return fm;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getResidentPermit() {
		return residentPermit;
	}

	public void setResidentPermit(String residentPermit) {
		this.residentPermit = residentPermit;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
