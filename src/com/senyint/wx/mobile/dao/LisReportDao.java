/**
 * AboutDao.java
 * com.senyint.wx.mobile.dao
 * Function: TODO ADD FUNCTION
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2014年12月11日 		gjp
 *
 * Copyright (c) 2014, senyint All Rights Reserved.
*/
/**
* @File: AboutDao.java
* @Package com.senyint.wx.mobile.dao
* @Description: TODO Describe the File
*
* @Company: senyint (Dalian) Co., Ltd
* @author   gjp
* @date     2014年12月11日
* @version  1.0
*/

package com.senyint.wx.mobile.dao;

import java.util.List;
import java.util.Map;

import com.senyint.wx.mobile.entity.LisReport;
import com.senyint.wx.mobile.entity.ReportLisResult;
import com.senyint.wx.mobile.entity.ReportLisSampleInfo;
import com.senyint.wx.mobile.entity.ReportPacsSj;

/**
 * @Type: AboutDao
 * @Description: TODO  Describe the Type
 *
 * @Company: senyint (Dalian) Co., Ltd
 * @author   gjp
 * @date     2014年12月11日
 * @version  1.0
 *
 */
public interface LisReportDao {
	
	/**
	 * @param bblsh
	 * @return
	 */
	public List<LisReport> findByBblsh(String bblsh);
	
	
	/**
	 * 根据身份证查找检查报告
	 * @param Identity
	 * @return
	 */
	//public List<ReportLisSampleInfo> findByIdentity(Map<String,Object> map);
	
	//public List<ReportLisSampleInfo> findByIdentity(int firstResult, int maxResult, Map<String,Object> map);
	
	/**
	 * 根据身份证号查询检验-- 		
	* The method <code> findReportPacs </code> .		 		
	* @author  gjp senyint (Dalian) Co., Ltd.		
	* 
	* @param patientidnumber
	* @return
	 */
	List<ReportLisSampleInfo> findReportLisPage(String username,String patientidnumber,Integer startNum,Integer endNum);

	/**根据标本流水号查找患者检查结果
	 * @param bblsh
	 * @param map
	 * @return
	 */
	public List<ReportLisResult> findLisByBblsh(String bblsh);
	
	
	/**根据标本流水号查询ReportLisSampleInfo实体
	 * @param bblsh
	 * @return
	 */
	public ReportLisSampleInfo findReportLisSampleEntityByBblsh(String bblsh);
	
	  /**
	   * 根据身份证号查询报告总数		
	  * The method <code> findReportPacsCount </code> .		 		
	  * 		
	  * @author  gjp senyint (Dalian) Co., Ltd.		
	  * 
	  * @param patientidnumber
	  * @return
	   */
	  Integer findReportLisCount(String username,String patientidnumber);
}
