package com.admission.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.admission.dao.BaseDaoHibernate;
import com.admission.dao.CityDao;
import com.admission.entity.City;
import com.admission.entity.Province;

@Repository("cityDao")
public class CityDaoImpl extends BaseDaoHibernate<City> implements CityDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<City> getByProvince(Province province) throws Exception {
		DetachedCriteria c = DetachedCriteria.forClass(City.class).add(Restrictions.eq("province", province));
		return this.getHibernateTemplate().findByCriteria(c);
	}

}
