package com.admission.service;

import java.io.InputStream;
import java.util.List;

import com.admission.entity.City;
import com.admission.entity.District;
import com.admission.entity.Province;

public interface RegionalismService {
	public void updateRegionalism(InputStream excelInputStream) throws Exception;
	
	public List<Province> getProvinceList();
	
	public List<City> getCityListByProvince(int provinceId) throws Exception;
	
	public List<District> getDistrictListByCity(int cityId) throws Exception;
}
