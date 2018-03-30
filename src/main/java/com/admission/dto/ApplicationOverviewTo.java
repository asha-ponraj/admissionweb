package com.admission.dto;

import javax.xml.bind.annotation.XmlRootElement;

import com.admission.entity.Application;

@XmlRootElement
public class ApplicationOverviewTo {
	private Integer id;
	private String barcode;
	private String username;
	private String gradeStr;
	private String candidateTypeStr;
	private String name;
	private String gender;
	private String nation;
	private String pidTypeStr;
	private String pidNumber;
	private String createTimeStr;
	private String downloadTimeStr;
	private String checkinTimeStr;
	private String recheckinStr;
	private int status;
	private String statusStr;
	private String birthdayStr;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCandidateTypeStr() {
		return candidateTypeStr;
	}
	public void setCandidateTypeStr(String candidateTypeStr) {
		this.candidateTypeStr = candidateTypeStr;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	
	
	public static ApplicationOverviewTo from(Application app) {
		ApplicationOverviewTo o = new ApplicationOverviewTo();
		
		o.setId(app.getId());
		o.setBarcode(app.getBarcode());
		o.setUsername(app.getUsername());
		o.setGradeStr(Application.gradeDesc(app.getGrade()));
		o.setCandidateTypeStr(Application.candidateTypeDesc(app.getCandidateType()));
		o.setName(app.getName());
		o.setGender(app.getGender());
		o.setNation(app.getNation());
		o.setPidTypeStr(Application.pidTypeDesc(app.getPidType()));
		o.setPidNumber(app.getPidNumber());
		o.setCreateTimeStr(app.getCreateTimeStr());
		o.setDownloadTimeStr(app.getDownloadTimeStr());
		o.setCheckinTimeStr(app.getCheckinTimeStr());
		o.setRecheckinStr(app.getRecheckin() == 0 ? "" : "重复");
		o.setStatus(app.getStatus());
		o.setStatusStr(app.getStatusStr());
		o.setBirthdayStr(app.getBirthdayStr());
		
		return o;
	}
	public String getPidTypeStr() {
		return pidTypeStr;
	}
	public void setPidTypeStr(String pidTypeStr) {
		this.pidTypeStr = pidTypeStr;
	}
	public String getPidNumber() {
		return pidNumber;
	}
	public void setPidNumber(String pidNumber) {
		this.pidNumber = pidNumber;
	}
	public String getStatusStr() {
		return statusStr;
	}
	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}
	public String getCheckinTimeStr() {
		return checkinTimeStr;
	}
	public void setCheckinTimeStr(String checkinTimeStr) {
		this.checkinTimeStr = checkinTimeStr;
	}
	public String getRecheckinStr() {
		return recheckinStr;
	}
	public void setRecheckinStr(String recheckinStr) {
		this.recheckinStr = recheckinStr;
	}
	public String getBirthdayStr() {
		return birthdayStr;
	}
	public void setBirthdayStr(String birthdayStr) {
		this.birthdayStr = birthdayStr;
	}
	public String getDownloadTimeStr() {
		return downloadTimeStr;
	}
	public void setDownloadTimeStr(String downloadTimeStr) {
		this.downloadTimeStr = downloadTimeStr;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getGradeStr() {
		return gradeStr;
	}
	public void setGradeStr(String gradeStr) {
		this.gradeStr = gradeStr;
	}
}
