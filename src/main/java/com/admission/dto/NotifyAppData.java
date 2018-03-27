package com.admission.dto;

public class NotifyAppData {
	private int fromId;
	private int toId;
	private String notify;
	
	public NotifyAppData() {
		
	}

	public int getFromId() {
		return fromId;
	}

	public void setFromId(int fromId) {
		this.fromId = fromId;
	}

	public int getToId() {
		return toId;
	}

	public void setToId(int toId) {
		this.toId = toId;
	}

	public String getNotify() {
		return notify;
	}

	public void setNotify(String notify) {
		this.notify = notify;
	}
	
	
}
