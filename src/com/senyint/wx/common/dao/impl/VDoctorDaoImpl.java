package com.senyint.wx.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.senyint.core.dao.impl.GenericDaoImpl;
import com.senyint.wx.common.dao.VDoctorDao;
import com.senyint.wx.common.entity.VDoctor;
@Repository
public class VDoctorDaoImpl extends
GenericDaoImpl<VDoctor, Integer> implements VDoctorDao {

}
