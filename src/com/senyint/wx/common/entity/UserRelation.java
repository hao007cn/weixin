package com.senyint.wx.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.senyint.core.entity.persistence.SurrogateKeyObject;
/**
 * 用户亲属关系表
 * @author gjp
 *
 */
@Entity
@Table(name="user_relation")
public class UserRelation extends SurrogateKeyObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1837621181408158429L;
	private Long patientId;
	private Long patiendChildId;
	
	@Column(name="patient_id_")
	public Long getPatientId() {
		return patientId;
	}
	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}
	
	@Column(name="patiend_child_id_")
	public Long getPatiendChildId() {
		return patiendChildId;
	}
	public void setPatiendChildId(Long patiendChildId) {
		this.patiendChildId = patiendChildId;
	}
	
	

	
}
