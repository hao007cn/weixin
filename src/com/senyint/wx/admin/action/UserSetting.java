/**
 * UserSetting.java
 * com.senyint.wx.admin.action
 * Function: TODO ADD FUNCTION
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2014年12月8日 		gjp
 *
 * Copyright (c) 2014, senyint All Rights Reserved.
*/
/**
* @File: UserSetting.java
* @Package com.senyint.wx.admin.action
* @Description: TODO Describe the File
*
* @Company: senyint (Dalian) Co., Ltd
* @author   gjp
* @date     2014年12月8日
* @version  1.0
*/

package com.senyint.wx.admin.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.senyint.common.util.MD5Util;
import com.senyint.wx.admin.dao.UserDao;
import com.senyint.wx.admin.entity.User;
import com.senyint.wx.common.utils.ArgumentUtil;
import com.senyint.wx.common.web.Operate;

/**
 * ClassName:UserSetting
 *
 * @author   gjp
 * @version  
 * @since    Ver 1.1
 * @Date	 2014年12月8日
 *
 * @see 	 
 */

@Controller
@RequestMapping(value = "/usersetting")
public class UserSetting extends SupportAction {

	@Autowired
	private UserDao userDao;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(ModelMap model, HttpServletRequest request,
			HttpSession session) {

		model.addAttribute("userid", getLoginUserId());
		return "userSetting";
	}

	/**
	 * 個人資料修改操作 
	 * 
	 * @author gjp senyint (Dalian) Co., Ltd.
	 * 
	 * @param user
	 * @param request
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@Operate(desc = "修改个人资料")
	@RequestMapping(value = "saveuser", method = RequestMethod.POST)
	public String saveUser(User user, HttpServletRequest request,
			HttpSession session, RedirectAttributes redirectAttributes) {

		// 修改操作
		User temp = userDao.findByPoid(user.getPoid());
		temp.setEnabled(user.getEnabled());
		temp.setName(user.getName());
		if (!user.getPassword().isEmpty()) {
			temp.setPassword(MD5Util.md5Encrypt(user.getPassword()));
		}
		temp.setSex(user.getSex());
		temp.setUsername(user.getUsername());
		temp.setModifyDate(ArgumentUtil.getSysDate());
		temp.setModifyUserName(getLoginUserInfo().getUsername());
		userDao.makePersistent(temp);
		redirectAttributes.addFlashAttribute("message", "保存成功");
		return "redirect:/admin/usersetting";
	}
}
