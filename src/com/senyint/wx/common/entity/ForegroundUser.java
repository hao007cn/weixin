/**
 * ForegroundUser.java
 * com.senyint.wx.common.entity
 * Function: 网络用户
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2014年12月2日 		gjp
 *
 * Copyright (c) 2014, senyint All Rights Reserved.
*/
package com.senyint.wx.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.senyint.common.web.springmvc.JsonDateFormat;
import com.senyint.core.entity.persistence.SurrogateKeyObject;

/**
 * @Type: ForegroundUser
 * @Description: 网络用户
 *
 * @Company: senyint (Dalian) Co., Ltd
 * @author   gjp
 * @date     2014年12月2日
 * @version  1.0
 *
 */
public class ForegroundUser extends SurrogateKeyObject{

	private static final long serialVersionUID = 5108256828623239854L;
	private String name;
	private String sex;
	private Boolean state;
	private String mobile;
	private String cardid;
	private String hcardid;
	private String remarks;
	private Date updatedate;
	private Date regdate;
	private Boolean subflag;
	private Integer subuserid;
	private String openid;
	private Boolean locked;
	private String lockedReason;
	private Integer missedTimes;
	private Boolean deleteFlag;
	// 在his系统里的id
	private String hisId;
	private Boolean cantNotOrderFlag;
	private Integer modifiedCount;
	private Long patietId;
	/**
	 * 姓名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 姓名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 性别
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * 性别
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * 状态
	 */
	public Boolean getState() {
		return state;
	}
	/**
	 * 状态
	 */
	public void setState(Boolean state) {
		this.state = state;
	}
	/**
	 * 电话号
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 电话号
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 证件号
	 */
	public String getCardid() {
		return cardid;
	}
	/**
	 * 证件号
	 */
	public void setCardid(String cardid) {
		if(!cardid.isEmpty()){
			this.cardid = cardid.trim().toUpperCase();
		}else {
			this.cardid = cardid;
		}		
	}
	/**
	 * 医院证件号
	 */
	public String getHcardid() {
		return hcardid;
	}
	/**
	 * 医院证件号
	 */
	public void setHcardid(String hcardid) {
		this.hcardid = hcardid;
	}
	/**
	 * 备注
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * 备注
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * 修改时间
	 */
	@JsonSerialize(using=JsonDateFormat.class)
	public Date getUpdatedate() {
		return updatedate;
	}
	/**
	 * 修改时间
	 */
	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}
	/**
	 * 注册时间
	 */
	@JsonSerialize(using=JsonDateFormat.class)
	public Date getRegdate() {
		return regdate;
	}
	/**
	 * 注册时间
	 */
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	/**
	 * 子用户归属用户ID
	 */
	public Integer getSubuserid() {
		return subuserid;
	}
	/**
	 * 子用户属于子用户ID
	 */
	public void setSubuserid(Integer subuserid) {
		this.subuserid = subuserid;
	}
	/**
	 * 是否是子用户
	 */
	public Boolean getSubflag() {
		return subflag;
	}
	/**
	 * 是否是子用户
	 */
	public void setSubflag(Boolean subflag) {
		this.subflag = subflag;
	}
	/**
	 * @return 微信认证标识
	 */
	public String getOpenid() {
		return openid;
	}
	/**
	 * @param 微信认证标识
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}

	/**
	 * 是否已被锁
	 * @return
	 */
	public Boolean getLocked() {
		return locked;
	}
	public void setLocked(Boolean locked) {
		this.locked = locked;
	}
	/**
	 * 被封锁原因
	 * @return
	 */
	public String getLockedReason() {
		return lockedReason;
	}
	public void setLockedReason(String lockedReason) {
		this.lockedReason = lockedReason;
	}
	/**
	 * 爽约次数
	 * 
	 * @return
	 */
	public Integer getMissedTimes() {
		return missedTimes;
	}
	public void setMissedTimes(Integer missedTimes) {
		this.missedTimes = missedTimes;
	}
	/**
	 * @return 删除标识
	 */
	public Boolean getDeleteFlag() {
		return deleteFlag;
	}
	/**
	 * @param 删除标识
	 */
	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getHisId() {
		return hisId;
	}
	public void setHisId(String hisId) {
		this.hisId = hisId;
	}
	
	public Boolean getCantNotOrderFlag() {
		return cantNotOrderFlag;
	}
	public void setCantNotOrderFlag(Boolean cantNotOrderFlag) {
		this.cantNotOrderFlag = cantNotOrderFlag;
	}
	public Integer getModifiedCount() {
		return modifiedCount;
	}
	public void setModifiedCount(Integer modifiedCount) {
		this.modifiedCount = modifiedCount;
	}
	
	
	/**
	 * 病人id
	 * @return
	 */
	public Long getPatietId() {
		return patietId;
	}
	public void setPatietId(Long patietId) {
		this.patietId = patietId;
	}
}
