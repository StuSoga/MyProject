package com.zero.dao.hibernate;

import com.zero.dao.Pager;
import com.zero.dao.ReflectionUtils;
import com.zero.dao.SimpleHibernateDao;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zero on 16/3/1.
 */
public class HibernateDao<T, ID extends Serializable> extends SimpleHibernateDao<T, ID> {
	public HibernateDao() {
		super();
	}

	public HibernateDao(final SessionFactory sessionFactory, final Class<T> entityClass) {
		super(sessionFactory, entityClass);
	}

	/**
	 *
	 * @param page
	 * @return
	 */
	public Pager<T> getAll(final Pager<T> page) {
		return this.findPage(page);
	}

	/**
	 *
	 * @param page
	 * @param hql
	 * @param values
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Pager<T> findPage(final Pager<T> page, final String hql, final Map<String, ?> values) {
		Assert.notNull(page, "page is not null!");

		Query q = this.createQuery(hql, values);

		if (page.isAutoCount()) {
			long totalCount = this.countHqlResult(hql, values);
			page.setTotalCount(totalCount);
		}

		this.setPageParameterToQuery(q, page);

		List result = q.list();
		page.setResults(result);
		return page;
	}

	/**
	 *
	 * @param page
	 * @param criterions
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Pager<T> findPage(final Pager<T> page, final Criterion... criterions) {
		Assert.notNull(page, "page is not null!");

		Criteria c = this.createCriteria(criterions);

		if (page.isAutoCount()) {
			long totalCount = this.countCriteriaResult(c);
			page.setTotalCount(totalCount);
		}

		this.setPageParameterToCriteria(c, page);

		List result = c.list();
		page.setResults(result);
		return page;
	}

	protected Query setPageParameterToQuery(final Query q, final Pager<T> page) {

		Assert.isTrue(page.getPageSize() > 0, "Page Size must larger than zero");

		q.setFirstResult(page.getFirst() - 1);
		q.setMaxResults(page.getPageSize());

		return q;
	}

	protected Criteria setPageParameterToCriteria(final Criteria c, final Pager<T> page) {

		Assert.isTrue(page.getPageSize() > 0, "Page Size must larger than zero");
		c.setFirstResult(page.getFirst() - 1);
		c.setMaxResults(page.getPageSize());
		return c;
	}

	protected long countHqlResult(final String hql, final Object... values) {
		String countHql = this.prepareCountHql(hql);

		try {
			Long count = this.findUnique(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
		}
	}

	protected long countHqlResult(final String hql, final Map<String, ?> values) {
		String countHql = this.prepareCountHql(hql);

		try {
			Long count = this.findUnique(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
		}
	}

	private String prepareCountHql(String orgHql) {
		String fromHql = orgHql;
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");

		String countHql = "select count(*) " + fromHql;
		return countHql;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected long countCriteriaResult(final Criteria c) {
		CriteriaImpl impl = (CriteriaImpl) c;

		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();

		List<CriteriaImpl.OrderEntry> orderEntries = null;
		try {
			orderEntries = (List) ReflectionUtils.getFieldValue(impl, "orderEntries");
			ReflectionUtils.setFieldValue(impl, "orderEntries", new ArrayList());
		} catch (Exception e) {
			this.LOG.error("Impossible exception:{}", e.getMessage());
		}

		Long totalCountObject = (Long) c.setProjection(Projections.rowCount()).uniqueResult();
		long totalCount = (totalCountObject != null) ? totalCountObject : 0;

		c.setProjection(projection);

		if (projection == null) {
			c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null) {
			c.setResultTransformer(transformer);
		}
		try {
			ReflectionUtils.setFieldValue(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
			this.LOG.error("impossible exception {}", e.getMessage());
		}

		return totalCount;
	}
}
