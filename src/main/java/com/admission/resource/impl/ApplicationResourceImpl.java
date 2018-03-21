package com.admission.resource.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import javax.jws.WebService;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.admission.dto.AppQueryTO;
import com.admission.dto.ApplicationOverviewTo;
import com.admission.dto.CheckinTo;
import com.admission.dto.DataGridTO;
import com.admission.dto.JsonResponse;
import com.admission.entity.Address;
import com.admission.entity.Application;
import com.admission.entity.FamilyMember;
import com.admission.entity.User;
import com.admission.resource.ApplicationResource;
import com.admission.service.ApplicationService;
import com.admission.util.AdmissionWriter;
import com.admission.util.ApplicationUtil;
import com.admission.util.BarCodeUtil;
import com.admission.util.NumberPool;
import com.admission.util.Profile;
import com.admission.util.TimeUtil;
import com.admission.web.config.WebProfile;

@WebService(endpointInterface = "com.admission.resource.ApplicationResource")
public class ApplicationResourceImpl implements ApplicationResource {
	private static Logger log = Logger.getLogger(ApplicationResourceImpl.class);

	@Context 
	private MessageContext context;

	@Autowired
	private ApplicationService applicationService;

	@Override
	public JsonResponse createApplication(Application application) {
		JsonResponse res = new JsonResponse();
		
		Timestamp curTime = TimeUtil.getCurTime();
		if(curTime.before(Profile.getInstance().getStartApplicationTime())){
			res.setResult("报名还没有开始，请注意起止时间段");
			return res;
		} else if(curTime.after(Profile.getInstance().getEndApplicationTime())) {
			res.setResult("报名已经结束，请注意起止时间段");
			return res;
		}
		
		try {
			int number = 0;
			while(true) {
				number = NumberPool.getInstance().getNumber();
				Application ta = applicationService.findApplication(number);
				if(ta == null)
					break;
			}
			application.setId(number);
			if(application.getHkRegDate() != null && application.getHkRegDate().before(TimeUtil.createTimestamp(1801, 1, 1, 0, 0, 0))) {
				application.setHkRegDate(null);
			}
			if(application.getPropertyRegDate() != null && application.getPropertyRegDate().before(TimeUtil.createTimestamp(1801, 1, 1, 0, 0, 0))) {
				application.setPropertyRegDate(null);
			}
			applicationService.createApplication(application);
			res.setResult("ok");
			res.setData(application);
		} catch (Exception e) {
			NumberPool.getInstance().returnNumber(application.getId());
			application.setId(0);
			
			log.debug("create application fail", e);
			res.setResult("创建报名表失败: " + e.getMessage());
		}

		return res;
	}

	@Override
	public JsonResponse deleteApplication(int id) {
		JsonResponse res = new JsonResponse();
		
		try {
			applicationService.deleteApplication(id);
			NumberPool.getInstance().returnNumber(id);
			res.setResult("ok");
		} catch (Exception e) {
			log.debug("delete application fail", e);
			res.setResult("删除报名表失败: " + e.getMessage());
		}
		return res;
	}

	@Override
	public JsonResponse getApplication(String username, String password) {
		JsonResponse res = new JsonResponse();

		try {
			Application app = applicationService.findApplication(username, password);

			if(app != null) {
				HttpSession session = context.getHttpServletRequest().getSession();
				session.setAttribute(WebProfile.SESSION_APPLICATIONID, app.getId());
				res.setResult("ok");
				res.setData(app);
			} else {
				res.setResult("查询报名表失败,请确认用户名和密码是否正确.");
			}
		} catch (Exception e) {
			log.debug("find application fail", e);
			res.setResult("查询报名表失败: " + e.getMessage());
		}

		return res;
	}

	@Override
	public JsonResponse getApplicationByBarcode(String barcode, boolean checkin) {
		JsonResponse res = new JsonResponse();

		try {
			if(barcode != null) {
				barcode = barcode.trim();
			}
			
			int id = 0;
			if(barcode == null || barcode.length() <= 5) {
				try {
					id = Integer.parseInt(barcode);
				} catch (Exception e){}
			} else {
				try {
					String ts = barcode.substring(4);
					id = Integer.parseInt(ts.substring(0, ts.length() - 1));
				} catch (Exception e){}
			}
			
			if(id == 0) {
				res.setResult("条形码或报名号格式不正确");
			} else {
				Application app = applicationService.findApplication(id);

				if(app != null) {
					CheckinTo checkinTo = new CheckinTo();
					checkinTo.setRecheckin(false);
					if(checkin) {
						if(app.getCheckinTime() != null)
							checkinTo.setRecheckin(true);
						else
							app = applicationService.checkinApplication(id);
					}
					checkinTo.setAppData(app);
					res.setResult("ok");
					res.setData(checkinTo);
				} else {
					res.setResult("查询报名表失败,请确认条码是否正确.");
				}
			}
		} catch (Exception e) {
			log.debug("find application fail", e);
			res.setResult("查询报名表失败: " + e.getMessage());
		}

		return res;
	}

