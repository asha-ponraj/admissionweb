package com.admission.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.admission.dao.OptionItemDao;
import com.admission.entity.OptionItem;
import com.admission.service.ComponentService;

@Transactional(propagation=Propagation.REQUIRED)
@Service("componentService")
public class ComponentServiceImpl implements ComponentService {

	@Autowired
	private OptionItemDao optionItemDao;
	
	public List<OptionItem> findOptionItemByComKey(String parentComKey, String parentItemValue, String comKey) throws Exception {
		return optionItemDao.findByComKey(parentComKey, parentItemValue, comKey);
	}
}
