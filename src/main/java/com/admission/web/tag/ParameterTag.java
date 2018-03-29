package com.admission.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.admission.entity.Parameter;
import com.admission.service.ParameterService;

public class ParameterTag extends TagSupport {
	private static Logger logger = Logger.getLogger(ParameterTag.class);

	private static final long serialVersionUID = 1L;

	private String name;
	private String defaultValue;
	private WebApplicationContext webApplication;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDefaultValue() {
		return defaultValue;
	}
	
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public int doStartTag() throws JspException {
		if(webApplication == null) {
			webApplication = ContextLoader.getCurrentWebApplicationContext();
		}
		
		try {
			ParameterService parameterService = (ParameterService)webApplication.getBean("parameterService");
			Parameter param = parameterService.findParameterByName(name);
			if(param != null) {
				pageContext.getOut().write(param.getValue());
			} else if(defaultValue != null) {
				pageContext.getOut().write(defaultValue);
			}
		} catch(Exception e) {
			logger.debug("Fetch parameter failed", e);
		}

		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
}
