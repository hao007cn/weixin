
package com.senyint.wx.admin.dao;

import com.senyint.core.dao.GenericDao;
import com.senyint.wx.admin.entity.Resource;

/**
* @Type: ResourcesEao
* @Description: 资源ResourcesDao
*
* @Company: Senyint (Dalian) Co., Ltd
* @author   zhz
* @date     2012-12-12
* @version  1.0
*
*/
public interface ResourceDao extends GenericDao<Resource, Integer>{
	
	public Resource findRootResource();
	
}
