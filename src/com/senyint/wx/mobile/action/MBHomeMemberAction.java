/**
 * HomeMemberAction.java
 * com.senyint.wx.mobile.action
 * Function: TODO ADD FUNCTION
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2014年12月10日 		gjp
 *
 * Copyright (c) 2014, senyint All Rights Reserved.
 */
/**
 * @File: HomeMemberAction.java
 * @Package com.senyint.wx.mobile.action
 * @Description: TODO Describe the File
 *
 * @Company: senyint (Dalian) Co., Ltd
 * @author   gjp
 * @date     2014年12月10日
 * @version  1.0
 */

package com.senyint.wx.mobile.action;

import java.util.Date;
import java.util.List;

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
import com.senyint.wx.common.dao.UserRelationDao;
import com.senyint.wx.common.entity.ForegroundUser;
import com.senyint.wx.common.entity.ForegroundUserInHis;
import com.senyint.wx.common.entity.UserModifyLog;
import com.senyint.wx.common.entity.UserRelation;
import com.senyint.wx.common.entity.UserRelevance;
import com.senyint.wx.common.utils.ArgumentUtil;
import com.senyint.wx.common.utils.PropertyUtil;
import com.senyint.wx.common.web.Constants;
import com.senyint.wx.mobile.service.ForegroundRegistrationService;

/**
 * ClassName:HomeMemberAction
 * 
 * @author gjp
 * @version
 * @since Ver 1.1
 * @Date 2014年12月10日
 * 
 * @see
 */
@Controller
@RequestMapping(value = "/homemember")
public class MBHomeMemberAction extends MBSupportAction {

	@Autowired
	private HisAccessDao hisAccessDao;

	@Autowired
	private UserModifyLogDao userModifyLogDao;
	
	@Autowired
	private ForegroundRegistrationService foregroundRegistrationService;

	@Autowired
	private UserRelationDao userRelationDao;

