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
* @Type: OnlineOperateLog
* @Description: 操作日志
*
* @Company: senyint (Dalian) Co., Ltd
* @author   sun.zhi
* @date     2015-4-22
* @version  1.0
*
*/
@Entity
@Table(name="operate_log")
public class OnlineOperateLog extends SurrogateKeyObject { 
	
	private static final long serialVersionUID = 1L;

	private String ip;
	
	private String userName;
	
	private String userId;
	
	private String userCardid;
	
	private String operate;
	
	private String description;
	
	private String url;
	
	private Date operate_time;
	
	private String mobileType;
	
	private String browserType;
	
	private String browserDetail;
	
	private Boolean deleteFlag;

	@Column(name = "ip_", length=30)
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@Column(name = "userName_", length=50)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name = "userId_", length=20)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "userCardid_", length=20)
	public String getUserCardid() {
		return userCardid;
	}

	public void setUserCardid(String userCardid) {
		this.userCardid = userCardid;
	}

	@Column(name = "operate_", length=50)
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

	@Column(name = "mobileType_", length=30)
	public String getMobileType() {
		return mobileType;
	}

	public void setMobileType(String mobileType) {
		this.mobileType = mobileType;
	}

	@Column(name = "browserType_", length=30)
	public String getBrowserType() {
		return browserType;
	}

	public void setBrowserType(String browserType) {
		this.browserType = browserType;
	}

	@Column(name = "browserDetail_", length=100)
	public String getBrowserDetail() {
		return browserDetail;
	}

	public void setBrowserDetail(String browserDetail) {
		this.browserDetail = browserDetail;
	}

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
}
