package com.senyint.wx.admin.dao.impl;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.senyint.core.dao.impl.GenericDaoImpl;
import com.senyint.wx.admin.dao.ResourceDao;
import com.senyint.wx.admin.entity.Resource;


/**
 * 
* @Type: ResourcesEaoImpl
* @Description: 资源ResourcesDao实现
*
* @Company: Senyint (Dalian) Co., Ltd
* @author   gary.ch
* @date     2014-2-10
* @version  1.0
*
 */
@Repository
public class ResourceDaoImpl extends GenericDaoImpl<Resource, Integer> implements ResourceDao{

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Resource findRootResource() {
		Query q  =  createQuery("from Resource r where r.parent = null and r.type = 0",true);
		return (Resource) q.getSingleResult();
	}
}
