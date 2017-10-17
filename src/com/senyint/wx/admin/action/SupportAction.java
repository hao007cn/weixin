package com.senyint.wx.admin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.senyint.common.web.ajax.AjaxResult;
import com.senyint.common.web.ajax.GridParam;
import com.senyint.common.web.ajax.GridResult;
import com.senyint.wx.admin.bean.UserInfo;
import com.senyint.wx.common.web.Constants;

public class SupportAction {
	
	public UserInfo getLoginUserInfo() {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes)ra).getRequest();
		
		HttpSession session = request.getSession();
		return (UserInfo) session.getAttribute(Constants.SESSION_USER_INFO_KEY);
	}
	
	public String getLoginUsername() {
		return getLoginUserInfo().getUsername();
	}
	
	public Integer getLoginUserId() {
		return getLoginUserInfo().getPoid();
	}
	
	public String getLoginUserRealName() {
		return getLoginUserInfo().getName();
	}
	
	public void addMessage() {
		
	}
	
	public AjaxResult ajaxResult(Integer success, String message) {
		return new AjaxResult(success, message);
	}
	
	public AjaxResult ajaxResult(Integer success, String message, Object data) {
		return new AjaxResult(success, message, data);
	}
	
	public AjaxResult ajaxSuccess() {
		return new AjaxResult(AjaxResult.EXECUTE_SUCCESS, null);
	}
	
	public AjaxResult ajaxSuccess(String message) {
		return new AjaxResult(AjaxResult.EXECUTE_SUCCESS, message);
	}
	
	public AjaxResult ajaxSuccess(String message, Object data) {
		return new AjaxResult(AjaxResult.EXECUTE_SUCCESS, message, data);
	}
	
	public AjaxResult ajaxFail() {
		return new AjaxResult(AjaxResult.EXECUTE_FAILURE, "");
	}
	
	public AjaxResult ajaxFail(String message) {
		return new AjaxResult(AjaxResult.EXECUTE_FAILURE, message);
	}
	
	public AjaxResult ajaxFail(String message, Object data) {
		return new AjaxResult(AjaxResult.EXECUTE_FAILURE, message, data);
	}
	
	public GridResult gridResult(Integer page, Integer pageSize, Integer records, Object rows) {
		
		return new GridResult(page, pageSize, records, rows);
	}
	
	public GridResult gridResult(GridParam gridParam, Integer records, Object rows) {
		
		return new GridResult(gridParam.getPage(), gridParam.getRows(), records, rows);
	}
}
