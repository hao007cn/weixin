/**
 * UserModifyLog.java
 * com.senyint.wx.common.entity
 * Function: 记录前台用户修改信息
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2014-12-23 		sunzhi
 *
 * Copyright (c) 2014, Senyint All Rights Reserved.
*/

package com.senyint.wx.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.senyint.core.entity.component.BaseEntity;

/**
 * ClassName: UserModifyLog
 * Function: 前台用户修改记录
 * Reason:	 前台用户修改记录
 *
 * @author   sunzhi
 * @version  
 * @since    Ver 1.1
 * @Date	 2014-12-23
 *
 * @see 	 
 */
@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name="fuser_modify_log")
@org.hibernate.annotations.BatchSize(size = 20)
public class UserModifyLog extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6835968072596456134L;
	
	private Long optUserId;
	
	private Long userId;
	
	private String beforeValue;
	
	private String afterValue;
	
	private Date modifyTime;

	@Column(name="optUserId_")
	public Long getOptUserId() {
		return optUserId;
	}

	public void setOptUserId(Long optUserId) {
		this.optUserId = optUserId;
	}

	@Column(name="userId_")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name="beforeValue_", length=200)
	public String getBeforeValue() {
		return beforeValue;
	}

	public void setBeforeValue(String beforeValue) {
		this.beforeValue = beforeValue;
	}

	@Column(name="afterValue_", length=200)
	public String getAfterValue() {
		return afterValue;
	}

	public void setAfterValue(String afterValue) {
		this.afterValue = afterValue;
	}
	
	@Column(name="modifyTime_")
	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}
