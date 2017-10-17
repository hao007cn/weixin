package com.senyint.wx.common.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.senyint.common.util.DateUtils;
import com.senyint.core.dao.exception.AppRuntimeException;
import com.senyint.core.dao.impl.GenericDaoImpl;
import com.senyint.wx.common.dao.OrderDao;
import com.senyint.wx.common.entity.ForegroundUser;
import com.senyint.wx.common.entity.Orders;

@Repository
public class OrderDaoImpl extends GenericDaoImpl<Orders, Integer> implements OrderDao {
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Integer findMyOrdersCount(Map<String, Object> param, ForegroundUser curuser) {
		
		StringBuffer jpqlCount = new StringBuffer("select count(orders.poid) ");
		jpqlCount.append(" FROM Orders orders ");
		jpqlCount.append(" WHERE optType = '预约' ");
		
		List listParams = new ArrayList();
		
		jpqlCount.append(" AND userId = ? ");
		listParams.add(curuser.getPoid());
		
		Query q = createQuery(jpqlCount.toString(), false, listParams.toArray());
		
		return (((Long) q.getSingleResult())).intValue();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Orders> findMyOrdersList(Integer firstResult, Integer maxResult, Map<String, Object> param, ForegroundUser curuser) {
		String type = (String) param.get("type");
		String orderBy = (String) param.get("orderBy");
		
		StringBuffer jpql = new StringBuffer("select orders ");
		jpql.append(" FROM Orders orders ");
		jpql.append(" WHERE optType = '预约' ");
		
		List listParams = new ArrayList();
		
		jpql.append(" AND userId = ? ");
		listParams.add(curuser.getPoid());
		
		if (StringUtils.isNotBlank(orderBy)) {
			jpql.append(" " + orderBy + " ");
		} else {
			if ("1".equals(type)) {
				jpql.append(" ORDER BY createDate asc ");
			} else {
				jpql.append(" ORDER BY createDate desc ");
			}
		}
		
		Query q = createQuery(jpql.toString(), false, listParams.toArray());
		
		return q.setFirstResult(firstResult).setMaxResults(maxResult).getResultList();
	}
	
	/**
	* The method <code> findDoctorPatientCount </code> .
	* 获得医生患者数
	* 
	* @author sunzhi Senyint (Dalian) Co., Ltd.
	* 
	* @param doctorId
	* @return
	*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Integer findDoctorPatientCount(Integer doctorId) {
		
		StringBuffer jpqlCount = new StringBuffer("select count(patient) ");
		jpqlCount.append(" FROM Orders orders ");
		jpqlCount.append(" WHERE 1 = 1 ");
		
		List listParams = new ArrayList();
		jpqlCount.append(" AND schedule.doctor.poid = ? ");
		listParams.add(doctorId);
		
		Query q = createQuery(jpqlCount.toString(), false, listParams.toArray());
		
		return (((Long) q.getSingleResult())).intValue();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Integer fetchOrdersCount(Map<String, String> params) {
		
		StringBuffer jpqlCount = new StringBuffer("select count(orders.poid) ");
		jpqlCount.append(" FROM Orders orders ");
		jpqlCount.append(" WHERE 1 = 1 ");
		
		List listParams = new ArrayList();
		
		if (StringUtils.isNotBlank(params.get("oderName"))) {
			jpqlCount.append(" AND userName = ? ");
			listParams.add("%" + params.get("oderName") + "%");
		}
		
		if (StringUtils.isNotBlank(params.get("patientName"))) {
			jpqlCount.append(" AND patientName like ? ");
			listParams.add("%" + params.get("patientName") + "%");
		}
		
		if (StringUtils.isNotBlank(params.get("dateStart"))) {
			jpqlCount.append(" AND schedule.date >= ? ");
			listParams.add(DateUtils.strToDate(params.get("dateStart"), "yyyy-MM-dd"));
		}
		
		if (StringUtils.isNotBlank(params.get("dateEnd"))) {
			jpqlCount.append(" AND schedule.date <= ? ");
			listParams.add(DateUtils.strToDate(params.get("dateEnd"), "yyyy-MM-dd"));
		}
		
		Query q = createQuery(jpqlCount.toString(), false, listParams.toArray());
		
		return (((Long) q.getSingleResult())).intValue();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Orders> findOrdersList(Integer startRow, Integer rows,
			String orderBy, Map<String, String> params) {
		
		StringBuffer jpql = new StringBuffer("select orders ");
		jpql.append(" FROM Orders orders ");
		jpql.append(" WHERE 1 = 1 ");
		
		List listParams = new ArrayList();
		
		if (StringUtils.isNotBlank(params.get("oderName"))) {
			jpql.append(" AND userName like ? ");
			listParams.add("%" + params.get("oderName") + "%");
		}
		
		if (StringUtils.isNotBlank(params.get("oderCardid"))) {
			jpql.append(" AND userCardid = ? ");
			listParams.add(params.get("oderCardid"));
		}
		
		if (StringUtils.isNotBlank(params.get("patientName"))) {
			jpql.append(" AND patientName like ? ");
			listParams.add("%" + params.get("patientName") + "%");
		}
		
		if (StringUtils.isNotBlank(params.get("patientCardid"))) {
			jpql.append(" AND patientCardid = ? ");
			listParams.add(params.get("patientCardid"));
		}
		
		if (StringUtils.isNotBlank(params.get("patientHcardid"))) {
			jpql.append(" AND patientHcardid = ? ");
			listParams.add(params.get("patientHcardid"));
		}
		
		if (StringUtils.isNotBlank(params.get("dateStart"))) {
			jpql.append(" AND schedule.date >= ? ");
			listParams.add(DateUtils.strToDate(params.get("dateStart"), "yyyy-MM-dd"));
		}
		
		if (StringUtils.isNotBlank(params.get("dateEnd"))) {
			jpql.append(" AND schedule.date <= ? ");
			listParams.add(DateUtils.strToDate(params.get("dateEnd"), "yyyy-MM-dd"));
		}
		
		if (StringUtils.isNotBlank(orderBy)) {
			jpql.append(" order by " + orderBy + " ");
		} else {
			jpql.append(" order by schedule.date asc ");
		}
		
		Query q = createQuery(jpql.toString(), false, listParams.toArray());
		
		return q.setFirstResult(startRow).setMaxResults(rows).getResultList();
	}
}
