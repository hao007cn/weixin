package com.senyint.wx.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.senyint.core.dao.impl.GenericDaoImpl;
import com.senyint.wx.common.dao.UserModifyLogDao;
import com.senyint.wx.common.entity.UserModifyLog;

@Repository
public class UserModifyLogDaoImpl extends GenericDaoImpl<UserModifyLog, Integer> implements UserModifyLogDao {
}
