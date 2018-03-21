package com.admission.entity;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.admission.util.StrUtil;
import com.admission.util.TimeUtil;

@Entity
@Table(name = "t_application")
@XmlRootElement
public class Application  extends BaseEntity {
	private static final long serialVersionUID = 2086040663326998265L;
	public static final int AS_SUBMITED = 1;
	public static final int AS_ACCEPTED = 2;
	public static final int AS_NOTIFIED = 3;
	public static final int AS_DOWNLOADED = 4;
	public static final int AS_CHECKIN = 5;
	public static final int AS_DENIED = 10;
	public static final String statusDesc(int status) {
		switch(status) {
		case AS_SUBMITED:
			return "报名已提交";
		case AS_ACCEPTED:
			return "学校已受理";
		case AS_NOTIFIED:
			return "面谈通知已发放";
		case AS_DOWNLOADED:
			return "报名表已下载";
		case AS_CHECKIN:
			return "面谈活动已签到";
		case AS_DENIED:
			return "报名被拒绝";
		}
		return "未知状态";
	}
	
	public static final int GRADE_0 = 0;
	public static final int GRADE_1 = 1;
	public static final int GRADE_2 = 2;
	public static final int GRADE_3 = 3;
	public static final String gradeDesc(int grade) {
		switch(grade) {
		case GRADE_0:
			return "托班";
		case GRADE_1:
			return "小班";
		case GRADE_2:
			return "中班";
		case GRADE_3:
			return "大班";
		}
		return "未知年级";
	}
	
	public static final int CANDIDATE_TYPE_1 = 1;
	public static final int CANDIDATE_TYPE_2 = 2;
	public static final int CANDIDATE_TYPE_3 = 3;
	public static final int CANDIDATE_TYPE_4 = 4;
	public static final String candidateTypeDesc(int type) {
		switch(type) {
		case CANDIDATE_TYPE_1:
			return "人户一致";
		case CANDIDATE_TYPE_2:
			return "户籍";
		case CANDIDATE_TYPE_3:
			return "产证";
		case CANDIDATE_TYPE_4:
			return "其他";
		}
		return "未知招生对象属性";
	}

	
	public static final int PID_TYPE_1 = 1;
	public static final int PID_TYPE_2 = 2;
	public static final int PID_TYPE_3 = 3;
	public static final int PID_TYPE_4 = 4;
	public static final int PID_TYPE_5 = 5;
	public static final String pidTypeDesc(int type) {
		switch(type) {
		case PID_TYPE_1:
			return "居民身份证";
		case PID_TYPE_2:
			return "港澳居民来往内地通行证";
		case PID_TYPE_3:
			return "台湾居民来往大陆通行证";
		case PID_TYPE_4:
			return "护照";
		case PID_TYPE_5:
			return "其他";
		}
		return "";
	}
	
	public static final int HKNATURE_1 = 1;
	public static final int HKNATURE_2 = 2;
	public static final int HKNATURE_3 = 3;
	public static final int HKNATURE_4 = 4;
	public static final int HKNATURE_5 = 5;
	public static final int HKNATURE_6 = 6;
	public static String hkNatureDesc(int hkNature) {
		switch(hkNature) {
		case HKNATURE_1:
			return "非农业家庭户口";
		case HKNATURE_2:
			return "农业家庭户口";
		case HKNATURE_3:
			return "非农业集体户口";
		case HKNATURE_4:
			return "农业集体户口";
		case HKNATURE_5:
			return "未落常住户口(袋袋户口)";
		case HKNATURE_6:
			return "其他户口";
		}
		return "";
	}
	
	public static final int HK_TYPE_1 = 1;
	public static final int HK_TYPE_2 = 2;
	public static final int HK_TYPE_3 = 3;
	public static final int HK_TYPE_4 = 4;
	public static final int HK_TYPE_5 = 5;
	public static final int HK_TYPE_6 = 6;
	public static final int HK_TYPE_7 = 7;
	public static final int HK_TYPE_8 = 8;
	public static final int HK_TYPE_9 = 9;
	public static final int HK_TYPE_10 = 10;
	public static final int HK_TYPE_11 = 11;
	public static String hkTypeDesc(int type) {
		switch(type) {
		case HK_TYPE_1:
			return "本市本区户籍";
		case HK_TYPE_2:
			return "本市外区户籍";
		case HK_TYPE_3:
			return "香港";
		case HK_TYPE_4:
			return "澳门";
		case HK_TYPE_5:
			return "台湾";
		case HK_TYPE_6:
			return "外籍";
		case HK_TYPE_7:
			return "无户口";
		case HK_TYPE_8:
			return "外省市(上海市居住证)";
		case HK_TYPE_9:
			return "外省市(海外证)";
		case HK_TYPE_10:
			return "外省市(上海市临时居住证)";
		case HK_TYPE_11:
			return "外省市(无本市居住证)";
		}
		return "";
	}
	
