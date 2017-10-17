package com.senyint.wx.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.senyint.core.entity.persistence.SurrogateKeyObject;
/**
 * 微信用户和服务器用户关联
 * @author gjp
 *
 */
@Entity
@Table(name="user_relevance")
public class UserRelevance extends SurrogateKeyObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2952616490376272889L;
	private Long patientId;
	private String openId;
	
	/**
	 * @return
	 */
	@Column(name="patient_id_",length=18)
	public Long getPatientId() {
		return patientId;
	}
	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}
	/**
	 * @return
	 */
	@Column(name="open_id_",length=50)
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	
	
	
}
