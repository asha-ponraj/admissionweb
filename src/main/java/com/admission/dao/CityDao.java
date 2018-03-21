package com.admission.dao;

import java.util.List;

import com.admission.entity.City;
import com.admission.entity.Province;

public interface CityDao extends AbstractDao<City> {
	public List<City> getByProvince(Province province) throws Exception;
}
