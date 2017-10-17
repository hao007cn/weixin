package com.senyint.wx.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.senyint.core.entity.component.BaseEntity;
import com.senyint.core.entity.component.CodeNode;

/**
 * 文章类别
 * 
 * @author wangchao
 * 
 */

@Entity
@Table(name = "article_type")
public class ArticleType extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 是否显示
	 */
	private boolean enabled;
	
	/**
	 * 父节点标记
	 */
	private Integer parentflg;
	
	/**
	 * 父id
	 */
	private Integer parentid; 

	@Column(name = "name_", length = 64)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "sort_", length = 2)
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Column(name = "remark_")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "enabled_")
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	@Column(name="parentid_")
	public Integer getParentid() {
		return parentid;
	}

	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}

	public Integer getParentflg() {
		return parentflg;
	}

	public void setParentflg(Integer parentflg) {
		this.parentflg = parentflg;
	}
	
}
