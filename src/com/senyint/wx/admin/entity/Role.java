package com.senyint.wx.admin.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.senyint.core.entity.component.CodeNode;


/**
 * role
 * 
 */
@Entity
@Table(name = "sys_role")
public class Role extends CodeNode<Role>{

	private static final long serialVersionUID = 1L;

	private List<User> owners = new ArrayList<User>();
	private List<Resource> resources = new ArrayList<Resource>();
	
	/**
	* @Fields userIds : 用户ids 页面参数
	*/
	private String[] userIds;
	
	/**
	* @Fields resIds : 资源ids 页面参数
	*/
	private String[] resIds;
	
	@ManyToMany(mappedBy = "roles")
	public List<User> getOwners() {
		return owners;
	}

	/**
	 * add a user by self
	 * 
	 * @param owner
	 */
	public void addOwner(User owner) {
		if (!owners.contains(owner)) {
			owner.addRole(this);
		}
	}

	/**
	 * remove a user by self
	 * 
	 * @param owner
	 */
	public void removeOwner(User owner) {
		if (owners.contains(owner)) {
			owner.removeRole(this);
		}
	}

	public void removeAllOwners() {
		if (owners != null) {
			for (int i = 0; i< owners.size(); i++) {
				User user = owners.get(i);
				if(user.getRoles() != null && user.getRoles().contains(this)) {
					user.getRoles().remove(this);
				}
			}
			owners.clear();
		}
	}
	
	
	public void setOwners(List<User> owners) {
		this.owners = owners;
	}
	
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="sys_role_resource",
			joinColumns ={@JoinColumn(name="role_id_")},
			inverseJoinColumns={@JoinColumn(name="resource_id_")})
	@org.hibernate.annotations.Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	
	/**
	 * add a resource
	 * 
	 * @param resource
	 */
	public void addResource(Resource resource){
		if(!resources.contains(resource)){
			resources.add(resource);
			resource.getRoles().add(this);
		}
	}

	/**
	 * remove a resource
	 * 
	 * @param resource
	 */
	public void removeResource(Resource resource){
		if(resources.contains(resource)){
			resources.remove(resource);	
			resource.getRoles().remove(this);
		}
	}

	@Transient
	public String[] getUserIds() {
		if (this.owners != null && this.owners.size() > 0) {
			String[] idarr = new String[this.owners.size()];
			int i = 0;
			for (User user : this.owners) {
				idarr[i] = String.valueOf(user.getPoid());
				i ++;
			}
			this.userIds = idarr;
		}
		return userIds;
	}

	public void setUserIds(String[] userIds) {
		this.userIds = userIds;
	}

	@Transient
	public String[] getResIds() {
		if (this.resources != null && this.resources.size() > 0) {
			String[] idarr = new String[this.resources.size()];
			int i = 0;
			for (Resource res : this.resources) {
				idarr[i] = String.valueOf(res.getPoid());
				i ++;
			}
			this.resIds = idarr;
		}
		return resIds;
	}

	public void setResIds(String[] resIds) {
		this.resIds = resIds;
	}
}
