package com.admission.dao.impl;

import org.springframework.stereotype.Repository;

import com.admission.dao.BaseDaoHibernate;
import com.admission.dao.ProvinceDao;
import com.admission.entity.Province;

@Repository("provinceDao")
public class ProvinceDaoImpl extends BaseDaoHibernate<Province> implements ProvinceDao {

}
