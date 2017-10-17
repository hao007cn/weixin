/**
 * ScheduleDao.java
 * com.senyint.wx.common.dao
 * Function: 排班dao
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2014-12-7 		sunzhi
 *
 * Copyright (c) 2014, Senyint All Rights Reserved.
*/

package com.senyint.wx.common.dao;

import java.util.List;
import java.util.Map;

import com.senyint.core.dao.GenericDao;
import com.senyint.wx.common.entity.Schedule;

/**
 * ClassName:ScheduleDao
 * Function: 排班dao
 * Reason:	 排班dao
 *
 * @author   sunzhi
 * @version  
 * @since    Ver 1.1
 * @Date	 2014-12-7
 *
 * @see 	 
 */
public interface ScheduleDao extends GenericDao<Schedule, Integer>{
	
	Integer findCount(Map<String, Object> param);
	
	List<Schedule> findList(int firstResult, int maxResult,
			Map<String, Object> param);
	
	/**
	 * 获取某一个月排班天集合
	 * @param param	含有dateStart,dateEnd
	 * @return
	 */
	List< Object[] > getScheduleDays(Map<String, Object> param);
	
	/**获取某端时间排班列表
	 * @param firstResult
	 * @param maxResult
	 * @param param
	 * @param ap
	 * @return
	 */
	List< Object[] > getScheduleList(int firstResult, int maxResult,Map<String, Object> param,String ap);
	List< Object[] > getScheduleList(Map<String, Object> param);
	
	/**获得某天排班医生总数
	 * @param param
	 * @return
	 */
	Integer getDocCountsInday(Map<String, Object> param);
}
