package com.admission.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "t_address")
@XmlRootElement
public class Address  extends BaseEntity {
	private static final long serialVersionUID = 2086040663326998265L;
	public static final int TYPE_HUKOU = 1;
	public static final int TYPE_PROPERTY = 2;
	public static final int TYPE_RESIDENCE = 3;
	
	@JsonIgnore
	private Application application;
	
	private Integer id;
	private int type;
	private String content;
	private String town;
	private String residentCouncil;
	private String postcode;
	
	public Address() {
		
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
	}

	@Column(name = "content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "town")
	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	@Column(name = "resident_council")
	public String getResidentCouncil() {
		return residentCouncil;
	}

	public void setResidentCouncil(String residentCouncil) {
		this.residentCouncil = residentCouncil;
	}

	@Column(name = "postcode")
	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
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
