/**
 * LoginAction.java
 * com.senyint.wx.web.action.system
 * Function: 登陆Action
 *
 *   ver     date      	    author
 * ──────────────────────────────────
 *           2014-11-19        sunzhi
 *
 * Copyright (c) 2014, Senyint All Rights Reserved.
 */

package com.senyint.wx.admin.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.senyint.common.util.MD5Util;
import com.senyint.wx.admin.bean.RoleBean;
import com.senyint.wx.admin.bean.UserInfo;
import com.senyint.wx.admin.dao.UserDao;
import com.senyint.wx.admin.entity.Role;
import com.senyint.wx.admin.entity.User;
import com.senyint.wx.common.utils.ArgumentUtil;
import com.senyint.wx.common.web.Constants;
import com.senyint.wx.common.web.Operate;

/**
 * ClassName:LoginAction Function: 系统用户登陆、登出 Reason: 登陆Action
 * 
 * @author sunzhi
 * @version
 * @since Ver 1.1
 * @Date 2014-11-19
 * 
 * @see
 */
@Controller
public class LoginAction {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private AuthenticationManager myAuthenticationManager;
	
	@RequestMapping(value = {"/", "/admin", "/admin/login"}, method = RequestMethod.GET)
	public String index(HttpServletRequest request) {
		//重新登录时销毁该用户的Session
		Object o = request.getSession().getAttribute(Constants.SPRING_SECURITY_CONTEXT);
		if(null != o){
			request.getSession().removeAttribute(Constants.SPRING_SECURITY_CONTEXT);
		}
		return "login";
	}

	@Operate(desc = "登录")
	@RequestMapping(value = "/admin/login", method = RequestMethod.POST)
	public String login(User user, ModelMap model,
			HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttributes) {
		try {
			// 取得用户名及密码
			String username = user.getUsername();
			String password = user.getPassword();
	
			if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
				redirectAttributes.addFlashAttribute("errMsg", "请输入用户名或密码！");
				return "redirect:/admin";
			}
			// 从数据库里检索用户
			User userPo = userDao.findByUsername(username);
	
			// 如果数据库里没有该用户或者该用户
			if (userPo == null || !MD5Util.md5Encrypt(password).equals(userPo.getPassword())) {
				redirectAttributes.addFlashAttribute("errMsg", "登陆失败，用户名或密码不正确！");
				return "redirect:/admin";
			}
	
			UserInfo userInfo = new UserInfo();
			userInfo.setUsername(userPo.getUsername());
			userInfo.setName(userPo.getName());
			userInfo.setPassword(userPo.getPassword());
			userInfo.setPhoto("");
			userInfo.setPoid(userPo.getPoid());
			userInfo.setSex(userPo.getSex().name());
	
			if (userPo.getRoles() != null && userPo.getRoles().size() > 0) {
				List<RoleBean> roleBeans = new ArrayList<RoleBean>();
				for (Role role : userPo.getRoles()) {
					RoleBean roleBean = new RoleBean();
					roleBean.setPoid(role.getPoid());
					roleBean.setName(role.getName());
					roleBean.setDesc(role.getDesc());
					if (role.getParent() != null) {
						roleBean.setParentId(role.getParent().getPoid());
					}
					roleBeans.add(roleBean);
				}
	
				userInfo.setRoles(roleBeans);
			}
			// gjp登录后记录登录时间
			userPo.setLastlogintime(ArgumentUtil.getSysDate());
			userDao.makePersistent(userPo);
			
			
			Authentication authentication = myAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,MD5Util.md5Encrypt(password)));
			SecurityContext securityContext = SecurityContextHolder.getContext();
			securityContext.setAuthentication(authentication);
			session.setAttribute(Constants.SPRING_SECURITY_CONTEXT, securityContext);  
			
			// session处理
			session.setAttribute(Constants.SESSION_USER_INFO_KEY, userInfo);
			
			String remember = request.getParameter("remember");
			if (StringUtils.isNotEmpty(remember)) {
				redirectAttributes.addFlashAttribute("userRemember", user);
			} else {
				redirectAttributes.addFlashAttribute("deleteCookie", "0");
			}
			
			} catch (AuthenticationException ae) {  
				redirectAttributes.addFlashAttribute("errMsg", "登录异常，请联系管理员！");
				return "redirect:/admin";
			}
		return "redirect:/admin/index";
	}
	
	@RequestMapping(value = "logout")
	public String logout(HttpSession session) {
		
		session.invalidate();
		
		return "redirect:/admin/login";
	}
	
	@RequestMapping(value = "denied")
	public String denied(HttpSession session) {
		return "/error/403";
	}
}
