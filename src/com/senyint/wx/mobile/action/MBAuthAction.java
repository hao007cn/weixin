package com.senyint.wx.mobile.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping( value = {"/"})
public class MBAuthAction {
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model,HttpServletRequest request) {
		
		String rd=request.getParameter("rd");
		if(StringUtils.isNotEmpty(rd))
		{
			model.addAttribute("rd",rd);
		}
		return "auth";
	}
	@RequestMapping(value="auth", method = {RequestMethod.GET, RequestMethod.POST})
	public String indexMove(Model model,HttpServletRequest request) {
		String rd=request.getParameter("rd");
		if(StringUtils.isNotEmpty(rd))
		{
			model.addAttribute("rd",rd);
		}
		return "auth";
	}
}
