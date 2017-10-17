/**
 * Order.java
 * com.senyint.wx.common.entity
 * Function: 预约记录
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2014-12-7 		sunzhi
 *
 * Copyright (c) 2014, Senyint All Rights Reserved.
*/
package com.senyint.wx.common.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.senyint.core.entity.component.AuditType;
import com.senyint.core.entity.component.BaseEntity;
import com.senyint.core.entity.component.OrderPayState;
import com.senyint.core.entity.component.OrderState;

/**
 * ClassName: Order
 * Function: 预约记录
 * Reason:	 预约记录
 *
 * @author   sunzhi
 * @version  
 * @since    Ver 1.1
 * @Date	 2014-12-7
 *
 * @see 
 */
@Entity
@Table(name = "orders_")
public class Orders extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2769751551062062838L;
	
	public static final String OPT_TYPE_APPLAY = "预约";
	
	public static final String OPT_TYPE_CANCEL = "取消";
	
	/**
	 * 操作类别
	 */
	private String optType;
	
	/**
	 * 病人id
	 */
	private String patientId;
	
	/**
	 * 病人id
	 */
	private String patientHisId;
	
	/**
	 * 号码
	 */
	private String snumber;
	
	/**
	 * 序号
	 */
	private String serialNo;
	
	/**
	 * 单据号
	 */
	private String formNo;
	
	private Integer userId;
	
	private String userHisId;
	
	/**
	 * 用户姓名
	 */
	private String userName;
	/**
	 * 用户身份证号
	 */
	private String userCardid;
	/**
	 * 就诊人姓名
	 */
	private String patientName;
	/**
	 * 就诊人身份证号
	 */
	private String patientCardid;
	/**
	 * 就诊人健康卡号
	 */
	private String patientHcardid;
	
	private String departmentName;
	
	private String doctorName;
	
	/**
	 * 取消时间
	 */
	private Date cancelTime;
	/**
	 * 就诊人姓名
	 */
	private String cancelUserName;
	/**
	 * 就诊人身份证号
	 */
	private String cancelUserCardid;
	/**
	 * 发生时间
	 */
	private Date aplTime;
	/**
	 * 关闭订单用户名
	 */
	private String closerUserName;
	/**
	 * 关闭订单用户姓名
	 */
	private String closerName;
	/**
	 * 支付时间
	 */
	private Date payTime;
	/**
	 * 退款时间
	 */
	private Date refundTime;
	/**
	 * 订单状态
	 */
	private OrderState state;
	/**
	 * 支付状态
	 */
	private OrderPayState payState;

	/**
	 * 审核状态
	 */
	private AuditType auditType;
	
	/**
	 * 备注
	 */
	private String remarks;
	
	/**
	 * 挂号费
	 */
	private BigDecimal fee;
	
	private String stateLabel;
	private String payStateLabel;
	private String auditTypeLabel;
	
	@Column(name = "opt_type_")
	public String getOptType() {
		return optType;
	}
	public void setOptType(String optType) {
		this.optType = optType;
	}
	@Column(name = "apltime_")
	public Date getAplTime() {
		return aplTime;
	}
	public void setAplTime(Date aplTime) {
		this.aplTime = aplTime;
	}
	
	@Column(name = "cancel_time_")
	public Date getCancelTime() {
		return cancelTime;
	}
	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}
	
	@Column(name = "pay_time_")
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	
	@Column(name = "refund_time_")
	public Date getRefundTime() {
		return refundTime;
	}
	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}
	
	@Column(name = "state_")
	@Enumerated
	public OrderState getState() {
		return state;
	}
	public void setState(OrderState state) {
		this.state = state;
	}
	
	@Column(name = "pay_state_")
	@Enumerated
	public OrderPayState getPayState() {
		return payState;
	}
	public void setPayState(OrderPayState payState) {
		this.payState = payState;
	}
	
	@Column(name = "user_name_")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name = "user_cardid_")
	public String getUserCardid() {
		return userCardid;
	}
	public void setUserCardid(String userCardid) {
		this.userCardid = userCardid;
	}
	
	@Column(name = "patient_name_")
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	
	@Column(name = "patient_cardid_")
	public String getPatientCardid() {
		return patientCardid;
	}
	public void setPatientCardid(String patientCardid) {
		this.patientCardid = patientCardid;
	}
	
	@Column(name = "patient_hcardid_")
	public String getPatientHcardid() {
		return patientHcardid;
	}
	public void setPatientHcardid(String patientHcardid) {
		this.patientHcardid = patientHcardid;
	}
	
	@Column(name = "cancel_user_name_")
	public String getCancelUserName() {
		return cancelUserName;
	}
	public void setCancelUserName(String cancelUserName) {
		this.cancelUserName = cancelUserName;
	}
	
	@Column(name = "cancel_user_cardid_")
	public String getCancelUserCardid() {
		return cancelUserCardid;
	}
	public void setCancelUserCardid(String cancelUserCardid) {
		this.cancelUserCardid = cancelUserCardid;
	}
	
	@Column(name = "closer_user_name_")
	public String getCloserUserName() {
		return closerUserName;
	}
	public void setCloserUserName(String closerUserName) {
		this.closerUserName = closerUserName;
	}
	
	@Column(name = "closer_name_")
	public String getCloserName() {
		return closerName;
	}
	public void setCloserName(String closerName) {
		this.closerName = closerName;
	}
	
	@Column(name = "audit_")
	@Enumerated
	public AuditType getAuditType() {
		return auditType;
	}
	public void setAuditType(AuditType auditType) {
		this.auditType = auditType;
	}
	
	@Transient
	public String getStateLabel() {
		if (this.state != null) {
			this.stateLabel = this.state.getDescription();
		}
		return stateLabel;
	}
	public void setStateLabel(String stateLabel) {
		this.stateLabel = stateLabel;
	}
	
	@Transient
	public String getPayStateLabel() {
		if (this.payState != null) {
			this.payStateLabel = this.payState.getDescription();
		}
		return payStateLabel;
	}
	public void setPayStateLabel(String payStateLabel) {
		this.payStateLabel = payStateLabel;
	}
	
	@Transient
	public String getAuditTypeLabel() {
		if (this.auditType != null) {
			this.auditTypeLabel = this.auditType.getDescription();
		}
		return auditTypeLabel;
	}
	public void setAuditTypeLabel(String auditTypeLabel) {
		this.auditTypeLabel = auditTypeLabel;
	}
	
	@Column(name="remarks_")
	@Type(type="text")
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	@Column(name="fee_")
	public BigDecimal getFee() {
		return fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	@Column(name="patient_id_")
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	@Column(name="patient_his_id_")
	public String getPatientHisId() {
		return patientHisId;
	}
	public void setPatientHisId(String patientHisId) {
		this.patientHisId = patientHisId;
	}
	@Column(name="snumber_")
	public String getSnumber() {
		return snumber;
	}
	public void setSnumber(String snumber) {
		this.snumber = snumber;
	}
	@Column(name="serial_no_")
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	@Column(name="form_no_")
	public String getFormNo() {
		return formNo;
	}
	public void setFormNo(String formNo) {
		this.formNo = formNo;
	}
	@Column(name="user_id_")
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	@Column(name="user_his_id_")
	public String getUserHisId() {
		return userHisId;
	}
	public void setUserHisId(String userHisId) {
		this.userHisId = userHisId;
	}
	
	@Column(name="department_name_")
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	@Column(name="doctor_name_")
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
}
