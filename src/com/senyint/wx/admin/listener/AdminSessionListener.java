/**
 * AdminSessionListener.java
 * com.senyint.wx.admin.listener
 * Function: 
 *
 *   ver     date      	    author
 * ──────────────────────────────────
 *           2013-7-16        sunzhi
 *
 * Copyright (c) 2013, Senyint All Rights Reserved.
 */
package com.senyint.wx.admin.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.senyint.common.util.FileUtils;
import com.senyint.wx.admin.utils.AdminWebUtils;

/**
 * ClassName: AdminSessionListener
 * Function: 监听session
 * Reason:	 监听session
 *
 * @author   sunzhi
 * @version  
 * @since    Ver 1.1
 * @Date     2013-7-16
 *
 * @see 
 */
public class AdminSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent sessionEvent) {
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		
		FileUtils.deleteFolder(AdminWebUtils.getSessionTmpDir(sessionEvent.getSession()));
	}

}
