package com.admission.dao;

import java.util.List;

import com.admission.dto.AppQueryTO;
import com.admission.dto.PageInfo;
import com.admission.entity.Application;

public interface ApplicationDao extends AbstractDao<Application> {
	public List<Application> find(String idnumber, String name, PageInfo info);
	
	public List<Application> find(AppQueryTO appQuery);
	
	public List<Application> find(int fromId, int toId);
	
	public List<Application> find(int pidType, String pidNumber);
	
	public void accept(int fromId, int toId);
	
	public void exNotify(int fromId, int toId, String notify);
	
	public List<Integer> getIdList();
	
	public long countByStatus(int status);
}
