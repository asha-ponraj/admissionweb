package com.admission.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.admission.dao.BaseDaoHibernate;
import com.admission.dao.OptionItemDao;
import com.admission.entity.OptionItem;
import com.admission.util.EmptyUtil;

@Repository("optionItemDao")
public class OptionItemDaoImpl extends BaseDaoHibernate<OptionItem> implements OptionItemDao {
	
	@SuppressWarnings("unchecked")
	@Override
	public OptionItem findByComKeyAndItemValue(String comKey, String itemValue) {
		DetachedCriteria criteria = DetachedCriteria.forClass(OptionItem.class);

		criteria.add(Restrictions.eq("comKey", comKey));
		criteria.add(Restrictions.eq("itemValue", itemValue));
		
		List<OptionItem> items = getHibernateTemplate().findByCriteria(criteria);
		if(items != null && items.size() > 0)
			return items.get(0);
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OptionItem> findByComKey(String parentComKey, String parentItemValue, String comKey) {
		DetachedCriteria criteria = DetachedCriteria.forClass(OptionItem.class);
		if(EmptyUtil.isEmpty(parentComKey) || EmptyUtil.isEmpty(parentItemValue)) {
			criteria.add(Restrictions.isNull("parent"));
		} else {
			criteria.createAlias("parent", "parentcom");
			criteria.add(Restrictions.eq("parentcom.comKey", parentComKey));
			criteria.add(Restrictions.eq("parentcom.itemValue", parentItemValue));
		}
		criteria.add(Restrictions.eq("comKey", comKey));
		criteria.addOrder(Order.asc("itemSeq"));
		criteria.addOrder(Order.asc("itemValue"));
		
		return getHibernateTemplate().findByCriteria(criteria);
	}
}
