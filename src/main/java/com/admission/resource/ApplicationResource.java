package com.admission.resource;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.context.annotation.Scope;

import com.admission.dto.AppData;
import com.admission.dto.AppQueryTO;
import com.admission.dto.JsonResponse;
import com.admission.entity.Application;

@WebService
@Path("/application")
@Produces( { MediaType.APPLICATION_JSON })
@Consumes( { MediaType.APPLICATION_JSON })
@Scope("singleton")
public interface ApplicationResource {

	@WebMethod
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public JsonResponse login(@FormParam("username")String username, @FormParam("password")String password); 
	
	@WebMethod
	@POST
	@Path("/find")
	public JsonResponse findApplication(AppQueryTO appQuery);
	
	@WebMethod
	@GET
	@Path("/detail")
	@Produces(MediaType.TEXT_HTML)
	public String getApplicationDetail(@QueryParam("id")int id); 

	public JsonResponse createApplication(Application application); 
	
	@WebMethod
	@POST
	@Path("/create")
	public JsonResponse createApplication(AppData appData);

	@WebMethod
	@GET
	@Path("/delete/{id}")
	public JsonResponse deleteApplication(@PathParam("id")int id); 

	@WebMethod
	@POST
	@Path("/deny")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public JsonResponse denyApplication(@FormParam("id")int id, @FormParam("reason")String reason); 

	@WebMethod
	@GET
	@Path("/reset/{id}")
	public JsonResponse resetApplication(@PathParam("id")int id); 

	@WebMethod
	@GET
	@Path("/get")
	public JsonResponse getApplication(@QueryParam("username")String username, @QueryParam("password")String password); 

	@WebMethod
	@GET
	@Path("/findbc")
	public JsonResponse getApplicationByBarcode(@QueryParam("barcode")String barcode, @QueryParam("checkin")boolean checkin); 

	@WebMethod
	@GET
	@Path("/recheckin")
	public JsonResponse recheckinApplication(@QueryParam("id")int id); 

	@WebMethod
	@GET
	@Path("/settimespace")
	public JsonResponse setTimeSpace(@QueryParam("startTime")String startTime, @QueryParam("endTime")String endTime,
			@QueryParam("downloadEndTime")String downloadEndTime);

	@WebMethod
	@GET
	@Path("/accept")
	public JsonResponse acceptApplication(@QueryParam("fromId")int fromId, @QueryParam("toId")int toId);

	@WebMethod
	@POST
	@Path("/notify")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public JsonResponse notifyApplication(@FormParam("fromId")int fromId, @FormParam("toId")int toId, @FormParam("notify")String notify); 

	@WebMethod
	@GET
	@Path("/download")
	@Produces("application/msword")
	public Response downloadApplication();

	@WebMethod
	@GET
	@Path("/preparedownloadall")
	public JsonResponse prepareDownloadApplicationAll();

	@WebMethod
	@GET
	@Path("/getpreparedownloadallstatus")
	public JsonResponse getPrepareDownloadApplicationAllStatus();

	@WebMethod
	@GET
	@Path("/downloadall")
	@Produces("application/vnd.ms-excel")
	public Response downloadApplicationAll();
	
	@WebMethod
	@GET
	@Path("/resetquerypassword")
	public JsonResponse resetApplicationPassword(@QueryParam("applicationid")int id); 
	
	@WebMethod
	@GET
	@Path("/getpassword")
	public JsonResponse getPassword(@QueryParam("applicationid")int id); 
}
