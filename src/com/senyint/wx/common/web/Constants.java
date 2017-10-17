/**
 * Constants.java
 * com.senyint.common
 * Function: 常量
 *
 *   ver     date      	    author
 * ──────────────────────────────────
 *           2013-6-14        sunzhi
 *
 * Copyright (c) 2013, Senyint All Rights Reserved.
 */

package com.senyint.wx.common.web;
/**
 * ClassName:Constants
 * Function: 常量
 * Reason:	 常量
 *
 * @author   sunzhi
 * @version  
 * @since    Ver 1.1
 * @Date     2013-6-14
 *
 * @see 
 */
public class Constants {
	public static final String SESSION_USER_INFO_KEY = "loginuserinfo";
	
	public static final String SESSION_TOP_USER_INFO_KEY = "logintopuserinfo";
	
	public static final String SPRING_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";
	
	public static final String ADMIN_NAV_SESSION = "admin_nav_session";
	
	public static final String TMP_DIR_ROOT_KEY = "admin.upload.tmp.dir.root";
	
	public static final String DEFAULT_TMP_DIR_ROOT = "tmp";
	
	//按钮是否显示
	public static final String  SWITCH_SHOW = "1";
	
	// 后台管理用户图片
	public static final String ADMIN_PIC_DIR_KEY = "admin.user.pic.dir";
	public static final String ADMIN_PIC_DEFAULT = "admin.user.pic.default";
	// 前台用户图片
	public static final String USER_PIC_DIR_KEY = "front.user.pic.dir";
	public static final String USER_PIC_DEFAULT = "front.user.pic.default";
	// 医生照片主路径key
	public static final String DOCTOR_PIC_DIR_KEY = "doctor.pic.dir";
	public static final String DOCTOR_PIC_DEFAULT = "doctor.pic.default";
	// 科室类别图标
	public static final String DEPT_PIC_DIR_KEY = "dept.type.img.dir";
	public static final String DEPT_PIC_DEFAULT = "dept.type.img.default";
	// 医生照片文件各前缀
	public static final String DOCTOR_PIC_PREFIX = "doctor_";
	public static final String ADMIN_PIC_PREFIX = "admin_";
	public static final String USER_PIC_PREFIX = "user_";
	
	public static final String FILE_TYPE_DOCTOR = "doctor";
	public static final String FILE_TYPE_ADMIN = "admin";
	public static final String FILE_TYPE_USER = "user";
	public static final String FILE_TYPE_DEPT = "dept";
	public static final String PIC_DEFAULT_KEY = "pic.default";
	public static final String AS_LIS_KEY="lis";
	public static final String AS_PACS_KEY="pacs";
	public static final String APPID_KEY = "wx.appid";
	public static final String SECRET_KEY = "wx.secret";
	
	public static final String APP_TYPE_KEY = "wx.app.type";
	
	// 用户账号被封锁的爽约次数
	public static final String USERS_LOCKED_AFTER_MISS_TIMES_KEY = "users.locked.after.miss.times";
	
	// 用户最大常用就诊人数
	public static final String USERS_PATIENT_COUNT_MAX = "users.patient.count.max";
	
	// 用户每月可以添加、修改就诊人次数
	public static final String USERS_PATIENT_MODIFY_TIMES_MAX = "users.patient.modify.times.max";

	// 医疗付款方式
	public static final String PAY_TYPE = "05";
	//
	public static final String  DEFAULT_CHANNEL = "微信";
	// 图片服务器原ip
	public static final String IMAGE_SERVER_IP_OLD = "image.server.ip.old";
	// 图片服务器新ip
	public static final String IMAGE_SERVER_IP_NEW = "image.server.ip.new";
	//文章默认类型父标记级别
	public static final Integer PARENT_FLAG_DEFAUT = 1 ;
	
	
	
}
