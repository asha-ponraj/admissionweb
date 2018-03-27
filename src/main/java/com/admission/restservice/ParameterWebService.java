package com.admission.restservice;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.admission.dto.DataGridTO;
import com.admission.dto.JsonResponse;
import com.admission.dto.PageInfo;
import com.admission.entity.Parameter;
import com.admission.service.ParameterService;

@Controller
@RequestMapping("/parameter")
public class ParameterWebService {
	private static Logger log = Logger.getLogger(ParameterWebService.class);

	@Autowired
	private ParameterService parameterService;


	@RequestMapping(value="/create", method=RequestMethod.POST, headers="Accept=application/json")
	@ResponseBody 
	public JsonResponse createParameter(@RequestBody Parameter parameter) {
		JsonResponse res = new JsonResponse();
		
		try {
			parameter = parameterService.createParameter(parameter);
			res.setResult("ok");
			res.setData(parameter);
		} catch (Exception e) {
			log.debug("create parameter fail", e);
			res.setResult("添加新闻失败: " + e.getMessage());
		}

		return res;
	}

	@RequestMapping(value="/update", method=RequestMethod.POST, headers="Accept=application/json")
	@ResponseBody 
	public JsonResponse updateParameter(@RequestBody Parameter parameter) {
		JsonResponse res = new JsonResponse();
		
		try {
			parameter = parameterService.updateParameter(parameter);
			res.setResult("ok");
			res.setData(parameter);
		} catch (Exception e) {
			log.debug("update parameter fail", e);
			res.setResult("编辑新闻失败: " + e.getMessage());
		}

		return res;
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET, headers="Accept=application/json")
	@ResponseBody 
	public JsonResponse deleteParameter(@PathVariable int id) {
		JsonResponse res = new JsonResponse();
		
		try {
			parameterService.deleteParameter(id);
			res.setResult("ok");
		} catch (Exception e) {
			log.debug("delete parameter fail", e);
			res.setResult("删除新闻失败: " + e.getMessage());
		}

		return res;
	}


	@RequestMapping(value="/find", method=RequestMethod.POST, headers="Accept=application/json")
	@ResponseBody 
	public JsonResponse findParameter(@RequestBody PageInfo info) {
		JsonResponse res = new JsonResponse();
		
		try {
			if(info == null)
				info = new PageInfo();
			
			List<Parameter> parameters = parameterService.findParameter(info);
			DataGridTO dgto = new DataGridTO();
			dgto.setTotal(info.getTotal());
			if(parameters != null && parameters.size() > 0) {
				for(Parameter tn : parameters) {
					dgto.getRows().add(tn);
				}
			}
			res.setResult("ok");
			res.setData(dgto);
		} catch (Throwable t) {
			log.debug("find application fail", t);
			res.setResult("查询新闻出错: " + t.getMessage());
		}
		return res;
	}

	@RequestMapping(value="/get/{name}", method=RequestMethod.GET, headers="Accept=application/json")
	@ResponseBody 
	public JsonResponse getParameter(@PathVariable String name) {
		JsonResponse res = new JsonResponse();
		
		try {
			Parameter tn = parameterService.findParameter(name);
			if(tn == null) {
				res.setResult("新闻不存在");
			} else {
				res.setResult("ok");
				res.setData(tn);
			}
		} catch (Throwable t) {
			log.debug("set timespace fail", t);
			res.setResult("获取新闻错误: " + t.getMessage());
		}
		return res;
	}
}
