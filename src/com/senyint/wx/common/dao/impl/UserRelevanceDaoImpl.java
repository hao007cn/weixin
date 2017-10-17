package com.senyint.wx.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.senyint.core.dao.impl.GenericDaoImpl;
import com.senyint.wx.common.dao.UserRelevanceDao;
import com.senyint.wx.common.entity.UserRelevance;

@Repository
public class UserRelevanceDaoImpl extends GenericDaoImpl<UserRelevance, Integer>implements UserRelevanceDao{

}
