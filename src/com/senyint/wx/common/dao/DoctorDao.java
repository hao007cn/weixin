package com.senyint.wx.common.dao;

import java.util.List;
import java.util.Map;

import com.senyint.core.dao.GenericDao;
import com.senyint.wx.common.entity.Doctor;

/**
 * 医生dao
 * 
 * @author sunzhi
 *
 */
public interface DoctorDao extends GenericDao<Doctor, Integer>{
	Integer fetchDoctorCount(boolean cache, Map<String, Object> params);
	
	List<Doctor> findDoctorList(int firstResult, int maxResult, String orders, boolean cache, Map<String, Object> params);
}
