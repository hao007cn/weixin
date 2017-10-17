/**
 * GenericEao.java
 * com.xy.eao
 * Function: TODO ADD FUNCTION
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2012-12-4 		Administrator
 *
 * Copyright (c) 2012, Senyint All Rights Reserved.
*/

package com.senyint.core.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.senyint.core.entity.persistence.PersistentObject;



/**
 * all eao's interface
 * 
 * @param <T>
 * @param <POID>
 */
public interface GenericDao<T extends PersistentObject<POID>, POID extends Serializable> {

	/**
	 * persist and return the specified entity, entity will be updated if it has
	 * been persisted already. the entity argument won't be modified. you should
	 * use the return one instead of the passed in argument instance.
	 * 
	 * @param entity
	 *            the entity to persist/merge
	 * @return the persisted/merged entity
	 */
	T makePersistent(T entity);

	/**
	 * persist and return the specified entities, entities will be updated if it
	 * has been persisted already. the element of collection argument won't be
	 * modified. you should use the return one instead of the passed in argument
	 * instance.
	 * 
	 * @param entities
	 *            the entity collection to persist/merge
	 * @return the persisted/merged entity
	 */
	List<T> makePersistent(Collection<T> entities);

	void makeTransient(T entity);

	void makeTransient(Collection<T> entities);
	
	void remove(T entity);
	
	void remove(Collection<T> entities);

	/**
	 * find entity by poid.
	 * 
	 * @param poid
	 * @return null if not found
	 * @exception IllegalArgumentException
	 *                if the poid is null
	 */
	T findByPoid(POID poid);

	/**
	 * find by poids that specified by example parameter
	 * 
	 * @param examples
	 *            instance with null poid will be ignored
	 * @return
	 */
	List<T> findByPoid(List<T> examples,boolean cache);

	List<T> findAll(boolean cache);

	List<T> findAll(int startRow, int pageSize,boolean cache);

	int countAll(boolean cache);

	/**
	 * query by example
	 * <p>
	 * using this method if your example instance contains associatdion entity.
	 * hibernate dose not support query example with association entity
	 * property.
	 * </p>
	 * 
	 * @param exampleInstance
	 *            the main example instance to query
	 * @param enableLike
	 *            enable like-query or not
	 * @param ordering
	 *            the ordering description with entity property. <br/>ex:
	 *            "propName, propName2 desc, propName3 desc"
	 * @param associatedPropNames
	 *            the association property names you want to query in the main
	 *            example instance, must corresponds to associatedPropExamples.
	 * @param associatedPropExamples
	 *            the association property value you want to query in the main
	 *            example instance, must corresponds to associatedPropNames.
	 * @param excludeProperty
	 *            the property name you want to exclude from this query.
	 * @return
	 */
	public List<T> findByExample(T exampleInstance, boolean enableLike,boolean cache,
			String ordering, String[] associatedPropNames,
			Object[] associatedPropExamples, String... excludeProperty);

	/**
	 * query by example
	 * <p>
	 * using this method if your example instance contains associatdion entity.
	 * hibernate dose not support query example with association entity
	 * property.
	 * </p>
	 * 
	 * @param exampleInstance
	 *            the main example instance to query
	 * @param enableLike
	 *            enable like-query or not
	 * @param associatedPropNames
	 *            the association property names you want to query in the main
	 *            example instance, must corresponds to associatedPropExamples.
	 * @param associatedPropExamples
	 *            the association property value you want to query in the main
	 *            example instance, must corresponds to associatedPropNames.
	 * @param excludeProperty
	 *            the property name you want to exclude from this query.
	 * @return
	 */
	List<T> findByExample(T exampleInstance, boolean enableLike,boolean cache,
			String[] associatedPropNames, Object[] associatedPropExamples,
			String... excludeProperty);

	List<T> findByExample(T exampleInstance, boolean enableLike,boolean cache,
			String... excludeProperty);

	/**
	 * fetch the query result count by hibernate example query with specified
	 * attributes.
	 * 
	 * @param exampleInstance
	 *            the example instance for hibernate QBE (query by example).
	 * @param enableLike
	 *            enable like-query or not
	 * @param excludeProperty
	 *            the property name you want to exclude from this query.
	 * @return the query result count, 0 if no result matches.
	 */
	int fetchCountByExample(T exampleInstance, boolean enableLike,boolean cache,
			String... excludeProperty);

	/**
	 * query by example. this returns results from specified firstResult, and
	 * limit the result in maxResult count.
	 * 
	 * @param exampleInstance
	 *            the example instance for hibernate QBE (query by example).
	 * @param enableLike
	 *            enable like-query or not
	 * @param firstResult
	 *            fetch return result from off set of whole query result.
	 * @param maxResult
	 *            the max return result count
	 * @param ordering
	 *            the ordering description with entity property. <br/>ex:
	 *            "propName, propName2 desc, propName3 desc"
	 * @param excludeProperty
	 *            the property name you want to exclude from this query.
	 * @return
	 */
	List<T> findByExample(T exampleInstance, boolean enableLike,boolean cache,
			int firstResult, int maxResult, String ordering,
			String... excludeProperty);
	
    int fetchCountByCriteria(boolean cache,org.hibernate.criterion.Criterion... criterion);

	List<T> findByCriteria(int firstResult, int maxResult,
			String ordering,boolean cache, org.hibernate.criterion.Criterion... criterion);
	
	List<T> findByCriteria(int firstResult, int maxResult,
			String ordering,boolean cache);

	List<T> findByCriteria(boolean cache,org.hibernate.criterion.Criterion... criterion);

	void flushAndClear();

	void flush();

	void clear();

}
