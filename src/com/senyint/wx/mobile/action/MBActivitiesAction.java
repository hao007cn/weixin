/**
 * Activities.java
 * com.senyint.wx.mobile.action
 * Function: TODO ADD FUNCTION
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2014年12月10日 		gjp
 *
 * Copyright (c) 2014, senyint All Rights Reserved.
*/
/**
* @File: Activities.java
* @Package com.senyint.wx.mobile.action
* @Description: TODO Describe the File
*
* @Company: senyint (Dalian) Co., Ltd
* @author   gjp
* @date     2014年12月10日
* @version  1.0
*/

package com.senyint.wx.mobile.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.senyint.common.util.DateFormatUtils;
import com.senyint.wx.common.dao.NewsDao;
import com.senyint.wx.common.entity.News;

/**
 * ClassName:Activities
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   gjp
 * @version  
 * @since    Ver 1.1
 * @Date	 2014年12月10日
 *
 * @see 	 
 */

@Controller
@RequestMapping( value = "/activities")
public class MBActivitiesAction {
	
	@Autowired
	private NewsDao newsDao;
	/**
	 * 新闻活动初始化列表页面		
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
			HttpSession session) {
		int datasum= newsDao.fetchCountByCriteria(false);
		model.addAttribute("pageSum",datasum);
		return "activities";
	}
	/**
	 * 新闻活动详细信息		
	* 		
	* @author  gjp senyint (Dalian) Co., Ltd.		
	* 
	* @param id
	* @param model
	* @param request
	* @param session
	* @param redirectAttributes
	* @return
	 */
	@RequestMapping(value="activitieWith",method = RequestMethod.GET)
	public String get(Integer id,ModelMap model,HttpServletRequest request,
			HttpSession session, RedirectAttributes redirectAttributes) {
		News news= newsDao.findByPoid(id);

		model.addAttribute("news", news);
		return "activitieWith";
	}
	/**
	 * 新闻活动下拉分页加载		
	* @author  gjp senyint (Dalian) Co., Ltd.		
	* 
	* @param model
	* @param response
	* @param request
	* @param session
	* @param redirectAttributes
	* @return
	 */
	@ResponseBody
	@RequestMapping(value="append",method = RequestMethod.POST)
	public String appendDate(ModelMap model,HttpServletResponse response,HttpServletRequest request,
			HttpSession session, RedirectAttributes redirectAttributes) {
		String pageNum= request.getParameter("pageNum");
		List<News> list= newsDao.findByCriteria(Integer.parseInt(pageNum), 10, "", false);
		StringBuffer outhtml= new StringBuffer("");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		for (News news : list) {
			outhtml.append("<a href=\""+request.getContextPath()+"/activities/activitieWith?id="+news.getPoid()+"\" class=\"list-group-item\"><h5>"+news.getTitle()+"&nbsp;&nbsp;<small>"+sdf.format(news.getCreateDate())+"</small></h5></a>");
		}
		return outhtml.toString();
	}
}
