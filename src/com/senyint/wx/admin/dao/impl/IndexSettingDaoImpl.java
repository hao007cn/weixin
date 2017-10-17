/**
 * IndexSettingDaoImpl.java
 * com.senyint.wx.admin.dao.impl
 * Function: TODO ADD FUNCTION
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2014年11月25日 		gjp
 *
 * Copyright (c) 2014, senyint All Rights Reserved.
*/
/**
* @File: IndexSettingDaoImpl.java
* @Package com.senyint.wx.admin.dao.impl
* @Description: TODO Describe the File
*
* @Company: senyint (Dalian) Co., Ltd
* @author   gjp
* @date     2014年11月25日
* @version  1.0
*/

package com.senyint.wx.admin.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.senyint.core.dao.impl.GenericDaoImpl;
import com.senyint.wx.admin.dao.IndexSettingDao;
import com.senyint.wx.common.entity.IndexSetting;

/**
 * ClassName:IndexSettingDaoImpl
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
 * @Type: IndexSettingDaoImpl
 * @Description: TODO  Describe the Type
 *
 * @Company: senyint (Dalian) Co., Ltd
 * @author   gjp
 * @date     2014年11月25日
 * @version  1.0
 *
 */
@Repository
public class IndexSettingDaoImpl extends GenericDaoImpl<IndexSetting, Integer> implements IndexSettingDao {

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<IndexSetting> findIndexSetting() {
			try {
				
				Query q  = createQuery("from IndexSetting se order by se.img_sequence",false);
				List<IndexSetting> indexSetting = (List<IndexSetting>) q.getResultList();
				return indexSetting;
				
			} catch (NoResultException e) {
				return null;
			}	
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public int getMaxSequence() {
		try {
			
			Query q  =  createQuery("select MAX(se.img_sequence) from IndexSetting se order by se.img_sequence",false);
			Object maxSequence = q.getSingleResult();
			if(maxSequence!=null)
			{
				return Integer.parseInt(maxSequence.toString());
			}
			else{
				return 0;
			}
		
		} catch (NoResultException e) {
			return 0;
		}	
		
	}

}
