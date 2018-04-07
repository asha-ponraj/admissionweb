package com.admission.service;

import java.util.List;

import com.admission.entity.OptionItem;

public interface ComponentService {
	public OptionItem createOptionItem(OptionItem optionItem) throws Exception;
	
	public OptionItem updateOptionItem(OptionItem optionItem) throws Exception;
	
	public void deleteOptionItem(int id) throws Exception;
	
	public OptionItem getOptionItem(String comKey, String itemValue) throws Exception;
	
	public List<OptionItem> findOptionItemByComKey(String parentComKey, String parentItemValue, String comKey) throws Exception;
	
	public List<OptionItem> findOptionItemsByParent(Integer parentId) throws Exception;
}
