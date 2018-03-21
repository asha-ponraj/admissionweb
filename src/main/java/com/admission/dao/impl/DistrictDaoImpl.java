package com.admission.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.admission.dao.BaseDaoHibernate;
import com.admission.dao.DistrictDao;
import com.admission.entity.City;
import com.admission.entity.District;

@Repository("districtDao")
public class DistrictDaoImpl extends BaseDaoHibernate<District> implements DistrictDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<District> getByCity(City city) throws Exception {
		DetachedCriteria c = DetachedCriteria.forClass(District.class).add(Restrictions.eq("city", city));
		return this.getHibernateTemplate().findByCriteria(c);
	}

}
