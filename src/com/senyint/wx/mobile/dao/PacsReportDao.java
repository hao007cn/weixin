/**
 * pacsReportDao.java
 * com.senyint.wx.mobile.dao
 * Function: TODO ADD FUNCTION
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2014年12月22日 		gjp
 *
 * Copyright (c) 2014, senyint All Rights Reserved.
*/
/**
* @File: pacsReportDao.java
* @Package com.senyint.wx.mobile.dao
* @Description: TODO Describe the File
*
* @Company: senyint (Dalian) Co., Ltd
* @author   gjp
* @date     2014年12月22日
* @version  1.0
*/

package com.senyint.wx.mobile.dao;

import java.util.List;

import com.senyint.wx.mobile.entity.ReportPacsSj;

/**
 * ClassName:pacsReportDao
 *
 * @author   gjp
 * @version  
 * @since    Ver 1.1
 * @Date	 2014年12月22日
 *
 * @see 	 
 */
public interface PacsReportDao {

	/**
	 * 根据身份证号查询影响资料		
	* The method <code> findReportPacs </code> .		 		
	* @author  gjp senyint (Dalian) Co., Ltd.		
	* 
	* @param patientidnumber
	* @return
	 */
  List<ReportPacsSj> findReportPacsPage(String patientname,String patientidnumber,Integer startNum,Integer endNum);
  /**
   * 根据身份证号查询报告总数		
  * The method <code> findReportPacsCount </code> .		 		
  * 		
  * @author  gjp senyint (Dalian) Co., Ltd.		
  * 
  * @param patientidnumber
  * @return
   */
  Integer findReportPacsCount(String patientname,String patientidnumber);
  /**
   * 根据id取得报告详细信息		
  * The method <code> getReportPacs </code> .		 		
  * 		
  * @author  gjp senyint (Dalian) Co., Ltd.		
  * 
  * @param id
  * @return
   */
  ReportPacsSj getReportPacs(String id);
}
