package com.senyint.wx.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.senyint.core.entity.persistence.SurrogateKeyObject;
import com.senyint.wx.common.utils.ArgumentUtil;

@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "access_statistics")
public class AccessStatistics extends SurrogateKeyObject {
	/**
	 * 在线访问统计表
	 */
	private static final long serialVersionUID = 1L;
	private Integer user_id;
	private String user_name;
	private Date visit_time;
	private String visit_module;
	/**
	 * 访问人id
	 * @return
	 */
	@Column(name = "user_id_")
	public Integer getUser_id() {
		return user_id;
	}
	/**
	 * 访问人id
	 * @return
	 */
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	/**
	 * 访问人姓名
	 * @param user_name
	 */
	@Column(name = "user_name_", length = 20)
	public String getUser_name() {
		return user_name;
	}
	/**
	 * 访问人姓名
	 * @param user_name
	 */
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	/**
	 * 访问时间
	 * @return
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "visit_time_")
	public Date getVisit_time() {
		return visit_time;
	}
	/**
	 * 访问时间
	 * @return
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "visit_time_")
	public void setVisit_time(Date visit_time) {
		this.visit_time = visit_time;
	}
	/**
	 * 访问模块
	 * @return
	 */
	@Column(name = "visit_module_", length = 20)
	public String getVisit_module() {
		return visit_module;
	}
	/**
	 * 访问模块
	 * @return
	 */
	public void setVisit_module(String visit_module) {
		this.visit_module = visit_module;
	}
	/**
	 * 用户访问记录统计
	 * @param user_id 访问用户id
	 * @param user_name 访问用户name
	 * @param visit_module 用户访问模块
	 */
	public void setAccessStatistics(ForegroundUser fu,String visit_module) {
		this.user_id=fu.getPoid();
		this.user_name=fu.getName();
		this.visit_time=ArgumentUtil.getSysDate();
		this.visit_module=visit_module;
	}
	
}
