package com.admission.util;

public class MyDataSource {

	public String getDriver() {
		return Profile.getInstance().getDbDriver();
	}
	
	public String getUrl() {
		return Profile.getInstance().getDbUrl();
	}
	
	public String getUser() {
		return Profile.getInstance().getDbUser();
	}
	
	public String getPassword() {
		return Profile.getInstance().getDbPassword();
	}
}
