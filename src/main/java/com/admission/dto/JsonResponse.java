package com.admission.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JsonResponse {
	private String result;
	private Object data;
	
	public JsonResponse(String result, Object data) {
		this.result = result;
		this.data = data;
	}
	
	public JsonResponse() {
		
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
