package com.senyint.wx.common.dao.impl;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.senyint.core.dao.impl.GenericDaoImpl;
import com.senyint.wx.common.dao.DepartmentDao;
import com.senyint.wx.common.entity.Department;

/**
 * 科室Dao实现
 * 
 * @author sunzhi
 *
 */
@Repository
public class DepartmentDaoImpl extends GenericDaoImpl<Department, Integer> implements DepartmentDao {

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void remove(String id) {
		String[] ids = id.split(",");
		for (String idTmp : ids) {
			if (StringUtils.isNotBlank(idTmp)) {
				Department dept = this.findByPoid(Integer.parseInt(idTmp));
				
				this.remove(dept);
			}
		}
	}
		
}
