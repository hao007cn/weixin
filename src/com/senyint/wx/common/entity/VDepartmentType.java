package com.senyint.wx.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.senyint.core.entity.persistence.SurrogateKeyObject;

@Entity
@Table(name = "v_department_type")
public class VDepartmentType extends SurrogateKeyObject{
	
	private static final long serialVersionUID = 8307285089634463759L;
	private String sort;
	private String name;
	private String icons;
	private String enabled;
	private String remarks;
	@Column(name = "sort")
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	@Column(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "icons")
	public String getIcons() {
		return icons;
	}
	public void setIcons(String icons) {
		this.icons = icons;
	}
	@Column(name = "enabled")
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	@Column(name = "remarks")
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
