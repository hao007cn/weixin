package com.senyint.wx.common.dao.impl;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.senyint.common.util.DateUtils;
import com.senyint.common.web.Constants;
import com.senyint.core.dao.impl.GenericDaoImpl;
import com.senyint.wx.common.dao.VScheduleDao;
import com.senyint.wx.common.entity.Schedule;
import com.senyint.wx.common.entity.VSchedule;

/**
 * 排班Dao实现
 * 
 * @author sunzhi
 *
 */
@Repository
public class VScheduleDaoImpl extends GenericDaoImpl<VSchedule, Integer> implements VScheduleDao {

	@Override
	public List<Date> getScheduleDays(boolean cache,Map<String, Object> param) {
		StringBuffer jpql = new StringBuffer(
				"select  Distinct scheduleF.date from VSchedule as scheduleF where scheduleF.date >=? and scheduleF.date<=? and scheduleF.numberType != ? ");
		List listParams = new ArrayList();
		String dateStart = (String) param.get("dateStart");
		String dateEnd = (String) param.get("dateEnd");
		listParams.add(DateUtils.strToDate(dateStart, "yyyy-MM-dd"));
		listParams.add(DateUtils.strToDate(dateEnd, "yyyy-MM-dd"));
		//排除特诊
		listParams.add(Constants.outNumberType);
		return createQuery(jpql.toString(), cache, listParams.toArray()).getResultList();
	}
	

	@Override
	public List<Object[]> getScheduleList(int firstResult, int maxResult,boolean cache,String queryDate) {
		StringBuffer jpql = new StringBuffer();
		jpql.append("select  Distinct scheduleF.doctorId,scheduleF.departmentId from VSchedule as scheduleF where scheduleF.date = ? and scheduleF.numberType != ? order by scheduleF.departmentId asc");
		List listParams = new ArrayList();
		listParams.add(DateUtils.strToDate(queryDate, "yyyy-MM-dd"));
		//排除特诊
		listParams.add(Constants.outNumberType);
		List<Object[]> list = createQuery(jpql.toString(), cache, listParams.toArray()).setMaxResults(maxResult).setFirstResult(firstResult).getResultList();
		return list;
	}

	@Override
	public Integer getDocCountsInday(boolean cache,String queryDate) {
		StringBuffer jpql = new StringBuffer();
		jpql.append("select  count(Distinct scheduleF.doctorId) from VSchedule as scheduleF where scheduleF.date = ? and scheduleF.numberType != ? ");
		List listParams = new ArrayList();
		listParams.add(DateUtils.strToDate(queryDate, "yyyy-MM-dd"));
		//排除特诊
		listParams.add(Constants.outNumberType);
		return (((Long) createQuery(jpql.toString(), cache, listParams.toArray()).getSingleResult())).intValue();
	}
	
}
