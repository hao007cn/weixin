package com.senyint.wx.common.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.senyint.common.util.DateFormatUtils;
import com.senyint.common.util.DateUtils;
import com.senyint.core.dao.impl.GenericDaoImpl;
import com.senyint.wx.common.dao.ScheduleDao;
import com.senyint.wx.common.entity.Schedule;

/**
 * 排班Dao实现
 * 
 * @author sunzhi
 *
 */
@Repository
public class ScheduleDaoImpl extends GenericDaoImpl<Schedule, Integer> implements ScheduleDao {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Integer findCount(Map<String, Object> param) {
		StringBuffer jpqlCount = new StringBuffer("select count(schedule.poid) ");
		jpqlCount.append(" FROM Schedule schedule ");
		jpqlCount.append(" WHERE 1 = 1 ");
		jpqlCount.append("  AND schedule.deleteFlg = 0 ");
		String doctorName = (String) param.get("doctorName");
		String departmentId = (String) param.get("departmentId");
		String dateStart = (String) param.get("dateStart");
		String dateEnd = (String) param.get("dateEnd");
		List listParams = new ArrayList();
		
		if (StringUtils.isNotBlank(doctorName)) {
			jpqlCount.append(" AND doctor.name like ?");
			listParams.add("%"+doctorName+"%");
		}
		
		if (StringUtils.isNotBlank(departmentId)) {
			jpqlCount.append(" AND doctor.department.id = ? ");
			listParams.add(Integer.parseInt(departmentId));
		}
		
		if (StringUtils.isNotBlank(dateStart)) {
			jpqlCount.append(" AND date >= ? ");
			listParams.add(DateUtils.strToDate(dateStart, "yyyy-MM-dd"));
		}
		
		if (StringUtils.isNotBlank(dateEnd)) {
			jpqlCount.append(" AND date <= ? ");
			listParams.add(DateUtils.strToDate(dateEnd, "yyyy-MM-dd"));
		}
		
