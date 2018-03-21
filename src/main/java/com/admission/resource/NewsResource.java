package com.admission.resource;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.context.annotation.Scope;

import com.admission.dto.JsonResponse;
import com.admission.dto.PageInfo;
import com.admission.entity.News;

@WebService
@Path("/news")
@Produces( { MediaType.APPLICATION_JSON })
@Consumes( { MediaType.APPLICATION_JSON })
@Scope("singleton")
public interface NewsResource {

	@WebMethod
	@POST
	@Path("/create")
	public JsonResponse createNews(News news); 

	@WebMethod
	@POST
	@Path("/update")
	public JsonResponse updateNews(News news); 

	@WebMethod
	@GET
	@Path("/delete/{id}")
	public JsonResponse deleteNews(@PathParam("id")int id); 
	
	@WebMethod
	@POST
	@Path("/find")
	public JsonResponse findNews(PageInfo info);

	@WebMethod
	@GET
	@Path("/get/{id}")
	public JsonResponse getNews(@PathParam("id")int id);
}
