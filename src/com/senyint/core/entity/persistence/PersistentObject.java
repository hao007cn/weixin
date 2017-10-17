package com.senyint.core.entity.persistence;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 */
@MappedSuperclass
public abstract class PersistentObject<K extends Serializable> implements Serializable {
	
	private static final long serialVersionUID = -2378163687312264614L;
	private boolean isPersisted;
	
	/**
	 * Entity是否已經Persisted
	 * <p>
	 * true: Entity已經被Persisted<br/>
	 * false: Entity尚未被Persisted<br/>
	 * 可以用此欄位來判斷是否為全新的Entity
	 * </p>
	 * 
	 * @return
	 */
	@Transient
	public boolean getIsPersisted() {
		return isPersisted;
	}
	public void setIsPersisted(boolean isPersisted) {
		this.isPersisted = isPersisted;
	}

	@Override
	public int hashCode() {
		if(this.getPoid() == null) {
			return super.hashCode();
		}
		
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.getPoid() == null) ? 0 : this.getPoid().hashCode());
		return result;
	}

	/**
	 * check the equality of two {@link PersistentObject} by poid.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		PersistentObject<K> other;
		try {
			other = (PersistentObject<K>) obj;
		} catch (ClassCastException e) {
			return false;
		}

		// check if both are entity (with poid)
		if (this.getPoid() != null && other.getPoid() != null) {
			return this.getPoid().equals(other.getPoid());
		}
		return false;
	}

	/**
	 * show the poid of this {@link PersistentObject}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("poid", this.getPoid())
				.toString();
	}

	@Transient
	public abstract K getPoid();
	
	public abstract void setPoid(K poid);
	
	/**
	 * Subclass need override it to clean default initial value
	 * <p>
	 * Call this method before use GenericEao::findByExample()
	 * </p>
	 */
	public void cleanInitial() {
		// Default do nothing
	}
	
}
