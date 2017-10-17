package com.senyint.wx.common.web;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作日志注释
 * @author Gary
 * 2014-05-07
 *
 */
//表示该注解用于什么地方
@Target(ElementType.METHOD)   
//表示在什么级别保存该注解信息
@Retention(RetentionPolicy.RUNTIME)   
//表示该注解生成文档时生成
@Documented  
//表示注解继承
@Inherited  
public @interface Operate {
	String desc();  
}
