package com.admission.resource;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.context.annotation.Scope;

import com.admission.dto.JsonResponse;

@WebService
@Path("/system")
@Produces( { MediaType.APPLICATION_JSON })
@Consumes( { MediaType.APPLICATION_JSON })
@Scope("singleton")
public interface SystemResource {
	
	@WebMethod
	@POST
	@Path("/password")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public JsonResponse password(@FormParam("originPwd")String originPwd, @FormParam("newPwd")String newPwd); 

}
