package com.senyint.wx.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.senyint.core.dao.impl.GenericDaoImpl;
import com.senyint.wx.common.dao.JobTitleDao;
import com.senyint.wx.common.entity.JobTitle;

@Repository
public class JobTitleDaoImpl extends GenericDaoImpl<JobTitle, Integer> implements JobTitleDao{

}
