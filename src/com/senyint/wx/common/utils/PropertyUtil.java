package com.senyint.wx.common.utils;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * 属性文件共通类
 * 
 * @author 孙志
 * 
 */
public class PropertyUtil {

	private static String DEFAULT_CONFIG_FILE_NAME = "SystemConfig";
	
	private static String RESOURCE_FILE_PATH = "ApplicationResource";
	
	private static Logger logger = Logger.getLogger(PropertyUtil.class);
	
	private PropertyUtil() {
	};

	/**
	 * 取得属性值
	 * 
	 * @param propertyFilePath 属性文件路径
	 * @param key key值
	 * @param arguments 数组参数
	 * @return
	 */
	public static String getVal(String propertyFilePath, String key,
			Object[] arguments) {

		ResourceBundle bundle = ResourceBundle.getBundle(propertyFilePath);
		String value = "";
		if (arguments == null || arguments.length == 0) {
			value = bundle.getString(key);
		} else {
			value = MessageFormat.format(bundle.getString(key), arguments);
		}

		return value;
	}

	/**
	 * 取得属性值
	 * 
	 * @param propertyFilePath 属性文件路径
	 * @param key key值
	 * @return
	 */
	public static String getVal(String propertyFilePath, String key) {

		return getVal(propertyFilePath, key, null);
	}
	
	/**
	 * 取得系统配置属性值
	 * 
	 * @param key key值
	 * @return
	 */
	public static String getSysVal(String key) {
		return getVal(DEFAULT_CONFIG_FILE_NAME, key);
	}

	public static String getSysVal(String key,boolean isbase){
		String result = "";
		if(isbase){
			ArgumentUtil au =  new ArgumentUtil();
			result = au.getSysVal(key);
		}
		return result;
	}
	
	
	
	
	/**
	 * 取得属性值(无参数)
	 * 
	 * @param key 属性键名
	 * @param parameter 传入参数
	 * @return
	 */
	public static String getMessage(String key,String argument) {
		return getMessage(key,new Object[]{argument});
	}
	
	/**
	 * 取得属性值(单个参数)
	 * 
	 * @param key 属性键名
	 * @return
	 */
	public static String getMessage(String key) {
		return getMessage(key,(Object[])null);
	}
	
	/**
	 * 取得属性值(多个参数)
	 * 
	 * @param key 属性键名
	 * @param arguments 多个参数
	 * @return
	 */
	public static String getMessage(String key,Object[] arguments) {
		try {
			ResourceBundle bundle = ResourceBundle.getBundle(RESOURCE_FILE_PATH);
			String value = "";
			if (arguments == null || arguments.length == 0) {
				value = bundle.getString(key);
			} else {
				value = MessageFormat.format(bundle.getString(key), arguments);
			}
			return value;
		} catch(Exception e) {
			logger.error("PropertyUtil.getMessage error![key=" + key + "]", e);
			return "";
		}
	}
}
