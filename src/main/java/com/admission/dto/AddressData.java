package com.admission.dto;

import javax.xml.bind.annotation.XmlRootElement;

import com.admission.entity.Address;

@XmlRootElement
public class AddressData {
	private String type;
	private String content;
	private String town;
	private String residentCouncil;
	private String postcode;
	
	public AddressData() {
		
	}
	
	public Address buildAddress() {
		Address add = new Address();
		
		try {
			add.setType(Integer.parseInt(type));
		} catch(Exception e){}
		add.setContent(content);
		add.setTown(town);
		add.setResidentCouncil(residentCouncil);
		add.setPostcode(postcode);
		
		return add;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getResidentCouncil() {
		return residentCouncil;
	}

	public void setResidentCouncil(String residentCouncil) {
		this.residentCouncil = residentCouncil;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
}
