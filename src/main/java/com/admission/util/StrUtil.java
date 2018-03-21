package com.admission.util;

import java.text.MessageFormat;
import java.util.UUID;

public class StrUtil {
	private static char[] randomStrSeeds = new char[] {
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
	};
	public static String replaceAll(String src, String old, String replace) {
		if(old == null || old.length() == 0)
			return src;
		
		StringBuffer sb = new StringBuffer(src);
		int len = old.length();
		boolean changed = false;
		for(int i=0; i<sb.length()-len; i++) {
			boolean found = true;
			for(int ip=i; ip<len+i; ip++) {
				if(sb.charAt(ip) != old.charAt(ip-i)) {
					found = false;
					break;
				}
			}
			
			if(found) {
				changed = true;
				sb.replace(i, i+len, replace);
				i += replace.length();
			}
		}
		
		if(changed)
			return sb.toString();
		
		return src;
	}
	
	public static String getRandomString(int length) {
		StringBuffer sb = new StringBuffer();
		
		for(int i=0; i<length; i++) {
			int index = (int)(Math.random() * randomStrSeeds.length);
			sb.append(randomStrSeeds[index]);
		}
		
		return sb.toString();
	}
	
	public static String trimString(String src) {
		if(src == null)
			return null;
		
		return src.trim();
	}
	
	public static String getSQLLikeString(String src) {
		return MessageFormat.format("%{0}%", src);
	}
	
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
}
