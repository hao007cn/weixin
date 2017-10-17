package com.senyint.core.entity.component;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.senyint.common.web.springmvc.JsonDateFormat;

/**
 */
@Embeddable
public class ModifyInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String createUserId;
	private String createUserName;
	private Date createDate;
	private String modifyUserId;
	private String modifyUserName;
	private Date modifyDate;
	private String createDepartmentName;
	private String modifyDepartmentName;

	/**
	 * default constructor
	 */
	public ModifyInfo() {
		super();
	}
	
	@Column(name = "createDepartmentName", length = 20, nullable = true, updatable = true)
	public String getCreateDepartmentName() {
		return createDepartmentName;
	}

	public void setCreateDepartmentName(String createDepartmentName) {
		this.createDepartmentName = createDepartmentName;
	}

	@Column(name = "modifyDepartmentName", length = 20, nullable = true)
	public String getModifyDepartmentName() {
		return modifyDepartmentName;
	}

	public void setModifyDepartmentName(String modifyDepartmentName) {
		this.modifyDepartmentName = modifyDepartmentName;
	}

	/**
	 * 新增人员帐号
	 * 
	 * @return
	 */
	@Column(name = "createUserId", length = 50, nullable = true, updatable = true)
	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	/**
	 * 新增人员姓名
	 * 
	 * @return
	 */
	@Column(name = "createUserName", columnDefinition = "nvarchar(100)", length = 100, nullable = true, updatable = true)
	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * 新增日期时间
	 * 
	 * @return
	 */
	@JsonSerialize(using=JsonDateFormat.class)
	@Column(name = "createDate", nullable = true, updatable = true)
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 修改日期时间
	 * 
	 * @return
	 */
	@JsonSerialize(using=JsonDateFormat.class)
	@Column(name = "modifyDate")
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	/**
	 * 修改人员帐号
	 * 
	 * @return
	 */
	@Column(name = "modifyUserId", length = 50)
	public String getModifyUserId() {
		return modifyUserId;
	}

	public void setModifyUserId(String modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

	/**
	 * 修改人员姓名
	 * 
	 * @return
	 */
	@Column(name = "modifyUserName", columnDefinition = "nvarchar(50)", length = 50)
	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	/**
	 * make a copy instance having the same creation, modify information is
	 * remaining null.
	 * 
	 * @return
	 */
	public ModifyInfo copyCreateInfo() {
		ModifyInfo copy = new ModifyInfo();
		copy.setCreateUserId(this.getCreateUserId());
		copy.setCreateUserName(this.getCreateUserName());
		copy.setCreateDate(this.getCreateDate());
		copy.setCreateDepartmentName(this.getCreateDepartmentName());
		return copy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result
				+ ((createUserId == null) ? 0 : createUserId.hashCode());
		result = prime * result
				+ ((createUserName == null) ? 0 : createUserName.hashCode());
		result = prime * result
				+ ((createDepartmentName == null) ? 0 : createDepartmentName.hashCode());
		result = prime * result
				+ ((modifyDate == null) ? 0 : modifyDate.hashCode());
		result = prime * result
				+ ((modifyUserId == null) ? 0 : modifyUserId.hashCode());
		result = prime * result
				+ ((modifyUserName == null) ? 0 : modifyUserName.hashCode());
		result = prime * result
				+ ((modifyDepartmentName == null) ? 0 : modifyDepartmentName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ModifyInfo other = (ModifyInfo) obj;
		if (createDate == null) {
			if (other.createDate != null)
				return false;
		} else if (!createDate.equals(other.createDate))
			return false;
		if (createUserId == null) {
			if (other.createUserId != null)
				return false;
		} else if (!createUserId.equals(other.createUserId))
			return false;
		if (createUserName == null) {
			if (other.createUserName != null)
				return false;
		} else if (!createUserName.equals(other.createUserName))
			return false;
		if (createDepartmentName == null) {
			if (other.createDepartmentName != null)
				return false;
		} else if (!createDepartmentName.equals(other.createDepartmentName))
			return false;
		if (modifyDate == null) {
			if (other.modifyDate != null)
				return false;
		} else if (!modifyDate.equals(other.modifyDate))
			return false;
		if (modifyUserId == null) {
			if (other.modifyUserId != null)
				return false;
		} else if (!modifyUserId.equals(other.modifyUserId))
			return false;
		if (modifyUserName == null) {
			if (other.modifyUserName != null)
				return false;
		} else if (!modifyUserName.equals(other.modifyUserName))
			return false;
		if (modifyDepartmentName == null) {
			if (other.modifyDepartmentName != null)
				return false;
		} else if (!modifyDepartmentName.equals(other.modifyDepartmentName))
			return false;
		return true;
	}

}
