package com.senyint.common.web.ajax;

public class AjaxResult {
	public final static Integer EXECUTE_SUCCESS = 1;
	public final static Integer EXECUTE_FAILURE = 0;
	
	public AjaxResult() {
		
	}
	public AjaxResult(Integer success, String message) {
		this.success = success;
		this.message = message;
	}
	public AjaxResult(Integer success, String message, Object data) {
		this.success = success;
		this.message = message;
		this.data = data;
	}
	
	/**
	 * 处理结果
	 * 1: 成功
	 * 0: 失败
	 * 
	 */
	private Integer success;
	/**
	 * 返回信息
	 */
	private String message;
	/**
	 * 要返回的数据
	 */
	private Object data;
	public Integer getSuccess() {
		return success;
	}
	public void setSuccess(Integer success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
