package com.senyint.wx.common.dao;

import com.senyint.core.dao.GenericDao;
import com.senyint.wx.common.entity.AccessStatistics;
/**
 * 访问统计
 * @author gjp
 *
 */
public interface AccessStatisticsDao extends GenericDao<AccessStatistics,Integer>{
	/**
	 * 根据时间和类型查询统计
	 * @param accessStatistics
	 * @return
	 */
	int getAccessDateAndTypeCount(AccessStatistics accessStatistics);
	/**
	 * 根据类型查询统计
	 * @param accessStatistics
	 * @return
	 */
	int getAccessTypeCount(AccessStatistics accessStatistics);
}
