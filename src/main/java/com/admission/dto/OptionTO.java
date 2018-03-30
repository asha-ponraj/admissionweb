package com.admission.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OptionTO {
	private String id;
	private String text;
	private boolean selected;
	
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

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	
}
