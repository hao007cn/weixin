/**
 * Feedback.java
 * com.senyint.wx.common.entity
 * Function: TODO ADD FUNCTION
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2014年12月11日 		gjp
 *
 * Copyright (c) 2014, senyint All Rights Reserved.
*/
/**
* @File: Feedback.java
* @Package com.senyint.wx.common.entity
* @Description: TODO Describe the File
*
* @Company: senyint (Dalian) Co., Ltd
* @author   gjp
* @date     2014年12月11日
* @version  1.0
*/

package com.senyint.wx.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.senyint.common.web.springmvc.JsonDateFormat;
import com.senyint.core.entity.persistence.SurrogateKeyObject;

/**
 * ClassName:Feedback
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   gjp
 * @version  
 * @since    Ver 1.1
 * @Date	 2014年12月11日
 *
 * @see 	 
 */
@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name="feedback")
@org.hibernate.annotations.BatchSize(size = 20)
public class Feedback extends SurrogateKeyObject {
	
	/**
	* @Fields serialVersionUID : TODO  Describe the Fields
	*/
	private static final long serialVersionUID = -1546000232733350316L;
	private String content;
	private Integer creat_userid;
	private String creat_username;
	private Date creat_date;
	private Integer read_flag;
	/**
	 * @return 反馈内容
	 */
	@Column(name = "content_")
	public String getContent() {
		return content;
	}
	/**
	 * @param 反馈内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return 反馈人id
	 */
	@Column(name = "creat_userid_")
	public Integer getCreat_userid() {
		return creat_userid;
	}
	/**
	 * @param 反馈人id
	 */
	public void setCreat_userid(Integer creat_userid) {
		this.creat_userid = creat_userid;
	}
	/**
	 * @return 反馈人姓名
	 */
	@Column(name = "creat_username_",length = 20)
	public String getCreat_username() {
		return creat_username;
	}
	/**
	 * @param 反馈人姓名
	 */
	public void setCreat_username(String creat_username) {
		this.creat_username = creat_username;
	}
	/**
	 * @return 反馈时间
	 */
	@JsonSerialize(using=JsonDateFormat.class)
	@Column(name = "creat_date_")
	public Date getCreat_date() {
		return creat_date;
	}
	/**
	 * @param 反馈时间
	 */
	public void setCreat_date(Date creat_date) {
		this.creat_date = creat_date;
	}
	/**
	 * @return 标识
	 */
	@Column(name = "read_flag_")
	public Integer getRead_flag() {
		return read_flag;
	}
	/**
	 * @param 标识
	 */
	public void setRead_flag(Integer read_flag) {
		this.read_flag = read_flag;
	}
	
}
