package com.senyint.wx.common.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.senyint.common.util.DateUtils;
import com.senyint.core.entity.component.AuditType;
import com.senyint.core.entity.component.OrderPayState;
import com.senyint.core.entity.component.OrderState;
import com.senyint.wx.common.dao.HisAccessDao;
import com.senyint.wx.common.dao.OrderDao;
import com.senyint.wx.common.dao.ScheduleDao;
import com.senyint.wx.common.entity.ForegroundUser;
import com.senyint.wx.common.entity.Orders;
import com.senyint.wx.common.utils.ArgumentUtil;
import com.senyint.wx.mobile.entity.OutpatientLisSampleInfo;

@Service
@Transactional
public class OrderService {
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private ScheduleDao scheduleDao;
	
	@Autowired
	public HisAccessDao hisAccessDao;


	/**
	 * @param params
	 * @return Map<resCode, msg> ,resCode: 1成功，0失败
	 * @throws Exception 
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Map<String, String> createOrder(Map<String, Object> params) throws Exception {
		long patientId = Long.valueOf((String)params.get("patientId"));
		String patientName = (String)params.get("patientName");
		String departmentId = (String)params.get("departmentId");
		String departmentName = (String)params.get("departmentName");
		String doctorName = (String)params.get("doctorName");
		String snumber = (String)params.get("snumber");
		String serialNo = (String)params.get("serialNo");
		Integer fee = Integer.valueOf((String)params.get("fee"));
		String date = (String) params.get("date");
		String startTime = (String) params.get("startTime");
		
		ForegroundUser curUser = (ForegroundUser) params.get("currentUser");
		
		// TODO
		// 检查能不能预约
		// 检查同一科室只能挂一个号
		// 检查一天最多挂两个号
		String res = this.hisAccessDao.orderCheck(String.valueOf(patientId), departmentId, DateUtils.strToDate(date, DateUtils.FORMAT_yyyy_MM_dd));
		if (!"0".equals(res)) {
			Map<String, String> resMap = new HashMap<String, String>();
			resMap.put("resKey", "0");
			resMap.put("message", res);
			return resMap;
		}
		
		// 预约
		Date now = ArgumentUtil.getSysDate();
		Orders order = new Orders();
		order.setOptType(Orders.OPT_TYPE_APPLAY);
		order.setAplTime(DateUtils.strToDate(date + " " + startTime, "yyyy-MM-dd HH:mm"));
		order.setSnumber(snumber);
		order.setSerialNo(serialNo);
		// 订单状态-提交
		order.setState(OrderState.SUBMITED);
		// 支付状态-未支付
		order.setPayState(OrderPayState.UNPAY);
		
		// 费用
		order.setFee(new BigDecimal(fee));
		
		// 提交人信息-身份证号、用户姓名等
		order.setUserId(curUser.getPoid());
		order.setUserCardid(curUser.getCardid());
		order.setUserName(curUser.getName());
		order.setUserHisId(curUser.getHisId());
		
		// 就诊人信息-身份证号，健康卡号、姓名等
		order.setPatientHisId(String.valueOf(patientId));
		order.setPatientName(patientName);
		
		// 科室
		order.setDepartmentName(departmentName);
		// 医生
		order.setDoctorName(doctorName);
		
		// 审核状态-未审
		order.setAuditType(AuditType.UNAUDIT);
		
		// 修改信息
		order.setCreateDate(now);
		order.setCreateUserId(String.valueOf(curUser.getPoid()));
		order.setCreateUserName(curUser.getName());
		order.setModifyDate(now);
		order.setModifyUserId(String.valueOf(curUser.getPoid()));
		order.setModifyUserName(curUser.getName());
		
		//String formNo = this.orderDao.getFormNo();
		String formNo = hisAccessDao.getNextNo("12");
		
		order.setFormNo(formNo);
		
		order = this.orderDao.makePersistent(order);
		params.put("orderId", order.getPoid());
		params.put("formNo", formNo);
		
		Map<String, String> resMap = this.hisAccessDao.createOrder(params);
		
		return resMap;
	}
	
	/**
	 * 取消预约
	 * 
	 * @param orderId
	 * @param curUser
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Map<String, String> cancelOrders(String formNo, ForegroundUser curUser) throws Exception{
		Map<String, String> resMap = new HashMap<String, String>();
		resMap.put("resKey", "0");
		resMap.put("message", "预约失败");
		
		OutpatientLisSampleInfo outpatientInfo = hisAccessDao.findByFormNo(formNo);
		Date aplDate = outpatientInfo.getKzdate();
		
		// 状态check
		if (outpatientInfo.getExecuteState() > 0) {
			resMap.put("resKey", "0");
			resMap.put("message", "预约不可取消！");
			return resMap;
			
		}
		
		// 时间check
		if (DateUtils.format(aplDate, DateUtils.FORMAT_yyyy_MM_dd).compareTo(DateUtils.format(ArgumentUtil.getSysDate(), DateUtils.FORMAT_yyyy_MM_dd)) <= 0) {
			resMap.put("resKey", "0");
			resMap.put("message", "不可在看诊当天取消预约！");
			return resMap;
		}
		
		// 预约
		Date now = ArgumentUtil.getSysDate();
		Orders order = new Orders();
		order.setOptType(Orders.OPT_TYPE_CANCEL);
		order.setAplTime(aplDate);
		// 订单状态-提交
		order.setState(OrderState.CANCELED);
		// 支付状态-未支付
		order.setPayState(OrderPayState.UNPAY);
		
		// 提交人信息-身份证号、用户姓名等
		order.setUserId(curUser.getPoid());
		order.setUserCardid(curUser.getCardid());
		order.setUserName(curUser.getName());
		order.setUserHisId(curUser.getHisId());
		
		// 就诊人信息-身份证号，健康卡号、姓名等
		order.setPatientHisId(String.valueOf(outpatientInfo.getPatientID()));
		order.setPatientName(outpatientInfo.getPatientName());
		
		// 审核状态-未审
		order.setAuditType(AuditType.UNAUDIT);
		
		// 修改信息
		order.setCreateDate(now);
		order.setCreateUserId(String.valueOf(curUser.getPoid()));
		order.setCreateUserName(curUser.getName());
		order.setModifyDate(now);
		order.setModifyUserId(String.valueOf(curUser.getPoid()));
		order.setModifyUserName(curUser.getName());
		
		order.setState(OrderState.CANCELED);
		order.setCancelTime(ArgumentUtil.getSysDate());
		order.setCancelUserCardid(curUser.getCardid());
		order.setCancelUserName(curUser.getName());
		
		order = this.orderDao.makePersistent(order);
		
		this.hisAccessDao.cancelOrder(formNo, order.getPoid());
		
		resMap.put("resKey", "1");
		resMap.put("message", "取消成功！");
		return resMap;
	}
}
