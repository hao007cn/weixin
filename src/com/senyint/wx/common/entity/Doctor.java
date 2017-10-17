package com.senyint.wx.common.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Type;

import com.senyint.core.entity.component.BaseEntity;
import com.senyint.core.entity.component.SexType;
@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "doctor")
public class Doctor extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3934525755115712584L;
	
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 科室
	 */
//	private Department department;
	
	private List<Department> departments;
	/**
	 * 性别
	 */
	private SexType sex;
	/**
	 * 职位
	 */
	private String position = "";
	/**
	 * 职称
	 */
	private String jobTitle = "";
	/**
	 * 是否开启
	 */
	private boolean enabled = true;
	/**
	 * 出诊费
	 */
	private BigDecimal fee;
	/**
	 * 出诊时间
	 */
	private String outCallTime = "";
	/**
	 * 简介
	 */
	private String desc;
	/**
	 * 备注
	 */
	private String remarks;
	
	/**
	 * 科室id
	 */
	private String departmentId;
	/**
	 * 科室名称
	 */
	private String departmentName;
	
	/**
	 * 页面传参用， 照片地址
	 * 
	 */
	private String picPath;
	
	/**
	 * 患者数
	 */
	private Integer patientCount = 0;
	
	/**
	* @Fields letters : 助记码
	*/
	private String letters;
	
	
	/**
	 * 姓名
	 * @return
	 */
	@Column(name = "name_", length = 20)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 科室
	 * @return
	 */
//	@JsonIgnore
//	@OneToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "departmentId")
//	public Department getDepartment() {
//		return department;
//	}
//	public void setDepartment(Department department) {
//		this.department = department;
//	}

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, targetEntity = Department.class)
	@JoinTable(name="department_doctor",
			joinColumns ={@JoinColumn(name="doctor_id_")},
			inverseJoinColumns={@JoinColumn(name="deparment_id_")})
	@org.hibernate.annotations.Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
	@org.hibernate.annotations.Cascade(value = org.hibernate.annotations.CascadeType.DELETE)	
	public List<Department> getDepartments() {
		return departments;
	}
	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	/**
	 * 加入一個部门
	 * 
	 * @param role
	 */
	public void addDepartment(Department department){
		if (departments == null) {
			departments = new ArrayList<Department>();
			departments.add(department);
		} else if(!departments.contains(department)){
			departments.add(department);
			department.getDoctors().add(this);
		}
	}

	/**
	 * 移除一個部门
	 * 
	 * @param role
	 */
	public void removeDepartment(Department department){
		if(departments.contains(department)){
			departments.remove(department);	
			department.getDoctors().remove(this);
		}
	}

	public void removeAllDepartments() {
		if (departments != null) {
			for (int i = 0; i< departments.size(); i++) {
				Department department = departments.get(i);
				if(department.getDoctors() != null && department.getDoctors().contains(this)) {
					department.getDoctors().remove(this);
				}
			}
			departments.clear();
		}
	}
	
	/**
	 * 性别
	 * @return
	 */
	@Enumerated
	@Column(name = "sex_")
	public SexType getSex() {
		return sex;
	}
	public void setSex(SexType sex) {
		this.sex = sex;
	}
	/**
	 * 职位
	 * @return
	 */
	@Column(name = "position_", length = 50)
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	/**
	 * 职称
	 * @return
	 */
	@Column(name = "jobTitle_", length = 50)
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	/**
	 * 启用状态
	 * @return
	 */
	@Column(name = "enabled_")
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	/**
	 * 简介
	 * 
	 * @return
	 */
	@Column(name = "description_")
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	@Column(name = "fee_")
	public BigDecimal getFee() {
		return fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	/**
	 * 备注
	 * @return
	 */
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	@Transient
	public String getDepartmentId() {
//		if (this.department != null) {
//			this.departmentId = this.department.getPoid();
//		}
		
		if (this.departments != null && this.departments.size() > 0) {
			departmentId = "";
			for (Department dept : this.departments) {
				departmentId += dept.getPoid() + ",";
			}
			departmentId = departmentId.replaceAll(",$", "");
		}
		
		return departmentId;
	}
	
	/*@Transient
	public Integer getDepartmentId() {
		if (this.department != null) {
			this.departmentId = this.department.getPoid();
		}
		
		return departmentId;
	}*/
	
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	
	@Transient
	public String getDepartmentName() {
//		if (this.department != null) {
//			this.departmentName = this.department.getName();
//		}
		
		if (this.departments != null && this.departments.size() > 0) {
			departmentName = "";
			for (Department dept : this.departments) {
				departmentName += dept.getName() + ",";
			}
			departmentName = departmentName.replaceAll(",$", "");
		}
		return departmentName;
	}
	
	/*@Transient
	public String getDepartmentName() {
		if (this.department != null) {
			this.departmentName = this.department.getName();
		}
		return departmentName;
	}*/
	
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	@Transient
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	
	@Transient
	public Integer getPatientCount() {
		return patientCount;
	}
	public void setPatientCount(Integer patientCount) {
		this.patientCount = patientCount;
	}
	
	@Column(name = "outCallTime_")
	public String getOutCallTime() {
		return outCallTime;
	}
	public void setOutCallTime(String outCallTime) {
		this.outCallTime = outCallTime;
	}
	
	@Column(name = "letters_")
	public String getLetters() {
		return letters;
	}
	public void setLetters(String letters) {
		this.letters = letters;
	}
}
