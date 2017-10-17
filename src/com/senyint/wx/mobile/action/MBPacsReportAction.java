/**
 * PacsReportAction.java
 * com.senyint.wx.mobile.action
 * Function: TODO ADD FUNCTION
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2014年12月22日 		gjp
 *
 * Copyright (c) 2014, senyint All Rights Reserved.
*/
/**
* @File: PacsReportAction.java
* @Package com.senyint.wx.mobile.action
* @Description: TODO Describe the File
*
* @Company: senyint (Dalian) Co., Ltd
* @author   gjp
* @date     2014年12月22日
* @version  1.0
*/

package com.senyint.wx.mobile.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.senyint.wx.common.dao.AccessStatisticsDao;
import com.senyint.wx.common.entity.AccessStatistics;
import com.senyint.wx.common.entity.ForegroundUser;
import com.senyint.wx.common.web.Constants;
import com.senyint.wx.mobile.dao.PacsReportDao;
import com.senyint.wx.mobile.entity.ReportPacsSj;

/**
 * ClassName:PacsReportAction
 *
 * @author   gjp
 * @version  
 * @since    Ver 1.1
 * @Date	 2014年12月22日
 *
 * @see 	 
 */
@Controller
@RequestMapping(value = "/pacsreport")
public class MBPacsReportAction {
	@Autowired
	private PacsReportDao pacsReportDao;
	@Autowired
	private AccessStatisticsDao accessStatisticsDao;
	/**
	 * 初始化页面		
	* The method <code> index </code> .		 		
	* 		
	* @author  gjp senyint (Dalian) Co., Ltd.		
	* 
	* @param model
	* @param request
	* @param session
	* @return
	 */
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
	public String index(ModelMap model, HttpServletRequest request,
			HttpSession session) {
		String cardid= request.getParameter("cardid").trim().toUpperCase();
		String name= request.getParameter("name");
		int rs= pacsReportDao.findReportPacsCount(name,cardid);
		model.addAttribute("pageSum",rs);
		model.addAttribute("cardid",cardid);
		model.addAttribute("name",name);
		return "selfService/reportSelect/reportPacsList";
	}
	/**
	 * 下拉分页查询		
	* The method <code> appendDate </code> .		 		
	* 		
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
		String cardid= request.getParameter("cardid").trim().toUpperCase();
		String name= request.getParameter("name");
		int startNum=0;
		int endNum=10;
		if(StringUtils.isNotEmpty(pageNum))
		{
			startNum=Integer.parseInt(pageNum);
			endNum=Integer.parseInt(pageNum)+10;
		}
		List<ReportPacsSj> pacsList= pacsReportDao.findReportPacsPage(name,cardid, startNum, endNum);
		StringBuffer outhtml= new StringBuffer("");
		for (ReportPacsSj pacsSj : pacsList) {
			outhtml.append("<a href=\""+request.getContextPath()+"/pacsreport/pacsreportwith?id="+pacsSj.getXeguid()+"\" class=\"list-group-item\"><h5>"+pacsSj.getApplydepartment()+"&nbsp;&nbsp;<small>"+pacsSj.getStudytime()+"</small></h5></a>");
		}
		//记录访问统计
		ForegroundUser fu= (ForegroundUser)session.getAttribute(Constants.SESSION_TOP_USER_INFO_KEY);
		AccessStatistics ast= new AccessStatistics();
		ast.setAccessStatistics(fu, Constants.AS_PACS_KEY);
		accessStatisticsDao.makePersistent(ast);
		return outhtml.toString();
	}
	/**
	 * pacs报告详细信息		
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
	@RequestMapping(value="pacsreportwith",method = RequestMethod.GET)
	public String get(String id,ModelMap model,HttpServletRequest request,
			HttpSession session, RedirectAttributes redirectAttributes) {
		ReportPacsSj rps= pacsReportDao.getReportPacs(id);
		model.addAttribute("pacsitem", rps);
		return "selfService/reportSelect/reportPacsDetail";
	}
}
