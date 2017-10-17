package com.senyint.wx.mobile.action;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.senyint.common.util.IdcardInfoExtractor;
import com.senyint.common.web.ajax.AjaxResult;
import com.senyint.common.weixin.popular.api.SnsAPI;
import com.senyint.common.weixin.popular.bean.SnsToken;
import com.senyint.wx.common.dao.HisAccessDao;
import com.senyint.wx.common.dao.UserRelevanceDao;
import com.senyint.wx.common.entity.ForegroundUser;
import com.senyint.wx.common.entity.ForegroundUserInHis;
import com.senyint.wx.common.entity.UserRelevance;
import com.senyint.wx.common.utils.PropertyUtil;
import com.senyint.wx.common.web.Constants;
import com.senyint.wx.common.web.Operate;
import com.senyint.wx.mobile.service.ForegroundRegistrationService;

@Controller
@RequestMapping(value = "/login")
public class MBLoginAction {
	@Autowired
	private HisAccessDao hisAccessDao;
	@Autowired
	private ForegroundRegistrationService foregroundRegistrationService;
	@Autowired
	private UserRelevanceDao userRelevanceDao;
	
	@Operate(desc = "用户登录")
	@RequestMapping(method = RequestMethod.GET)
	public String index(ModelMap model,HttpServletRequest request, HttpSession session) throws Exception {
//		if (1==1) { //开发阶段代码，生产部署时去掉 TODO
//			Conjunction con = Restrictions.and();
//			//取得openid后去数据库检索是否存在用户
//			con.add(Restrictions.eq("openid", "oOuEOs3qq5B4-tDu57wOhZ59UP-o"));
//			List<ForegroundUser> list= foregroundUserDao.findByCriteria(false, con);
//			if(list.get(0).getLocked())
//			{
//				return "locked";
//			}
//			session.setAttribute(Constants.SESSION_TOP_USER_INFO_KEY, list.get(0));
//			return "redirect:/index";
//		}
		String code= request.getParameter("code");
		String rd = request.getParameter("rd");
		
//		if(1==1){
//			model.addAttribute("openid", "oOuEOs3qq5B4-tDu57wOhZ59UP-o0002015020201-test01");	
//			model.addAttribute("rd", rd);
//			return "registrationCardId";
//		}
		
		if(1==1){
			model.addAttribute("rd", rd);
			model.addAttribute("openid", "oOuEOs3qq5B4-tDu57wOhZ59UP-o0002015020201-test01");
			foregroundRegistrationService.setUserInfo2Session("oOuEOs3qq5B4-tDu57wOhZ59UP-o0002015020201-test01", session);
			if(StringUtils.isNotEmpty(rd))
			{
				return "redirect:/"+rd;
			}else {
				return "redirect:/index";
			}
		}
		
//		if(1==1){
//		model.addAttribute("openid", "oOuEOs3qq5B4-tDu57wOhZ59UP-onwes");	
//		model.addAttribute("rd", rd);
//		return "registration";
//	}
		
		//获取微信的code后
		if(StringUtils.isNotEmpty(code)) {
			SnsAPI sApi =new SnsAPI();
			//换取Token
			SnsToken stk = sApi.oauth2AccessToken(
					PropertyUtil.getSysVal(Constants.APPID_KEY,true),
					PropertyUtil.getSysVal(Constants.SECRET_KEY,true), code);
			if(StringUtils.isNotEmpty(stk.getOpenid())) {
				model.addAttribute("openid",stk.getOpenid());
			//返回用户注册	
			} else {
				//用户第一次登陆
				return "redirect:/index";
			}
			Conjunction con = Restrictions.and();
			//取得openid后去数据库检索是否存在用户
			con.add(Restrictions.eq("openId",stk.getOpenid()));
			List<UserRelevance> list= userRelevanceDao.findByCriteria(false, con);
			
			if(list.size() > 0) {
				foregroundRegistrationService.setUserInfo2Session(stk.getOpenid(), session);
				if(StringUtils.isNotEmpty(rd)) {
					return "redirect:/"+rd;
				} else {
					return "redirect:/index";
				}
			} else {
				//用户第一次登陆
				model.addAttribute("openid", stk.getOpenid());	
				model.addAttribute("rd", rd);
				return "registrationCardId";
			}
		} 
		
		return "redirect:/index";
	}
	
	/**
	 * 校验姓名和身份证在his中是否存在
	 * 存在允许注册 
	 * 不存在不允许注册
	 * @param request
	 * @param model
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@Operate(desc = "用户注册姓名和身份证验证")
	@RequestMapping(value = "/registValidate",method = RequestMethod.POST)
	public String registValidate(HttpServletRequest request,Model model,HttpSession session) throws Exception{
		//身份证
		String cardId = request.getParameter("cardid").trim().toUpperCase();
		//姓名
		String userName = request.getParameter("userName");
		String openid = request.getParameter("openid");
		//当前游览页
		String rd =  request.getParameter("rd");
		boolean flag = hisAccessDao.isHasCardId(cardId, userName);
		//true 身份证在his中有， false 身份证在his中不存在
		if(flag){
			//获取patientId
			ForegroundUserInHis inHis = foregroundRegistrationService.getForegroundUserInHis(cardId, userName);
			Long patientId = inHis.getPatientId();
			//向用户和his关系表添加一条数据
			//foregroundRegistrationService.addNewUserRelevan(openid,String.valueOf(patientId));
			//把前台用户 信息放入session
			//foregroundRegistrationService.setUserInfo2Session(openid, session);
			//直接跳转到当前游览页面
			//			if(StringUtils.isNotEmpty(rd))
			//			{
			//				return "redirect:/"+rd;
			//			}else {
			//				return "redirect:/index";
			//			}
			ForegroundUser fu = foregroundRegistrationService.foregroundUserInHis2ForegroundUser(inHis);
			model.addAttribute("foregroundUser",fu);
			model.addAttribute("openid",openid);
			return "register";
			
		}else{
			/***his中无信息注册
			ForegroundUser foregroundUser = new ForegroundUser();
			foregroundUser.setName(userName);
			foregroundUser.setCardid(cardId);
			model.addAttribute("foregroundUser", foregroundUser);
			model.addAttribute("openid", openid);
			return "registration";****/
			//返回注册信息提醒页
			return "illegalityRegisterLocked";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/isValidatedAllIdcard", method = RequestMethod.POST)
	public AjaxResult  isValidatedAllIdcard(Model model,HttpServletRequest request){
		String cardId = request.getParameter("cardId").trim().toUpperCase();
		boolean flag = IdcardInfoExtractor.isValidatedAllIdcard(cardId);
		if(flag){
			return new AjaxResult(AjaxResult.EXECUTE_SUCCESS, null);
		}else{
			return new AjaxResult(AjaxResult.EXECUTE_FAILURE, "请输入合法身份证号");
		}
	}
	
	
} 
