package com.senyint.core.entity.component;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.senyint.common.web.springmvc.JsonDateFormat;
import com.senyint.core.entity.persistence.SurrogateKeyObject;

@MappedSuperclass
public class BaseEntity extends SurrogateKeyObject  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8161700739965414138L;
	
	/**
	 * 删除标记
	 */
	private Boolean deleteFlg = false;
	private String createUserId;
	private String createUserName;
	private Date createDate;
	private String modifyUserId;
	private String modifyUserName;
	private Date modifyDate;
	private String createDepartmentName;
	private String modifyDepartmentName;
	
	@Column(name="create_user_id_", length=50)
	public String getCreateUserId() {
		return createUserId;
	}


	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name="create_user_name_", length=50)
	public String getCreateUserName() {
		return createUserName;
	}


	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	@JsonSerialize(using=JsonDateFormat.class)
	@Column(name="create_date_", length=50)
	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name="modify_user_id_", length=50)
	public String getModifyUserId() {
		return modifyUserId;
	}


	public void setModifyUserId(String modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

	@Column(name="modify_user_name_", length=50)
	public String getModifyUserName() {
		return modifyUserName;
	}


	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}
	@JsonSerialize(using=JsonDateFormat.class)
	@Column(name="modify_date_")
	public Date getModifyDate() {
		return modifyDate;
	}


	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	@Column(name="create_department_name_", length=50)
	public String getCreateDepartmentName() {
		return createDepartmentName;
	}

	public void setCreateDepartmentName(String createDepartmentName) {
		this.createDepartmentName = createDepartmentName;
	}

	@Column(name="modify_department_name_", length=50)
	public String getModifyDepartmentName() {
		return modifyDepartmentName;
	}


	public void setModifyDepartmentName(String modifyDepartmentName) {
		this.modifyDepartmentName = modifyDepartmentName;
	}


	@Column(name="delete_flg_", columnDefinition="int default 0")
	public Boolean getDeleteFlg() {
		return deleteFlg;
	}


	public void setDeleteFlg(Boolean deleteFlg) {
		this.deleteFlg = deleteFlg;
	}
}
