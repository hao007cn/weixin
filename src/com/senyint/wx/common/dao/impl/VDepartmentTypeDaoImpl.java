package com.senyint.wx.common.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.senyint.core.dao.impl.GenericDaoImpl;
import com.senyint.wx.common.dao.VDepartmentTypeDao;
import com.senyint.wx.common.entity.VDepartmentType;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Repository
public class VDepartmentTypeDaoImpl extends
		GenericDaoImpl<VDepartmentType, Integer> implements VDepartmentTypeDao {

	@Override
	public List<VDepartmentType> findDistinctDepartmentType(String area) {
		StringBuffer hql = new StringBuffer("select  DISTINCT vt ");
		hql.append(" FROM VDepartmentType vt,VDepartment vd ");
		hql.append(" WHERE vt.poid=vd.typeId and vt.enabled='1'");
		List listParams = new ArrayList();
		if (("1").equals(area)) {
			hql.append("  AND vd.area != '2' AND vd.area != '3' AND vd.area != '4'");
		} else {
			if (StringUtils.isNotBlank(area)) {
				hql.append("  AND vd.area = ? ");
				listParams.add(area);
			}
		}
		hql.append(" order by vt.sort asc");
		return createQuery(hql.toString(), false, listParams.toArray())
				.getResultList();
	}

}
