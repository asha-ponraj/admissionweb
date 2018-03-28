package com.admission.service;

import java.util.List;

import com.admission.dto.PageInfo;
import com.admission.entity.Parameter;

public interface ParameterService {
	public Parameter createParameter(Parameter parameter) throws Exception;
	
	public Parameter updateParameter(Parameter parameter) throws Exception;
	
	public void deleteParameter(int id) throws Exception;
	
	public Parameter findParameterById(int id) throws Exception;
	
	public Parameter findParameterByName(String name) throws Exception;
	
	public List<Parameter> findParameter(PageInfo pageInfo);
}
