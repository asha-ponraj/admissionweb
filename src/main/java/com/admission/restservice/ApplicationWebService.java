package com.admission.restservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.admission.dto.AppData;
import com.admission.dto.AppQueryTO;
import com.admission.dto.ApplicationOverviewTo;
import com.admission.dto.CheckinTo;
import com.admission.dto.DataGridTO;
import com.admission.dto.DenyAppData;
import com.admission.dto.JsonResponse;
import com.admission.dto.NotifyAppData;
import com.admission.entity.Address;
import com.admission.entity.Application;
import com.admission.entity.FamilyMember;
import com.admission.service.ApplicationService;
import com.admission.util.AdmissionWriter;
import com.admission.util.ApplicationUtil;
import com.admission.util.BarCodeUtil;
import com.admission.util.EmptyUtil;
import com.admission.util.InputValidatorConfig;
import com.admission.util.NumberPool;
import com.admission.util.Profile;
import com.admission.util.TimeUtil;
import com.admission.web.config.WebProfile;

@Controller
@RequestMapping("/application")
public class ApplicationWebService {
	private static Logger log = Logger.getLogger(ApplicationWebService.class);

	@Autowired
	private ApplicationService applicationService;

	@RequestMapping(value="/get", method=RequestMethod.POST, headers="Accept=application/json")
	@ResponseBody 
	public JsonResponse getApplication(@RequestBody Map<String,String> paramMap, HttpSession session) {
		JsonResponse res = new JsonResponse();
                String username = paramMap.get("username");
                String password = paramMap.get("password");

		try {
			Application app = applicationService.findApplication(username, password);

			if(app != null) {
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
	
	@RequestMapping(value="/find", method=RequestMethod.POST, headers="Accept=application/json")
	@ResponseBody 
	public JsonResponse findApplication(@RequestBody AppQueryTO appQuery) {
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
	
	@RequestMapping(value="/detail", method=RequestMethod.GET, headers="Accept=application/json")
	public void getApplicationDetail(@RequestParam int id, HttpServletResponse response) {
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
					sb.append(tm.getCompany());
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
		
		response.setContentType(MediaType.TEXT_HTML_VALUE);
		response.setCharacterEncoding("UTF-8");
		
		try {
			response.getWriter().write(sb.toString());
			response.flushBuffer();
		} catch (Exception e) {
			log.debug("write output failed", e);
			response.setStatus(500);
		}
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST, headers="Accept=application/json")
	@ResponseBody 
	public JsonResponse createApplication(@RequestBody AppData appData) {
		JsonResponse res = new JsonResponse();
		Application application = null;

		try {
			application = appData.buildApplication();
		} catch (Exception e) {
			log.debug("build application failed", e);
			res.setResult(e.getMessage());
			return res;
		}
		
		Timestamp curTime = TimeUtil.getCurTime();
		if(curTime.before(Profile.getInstance().getStartApplicationTime())){
			res.setResult("报名还没有开始，请注意起止时间段");
			return res;
		} else if(curTime.after(Profile.getInstance().getEndApplicationTime())) {
			res.setResult("报名已经结束，请注意起止时间段");
			return res;
		}
	
		//户籍地址验证
		Address hkAddress = application.addressMap().get(Address.TYPE_HUKOU);
		if(InputValidatorConfig.getBoolean(InputValidatorConfig.HKADDRESS_REQUIRED)) {
			if(hkAddress == null || EmptyUtil.isEmpty(hkAddress.getContent())) {
				res.setResult("请输入完整的户籍地址");
				return res;
			}
		}
		String hkAddressValidator = InputValidatorConfig.getString(InputValidatorConfig.HKADDRESS_VALIDATOR);
		if(!EmptyUtil.isEmpty(hkAddressValidator) 
				&& hkAddress != null && !EmptyUtil.isEmpty(hkAddress.getContent()) 
				&& !Pattern.matches(hkAddressValidator, hkAddress.getContent())) {
			res.setResult(InputValidatorConfig.getString(InputValidatorConfig.HKADDRESS_VALIDATOR_TIP));
			return res;
		}
		
		//产证地址验证
		Address propertyAddress = application.addressMap().get(Address.TYPE_PROPERTY);
		if(InputValidatorConfig.getBoolean(InputValidatorConfig.PROPERTYADDRESS_REQUIRED)) {
			if(propertyAddress == null || EmptyUtil.isEmpty(propertyAddress.getContent())) {
				res.setResult("请输入完整的产证地址");
				return res;
			}
		}
		String propertyAddressValidator = InputValidatorConfig.getString(InputValidatorConfig.PROPERTYADDRESS_VALIDATOR);
		if(!EmptyUtil.isEmpty(propertyAddressValidator) 
				&& propertyAddress != null && !EmptyUtil.isEmpty(propertyAddress.getContent()) 
				&& !Pattern.matches(propertyAddressValidator, propertyAddress.getContent())) {
			res.setResult(InputValidatorConfig.getString(InputValidatorConfig.PROPERTYADDRESS_VALIDATOR_TIP));
			return res;
		}
		
		//居住地址验证
		Address residentAddress = application.addressMap().get(Address.TYPE_RESIDENCE);
		if(InputValidatorConfig.getBoolean(InputValidatorConfig.RESIDENTADDRESS_REQUIRED)) {
			if(residentAddress == null || EmptyUtil.isEmpty(residentAddress.getContent())) {
				res.setResult("请输入完整的现住地址");
				return res;
			}
		}
		String residentAddressValidator = InputValidatorConfig.getString(InputValidatorConfig.RESIDENTADDRESS_VALIDATOR);
		if(!EmptyUtil.isEmpty(residentAddressValidator) 
				&& residentAddress != null && !EmptyUtil.isEmpty(residentAddress.getContent()) 
				&& !Pattern.matches(residentAddressValidator, residentAddress.getContent())) {
			res.setResult(InputValidatorConfig.getString(InputValidatorConfig.RESIDENTADDRESS_VALIDATOR_TIP));
			return res;
		}
		
		Date birthday = application.getBirthday();
		if(birthday == null) {
			res.setResult("请输入生日");
			return res;
		} else {
			String dob = TimeUtil.dateToSqlString(birthday);
			String minDob = TimeUtil.dateToSqlString(Profile.getInstance().getMinBirthday());
			String maxDob = TimeUtil.dateToSqlString(Profile.getInstance().getMaxBirthday());
			if(dob.compareTo(minDob) < 0) {
				res.setResult("孩子的生日超过报名许可年龄");
				return res;
			}
			if(dob.compareTo(maxDob) > 0) {
				res.setResult("孩子的生日不到报名许可年龄");
				return res;
			}
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
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET, headers="Accept=application/json")
	@ResponseBody 
	public JsonResponse deleteApplication(@PathVariable("id") int id) {
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
	
	@RequestMapping(value="/accept", method=RequestMethod.GET, headers="Accept=application/json")
	@ResponseBody 
	public JsonResponse acceptApplication(@RequestParam int fromId, @RequestParam int toId) {
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
	
	@RequestMapping(value="/deny/{id}", method=RequestMethod.POST, headers="Accept=application/json")
	@ResponseBody 
	public JsonResponse denyApplication(@RequestBody DenyAppData denyAppData) {
		int id = denyAppData.getId();
		String reason = denyAppData.getReason();
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
	
	@RequestMapping(value="/reset/{id}", method=RequestMethod.GET, headers="Accept=application/json")
	@ResponseBody 
	public JsonResponse resetApplication(@PathVariable int id) {
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
	
	@RequestMapping(value="/notify", method=RequestMethod.POST, headers="Accept=application/json")
	@ResponseBody 
	public JsonResponse notifyApplication(@RequestBody NotifyAppData notifyAppData) {
		int fromId = notifyAppData.getFromId();
		int toId = notifyAppData.getToId();
		String notify = notifyAppData.getNotify();
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
	
	@RequestMapping(value="/download", method=RequestMethod.GET, produces="application/msword", headers="Accept=application/json")
	public void downloadApplication(HttpServletResponse response, HttpSession session) {
		Integer id = (Integer)session.getAttribute(WebProfile.SESSION_APPLICATIONID);

		if(id == null) {
			response.setStatus(401);
			return;
		}
		
		if(TimeUtil.getCurTime().after(Profile.getInstance().getDownloadEndTime())) {
			response.setStatus(404);
			return;
		}
		
		File appFile = new File(Profile.getApplicationPath(), "application-"+id+".doc");
		
		try {
			Application app = applicationService.findApplication(id);
			if(app == null) {
				response.setStatus(204);
				return;
			} else {
				String barcode = app.getBarcode();
				File barcodeFile = new File(Profile.getBarcodePath(), barcode + ".jpg");
				if(!barcodeFile.exists())
					BarCodeUtil.build(barcode, barcodeFile);
				
				AdmissionWriter.buildRTFDoc(app, barcodeFile, appFile);
				
				applicationService.markApplicationDownloaded(id);
				
				response.setContentType("application/msword");
				response.setContentLength((int)appFile.length());
				response.addHeader("Content-Disposition", "attachment; filename=application.doc");
				InputStream is = new FileInputStream(appFile);
				org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
			    response.flushBuffer();
			}
		} catch (Throwable t) {
			log.debug("download application", t);
			response.setStatus(500);
			return;
		}
	}
	
	@RequestMapping(value="/downloadall", method=RequestMethod.GET, produces="application/vnd.ms-excel", headers="Accept=application/json")
	public void downloadApplicationAll(HttpServletResponse response) {
		File file = new File(Profile.getExportPath(), TimeUtil.getCurTimeString()+".xls");
		FileOutputStream fos = null;
		try {
			List<Application> apps = applicationService.findAllApplication();
			HSSFWorkbook wb = ApplicationUtil.buildWorkbook(apps);
			
			fos = new FileOutputStream(file);
			wb.write(fos);
		} catch (Throwable t) {
			log.debug("generate applications file failed", t);
			response.setStatus(500);
			return;
		} finally {
			if(fos != null) {
				try {
					fos.close();
				}catch(IOException ioe){}
			}
		}
		
		try {
			response.setContentType("application/vnd.ms-excel");
			response.setContentLength((int)file.length());
			response.addHeader("Content-Disposition", "attachment; filename="+file.getName());

			FileInputStream fis = new FileInputStream(file);
			org.apache.commons.io.IOUtils.copy(fis, response.getOutputStream());
		    response.flushBuffer();
		} catch (Throwable t) {
			log.debug("download applications failed", t);
			response.setStatus(500);
		}
	}
	
	@RequestMapping(value="/resetquerypassword/{id}", method=RequestMethod.GET, headers="Accept=application/json")
	@ResponseBody 
	public JsonResponse resetApplicationPassword(@PathVariable int id) {
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
	
	@RequestMapping(value="/getpassword", method=RequestMethod.GET, headers="Accept=application/json")
	@ResponseBody 
	public JsonResponse getPassword(@RequestParam("applicationid") int id) {
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
	
	@RequestMapping(value="/findbc", method=RequestMethod.GET, headers="Accept=application/json")
	@ResponseBody 
	public JsonResponse getApplicationByBarcode(@RequestParam String barcode, @RequestParam boolean checkin) {
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
					id = Integer.parseInt(ts);
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
	
	@RequestMapping(value="/recheckin/{id}", method=RequestMethod.GET, headers="Accept=application/json")
	@ResponseBody 
	public JsonResponse recheckinApplication(@PathVariable int id) {
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
}
