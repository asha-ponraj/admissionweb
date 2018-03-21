package com.admission.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OptionTO {
	private String id;
	private String text;
	
	public OptionTO() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
}
