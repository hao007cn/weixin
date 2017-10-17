package com.senyint.wx.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.senyint.core.dao.impl.GenericDaoImpl;
import com.senyint.wx.common.dao.VRegNumberDao;
import com.senyint.wx.common.entity.VRegNumber;

@Repository
public class VRegNumberDaoImpl extends GenericDaoImpl<VRegNumber, Integer> implements VRegNumberDao {
}
