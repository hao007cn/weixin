package com.senyint.wx.admin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.senyint.core.entity.component.BaseEntity;
import com.senyint.wx.admin.entity.component.ArgumentType;
/**
 * @author wangchao
 *
 */
@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name ="sys_argument")
@org.hibernate.annotations.BatchSize(size = 20)
public class Argument extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 参数名
	 */
	private String name;
	
	/**
	 * 参数代码
	 */
	private String code;
	
	/**
	 * 参数值
	 */
	private String value;
	
	/**
	 * 参数类型
	 */
	private ArgumentType argumentType;
	
	/**
	 * 能否修改
	 */
	private Boolean isModify = false ;

	/**
	 * 默认值
	 */
	private String defaultValue;
	
	/**
	 * 描述
	 */
	private String description;
	
	@Column(name = "name_")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "code_")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name = "value_")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "argument_type_")
	public ArgumentType getArgumentType() {
		return argumentType;
	}

	public void setArgumentType(ArgumentType argumentType) {
		this.argumentType = argumentType;
	}


	@Column(name = "default_value_")
	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	@Column(name = "isModify_", columnDefinition = "int default 0 ")
	public Boolean getIsModify() {
		return isModify;
	}

	public void setIsModify(Boolean isModify) {
		this.isModify = isModify;
	}
	@Column(name = "description_")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
