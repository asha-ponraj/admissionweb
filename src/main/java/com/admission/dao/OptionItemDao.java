package com.admission.dao;

import java.util.List;

import com.admission.entity.OptionItem;

public interface OptionItemDao extends AbstractDao<OptionItem> {
	public OptionItem findByComKeyAndItemValue(String comKey, String itemValue);
	public List<OptionItem> findByComKey(String parentComKey, String parentItemValue, String comKey);
	public List<OptionItem> findByParent(Integer parentId);
}
