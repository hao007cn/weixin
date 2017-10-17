/**
 * AdminWebUtils.java
 * com.senyint.wx.admin.utils
 * Function: 公共方法
 *
 *   ver     date      	    author
 * ──────────────────────────────────
 *           2013-9-27        sunzhi
 *
 * Copyright (c) 2013, Senyint All Rights Reserved.
 */
package com.senyint.wx.admin.utils;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.senyint.wx.common.utils.PropertyUtil;
import com.senyint.wx.common.web.Constants;

/**
 * ClassName: AdminWebUtils
 * Function: 公共方法
 * Reason:	 公共方法
 *
 * @author   sunzhi
 * @version  
 * @since    Ver 1.1
 * @Date     2013-9-27
 *
 * @see 
 */
public class AdminWebUtils {
	
	/**
	 * 获得项目上下文根路径
	 * 
	 * @return
	 */
	public static String getContextRealPath() {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes)ra).getRequest();
		
		HttpSession session = request.getSession();
		
		return session.getServletContext().getRealPath("/");
	}
	
	/**
	 * 获取项目临时路径
	 * 
	 * @return
	 */
	public static String getAppTmpRootDir() {
		String tmpDirRoot = PropertyUtil.getSysVal(Constants.TMP_DIR_ROOT_KEY);
		if (tmpDirRoot == null) {
			tmpDirRoot = Constants.DEFAULT_TMP_DIR_ROOT;
		}
		
		return tmpDirRoot.replaceAll("\\\\", "\\" + File.separator).replaceAll("/", "\\" + File.separator);
	}
	
	/**
	 * 获取会话中临时路径，会话创建时创建，会话结束时销毁
	 * 
	 * @return
	 */
	public static String getSessionTmpDir() {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes)ra).getRequest();
		
		HttpSession session = request.getSession();
		
		return getSessionTmpDir(session);
	}
	
	/**
	 *  获取会话中临时路径，会话创建时创建，会话结束时销毁
	 *  
	 * @param session
	 * @return
	 */
	public static String getSessionTmpDir(HttpSession session) {
		
		String sessionTmpDir = session.getServletContext().getRealPath("/") + getAppTmpRootDir() + File.separator + session.getId();
		File dir = new File(sessionTmpDir);
		if (!dir.exists() || !dir.isDirectory()) {
			dir.mkdirs();
		}
		
		return sessionTmpDir;
	}
	
	/**
	 *  获取会话中临时路径对应的url，该url可直接在页面引用
	 * 
	 * @return
	 */
	public static String getSessionTmpDirUrl() {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes)ra).getRequest();
		
		HttpSession session = request.getSession();
		return (getAppTmpRootDir() + File.separator + session.getId()).replaceAll("\\\\", "/");
	}
}
