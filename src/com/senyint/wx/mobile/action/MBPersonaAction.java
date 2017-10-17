/**
 * PersonaAction.java
 * com.senyint.wx.mobile.action
 * Function: TODO ADD FUNCTION
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2014年12月16日 		gjp
 *
 * Copyright (c) 2014, senyint All Rights Reserved.
 */
/**
 * @File: PersonaAction.java
 * @Package com.senyint.wx.mobile.action
 * @Description: TODO Describe the File
 *
 * @Company: senyint (Dalian) Co., Ltd
 * @author   gjp
 * @date     2014年12月16日
 * @version  1.0
 */

package com.senyint.wx.mobile.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.senyint.common.util.DateUtils;
import com.senyint.core.dao.exception.AppRuntimeException;
import com.senyint.wx.common.dao.HisAccessDao;
import com.senyint.wx.common.dao.UserModifyLogDao;
import com.senyint.wx.common.entity.ForegroundUser;
import com.senyint.wx.common.entity.ForegroundUserInHis;
import com.senyint.wx.common.entity.UserModifyLog;
import com.senyint.wx.common.entity.UserRelevance;
import com.senyint.wx.common.utils.ArgumentUtil;
import com.senyint.wx.common.utils.PropertyUtil;
import com.senyint.wx.common.web.Constants;
import com.senyint.wx.mobile.service.ForegroundRegistrationService;

/**
 * ClassName:PersonaAction
 * 
 * @author gjp
 * @version
 * @since Ver 1.1
 * @Date 2014年12月16日
 * 
 * @see
 */