	public static final int PRO_NATURE_1 = 1;
	public static final int PRO_NATURE_2 = 2;
	public static final int PRO_NATURE_3 = 3;
	public static final int PRO_NATURE_4 = 4;
	public static final int PRO_NATURE_5 = 5;
	public static String propertyNatureDesc(int nature) {
		switch(nature) {
		case PRO_NATURE_1:
			return "产权房/经适房";
		case PRO_NATURE_2:
			return "公租房";
		case PRO_NATURE_3:
			return "配住廉租房";
		case PRO_NATURE_4:
			return "集体宿舍";
		case PRO_NATURE_5:
			return "其他";
		}
		return "";
	}
	
	public static final int PRO_TYPE_1 = 1;
	public static final int PRO_TYPE_2 = 2;
	public static final int PRO_TYPE_3 = 3;
	public static String propertyTypeDesc(int type) {
		switch(type) {
		case PRO_TYPE_1:
			return "本地段";
		case PRO_TYPE_2:
			return "本区";
		case PRO_TYPE_3:
			return "本市";
		}
		return "";
	}
	
	private Integer id;
	private String subbarcode;
	private String username;
	private String password;
	private int grade; //年级
	private int candidateType; //招生对象属性
	private String name; //姓名
	private String formerName; //曾用名
	private String gender; //性别
	private String jiguan; //籍贯
	private String ethnicity; //民族
	private String nation; //国别
	private Date birthday; //生日
	private String birthdayStr; //生日字符描述
	private boolean hasBirthCert; //有无出生证
	private String birthPlace; //出生地
	private boolean onlyChild; //是否独生子女
	private boolean kindergartened; //上过幼儿园
	private String nursery; //幼儿园名称
	private int pidType; //证件类型
	private String pidNumber; //证件编号
	private int hkNature; //户口性质
	private String hkNatureStr; //户口性质字符描述
	private int hkType;  //户籍类别
	private String hkTypeStr;  //户籍类别字符描述
	private Timestamp hkRegDate; //户籍登记日
	private String hkRegDateStr; //户籍登记日字符描述
	private int propertyNature; //住房性质
	private String propertyNumber; //产权证编号 
	private Timestamp propertyRegDate; //产证登记日期
	private String propertyRegDateStr; //产证登记日期字符描述
	private String propertyOther; //其他住房性质
	private int propertyType; //产证类别
	private String health; //健康状况
	private boolean allergic; //有无过敏史
	private String specificDisease; //特殊病史
	private boolean immunityCert; //计划免疫证
	private String remark; //家长需要特别说明的情况
	private Timestamp createTime;
	private String createTimeStr;
	private int status;
	private String statusStr;
	private Timestamp acceptTime;
	private String acceptTimeStr;
	private Timestamp notifyTime;
	private String notifyTimeStr;
	private String notify;
	private Timestamp downloadTime;
	private String downloadTimeStr;
	private Timestamp checkinTime;
	private String checkinTimeStr;
	private int recheckin;
	private String recheckinStr = "";
	
	private List<Address> addresses = new LinkedList<Address>();
	private List<FamilyMember> members = new LinkedList<FamilyMember>();
	private Map<Integer, Address> addressMap = new HashMap<Integer, Address>();
	private Map<Integer, FamilyMember> memberMap = new HashMap<Integer, FamilyMember>();
	
	public Application() {
		
	}
	
	public Map<Integer, Address> addressMap() {
		for(Address ta : addresses) {
			switch(ta.getType()) {
			case Address.TYPE_HUKOU:
			case Address.TYPE_RESIDENCE:
			case Address.TYPE_PROPERTY:
				addressMap.put(ta.getType(), ta);
				break;
			}
		}
		
		return addressMap;
	}
	
	public Map<Integer, FamilyMember> memberMap() {
		for(FamilyMember tf : members) {
			switch(tf.getType()) {
			case FamilyMember.TYPE_FATHER:
			case FamilyMember.TYPE_MOTHER:
				memberMap.put(tf.getType(), tf);
				break;
			}
		}
		
		return memberMap;
	}

