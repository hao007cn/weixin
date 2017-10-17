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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.senyint.common.web.ajax.GridParam;
import com.senyint.common.web.ajax.GridResult;
import com.senyint.wx.admin.dao.ArgumentDao;
import com.senyint.wx.admin.entity.Argument;
import com.senyint.wx.common.web.Operate;

@Controller
@RequestMapping(value = "/arguments")
public class ArgumentAction extends SupportAction{
	
	@Autowired
	private ArgumentDao argumentDao;
	
	/**
	 * 参数配置主页
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@Operate(desc = "参数配置页")
	@RequestMapping( method = RequestMethod.GET)
	public String index(Model model){
		return "arguments";
	}

	/**
	 * 
	 * @param gridParam
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@Operate( desc = "参数数据加载页")
	@ResponseBody
	@RequestMapping(value = "loadpage" , method = RequestMethod.POST)
	public GridResult loadPage(GridParam gridParam, HttpServletRequest request,
			HttpServletResponse response, HttpSession session){
		
		Conjunction con =  Restrictions.and();
		String argumentName = request.getParameter("argumentName");
		if(StringUtils.isNotBlank(argumentName)){
			con.add(Restrictions.like("name", argumentName, MatchMode.ANYWHERE));
		}
		
		con.add(Restrictions.eq("deleteFlg", false));
		int records = argumentDao.fetchCountByCriteria(false, con);
		List<Argument> argyList = argumentDao.findByCriteria(gridParam.getStartRow(), gridParam.getRows(), "name desc", false,con); 
		for(Argument arg : argyList){
			String value = arg.getValue();
			String temp = "";
			boolean flag = value.contains("\\\\");
			if(flag){
				temp = value.replace("\\\\", "\\");
				arg.setValue(temp);
			}
		}
		return gridResult(gridParam,records,argyList);
	}
	
	@Operate(desc = "保存参数设置")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(Argument argument , HttpServletRequest request,
			HttpSession session, RedirectAttributes redirectAttributes) {
		if (argument.getPoid() == null) {
			argument.setDeleteFlg(false);
			argumentDao.makePersistent(argument);
		} else {
			Argument argtemp = argumentDao.findByPoid(argument.getPoid());
			String temp = argtemp.getValue();
			if(temp.contains("\\")){
				String value = temp.replace("\\", "\\");
				argtemp.setValue(value);
			}else{
				argtemp.setValue(argument.getValue());
			}
			argumentDao.makePersistent(argtemp);
		}
		redirectAttributes.addFlashAttribute("message", "保存成功");
		return "redirect:/admin/arguments";
	}
	
	@Operate(desc = "获取参数数据")
	@ResponseBody
	@RequestMapping(value = "/getdata", method = RequestMethod.POST)
	public Argument getData(Integer poid, HttpServletRequest request,
			HttpSession session) {
		Argument argument = argumentDao.findByPoid(poid);
		//转义斜线
		String value = argument.getValue();
		String temp = "";
		boolean flag = value.contains("\\\\");
		if(flag){
			temp = value.replace("\\\\", "\\");
			argument.setValue(temp);
		}
		return argument;
	}
	
	public ArgumentDao getArgumentDao() {
		return argumentDao;
	}

	public void setArgumentDao(ArgumentDao argumentDao) {
		this.argumentDao = argumentDao;
	}
	
	
}
