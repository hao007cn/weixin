package com.senyint.wx.admin.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.senyint.core.entity.component.CodeNode;
import com.senyint.wx.admin.entity.component.ResourceType;

/**
 * 菜单URL
 */
@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "sys_resource")
@org.hibernate.annotations.BatchSize(size = 20)
public class Resource extends CodeNode<Resource> {

	private static final long serialVersionUID = 4877790487100736888L;
	private String resKey;
	private String resUrl;
	private String resClass;
	private ResourceType type;
	private boolean display; 
	
	private Integer parentId;
	private Integer position;
	
	private List<Role> roles = new ArrayList<Role>();
	
	public Resource() {
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
	public Resource(String name,String resUrl) {
		super(name);
		this.resUrl = resUrl;
	}
	
	@Transient
	public boolean isDisplay() {
		return display;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}

	@Column(name = "resKey_", length = 255)
	public String getResKey() {
		return resKey;
	}

	public void setResKey(String resKey) {
		this.resKey = resKey;
	}
	
	@Column(name = "resUrl_", length = 1024)
	public String getResUrl() {
		return resUrl;
	}

	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}
	
	@Column(name = "resClass_")
	public String getResClass() {
		return resClass;
	}

	public void setResClass(String resClass) {
		this.resClass = resClass;
	}

	/**
	 * 菜单、菜单节点、地址
	 * 
	 * @return
	 */
	@Enumerated
	@Column(name = "type_")
	public ResourceType getType() {
		return type;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}

	@JsonIgnore
	@ManyToMany(mappedBy = "resources")
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	/**
	 * add a user by self
	 * 
	 * @param owner
	 */
	public void addRole(Role role) {
		if (!roles.contains(role)) {
			role.addResource(this);
		}
	}

	/**
	 * remove a user by self
	 * 
	 * @param owner
	 */
	public void removeRole(Role role) {
		if (roles.contains(role)) {
			role.removeResource(this);
		}
	}

	public void removeAllRoles() {
		removeAllRoles(this);
	}
	
	private void removeAllRoles(Resource res) {
		if (res != null) {
			List<Role> roles = res.getRoles();
			if (roles != null) {
				for (int i = 0; i< roles.size(); i++) {
					Role role = roles.get(i);
					if(role.getResources() != null && role.getResources().contains(res)) {
						role.getResources().remove(res);
					}
				}
				roles.clear();
			}
			
			if (res.getChildren() != null) {
				for (Resource child : res.getChildren()) {
					removeAllRoles(child);
				}
			}
		}
	}

	@Transient
	public Integer getParentId() {
		if (this.getParent() != null) {
			this.parentId = this.getParent().getPoid();
		}
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Transient
	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}
}
