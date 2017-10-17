package com.senyint.core.entity.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.senyint.core.entity.persistence.PersistentObject;

/**
 * a selected entity
 * 
 * @param <E>
 */
public class MarkableEntity<E> implements Comparable<MarkableEntity<E>>,IMarkableEntity<E> {

	private static final long serialVersionUID = 1L;

	private boolean marked;

	private E entity;

	public MarkableEntity() {
		super();
	}

	public MarkableEntity(E entity) {
		super();
		this.entity = entity;
	}

	public MarkableEntity(boolean marked, E entity) {
		super();
		this.marked = marked;
		this.entity = entity;
	}

	public boolean isMarked() {
		return marked;
	}

	public void setMarked(boolean marked) {
		this.marked = marked;
	}

	public E getEntity() {
		return entity;
	}

	public void setEntity(E entity) {
		this.entity = entity;
	}

	public static <E> List<MarkableEntity<E>> wrap(Collection<E> entities) {
		List<MarkableEntity<E>> result = new ArrayList<MarkableEntity<E>>();
		if (entities == null) {
			return result;
		}

		for (E entity : entities) {
			result.add(new MarkableEntity<E>(entity));
		}
		return result;
	}

	/**
	 *  
	 * */
	public static <E> List<E> unwrapWrapper(
			Collection<MarkableEntity<E>> wrapped) {
		List<E> result = new ArrayList<E>();
		if (wrapped == null) {
			return result;
		}

		for (MarkableEntity<E> markableEntity : wrapped) {
			result.add(markableEntity.getEntity());
		}
		return result;
	}

	public static <E> List<MarkableEntity<E>> wrapAsMarked(List<E> entities) {
		List<MarkableEntity<E>> result = new ArrayList<MarkableEntity<E>>();
		if (entities == null) {
			return result;
		}

		for (E entity : entities) {
			result.add(new MarkableEntity<E>(true, entity));
		}
		return result;
	}

	public static <E> List<E> unwrap(Collection<MarkableEntity<E>> wrapped) {

		List<E> result = new ArrayList<E>();
		if (wrapped == null) {
			return result;
		}

		for (MarkableEntity<E> markableEntity : wrapped) {
			result.add(markableEntity.getEntity());
		}
		return result;
	}

	public static <E> List<MarkableEntity<E>> selectMarked(
			Collection<MarkableEntity<E>> wrapped) {

		List<MarkableEntity<E>> result = new ArrayList<MarkableEntity<E>>();
		for (MarkableEntity<E> markableEntity : wrapped) {
			if (markableEntity.isMarked()) {
				result.add(markableEntity);
			}
		}
		return result;
	}

	/**
	 * 
	 * @param <E>
	 * @param wrapped
	 * @return
	 */
	public static <E> List<E> selectMarkedEntities(
			Collection<MarkableEntity<E>> wrapped) {

		List<E> result = new ArrayList<E>();
		for (MarkableEntity<E> markableEntity : wrapped) {
			if (markableEntity.isMarked()) {
				result.add(markableEntity.getEntity());
			}
		}
		return result;
	}

	/**
	 * set all wrappers's mark as true
	 * 
	 * @param <E>
	 * @param wrapped
	 */
	public static <E extends PersistentObject<? extends Serializable>> void markAll(
			Collection<MarkableEntity<E>> wrapped) {
		markAllWith(wrapped, true);
	}

	/**
	 * set all wrappers's mark as false
	 * 
	 * @param <E>
	 * @param wrapped
	 */
	public static <E> void unmarkAll(Collection<MarkableEntity<E>> wrapped) {
		markAllWith(wrapped, false);
	}

	/**
	 * @param <E>
	 * @param wrapped
	 * @param entity
	 */
	public static <E> void markEntity(Collection<MarkableEntity<E>> wrapped,
			E entity) {
		for (MarkableEntity<E> markableEntity : wrapped) {
			if (markableEntity.entity.equals(entity)) {
				markableEntity.setMarked(Boolean.TRUE);
				break;
			}
		}

	}

	/**
	 * @param <E>
	 * @param wrapped
	 * @param entitys
	 */
	public static <E> void markEntitys(Collection<MarkableEntity<E>> wrapped,
			Collection<E> entitys) {
		for (MarkableEntity<E> markableEntity : wrapped) {
			for (E entity : entitys) {
				if (markableEntity.entity.equals(entity)) {
					markableEntity.setMarked(Boolean.TRUE);
					break;
				}
			}
		}

	}

	/**
	 * 
	 * @param <E>
	 * @param wrapped
	 * @param marked
	 */
	private static <E> void markAllWith(Collection<MarkableEntity<E>> wrapped,
			boolean marked) {
		for (MarkableEntity<E> markableEntity : wrapped) {
			markableEntity.setMarked(marked);
		}
	}

	/**
	 * compare the order with entity
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * @throws UnsupportedOperationException
	 *             if the wrapped entity does not implement {@link Comparable}
	 *             interface
	 */
	@SuppressWarnings("unchecked")
	public int compareTo(MarkableEntity<E> other) {
		if (!(this.entity instanceof Comparable)) {
			throw new UnsupportedOperationException(
					"the wrapped entity does not implement Comparable interface");
		}

		return ((Comparable<E>) this.entity).compareTo(other.entity);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entity == null) ? 0 : entity.hashCode());
		return result;
	}

	/**
	 * equal if two {@link MarkableEntity} wrap the same entity
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final MarkableEntity other = (MarkableEntity) obj;
		if (entity == null) {
			if (other.entity != null)
				return false;
		} else if (!entity.equals(other.entity))
			return false;
		return true;
	}

}
