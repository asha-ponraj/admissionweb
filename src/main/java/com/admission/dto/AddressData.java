package com.admission.dto;

import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlRootElement;

import com.admission.entity.Address;
import com.admission.entity.OptionItem;
import com.admission.util.ComponentMode;
import com.admission.util.EmptyUtil;
import com.admission.util.ServiceAccess;

@XmlRootElement
public class AddressData {
	private String type;
	private String content;
	private String town;
	private String residentCouncil;
	private String postcode;
	
	private String hkComKey;
	private String hkItemValue;
	private String hkAddressNo;
	private String hkAddressRoom;
	
	public AddressData() {
		
	}
	
	public Address buildAddress() throws Exception {
		Address add = new Address();
		
		try {
			add.setType(Integer.parseInt(type));
		} catch(Exception e){}
		
		if(add.getType() == Address.TYPE_HUKOU) {
			if(ComponentMode.getHkAddressMode() == ComponentMode.MODE_SELECT) {

				OptionItem item = ServiceAccess.getInstance().getComponentService().getOptionItem(hkComKey, hkItemValue);
				
				StringBuilder sb = new StringBuilder();
				OptionItem ti = item;
				while(ti != null) {
					if(sb.length() > 0) {
						sb.insert(1, " ");
					}
					sb.insert(0, ti.getItemText());
					
					ti = ti.getParent();
				}
				String validator = item.getValidator();
				if(!EmptyUtil.isEmpty(validator)) {
					if(!Pattern.matches(validator, hkAddressNo)) {
						throw new Exception("户籍地址号码不在招生范围");
					}
				}
				sb.append(hkAddressNo).append("号");
				sb.append(hkAddressRoom);
				add.setContent(sb.toString());
			} else {
				add.setContent(content);
			}
		} else {
			add.setContent(content);
		}
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

	public String getHkComKey() {
		return hkComKey;
	}

	public void setHkComKey(String hkComKey) {
		this.hkComKey = hkComKey;
	}

	public String getHkItemValue() {
		return hkItemValue;
	}

	public void setHkItemValue(String hkItemValue) {
		this.hkItemValue = hkItemValue;
	}

	public String getHkAddressNo() {
		return hkAddressNo;
	}

	public void setHkAddressNo(String hkAddressNo) {
		this.hkAddressNo = hkAddressNo;
	}

	public String getHkAddressRoom() {
		return hkAddressRoom;
	}

	public void setHkAddressRoom(String hkAddressRoom) {
		this.hkAddressRoom = hkAddressRoom;
	}
}
