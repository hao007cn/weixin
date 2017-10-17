/**
 * FeedbackAction.java
 * com.senyint.wx.mobile.action
 * Function: TODO ADD FUNCTION
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2014年12月11日 		gjp
 *
 * Copyright (c) 2014, senyint All Rights Reserved.
*/
/**
* @File: FeedbackAction.java
* @Package com.senyint.wx.mobile.action
* @Description: TODO Describe the File
*
* @Company: senyint (Dalian) Co., Ltd
* @author   gjp
* @date     2014年12月11日
* @version  1.0
*/

package com.senyint.wx.mobile.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.senyint.wx.common.dao.FeedbackDao;
import com.senyint.wx.common.entity.Feedback;
import com.senyint.wx.common.entity.ForegroundUser;
import com.senyint.wx.common.utils.ArgumentUtil;
import com.senyint.wx.common.web.Constants;


/**
 * ClassName:FeedbackAction
 *
 * @author   gjp
 * @version  
 * @since    Ver 1.1
 * @Date	 2014年12月11日
 *
 * @see 	 
 */

@Controller
@RequestMapping( value = "/feedback")
public class MBFeedbackAction {
	
	@Autowired
	private FeedbackDao feedbackDao;
	/**
	 * 问题反馈		
	* 		
	* @author  gjp senyint (Dalian) Co., Ltd.		
	* 
	* @param model
	* @param request
	* @param session
	* @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String index(ModelMap model, HttpServletRequest request,
			HttpSession session){
		return "feedback";
	}
	/**
	 * 保存问题反馈		
	* 		
	* @author  gjp senyint (Dalian) Co., Ltd.		
	* 
	* @param request
	* @param session
	* @return
	 */
	@ResponseBody
	@RequestMapping(value="save",method= RequestMethod.POST)
	public Map<String, Integer> save(HttpServletRequest request,
			HttpSession session){
		Feedback feedback = new Feedback();
		feedback.setContent(request.getParameter("feedback"));
		ForegroundUser fu= (ForegroundUser)session.getAttribute(Constants.SESSION_TOP_USER_INFO_KEY);
		feedback.setCreat_date(ArgumentUtil.getSysDate());
		feedback.setCreat_userid(fu.getPoid());
		feedback.setCreat_username(fu.getName());
		feedback.setRead_flag(1);
		Map<String,Integer> map = new HashMap<String, Integer>();
		feedbackDao.makePersistent(feedback);
		map.put("flag",1);
		return map;
	}
}
