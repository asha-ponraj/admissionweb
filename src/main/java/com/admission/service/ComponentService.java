package com.admission.service;

import java.util.List;

import com.admission.entity.OptionItem;

public interface ComponentService {
	public List<OptionItem> findOptionItemByComKey(String parentComKey, String parentItemValue, String comKey) throws Exception;
}
