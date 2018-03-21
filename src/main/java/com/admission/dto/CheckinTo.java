package com.admission.dto;

import javax.xml.bind.annotation.XmlRootElement;

import com.admission.entity.Application;

@XmlRootElement
public class CheckinTo {
	private boolean recheckin;
	private Application appData;
	
	public boolean isRecheckin() {
		return recheckin;
	}
	public void setRecheckin(boolean recheckin) {
		this.recheckin = recheckin;
	}
	public Application getAppData() {
		return appData;
	}
	public void setAppData(Application appData) {
		this.appData = appData;
	}
}
