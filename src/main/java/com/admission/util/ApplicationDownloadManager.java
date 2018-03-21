package com.admission.util;

public class ApplicationDownloadManager {
	private static ApplicationDownloadManager instance;
	
	private ApplicationDownloadManager() {
		
	}
	
	public synchronized static ApplicationDownloadManager getInstance() {
		if(instance == null) {
			instance = new ApplicationDownloadManager();
		}
		
		return instance;
	}
	
	public  void prepareDownload() {
		
	}
}
