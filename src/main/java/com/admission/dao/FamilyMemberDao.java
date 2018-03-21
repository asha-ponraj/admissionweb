package com.admission.dao;

import com.admission.entity.FamilyMember;

public interface FamilyMemberDao extends AbstractDao<FamilyMember> {
	public void deleteByApplication(int appId);
}
