package com.admission.util;

import java.util.Collection;

public class EmptyUtil {
	
	/**
	 * Return if a string is null or empty
	 * @param str
	 * @return true for null and empty, otherwise false
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}
	
	/**
	 * Return if a string is null or empty
	 * @param str source string
	 * @param trim whether trim the string
	 * @return true for null and empty, otherwise false
	 */
	public static boolean isEmpty(String str, boolean trim) {
		if(str == null)
			return true;
		
		if(trim) {
			str = str.trim();
		}
		
		return str.length() == 0;
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Collection collection) {
		return collection == null || collection.size() == 0;
	}
}
