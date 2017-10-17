/**
 * HospitalSetting.java
 * com.senyint.wx.common.entity
 * Function: TODO ADD FUNCTION
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2014年11月26日 		gjp
 *
 * Copyright (c) 2014, senyint All Rights Reserved.
*/
/**
* @File: HospitalSetting.java
* @Package com.senyint.wx.common.entity
* @Description: TODO Describe the File
*
* @Company: senyint (Dalian) Co., Ltd
* @author   gjp
* @date     2014年11月26日
* @version  1.0
*/

package com.senyint.wx.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Type;

import com.senyint.common.web.springmvc.JsonDateFormat;
import com.senyint.core.entity.persistence.SurrogateKeyObject;

/**
 * ClassName:HospitalSetting
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   gjp
 * @version  
 * @since    Ver 1.1
 * @Date	 2014年11月26日
 *
 * @see 	 
 */
/**
 * @Type: HospitalSetting
 * @Description: TODO  Describe the Type
 *
 * @Company: senyint (Dalian) Co., Ltd
 * @author   gjp
 * @date     2014年11月26日
 * @version  1.0
 *
 */
@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name="hospital_setting")
@org.hibernate.annotations.BatchSize(size = 20)
public class HospitalSetting extends SurrogateKeyObject{
	/**
	* @Fields serialVersionUID : TODO  Describe the Fields
	*/
	private static final long serialVersionUID = -3248182606207273751L;
	private Date updateDate;
	private String hospitalContent;
	/**
	 * 修改时间
	 */
	@JsonSerialize(using=JsonDateFormat.class)
	@Column(name = "updateDate_")
	public Date getUpdateDate() {
		return updateDate;
	}
	/**
	 * 修改时间
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * 文本内容
	 */
	@Column(name = "hospitalContent_")
	public String getHospitalContent() {
		return hospitalContent;
	}
	/**
	 * 文本内容
	 */
	public void setHospitalContent(String hospitalContent) {
		this.hospitalContent = hospitalContent;
	}
	
}
