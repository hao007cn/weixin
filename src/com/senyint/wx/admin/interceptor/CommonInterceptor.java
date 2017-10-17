package com.senyint.wx.admin.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.senyint.wx.common.web.Constants;

public class CommonInterceptor implements  HandlerInterceptor {
	private Logger log = Logger.getLogger(CommonInterceptor.class);
	
	/**
	 * 在业务处理器处理请求之前被调用 
	 * 如果返回false 
	 *   从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
	 * 
	 * 如果返回true
	 *   执行下一个拦截器,直到所有的拦截器都执行完毕
	 *   再执行被拦截的Controller
	 *   然后进入拦截器链,
	 *   从最后一个拦截器往回执行所有的postHandle()
	 *   接着再从最后一个拦截器往回执行所有的afterCompletion()
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		String[] noFilters = new String[] { "login", "logout" , "file"};
		
		String uri = request.getRequestURI();
		boolean beFilter = true;
		
		for (String s : noFilters) {
			if (uri.indexOf(s) != -1) {
				beFilter = false;
				break;
			}
		}
		
		if (beFilter) {
			Object userObj = request.getSession().getAttribute(
					Constants.SESSION_USER_INFO_KEY);
			if (userObj == null) {
				request.getRequestDispatcher("/admin/login").forward(request, response);
				return false;
			}
		}
		
		if (request.getSession().getAttribute("message") != null && ((String) request.getSession().getAttribute("message")) != "") {
			request.setAttribute("message", request.getSession().getAttribute("message"));
			request.getSession().removeAttribute("message");
		}
		
		return true;
	}
	
	/**
	 * 在业务处理器处理请求执行完成后,生成视图之前执行的动作 
	 */
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		
	}

	/**
	 * 在DispatcherServlet完全处理完请求后被调用
	 *   当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
	 */
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}

}
