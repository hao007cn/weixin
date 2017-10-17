/**
 * FeetBackAction.java
 * com.senyint.wx.admin.action
 * Function: TODO ADD FUNCTION
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2014年12月20日 		gjp
 *
 * Copyright (c) 2014, senyint All Rights Reserved.
 */
/**
 * @File: FeetBackAction.java
 * @Package com.senyint.wx.admin.action
 * @Description: TODO Describe the File
 *
 * @Company: senyint (Dalian) Co., Ltd
 * @author   gjp
 * @date     2014年12月20日
 * @version  1.0
 */

package com.senyint.wx.admin.action;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.senyint.common.web.ajax.GridParam;
import com.senyint.common.web.ajax.GridResult;
import com.senyint.wx.common.dao.FeedbackDao;
import com.senyint.wx.common.entity.Feedback;
import com.senyint.wx.common.web.Operate;

/**
 * ClassName:FeedBackAction
 * 
 * @author gjp
 * @version
 * @since Ver 1.1
 * @Date 2014年12月20日
 * 
 * @see
 */
@Controller
@RequestMapping(value = "/feedback")
public class FeedBackAction extends SupportAction {

	@Autowired
	private FeedbackDao feedbackDao;

	@Operate(desc = "反馈页面")
	@RequestMapping(method = RequestMethod.GET)
	public String index(ModelMap model, HttpServletRequest request,
			HttpSession session) {
		return "feedback";
	}

	/**
	 * 加载表格数据
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
		if (StringUtils.isNotBlank(request.getParameter("content"))) {
			con.add(Restrictions.like("content",request.getParameter("content"),MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotBlank(request.getParameter("readflag"))) {
			con.add(Restrictions.eq("read_flag",Integer.parseInt(request.getParameter("readflag"))));
		}
		int records = feedbackDao.fetchCountByCriteria(false, con);
		List<Feedback> list = feedbackDao.findByCriteria(
				gridParam.getStartRow(), gridParam.getRows(),
				"creat_date desc", false, con);
		return gridResult(gridParam, records, list);
	}
	/**
	 * 取得反馈详细内容		
	* The method <code> getData </code> .		 				
	* @author  gjp senyint (Dalian) Co., Ltd.		
	* 
	* @param poid
	* @param request
	* @param session
	* @return
	 */
	@ResponseBody
	@RequestMapping(value = "getdata", method = RequestMethod.POST)
	public Feedback getData(Integer poid, HttpServletRequest request,
			HttpSession session) {
		Feedback feedback = feedbackDao.findByPoid(poid);
		return feedback;
	}
	/**
	 * 保存反馈状态		
	* The method <code> save </code> .		 				
	* @author  gjp senyint (Dalian) Co., Ltd.		
	* 
	* @param feedback
	* @param request
	* @param session
	* @param redirectAttributes
	* @return
	 */
	@Operate(desc = "保存反馈状态")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(Feedback feedback, HttpServletRequest request,
			HttpSession session, RedirectAttributes redirectAttributes) {
		if (feedback.getPoid() != null) {
			Feedback temp = feedbackDao.findByPoid(feedback.getPoid());
			temp.setRead_flag(feedback.getRead_flag());
			feedbackDao.makePersistent(temp);
			redirectAttributes.addFlashAttribute("message", "保存成功");
		}
		return "redirect:/admin/feedback";
	}
}
