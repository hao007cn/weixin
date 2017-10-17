package com.senyint.wx.mobile.entity;

import java.util.Date;


/**
 * @author Administrator
 * --list头信息
 *
 */
public class OutpatientLisSampleInfo {
	
	/**
	 * 挂号单号
	 */
	private String ghdh;

	/**
	 * 病人id
	 */
	private Long patientID;
	
	/**
	 * 姓名
	 */
	private String patientName;
	
	/**
	 * 身份证号
	 */
	private String sfzh;
	
	/**
	 * 看诊科室
	 */
	private Long kzks;
	/**
	 * 科室名
	 */
	private String ksname;
	/**
	 * 最终看诊医生
	 */
	private String zzkzys;
	/**
	 * 执行状态
	 */
	private Integer executeState;
	/**
	 * 看诊日期
	 */
	private Date kzdate;
	/**
	 * 预约
	 */
	private Integer yuyue;
	/**
	 * 预约方式
	 */
	private String yuyuefs;
	/**
	 * 登记时间
	 */
	private Date djdate;
	
	private String stateLabel;
	public String getGhdh() {
		return ghdh;
	}
	public void setGhdh(String ghdh) {
		this.ghdh = ghdh;
	}
	public Long getPatientID() {
		return patientID;
	}
	public void setPatientID(Long patientID) {
		this.patientID = patientID;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getSfzh() {
		return sfzh;
	}
	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}
	public Long getKzks() {
		return kzks;
	}
	public void setKzks(Long kzks) {
		this.kzks = kzks;
	}
	public String getKsname() {
		return ksname;
	}
	public void setKsname(String ksname) {
		this.ksname = ksname;
	}
	public String getZzkzys() {
		return zzkzys;
	}
	public void setZzkzys(String zzkzys) {
		this.zzkzys = zzkzys;
	}
	public Integer getExecuteState() {
		return executeState;
	}
	public void setExecuteState(Integer executeState) {
		this.executeState = executeState;
	}
	public Date getKzdate() {
		return kzdate;
	}
	public void setKzdate(Date kzdate) {
		this.kzdate = kzdate;
	}
	public Integer getYuyue() {
		return yuyue;
	}
	public void setYuyue(Integer yuyue) {
		this.yuyue = yuyue;
	}
	public String getYuyuefs() {
		return yuyuefs;
	}
	public void setYuyuefs(String yuyuefs) {
		this.yuyuefs = yuyuefs;
	}
	public Date getDjdate() {
		return djdate;
	}
	public void setDjdate(Date djdate) {
		this.djdate = djdate;
	}
	public String getStateLabel() {
		if (this.executeState > 0) {
			stateLabel = "结束";
		} else {
			stateLabel = "待就诊";
		}
		return stateLabel;
	}
	public void setStateLabel(String stateLabel) {
		this.stateLabel = stateLabel;
	}
}
