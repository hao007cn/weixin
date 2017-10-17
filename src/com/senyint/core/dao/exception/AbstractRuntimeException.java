package com.senyint.core.dao.exception;

/**
 * 提供 AppRuntimeException 及  SysRuntimeException 所继承的抽象基础类别
 * <p>
 * 应用程式所自行定义的 runtime exception,不应该直接继承这个exception
 * </p>
 * 
 *
 */
public abstract class AbstractRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * 異常代碼
	 */
	private String code = "";
	
	/**
	 * 異常代碼說明
	 */
	private String explanation = "";
	
	public AbstractRuntimeException() {
	}

	public AbstractRuntimeException(String message) {
		super(message);
	}

	public AbstractRuntimeException(Throwable cause) {
		super(cause);
	}

	public AbstractRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public AbstractRuntimeException(String code, String explanation) {
		super();
		this.code = code;
		this.explanation = explanation;
	}

	public AbstractRuntimeException(String code, String explanation, Throwable cause) {
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
