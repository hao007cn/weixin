package com.senyint.wx.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.senyint.core.entity.persistence.SurrogateKeyObject;

@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "v_doctor")
public class VDoctor extends SurrogateKeyObject {
	private static final long serialVersionUID = -1218991164211151783L;
	
	private Integer did;
	private String description;
	private Integer enabled;
	private String fee;
	private String jobTitle;
	private String name;
	private String outCallTime;
	private String position;
	private String remarks;
	private String sex;
	private String imgpath;
	private Integer departmentId;
	
	private String rname;
	
	@Column(name = "did")
	public Integer getDid() {
		return did;
	}

	public void setDid(Integer did) {
		this.did = did;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "enabled")
	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	@Column(name = "fee")
	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	@Column(name = "jobtitle")
	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "outcalltime")
	public String getOutCallTime() {
		return outCallTime;
	}

	public void setOutCallTime(String outCallTime) {
		this.outCallTime = outCallTime;
	}

	@Column(name = "position")
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Column(name = "remarks")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "sex")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "imgpath")
	public String getImgpath() {
		return imgpath;
	}

	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}

	@Column(name = "departmentid")
	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	@Transient
	public String getRname() {
		if (StringUtils.isNotBlank(this.imgpath)) {
			rname = this.imgpath.replaceAll("http\\:\\/\\/172\\.16\\.99\\.30\\:81\\/web\\/image\\/", "").replaceAll("\\.jpg", "");
		}
		return rname;
	}

	public void setRname(String rname) {
		this.rname = rname;
	}
	
}