package com.senyint.wx.common.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.senyint.core.entity.component.CodeNode;

/**
 * 科室
 * 
 * @author sunzhi
 *
 */
@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "department")
public class Department extends CodeNode<Department>{

	private static final long serialVersionUID = 9047229404124461701L;
	
	private Integer parentId;
	
	private boolean enabled;
	
	private DepartmentType type;
	
	private Integer typeId;
	
	private String typeName;
	
	private boolean orderabled;
	
	private String moveType;
	
	private Integer targetPoid;
	private List<Department> livedChildren;
	
	/**
	* @Fields parentFlg : 是否实际科室
	*/
	private boolean parentFlg;
	
	private String parentName;
	
	private String icon;
	
	private List<Doctor> doctors;
	

	@Transient
	public Integer getParentId() {
		if (this.getParent() != null) {
			this.parentId = this.getParent().getPoid();
		}
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	/**
	 * 科室类别
	 * 
	 * @return
	 */
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "typeId_")
	public DepartmentType getType() {
		return type;
	}

	public void setType(DepartmentType type) {
		this.type = type;
	}

	@Transient
	public Integer getTypeId() {
		if (this.type != null) {
			this.typeId = this.type.getPoid();
		}
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	@Transient
	public String getTypeName() {
		if (this.type != null) {
			this.typeName = this.type.getName();
		}
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Column(name = "orderabled_")
	public boolean isOrderabled() {
		return orderabled;
	}

	public void setOrderabled(boolean orderabled) {
		this.orderabled = orderabled;
	}
	
	@Transient
	public boolean isRealOrderabled() {
		if (!orderabled) {
			return false;
		}
		
		if (this.getParent() != null) {
			return checkParentOrderabled(this.getParent());
		}
		
		return true;
	}
	
	private boolean checkParentOrderabled(Department dept) {
		if (dept != null) {
			if (!dept.isOrderabled()) {
				return false;
			} else {
				if (dept.getParent() != null) {
					return checkParentOrderabled(dept.getParent());
				}
			}
		}
		
		return true;
	}
	
	@Transient
	public boolean isRealEnabled() {
		if (!enabled) {
			return false;
		}
		
		if (this.getParent() != null) {
			return checkParentEnabled(this.getParent());
		}
		
		return true;
	}
	
	private boolean checkParentEnabled(Department dept) {
		if (dept != null) {
			if (!dept.isEnabled()) {
				return false;
			} else {
				if (dept.getParent() != null) {
					return checkParentEnabled(dept.getParent());
				}
			}
		}
		
		return true;
	}
	
	@Transient
	public List<Department> getLivedChildren() {
		
//		if (livedChildren == null && this.getChildren() != null && this.getChildren().size() > 0) {
//			livedChildren = new ArrayList<Department>();
//			for (Department dept : this.getChildren()) {
//				if (!dept.getDeleteFlg()) {
//					livedChildren.add(dept);
//				}
//			}
//		}
		
		return livedChildren;
	}
	
	@Column(name = "parentFlg_")
	public boolean isParentFlg() {
		return parentFlg;
	}

	public void setParentFlg(boolean parentFlg) {
		this.parentFlg = parentFlg;
	}

	@Transient
	public String getIsParent() {
		return String.valueOf(parentFlg);
	}
	
	@Transient
	public String getParentName() {
		if (this.getParent() != null) {
			this.parentName = this.getParent().getName();
		}
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
	@Column(name = "icon", length=50)
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	@JsonIgnore
	@Transient
//	@ManyToMany(mappedBy = "departments")
	public List<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}

//	/**
//	 * add a doctor by self
//	 * 
//	 * @param doctor
//	 */
//	public void addDoctor(Doctor doctor) {
//		if (!doctors.contains(doctor)) {
//			doctor.addDepartment(this);
//		}
//	}
//
//	/**
//	 * remove a doctor by self
//	 * 
//	 * @param doctor
//	 */
//	public void removeDoctor(Doctor doctor) {
//		if (doctors.contains(doctor)) {
//			doctor.removeDepartment(this);
//		}
//	}
//
//	public void removeAllDoctors() {
//		if (doctors != null) {
//			for (int i = 0; i< doctors.size(); i++) {
//				Doctor doctor = doctors.get(i);
//				if(doctor.getDepartments() != null && doctor.getDepartments().contains(this)) {
//					doctor.getDepartments().remove(this);
//				}
//			}
//			doctors.clear();
//		}
//	}
	
	@Transient
	public String getNocheck() {
		return this.getIsParent();
	}

	/**
	 * 拖拽状态
	 * @return
	 */
	@Transient
	public String getMoveType() {
		return moveType;
	}

	public void setMoveType(String moveType) {
		this.moveType = moveType;
	}
	/**
	 * 拖拽目標ID
	 * @return
	 */
	@Transient
	public Integer getTargetPoid() {
		return targetPoid;
	}

	public void setTargetPoid(Integer targetPoid) {
		this.targetPoid = targetPoid;
	}
}
