package com.admission.util;

import org.apache.log4j.Logger;

import com.admission.entity.Parameter;
import com.admission.service.ParameterService;

public class InputValidatorConfig {
	private static final Logger log = Logger.getLogger(InputValidatorConfig.class);
	
	public static final String PROPERTYADDRESS_REQUIRED = "candidate.propertyaddress.required";
	public static final String PROPERTYADDRESS_VALIDATOR = "candidate.propertyaddress.validator";
	public static final String PROPERTYADDRESS_VALIDATOR_TIP = "candidate.propertyaddress.validatortip";
	
	public static final String HKADDRESS_REQUIRED = "candidate.hkaddress.required";
	public static final String HKADDRESS_VALIDATOR = "candidate.hkaddress.validator";
	public static final String HKADDRESS_VALIDATOR_TIP = "candidate.hkaddress.validatortip";

	public static final String RESIDENTADDRESS_REQUIRED = "candidate.residentaddress.required";
	public static final String RESIDENTADDRESS_VALIDATOR = "candidate.residentaddress.validator";
	public static final String RESIDENTADDRESS_VALIDATOR_TIP = "candidate.residentaddress.validatortip";

	public static boolean getBoolean(String key) {
		ParameterService paramService = ServiceAccess.getInstance().getParameterService();
		try {
			Parameter param = paramService.findParameterByName(key);
			if(param != null) {
				if(!EmptyUtil.isEmpty(param.getValue())) {
					return param.getValue().equalsIgnoreCase("true");
				}
			}
		} catch (Exception e) {
			log.debug("Fetch parameter failed", e);
		}
		
		return false;
	}
	
	public static String getString(String key) {
		ParameterService paramService = ServiceAccess.getInstance().getParameterService();
		try {
			Parameter param = paramService.findParameterByName(key);
			if(param != null) {
				if(!EmptyUtil.isEmpty(param.getValue())) {
					return param.getValue();
				}
			}
		} catch (Exception e) {
			log.debug("Fetch parameter failed", e);
		}
		
		return null;
	}
}
