package com.senyint.wx.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Type;

import com.senyint.common.web.springmvc.JsonDateFormat;
import com.senyint.core.entity.persistence.SurrogateKeyObject;



/**
* @Type: OperateLog
* @Description: 操作日志
*
* @Company: senyint (Dalian) Co., Ltd
* @author   zhz
* @date     2014-4-22
* @version  1.0
*
*/
@Entity
@Table(name="sys_operate_log")
public class OperateLog extends SurrogateKeyObject { 
	
	private static final long serialVersionUID = 1L;

	private String ip;
	
	private String user_name;
	
	private String user_id;
	
	private String operate;
	
	private String description;
	
	private String url;
	
	private Date operate_time;
	
	private Boolean deleteFlag;

	@Column(name = "ip_")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	@Column(name = "user_name_")
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	@Column(name = "user_id_")
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	@Column(name = "operate_")
	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}
	@Column(name = "description_")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "url_")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	@JsonSerialize(using=JsonDateFormat.class)
	@Column(name = "operateTime_")
	public Date getOperate_time() {
		return operate_time;
	}

	public void setOperate_time(Date operate_time) {
		this.operate_time = operate_time;
	}

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
}
