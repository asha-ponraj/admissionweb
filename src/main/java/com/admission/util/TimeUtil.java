package com.admission.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    /**
     * 按照一定格式获取数据库时间的字符串形式
     * @param ts 时间戳，数据库时间格式
     * @return 如果传进来的参数为空，则返回空字符串；否则，返回对应的时间格式的字符串
     */
	public static String getSQLTimestamp(Timestamp ts) {
		if (ts == null)
			return "";
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return f.format(ts);
	}

	public static String getSQLDate(Date ts) {
		if (ts == null)
			return "";
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		return f.format(ts);
	}

    /**
     * 获取系统当前时间
     * @return 时间
     */
	public static Timestamp getCurTime() {
		return new Timestamp(new java.util.Date().getTime());
	}
    /**
     * 将当前时间转换成给定的时间格式
     * @param format 时间格式
     * @return 如果没有格式，返回null；否则返回指定格式的时间字符串
     */
	public static String getCurTimeString(String format) {
		if (format == null)
			return null;
		java.util.Date nowDate = new java.util.Date();
		SimpleDateFormat f = new SimpleDateFormat(format);
		return f.format(nowDate);
	}
    
	/**
	 * 得到当前时间 格式为yyyyMMddHHmmss
	 * @return 当前时间
	 */
	public static String getCurTimeString() {
		java.util.Date date = new java.util.Date();

		SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
		return f.format(date);
	}
	
	/**
	 * Convert a string "2017-01-28" to a Date object
	 * @param str date string like "2017-01-28"
	 * @return date object
	 * @throws ParseException
	 */
	public static Date sqlStringToDate(String str) throws ParseException {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		return f.parse(str);
	}
	
	/**
	 * Convert a date object to a string like "2017-01-28"
	 * @param date date object
	 * @return a string like "2017-01-28"
	 */
	public static String dateToSqlString(Date date) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		return f.format(date);
	}
    
	/**
	 * 得到当前时间 格式为yyyyMMddHHmmssSSS
	 * @return 当前时间
	 */
	public static String getCurTimeStringEx() {
		java.util.Date date = new java.util.Date();

		SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return f.format(date);
	}

	/**
	 * 将字符串形式的时间转成Date
	 * @param source 时间 
	 * @return Date 时间
	 * @throws ParseException
	 */
	public static Date changeStringToDate(String source) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.parse(source);
	}
	
	/**
	 * 将字符串形式的时间转成Timestamp
	 * @param source 时间 
	 * @return Timestamp 时间
	 * @throws ParseException
	 */
	public static Timestamp changeStringtoTimestamp(String source) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = format.parse(source);
		return new Timestamp(date.getTime());
	}
	
	/**
	 * Create a date object specifying year, month, day
	 * @param year
	 * @param month
	 * @param day
	 * @return date object
	 */
	public static Date createDate(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month-1, day, 0, 0, 0);
		return new Date(c.getTimeInMillis());
	}
	
	/**
	 * 根据制定的时间信息创建Timestamp的对象
	 * @param year 年份，如:1998
	 * @param month 月份，1~12
	 * @param day 日,1~31
	 * @param hour 时,0~23
	 * @param minute 分,0~59
	 * @param second 秒,0~59
	 * @return
	 */
    public static Timestamp createTimestamp(int year, int month, int day, int hour, int minute, int second) {
        Calendar c = Calendar.getInstance();
        c.set(year, month-1, day, hour, minute, second);
        return new Timestamp(c.getTimeInMillis());
    }

    public static Timestamp createTimestamp(Date date, int hour, int minute, int second) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        c.set(year, month, day, hour, minute, second);
        return new Timestamp(c.getTimeInMillis());
    }

    /**
     * 按照一定格式获取数据库时间的字符串形式
     * @param ts 时间戳，数据库时间格式
     * @return 如果传进来的参数为空，则返回空字符串；否则，返回对应的时间格式的字符串
     */
	public static String getStringTimestamp(Timestamp ts) {
		if (ts == null)
			return "";
		SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		return f.format(ts);
	}
	
	public static String getStringDate(Date d) {
		if(d == null)
			return "";
		
		SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日");
		return f.format(d);
	}
	
	/**
	 * 改变时间 格式为yyyy－MM－dd
	 * @return 当前时间
	 */
	public static String changeTimestamp(Timestamp ts) {
		if (ts == null)
			return "";
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		return f.format(ts);
	}
	
	public static String getTime(long time) {
		if(time > 0){
			Timestamp t = new Timestamp(time);
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return f.format(t);
		}
		return null;
	}
	
	public static void main(String args[]) {
		String str = "2017-01-28";
		try {
			Date d = sqlStringToDate(str);
			String ts = dateToSqlString(d);
			System.out.println(ts);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
