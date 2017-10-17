/**
 * HospitalSetting.java
 * com.senyint.wx.admin.action
 * Function: TODO ADD FUNCTION
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2014年11月26日 		gjp
 *
 * Copyright (c) 2014, senyint All Rights Reserved.
*/
/**
* @File: HospitalSetting.java
* @Package com.senyint.wx.admin.action
* @Description: TODO Describe the File
*
* @Company: senyint (Dalian) Co., Ltd
* @author   gjp
* @date     2014年11月26日
* @version  1.0
*/

package com.senyint.wx.admin.action;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.senyint.wx.admin.dao.HospitalSettingDao;
import com.senyint.wx.common.entity.HospitalSetting;
import com.senyint.wx.common.utils.ArgumentUtil;
import com.senyint.wx.common.web.Operate;
/**
 * ClassName:HospitalSetting
 *
 * @author   gjp
 * @version  
 * @since    Ver 1.1
 * @Date	 2014年11月26日
 *
 * @see 	 
 */
@Controller
@RequestMapping(value = "/hospitalSetting")
public class HospitalSettingAction {
	@Autowired
	private HospitalSettingDao hospitalSettingDao;
	
	@Operate(desc = "医院信息基本设置页面")
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model,HttpServletRequest request, HttpSession session) {
		
		List<HospitalSetting> list = hospitalSettingDao.findAll(false);
		model.addAttribute("hospitalSetting",list.get(0));
		return "hospitalSetting";
	}
	@Operate(desc = "保存医院基本设置")
	@RequestMapping(value="/save",method = RequestMethod.POST)
	public String save(HttpServletRequest request, HttpSession session,RedirectAttributes redirectAttributes) { 
		String hospitalContent = request.getParameter("hospitalContent");
		if(!hospitalContent.isEmpty())
		{
			HospitalSetting hs =new HospitalSetting();
			hs.setPoid(1);
			hs.setHospitalContent(hospitalContent);
			hs.setUpdateDate(ArgumentUtil.getSysDate());
			hospitalSettingDao.makePersistent(hs);
			redirectAttributes.addFlashAttribute("message", "编辑成功");
		}
		return "redirect:/admin/hospitalSetting";
	}

}
