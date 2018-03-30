package com.admission.dao;

import java.util.List;

import com.admission.entity.OptionItem;

public interface OptionItemDao extends AbstractDao<OptionItem> {
	public List<OptionItem> findByComKey(String parentComKey, String parentItemValue, String comKey);
}
