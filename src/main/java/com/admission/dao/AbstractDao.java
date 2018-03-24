package com.admission.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.type.Type;

import com.admission.entity.BaseEntity;
import com.admission.entity.Pagination;

public abstract interface AbstractDao<E extends BaseEntity> {

	public abstract Serializable save(E obj);

	public abstract E findById(Serializable id);

	public abstract List<E> queryById(Serializable id);

	public abstract Pagination pageById(Serializable id);

	public abstract E findByProperty(final String propertyName, final Object value);

	public abstract boolean isPropertyExist(String propertyName, Object value);

	public abstract void saveOrUpdate(E obj);

	public abstract void saveOrUpdateAll(Collection<E> entities);

	public abstract void update(E obj);

	public abstract void delete(E obj);

	public abstract void deleteAll(Collection<E> collection);

	public abstract int deleteAll();

	public abstract List<E> getAll();

	public abstract List<E> getAllInOrder(String orderHql);

	public abstract Pagination getAll(int pageNumber, int pageSize);

	public abstract long querySize(String hql, Object[] values);

	public abstract long querySize(String hql, Map<String, Object> values);

	public abstract long getTotalCount();
	
	public abstract List<E> queryList(String orderHql);
	
	public abstract Pagination queryAllForPage(Map<String, Object> values, int pageNumber, int pageSize);
	
	public abstract long queryMax(String property);
	
	public abstract List<?> queryList(String hql, final Object[] paramArr, final Type[] paramTypeArr);
}
