package com.admission.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Properties;

public class Profile {
	private static Profile instance;
	private static final String PROFILE_FILENAME = "qywebsystem.properties";
	private File profileFile;
	
	private Timestamp startApplicationTime;
	private static final String KEY_STARTAPPLICATIONTIME = "startapplicationtime";
	private Timestamp endApplicationTime;
	private static final String KEY_ENDAPPLICATIONTIME = "endapplicationtime";
	private Timestamp downloadEndTime;
	private static final String KEY_DOWNLOADENDTIME = "enddownloadtime";
	private String selectedNotifyTip;
	private static final String KEY_SELECTEDNOTIFYTIP = "selectednotifytip";
	
	public synchronized static Profile getInstance() {
		if(instance == null) {
			instance = new Profile();
		}
		
		return instance;
	}
	
	private Profile() {
		profileFile = new File(getHomePath(), PROFILE_FILENAME);
		
		load();
	}
	
	
	public static String getFileSeparator() {
		return System.getProperty("file.separator");
	}
	
	public static String getHomePath() {
//		String homeDir = System.getProperty("user.home");
		String homeDir = "c:";
		String path = MessageFormat.format("{0}{1}{2}", homeDir, getFileSeparator(), "qywebsystem");
		File f = new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
		
		return path;
	}
	
	public static File getApplicationPath() {
		File dir = new File(getHomePath(), "application");
		if(!dir.exists())
			dir.mkdir();
		
		return dir;
	}
	
	public static File getBarcodePath() {
		File dir = new File(getHomePath(), "barcode");
		if(!dir.exists())
			dir.mkdir();
		
		return dir;
	}
	
	public static File getExportPath() {
		File dir = new File(getHomePath(), "export");
		if(!dir.exists())
			dir.mkdir();
		
		return dir;
	}
	
	public synchronized void load() {
		Properties pro = new Properties();
		try {
			pro.load(new FileInputStream(profileFile));
		} catch (Exception e) {
//			log.debug("load profile fail", e);
		}
		
		Timestamp tmpTime = null;
		Timestamp startTime = null;
		try {
			tmpTime = TimeUtil.changeStringtoTimestamp(getPropertyString(pro, KEY_STARTAPPLICATIONTIME, "2015-04-25 00:00:00"));
		} catch (ParseException e) {
		}
		if(tmpTime == null)
			startTime = TimeUtil.createTimestamp(2015, 4, 25, 0, 0, 0);
		else {
			startTime = tmpTime;
		}
		setStartApplicationTime(startTime);
		
		Timestamp endTime = null;
		try {
			tmpTime = TimeUtil.changeStringtoTimestamp(getPropertyString(pro, KEY_ENDAPPLICATIONTIME, "2015-04-28 23:59:59"));
		} catch (ParseException e) {
		}
		if(tmpTime == null)
			endTime = TimeUtil.createTimestamp(2015, 4, 28, 23, 59, 59);
		else {
			endTime = tmpTime;
		}
		setEndApplicationTime(endTime);
		
		Timestamp endDownloadTime = null;
		try {
			tmpTime = TimeUtil.changeStringtoTimestamp(getPropertyString(pro, KEY_DOWNLOADENDTIME, "2015-05-09 18:00:00"));
		} catch (ParseException e) {
		}
		if(tmpTime == null)
			endDownloadTime = TimeUtil.createTimestamp(2015, 5, 9, 18, 0, 0);
		else {
			endDownloadTime = tmpTime;
		}
		setDownloadEndTime(endDownloadTime);
		
		this.setSelectedNotifyTip(getPropertyString(pro, KEY_SELECTEDNOTIFYTIP, ""));
	}
	
	public synchronized void save() {
		Properties pro = new Properties();

		pro.setProperty(KEY_STARTAPPLICATIONTIME, TimeUtil.getSQLTimestamp(getStartApplicationTime()));
		pro.setProperty(KEY_ENDAPPLICATIONTIME, TimeUtil.getSQLTimestamp(getEndApplicationTime()));
		pro.setProperty(KEY_DOWNLOADENDTIME, TimeUtil.getSQLTimestamp(getDownloadEndTime()));
		pro.setProperty(KEY_SELECTEDNOTIFYTIP, getSelectedNotifyTip());
		
		try {
			pro.store(new FileOutputStream(profileFile), "protential calculator profile file");
		} catch (Exception e) {
		}
	}
	
	private String getPropertyString(Properties pro, String key, String defaultValue) {
		String str = pro.getProperty(key);
		if(str != null) {
			str = str.trim();
			if(str.length() > 0)
				return str.trim();
		}
		
		return defaultValue;
	}

	public Timestamp getStartApplicationTime() {
		return startApplicationTime;
	}

	public void setStartApplicationTime(Timestamp startApplicationTime) {
		this.startApplicationTime = startApplicationTime;
	}

	public Timestamp getEndApplicationTime() {
		return endApplicationTime;
	}

	public void setEndApplicationTime(Timestamp endApplicationTime) {
		this.endApplicationTime = endApplicationTime;
	}

	public Timestamp getDownloadEndTime() {
		return downloadEndTime;
	}

	public void setDownloadEndTime(Timestamp downloadEndTime) {
		this.downloadEndTime = downloadEndTime;
	}

	public String getSelectedNotifyTip() {
		return selectedNotifyTip;
	}

	public void setSelectedNotifyTip(String selectedNotifyTip) {
		this.selectedNotifyTip = selectedNotifyTip;
	}
}
