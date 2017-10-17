/**
 * Schedule.java
 * com.senyint.wx.common.entity
 * Function: 排班
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2014-12-7 		sunzhi
 *
 * Copyright (c) 2014, Senyint All Rights Reserved.
*/

package com.senyint.wx.common.entity;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.senyint.core.entity.persistence.SurrogateKeyObject;




@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "V_SCHEDULE")
public class VSchedule extends SurrogateKeyObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 号码
	 */
	private String number;
	/**
	 * 号类
	 */
	private String numberType;
	/**
	 * 科室ID
	 */
	private Integer departmentId;
	/**
	 * 项目ID
	 */
	private Integer projectId;
	/**
	 * 医生ID
	 */
	private Integer doctorId;
	/**
	 * 医生姓名
	 */
	private String doctorName;
	/**
	 * 日期
	 */
	private Date date;
	/**
	 * 星期
	 */
	private String week;
	/**
	 * 排班
	 */
	private String schedules;
	/**
	 * 限号数
	 */
	private Integer limitNums;
	/**
	 * 限约数
	 */
	private Integer limitFee;
	/**
	 * 已约数
	 */
	private Integer feeToOver;
	
	/**
	 * 上午预约数
	 */
	private Integer amCounts;
	
	/**
	 * 下午预约数
	 */
	private Integer pmCounts;
	
	/**
	 * 全天预约数
	 */
	private Integer alldayCounts;
	
	/**
	 * 是否是全天
	 */
	private Boolean isAllday;
	
	private Integer fee;
	
	@Column(name= "号码" )
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	@Column(name= "号类" )
	public String getNumberType() {
		return numberType;
	}
	public void setNumberType(String numberType) {
		this.numberType = numberType;
	}
	
	@Column(name= "医生姓名" )
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	
	@Column(name= "日期" )
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	@Column(name= "星期" )
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	
	@Column(name= "排班" )
	public String getSchedules() {
		return schedules;
	}
	
	public void setSchedules(String schedules) {
		this.schedules = schedules;
	}
	@Column(name= "科室ID" )
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	@Column(name= "项目ID" )
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	@Column(name= "医生ID" )
	public Integer getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}
	
	@Column(name= "限号数" )
	public Integer getLimitNums() {
		return limitNums;
	}
	public void setLimitNums(Integer limitNums) {
		this.limitNums = limitNums;
	}
	@Column(name= "限约数" )
	public Integer getLimitFee() {
		return limitFee;
	}
	public void setLimitFee(Integer limitFee) {
		this.limitFee = limitFee;
	}
	@Column(name= "已约数" )
	public Integer getFeeToOver() {
		return feeToOver;
	}
	public void setFeeToOver(Integer feeToOver) {
		this.feeToOver = feeToOver;
	}
	
	@Transient
	public Integer getAmCounts() {
		return amCounts;
	}
	public void setAmCounts(Integer amCounts) {
		this.amCounts = amCounts;
	}
	@Transient
	public Integer getPmCounts() {
		return pmCounts;
	}
	public void setPmCounts(Integer pmCounts) {
		this.pmCounts = pmCounts;
	}
	@Transient
	public Integer getAlldayCounts() {
		return alldayCounts;
	}
	public void setAlldayCounts(Integer alldayCounts) {
		this.alldayCounts = alldayCounts;
	}
	@Transient
	public Boolean getIsAllday() {
		return isAllday;
	}
	public void setIsAllday(Boolean isAllday) {
		this.isAllday = isAllday;
	}
	
	@Column(name= "挂号金额")
	public Integer getFee() {
		return fee;
	}
	public void setFee(Integer fee) {
		this.fee = fee;
	}
}
