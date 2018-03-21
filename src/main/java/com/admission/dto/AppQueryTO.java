package com.admission.dto;

import javax.xml.bind.annotation.XmlRootElement;

import com.admission.util.StrUtil;

@XmlRootElement
public class AppQueryTO extends PageInfo {
	private static final long serialVersionUID = 1L;
	private int applicationId;
	private String pidnumber;
	private String name;
	private String gender;
	private String nation;
	private int status;
	private boolean blur;
	
	public int getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}
	public String getPidnumber() {
		return pidnumber;
	}
	public void setPidnumber(String pidnumber) {
		this.pidnumber = StrUtil.trimString(pidnumber);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = StrUtil.trimString(name);
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = StrUtil.trimString(gender);
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = StrUtil.trimString(nation);
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public boolean isBlur() {
		return blur;
	}
	public void setBlur(boolean blur) {
		this.blur = blur;
	}
}
