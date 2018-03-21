package com.admission.dao.impl;

import java.util.List;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.admission.dao.BaseDaoHibernate;
import com.admission.dao.NewsDao;
import com.admission.dto.PageInfo;
import com.admission.entity.News;

@Repository("newsDao")
public class NewsDaoImpl extends BaseDaoHibernate<News> implements NewsDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<News> findTop() {
		DetachedCriteria criteria = DetachedCriteria.forClass(News.class);
		criteria.add(Restrictions.eq("top", true));
		criteria.addOrder(Order.desc("topTime"));
		
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<News> findAll(PageInfo info) {
		DetachedCriteria criteria = DetachedCriteria.forClass(News.class);

		criteria.addOrder(Order.desc("top")).addOrder(Order.desc("seq"));
		
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
