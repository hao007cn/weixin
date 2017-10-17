package com.senyint.wx.common.dao;

import com.senyint.core.dao.GenericDao;
import com.senyint.wx.common.entity.Department;

/**
 * 科室dao
 * 
 * @author sunzhi
 *
 */
public interface DepartmentDao extends GenericDao<Department, Integer>{

	void remove(String id);
	
}
