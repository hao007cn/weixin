/**
 * UserAction.java
 * com.senyint.wx.admin.action
 * Function: TODO ADD FUNCTION
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2014年11月28日 		gjp
 *
 * Copyright (c) 2014, senyint All Rights Reserved.
 */
/**
 * @File: UserAction.java
 * @Package com.senyint.wx.admin.action
 * @Description: TODO Describe the File
 *
 * @Company: senyint (Dalian) Co., Ltd
 * @author   gjp
 * @date     2014年11月28日
 * @version  1.0
 */

package com.senyint.wx.admin.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.senyint.common.util.DateUtils;
import com.senyint.common.util.MD5Util;
import com.senyint.common.web.ajax.GridParam;
import com.senyint.common.web.ajax.GridResult;
import com.senyint.wx.admin.dao.UserDao;
import com.senyint.wx.admin.entity.User;
import com.senyint.wx.common.utils.ArgumentUtil;
import com.senyint.wx.common.web.Operate;

/**
 * ClassName:UserAction
 * 
 * @author gjp
 * @version
 * @since Ver 1.1
 * @Date 2014年11月28日
 * 
 * @see
 */
@Controller
@RequestMapping(value = "/user")
public class UserAction extends SupportAction {
	@Autowired
	private UserDao userDao;

	/**
	 * 後臺用戶管理頁面跳轉
	 * 
	 * @author gjp senyint (Dalian) Co., Ltd.
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@Operate(desc = "后台用户管理页面")
	@RequestMapping(method = RequestMethod.GET)
	public String index(ModelMap model, HttpServletRequest request,
			HttpSession session) {

		return "adminUser";
	}

	/**
	 * 加載表格數據
	 * 
	 * @author gjp senyint (Dalian) Co., Ltd.
	 * 
	 * @param gridParam
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "loadpage", method = RequestMethod.POST)
	public GridResult loadPage(GridParam gridParam, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		Conjunction con = Restrictions.and();
		if (StringUtils.isNotBlank(request.getParameter("tempid"))) {
			con.add(Restrictions.eq("poid",
					Integer.parseInt(request.getParameter("tempid"))));
		}
		if (StringUtils.isNotBlank(request.getParameter("startDate"))) {
			String startDate = request.getParameter("startDate") + " 00:00:00";
			con.add(Restrictions.ge("createDate",
					DateUtils.strToDate(startDate, "yyyy-MM-dd HH:mm:ss")));
		}
		if (StringUtils.isNotBlank(request.getParameter("endDate"))) {
			String endDate = request.getParameter("endDate") + " 23:59:59";
			con.add(Restrictions.le("createDate",
					DateUtils.strToDate(endDate, "yyyy-MM-dd HH:mm:ss")));
		}
		int records = userDao.fetchCountByCriteria(false, con);
		List<User> list = userDao.findByCriteria(gridParam.getStartRow(),
				gridParam.getRows(), "createDate desc", false, con);
		return gridResult(gridParam, records, list);
	}

	/**
	 * 保存方法
	 * 
	 * @author gjp senyint (Dalian) Co., Ltd.
	 * 
	 * @param user
	 * @param request
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@Operate(desc = "保存后台用户数据")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(User user, HttpServletRequest request,
			HttpSession session, RedirectAttributes redirectAttributes) {
		if (user.getPoid() == null) {
			user.setCreateDate(ArgumentUtil.getSysDate());
			user.setCreateUserId(getLoginUserInfo().getPoid().toString());
			user.setCreateUserName(getLoginUserInfo().getUsername());
			user.setPassword(MD5Util.md5Encrypt(user.getPassword()));
			// 新增操作
			userDao.makePersistent(user);
		} else {
			// 修改操作
			User temp = userDao.findByPoid(user.getPoid());
			temp.setEnabled(user.getEnabled());
			temp.setName(user.getName());
			if (!temp.getPassword().equals(user.getPassword())) {
				temp.setPassword(MD5Util.md5Encrypt(user.getPassword()));
			}
			temp.setSex(user.getSex());
			temp.setUsername(user.getUsername());
			temp.setModifyDate(ArgumentUtil.getSysDate());
			temp.setModifyUserName(
					getLoginUserInfo().getUsername());
			userDao.makePersistent(temp);
		}
		redirectAttributes.addFlashAttribute("message", "保存成功");
		return "redirect:/admin/user";
	}

	/**
	 * 根據id獲取數據
	 * 
	 * @author gjp senyint (Dalian) Co., Ltd.
	 * 
	 * @param poid
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getdata", method = RequestMethod.POST)
	public User getData(Integer poid, HttpServletRequest request,
			HttpSession session) {
		User user = userDao.findByPoid(poid);
		return user;
	}

	/**
	 * 刪除方法
	 * 
	 * @author gjp senyint (Dalian) Co., Ltd.
	 * 
	 * @param poid
	 * @param request
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@Operate(desc = "删除后台用户数据")
	@RequestMapping(value = "del", method = RequestMethod.POST)
	public String delete(Integer poid, HttpServletRequest request,
			HttpSession session, RedirectAttributes redirectAttributes) {
		User user = userDao.findByPoid(poid);
		userDao.remove(user);
		redirectAttributes.addFlashAttribute("message", "删除成功");
		return "redirect:/admin/user";
	}

	/**
	 * 後臺用戶自動補全方法
	 * 
	 * @author gjp senyint (Dalian) Co., Ltd.
	 * 
	 * @param q
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "autouser", method = RequestMethod.GET)
	public List<User> getAutoUser(String q, HttpServletRequest request,
			HttpSession session) {
		if (StringUtils.isNotBlank(q)) {
			try {
				Conjunction con = Restrictions.and();
				con.add(Restrictions.eq("deleteFlg", false));
				con.add(Restrictions.like("name",new String(q.getBytes("iso-8859-1"),"UTF-8"),MatchMode.ANYWHERE));
				List<User> list = userDao.findByCriteria(0,20,"",false,con);
				return list;
			} catch (UnsupportedEncodingException e) {

			}
		}

		return null;
	}

	/**
	 * 密碼校驗
	 * 
	 * @author gjp senyint (Dalian) Co., Ltd.
	 * 
	 * @param fieldId
	 * @param fieldValue
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getpassword", method = RequestMethod.GET)
	public List<Object> getPassWord(String fieldId, String fieldValue,
			HttpServletRequest request, HttpSession session) {
		List<Object> list = new ArrayList<Object>();
		if (StringUtils.isNotBlank(fieldValue)) {
			if (MD5Util.md5Encrypt(fieldValue).equals(
					getLoginUserInfo().getPassword())) {
				list.add(fieldId);
				list.add(true);
			} else {
				list.add(fieldId);
				list.add(false);
			}
		}
		return list;
	}
}
