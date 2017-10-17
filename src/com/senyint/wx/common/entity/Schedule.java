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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.senyint.common.util.DateUtils;
import com.senyint.core.entity.component.BaseEntity;

/**
 * ClassName:Schedule
 * Function: 排班
 * Reason:	 排班
 *
 * @author   sunzhi
 * @version  
 * @since    Ver 1.1
 * @Date	 2014-12-7
 *
 * @see 	 
 */
@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "schedule")
public class Schedule extends BaseEntity {

	/**
	* @Fields serialVersionUID : 
	*/
	private static final long serialVersionUID = -2981753391467420484L;
	
	/**
	* @Fields date : 日期
	*/
	private Date date;
	/**
	* @Fields doctor : 医生
	*/
	private Doctor doctor;
	/**
	* @Fields ap : 上下午
	*/
	private String ap;
	/**
	* @Fields counts : 号数
	*/
	private Integer counts;
	
	/**
	 * @Fields counts : 剩余号数
	 */
	private Integer leftCounts;
	/**
	 * 专家id
	 */
	private Integer doctorId;
	/**
	 * 专家名称
	 */
	private String doctorName;
	
	/**
	 * 科室id
	 */
	private Integer departmentId;
	/**
	 * 科室名称
	 */
	private String departmentName;
	
	/**
	* @Fields doctorIds : 医生ids 页面参数
	*/
	private String[] doctorIds;
	
	/**
	* @Fields dateStart : 开始日期 页面参数
	*/
	private Date dateStart;
	/**
	* @Fields dateEnd : 结束日期 页面参数
	*/
	private Date dateEnd;
	
	/**
	* @Fields weeks : 周序列  页面参数
	*/
	private String weeks;
	
	/**
	* @Fields weekArr : 周数组  页面参数
	*/
	private String[] weekArr;
	/**
	* @Fields ap : 上下午
	*/
	private String[] aps;
	
	/**
	 * 上午排号数
	 */
	private Integer amCount;
	
	/**
	 * 下午排号数
	 */
	private Integer pmCount;
	
	private String year;

	private String month;
	
	private String day;
	
	private String week;
	
	private Department department;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "date_")
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "departmentId_")
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "doctorId_")
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
	@Column(name = "ap_")
	public String getAp() {
		return ap;
	}
	public void setAp(String ap) {
		this.ap = ap;
	}
	
	@Column(name = "counts_")
	public Integer getCounts() {
		return counts;
	}
	public void setCounts(Integer counts) {
		this.counts = counts;
	}

	@Column(name = "leftCounts_")
	public Integer getLeftCounts() {
		return leftCounts;
	}
	public void setLeftCounts(Integer leftCounts) {
		this.leftCounts = leftCounts;
	}
	
	@Transient
	public Integer getDoctorId() {
		if (this.doctor != null) {
			this.doctorId = this.doctor.getPoid();
		}
		return doctorId;
	}
	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}
	@Transient
	public String getDoctorName() {
		if (this.doctor != null) {
			this.doctorName = this.doctor.getName();
		}
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	@Transient
	public Integer getDepartmentId() {
		if (this.department != null) {
			this.departmentId = this.department.getPoid();
		}
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	@Transient
	public String getDepartmentName() {
		if (this.department != null) {
			this.departmentName = this.department.getName();
		}
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	@JsonIgnore
	@Transient
	public String[] getDoctorIds() {
		return doctorIds;
	}
	public void setDoctorIds(String[] doctorIds) {
		this.doctorIds = doctorIds;
	}
	@JsonIgnore
	@Transient
	public Date getDateStart() {
		return dateStart;
	}
	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}
	@JsonIgnore
	@Transient
	public Date getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}
	@JsonIgnore
	@Transient
	public String getWeeks() {
		return weeks;
	}
	public void setWeeks(String weeks) {
		this.weeks = weeks;
	}
	@JsonIgnore
	@Transient
	public String[] getWeekArr() {
		return weekArr;
	}
	public void setWeekArr(String[] weekArr) {
		this.weekArr = weekArr;
	}
	@JsonIgnore
	@Transient
	public String[] getAps() {
		return aps;
	}
	public void setAps(String[] aps) {
		this.aps = aps;
	}
	@Transient
	public Integer getAmCount() {
		return amCount;
	}
	public void setAmCount(Integer amCount) {
		this.amCount = amCount;
	}
	@Transient
	public Integer getPmCount() {
		return pmCount;
	}
	public void setPmCount(Integer pmCount) {
		this.pmCount = pmCount;
	}
	@Transient
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	@Transient
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	@Transient
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getWeek() {
		if (this.date != null) {
			SimpleDateFormat format = new SimpleDateFormat("E", Locale.CHINA);
			week = DateUtils.format(this.date, format);
		}
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
}
