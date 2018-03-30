package com.admission.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import com.admission.dto.OptionTO;

@Entity
@Table(name = "t_optionitem")
@XmlRootElement
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class OptionItem extends BaseEntity {
	private static final long serialVersionUID = 2086040663326998265L;
	
	private int id;
	private String comKey;
	private String itemValue;
	private String itemText;
	private String itemSeq;
	private boolean itemSelected;
	private OptionItem parent;
	
	public static OptionTO from(OptionItem item) {
		OptionTO optionTO = new OptionTO();
		optionTO.setId(item.getItemValue());
		optionTO.setText(item.getItemText());
		optionTO.setSelected(item.isItemSelected());
		
		return optionTO;
	}
	
	public OptionItem() {
		
	}

	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	@Column(name = "id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "com_key")
	public String getComKey() {
		return comKey;
	}

	public void setComKey(String comKey) {
		this.comKey = comKey;
	}

	@Column(name = "item_value")
	public String getItemValue() {
		return itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	@Column(name = "item_text")
	public String getItemText() {
		return itemText;
	}

	public void setItemText(String itemText) {
		this.itemText = itemText;
	}

	@Column(name = "item_seq")
	public String getItemSeq() {
		return itemSeq;
	}

	public void setItemSeq(String itemSeq) {
		this.itemSeq = itemSeq;
	}

	@Column(name = "item_selected")
	public boolean isItemSelected() {
		return itemSelected;
	}

	public void setItemSelected(boolean itemSelected) {
		this.itemSelected = itemSelected;
	}

	@ManyToOne
	@JoinColumn(name="parent_id")
	public OptionItem getParent() {
		return parent;
	}

	public void setParent(OptionItem parent) {
		this.parent = parent;
	}
	
}
