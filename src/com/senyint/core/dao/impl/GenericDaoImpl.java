package com.senyint.core.dao.impl;



import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.PropertyValueException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.ejb.QueryHints;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.senyint.core.dao.GenericDao;
import com.senyint.core.dao.exception.ErrorCode;
import com.senyint.core.dao.exception.ErrorCodeHelper;
import com.senyint.core.dao.exception.ExceptionUtil;
import com.senyint.core.dao.exception.SysRuntimeException;
import com.senyint.core.entity.persistence.PersistentObject;


/**
 * default transaction mechanism is {@link Propagation#SUPPORTS} and
 * <code>readOnly = true</code>
 *
 * @param <T>
 * @param <POID>
 */
public abstract class GenericDaoImpl<T extends PersistentObject<POID>, POID extends Serializable>
		extends EntityManagerDaoImpl implements GenericDao<T, POID> {

	/**
	 * the property name of PersistentObejct's object id
	 */
	private static final String POID_PROP_NAME = "poid";

	private Class<T> entityBeanType;

	public GenericDaoImpl() {
		this.entityBeanType = fetchEntityType();
	}

	/**
	 * fetch entity type this generic eao handled
	 * <p>
	 * overwrite this method to change the entity type the sub eao class handled
	 * </p>
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Class<T> fetchEntityType() {
		// Only support JDK interface proxy
		return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		
		// To support CGLIB class proxy
		//Class<?> clazz = getClass();
		
		//while(clazz != null && !clazz.getSuperclass().equals(JpaGenericEao.class)) {
		/*while(clazz != null && !Modifier.isAbstract(clazz.getSuperclass().getModifiers())) {
			clazz = clazz.getSuperclass();
		}
		Type type = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];
		if(type instanceof TypeVariable<?>) {
			TypeVariable typeVar = (TypeVariable) type;
			type = typeVar.getBounds()[0];
		}
		return (Class<T>) type;*/
	}

	public Class<T> getEntityBeanType() {
		return entityBeanType;
	}

	/* (non-Javadoc)
	 * @see makePersistent(java.lang.Object)
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public T makePersistent(T entity) {
		try {
			T mergedEntity = getEntityManager().merge(entity);//getEntityManager().find(getEntityBeanType(), "req-1")
			return makeIsPersisted(mergedEntity);
		} catch (Exception e) {
			if (ExceptionUtil.containsException(e, ConstraintViolationException.class)) {
				throw new SysRuntimeException(
						ErrorCode.SAVE_CONSTRAINT_VIOLATION,
						ErrorCodeHelper.getInstance().getPatternMessage(ErrorCode.SAVE_CONSTRAINT_VIOLATION, entity.getClass(), entity.getPoid()),
						e);
			} else if (ExceptionUtil.containsException(e, PropertyValueException.class)) {
					PropertyValueException pve = (PropertyValueException) ExceptionUtil.fetchExceptionWithClass(e, PropertyValueException.class);
					throw new SysRuntimeException(
							ErrorCode.SAVE_NOT_NULL_PROPERTY_WITH_NULL,
							ErrorCodeHelper.getInstance().getPatternMessage(ErrorCode.SAVE_NOT_NULL_PROPERTY_WITH_NULL, pve.getPropertyName(), pve.getEntityName()),
							e);
			} else if (ExceptionUtil.containsException(e, DataException.class)) {
				DataException de = (DataException) ExceptionUtil.fetchExceptionWithClass(e, DataException.class);
				throw new SysRuntimeException(
						ErrorCode.SAVE_DATA_EXCEPTION,
						ErrorCodeHelper.getInstance().getPatternMessage(ErrorCode.SAVE_DATA_EXCEPTION, entity.getClass(), de.getSQLException().getMessage()),
						e);
			} else {
				throw new SysRuntimeException(
						ErrorCode.SAVE_EXCEPTION,
						ErrorCodeHelper.getInstance().getPatternMessage(ErrorCode.SAVE_EXCEPTION, entity.getClass(), entity.getPoid()),
						e);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.inqgen.iqlis.persistence.eao.GenericEao#makePersistent(java.util.Collection)
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<T> makePersistent(Collection<T> entities) {
		List<T> results = new ArrayList<T>();
		for (T entity : entities) {
			results.add(makePersistent(entity));
		}
		return results;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void makeTransient(T entity) {
		try {
			getEntityManager().remove(entity);
			flush();
		} catch (Exception e) {
			if (ExceptionUtil.containsException(e, ConstraintViolationException.class)) {
				throw new SysRuntimeException(
						ErrorCode.DELETE_CONSTRAINT_VIOLATION,
						ErrorCodeHelper.getInstance().getPatternMessage(ErrorCode.DELETE_CONSTRAINT_VIOLATION, entity.getClass(), entity.getPoid()),
						e);
			} else {
				throw new SysRuntimeException(
						ErrorCode.DELETE_EXCEPTION,
						ErrorCodeHelper.getInstance().getPatternMessage(ErrorCode.DELETE_EXCEPTION, entity.getClass(), entity.getPoid()),
						e);
			}
		}
		makeIsNotPersisted(entity);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void remove(T entity) {
		try {
			getEntityManager().remove(getEntityManager().merge(entity));
			flush();
		} catch (Exception e) {
			if (ExceptionUtil.containsException(e, ConstraintViolationException.class)) {
				throw new SysRuntimeException(
						ErrorCode.DELETE_CONSTRAINT_VIOLATION,
						ErrorCodeHelper.getInstance().getPatternMessage(ErrorCode.DELETE_CONSTRAINT_VIOLATION, entity.getClass(), entity.getPoid()),
						e);
			} else {
				throw new SysRuntimeException(
						ErrorCode.DELETE_EXCEPTION,
						ErrorCodeHelper.getInstance().getPatternMessage(ErrorCode.DELETE_EXCEPTION, entity.getClass(), entity.getPoid()),
						e);
			}
		}
		makeIsNotPersisted(entity);
	}

	/**
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void makeTransient(Collection<T> entities)
	{
		for (T entity : entities)
		{
			makeTransient(entity);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void remove(Collection<T> entities)
	{
		for (T entity : entities)
		{
			remove(entity);
		}
	}

	public T findByPoid(POID poid) {
		if(poid == null) {
			return null;
		}
		T entity = getEntityManager().find(getEntityBeanType(), poid);
		if (entity != null) {
			return makeIsPersisted(entity);
		} else {
			return entity;
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> findByPoid(List<T> examples,boolean cache) {
		if (examples.isEmpty()) {
			return new ArrayList<T>();
		}

		List<POID> poidList = new ArrayList<POID>();
		for (T example : examples) {
			POID poid = example.getPoid();
			if (poid != null) {
				poidList.add(poid);
			}
		}

		Criteria criteria = createCriteria().add(Restrictions.in("poid", poidList));
		if(cache){
			criteria.setCacheable(true);
		}
		return makeIsPersisted(criteria.list());
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll(int startRow, int pageSize,boolean cache){
		Query q = getEntityManager().createQuery("select e from " + getEntityBeanType().getName() + " e");
		q.setFirstResult(startRow);
		q.setMaxResults(pageSize);
		if(cache)q.setHint(QueryHints.HINT_CACHEABLE, cache);
		return makeIsPersisted(q.getResultList());
	}
	
	public int countAll(boolean cache){
		Query q = getEntityManager().createQuery("select count(poid) from " + getEntityBeanType().getName());
		if(cache)q.setHint(QueryHints.HINT_CACHEABLE, cache);
		return Integer.parseInt(q.getSingleResult().toString());
	}
	
	
	@SuppressWarnings("unchecked")
	public List<T> findAll(boolean cache) {
		Query q =  getEntityManager().createQuery("select e from " + getEntityBeanType().getName() + " e");
		if(cache)q.setHint(QueryHints.HINT_CACHEABLE, cache);
		return makeIsPersisted(q.getResultList());
	}

	public List<T> findByExample(T exampleInstance, boolean enableLike,boolean cache, String... excludeProperty) {
		return findByExample(exampleInstance, enableLike,cache,
				ArrayUtils.EMPTY_STRING_ARRAY, ArrayUtils.EMPTY_OBJECT_ARRAY,
				excludeProperty);
	}

	public List<T> findByExample(T exampleInstance, boolean enableLike,boolean cache,
			String[] associatedPropNames, Object[] associatedPropExamples,
			String... excludeProperty) {
		return findByExample(exampleInstance, enableLike, cache,
				associatedPropNames, associatedPropExamples, excludeProperty);
    }

	@SuppressWarnings("unchecked")
	public List<T> findByExample(T exampleInstance, boolean enableLike,boolean cache,
			String ordering, String[] associatedPropNames,
			Object[] associatedPropExamples, String... excludeProperty) {

		org.hibernate.Criteria criteria = createExampleCriteria(exampleInstance,
				enableLike,cache, associatedPropNames, associatedPropExamples,
				excludeProperty);

		criteria = addOrderToCriteria(criteria, ordering);

        List result = criteria.list();
		return makeIsPersisted(result);
	}

	private org.hibernate.Criteria createExampleCriteria(T exampleInstance,
			boolean enableLike,boolean cache, String[] associatedPropNames,
			Object[] associatedPropExamples, String... excludeProperty) {

		// validate parameters
		if (associatedPropNames == null) {
			associatedPropNames = new String[] {};
		}
		if (associatedPropExamples == null) {
			associatedPropExamples = new Object[] {};
		}
		if(associatedPropExamples.length != associatedPropNames.length) {
			String msg = MessageFormat.format(
					"argument associatedPropNames length:{0} and associatedPropExamples length:{1} does not match",
					associatedPropNames.length, associatedPropExamples.length);
			throw new IllegalArgumentException(msg);
		}
		int indexOfNull= ArrayUtils.indexOf(associatedPropNames, null);
		if (indexOfNull >= 0) {
			String msg = MessageFormat.format(
					"argument associatedPropNames contains null element at pos:{0}", indexOfNull);
			throw new IllegalArgumentException(msg);
		}
		if (excludeProperty == null) {
			excludeProperty = ArrayUtils.EMPTY_STRING_ARRAY;
		}

		// Using Hibernate native
		org.hibernate.Criteria criteria = createCriteria();
		if(cache){
			criteria.setCacheable(cache);
		}
        org.hibernate.criterion.Example example = org.hibernate.criterion.Example.create(exampleInstance);
        for (String exclude : excludeProperty) {
            example.excludeProperty(exclude);
        }
        if (enableLike) {
        	example.enableLike(org.hibernate.criterion.MatchMode.ANYWHERE);
        }
        criteria.add(example);

        // in hibernate, primary key, foreign key, and version column won't be taken as criterion,
        // we have to add it manually.
        if(exampleInstance.getPoid() != null) {
        	if(enableLike && String.class.equals(exampleInstance.getPoid().getClass())) {
        		criteria.add(Restrictions.ilike(POID_PROP_NAME, "%" + exampleInstance.getPoid() + "%"));
        	} else {
        		criteria.add(Restrictions.eq(POID_PROP_NAME, exampleInstance.getPoid()));
        	}
        }

        // ordering

        // add association object criteria
        for(int i=0; i<associatedPropNames.length; i++) {
        	if(associatedPropExamples[i] != null){
            	String propName = associatedPropNames[i];
            	Object value = associatedPropExamples[i];
            	Criterion assExample = Example.create(value);
            	criteria.createCriteria(propName).add(assExample);
        	}
        }
		return criteria;
	}

	public int fetchCountByExample(T exampleInstance, boolean enableLike,boolean cache,
			String... excludeProperty) {
		Criteria criteria = createExampleCriteria(exampleInstance, enableLike,cache,
				ArrayUtils.EMPTY_STRING_ARRAY, ArrayUtils.EMPTY_OBJECT_ARRAY,
				excludeProperty);
		Integer rowCount = (Integer) criteria.setProjection(Projections.count(POID_PROP_NAME)).uniqueResult();
		return rowCount.intValue();
	}

	public int fetchCountByCriteria(boolean cache,org.hibernate.criterion.Criterion... criterion){
		org.hibernate.Criteria criteria = createCriteria();
		if(cache){
			 criteria.setCacheable(cache);
		}
		for (org.hibernate.criterion.Criterion c : criterion) {
			criteria.add(c);
		}
		Integer rowCount = ((Long)criteria.setProjection(Projections.count(POID_PROP_NAME)).uniqueResult()).intValue();
		return rowCount.intValue();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<T> findByExample(T exampleInstance, boolean enableLike,boolean cache,
			int firstResult, int maxResult, String ordering, String... excludeProperty) {

		// create criteria by example
		Criteria criteria = createExampleCriteria(exampleInstance, enableLike,cache,
				ArrayUtils.EMPTY_STRING_ARRAY, ArrayUtils.EMPTY_OBJECT_ARRAY,
				excludeProperty);

		// narrow down result
		criteria = criteria.setFirstResult(firstResult).setMaxResults(maxResult);

		// add ordering
		criteria = addOrderToCriteria(criteria, ordering);

		List result = criteria.list();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findByCriteria(int firstResult, int maxResult,String ordering,boolean cache,org.hibernate.criterion.Criterion... criterion) {
		org.hibernate.Criteria criteria = createCriteria();
		if(cache){
			criteria.setCacheable(cache);
		}
		for (org.hibernate.criterion.Criterion c : criterion) {
			criteria.add(c);
		}
		criteria = criteria.setFirstResult(firstResult).setMaxResults(maxResult);
		criteria=  addOrderToCriteria(criteria,ordering);
		return makeIsPersisted(criteria.list());
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findByCriteria(int firstResult, int maxResult,String ordering,boolean cache) {
		org.hibernate.Criteria criteria = createCriteria();
		if(cache){
			criteria.setCacheable(cache);
		}
		criteria = criteria.setFirstResult(firstResult).setMaxResults(maxResult);
		criteria=  addOrderToCriteria(criteria,ordering);
		return makeIsPersisted(criteria.list());
	}
	
	
	private Criteria addOrderToCriteria(Criteria criteria, String ordering) {
		if(StringUtils.isBlank(ordering)) {
			return criteria;
		}

		String[] orderingDescs = StringUtils.split(ordering, ",");
		for(String orderDescString : orderingDescs) {
			String[] orderDesc = StringUtils.split(orderDescString, " ");
			String propName = orderDesc[0];

			boolean desc = false;
			if (orderDesc.length > 1) {
				desc = orderDesc[1].toLowerCase().contains("desc");
			}

			if (desc) {
				criteria.addOrder(Order.desc(propName));
			} else {
				criteria.addOrder(Order.asc(propName));
			}
		}
		return criteria;
	}

	@SuppressWarnings("unchecked")
	public List<T> findByCriteria(boolean cache,org.hibernate.criterion.Criterion... criterion) {
		// Using Hibernate native
		org.hibernate.Criteria criteria = createCriteria();
		
		if(cache){
			criteria.setCacheable(cache);
		}
		
		for(org.hibernate.criterion.Criterion c : criterion) {
			criteria.add(c);
		}
		return makeIsPersisted(criteria.list());
	}

	// Using Hibernate native
	protected org.hibernate.Criteria createCriteria() {

		// *** When EntityManager got from Seam Framework use it ***
		//return ((org.jboss.seam.persistence.FullTextHibernateSessionProxy) getEntityManager().getDelegate()).createCriteria(getEntityBeanType());

		// *** When EntityManger got from Spring Framework use it ***
		return ((org.hibernate.ejb.HibernateEntityManager) getEntityManager()).getSession().createCriteria(getEntityBeanType());
	}
	
	@SuppressWarnings("unchecked")
	protected org.hibernate.Criteria createCriteria(Class entity,boolean cache) {

		// *** When EntityManager got from Seam Framework use it ***
		//return ((org.jboss.seam.persistence.FullTextHibernateSessionProxy) getEntityManager().getDelegate()).createCriteria(entity);

		// *** When EntityManger got from Spring Framework use it ***
		org.hibernate.Criteria criteria =((org.hibernate.ejb.HibernateEntityManager) getEntityManager()).getSession().createCriteria(entity);
		
		if(cache){
			criteria.setCacheable(cache);
		}
		return criteria;
	}	

	@Override
	@SuppressWarnings("unchecked")
	protected List<T> query(String jpql,boolean cache,Object... params) {
		return super.query(jpql,cache, params);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected T queryForFirst(String jpql,boolean cache, Object... params) {
		return (T) super.queryForFirst(jpql,cache, params);
	}

	/**
	 * return the first entity of the specified list. or return null if the list
	 * is null or is empty.
	 *
	 * @param entities
	 * @return the first entity of the list, or null.
	 * @author Morel
	 */
	protected T returnFirstOrNull(List<T> entities) {
		if(entities != null && entities.size() > 0) {
			return makeIsPersisted(entities.get(0));
		}
		return null;
	}

	/**
	 * set the {@link PersistentObject#getIsPersisted()} to true.
	 *
	 * <p>
	 * this method does not support cascading feature
	 * </p>
	 *
	 * @param entity
	 * @return
	 */
	protected T makeIsPersisted(T entity) {
		entity.setIsPersisted(true);
		return entity;
	}

	protected List<T> makeIsPersisted(List<T> entities) {
		for (T entity : entities) {
			makeIsPersisted(entity);
		}
		return entities;
	}

	protected T makeIsNotPersisted(T entity) {
		entity.setIsPersisted(false);
		return entity;
	}
	

}
