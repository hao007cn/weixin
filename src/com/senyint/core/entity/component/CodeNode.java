package com.senyint.core.entity.component;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


/**
 * Nodetable entity.
 * 
 */
@MappedSuperclass
public abstract class CodeNode<E extends CodeNode<E>> extends BaseEntity {

	// Fields
	private static final long serialVersionUID = 4877790487100736888L;

	private String name;

	private String desc;
	
	private Integer sort;
	
	private E parent;
	
	private List<E> children = new ArrayList<E>();
	
	public CodeNode() {
		super();
	}

	/**
	 * construct with necessary attributes
	 * 
	 * @param code
	 * @param name
	 * @param level
	 * @param viewSequence
	 * @param creator
	 */
	public CodeNode(String name) {
		super();
		this.name = name;
	}


	/**
	 * node Name
	 * 
	 * @return
	 */
	@Column(name = "name_", length = 100, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	/**
	 * node's description
	 * 
	 * @return
	 */
	@Column(name = "desc_",length=500)
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	@Column(name = "sort_")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	/**
	 * parent Node
	 * 
	 * @return
	 */
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parentId_")
	public E getParent() {
		return parent;
	}

	public void setParent(E parent) {
		this.parent = parent;
	}
	
	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	@LazyCollection(LazyCollectionOption.EXTRA)
	@org.hibernate.annotations.Cascade(value = org.hibernate.annotations.CascadeType.DELETE)
	@org.hibernate.annotations.Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
	@OrderBy("sort asc")
	public List<E> getChildren() {
		return children;
	}

	public void setChildren(List<E> children) {
		this.children = children;
	}

	/**
	 * add subNode
	 * 
	 * @param node
	 */
	@SuppressWarnings("unchecked")
	public void addChild(E node) {
		node.setParent((E)this);
		getChildren().add(node);
	}

	/**
	 * move subNode
	 * 
	 * @param node
	 */
	public void removeChlld(E node) {
		node.setParent(null);
		getChildren().remove(node);
	}

	@Transient
	public boolean hasChildren() {
		return !getChildren().isEmpty();
	}

	@Transient
	public boolean hasChild(Integer nodeCode) {
		if (nodeCode == null) {
			return false;
		}

		for (E child : children) {
			if (child != null && child.getPoid() == nodeCode) {
				return true;
			}
		}

		return false;
	}

}
