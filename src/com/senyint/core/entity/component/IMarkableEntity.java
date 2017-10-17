package com.senyint.core.entity.component;


public interface IMarkableEntity<E> extends Markable {

	public E getEntity();

	public void setEntity(E entity);

}
