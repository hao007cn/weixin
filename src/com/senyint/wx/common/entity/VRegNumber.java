/**
 * VRegNumber.java
 * com.senyint.wx.common.entity
 * Function: 排班时间点
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2015-1-23 		sunzhi
 *
 * Copyright (c) 2015, Senyint All Rights Reserved.
*/

package com.senyint.wx.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.senyint.core.entity.persistence.SurrogateKeyObject;

/**
 * ClassName:VRegNumber
 * Function: 排班时间点
 * Reason:	 排班时间点
 *
 * @author   sunzhi
 * @version  
 * @since    Ver 1.1
 * @Date	 2015-1-23
 *
 * @see 	 
 */

@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "V_REGISTRATION_NUMBER")
public class VRegNumber extends SurrogateKeyObject {
	
	/**
	* @Fields serialVersionUID : 
	*/
	private static final long serialVersionUID = -4678489434265197609L;
	
	/**
	* @Fields number : 号码
	*/
	private String number;
	/**
	* @Fields date : 日期
	*/
	private Date  date;
	/**
	* @Fields serialNo : 序号
	*/
	private String serialNo;
	/**
	* @Fields timePoint : 时间点
	*/
	private String timePoint;
	/**
	* @Fields startTime : 开始时间
	*/
	private String startTime;
	/**
	* @Fields endTime : 结束时间
	*/
	private String endTime;
	/**
	* @Fields limitCount : 限制数量
	*/
	private Integer limitCount;
	/**
	* @Fields orderFlg : 是否预约
	*/
	private Integer orderFlg;
	
	private Integer orderState;
	
	private String orderStateLabel;
	
	@Column(name = "号码")
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	@Column(name = "日期")
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	@Column(name = "序号")
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
	@Column(name = "时间点")
	public String getTimePoint() {
		return timePoint;
	}
	public void setTimePoint(String timePoint) {
		this.timePoint = timePoint;
	}
	
	@Column(name = "开始时间")
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	@Column(name = "结束时间")
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	@Column(name = "限制数量")
	public Integer getLimitCount() {
		return limitCount;
	}
	public void setLimitCount(Integer limitCount) {
		this.limitCount = limitCount;
	}
	
	@Column(name = "是否预约")
	public Integer getOrderFlg() {
		return orderFlg;
	}
	public void setOrderFlg(Integer orderFlg) {
		this.orderFlg = orderFlg;
	}
	@Column(name = "已预约")
	public Integer getOrderState() {
		return orderState;
	}
	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}
	@Transient
	public String getOrderStateLabel() {
		return orderStateLabel;
	}
	public void setOrderStateLabel(String orderStateLabel) {
		this.orderStateLabel = orderStateLabel;
	}
}
