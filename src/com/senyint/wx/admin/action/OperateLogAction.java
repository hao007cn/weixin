/**
 * OperateLogAction.java
 * com.senyint.wx.admin.action
 * Function: TODO ADD FUNCTION
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2014年12月4日 		gjp
 *
 * Copyright (c) 2014, senyint All Rights Reserved.
 */
/**
 * @File: OperateLogAction.java
 * @Package com.senyint.wx.admin.action
 * @Description: TODO Describe the File
 *
 * @Company: senyint (Dalian) Co., Ltd
 * @author   gjp
 * @date     2014年12月4日
 * @version  1.0
 */

package com.senyint.wx.admin.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.senyint.common.util.DateUtils;
import com.senyint.common.web.ajax.GridParam;
import com.senyint.common.web.ajax.GridResult;
import com.senyint.wx.common.dao.OperateLogDao;
import com.senyint.wx.common.entity.OperateLog;
import com.senyint.wx.common.web.Operate;

/**
 * 日誌操作
 * 
 * @Type: OperateLogAction

 * 
 * @Company: senyint (Dalian) Co., Ltd
 * @author gjp
 * @date 2014年12月4日
 * @version 1.0
 * 
 */
@Controller
@RequestMapping(value = "/operatelog")
public class OperateLogAction extends SupportAction {

	@Autowired
	private OperateLogDao operateLogDao;

	/**
	 * 日誌頁面跳轉 
	 * 
	 * @author gjp senyint (Dalian) Co., Ltd.
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@Operate(desc = "日志页面")
	@RequestMapping(method = RequestMethod.GET)
	public String index(ModelMap model, HttpServletRequest request,
			HttpSession session) {

		return "operateLog";
	}

	/**
	 * 加載頁面grid數據 
	 * )
	 * 
	 * @author gjp senyint (Dalian) Co., Ltd.
	 * 
	 * @param gridParam
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "loadpage", method = RequestMethod.POST)
	public GridResult loadPage(GridParam gridParam, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		Conjunction con = Restrictions.and();
		if (StringUtils.isNotBlank(request.getParameter("tempid"))) {
			con.add(Restrictions.eq("poid",
					request.getParameter("tempid")));
		}
		if (StringUtils.isNotBlank(request.getParameter("startDate"))) {
			String startDate=request.getParameter("startDate")+" 00:00:00";
			con.add(Restrictions.ge("operate_time", DateUtils.strToDate(startDate, "yyyy-MM-dd HH:mm:ss")));
		}
		if (StringUtils.isNotBlank(request.getParameter("endDate"))) {
			String endDate=request.getParameter("endDate")+" 23:59:59";
			con.add(Restrictions.le("operate_time",DateUtils.strToDate(endDate,"yyyy-MM-dd HH:mm:ss")));
		}
		int records = operateLogDao.fetchCountByCriteria(false, con);
		List<OperateLog> list = operateLogDao.findByCriteria(
				gridParam.getStartRow(), gridParam.getRows(), "operate_time desc",
				false, con);
		return gridResult(gridParam, records, list);
	}
}
