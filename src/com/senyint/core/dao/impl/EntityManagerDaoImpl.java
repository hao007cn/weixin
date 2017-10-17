package com.senyint.core.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.ejb.QueryHints;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * JPA {@link EntityManager} based EAO
 * <p>
 * this could be used when non one-entity-per-table case. it won't limit the
 * entity type this EAO handles for. you can get entity manager directly and
 * have your own operation freely.
 * </p>
 * 
 * 
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public abstract class EntityManagerDaoImpl {

	private EntityManager entityManager;

	public EntityManagerDaoImpl() {
		super();
	}

	@PersistenceContext(unitName="default")
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	protected EntityManager getEntityManager() {
		if (entityManager == null)
			throw new IllegalStateException(
					"EntityManager has not been set on EAO before usage");
		return entityManager;
	}

	/**
	 * flush and clear entity manager
	 */
	public void flushAndClear() {
		getEntityManager().flush();
		getEntityManager().clear();
	}

	/**
	 * flush entity manager
	 */
	public void flush() {
		getEntityManager().flush();
	}

	/**
	 * clear entity manager
	 */
	public void clear() {
		getEntityManager().clear();
	}

	/**
	 * this is equivalent to
	 * <code>getEntityManager().createQuery(String jpql);</code>
	 * 
	 * @param jpql
	 * @return
	 */
	protected Query createQuery(String jpql,Boolean cache) {
		Query q = getEntityManager().createQuery(jpql);
		if(cache){
			return q.setHint(QueryHints.HINT_CACHEABLE, cache);
		}
		return q;
	}

	/**
	 * create query by entity manager and set parameters accordingly
	 * 
	 * @param jpql
	 *            JPQL string, using '?' as parameter place holder
	 * @param params
	 * @return
	 */
	protected Query createQuery(String jpql,Boolean cache,Object... params) {
		Query q = createQuery(jpql,cache);
		for (int i = 0; i < params.length; i++) {
			q = q.setParameter(i + 1, params[i]);
		}
		return q;
	}

	/**
	 * query for a list of result by specified query expression and partameters
	 * 
	 * @param jpql
	 *            JPQL string, using '?' as parameter place holder
	 * @param params
	 *            the parameters for specified jpql
	 * @return if no result, an empty list will be returned
	 */
	@SuppressWarnings("unchecked")
	protected List query(String jpql,boolean cache,Object... params) {
		if(cache){
			createQuery(jpql,cache,params).getResultList();;
		}
		return createQuery(jpql,cache,params).getResultList();
	}

	/**
	 * query and get the first element from query result. return null if result
	 * is empty.
	 * 
	 * @param jpql
	 *            JPQL string, using '?' as parameter place holder
	 * @param params
	 *            the parameters for specified jpql
	 * @return null if result is empty
	 */
	@SuppressWarnings("unchecked")
	protected Object queryForFirst(String jpql,boolean cache, Object... params) {
		List resultList = createQuery(jpql,cache,params).getResultList();
		return returnFirstOrNull(resultList);
	}

	/**
	 * execute update/delete query string with specified parameters.
	 * 
	 * @param jpql
	 *            JPQL string, using '?' as parameter place holder
	 * @param params
	 *            the parameters for specified jpql
	 * @return the number of objects changed by the call
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	protected int executeUpdate(String jpql, Object... params) {
		return createQuery(jpql,false, params).executeUpdate();
	}

	/**
	 * return the first entity of the specified list. or return null if the list
	 * is null or is empty.
	 * 
	 * @param entities
	 * @return the first entity of the list, or null.
	 * @author Morel
	 */
	@SuppressWarnings("unchecked")
	private Object returnFirstOrNull(List entities) {
		if (entities != null && entities.size() > 0) {
			return entities.get(0);
		}
		return null;
	}

}