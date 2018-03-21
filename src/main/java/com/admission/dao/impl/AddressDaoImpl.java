package com.admission.dao.impl;

import org.springframework.stereotype.Repository;

import com.admission.dao.AddressDao;
import com.admission.dao.BaseDaoHibernate;
import com.admission.entity.Address;

@Repository("addressDao")
public class AddressDaoImpl extends BaseDaoHibernate<Address> implements AddressDao {

	@Override
	public void deleteByApplication(int appId) {
		getHibernateTemplate().bulkUpdate("delete from Address a where a.application.id = " + appId);
	}

}
