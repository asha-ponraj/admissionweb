package com.admission.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.admission.dao.AddressDao;
import com.admission.dao.ApplicationDao;
import com.admission.dao.FamilyMemberDao;
import com.admission.dto.AppQueryTO;
import com.admission.dto.PageInfo;
import com.admission.entity.Address;
import com.admission.entity.Application;
import com.admission.entity.FamilyMember;
import com.admission.service.ApplicationService;
import com.admission.util.StrUtil;
import com.admission.util.TimeUtil;

@Transactional(propagation=Propagation.REQUIRED)
@Service("applicationService")
public class ApplicationServiceImpl implements ApplicationService {

	@Autowired
	private ApplicationDao applicationDao;
	
	@Autowired
	private AddressDao addressDao;
	
	@Autowired
	private FamilyMemberDao familyMemberDao;

	@Override
	public void createApplication(Application application) throws Exception {
		Application app = null;
		app = applicationDao.findByProperty("username", application.getUsername());
		if(app != null)
			throw new Exception("相同的用户名已经存在,请重新输入一个用户名.");
		
		int pidType = application.getPidType();
		String pidNumber = application.getPidNumber();
		if(pidNumber != null)	pidNumber = pidNumber.trim();
		if(pidNumber == null || pidNumber.length() == 0) {
			throw new Exception("请输入身份证件号码");
		}
		
		List<Application> apps = applicationDao.find(pidType, pidNumber);
		if(apps != null && apps.size() > 0)
			throw new Exception("该身份证件的报名表已经注册,请不要重复登记.");
		
		if(application.getRemark().length() > 500) {
			throw new Exception("家长特别说明的内容长度不要超过500个字.");
		}
		
		application.setCreateTime(TimeUtil.getCurTime());
		application.setStatus(Application.AS_SUBMITED);
		application.setSubbarcode(StrUtil.getRandomString(4));
		applicationDao.save(application);
		
		for(Address ta : application.getAddresses()) {
			ta.setApplication(application);
			addressDao.save(ta);
		}
		
		Set<Integer> fmTypes = new HashSet<Integer>();
		for(FamilyMember tm : application.getMembers()) {
			switch(tm.getType()) {
			case FamilyMember.TYPE_FATHER:
			case FamilyMember.TYPE_MOTHER:
				if(!fmTypes.contains(tm.getType())) {
					tm.setApplication(application);
					familyMemberDao.save(tm);
				}
				break;
			}
		}
	}

	@Override
	public void deleteApplication(int id) throws Exception {
		Application app = applicationDao.findById(id);
		if(app == null)
			throw new Exception("要删除的报名表不存在");
		
		addressDao.deleteByApplication(id);
		familyMemberDao.deleteByApplication(id);
		
		applicationDao.delete(app);
	}
	
	@Override
	public void deleteAllApplications() throws Exception {
		addressDao.deleteAll();
		familyMemberDao.deleteAll();
		applicationDao.deleteAll();
	}

	@Override
	public Application checkinApplication(int applicationId) throws Exception {
		Application app = applicationDao.findById(applicationId);
		if(app == null)
			throw new Exception("要签到的报名表不存在，请重新查询");
		
		app.setCheckinTime(TimeUtil.getCurTime());
		app.setStatus(Application.AS_CHECKIN);
		applicationDao.save(app);
		
		return app;
	}

	@Override
	public Application recheckinApplication(int applicationId) throws Exception {
		Application app = applicationDao.findById(applicationId);
		if(app == null)
			throw new Exception("要登记重复签到报名表不存在，请重新查询");
		
		app.setRecheckin(1);
		applicationDao.save(app);
		
		return app;
	}

	@Override
	public Application findApplication(String username, String password) {
		Application app = applicationDao.findByProperty("username", username);
		if(app == null)
			return null;
		
		return app.getPassword().equals(password)?app:null;
	}

	@Override
	public List<Application> findApplication(String idnumber, String name,
			PageInfo pageInfo) {
		return applicationDao.find(idnumber, name, pageInfo);
	}

	@Override
	public List<Application> findApplication(AppQueryTO appQuery) {
		return applicationDao.find(appQuery);
	}

	@Override
	public Application findApplication(int id) {
		return applicationDao.findById(id);
	}

	@Override
	public List<Application> findApplication(int fromId, int toId) {
		return applicationDao.find(fromId, toId);
	}

	@Override
	public void acceptApplication(int fromId, int toId) throws Exception {
		applicationDao.accept(fromId, toId);
	}

	@Override
	public void notifyApplication(int fromId, int toId, String notify)
			throws Exception {
		applicationDao.exNotify(fromId, toId, notify);
	}
	
	@Override
	public Application markApplicationDownloaded(int applicationId) throws Exception {
		Application app = applicationDao.findById(applicationId);
		if(app == null)
			throw new Exception("报名表不存在");
		
		if(app.getStatus() < Application.AS_NOTIFIED)
			throw new Exception("请等到收到通知后再下载报名表");
		
		boolean needSave = false;
		if(app.getDownloadTime() == null) {
			app.setDownloadTime(TimeUtil.getCurTime());
			needSave = true;
		}
		
		if(app.getStatus() < Application.AS_DOWNLOADED) {
			app.setStatus(Application.AS_DOWNLOADED);
			needSave = true;
		}

		if(needSave)
			applicationDao.save(app);
		
		return app;
	}

	@Override
	public List<Application> findAllApplication() {
		return applicationDao.getAll();
	}

	@Override
	public String resetApplicationPassword(int applicationId) throws Exception {
		Application app = applicationDao.findById(applicationId);
		if(app == null) {
			throw new Exception("报名表不存在");
		}
		
		String newPwd = StrUtil.getRandomString(6);
		app.setPassword(newPwd);
		
		applicationDao.saveOrUpdate(app);
		
		return newPwd;
	}

	@Override
	public List<Integer> getApplicationIdList() {
		return applicationDao.getIdList();
	}

	@Override
	public Application denyApplication(int id, String reason) throws Exception {
		Application app = applicationDao.findById(id);
		if(app == null) {
			throw new Exception("报名表不存在");
		}
		
		if(app.getStatus() != Application.AS_SUBMITED) {
			throw new Exception("报名表状态不是提交状态，不能拒绝");
		}
		
		app.setNotifyTime(TimeUtil.getCurTime());
		app.setNotify(reason);
		app.setStatus(Application.AS_DENIED);
		
		applicationDao.saveOrUpdate(app);
		
		return app;
	}

	@Override
	public Application resetApplication(int id) throws Exception {
		Application app = applicationDao.findById(id);
		if(app == null) {
			throw new Exception("报名表不存在");
		}
		
		if(app.getStatus() != Application.AS_DENIED) {
			throw new Exception("报名表状态不是拒绝状态，不能恢复");
		}
		
		app.setNotifyTime(null);
		app.setNotify("");
		app.setStatus(Application.AS_SUBMITED);
		
		applicationDao.saveOrUpdate(app);
		
		return app;
	}
}
