package com.senyint.wx.common.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.senyint.core.entity.component.BaseEntity;
/**
 * @author wangchao
 *
 */
@Entity
@Table(name = "job_title")
public class JobTitle extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 称职名
	 */
	private String name;
	
	/**
	 * 挂号费
	 */
	private BigDecimal fee;
	
	/**
	 * 是否启用
	 */
	private boolean enabled = false;
	
	@Column(name = "name_",length = 100)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "fee_")
	public BigDecimal getFee() {
		return fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	
	@Column(name="enabled_", columnDefinition="int default 0")
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}
