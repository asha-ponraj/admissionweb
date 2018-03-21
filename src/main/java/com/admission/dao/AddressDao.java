package com.admission.dao;

import com.admission.entity.Address;

public interface AddressDao extends AbstractDao<Address> {
	public void deleteByApplication(int appId);
}
