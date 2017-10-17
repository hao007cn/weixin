package com.senyint.wx.admin.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.senyint.common.web.springmvc.JsonDateFormat;
import com.senyint.common.web.springmvc.JsonSexFormat;
import com.senyint.core.entity.component.BaseEntity;
import com.senyint.core.entity.component.SexType;

/**
 * 使用者
 * 
 * @version
 * @author
 * 
 */
/* hibernate cache setting
@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
*/
@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "sys_user")
@org.hibernate.annotations.BatchSize(size = 20)
public class User extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private String name;
	private SexType sex;
	private Boolean enabled;
	private Date lastlogintime;
	private List<Role> roles = new ArrayList<Role>();

	/**
	 * 取得拥有角色
	 * 
	 * @return
	 */
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
	@JoinTable(name="sys_user_role",
			joinColumns ={@JoinColumn(name="user_id_")},
			inverseJoinColumns={@JoinColumn(name="role_id_")})
	@org.hibernate.annotations.Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
	@org.hibernate.annotations.Cascade(value = org.hibernate.annotations.CascadeType.DELETE)
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	/**
	 * 加入一個角色
	 * 
	 * @param role
	 */
	public void addRole(Role role){
		if(!roles.contains(role)){
			roles.add(role);
			role.getOwners().add(this);
		}
	}

	/**
	 * 移除一個角色
	 * 
	 * @param role
	 */
	public void removeRole(Role role){
		if(roles.contains(role)){
			roles.remove(role);	
			role.getOwners().remove(this);
		}
	}
	
	/**
	 * 使用者登录帐号
	 * 
	 * @return
	 */
	@Column(name = "username_", length = 20)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
	@Column(name = "password_", length = 50)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 姓名
	 * 
	 * @return
	 */
	@Column(name = "name_")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 性别
	 * 
	 * @return
	 */
	@JsonSerialize(using=JsonSexFormat.class)
	@Enumerated
	@Column(name = "sex_")
	public SexType getSex() {
		return sex;
	}

	public void setSex(SexType sex) {
		this.sex = sex;
	}


	/**
	 * 是否启用
	 * 
	 * @return
	 */
	@Column(name = "enabled_")
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * 最后登录时间
	 */
	@JsonSerialize(using=JsonDateFormat.class)
	@Column(name = "lastlogintime_")
	public Date getLastlogintime() {
		return lastlogintime;
	}

	/**
	 * 最后登录时间
	 */
	public void setLastlogintime(Date lastlogintime) {
		this.lastlogintime = lastlogintime;
	}
}
