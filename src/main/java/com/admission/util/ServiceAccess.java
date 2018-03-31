package com.admission.util;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.admission.service.ComponentService;
import com.admission.service.ParameterService;

public class ServiceAccess {
	private static ServiceAccess instance;
	
	private WebApplicationContext webApplication;
	
	private ServiceAccess() {}
	
	public static ServiceAccess getInstance() {
		if(instance == null) {
			instance = new ServiceAccess();
			
			instance.webApplication = ContextLoader.getCurrentWebApplicationContext();
		}
		return instance;
	}

	public ComponentService getComponentService() {
		return (ComponentService)webApplication.getBean("componentService");
	}

	public ParameterService getParameterService() {
		return (ParameterService)webApplication.getBean("parameterService");
	}
}
