/**
 * Attention.java
 * com.senyint.wx.common.entity
 * Function: 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2014-12-23 		sunzhi
 *
 * Copyright (c) 2014, Senyint All Rights Reserved.
*/

package com.senyint.wx.common.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.senyint.core.entity.component.BaseEntity;

/**
 * ClassName:Attention
 * Function: 关注
 * Reason:	 关注
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
@Table(name="attention")
@org.hibernate.annotations.BatchSize(size = 20)
public class Attention extends BaseEntity{

	/**
	* @Fields serialVersionUID : 
	*/
	private static final long serialVersionUID = -3210963597144004091L;
	
	private ForegroundUser user;
	private Doctor doctor;
//	@OneToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "userId_")
	public ForegroundUser getUser() {
		return user;
	}
	public void setUser(ForegroundUser user) {
		this.user = user;
	}
	
//	@OneToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "doctorId_")
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
}
