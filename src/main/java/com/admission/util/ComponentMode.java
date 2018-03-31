package com.admission.util;

import org.apache.log4j.Logger;

import com.admission.entity.Parameter;
import com.admission.service.ParameterService;

public class ComponentMode {
	private static final Logger log = Logger.getLogger(ComponentMode.class);
	
	public static final int MODE_INPUT = 0;
	public static final int MODE_SELECT = 1;
	
	private static final String KEY_HKADDRESSMODE = "component.hkaddress.mode";
	
	public static int getHkAddressMode() {
		ParameterService parameterService = ServiceAccess.getInstance().getParameterService();
		try {
			Parameter param = parameterService.findParameterByName(KEY_HKADDRESSMODE);
			if(param != null && !EmptyUtil.isEmpty(param.getValue())) {
				try {
					int mode = Integer.parseInt(param.getValue());
					switch(mode) {
					case MODE_SELECT:
						return MODE_SELECT;
					default:
						return MODE_INPUT;
					}
				} catch (Exception ee) {}
			}
		} catch (Exception e) {
			log.debug("fetch parameter failed", e);
		}
		
		return MODE_INPUT;
	}
}
