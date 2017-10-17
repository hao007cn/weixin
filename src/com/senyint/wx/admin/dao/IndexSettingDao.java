/**
 * IndexSettingDao.java
 * com.senyint.wx.admin.dao
 * Function: TODO ADD FUNCTION
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2014年11月25日 		gjp
 *
 * Copyright (c) 2014, senyint All Rights Reserved.
*/
/**
* @File: IndexSettingDao.java
* @Package com.senyint.wx.admin.dao
* @Description: TODO Describe the File
*
* @Company: senyint (Dalian) Co., Ltd
* @author   gjp
* @date     2014年11月25日
* @version  1.0
*/

package com.senyint.wx.admin.dao;

import java.util.List;

import com.senyint.core.dao.GenericDao;
import com.senyint.wx.common.entity.IndexSetting;

/**
 * ClassName:IndexSettingDao
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   gjp
 * @version  
 * @since    Ver 1.1
 * @Date	 2014年11月25日
 *
 * @see 	 
 */
/**
 * @Type: IndexSettingDao
 * @Description: TODO  Describe the Type
 *
 * @Company: senyint (Dalian) Co., Ltd
 * @author   gjp
 * @date     2014年11月25日
 * @version  1.0
 *
 */
public interface IndexSettingDao extends GenericDao<IndexSetting, Integer> {
	/**
	 * Sequence排序查找所有数据		
	* The method <code> findIndexSetting </code> .		 		
	* TODO(Describe the methods )
	* 		
	* @author  gjp senyint (Dalian) Co., Ltd.		
	* 
	* @return
	 */
	List<IndexSetting> findIndexSetting();
	/**
	 * 查找img_sequence最大值		
	* The method <code> getMaxSequence </code> .		 		
	* TODO(Describe the methods )
	* 		
	* @author  gjp senyint (Dalian) Co., Ltd.		
	* 
	* @return
	 */
	int getMaxSequence();
}
