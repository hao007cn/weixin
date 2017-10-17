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

package com.senyint.wx.common.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.senyint.wx.common.entity.ForegroundUser;
import com.senyint.wx.common.entity.ForegroundUserInHis;
import com.senyint.wx.mobile.entity.OutpatientLisSampleInfo;


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
public interface HisAccessDao {
	
	public static final Integer TYPE_KAN_ZHEN_JI_LU = 1;
	
	public static final Integer TYPE_YU_YUE_JI_LU = 2;
	
	/**
	 * 根据身份证号和姓名查询-- 		
	* The method <code> findOutpatientPage </code> .		 		
	* @author  zcl senyint (Dalian) Co., Ltd.		
	* 
	* @param patientidnumber
	* @param fuName
	* @return
	 */
	List<OutpatientLisSampleInfo> findOutpatientPage(String patientidnumber, String fuName ,Integer startNum,Integer endNum, Integer type) throws Exception;;

	  /**
	   * 根据身份证号和姓名查询看诊总数
	  * The method <code> findOutpatientCount </code> .		 		
	  * 		
	  * @author  zcl senyint (Dalian) Co., Ltd.		
	  * 
	  * @param patientidnumber
	  * @return
	   */
	Integer findOutpatientCount(String patientidnumber,String fuName, Integer type) throws Exception;;

	OutpatientLisSampleInfo findByFormNo(String formNo) throws Exception;

	List<OutpatientLisSampleInfo> findByFormNo(List<String> formNo) throws Exception;
	
	String getNextNo(String type) throws Exception;
	
	String orderCheck(String patientId, String snumber, Date date) throws Exception;

	String getFormNo() throws Exception;
	/**
	 * 验证身份证号是否存在于his
	 * @param cardid
	 * @return
	 */
	public boolean isHasCardId(String cardId,String userName) throws Exception;
	
	/**
	 * 获取 his中用户信息 通过姓名 和 身份证
	 * @param cardId
	 * @return
	 */
	public ForegroundUserInHis queryForegroundUserInHis(String cardId,String userName) throws Exception;
	
	/**
	 * 通过病人id获取his病人信息
	 * @param patiendId
	 * @return
	 */
	public ForegroundUserInHis queryForegroundUserInHisById(String patiendId) throws Exception;
	
	/**
	 * 生成新病人id
	 * @return
	 */
	public Long genericId() throws Exception;
	
	/**
	 * 生成支付方式名称
	 * @return
	 */
	public String genericPayTypeName(String payType) throws Exception;
	
	/**
	 * 
	 * @param cardId
	 * @return
	 */
	public Date getUserBirthDay(String cardId);
	
	/**
	 * 年龄
	 * @return
	 */
	public String getUserAge(String hisId,Date birthDate,Date currentDate) throws Exception;
	
	/**
	 * @return 向his表中插入一条数据
	 */
	public void insertNewUser2His (ForegroundUserInHis inhis) throws Exception;
	
	/**
	 * 向his更新一条数据
	 * @param inhis
	 * @return
	 */
	public void update2His(ForegroundUserInHis inhis) throws Exception;

	Map<String, String> createOrder(Map<String, Object> params) throws Exception;

	void cancelOrder(String formNo, Integer poid) throws Exception;

}