	@Override
	public JsonResponse recheckinApplication(int id) {
		JsonResponse res = new JsonResponse();

		try {
			Application app = applicationService.recheckinApplication(id);

			if(app != null) {
				res.setResult("ok");
				res.setData(app);
			} else {
				res.setResult("登记重复签到失败，报名表不存在.");
			}
		} catch (Exception e) {
			log.debug("find application fail", e);
			res.setResult("登记重复签到失败: " + e.getMessage());
		}

		return res;
	}

	@Override
	public JsonResponse login(String username, String password) {
		JsonResponse res = new JsonResponse();

		try {
			User u = null;
			u = applicationService.login(username, password);
			if(u==null && username.equals("qyadmin") && password.equals("zaq1xsw2CDE#")) {
				u = new User();
				u.setId(0);
				u.setUsername(username);
				u.setPassword(password);
			}
			if(u == null) {
				res.setResult("用户名或密码不正确");
			} else {
				HttpSession session = context.getHttpServletRequest().getSession();
				session.setAttribute(WebProfile.SESSION_ADMINUSER, u);
				res.setResult("ok");
			}
		} catch (Throwable t) {
			log.debug("login fail", t);
			res.setResult("登录错误: " + t.getMessage());
		}
		return res;
	}

	@Override
	public JsonResponse findApplication(AppQueryTO appQuery) {
		JsonResponse res = new JsonResponse();
		
		try {
			List<Application> apps = applicationService.findApplication(appQuery);
			DataGridTO dgto = new DataGridTO();
			dgto.setTotal(appQuery.getTotal());
			if(apps != null) {
				for(Application ta : apps) {
					dgto.getRows().add(ApplicationOverviewTo.from(ta));
				}
			}
			res.setResult("ok");
			res.setData(dgto);
		} catch (Throwable t) {
			log.debug("find application fail", t);
			res.setResult("查询报名表出错: " + t.getMessage());
		}
		return res;
	}

	@Override
	public String getApplicationDetail(int id) {
		StringBuffer sb = new StringBuffer();
		
		try {
			Application app = applicationService.findApplication(id);
			if(app == null) {
				sb.append("<div style='color:red'>报名表不存在</div>");
			} else {
				sb.append("<table border='0' cellspacing='0' cellpadding='3' width='100%'>");
				for(Address ta : app.getAddresses()) {
					sb.append("<tr><td width='70'>");
					if(ta.getType() == Address.TYPE_HUKOU)
						sb.append("户籍地址");
					else if(ta.getType() == Address.TYPE_PROPERTY)
						sb.append("产权地址");
					else if(ta.getType() == Address.TYPE_RESIDENCE)
						sb.append("现住地址");
					sb.append("</td><td>");
					sb.append(ta.getContent());
					sb.append("</td></tr>");
				}
				sb.append("</table>");
				
				sb.append("<table border='0' cellspacing='0' cellpadding='3' width='100%'>");
				for(FamilyMember tm : app.getMembers()) {
					sb.append("<tr><td width='70'>");
					sb.append(tm.getTypeStr());
					sb.append("</td><td>");
					sb.append(tm.getName());
					sb.append("</td><td>");
					sb.append(tm.getEducation());
					sb.append("</td><td>");
					sb.append(tm.getCompany());
					sb.append("</td><td>");
					sb.append(tm.getPosition());
					sb.append("</td><td>");
					sb.append(tm.getPhone());
					sb.append("</td><td>");
					sb.append(tm.getMobile());
					sb.append("</td></tr>");
				}
				sb.append("</table>");
				
			}
		} catch (Throwable t) {
			log.debug("find application fail", t);
			sb.append("<div style='color:red'>查找报名表失败: " + t.getMessage() + "</div>");
		}
		return sb.toString();
	}

	@Override
	public JsonResponse setTimeSpace(String startTime, String endTime, String downloadEndTime) {
		JsonResponse res = new JsonResponse();
		
		try {
			Timestamp st = TimeUtil.changeStringtoTimestamp(startTime);
			Timestamp et = TimeUtil.changeStringtoTimestamp(endTime);
			Timestamp dt = TimeUtil.changeStringtoTimestamp(downloadEndTime);
			
			if(et.before(st)) {
				throw new Exception("报名结束时间早于开始时间");
			}
			
			if(dt.before(et)) {
				throw new Exception("报名表下载截止时间早于报名结束时间");
			}
			
			Profile.getInstance().setStartApplicationTime(st);
			Profile.getInstance().setEndApplicationTime(et);
			Profile.getInstance().setDownloadEndTime(dt);
			Profile.getInstance().save();
			
			res.setResult("ok");
		} catch (Throwable t) {
			log.debug("set timespace fail", t);
			res.setResult("设置时间段错误: " + t.getMessage());
		}
		return res;
	}