		return (((Long) createQuery(jpqlCount.toString(), false, listParams.toArray()).getSingleResult())).intValue();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Schedule> findList(int firstResult, int maxResult,
			Map<String, Object> param) {
		
		StringBuffer jpql = new StringBuffer(" Select schedule ");
		jpql.append(" FROM Schedule schedule ");
		jpql.append(" WHERE 1 = 1 ");
		jpql.append("  AND schedule.deleteFlg = 0 ");
		String doctorName = (String) param.get("doctorName");
		String departmentId = (String) param.get("departmentId");
		String dateStart = (String) param.get("dateStart");
		String dateEnd = (String) param.get("dateEnd");
		
		String orderBy = (String) param.get("orderBy");
		
		List listParams = new ArrayList();
		
		if (StringUtils.isNotBlank(doctorName)) {
			jpql.append(" AND doctor.name like ?");
			listParams.add("%"+doctorName+"%");
		}
		
		if (StringUtils.isNotBlank(departmentId)) {
			jpql.append(" AND doctor.department.id = ? ");
			listParams.add(Integer.parseInt(departmentId));
		}
		
		if (StringUtils.isNotBlank(dateStart)) {
			jpql.append(" AND date >= ? ");
			listParams.add(DateUtils.strToDate(dateStart, "yyyy-MM-dd"));
		}
		
		if (StringUtils.isNotBlank(dateEnd)) {
			jpql.append(" AND date <= ? ");
			listParams.add(DateUtils.strToDate(dateEnd, "yyyy-MM-dd"));
		}
		
		if (StringUtils.isNotBlank(orderBy)) {
			jpql.append(" ORDER BY " + orderBy + " ");
		} else {
			jpql.append(" ORDER BY date asc ");
		}
		
		return createQuery(jpql.toString(), false, listParams.toArray()).setFirstResult(firstResult).setMaxResults(maxResult).getResultList();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List< Object[] > getScheduleDays(Map<String, Object> param) {
		String dateStart = (String) param.get("dateStart");
		String dateEnd = (String) param.get("dateEnd");
		StringBuffer jpql = new StringBuffer();
		List listParams = new ArrayList();
		jpql.append(" SELECT ");
//		jpql.append(" to_char(s.date,'yyyy') AS year,");
//		jpql.append(" to_char(s.date,'mm') AS month, ");
		jpql.append(" avg(to_char(s.date,'dd')) AS day, ");
		jpql.append(" to_char(s.date,'yyyy-MM-dd') AS dates ");
		jpql.append(" FROM ");
		jpql.append(" Schedule as s ");
		jpql.append(" WHERE 1=1 ");
		jpql.append("  AND s.deleteFlg = 0 ");
		if (StringUtils.isNotBlank(dateEnd)) {
			jpql.append("  AND to_char(s.date,'yyyy-mm-dd') >= ? ");
			listParams.add(dateStart);
		}
		
		if (StringUtils.isNotBlank(dateEnd)) {
			jpql.append(" AND to_char(s.date,'yyyy-mm-dd') <= ? ");
			listParams.add(dateEnd);
		} 
		jpql.append(" GROUP BY to_char(s.date,'yyyy-MM-dd')");
		return createQuery(jpql.toString(), false, listParams.toArray()).getResultList();
	}

	@Override
	public List<Object[]> getScheduleList(int firstResult, int maxResult,Map<String, Object> param, String ap) {
		Date dateStart = (Date) param.get("dateStart");
		Date dateEnd = (Date) param.get("dateEnd");
		StringBuffer jpql = new StringBuffer();
		List listParams = new ArrayList();
		jpql.append(" SELECT ");
		jpql.append(" s.doctor.poid AS doctor, ");
		jpql.append(" to_char(s.date,'yyyy-MM-dd') AS date ");		
		jpql.append(" FROM ");
		jpql.append(" Schedule AS s");
		jpql.append(" WHERE 1 = 1 ");
		jpql.append("  AND s.deleteFlg = 0 ");
		if (StringUtils.isNotBlank(DateFormatUtils.formatDate(dateStart))) {
			jpql.append(" AND s.date >= ? ");
			listParams.add(dateStart);
		}
		if (StringUtils.isNotBlank(DateFormatUtils.formatDate(dateEnd))) {
			jpql.append(" AND s.date <= ? ");
			listParams.add(dateEnd);
		}
//		if (StringUtils.isNotBlank(ap)) {
//			jpql.append(" AND s.ap = ? ");
//			listParams.add( ap );
//		}
		jpql.append(" GROUP BY to_char(s.date,'yyyy-MM-dd'), s.doctor.poid ");
		//if(StringUtils.isNotBlank(ap)){
		//	jpql.append(" , s.ap");
	//	}
		//jpql.append(" ORDER BY s.date ");
	
		return createQuery(jpql.toString(), false, listParams.toArray()).setFirstResult(firstResult).setMaxResults(maxResult).getResultList();
	}
	@Override
	public List<Object[]> getScheduleList(Map<String, Object> param) {
		Date dateStart = (Date) param.get("dateStart");
		Date dateEnd = (Date) param.get("dateEnd");
		StringBuffer jpql = new StringBuffer();
		List listParams = new ArrayList();
		jpql.append(" SELECT ");
		jpql.append(" s.doctor.poid AS doctor,");
		jpql.append(" to_char(s.date,'yyyy-MM-dd') AS date ");
		jpql.append(" FROM ");
		jpql.append(" Schedule AS s");
		jpql.append(" WHERE 1 = 1 ");
		jpql.append("  AND s.deleteFlg = ? ");
		listParams.add(false);
		if (StringUtils.isNotBlank(DateFormatUtils.formatDate(dateStart))) {
			jpql.append(" AND s.date >= ? ");
			listParams.add(dateStart);
		}
		if (StringUtils.isNotBlank(DateFormatUtils.formatDate(dateEnd))) {
			jpql.append(" AND s.date <= ? ");
			listParams.add(dateEnd);
		}
		jpql.append(" GROUP BY to_char(s.date,'yyyy-MM-dd'),s.doctor.poid");
		
		jpql.append(" ORDER BY to_char(s.date,'yyyy-MM-dd') ");
	
		return createQuery(jpql.toString(), false, listParams.toArray()).getResultList();
	}
	
	//分组以后count函数统计的是每组内的总数
	@Override
	public Integer getDocCountsInday(Map<String, Object> param) {
		Date dateStart = (Date) param.get("dateStart");
		Date dateEnd = (Date) param.get("dateEnd");
		int docCounts = getScheduleList(param).size();
		return docCounts;
	}
}
