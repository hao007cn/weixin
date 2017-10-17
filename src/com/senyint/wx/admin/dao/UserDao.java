package com.senyint.wx.admin.dao;

import com.senyint.core.dao.GenericDao;
import com.senyint.wx.admin.entity.User;

/**
* @Type: UserEao
* @Description: 用户EAO
*
* @Company: Senyint (Dalian) Co., Ltd
* @author   Gary
* @date     2014-2-10
* @version  1.0
*
*/
public interface UserDao extends GenericDao<User, Integer>{
	
	User findByUsername(String username);
	
}
