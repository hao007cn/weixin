package com.senyint.wx.common.dao;

import java.util.List;

import com.senyint.core.dao.GenericDao;
import com.senyint.wx.common.entity.VDepartmentType;

public interface VDepartmentTypeDao extends GenericDao<VDepartmentType, Integer>{
	/**
	 * 关联科室列表去重DepartmentType
	 * @param area 医院部门编号
	 * @return
	 */
	List<VDepartmentType> findDistinctDepartmentType(String area);
}
