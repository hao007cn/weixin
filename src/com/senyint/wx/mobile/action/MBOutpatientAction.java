package com.senyint.wx.mobile.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.senyint.common.util.DateUtils;
import com.senyint.wx.common.dao.AccessStatisticsDao;
import com.senyint.wx.common.dao.HisAccessDao;
import com.senyint.wx.common.entity.ForegroundUser;
import com.senyint.wx.common.web.Constants;
import com.senyint.wx.common.web.Operate;
import com.senyint.wx.mobile.entity.OutpatientLisSampleInfo;

@Controller
@RequestMapping(value = "/outpatient")
public class MBOutpatientAction extends MBSupportAction {

	@Autowired
	private HisAccessDao hisAccessDao;

	@Autowired
	private AccessStatisticsDao accessStatisticsDao;
	/**
	 * The method <code> index </code> . 看诊查询首页
	 * 
	 * @author zhaocl Senyint (Dalian) Co., Ltd.
	 * 
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@Operate(desc = "看诊查询-首页")
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model, HttpServletRequest request,HttpSession session) throws Exception {
		// 常用就诊人列表
		//从session中提取姓名 身份证信息
		ForegroundUser fu = (ForegroundUser) session.getAttribute(Constants.SESSION_TOP_USER_INFO_KEY);
		int pageSum = hisAccessDao.findOutpatientCount(fu.getCardid(),fu.getName(), HisAccessDao.TYPE_KAN_ZHEN_JI_LU);
		model.addAttribute("cardid", fu.getCardid());
		model.addAttribute("fuName", fu.getName());
		model.addAttribute("pageSum", pageSum);
		return "selfService/outpatient/outpatientIndex";
	}
	@ResponseBody
	@RequestMapping(value = "/append", method = RequestMethod.POST)
	public String lisAppendDate(Model model, HttpServletRequest request,HttpSession session) throws Exception {
		String pageNum = request.getParameter("pageNum");
		String cardId = request.getParameter("cardid").trim().toUpperCase();
		String fuName = request.getParameter("fuName");
		int startNum = 0;
		int endNum = 10;
		if (StringUtils.isNotEmpty(pageNum)) {
			startNum = Integer.parseInt(pageNum);
			endNum = Integer.parseInt(pageNum) + 10;
		}
		List<OutpatientLisSampleInfo> lisSampleInfoList = hisAccessDao
					.findOutpatientPage(cardId,fuName,startNum, endNum, HisAccessDao.TYPE_KAN_ZHEN_JI_LU);
		StringBuffer outhtml = new StringBuffer("");
		for (OutpatientLisSampleInfo info : lisSampleInfoList) {
			String doctorName = info.getKsname();
			
			if(StringUtils.isEmpty(doctorName)){
				doctorName = "";
			}
			
			if("null".equals(doctorName)){
				doctorName = "";
			}
			
			outhtml.append("<tr>");
			outhtml.append("<td>" + doctorName + "</td>");
			outhtml.append("<td>" + info.getZzkzys()+ "</td>");
			outhtml.append("<td>" + (info.getExecuteState() > 0 ? "完成" : "未完成") + "</td>");
			outhtml.append("<td>" + DateUtils.format(info.getKzdate(), "yyyy-MM-dd") + "</td>");
			outhtml.append("/<tr>");
		}
		return outhtml.toString();
	}
}
