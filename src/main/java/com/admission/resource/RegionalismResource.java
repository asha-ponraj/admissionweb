package com.admission.resource;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.springframework.context.annotation.Scope;

import com.admission.dto.JsonResponse;

@WebService
@Path("/regionalism")
@Produces( { MediaType.APPLICATION_JSON })
@Consumes( { MediaType.APPLICATION_JSON })
@Scope("singleton")
public interface RegionalismResource {

	@WebMethod
	@GET
	@Path("/getProvinceListForSelect")
	public JsonResponse getProvinceListForSelect(); 

	@WebMethod
	@GET
	@Path("/getCityListForSelect")
	public JsonResponse getCityListForSelect(@QueryParam("provinceId")int provinceId); 

	@WebMethod
	@GET
	@Path("/getDistrictListForSelect")
	public JsonResponse getDistrictListForSelect(@QueryParam("cityId")int cityId); 

	@WebMethod
	@POST
	@Path("/updateRegionalism")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public JsonResponse updateRegionalism(MultipartBody body); 
}
