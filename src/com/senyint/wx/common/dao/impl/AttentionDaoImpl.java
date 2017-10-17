package com.senyint.wx.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.senyint.core.dao.impl.GenericDaoImpl;
import com.senyint.wx.common.dao.AttentionDao;
import com.senyint.wx.common.entity.Attention;

/**
 * 关注Dao实现
 * 
 * @author sunzhi
 *
 */
@Repository
public class AttentionDaoImpl extends GenericDaoImpl<Attention, Integer> implements AttentionDao {

}
