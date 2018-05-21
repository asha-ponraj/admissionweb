package com.admission.service;

import java.util.List;

import com.admission.dto.AppQueryTO;
import com.admission.dto.PageInfo;
import com.admission.entity.Application;

public interface ApplicationService {
	public void createApplication(Application application) throws Exception;
	
	public void deleteApplication(int id) throws Exception;
	
	public void deleteAllApplications() throws Exception;
	
	public Application denyApplication(int id, String reason) throws Exception;
	
	public Application resetApplication(int id) throws Exception;
	
	public Application findApplication(String username, String password);
	
	public Application findApplication(int id);
	
	public List<Application> findAllApplication();
	
	public List<Application> findApplication(String idnumber, String name, PageInfo pageInfo);
	
	public List<Application> findApplication(AppQueryTO appQuery);

	public List<Application> findApplication(int fromId, int toId);
	
	public Application acceptApplication(int id) throws Exception;
	
	public void acceptApplications(int fromId, int toId) throws Exception;
	
	public void notifyApplication(int fromId, int toId, String notify) throws Exception;
	
	public Application markApplicationDownloaded(int applicationId) throws Exception;
	
	public Application checkinApplication(int applicationId) throws Exception;
	
	public Application recheckinApplication(int applicationId) throws Exception;
	
	public Application uncheckinApplication(int applicationId) throws Exception;
	
	public String resetApplicationPassword(int applicationId) throws Exception;
	
	public List<Integer> getApplicationIdList() throws Exception;
}
