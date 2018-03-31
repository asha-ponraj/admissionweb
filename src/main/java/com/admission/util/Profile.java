package com.admission.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Pattern;

public class Profile {
	private static Profile instance;
	private static final String SUBPATH_NAME = "data";
	private static final String PROFILE_FILENAME = "profile.properties";
	private File profileFile;
	
	private Timestamp startApplicationTime;
	private static final String KEY_STARTAPPLICATIONTIME = "startapplicationtime";
	private Timestamp endApplicationTime;
	private static final String KEY_ENDAPPLICATIONTIME = "endapplicationtime";
	private Timestamp downloadEndTime;
	private static final String KEY_DOWNLOADENDTIME = "enddownloadtime";
	private String selectedNotifyTip;
	private static final String KEY_SELECTEDNOTIFYTIP = "selectednotifytip";
	private Date maxBirthday;
	private static final String KEY_MAXBIRTHDAY = "maxbirthday";
	private Date minBirthday;
	private static final String KEY_MINBIRTHDAY = "minbirthday";
	private String addressFilter;
	private static final String KEY_ADDRESSFILTER = "addressfilter";
	private String addressTip;
	private static final String KEY_ADDRESSTIP = "addresstip";
	
	private static final String KEY_DBDRIVER = "db_driver";
	private static final String KEY_DBURL = "db_url";
	private static final String KEY_DBUSER = "db_user";
	private static final String KEY_DBPASSWORD = "db_password";
	private String dbDriver;
	private String dbUrl;
	private String dbUser;
	private String dbPassword;
	
	public String getDbDriver() {
		return dbDriver;
	}

	public void setDbDriver(String dbDriver) {
		this.dbDriver = dbDriver;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	private Pattern addressPattern;
	
	public synchronized static Profile getInstance() {
		if(instance == null) {
			instance = new Profile();
		}
		
		return instance;
	}
	
	public synchronized static void reload() {
		instance = new Profile();
	}
	
	private Profile() {
		profileFile = new File(getHomePath(), PROFILE_FILENAME);
		
		load();
	}
	
	
	public static String getFileSeparator() {
		return System.getProperty("file.separator");
	}
	
	public static String getHomePath() {
		String homeDir = System.getProperty("demo.admission.home");
//		String homeDir = "c:";
		String path = MessageFormat.format("{0}{1}{2}", homeDir, getFileSeparator(), SUBPATH_NAME);
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
			tmpTime = TimeUtil.changeStringtoTimestamp(getPropertyString(pro, KEY_STARTAPPLICATIONTIME, "2017-04-10 00:00:00"));
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
			tmpTime = TimeUtil.changeStringtoTimestamp(getPropertyString(pro, KEY_ENDAPPLICATIONTIME, "2017-04-28 23:59:59"));
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
			tmpTime = TimeUtil.changeStringtoTimestamp(getPropertyString(pro, KEY_DOWNLOADENDTIME, "2017-05-09 18:00:00"));
		} catch (ParseException e) {
		}
		if(tmpTime == null)
			endDownloadTime = TimeUtil.createTimestamp(2015, 5, 9, 18, 0, 0);
		else {
			endDownloadTime = tmpTime;
		}
		setDownloadEndTime(endDownloadTime);
		
		this.setSelectedNotifyTip(getPropertyString(pro, KEY_SELECTEDNOTIFYTIP, ""));
		
		Date tmpDate = null;
		try {
			tmpDate = TimeUtil.sqlStringToDate(getPropertyString(pro, KEY_MINBIRTHDAY, "2013-09-01"));
		} catch(ParseException e){
		}
		if(tmpDate == null)
			setMinBirthday(TimeUtil.createDate(2013, 9, 1));
		else
			setMinBirthday(tmpDate);
		
		try {
			tmpDate = TimeUtil.sqlStringToDate(getPropertyString(pro, KEY_MAXBIRTHDAY, "2014-08-31"));
		} catch(ParseException e){
		}
		if(tmpDate == null)
			setMaxBirthday(TimeUtil.createDate(2014, 8, 31));
		else
			setMaxBirthday(tmpDate);
		
		this.setAddressFilter(getPropertyString(pro, KEY_ADDRESSFILTER, ""));
		this.setAddressTip(getPropertyString(pro, KEY_ADDRESSTIP, ""));
		
		this.setDbDriver(getPropertyString(pro, KEY_DBDRIVER, "com.mysql.jdbc.Driver"));
		this.setDbUrl(getPropertyString(pro, KEY_DBURL, "jdbc:mysql://localhost:3306/demodb?user=demo&password=config&autoReconnect=true&autoReconnectForPools=true&useunicode=true&characterEncoding=utf8"));
		this.setDbUser(getPropertyString(pro, KEY_DBUSER, "demo"));
		this.setDbPassword(getPropertyString(pro, KEY_DBPASSWORD, "config"));
	}
	
	public synchronized void save() {
		Properties pro = new Properties();

		pro.setProperty(KEY_STARTAPPLICATIONTIME, TimeUtil.getSQLTimestamp(getStartApplicationTime()));
		pro.setProperty(KEY_ENDAPPLICATIONTIME, TimeUtil.getSQLTimestamp(getEndApplicationTime()));
		pro.setProperty(KEY_DOWNLOADENDTIME, TimeUtil.getSQLTimestamp(getDownloadEndTime()));
		pro.setProperty(KEY_SELECTEDNOTIFYTIP, getSelectedNotifyTip());
		pro.setProperty(KEY_MINBIRTHDAY, TimeUtil.dateToSqlString(getMinBirthday()));
		pro.setProperty(KEY_MAXBIRTHDAY, TimeUtil.dateToSqlString(getMaxBirthday()));
		pro.setProperty(KEY_ADDRESSFILTER, getAddressFilter());
		pro.setProperty(KEY_ADDRESSTIP, getAddressTip());
		
		pro.setProperty(KEY_DBDRIVER, getDbDriver());
		pro.setProperty(KEY_DBURL, getDbUrl());
		pro.setProperty(KEY_DBUSER, getDbUser());
		pro.setProperty(KEY_DBPASSWORD, getDbPassword());
		
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

	public Date getMaxBirthday() {
		return maxBirthday;
	}

	public void setMaxBirthday(Date maxBirthday) {
		this.maxBirthday = maxBirthday;
	}

	public Date getMinBirthday() {
		return minBirthday;
	}

	public void setMinBirthday(Date minBirthday) {
		this.minBirthday = minBirthday;
	}

	public String getAddressFilter() {
		return addressFilter;
	}

	public void setAddressFilter(String addressFilter) {
		if(addressFilter != null)
			this.addressFilter = addressFilter.trim();
		else
			this.addressFilter = null;
	}

	public String getAddressTip() {
		return addressTip;
	}

	public void setAddressTip(String addressTip) {
		if(addressTip != null)
			this.addressTip = addressTip.trim();
		else
			this.addressTip = "";
	}

	public synchronized Pattern getAddressPattern() {
		if(addressPattern == null) {
			if(addressFilter == null || addressFilter.length() == 0)
				return null;
			
			addressPattern = Pattern.compile(addressFilter);
		}
		return addressPattern;
	}

	public void setAddressPattern(Pattern addressPattern) {
		this.addressPattern = addressPattern;
	}
	
	public String getApplicationYear() {
		SimpleDateFormat f = new SimpleDateFormat("yyyy");
		return f.format(this.startApplicationTime);
	}
}
