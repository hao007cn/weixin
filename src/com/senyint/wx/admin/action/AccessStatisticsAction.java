package com.senyint.wx.admin.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.senyint.wx.common.dao.AccessStatisticsDao;
import com.senyint.wx.common.entity.AccessStatistics;
import com.senyint.wx.common.utils.ArgumentUtil;
import com.senyint.wx.common.web.Constants;

@Controller
@RequestMapping(value = "/accessstatistics")
public class AccessStatisticsAction {
	@Autowired
	private AccessStatisticsDao accessStatisticsDao;
	public String index(Model model, HttpServletRequest request){
	
		//微信用户统计
		AccessStatistics ast= new AccessStatistics();
		ast.setVisit_module(Constants.AS_LIS_KEY);
		ast.setVisit_time(ArgumentUtil.getSysDate());
		//今日lis访问总数
		int lisCount=accessStatisticsDao.getAccessDateAndTypeCount(ast);
		ast.setVisit_module(Constants.AS_PACS_KEY);
		//今日pacs访问总数
		int pacsCount=accessStatisticsDao.getAccessDateAndTypeCount(ast);
		ast.setVisit_time(null);
		//pacs访问总数
		int pacsSum=accessStatisticsDao.getAccessTypeCount(ast);
		ast.setVisit_module(Constants.AS_LIS_KEY);
		//lis访问总数
		int lisSum=accessStatisticsDao.getAccessTypeCount(ast);
		model.addAttribute("lisCount", lisCount);
		model.addAttribute("pacsCount", pacsCount);
		model.addAttribute("todayCount", lisCount+pacsCount);
		model.addAttribute("pacsSum", pacsSum);
		model.addAttribute("lisSum", lisSum);
		model.addAttribute("totalCount", pacsSum+lisSum);
		return "accessStatistics";
	}
}
