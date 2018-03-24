package com.admission.resource.impl;

import javax.jws.WebService;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.admission.dto.JsonResponse;
import com.admission.entity.User;
import com.admission.resource.SystemResource;
import com.admission.service.UserService;
import com.admission.util.Profile;
import com.admission.web.config.WebProfile;

@WebService(endpointInterface = "com.admission.resource.SystemResource")
public class SystemResourceImpl implements SystemResource {
	private Logger log = Logger.getLogger(SystemResourceImpl.class);
	
	@Context 
	private MessageContext context;

	@Autowired
	private UserService userService;

	@Override
	public JsonResponse password(String originPwd, String newPwd) {
		JsonResponse res = new JsonResponse();
		
		HttpSession session = context.getHttpServletRequest().getSession();
		User u = (User)session.getAttribute(WebProfile.SESSION_ADMINUSER);
		if(u == null) {
			res.setResult("请先登录");
		} else {
			try {
				u = userService.passwordUser(u.getId(), originPwd, newPwd);
				session.setAttribute(WebProfile.SESSION_ADMINUSER, u);
				
				res.setResult("ok");
			} catch (Exception e) {
				log.debug("password", e);
				res.setResult("修改密码失败: " + e.getMessage());
			}
		}
		return res;
	}

	@Override
	public JsonResponse reloadcfg() {
		Profile.reload();
		
		JsonResponse res = new JsonResponse();
		res.setResult("ok");
		
		return res;
	}
}