@Controller
@RequestMapping(value = "/persona")
public class MBPersonaAction extends MBSupportAction {
	@Autowired
	private HisAccessDao hisAccessDao;
	@Autowired
	private UserModifyLogDao userModifyLogDao;
	@Autowired
	private ForegroundRegistrationService foregroundRegistrationService;
	/**
	 * 个人中心 The method <code> index </code> .
	 * 
	 * @author gjp senyint (Dalian) Co., Ltd.
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String index(ModelMap model, HttpServletRequest request,
			HttpSession session) {
		ForegroundUser foregroundUser = (ForegroundUser) session
				.getAttribute(Constants.SESSION_TOP_USER_INFO_KEY);
		model.addAttribute("foregroundUser", foregroundUser);
		return "persona";
	}

	/**
	 * 个人信息页面 The method <code> personaWith </code> .
	 * 
	 * @author gjp senyint (Dalian) Co., Ltd.
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "personawith", method = RequestMethod.GET)
	public String personaWith(ModelMap model, HttpServletRequest request,
			HttpSession session) throws Exception {

		ForegroundUser foregroundUser = (ForegroundUser) session
				.getAttribute(Constants.SESSION_TOP_USER_INFO_KEY);
		ForegroundUserInHis fuih= hisAccessDao.queryForegroundUserInHisById(foregroundUser.getPatietId().toString());
		ForegroundUser fu= foregroundRegistrationService.foregroundUserInHis2ForegroundUser(fuih);
		model.addAttribute("foregroundUser",fu);
		return "personaWith";
	}

	/**
	 * 个人信息修改页面
	 * 
	 * @author gjp senyint (Dalian) Co., Ltd.
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "updatepersona", method = RequestMethod.GET)
	public String updatePersona(ModelMap model, HttpServletRequest request,
			HttpSession session) throws Exception {
		ForegroundUser foregroundUser = (ForegroundUser) session
				.getAttribute(Constants.SESSION_TOP_USER_INFO_KEY);
		ForegroundUserInHis fuih= hisAccessDao.queryForegroundUserInHisById(foregroundUser.getPatietId().toString());
		ForegroundUser fu= foregroundRegistrationService.foregroundUserInHis2ForegroundUser(fuih);
		model.addAttribute("foregroundUser",fu);
		return "updatePersona";
	}

	/**
	 * 后台保存个人资料 The method <code> save </code> . TODO(Describe the methods )
	 * 
	 * @author gjp senyint (Dalian) Co., Ltd.
	 * 
	 * @param foregroundUser
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(ForegroundUser fUser, ModelMap model,
			HttpServletRequest request, HttpSession session,
			RedirectAttributes redirectAttributes) {
		// 判断一个月内的修改次数
		if (!checkModifyTimes()) {
			redirectAttributes.addFlashAttribute("errMsg",
					"本月修改就诊人信息次数过多，该操作已被取消！");
			return "redirect:/persona/personawith";
		} else {
			UserModifyLog userModifyLog = new UserModifyLog();
			userModifyLog.setModifyTime(ArgumentUtil.getSysDate());
			userModifyLog.setOptUserId(getLoginUserId());
			userModifyLog.setUserId(getLoginUserId());
			userModifyLog.setBeforeValue(fUser.getMobile());
			userModifyLog.setAfterValue(fUser.getMobile());
			this.userModifyLogDao.makePersistent(userModifyLog);
		}
		try {
			ForegroundUserInHis inhis = hisAccessDao
					.queryForegroundUserInHisById(fUser.getPatietId().toString());
			inhis.setHouseTel(fUser.getMobile());
			//获取用户的openid
			ForegroundUser foregroundUser = (ForegroundUser) session.getAttribute(Constants.SESSION_TOP_USER_INFO_KEY);
			String openid = foregroundUser.getOpenid();
			//获取用户和his关系表的poid 设置为更新的验证码
			UserRelevance relevance = foregroundRegistrationService.getuserUserRelationByOpenid(openid);
			inhis.setValidateCode(relevance.getPoid().toString());
			hisAccessDao.update2His(inhis);
			ForegroundUser fu= foregroundRegistrationService.foregroundUserInHis2ForegroundUser(inhis);
			//session.setAttribute(Constants.SESSION_TOP_USER_INFO_KEY, fu);
			foregroundRegistrationService.setUserInfo2Session(openid, session);
			
		} catch(AppRuntimeException e) {
			
			model.addAttribute("errMsg", e.getExplanation());
		
		} catch(Exception e) {
			
			model.addAttribute("errMsg", "数据异常，请稍后重试！");
		}
		return "redirect:/persona/personawith";
	}

	private boolean checkModifyTimes() {
		// 获取最近一个月修改次数
		Conjunction con = Restrictions.and(Restrictions.eq("optUserId",
				getLoginUserId()));
		con.add(Restrictions.ge("modifyTime",
				DateUtils.nDaysAfterDate(ArgumentUtil.getSysDate(), -30)));

		int modifiedCount = this.userModifyLogDao.fetchCountByCriteria(false,
				con);
		if (modifiedCount >= Integer.parseInt(PropertyUtil
				.getSysVal(Constants.USERS_PATIENT_MODIFY_TIMES_MAX,true))) {
			return false;
		}
		
//		if(modifiedCount >=Integer.parseInt(argumentService.getSysVal(Constants.USERS_PATIENT_COUNT_MAX))){
//			return false;
//		}
		return true;
	}

	/** 
	 *  用户注册 The method <code> register </code> . 
	 * 
	 * @author gjp senyint (Dalian) Co., Ltd.
	 * 
	 * @param foregroundUser
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String register(ForegroundUser fUser,ModelMap model,
			HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		try {
			ForegroundUserInHis inhis = hisAccessDao
					.queryForegroundUserInHisById(fUser.getPatietId()
							.toString());
			//电话
			inhis.setHouseTel(fUser.getMobile());

			String openid = request.getParameter("openid");
		
			//判断关系表中是否有openid
			UserRelevance userRelevance = foregroundRegistrationService
					.getuserUserRelationByOpenid(openid);
			if (userRelevance != null) {
				//获取openId 和 patiendid
				inhis.setValidateCode(userRelevance.getPoid().toString());
				hisAccessDao.update2His(inhis);
				ForegroundUser fu = foregroundRegistrationService
						.foregroundUserInHis2ForegroundUser(inhis);
				//更新session
				foregroundRegistrationService.setUserInfo2Session(openid, request.getSession());
			} else {
				//向用户和his关系表中插入一条数据
				//保存的新对象
				UserRelevance relevance =  foregroundRegistrationService.addNewUserRelevan(openid, fUser.getPatietId().toString());
				//设置验证码
				inhis.setValidateCode(relevance.getPoid().toString());
				hisAccessDao.update2His(inhis);
				foregroundRegistrationService.setUserInfo2Session(openid, request.getSession());
			}

		} catch (AppRuntimeException e) {
			e.printStackTrace();
			model.addAttribute("errMsg", e.getExplanation());

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errMsg", "数据异常，请稍后重试！");
		}
		return "redirect:/persona/personawith";
	}
	
	/**
	 * 微信登录完善主用户信息 The method <code> saveForegroundUser </code> .
	 * 
	 * @author gjp senyint (Dalian) Co., Ltd.
	 * 
	 * @param foregroundUser
	 * @param request
	 * @param session
	 * @param redirectAttributes
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "savenew", method = RequestMethod.POST)
	public String saveNew(Model model,ForegroundUser foregroundUser,
			HttpServletRequest request, HttpSession session,
			RedirectAttributes redirectAttributes) throws Exception {

		// 获得patientId
		Long patientId = hisAccessDao.genericId();
		// 获得openId
		String openId = request.getParameter("openid");
		
		try {
			// 完善信息的时候 向his插入一条数据
			foregroundRegistrationService
					.addNewUser2His(foregroundUser.getCardid(),
							foregroundUser.getMobile(), foregroundUser.getOpenid(),
							foregroundUser.getName(), patientId);
			// 向USER_RELEVANCE 插入一条数据
			foregroundRegistrationService.addNewUserRelevan(openId,
					patientId.toString());
			foregroundRegistrationService.setUserInfo2Session(openId, session);
			String rd = request.getParameter("rd");
			
			if (StringUtils.isNotEmpty(rd)) {
				return "redirect:/" + rd;
			}
			return "redirect:/index";
		} catch(AppRuntimeException e) {
			model.addAttribute("errMsg", e.getExplanation());
			model.addAttribute("foregroundUser", foregroundUser);
			model.addAttribute("openid", openId);
			return "registration";
		
		} catch(Exception e) {
			model.addAttribute("errMsg", "数据异常，请稍后重试！");
			model.addAttribute("foregroundUser", foregroundUser);
			model.addAttribute("openid", openId);
			return "registration";
		}
	}
	
	
	
	
	
}
