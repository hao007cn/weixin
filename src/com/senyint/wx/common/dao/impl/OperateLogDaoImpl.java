package com.senyint.wx.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.senyint.core.dao.impl.GenericDaoImpl;
import com.senyint.wx.common.dao.OperateLogDao;
import com.senyint.wx.common.entity.OperateLog;

/**
 * 操作日志Dao实现
 * 
 * @author gary.ch
 *
 */
@Repository
public class OperateLogDaoImpl extends GenericDaoImpl<OperateLog, Integer> implements OperateLogDao {

}
