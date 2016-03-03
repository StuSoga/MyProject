package com.zero.dao;

import org.hibernate.*;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by zero on 16/3/1.
 */
public class SimpleHibernateDao<T,ID extends Serializable> {

	protected Logger LOG= LoggerFactory.getLogger(this.getClass());

	protected SessionFactory sessionFactory;

	protected Class<T> entityClass;

	public SimpleHibernateDao(){
		this.entityClass=ReflectionUtils.getSuperClassGenricType(this.getClass());
	}

	public SimpleHibernateDao(final SessionFactory sessionFactory,final Class<T> entityClass){
		this.sessionFactory=sessionFactory;
		this.entityClass=entityClass;
	}
	public SessionFactory getSessionFactory(){
		return this.sessionFactory;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	public void save(final T entity) {
		Assert.notNull(entity, "entity can not be null!");
		this.getSession().saveOrUpdate(entity);
		this.LOG.debug("save entity {}", entity);
	}

	public void delete(final T entity) {
		Assert.notNull(entity, "entity can not be null!");
		this.getSession().delete(entity);
		this.LOG.debug("delete entity {}", entity);
	}

	public void delete(final ID id) {
		Assert.notNull(id, "id can not be null!");
		this.delete(this.get(id));
		this.LOG.debug("delete entity {},id is {}", this.entityClass.getSimpleName(), id);
	}

	@SuppressWarnings("unchecked")
	public T get(ID id) {
		Assert.notNull(id, "id can not be null!");
		return (T) this.getSession().get(this.entityClass, id);
	}

	public List<T> get(final Collection<ID> ids) {
		return this.find(Restrictions.in(this.getIdName(), ids));
	}

	public List<T> getAll() {
		return this.find();
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll(String orderByProperty, boolean isAsc) {
		Criteria c = this.createCriteria();
		if (isAsc) {
			c.addOrder(Order.asc(orderByProperty));
		} else {
			c.addOrder(Order.desc(orderByProperty));
		}
		return c.list();
	}

	public List<T> findBy(final String propertyName, final Object value) {
		Assert.hasText(propertyName, "propertyName can not be null!");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return this.find(criterion);
	}

	@SuppressWarnings("unchecked")
	public T findUniqueBy(final String propertyName, final Object value) {
		Assert.hasText(propertyName, "propertyName can not be null!");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return (T) this.createCriteria(criterion).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public <X> List<X> find(final String hql, final Object... values) {
		return this.createQuery(hql, values).list();
	}

	@SuppressWarnings("unchecked")
	public  List<T> query(final String hql, final Object... values) {
		return this.createQuery(hql, values).list();
	}

	@SuppressWarnings("unchecked")
	public <X> List<X> find(final String hql, final Map<String, ?> values) {
		return this.createQuery(hql, values).list();
	}

	@SuppressWarnings("unchecked")
	public <X> List<X> find(int limit, final String hql, final Map<String, Object> values) {
		if (limit == 0) {
			limit = 1;
		}
		Query query = this.createQuery(hql, values);
		query.setFirstResult(0);
		query.setMaxResults(limit);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public <X> X findUnique(final String hql, final Object... values) {
		return (X) this.createQuery(hql, values).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public <X> X findUnique(final String hql, final Map<String, ?> values) {
		return (X) this.createQuery(hql, values).uniqueResult();
	}

	public int batchExecute(final String hql, final Object... values) {
		return this.createQuery(hql, values).executeUpdate();
	}

	public int batchExecute(final String hql, final Map<String, ?> values) {
		return this.createQuery(hql, values).executeUpdate();
	}

	public Query createQuery(final String queryString, final Object... values) {
		Assert.hasText(queryString, "queryString can not be null!");
		Query query = this.getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	public Query createQuery(final String queryString, final Map<String, ?> values) {
		Assert.hasText(queryString, "queryString can not be null!");
		Query query = this.getSession().createQuery(queryString);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}

	@SuppressWarnings("unchecked")
	public List<T> find(final Criterion... criterions) {
		return this.createCriteria(criterions).list();
	}

	@SuppressWarnings("unchecked")
	public T findUnique(final Criterion... criterions) {
		return (T) this.createCriteria(criterions).uniqueResult();
	}

	public Criteria createCriteria(final Criterion... criterions) {
		Criteria criteria = this.getSession().createCriteria(this.entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	public void initProxyObject(Object proxy) {
		Hibernate.initialize(proxy);
	}

	public void flush() {
		this.getSession().flush();
	}

	public Query distinct(Query query) {
		query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return query;
	}

	public Criteria distinct(Criteria criteria) {
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria;
	}

	public String getIdName() {
		ClassMetadata meta = this.getSessionFactory().getClassMetadata(this.entityClass);
		return meta.getIdentifierPropertyName();
	}

	public boolean isPropertyUnique(final String propertyName, final Object newValue, final Object oldValue) {
		if (newValue == null || newValue.equals(oldValue)) {
			return true;
		}
		Object object = this.findUniqueBy(propertyName, newValue);
		return (object == null);
	}
}