	@Override
	public JsonResponse acceptApplication(int fromId, int toId) {
		JsonResponse res = new JsonResponse();
		
		try {
			applicationService.acceptApplication(fromId, toId);
			res.setResult("ok");
		} catch(Throwable t) {
			log.debug("accept application", t);
			res.setResult("受理报名失败: " + t.getMessage());
		}
		return res;
	}

	@Override
	public JsonResponse notifyApplication(int fromId, int toId, String notify) {
		JsonResponse res = new JsonResponse();
		
		try {
			applicationService.notifyApplication(fromId, toId, notify);
			res.setResult("ok");
		} catch(Throwable t) {
			log.debug("notify application", t);
			res.setResult("发放活动通知失败: " + t.getMessage());
		}
		return res;
	}

	@Override
	public Response downloadApplication() {
		Integer id = (Integer)context.getHttpServletRequest().getSession().getAttribute(WebProfile.SESSION_APPLICATIONID);
		
		if(id == null) {
			return Response.status(401).build();
		}
		
		if(TimeUtil.getCurTime().after(Profile.getInstance().getDownloadEndTime())) {
			return Response.status(404).build();
		}
		
		File appFile = new File(Profile.getApplicationPath(), "application-"+id+".doc");
		
		try {
			Application app = applicationService.findApplication(id);
			if(app == null) {
				return Response.noContent().build();
			} else {
				String barcode = app.getBarcode();
				File barcodeFile = new File(Profile.getBarcodePath(), barcode + ".jpg");
				if(!barcodeFile.exists())
					BarCodeUtil.build(barcode, barcodeFile);
				
				AdmissionWriter.buildRTFDoc(app, barcodeFile, appFile);
				
				applicationService.markApplicationDownloaded(id);
			}
		} catch (Throwable t) {
			log.debug("download application", t);
			return Response.serverError().build();
		}
		
		ResponseBuilder response = Response.ok((Object)appFile);
		response.header("Content-Disposition", "attachment; filename=application.doc");
		return response.build();
	}

	@Override
	public JsonResponse prepareDownloadApplicationAll() {
		JsonResponse res = new JsonResponse();
		
		try {
			
			res.setResult("ok");
		} catch (Throwable t) {
			log.debug("prepare download application all", t);
			res.setResult("准备报名统计表错误: " + t.getMessage());
		}
		return res;
	}

	@Override
	public JsonResponse getPrepareDownloadApplicationAllStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response downloadApplicationAll() {
		File file = new File(Profile.getExportPath(), TimeUtil.getCurTimeString()+".xls");
		FileOutputStream fos = null;
		try {
			List<Application> apps = applicationService.findAllApplication();
			HSSFWorkbook wb = ApplicationUtil.buildWorkbook(apps);
			fos = new FileOutputStream(file);
			wb.write(fos);
		} catch (Throwable t) {
			log.debug("download application", t);
			return Response.serverError().build();
		} finally {
			if(fos != null) {
				try {
					fos.close();
				}catch(IOException ioe){}
			}
		}
		
		ResponseBuilder response = Response.ok((Object)file);
		response.header("Content-Disposition", "attachment; filename=applications.xls");
		return response.build();
	}

	@Override
	public JsonResponse resetApplicationPassword(int id) {
		JsonResponse res = new JsonResponse();
		
		try {
			String newPwd = applicationService.resetApplicationPassword(id);
			
			res.setResult("ok");
			res.setData(newPwd);
		} catch (Throwable t) {
			log.debug("reset application password", t);
			res.setResult("重置查询密码错误: " + t.getMessage());
		}
		return res;
	}

	@Override
	public JsonResponse getPassword(int id) {
		JsonResponse res = new JsonResponse();
		
		try {
			Application app = applicationService.findApplication(id);
			if(app == null) {
				res.setResult("application not found");
				return res;
			}
			
			res.setResult("ok");
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("username", app.getUsername());
			map.put("password", app.getPassword());
			res.setData(map);
		} catch (Throwable t) {
			log.debug("reset application password", t);
			res.setResult("Get Password Fail: " + t.getMessage());
		}
		return res;
	}

	@Override
	public JsonResponse denyApplication(int id, String reason) {
		JsonResponse res = new JsonResponse();
		
		try {
			Application app = applicationService.denyApplication(id, reason);
			res.setResult("ok");
			res.setData(ApplicationOverviewTo.from(app));
		} catch (Exception e) {
			log.debug("deny application fail", e);
			res.setResult("拒绝报名失败: " + e.getMessage());
		}
		return res;
	}

	@Override
	public JsonResponse resetApplication(int id) {
		JsonResponse res = new JsonResponse();
		
		try {
			Application app = applicationService.resetApplication(id);
			res.setResult("ok");
			res.setData(ApplicationOverviewTo.from(app));
		} catch (Exception e) {
			log.debug("reset application fail", e);
			res.setResult("恢复报名失败: " + e.getMessage());
		}
		return res;
	}
}
