package com.senyint.wx.mobile.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.senyint.wx.common.dao.AccessStatisticsDao;
import com.senyint.wx.common.dao.HisAccessDao;
import com.senyint.wx.common.dao.UserRelationDao;
import com.senyint.wx.common.entity.AccessStatistics;
import com.senyint.wx.common.entity.ForegroundUser;
import com.senyint.wx.common.entity.UserRelation;
import com.senyint.wx.common.web.Constants;
import com.senyint.wx.common.web.Operate;
import com.senyint.wx.mobile.dao.LisReportDao;
import com.senyint.wx.mobile.entity.ReportLisResult;
import com.senyint.wx.mobile.entity.ReportLisSampleInfo;
import com.senyint.wx.mobile.service.ForegroundRegistrationService;

@Controller
@RequestMapping(value = "/report")
public class MBReportAction extends MBSupportAction {

	@Autowired
	private HisAccessDao hisAccessDao;

	@Autowired
	private LisReportDao lisReportDao;
	
	@Autowired
	private UserRelationDao userRelationDao;
	
	@Autowired
	private ForegroundRegistrationService foregroundRegistrationService;
	
	@Autowired
	private AccessStatisticsDao accessStatisticsDao;
	/**
	 * The method <code> index </code> . 报告查询首页
	 * 
	 * @author sunzhi Senyint (Dalian) Co., Ltd.
	 * 
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@Operate(desc = "报告查询-首页")
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model, HttpServletRequest request,HttpSession session) throws Exception {
		// 常用就诊人列表
		Conjunction con = Restrictions.and();
		con.add(Restrictions.eq("patientId", this.getLoginUserId()));
		List<UserRelation> urList= userRelationDao.findByCriteria(false, con);
		List<ForegroundUser> userList = foregroundRegistrationService.batchFindForegroundUserById(urList);
		ForegroundUser fu= (ForegroundUser)session.getAttribute(Constants.SESSION_TOP_USER_INFO_KEY);
		model.addAttribute("myForegroundUser",fu);
		model.addAttribute("foregroundUserList", userList);

		return "selfService/reportSelect/reportIndex";
	}

	/**
	 * The method <code> lisReportList </code> . lis检验报告列表
	 * 
	 * @author sunzhi Senyint (Dalian) Co., Ltd.
	 * @param model
	 * @return
	 */
	@Operate(desc = "lis报告列表")
	@RequestMapping(value = "/lisList", method = {RequestMethod.GET, RequestMethod.POST})
	public String lisReportListIndex(Model model, HttpServletRequest request,
			String name,String cardid) {
		int pageSum = lisReportDao.findReportLisCount(name,cardid);
		model.addAttribute("cardid", cardid);
		model.addAttribute("name", name);
		model.addAttribute("pageSum", pageSum);
		return "selfService/reportSelect/reportLisList";
	}

	@ResponseBody
	@RequestMapping(value = "/append", method = RequestMethod.POST)
	public String lisAppendDate(Model model, HttpServletRequest request,HttpSession session) {
		String pageNum = request.getParameter("pageNum");
		String cardId = request.getParameter("cardid").trim().toUpperCase();
		String name=request.getParameter("name");
		int startNum = 0;
		int endNum = 10;
		if (StringUtils.isNotEmpty(pageNum)) {
			startNum = Integer.parseInt(pageNum);
			endNum = Integer.parseInt(pageNum) + 10;
		}

		List<ReportLisSampleInfo> lisSampleInfoList = lisReportDao
				.findReportLisPage(name,cardId, startNum, endNum);
		StringBuffer outhtml = new StringBuffer("");
		for (ReportLisSampleInfo info : lisSampleInfoList) {
			outhtml.append("<a href=\"" + request.getContextPath()
					+ "/report/lisDetail?bblsh=" + info.getBblsh()
					+ " \"  class=\"list-group-item\"><h5>" + info.getSqxm()
					+ "<small>&nbsp;&nbsp;" + info.getBgsj()
					+ "</small></h5></a>");
		}
		//记录访问统计
		ForegroundUser fu= (ForegroundUser)session.getAttribute(Constants.SESSION_TOP_USER_INFO_KEY);
		AccessStatistics ast= new AccessStatistics();
		ast.setAccessStatistics(fu, Constants.AS_LIS_KEY);
		accessStatisticsDao.makePersistent(ast);
		return outhtml.toString();
	}

	/**
	 * The method <code> lisReportDetail </code> . lis报告明细
	 * 
	 * @author sunzhi Senyint (Dalian) Co., Ltd.
	 * 
	 * @param model
	 * @return
	 */
	@Operate(desc = "lis报告明细")
	@RequestMapping(value = "/lisDetail", method = RequestMethod.GET)
	public String lisReportDetail(String bblsh, Model model) {
		List<ReportLisResult> lisResultList = lisReportDao
				.findLisByBblsh(bblsh);
		ReportLisSampleInfo lisSampleInfo = lisReportDao
				.findReportLisSampleEntityByBblsh(bblsh);
		model.addAttribute("lisResultList", lisResultList);
		model.addAttribute("lisSampleInfo", lisSampleInfo);
		return "selfService/reportSelect/reportLisDetail";
	}


	public LisReportDao getLisReportDao() {
		return lisReportDao;
	}

	public void setLisReportDao(LisReportDao lisReportDao) {
		this.lisReportDao = lisReportDao;
	}

}
