package com.senyint.wx.admin.web;

import java.io.Serializable;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.senyint.wx.admin.bean.UserInfo;
import com.senyint.wx.common.dao.OperateLogDao;
import com.senyint.wx.common.entity.OperateLog;
import com.senyint.wx.common.utils.ArgumentUtil;
import com.senyint.wx.common.web.Constants;
import com.senyint.wx.common.web.Operate;

@Component
@Aspect
public class OperateLogAspect implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private OperateLogDao operateLogDao;
	
	
	/* @Around("execution(* org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter.handle(..))")  
	@Pointcut("execution(* com.senyint.crm.controller.*.*.*(..))")
	@Pointcut("execution(* com.senyint.crm.controller.*.*.*(..)) && @annotation(operate)")
	public void controllerPointcutter() {}*/
	
	@Before("execution(* com.senyint.wx.*.action.*.*(..)) && @annotation(operate)")
	public void writeLogBeforePoint(JoinPoint point,Operate operate)  throws Throwable   {
//		String methodName = point.getSignature().getName();//获取目标方法签名
		String className = point.getTarget().getClass().toString();// 获取目标类名
//		Class<? extends Object> target = point.getTarget().getClass();
//		Object[] args = point.getArgs();
//		Class[] parameterTypes = new Class[args.length];
//		for (int i = 0; i < args.length; i++) {
//			parameterTypes[i] = args[i].getClass();
//		}
//		Method method = target.getMethod(methodName,parameterTypes);
//		boolean hasAnnotation = method.isAnnotationPresent(com.senyint.web.annotation.OperateLog.class);
//		if (hasAnnotation) {
//			com.senyint.web.annotation.OperateLog annotation = method.getAnnotation(com.senyint.web.annotation.OperateLog.class);
//			String methodDesc = annotation.desc();
			try {
//				this.operateLogDao.clear();
				UserInfo  currentUserInfo = (UserInfo) getAttribute(Constants.SESSION_USER_INFO_KEY);
				if (currentUserInfo != null) {
					//记录登入信息;
					Date currDate = ArgumentUtil.getSysDate();
					OperateLog log = new OperateLog();
					log.setUser_id(currentUserInfo.getUsername());
					log.setUser_name(currentUserInfo.getName());
					log.setIp(getIpAddr(getRequest()));
					log.setOperate_time(currDate);
					log.setOperate(operate.desc());
					log.setDescription("成功");
					log.setUrl(getRequest().getRequestURI());
					operateLogDao.makePersistent(log);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	/**		
	* The method <code> getIpAddr </code> .		 		
	* 获得IP地址
	* 		
	* @author  zhz senyint (Dalian) Co., Ltd.		
	* 
	* @param request
	* @return 	
	*/
	private String getIpAddr(HttpServletRequest request) { 
	    String ip = request.getHeader("x-forwarded-for"); 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getHeader("Proxy-Client-IP"); 
	    } 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getHeader("WL-Proxy-Client-IP"); 
	    } 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getRemoteAddr(); 
	    } 
	    return ip; 
	}   
	
	
	
	private HttpServletRequest getRequest(){
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest(); 
	}
	
	public Serializable getAttribute(String name) {
		HttpSession session =  getRequest().getSession(false);
		if (session != null) {
			return (Serializable) session.getAttribute(name);
		} else {
			return null;
		}
	}
	
}
