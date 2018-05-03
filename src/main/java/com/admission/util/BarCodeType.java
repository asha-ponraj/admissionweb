package com.admission.util;

public enum BarCodeType {
	UPCA("upc_a", 0, 11), 
	EAN13("ean_13", 0, 12), 
	CODE39("code_39", 0, 12),
	CODE128("code_128", 0, 18);
	
	public static final String KEY_NAME = "sys.barcode.type";
	
	private String name;
	private int codeStartPos;
	private int codeLength;
	
	BarCodeType(String name, int codeStartPos, int codeLength) {
		this.name = name;
		this.codeStartPos = codeStartPos;
		this.codeLength = codeLength;
	}

	public String getName() {
		return name;
	}

	public int getCodeStartPos() {
		return codeStartPos;
	}

	public int getCodeLength() {
		return codeLength;
	}
	
	public static BarCodeType fromName(String name) {
		for(BarCodeType bct : BarCodeType.values()) {
			if(bct.getName().equalsIgnoreCase(name))
				return bct;
		}
		
		return EAN13;
	}
}
