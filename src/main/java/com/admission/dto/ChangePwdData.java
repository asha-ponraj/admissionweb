package com.admission.dto;

public class ChangePwdData {
	private String originPwd;
	private String newPwd;
	
	public ChangePwdData() {
		
	}

	public String getOriginPwd() {
		return originPwd;
	}

	public void setOriginPwd(String originPwd) {
		this.originPwd = originPwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
	
}
