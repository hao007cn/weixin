package com.senyint.wx.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.senyint.core.entity.component.BaseEntity;

/**
 * 科室类别
 * 
 * @author sunzhi
 *
 */
@Entity
@Table(name = "department_type")
public class DepartmentType extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3615213776004908512L;
	
	private String name;
	
	private String imgName;
	
	private String icons;
	
	private Integer sort;
	
	private boolean enabled;
	
	private String remarks;
	
	@Column(name = "name_", length = 64)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "imgName_", length = 125)
	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	@Column(name = "icons_", length = 125)
	public String getIcons() {
		return icons;
	}

	public void setIcons(String icons) {
		this.icons = icons;
	}

	@Column(name = "sort_", length = 2)
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	@Column(name = "enabled_")
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
