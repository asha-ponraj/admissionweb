package com.admission.dao.impl;

import java.util.List;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

import com.admission.dao.BaseDaoHibernate;
import com.admission.dao.ParameterDao;
import com.admission.dto.PageInfo;
import com.admission.entity.Parameter;

@Repository("parameterDao")
public class ParameterDaoImpl extends BaseDaoHibernate<Parameter> implements ParameterDao {

	@Override
	public Parameter findByName(String name) {
		return this.findByProperty("name", name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Parameter> findAll(PageInfo info) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Parameter.class);

		criteria.addOrder(Order.asc("name"));
		
		if(info != null){
			//分页处理
			criteria.setProjection(Projections.rowCount());
			int total = ((Long)this.getHibernateTemplate().findByCriteria(criteria).get(0)).intValue();
			info.setTotal(total);
			criteria.setProjection(null);
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);

			if(info.getPageSize() > 0) {
				int first = (info.getPageNumber() - 1) * info.getPageSize();
				return getHibernateTemplate().findByCriteria(criteria, first, info.getPageSize());
			}
		}
		return getHibernateTemplate().findByCriteria(criteria);
	}

}
