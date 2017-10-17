/**
 * AboutAction.java
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
* @File: AboutAction.java
* @Package com.senyint.wx.mobile.action
* @Description: TODO Describe the File
*
* @Company: senyint (Dalian) Co., Ltd
* @author   gjp
* @date     2014年12月11日
* @version  1.0
*/

package com.senyint.wx.mobile.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.senyint.wx.common.entity.HospitalSetting;
import com.senyint.wx.mobile.dao.AboutDao;

/**
 * ClassName:AboutAction
 *
 * @author   gjp
 * @version  
 * @since    Ver 1.1
 * @Date	 2014年12月11日
 *
 * @see 	 
 */
@Controller
@RequestMapping( value = "/about")
public class MBAboutAction {
	
	@Autowired
	private AboutDao aboutDao;
	
	/**
	 * 关于医院简介		
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
		List<HospitalSetting> list= aboutDao.findAll(false);
		if(list.size()>0)
		{
			model.addAttribute("about",list.get(0));
		}
		return "about";
	}
}
