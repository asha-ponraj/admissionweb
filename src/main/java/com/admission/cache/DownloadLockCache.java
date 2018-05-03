package com.admission.cache;

import java.util.Hashtable;

public class DownloadLockCache {
	private static DownloadLockCache instance;
	
	private Hashtable<Integer, Integer> lockTable = new Hashtable<Integer, Integer>();
	
	private DownloadLockCache() {
		
	}
	
	public static synchronized DownloadLockCache getInstance() {
		if(instance == null) {
			instance = new DownloadLockCache();
		}
		
		return instance;
	}
	
	public Object getLockObject(int applicationId) {
		Integer lockObj = null;
		
		synchronized(lockTable) {
			lockObj = lockTable.get(applicationId);
			if(lockObj == null) {
				lockObj = (Integer)applicationId;
				lockTable.put(applicationId, lockObj);
			}
		}
		
		return lockObj;
	}
}
