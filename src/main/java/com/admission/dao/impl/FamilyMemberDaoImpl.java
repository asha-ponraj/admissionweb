package com.admission.dao.impl;

import org.springframework.stereotype.Repository;

import com.admission.dao.BaseDaoHibernate;
import com.admission.dao.FamilyMemberDao;
import com.admission.entity.FamilyMember;

@Repository("familyMemberDao")
public class FamilyMemberDaoImpl extends BaseDaoHibernate<FamilyMember> implements FamilyMemberDao {

	@Override
	public void deleteByApplication(int appId) {
		getHibernateTemplate().bulkUpdate("delete from FamilyMember m where m.application.id = " + appId);
	}

}