	@Id
//	@GeneratedValue(generator="increment")
//	@GenericGenerator(name="increment", strategy="increment")
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "subbarcode")
	public String getSubbarcode() {
		return subbarcode;
	}

	public void setSubbarcode(String subbarcode) {
		this.subbarcode = subbarcode;
	}

	@Transient
	public String getBarcode() {
		if(subbarcode == null)
			return null;
		
		DecimalFormat df = new DecimalFormat("00000000");
		StringBuffer sb = new StringBuffer(subbarcode);
		sb.append(df.format(id));
		return sb.toString();
	}

	@Column(name = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = StrUtil.trimString(username);
	}

	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "grade")
	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}
	
	@Column(name = "candidate_type")
	public int getCandidateType() {
		return candidateType;
	}
	
	public void setCandidateType(int candidateType) {
		this.candidateType = candidateType;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "former_name")
	public String getFormerName() {
		return formerName;
	}

	public void setFormerName(String formerName) {
		this.formerName = formerName;
	}

	@Column(name = "gender")
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column(name = "jiguan")
	public String getJiguan() {
		return jiguan;
	}

	public void setJiguan(String jiguan) {
		this.jiguan = jiguan;
	}

	@Column(name = "ethnicity")
	public String getEthnicity() {
		return ethnicity;
	}

	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}

	@Column(name = "nation")
	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	@Column(name = "birthday")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
		if(birthday != null) {
			birthdayStr = TimeUtil.getStringDate(birthday);
		}
	}

	@Transient
	public String getBirthdayStr() {
		return birthdayStr;
	}

	public void setBirthdayStr(String birthdayStr) {
		this.birthdayStr = birthdayStr;
	}

	@Column(name = "has_birthcert")
	public boolean isHasBirthCert() {
		return hasBirthCert;
	}

	public void setHasBirthCert(boolean hasBirthCert) {
		this.hasBirthCert = hasBirthCert;
	}

	@Column(name = "birth_place")
	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	@Column(name = "only_child")
	public boolean isOnlyChild() {
		return onlyChild;
	}

	public void setOnlyChild(boolean onlyChild) {
		this.onlyChild = onlyChild;
	}

	@Column(name = "went_kindergarten")
	public boolean isKindergartened() {
		return kindergartened;
	}

	public void setKindergartened(boolean kindergartened) {
		this.kindergartened = kindergartened;
	}
	
	@Column(name = "nursery")
	public String getNursery() {
		return nursery;
	}
	
	public void setNursery(String nursery) {
		this.nursery = nursery;
	}

	@Column(name = "pid_type")
	public int getPidType() {
		return pidType;
	}

	public void setPidType(int pidType) {
		this.pidType = pidType;
	}

	@Column(name = "pid_number")
	public String getPidNumber() {
		return pidNumber;
	}

	public void setPidNumber(String pidNumber) {
		this.pidNumber = pidNumber;
	}

	@Column(name = "hk_nature")
	public int getHkNature() {
		return hkNature;
	}

	public void setHkNature(int hkNature) {
		this.hkNature = hkNature;
		setHkNatureStr(hkNatureDesc(hkNature));
	}

	@Transient
	public String getHkNatureStr() {
		return hkNatureStr;
	}

	public void setHkNatureStr(String hkNatureStr) {
		this.hkNatureStr = hkNatureStr;
	}

	@Column(name = "hk_type")
	public int getHkType() {
		return hkType;
	}

	public void setHkType(int hkType) {
		this.hkType = hkType;
		setHkTypeStr(hkTypeDesc(hkType));
	}

	@Transient
	public String getHkTypeStr() {
		return hkTypeStr;
	}

	public void setHkTypeStr(String hkTypeStr) {
		this.hkTypeStr = hkTypeStr;
	}

	@Column(name = "hk_regdate")
	public Timestamp getHkRegDate() {
		return hkRegDate;
	}

	public void setHkRegDate(Timestamp hkRegDate) {
		this.hkRegDate = hkRegDate;
		if(hkRegDate != null) {
			hkRegDateStr = TimeUtil.getStringDate(hkRegDate);
		} else {
			hkRegDateStr = "";
		}
	}

	@Transient
	public String getHkRegDateStr() {
		return hkRegDateStr;
	}

	public void setHkRegDateStr(String hkRegDateStr) {
		this.hkRegDateStr = hkRegDateStr;
	}

	@Column(name = "property_nature")
	public int getPropertyNature() {
		return propertyNature;
	}

	public void setPropertyNature(int propertyNature) {
		this.propertyNature = propertyNature;
	}

	@Column(name = "property_number")
	public String getPropertyNumber() {
		return propertyNumber;
	}

	public void setPropertyNumber(String propertyNumber) {
		this.propertyNumber = propertyNumber;
	}

	@Column(name = "property_regdate")
	public Timestamp getPropertyRegDate() {
		return propertyRegDate;
	}

	public void setPropertyRegDate(Timestamp propertyRegDate) {
		this.propertyRegDate = propertyRegDate;
		if(propertyRegDate != null) {
			propertyRegDateStr = TimeUtil.getStringDate(propertyRegDate);
		} else {
			propertyRegDateStr = "";
		}
	}

	@Transient
	public String getPropertyRegDateStr() {
		return propertyRegDateStr;
	}

	public void setPropertyRegDateStr(String propertyRegDateStr) {
		this.propertyRegDateStr = propertyRegDateStr;
	}

	@Column(name = "property_other")
	public String getPropertyOther() {
		return propertyOther;
	}

	public void setPropertyOther(String propertyOther) {
		this.propertyOther = propertyOther;
	}

	@Column(name = "property_type")
	public int getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(int propertyType) {
		this.propertyType = propertyType;
	}

	@Column(name = "health")
	public String getHealth() {
		return health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	@Column(name = "allergic")
	public boolean isAllergic() {
		return allergic;
	}

	public void setAllergic(boolean allergic) {
		this.allergic = allergic;
	}

	@Column(name = "specific_disease")
	public String getSpecificDisease() {
		return specificDisease;
	}

	public void setSpecificDisease(String specificDisease) {
		this.specificDisease = specificDisease;
	}

	@Column(name = "immunity_cert")
	public boolean isImmunityCert() {
		return immunityCert;
	}

	public void setImmunityCert(boolean immunityCert) {
		this.immunityCert = immunityCert;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "create_time")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
		if(createTime != null) {
			createTimeStr = TimeUtil.getStringTimestamp(createTime);
		}
	}

	@Transient
	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	@Column(name = "status")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
		statusStr = statusDesc(status);
	}

	@Transient
	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	@Column(name = "accept_time")
	public Timestamp getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(Timestamp acceptTime) {
		this.acceptTime = acceptTime;
		if(acceptTime != null) {
			acceptTimeStr = TimeUtil.getStringTimestamp(acceptTime);
		} else {
			acceptTimeStr = "";
		}
	}

	@Transient
	public String getAcceptTimeStr() {
		return acceptTimeStr;
	}

	public void setAcceptTimeStr(String acceptTimeStr) {
		this.acceptTimeStr = acceptTimeStr;
	}

	@Column(name = "notify_time")
	public Timestamp getNotifyTime() {
		return notifyTime;
	}

	public void setNotifyTime(Timestamp notifyTime) {
		this.notifyTime = notifyTime;
		if(notifyTime != null) {
			notifyTimeStr = TimeUtil.getStringTimestamp(notifyTime);
		} else {
			notifyTimeStr = "";
		}
	}

	@Transient
	public String getNotifyTimeStr() {
		return notifyTimeStr;
	}

	public void setNotifyTimeStr(String notifyTimeStr) {
		this.notifyTimeStr = notifyTimeStr;
	}

	@Column(name = "notify")
	public String getNotify() {
		return notify;
	}

	public void setNotify(String notify) {
		this.notify = notify;
	}

	@Column(name = "download_time")
	public Timestamp getDownloadTime() {
		return downloadTime;
	}

	public void setDownloadTime(Timestamp downloadTime) {
		this.downloadTime = downloadTime;
		if(downloadTime != null) {
			downloadTimeStr = TimeUtil.getStringTimestamp(downloadTime);
		} else {
			downloadTimeStr = "";
		}
	}

	@Transient
	public String getDownloadTimeStr() {
		return downloadTimeStr;
	}

	public void setDownloadTimeStr(String downloadTimeStr) {
		this.downloadTimeStr = downloadTimeStr;
	}

	@OneToMany(mappedBy="application", fetch=FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	@OneToMany(mappedBy="application", fetch=FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	public List<FamilyMember> getMembers() {
		return members;
	}

	public void setMembers(List<FamilyMember> members) {
		this.members = members;
	}

	@Column(name = "checkin_time")
	public Timestamp getCheckinTime() {
		return checkinTime;
	}

	public void setCheckinTime(Timestamp checkinTime) {
		this.checkinTime = checkinTime;
		if(checkinTime != null) {
			checkinTimeStr = TimeUtil.getStringTimestamp(checkinTime);
		}
	}

	@Transient
	public String getCheckinTimeStr() {
		return checkinTimeStr;
	}

	public void setCheckinTimeStr(String checkinTimeStr) {
		this.checkinTimeStr = checkinTimeStr;
	}

	@Column(name = "recheckin")
	public int getRecheckin() {
		return recheckin;
	}

	public void setRecheckin(int recheckin) {
		this.recheckin = recheckin;
		setRecheckinStr(recheckin != 0 ? "重复" : "");
	}

	@Transient
	public String getRecheckinStr() {
		return recheckinStr;
	}

	public void setRecheckinStr(String recheckinStr) {
		this.recheckinStr = recheckinStr;
	}
}
