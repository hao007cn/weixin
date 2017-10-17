package com.senyint.wx.common.dao.impl;

import java.text.SimpleDateFormat;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.senyint.core.dao.impl.GenericDaoImpl;
import com.senyint.wx.common.dao.AccessStatisticsDao;
import com.senyint.wx.common.entity.AccessStatistics;
/**
 * 访问统计
 * @author gjp
 *
 */
@Repository
public class AccessStatisticsDaoImpl extends GenericDaoImpl<AccessStatistics, Integer> implements AccessStatisticsDao {

	@Override
	public int getAccessDateAndTypeCount(AccessStatistics accessStatistics) {
		Integer rs=0;
		Query q = getEntityManager().createNativeQuery("select count(*) from access_statistics ast "
				+ "where"
				+ " to_char(ast.visit_time_,'yyyy-mm-dd') =:visit_time"
				+ " and "
				+ " ast.visit_module_ =:visit_module"
				);
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		q.setParameter("visit_time", sdf.format(accessStatistics.getVisit_time()));
		q.setParameter("visit_module", accessStatistics.getVisit_module());
		Object returnValues = q.getSingleResult();
		if(returnValues != null){
			rs=Integer.parseInt(returnValues.toString());
		}
		return rs;
	}

	@Override
	public int getAccessTypeCount(AccessStatistics accessStatistics) {
		Integer rs=0;
		Query q = getEntityManager().createNativeQuery("select count(*) from access_statistics ast "
				+ " where "
				+ " ast.visit_module_ =:visit_module "
				);
		q.setParameter("visit_module", accessStatistics.getVisit_module());
		Object returnValues = q.getSingleResult();
		if(returnValues != null){
			rs=Integer.parseInt(returnValues.toString());
		}
		return rs;
	}
	
}