	/**
	 * 获取子用户列表 The method <code> index </code> . TODO(Describe the methods )
	 * 
	 * @author gjp senyint (Dalian) Co., Ltd.
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String index(ModelMap model, HttpServletRequest request,
			HttpSession session) throws Exception {
		ForegroundUser fu = (ForegroundUser) session
				.getAttribute(Constants.SESSION_TOP_USER_INFO_KEY);
		Conjunction con = Restrictions.and();
		con.add(Restrictions.eq("patientId", fu.getPatietId()));
		// 查询出子用户id
		List<UserRelation> list = userRelationDao.findByCriteria(false, con);
		List<ForegroundUser> foregroundUserList = foregroundRegistrationService
				.batchFindForegroundUserById(list);
		model.addAttribute("foregroundUserList", foregroundUserList);
		return "homeMemberList";
	}

	/**
	 * 取得子用户详细信息
	 * 
	 * @param id
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "getsubuser", method = RequestMethod.GET)
	public String getSubUser(String id, ModelMap model,
			HttpServletRequest request, HttpSession session) throws Exception {
		ForegroundUserInHis fuih = hisAccessDao
				.queryForegroundUserInHisById(id);
		ForegroundUser fu = foregroundRegistrationService
				.foregroundUserInHis2ForegroundUser(fuih);
		model.addAttribute("foregroundUser", fu);
		return "subuserwith";
	}

	/**
	 * 新增子用户页面 The method <code> addMember </code> . TODO(Describe the methods )
	 * 
	 * @author gjp senyint (Dalian) Co., Ltd.
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "openadd", method = RequestMethod.GET)
	public String addMember(ModelMap model, HttpServletRequest request,
			HttpSession session) {

		return "addMember";
	}

	@RequestMapping(value = "updatemember", method = RequestMethod.GET)
	public String updateMember(String patiendId, ModelMap model,
			HttpServletRequest request, HttpSession session) throws Exception {

		ForegroundUserInHis fuih = hisAccessDao
				.queryForegroundUserInHisById(patiendId);
		ForegroundUser fu = foregroundRegistrationService
				.foregroundUserInHis2ForegroundUser(fuih);
		model.addAttribute("foregroundUser", fu);
		return "updateMember";
	}

	/**
	 * 保存子用户 The method <code> save </code> .
	 * 
	 * @author gjp senyint (Dalian) Co., Ltd.
	 * 
	 * @param user
	 * @param request
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(Model model,  ForegroundUser user, HttpServletRequest request,
			HttpSession session, RedirectAttributes redirectAttributes) {
		if (user.getPatietId() != null) {
			try {
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
					userModifyLog.setBeforeValue(user.getMobile());
					userModifyLog.setAfterValue(user.getMobile());
					this.userModifyLogDao.makePersistent(userModifyLog);
				}
				// 修改
				ForegroundUserInHis fuih = hisAccessDao
						.queryForegroundUserInHisById(user.getPatietId().toString());
				fuih.setHouseTel(user.getMobile());
				//获取用户的openid
				ForegroundUser foregroundUser = (ForegroundUser) session.getAttribute(Constants.SESSION_TOP_USER_INFO_KEY);
				String openid = foregroundUser.getOpenid();
				//获取用户和his关系表的poid 设置为更新的验证码
				UserRelevance relevance = foregroundRegistrationService.getuserUserRelationByOpenid(openid);
				fuih.setValidateCode(relevance.getPoid().toString());
				hisAccessDao.update2His(fuih);

				//根据自id添加关联添加关联
				String childId= request.getParameter("childId");
				if(!childId.isEmpty()){
					// session获取主用户信息
//					ForegroundUser foregroundUser = (ForegroundUser) session
//							.getAttribute(Constants.SESSION_TOP_USER_INFO_KEY);
					UserRelation userRelation = new UserRelation();
					// 添加主用户id
					userRelation.setPatientId(foregroundUser.getPatietId());
					// 添加自用户id
					userRelation.setPatiendChildId(Long.valueOf(childId));
					// 增加主从关系
					userRelationDao.makePersistent(userRelation);
				}

			} catch(AppRuntimeException e) {
				e.printStackTrace();
				model.addAttribute("errMsg", e.getExplanation());
			
			} catch(Exception e) {
				e.printStackTrace();
				model.addAttribute("errMsg", "添加常用联系人异常，请稍后重试！");
			
			}
			
		} else {
			try {
				String cardId = user.getCardid();
				String name = user.getName();
				//true 身份证在his中有， false 身份证在his中不存在
				//存在允许添加  不存在不允添加
				boolean flag = hisAccessDao.isHasCardId(cardId, name);
				if(flag){
					// 新增
					Long patientId = hisAccessDao.genericId();
					ForegroundUser foregroundUser = (ForegroundUser) session
							.getAttribute(Constants.SESSION_TOP_USER_INFO_KEY);
					foregroundRegistrationService.addNewSubUser2His(
							foregroundUser.getPatietId(), patientId, user.getCardid(),
							user.getMobile(), user.getName());
				}else{
					return "illegalityRegisterLocked";
				}
				
			} catch(AppRuntimeException e) {
				
				model.addAttribute("errMsg", e.getExplanation());
			
			} catch(Exception e) {
				model.addAttribute("errMsg", "添加常用联系人异常，请稍胡后重试！");
			}
		}

		return "redirect:/homemember";
	}

	private boolean checkModifyTimes() {
		// 获取本月修改次数
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
		return true;
	}

	/**
	 * 删除子用户 The method <code> del </code> .
	 * 
	 * @author gjp senyint (Dalian) Co., Ltd.
	 * 
	 * @param patietId
	 * @param request
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "del", method = RequestMethod.POST)
	public String del(String patietId, HttpServletRequest request,
			HttpSession session, RedirectAttributes redirectAttributes) {
		Conjunction con = Restrictions.and();
		con.add(Restrictions.eq("patiendChildId", Long.valueOf(patietId)));
		List<UserRelation> list = userRelationDao.findByCriteria(false, con);
		if (list.size() > 0) {
			userRelationDao.remove(list.get(0));
		}
		return "redirect:/homemember";
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
	@RequestMapping(value = "saveforegrounduser", method = RequestMethod.POST)
	public String saveForegroundUser(Model model,ForegroundUser foregroundUser,
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

	/**
	 * 自用户校验在his中是否存在 存在保存用户关系表，不存在跳转至完善信息页面
	 * 
	 * @param cardid
	 * @param username
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "findhisuser", method = RequestMethod.POST)
	public String findHisUser(ForegroundUser fu, ModelMap model,
			HttpServletRequest request, HttpSession session,
			RedirectAttributes redirectAttributes) throws Exception {
		// 添加常用就诊人次数
		Conjunction con = Restrictions.and(Restrictions.eq("patientId",
				getLoginUserId()));
		int subUserCount = userRelationDao.fetchCountByCriteria(false, con);
		if (subUserCount >= Integer.parseInt(PropertyUtil
				.getSysVal(Constants.USERS_PATIENT_COUNT_MAX,true))) {
			redirectAttributes.addFlashAttribute("errMsg", "常用就诊人最多不能超过"
					+ PropertyUtil
					.getSysVal(Constants.USERS_PATIENT_COUNT_MAX,true) + "个人");
			return "redirect:/homemember";
		}
		// 查询此用户在his中是否存在
		Boolean isHis = hisAccessDao.isHasCardId(fu.getCardid(),
				fu.getName());
		if (isHis) {
			// 查询his用户
			ForegroundUserInHis fuih = foregroundRegistrationService
					.getForegroundUserInHis(fu.getCardid(), fu.getName());
			// session获取主用户信息
			//ForegroundUser foregroundUser = (ForegroundUser) session
			//.getAttribute(Constants.SESSION_TOP_USER_INFO_KEY);
			//UserRelation userRelation = new UserRelation();
			// 添加主用户id
			//userRelation.setPatientId(foregroundUser.getPatietId());
			// 添加自用户id
			//userRelation.setPatiendChildId(fuih.getPatientId());
			// 增加主从关系
			//userRelationDao.makePersistent(userRelation);
			ForegroundUser fuser= foregroundRegistrationService.foregroundUserInHis2ForegroundUser(fuih);
			model.addAttribute("foregroundUser", fuser);
			model.addAttribute("childId",fuih.getPatientId());
			// 跳转至修改手机页面
			return "updateMember";
		} else {
			model.addAttribute("cardid", fu.getCardid());
			model.addAttribute("username", fu.getName());
			return "addNextMember";
		}
	}
}
