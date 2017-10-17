/**
 * OrderDao.java
 * com.senyint.wx.common.dao
 * Function: 订单dao
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2014-12-19 		sunzhi
 *
 * Copyright (c) 2014, Senyint All Rights Reserved.
*/
package com.senyint.wx.common.dao;

import java.util.List;
import java.util.Map;

import com.senyint.core.dao.GenericDao;
import com.senyint.wx.common.entity.ForegroundUser;
import com.senyint.wx.common.entity.Orders;

/**
 * 订单dao
 * 
 * @author sunzhi
 *
 */
public interface OrderDao extends GenericDao<Orders, Integer>{
	
	Integer findMyOrdersCount(Map<String, Object> param, ForegroundUser curuser);
	
	List<Orders> findMyOrdersList(Integer firstResult, Integer maxResult, Map<String, Object> param, ForegroundUser curuser);
	
	Integer findDoctorPatientCount(Integer doctorId);

	Integer fetchOrdersCount(Map<String, String> params);

	List<Orders> findOrdersList(Integer startRow, Integer rows, String string,
			Map<String, String> params);
}
