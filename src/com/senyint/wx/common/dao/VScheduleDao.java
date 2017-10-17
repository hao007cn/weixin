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

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.senyint.core.dao.GenericDao;
import com.senyint.wx.common.entity.Schedule;
import com.senyint.wx.common.entity.VSchedule;


public interface VScheduleDao extends GenericDao<VSchedule, Integer>{
	/**
	 * 获取某一个月排班天集合
	 * @param param	含有dateStart,dateEnd
	 * @return
	 */
	List< Date > getScheduleDays(boolean cache,Map<String, Object> param);
	
	/**获取一天的排班列表
	 * @param queryDate
	 * @return
	 */
	List< Object[] > getScheduleList(int firstResult, int maxResult,boolean cache,String queryDate);
	
	/**获得某天排班医生总数
	 * @param param
	 * @return
	 */
	Integer getDocCountsInday(boolean cache,String queryDate);
}
