package com.senyint.core.dao.exception;

/**
 * 所有Wrap系統所拋出的 runtime exception 繼承自這個exception
 * <p>
 * 呼叫端程式可依需要決定是否要處理所wrap的系統類runtime exception.
 * </p>
 * 
 *
 */
public class SysRuntimeException extends AbstractRuntimeException {

	private static final long serialVersionUID = 1L;
	
	public SysRuntimeException() {
	}

	public SysRuntimeException(String message) {
		super(message);
	}

	public SysRuntimeException(Throwable cause) {
		super(cause);
	}

	public SysRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public SysRuntimeException(String code, String explanation) {
		super(code, explanation);
	}

	public SysRuntimeException(String code, String explanation, Throwable cause) {
		super(code, explanation, cause);
	}
	
}
