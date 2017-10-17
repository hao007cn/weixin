package com.senyint.core.dao.exception;

/**
 * 所有應用程式所自行定義的 runtime exception 繼承自這個exception
 * <p>
 * 不需要強制呼叫端的程式處理的exception使用這個類別及自行定義的子類別,
 * 一般來說呼叫端程式(或正在操作系統的User)無法直接做錯誤復原, 所以使用runtime exception,
 * 讓呼叫的程式可以選擇處理或不處理而交由error page處理.
 * </p>
 * 
 *
 */
public class AppRuntimeException extends AbstractRuntimeException {

	private static final long serialVersionUID = 1L;
	
	public AppRuntimeException() {
	}

	public AppRuntimeException(String message) {
		super(message);
	}

	public AppRuntimeException(Throwable cause) {
		super(cause);
	}

	public AppRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public AppRuntimeException(String code, String explanation) {
		super(code, explanation);
	}

	public AppRuntimeException(String code, String explanation, Throwable cause) {
		super(code, explanation, cause);
	}

}
