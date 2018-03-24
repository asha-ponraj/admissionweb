package com.admission.resource.impl;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.core.Context;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.admission.dto.DataGridTO;
import com.admission.dto.JsonResponse;
import com.admission.dto.PageInfo;
import com.admission.entity.Parameter;
import com.admission.resource.ParameterResource;
import com.admission.service.ParameterService;

@WebService(endpointInterface = "com.admission.resource.ParameterResource")
public class ParameterResourceImpl implements ParameterResource {
	private static Logger log = Logger.getLogger(ParameterResourceImpl.class);

	@Context 
	private MessageContext context;

	@Autowired
	private ParameterService parameterService;

	@Override
	public JsonResponse createParameter(Parameter parameter) {
		JsonResponse res = new JsonResponse();
		
		try {
			parameter = parameterService.createParameter(parameter);
			res.setResult("ok");
			res.setData(parameter);
		} catch (Exception e) {
			log.debug("create parameter fail", e);
			res.setResult("添加参数失败: " + e.getMessage());
		}

		return res;
	}

	@Override
	public JsonResponse updateParameter(Parameter parameter) {
		JsonResponse res = new JsonResponse();
		
		try {
			parameter = parameterService.updateParameter(parameter);
			res.setResult("ok");
			res.setData(parameter);
		} catch (Exception e) {
			log.debug("update parameter fail", e);
			res.setResult("更新参数失败: " + e.getMessage());
		}

		return res;
	}
	
	@Override
	public JsonResponse deleteParameter(int id) {
		JsonResponse res = new JsonResponse();
		
		try {
			parameterService.deleteParameter(id);
			res.setResult("ok");
		} catch (Exception e) {
			log.debug("delete parameter fail", e);
			res.setResult("删除参数失败: " + e.getMessage());
		}

		return res;
	}


	@Override
	public JsonResponse findParameter(PageInfo info) {
		JsonResponse res = new JsonResponse();
		
		try {
			if(info == null)
				info = new PageInfo();
			
			List<Parameter> parameter = parameterService.findParameter(info);
			DataGridTO dgto = new DataGridTO();
			dgto.setTotal(info.getTotal());
			if(parameter != null && parameter.size() > 0) {
				for(Parameter tn : parameter) {
					dgto.getRows().add(tn);
				}
			}
			res.setResult("ok");
			res.setData(dgto);
		} catch (Throwable t) {
			log.debug("find parameter fail", t);
			res.setResult("查询参数出错: " + t.getMessage());
		}
		return res;
	}

	@Override
	public JsonResponse getParameter(String name) {
		JsonResponse res = new JsonResponse();
		
		try {
			Parameter tn = parameterService.findParameter(name);
			if(tn == null) {
				res.setResult("参数不存在");
			} else {
				res.setResult("ok");
				res.setData(tn);
			}
		} catch (Throwable t) {
			log.debug("get parameter fail", t);
			res.setResult("获取参数错误: " + t.getMessage());
		}
		return res;
	}
}
