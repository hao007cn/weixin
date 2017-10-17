/**
 * UserServiceImpl.java
 * com.xy.service
 * Function: TODO ADD FUNCTION
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2012-11-30 		Administrator
 *
 * Copyright (c) 2012, Senyint All Rights Reserved.
*/
package com.senyint.wx.admin.dao.impl;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.senyint.core.dao.impl.GenericDaoImpl;
import com.senyint.wx.admin.dao.UserDao;
import com.senyint.wx.admin.entity.User;


/**
* @Type: UserDaoImpl
* @Description: 用户Dao
*
* @Company: Senyint (Dalian) Co., Ltd
* @author   gary.ch
* @date     2014-11-18
* @version  1.0
*
*/
@Repository
public class UserDaoImpl extends GenericDaoImpl<User, Integer> implements UserDao{

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public User findByUsername(String username) {
		try {
			
			Query q  = createQuery("from User u where u.username =:username and enabled = 1",true);
			q.setParameter("username", username);
			User user = (User) q.getSingleResult();
			return user;
			
		} catch (NoResultException e) {
			return null;
		}
	}

	
	
}
