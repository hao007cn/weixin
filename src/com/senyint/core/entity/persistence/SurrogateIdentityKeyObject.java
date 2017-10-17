package com.senyint.core.entity.persistence;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * base class for persistent entity class that using a UUID generation strategy
 * surrogate key as primary key. the property <code>poid</code> is the surrogate
 * key column, and value will be generated automatically.
 * <p>
 * the UUID key would be generated automatically when persisting by under laying
 * persistence framework.
 * </p>
 * <p>
 * 直接繼承這個類別，並實作Entity應有的屬性。poid與id mapping會自動處理。
 * </p>
 * 
 * 
 */
@MappedSuperclass
public abstract class SurrogateIdentityKeyObject extends PersistentObject<Integer> {

	private static final long serialVersionUID = 1L;
	private Integer poid;

	public SurrogateIdentityKeyObject() {
		super();
	}

	@Id
	@org.hibernate.annotations.GenericGenerator(name = "hibernate-identity", strategy = "identity")
	@GeneratedValue(generator = "hibernate-identity")
	@Column(name = "id", unique = true, nullable = false)
	@Override
	public Integer getPoid() {
		return poid;
	}

	@Override
	public void setPoid(Integer poid) {
		this.poid = poid;
	}

}