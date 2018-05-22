package com.admission.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.admission.entity.BaseEntity;
import com.admission.entity.Pagination;

public abstract class BaseDaoHibernate<E extends BaseEntity> extends
		HibernateDaoSupport implements AbstractDao<E> {

	protected static final String COUNT_STR = "select count(*) ";
	protected static final String COUNT_ID = "select count(id) ";
	protected static final int PAGE_SIZE = 20;
	protected final Class<E> clazz;

	protected boolean cacheable = false;

	public boolean isCacheable() {

		return cacheable;
	}

	protected BaseDaoHibernate() {

		this(false);
	}

	@SuppressWarnings("unchecked")
	protected BaseDaoHibernate(boolean cacheable) {

		clazz = (Class<E>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		this.cacheable = cacheable;
	}

	protected BaseDaoHibernate(Class<E> clazz) {

		this(clazz, false);
	}

	protected BaseDaoHibernate(Class<E> clazz, boolean cacheable) {

		this.clazz = clazz;
		if (clazz == null)
			throw new IllegalArgumentException("Invalidate clazz.");
		this.cacheable = cacheable;
	}

	@Override
	protected MyHibernateTemplate createHibernateTemplate(
			SessionFactory sessionFactory) {

		MyHibernateTemplate template = new MyHibernateTemplate(sessionFactory);
		template.setCacheQueries(isCacheable());
		return template;
	}

	public MyHibernateTemplate getMyHibernateTemplate() {

		return (MyHibernateTemplate) super.getHibernateTemplate();
	}

	public Serializable save(E obj) {

		if (obj == null)
			return null;
		return getHibernateTemplate().save(obj);
	}

	public void saveOrUpdate(E obj) {

		if (obj == null)
			return;
		getHibernateTemplate().saveOrUpdate(obj);
	}

	public void saveOrUpdateAll(Collection<E> entities) {

		getHibernateTemplate().saveOrUpdateAll(entities);
	}

	public E findById(Serializable id) {

		return (id == null) ? null : getHibernateTemplate().get(clazz, id);
	}

	public Pagination pageById(Serializable id) {

		Pagination pg = new Pagination();
		List<E> rs = queryById(id);
		long totalCount = rs.size();
		pg.setTotalCount(totalCount);
		pg.setResult(rs);
		pg.setCurrentPage(1);
		pg.setPageSize(PAGE_SIZE);
		pg.setPageCount(Pagination.getPageCount(totalCount, PAGE_SIZE));
		return pg;
	}

	public List<E> queryById(Serializable id) {

		List<E> rs = new ArrayList<E>();
		E record = findById(id);
		if (record != null)
			rs.add(record);
		return rs;
	}

	@SuppressWarnings("unchecked")
	public E findByProperty(final String propertyName, final Object value) {
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		criteria.add(Restrictions.eq(propertyName, value));
		List<E> res = getHibernateTemplate().findByCriteria(criteria);
		if (res != null && res.size() > 0)
			return res.get(0);
		return null;
		/*
		 * Assert.hasText(propertyName, "propertyName must specified."); return
		 * (E) getHibernateTemplate().execute(new HibernateCallback<E>() {
		 * 
		 * public E doInHibernate(Session session) throws HibernateException,
		 * SQLException {
		 * 
		 * Criteria criteria = session.createCriteria(clazz).add(
		 * Restrictions.eq(propertyName, value)); return (E)
		 * criteria.uniqueResult(); } });
		 */
	}

	public void update(E obj) {
		if (obj == null)
			return;
		getHibernateTemplate().update(obj);
		getHibernateTemplate().flush();
	}

	public void delete(E obj) {

		if (obj == null)
			return;
		getHibernateTemplate().delete(obj);
	}

	public void deleteAll(Collection<E> collections) {

		if (collections == null)
			return;
		getHibernateTemplate().deleteAll(collections);
	}

	public int deleteAll() {

		String hql = "delete from " + clazz.getName();
		return getHibernateTemplate().bulkUpdate(hql);
	}

	public List<E> getAll() {

		return getAllInOrder(null);
	}

	@SuppressWarnings("unchecked")
	public List<E> getAllInOrder(String orderHql) {

		if (clazz == null)
			return new ArrayList<E>();
		String hql = "from " + clazz.getName();
		if (StringUtils.hasText(orderHql))
			hql = hql + " " + orderHql;
		return getHibernateTemplate().find(hql);
	}

	protected Pagination pageQuery(String hql, Object[] values,
			String countHql, int pageNumber, int pageSize) {

		long totalCount = querySize(countHql, values);
		return pageQuery(totalCount, hql, values, pageNumber, pageSize);
	}

	protected Pagination pageQuery(String hql, Map<String, Object> values,
			String countHql, int pageNumber, int pageSize) {

		Assert.notNull(countHql, "countHql can not be null.");
		long totalCount = querySize(countHql, values);
		return pageQuery(totalCount, hql, values, pageNumber, pageSize);
	}

	protected Pagination pageQuery(String hql, String countHql, int pageNumber,
			int pageSize) {

		return pageQuery(hql, (Object[]) null, countHql, pageNumber, pageSize);
	}

	protected Pagination pageQuery(Long totalCount, String hql,
			Object[] values, int pageNumber, int pageSize) {

		Pagination pagination = new Pagination();
		if (totalCount == null)
			totalCount = querySize(hql, values);
		long count = Pagination.getPageCount(totalCount, pageSize);
		pageNumber = (int) (pageNumber > count ? count : pageNumber);
		List<?> result = pageList(hql, values, pageNumber, pageSize);
		pagination.setTotalCount(totalCount);
		pagination.setResult(result);
		pagination.setCurrentPage(pageNumber);
		pagination.setPageSize(pageSize);
		pagination.setPageCount(count);
		return pagination;
	}

	protected Pagination pageQuery(Long totalCount, String hql,
			Map<String, Object> values, int pageNumber, int pageSize) {

		Pagination pagination = new Pagination();
		if (totalCount == null)
			totalCount = querySize(hql, values);
		long count = Pagination.getPageCount(totalCount, pageSize);
		pageNumber = (int) (pageNumber > count ? count : pageNumber);
		List<?> result = pageList(hql, values, pageNumber, pageSize);
		pagination.setTotalCount(totalCount);
		pagination.setResult(result);
		pagination.setCurrentPage(pageNumber);
		pagination.setPageSize(pageSize);
		pagination.setPageCount(count);
		return pagination;
	}

	public Pagination getAll(int pageNumber, int pageSize) {

		Pagination pagination = new Pagination();
		if (clazz == null)
			return pagination;
		long totalCount = querySize(clazz);
		long count = Pagination.getPageCount(totalCount, pageSize);
		pageNumber = (int) (pageNumber > count ? count : pageNumber);
		String hql = "from " + clazz.getName();
		List<?> result = pageList(hql, pageNumber, pageSize);
		pagination.setTotalCount(totalCount);
		pagination.setResult(result);
		pagination.setCurrentPage(pageNumber);
		pagination.setPageSize(pageSize);
		pagination.setPageCount(count);
		return pagination;
	}

	@SuppressWarnings("unchecked")
	protected E queryOne(String hql) {

		HibernateTemplate template = getHibernateTemplate();
		template.setMaxResults(0);
		List<?> e = template.find(hql);
		if (null != e & e.size() > 0)
			return (E) e.get(0);
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<E> queryList(String hqlStr) {

		if (clazz == null)
			return new ArrayList<E>();
		String hql = "from " + clazz.getName();
		if (StringUtils.hasText(hqlStr))
			hql = hql + " " + hqlStr;
		return getHibernateTemplate().find(hql);
	}

	protected List<?> queryList(String hql, Object value) {

		return getHibernateTemplate().find(hql, value);
	}

	protected List<?> queryList(String hql, Object[] values) {

		return getHibernateTemplate().find(hql, values);
	}

	protected List<?> queryList(String hql, Map<String, Object> values,
			Boolean enableCache) {

		return getMyHibernateTemplate().find(hql, values, enableCache);
	}

	protected List<?> queryList(String hql, Map<String, Object> values) {

		return getMyHibernateTemplate().find(hql, values, null);
	}

	protected List<?> queryList(String hql, Object[] values, Boolean enableCache) {

		return getMyHibernateTemplate().find(hql, values, enableCache);
	}

	protected List<?> queryList(DetachedCriteria criteria) {

		if (criteria == null)
			return new ArrayList<Object>();
		return getHibernateTemplate().findByCriteria(criteria);
	}

	protected List<?> pageList(String hql, int pageNumber, int pageSize) {

		return pageList(hql, (Object[]) null, pageNumber, pageSize);
	}

	public boolean isPropertyExist(final String propertyName, final Object value) {

		Assert.hasText(propertyName, "propertyName must specified.");
		List<E> list = getHibernateTemplate().execute(
				new HibernateCallback<List<E>>() {

					@SuppressWarnings("unchecked")
					public List<E> doInHibernate(Session session)
							throws HibernateException, SQLException {

						Criteria criteria = session.createCriteria(clazz).add(
								Restrictions.eq(propertyName, value));
						return (List<E>) criteria.list();
					}
				});
		return !(list.isEmpty());
	}

	protected List<?> pageList(DetachedCriteria criteria, int pageNumber,
			int pageSize) {

		if (criteria == null)
			return new ArrayList<Object>();
		final int firstResult = pageSize * (pageNumber - 1);
		return getHibernateTemplate().findByCriteria(criteria, firstResult,
				pageSize);
	}

	protected List<?> pageList(final String hql, final Object[] values,
			int pageNumber, final int pageSize) {

		return pageList(hql, values, null, pageNumber, pageSize);
	}

	protected List<?> pageList(final String hql, final Object[] values,
			final Boolean enableCache, int pageNumber, final int pageSize) {

		return getMyHibernateTemplate().pageList(hql, values, enableCache,
				pageNumber, pageSize);
	}

	protected List<?> pageList(final String hql,
			final Map<String, Object> values, int pageNumber, final int pageSize) {

		return pageList(hql, values, null, pageNumber, pageSize);
	}

	protected List<?> pageList(final String hql,
			final Map<String, Object> values, final Boolean enableCache,
			int pageNumber, final int pageSize) {

		return getMyHibernateTemplate().pageList(hql, values, enableCache,
				pageNumber, pageSize);
	}

	protected int executeHql(String hql) {

		return getHibernateTemplate().bulkUpdate(hql);
	}

	protected int executeHql(String hql, Object value) {

		return getHibernateTemplate().bulkUpdate(hql, value);
	}

	protected int executeHql(String hql, Object[] values) {

		return getHibernateTemplate().bulkUpdate(hql, values);
	}

	protected <T> List<T> executeSqlFind(final String sql,
			final ResultSetHandler<T> handler) {

		Assert.notNull(handler);
		Assert.hasText(sql);
		return getHibernateTemplate().execute(new HibernateCallback<List<T>>() {

			public List<T> doInHibernate(Session session)
					throws HibernateException, SQLException {

				final List<T> rs = new ArrayList<T>();
				session.doWork(new Work() {

					public void execute(Connection con) throws SQLException {

						PreparedStatement stmt = con.prepareStatement(sql);
						ResultSet row = stmt.executeQuery(sql);
						while (row.next()) {
							rs.add(handler.handle(row));
						}
						row.close();
						stmt.close();
					}
				});
				return rs;
			}
		});
	}

	protected int executeSqlUpdate(final String sql) {

		Assert.hasText(sql);
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {

			public Integer doInHibernate(Session session)
					throws HibernateException, SQLException {

				final List<Integer> rs = new ArrayList<Integer>();
				session.doWork(new Work() {

					public void execute(Connection con) throws SQLException {

						PreparedStatement stmt = con.prepareStatement(sql);
						int count = stmt.executeUpdate(sql);
						rs.add(count);
						stmt.close();
					}
				});
				return rs.get(0);
			}
		});
	}

	protected long querySize(Class<E> clazz) {

		String hql = "select count(*) from " + clazz.getName();
		return querySize(hql);
	}

	protected long querySize(String hql) {

		return querySize(hql, (Object[]) null);
	}

	protected long querySize(String hql, Object value) {

		return querySize(hql, new Object[] { value });
	}

	public long querySize(String hql, Object[] values) {

		if (hql == null)
			return 0;
		List<?> list = queryList(hql, values);
		if (list.size() == 1) {
			Object obj = list.get(0);
			if (obj instanceof Long)
				return ((Long) obj);
			return 1;
		} else if (list.size() > 1)
			return list.size();
		return 0;
	}

	public long querySize(String hql, Map<String, Object> values) {

		if (hql == null)
			return 0;
		List<?> list = queryList(hql, values);
		if (list.size() == 1) {
			Object obj = list.get(0);
			if (obj instanceof Long)
				return ((Long) obj);
			return 1;
		} else if (list.size() > 1)
			return list.size();
		return 0;
	}

	public long getTotalCount() {
		return this.querySize(clazz);
	}

	public Pagination queryAllForPage(Map<String, Object> values,
			int pageNumber, int pageSize) {

		Pagination pagination = new Pagination();
		if (clazz == null)
			return pagination;
		long totalCount = querySize(clazz);
		long count = Pagination.getPageCount(totalCount, pageSize);
		pageNumber = (int) (pageNumber > count ? count : pageNumber);
		String hql = "from " + clazz.getName();
		List<?> result = pageList(hql, values, pageNumber, pageSize);
		pagination.setTotalCount(totalCount);
		pagination.setResult(result);
		pagination.setCurrentPage(pageNumber);
		pagination.setPageSize(pageSize);
		pagination.setPageCount(count);
		pagination.setPageStart(pageNumber * pageSize + 1);
		return pagination;
	}

	public long queryMax(String property) {
		Object obj = getSession()
				.createCriteria(clazz)
				.setProjection(
						Projections.projectionList().add(
								Projections.max(property))).uniqueResult();
		
		if(obj == null)return 0;
		
		if(obj instanceof Integer)
			return ((Integer)obj).intValue();
		else if(obj instanceof Long)
			return ((Long)obj).longValue();
		else
			return 0;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<?> queryList(final String hql, final Object[] paramArr, final Type[] paramTypeArr) {
		List<?> list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException { 
				Query query = session.createQuery(hql);
				if (paramArr != null) {
					query.setParameters(paramArr, paramTypeArr);  
				}
				List<?> list = query.list();  
				return list;  
			}
		});
		
		return list; 
	}
}
