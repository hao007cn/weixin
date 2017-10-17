package com.senyint.wx.admin.bean;

import java.io.Serializable;

public class RoleBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 146422036143825264L;
	
	private Integer poid;
	private String name;
	private Integer parentId;
	private String desc;
	public Integer getPoid() {
		return poid;
	}
	public void setPoid(Integer poid) {
		this.poid = poid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
