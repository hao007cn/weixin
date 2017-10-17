package com.senyint.wx.admin.dao;

import java.util.Date;

import com.senyint.core.dao.GenericDao;
import com.senyint.wx.admin.entity.Argument;


public interface ArgumentDao extends GenericDao<Argument, Integer> {
	
	Date getSysDate();
	
}
