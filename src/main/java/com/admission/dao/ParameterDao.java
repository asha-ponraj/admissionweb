package com.admission.dao;

import java.util.List;

import com.admission.dto.PageInfo;
import com.admission.entity.Parameter;

public interface ParameterDao extends AbstractDao<Parameter> {
	public Parameter findByName(String name);
	
	public List<Parameter> findAll(PageInfo info);
}
