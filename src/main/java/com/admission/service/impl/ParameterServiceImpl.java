package com.admission.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.admission.cache.ParameterCache;
import com.admission.dao.ParameterDao;
import com.admission.dto.PageInfo;
import com.admission.entity.Parameter;
import com.admission.service.ParameterService;

@Transactional(propagation=Propagation.REQUIRED)
@Service("parameterService")
public class ParameterServiceImpl implements ParameterService {

	@Autowired
	private ParameterDao parameterDao;

	@Override
	public Parameter createParameter(Parameter parameter) throws Exception {
		Parameter tn = parameterDao.findByName(parameter.getName());
		if(tn != null)
			throw new Exception("同名称的参数已经存在了");
		
		parameterDao.save(parameter);
		
		ParameterCache.getInstance().setParameter(parameter.getName(), parameter);
		
		return parameter;
	}

	@Override
	public Parameter updateParameter(Parameter parameter) throws Exception {
		Parameter tn = parameterDao.findById(parameter.getId());
		if(tn == null)
			throw new Exception("参数不存在");
		
		Parameter ts = parameterDao.findByName(parameter.getName());
		if(ts != null && ts.getId().intValue() == tn.getId().intValue()) {
			throw new Exception("同名称的参数已经存在了");
		}
		
		tn.setName(parameter.getName());
		tn.setDescription(parameter.getDescription());
		tn.setValue(parameter.getValue());
		
		parameterDao.saveOrUpdate(tn);
		
		ParameterCache.getInstance().setParameter(tn.getName(), tn);
		
		return tn;
	}

	@Override
	public void deleteParameter(int id) throws Exception {
		Parameter tn = parameterDao.findById(id);
		if(tn == null)
			throw new Exception("参数不存在");
		
		parameterDao.delete(tn);
		
		ParameterCache.getInstance().deleteParameter(tn.getName());
	}

	@Override
	public Parameter findParameter(String name) throws Exception {
		Parameter p = ParameterCache.getInstance().getParameter(name);
		if(p == null) {
			p = parameterDao.findByName(name);
			if(p != null) {
				ParameterCache.getInstance().setParameter(p.getName(), p);
			}
		}
		return p;
	}

	@Override
	public List<Parameter> findParameter(PageInfo pageInfo) {
		List<Parameter> ps = parameterDao.findAll(pageInfo);
		
		if(ps != null && ps.size() > 0) {
			for(Parameter tp : ps) {
				ParameterCache.getInstance().setParameter(tp.getName(), tp);
			}
		}
		
		return ps;
	}
	
}
