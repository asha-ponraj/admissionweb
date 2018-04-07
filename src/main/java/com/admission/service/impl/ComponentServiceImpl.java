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
//	private static final Logger logger = Logger.getLogger(ComponentServiceImpl.class);

	@Autowired
	private OptionItemDao optionItemDao;
	
	@Override
	public OptionItem createOptionItem(OptionItem optionItem) throws Exception {
		optionItem.setId(null);
		if(optionItem.getParentId() == 0) {
			optionItem.setParent(null);
		} else {
			OptionItem parentItem = optionItemDao.findById(optionItem.getParentId());
			if(parentItem == null) {
				throw new Exception("Parent optionitem does not exist");
			}
			optionItem.setParent(parentItem);
		}
		
		optionItemDao.save(optionItem);
		
		return optionItem;
	}
	
	@Override
	public OptionItem updateOptionItem(OptionItem optionItem) throws Exception {
		if(optionItem.getId() == null) {
			throw new Exception("Update optionitem with unknown id");
		}
		
		OptionItem oldItem = optionItemDao.findById(optionItem.getId());
		if(oldItem == null) {
			throw new Exception("Optionitem does not exist");
		}
		
		oldItem.setComKey(optionItem.getComKey());
		oldItem.setItemSelected(optionItem.isItemSelected());
		oldItem.setItemSeq(optionItem.getItemSeq());
		oldItem.setItemText(optionItem.getItemText());
		oldItem.setItemValue(optionItem.getItemValue());
		oldItem.setValidator(optionItem.getValidator());
		
		optionItemDao.save(oldItem);
		
		return optionItem;
	}
	
	@Override
	public void deleteOptionItem(int id) throws Exception {
		OptionItem item = optionItemDao.findById(id);
		if(item == null) {
			throw new Exception("Optionitem does not exist");
		}
		
		optionItemDao.delete(item);
	}
	
	@Override
	public OptionItem getOptionItem(String comKey, String itemValue) {
		return optionItemDao.findByComKeyAndItemValue(comKey, itemValue);
	}
	
	@Override
	public List<OptionItem> findOptionItemByComKey(String parentComKey, String parentItemValue, String comKey) throws Exception {
		return optionItemDao.findByComKey(parentComKey, parentItemValue, comKey);
	}
	
	@Override
	public List<OptionItem> findOptionItemsByParent(Integer parentId) throws Exception {
		return optionItemDao.findByParent(parentId);
	}
}
