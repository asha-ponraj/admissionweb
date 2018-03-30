package com.admission.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "t_familymember")
@XmlRootElement
public class FamilyMember  extends BaseEntity {
	private static final long serialVersionUID = 2086040663326998265L;
	public static final int TYPE_FATHER = 1;
	public static final int TYPE_MOTHER = 2;
	public static String typeDesc(int type) {
		switch(type) {
		case TYPE_FATHER:
			return "父亲";
		case TYPE_MOTHER:
			return "母亲";
		}
		return "未知";
	}
	
	@JsonIgnore
	private Application application;
	private Integer id;
	private int type;
	private String typeStr;
	private String name;
	private String idNumber;
	private String company;
	private String residentPermit;
	private String phone;
	private String mobile;
	
	public FamilyMember() {
		
	}

	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "type")
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
		setTypeStr(typeDesc(type));
	}

	@Transient
	public String getTypeStr() {
		return typeStr;
	}

	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "id_number")
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	@Column(name = "company")
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Column(name = "resident_permit")
	public String getResidentPermit() {
		return residentPermit;
	}

	public void setResidentPermit(String residentPermit) {
		this.residentPermit = residentPermit;
	}

	@Column(name = "phone")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "mobile")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@XmlTransient
	@ManyToOne
	@JoinColumn(name="application_id")
	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}
	
}
