package com.senyint.wx.mobile.sys;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;

import com.senyint.core.entity.component.AppType;
import com.senyint.wx.common.web.Constants;

/**
 * 系统初始设置
 * 
 * @author sunzhi
 *
 */
public class WebConfiguration implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		servletContext.setAttribute(Constants.APP_TYPE_KEY, AppType.WEIXIN);
	}

}
