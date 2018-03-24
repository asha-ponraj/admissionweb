package com.admission.cache;

import java.util.Hashtable;

import com.admission.entity.Parameter;

public class ParameterCache {
	private static ParameterCache instance;
	private Hashtable<String, Parameter> parameterTable = new Hashtable<String, Parameter>();
	
	private ParameterCache() {
	}
	
	public static synchronized ParameterCache getInstance() {
		if(instance == null) {
			instance = new ParameterCache();
		}
		
		return instance;
	}

	public synchronized Parameter getParameter(String name) {
		return parameterTable.get(name);
	}
	
	public synchronized void setParameter(String name, Parameter parameter) {
		parameterTable.put(name, parameter);
	}
	
	public synchronized void deleteParameter(String name) {
		parameterTable.remove(name);
	}
	
	public synchronized void reset() {
		parameterTable.clear();
	}
}
