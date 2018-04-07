package com.admission.dto;

import com.admission.entity.OptionItem;

public class OptionItemTo {
	private int id;
	private int parentId;
	private String comKey;
	private String itemValue;
	private String itemText;
	private String itemSeq;
	private boolean itemSelected;
	private String validator;
	private String state;
	private int total;
	
	public OptionItemTo() {
		
	}
	
	public static OptionItemTo from(OptionItem item) {
		OptionItemTo itemTo = new OptionItemTo();
		itemTo.setId(item.getId());
		itemTo.setParentId(item.getParent() == null ? 0 : item.getParent().getId());
		itemTo.setComKey(item.getComKey());
		itemTo.setItemValue(item.getItemValue());
		itemTo.setItemText(item.getItemText());
		itemTo.setItemSeq(item.getItemSeq());
		itemTo.setItemSelected(item.isItemSelected());
		itemTo.setValidator(item.getValidator());
		itemTo.setState("closed");
		itemTo.setTotal(0);
		
		return itemTo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getComKey() {
		return comKey;
	}

	public void setComKey(String comKey) {
		this.comKey = comKey;
	}

	public String getItemValue() {
		return itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public String getItemText() {
		return itemText;
	}

	public void setItemText(String itemText) {
		this.itemText = itemText;
	}

	public String getItemSeq() {
		return itemSeq;
	}

	public void setItemSeq(String itemSeq) {
		this.itemSeq = itemSeq;
	}

	public boolean isItemSelected() {
		return itemSelected;
	}

	public void setItemSelected(boolean itemSelected) {
		this.itemSelected = itemSelected;
	}

	public String getValidator() {
		return validator;
	}

	public void setValidator(String validator) {
		this.validator = validator;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
	
}
