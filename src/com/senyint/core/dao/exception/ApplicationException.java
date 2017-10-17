package com.senyint.core.dao.exception;

/**
 * 所有應用程式所自行定義的 checked exception 繼承自這個exception
 * <p>
 * 需強制呼叫端的程式處理的exception才使用這個類別及自行定義的子類別,
 * 一般來說呼叫端程式(或正在操作系統的User)有機會做錯誤復原, 這時候才使用checked exception.
 * </p>
 * 
 */
public class ApplicationException extends Exception {

	private static final long serialVersionUID = 1L;

	private static final ErrorCodeHelper errorCodeHelper = ErrorCodeHelper
				.getInstance();
	
	/**
	 * get error message by error code, and format it with specieid parameters.
	 * <p>
	 * the error code was defined in {@link ErrorCode}, and the error
	 * message(pattern) was defined in {@link ErrorCodeHelper}. you must refer
	 * to these class to know the message pattern for correct error message.
	 * </p>
	 * 
	 * @param errorCode
	 * @param params
	 * @return
	 * @see ErrorCode
	 * @see ErrorCodeHelper
	 */
	protected static String getErrorMessage(String errorCode, Object... params) {
		return errorCodeHelper.getPatternMessage(errorCode, params);
	}

	/**
	 * 異常代碼
	 */
	private String code = "";
	
	/**
	 * 異常代碼說明
	 */
	private String explanation = "";

	public ApplicationException() {
	}

	public ApplicationException(String message) {
		super(message);
	}

	public ApplicationException(Throwable cause) {
		super(cause);
	}

	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApplicationException(String code, String explanation) {
		super();
		this.code = code;
		this.explanation = explanation;
	}

	public ApplicationException(String code, String explanation, Throwable cause) {
		super(cause);
		this.code = code;
		this.explanation = explanation;
	}
	
	@Override
	public String getMessage() {
		StringBuffer buf = new StringBuffer();
		buf.append("@@@@@[Error Code: ");
		buf.append(getCode());
		buf.append("] \"");
		buf.append(getExplanation());
		buf.append("\" ");
		if (super.getMessage() != null) {
			buf.append(super.getMessage());
		}
		return buf.toString();
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public String getExplanation() {
		return explanation;
	}
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	
	public String getCodeAndExplanation() {
		return code + " - " + explanation;
	}

}
