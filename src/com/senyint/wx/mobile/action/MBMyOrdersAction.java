/**
 * MyOrdersAction.java
 * com.senyint.wx.mobile.action
 * Function: 我的预约
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2014-12-21 		sunzhi
 *
 * Copyright (c) 2014, Senyint All Rights Reserved.
*/

package com.senyint.wx.mobile.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.senyint.common.web.ajax.AjaxResult;
import com.senyint.core.dao.exception.AppRuntimeException;
import com.senyint.core.entity.component.OrderState;
import com.senyint.wx.common.dao.HisAccessDao;
import com.senyint.wx.common.dao.OrderDao;
import com.senyint.wx.common.entity.Orders;
import com.senyint.wx.common.service.OrderService;
import com.senyint.wx.common.web.Operate;
import com.senyint.wx.mobile.entity.OutpatientLisSampleInfo;

/**
 * ClassName:MyOrdersAction
 * Function: 我的预约
 * Reason:	 我的预约
 *
 * @author   sunzhi
 * @version  
 * @since    Ver 1.1
 * @Date	 2014-12-21
 *
 * @see 	 
 */
@Controller
@RequestMapping(value = "myorders")
public class MBMyOrdersAction extends MBSupportAction {
	
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private OrderService orderService;
	@Autowired
	private HisAccessDao hisAccessDao;

	/**
	 * 我的预约主页
	 * 
	 * @return
	 */
	@Operate(desc="我的预约-首页")
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model, HttpServletRequest request) {
		// 查看类型 -- 1待就医     2已就医
		String type = request.getParameter("type");
		
		if (StringUtils.isBlank(type)) {
			type = "0";
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		
		Integer count = orderDao.findMyOrdersCount(params, getLoginUserInfo());
		
		model.addAttribute("type", type);
		model.addAttribute("pageSum", count);
		
		return "myOrders";
	}

	/**
	 * 我的预约-预约列表
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@Operate(desc="我的预约-预约列表")
	@RequestMapping(value = "/list")
	public String list(Model model, HttpServletRequest request) {
		try {
			// 查看类型 -- 1待就医     2已就医
			String type = request.getParameter("type");
			String pageNum = request.getParameter("pageNum");
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("type", type);
			
			List<Orders> list = orderDao.findMyOrdersList(Integer.parseInt(pageNum), 10, params, getLoginUserInfo());
			
			List<String> formNoList = new ArrayList<String>();
			
			if (list != null && list.size() > 0) {
				for (Orders order : list) {
					formNoList.add(order.getFormNo());
				}
			}
			
			List<OutpatientLisSampleInfo> otlist = this.hisAccessDao.findByFormNo(formNoList);
			
			if (otlist != null && otlist.size() > 0) {
				for (Orders order : list) {
					boolean find = false;
					for (OutpatientLisSampleInfo outpatient : otlist) {
						if (order.getFormNo().equals(outpatient.getGhdh())) {
							find = true;
							order.setState(outpatient.getExecuteState() > 0 ? OrderState.FINISHED : OrderState.SUBMITED);
						}
					}
					
					if (!find) {
						order.setState(OrderState.CANCELED);
					}
				}
			} else {
				if (list != null && list.size() > 0) {
					for (Orders order : list) {
						order.setState(OrderState.CANCELED);
					}
				}
			}
			
			
			model.addAttribute("orderList", list);
		} catch(Exception e) {
			model.addAttribute("errMsg", "数据加载异常！");
		}
		
		return "myOrderList";
	}

	/**
	 * 我的预约-预约详情
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@Operate(desc="我的预约-预约详情")
	@RequestMapping(value = "/detail/{poid}")
	public String detail(@PathVariable("poid") String poid, Model model, HttpServletRequest request) throws Exception {
		
		Orders order = orderDao.findByPoid(Integer.parseInt(poid));
		
		OutpatientLisSampleInfo orderInfo = hisAccessDao.findByFormNo(order.getFormNo());
		
		model.addAttribute("order", order);
		model.addAttribute("orderInfo", orderInfo);
		
		return "myOrderDetail";
	}

	/**
	 * 我的预约-取消预约
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@Operate(desc="我的预约-取消预约")
	@ResponseBody
	@RequestMapping(value = "/cancel/{formNo}")
	public AjaxResult cancel(@PathVariable("formNo") String formNo, Model model, HttpServletRequest request) {
		try {
			Map<String, String> res = this.orderService
					.cancelOrders(formNo, getLoginUserInfo());
			
			if ("1".equals(res.get("resKey"))) {
				return ajaxSuccess(res.get("message"));
			} else {
				return ajaxFail(res.get("message"));
			}
			
		} catch(AppRuntimeException e) {
			return ajaxFail(e.getExplanation());
		} catch(Exception e) {
			return ajaxFail("数据异常，预约失败！");
		}
	}
}
