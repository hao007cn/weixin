package com.senyint.wx.common.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.senyint.core.dao.impl.GenericDaoImpl;
import com.senyint.wx.common.dao.DoctorDao;
import com.senyint.wx.common.entity.Doctor;

/**
 * 医生Dao实现
 * 
 * @author sunzhi
 *
 */
@Repository
public class DoctorDaoImpl extends GenericDaoImpl<Doctor, Integer> implements DoctorDao {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Integer fetchDoctorCount(boolean cache, Map<String, Object> params) {
		StringBuffer jpql = new StringBuffer(
				"select count(distinct doctor.id) from Doctor as doctor where 1=1 ");
		List listParams = new ArrayList();
		
		if (StringUtils.isNotBlank((String) params.get("name")) ) {
			jpql.append(" and doctor.name like ? ");
			listParams.add("%" + params.get("name") + "%");
		}
		
		if ( params.get("enabled") != null ) {
			jpql.append(" and doctor.enabled = ? ");
			listParams.add(params.get("enabled"));
		}
		
		if (StringUtils.isNotBlank((String) params.get("departmentId")) ) {
			jpql.append(" and departmentId = ? ");
			listParams.add(Integer.parseInt((String)params.get("departmentId")));
		}
		
		return (((Long) createQuery(jpql.toString(), cache, listParams.toArray()).getSingleResult())).intValue();
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Doctor> findDoctorList(int firstResult, int maxResult, String orders, boolean cache, Map<String, Object> params) {
		StringBuffer jpql = new StringBuffer(
			"select distinct doctor from Doctor as doctor where 1=1 ");
		
		List listParams = new ArrayList();
		
		if (StringUtils.isNotBlank((String) params.get("name")) ) {
			jpql.append(" and doctor.name like ? ");
			listParams.add("%" + params.get("name") + "%");
		}
		
		if ( params.get("enabled") != null ) {
			jpql.append(" and doctor.enabled = ? ");
			listParams.add(params.get("enabled"));
		}
		
		if (StringUtils.isNotBlank((String) params.get("departmentId")) ) {
//			jpql.append(" and department.poid in ( :deptIdList ) ");
			jpql.append(" and departmentId = ? ");
			listParams.add(Integer.parseInt((String)params.get("departmentId")));
		}
		
		if (StringUtils.isNotBlank(orders)) {
			jpql.append(" ORDER BY " + orders + " ");
		} else {
			jpql.append(" ORDER BY poid asc ");
		}
		
		Query q = createQuery(jpql.toString(), cache, listParams.toArray());
		
//		if (StringUtils.isNotBlank((String) params.get("departmentId")) ) {
//			String departmentId = (String) params.get("departmentId");
//			String[] deptIdarr = departmentId.split(",");
//			
//			List<Integer> deptIdList = new ArrayList<Integer>();
//			for (String id : deptIdarr) {
//				if (StringUtils.isNotBlank(id)) {
//					deptIdList.add(Integer.parseInt(id));
//				}
//			}
//			
//			q.setParameter("deptIdList", deptIdList);
//		}
		
		return q.setFirstResult(firstResult).setMaxResults(maxResult).getResultList();
		
//		Doctor doc = this.findByPoid(1);
//		
//		System.out.println(doc.getDepartmentName());
//		
//		return null;
	}
}
