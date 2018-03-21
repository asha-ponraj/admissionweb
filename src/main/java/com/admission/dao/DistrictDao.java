package com.admission.dao;

import java.util.List;

import com.admission.entity.City;
import com.admission.entity.District;

public interface DistrictDao extends AbstractDao<District> {
	public List<District> getByCity(City city) throws Exception;
}
