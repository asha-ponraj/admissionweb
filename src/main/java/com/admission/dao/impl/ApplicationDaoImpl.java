package com.admission.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.admission.dao.ApplicationDao;
import com.admission.dao.BaseDaoHibernate;
import com.admission.dto.AppQueryTO;
import com.admission.dto.PageInfo;
import com.admission.entity.Application;
import com.admission.util.StrUtil;
import com.admission.util.TimeUtil;

@Repository("applicationDao")
public class ApplicationDaoImpl extends BaseDaoHibernate<Application> implements ApplicationDao {

	private Order getOrder(String sortName, String sortOrder) {
		//升序排列
		if(sortOrder.equalsIgnoreCase("asc")) {
			if(sortName==null)
				return Order.asc("id");
			if(sortName.equals("idNumber") || sortName.equals("name"))
				return Order.asc(sortName);
			else
				return Order.asc("id");
		}
		//降序排列
		else{
			if(sortName==null)
				return Order.desc("id");
			if(sortName.equals("idNumber") || sortName.equals("name"))
				return Order.desc(sortName);
			else
				return Order.desc("id");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Application> find(String idnumber, String name, PageInfo info) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Application.class);
		//查询条件查询
		if(idnumber != null)
			idnumber = idnumber.trim();
		if(idnumber != null && idnumber.length() > 0)
			criteria.add(Restrictions.eq("idnumber", idnumber));
		
		if(name != null)
			name = name.trim();
		if(name != null && name.length() > 0)
			criteria.add(Restrictions.eq("name", name));
		
		if(info != null){
			criteria.addOrder(getOrder(info.getSortName(),info.getSortOrder()));
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Application> find(AppQueryTO appQuery) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Application.class);
		//查询条件查询
		if(appQuery.getApplicationId() != 0) {
			criteria.add(Restrictions.eq("id", appQuery.getApplicationId()));
		}
		if(appQuery.getStatus() != 0) {
			criteria.add(Restrictions.eq("status", appQuery.getStatus()));
		}
		if(appQuery.getGender() != null && appQuery.getGender().length() > 0) {
			criteria.add(Restrictions.eq("gender", appQuery.getGender()));
		}
		if(appQuery.getGrade() != 0) {
			criteria.add(Restrictions.eq("grade", appQuery.getGrade()));
		}
		
		if(appQuery.isBlur()) {
			if(appQuery.getPidnumber() != null && appQuery.getPidnumber().length() > 0)
				criteria.add(Restrictions.like("pidNumber", StrUtil.getSQLLikeString(appQuery.getPidnumber())));
			if(appQuery.getName() != null && appQuery.getName().length() > 0)
				criteria.add(Restrictions.like("name", StrUtil.getSQLLikeString(appQuery.getName())));
			if(appQuery.getNation() != null && appQuery.getNation().length() > 0)
				criteria.add(Restrictions.like("nation", StrUtil.getSQLLikeString(appQuery.getNation())));
		} else {
			if(appQuery.getPidnumber() != null && appQuery.getPidnumber().length() > 0)
				criteria.add(Restrictions.eq("pidNumber", appQuery.getPidnumber()));
			if(appQuery.getName() != null && appQuery.getName().length() > 0)
				criteria.add(Restrictions.eq("name", appQuery.getName()));
			if(appQuery.getNation() != null && appQuery.getNation().length() > 0)
				criteria.add(Restrictions.eq("nation", appQuery.getNation()));
		}
		
		
		criteria.addOrder(getOrder(appQuery.getSortName(),appQuery.getSortOrder()));
		//分页处理
		criteria.setProjection(Projections.rowCount());
		int total = ((Long)this.getHibernateTemplate().findByCriteria(criteria).get(0)).intValue();
		appQuery.setTotal(total);
		criteria.setProjection(null);
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);

		if(appQuery.getPageSize() > 0) {
			int first = (appQuery.getPageNumber() - 1) * appQuery.getPageSize();
			return getHibernateTemplate().findByCriteria(criteria, first, appQuery.getPageSize());
		}

		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public void accept(int fromId, int toId) {
		Timestamp curTime = TimeUtil.getCurTime();
		String hql = "update Application a set a.acceptTime=?, a.status=? where a.status = ?";
		if(fromId > 0)
			hql += " and a.id >= ?";
		if(toId > 0)
			hql += " and a.id <= ?";
		
		if(fromId > 0 && toId > 0) {
			getHibernateTemplate().bulkUpdate(hql, curTime, Application.AS_ACCEPTED, Application.AS_SUBMITED, fromId, toId);
		}
		else if(fromId > 0) {
			getHibernateTemplate().bulkUpdate(hql, curTime, Application.AS_ACCEPTED, Application.AS_SUBMITED, fromId);
		} else if(toId > 0) {
			getHibernateTemplate().bulkUpdate(hql, curTime, Application.AS_ACCEPTED, Application.AS_SUBMITED, toId);
		} else {
			getHibernateTemplate().bulkUpdate(hql, curTime, Application.AS_ACCEPTED, Application.AS_SUBMITED);
		}
	}

	@Override
	public void exNotify(int fromId, int toId, String notify) {
		Timestamp curTime = TimeUtil.getCurTime();
		String hql1 = "update Application a set a.notifyTime=?, a.status=?, a.notify=? where a.status < ?";
		String hql2 = "update Application a set a.acceptTime=? where a.status = ? and a.acceptTime is null";
		
		if(fromId > 0) {
			hql1 += " and a.id >= ?";
			hql2 += " and a.id >= ?";
		}
		if(toId > 0) {
			hql1 += " and a.id <= ?";
			hql2 += " and a.id <= ?";
		}
		
		if(fromId > 0 && toId > 0) {
			getHibernateTemplate().bulkUpdate(hql1, curTime, Application.AS_NOTIFIED, notify, Application.AS_NOTIFIED, fromId, toId);
			getHibernateTemplate().bulkUpdate(hql2, curTime, Application.AS_NOTIFIED, fromId, toId);
		}
		else if(fromId > 0) {
			getHibernateTemplate().bulkUpdate(hql1, curTime, Application.AS_NOTIFIED, notify, Application.AS_NOTIFIED, fromId);
			getHibernateTemplate().bulkUpdate(hql2, curTime, Application.AS_NOTIFIED, fromId);
		} else if(toId > 0) {
			getHibernateTemplate().bulkUpdate(hql1, curTime, Application.AS_NOTIFIED, notify, Application.AS_NOTIFIED, toId);
			getHibernateTemplate().bulkUpdate(hql2, curTime, Application.AS_NOTIFIED, toId);
		} else {
			getHibernateTemplate().bulkUpdate(hql1, curTime, Application.AS_NOTIFIED, notify, Application.AS_NOTIFIED);
			getHibernateTemplate().bulkUpdate(hql2, curTime, Application.AS_NOTIFIED);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Application> find(int fromId, int toId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Application.class);
		if(fromId > 0)
			criteria.add(Restrictions.ge("id", fromId));
		if(toId > 0)
			criteria.add(Restrictions.le("id", toId));
		
		criteria.addOrder(Order.asc("id"));
		
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getIdList() {
		String hql = "select a.id from Application a order by a.id";
		List<Integer> res = getHibernateTemplate().find(hql);
		
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Application> find(int pidType, String pidNumber) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Application.class);
		criteria.add(Restrictions.eq("pidType", pidType));
		if(pidNumber != null && pidNumber.length() > 0)
			criteria.add(Restrictions.eq("pidNumber", pidNumber));
		
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@Override
	public long countByStatus(int status) {
		return querySize("select count(*) from Application a where a.status=?", status);
	}
}
