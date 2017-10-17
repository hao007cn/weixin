package com.senyint.wx.admin.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/icons")
public class IconsAction {
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "icons";
	}
}
