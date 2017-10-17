package com.senyint.core.dao.exception;

import org.apache.commons.lang.exception.ExceptionUtils;

/**
 * 
 *
 */
public class ExceptionUtil {

	/**
	 * Check exception所內含的causes是否包含clazz類型的Exception
	 * 
	 * @param exception a Exception to Check with
	 * @param clazz A Exception Class
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean containsException(Exception exception, Class clazz) {
		if (ExceptionUtils.indexOfThrowable(exception, clazz) != -1) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 取出exception所內含causes類型為clazz的Exception
	 * <p>
	 * 先呼叫ExceptionUtil.containsException()傳回true時才呼叫這個method,
	 * 否則找不到時可能會拋出IllegalArgumentException
	 * </p>
	 * 
	 * @param exception
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Throwable fetchExceptionWithClass(Exception exception, Class clazz) {
		int idx = ExceptionUtils.indexOfThrowable(exception, clazz);
		if (idx == -1) {
			throw new IllegalArgumentException("傳入的exception所內含的causes不包含類別:" + clazz);
		}
		Throwable t = exception.getCause();
		while (idx > 1) {
			t = exception.getCause();
			idx--;
		}
		return t;
	}
	
	/**
	 * 使用在錯誤處理頁面 (取出錯誤代碼及訊息)
	 */
	public static String fectcErrorCodeAndExplanation(String message) {
		int idx = message.indexOf("@@@@@");
		if (idx > 0) {
			return message.substring(idx + 5);
		}
		return "";
	}

}
