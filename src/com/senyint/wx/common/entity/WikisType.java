package com.senyint.wx.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.senyint.core.entity.component.BaseEntity;

/**
 * 健康百科类别
 * 
 * @author wangchao
 * 
 */

@Entity
@Table(name = "wikis_type")
public class WikisType extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 代表图标
	 */
	private String icon;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 是否显示
	 */
	private boolean enabled;

	/**
	 * 背景色
	 */
	private String backColor;

	@Column(name = "name_", length = 64)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "icon_", length = 125)
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "sort_", length = 2)
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Column(name = "remark_")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "enabled_")
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Column(name = "backColor_", length = 20)
	public String getBackColor() {
		return backColor;
	}

	public void setBackColor(String backColor) {
		this.backColor = backColor;
	}
}
