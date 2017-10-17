package com.senyint.core.dao.exception;


/**
 * 系統共通性錯誤代碼
 *
 *
 */
public interface ErrorCode {

	/**
	 * 001
	 */
	String SAVE_EXCEPTION = "001";

	/**
	 * 002
	 */
	String SAVE_CONSTRAINT_VIOLATION = "002";

	/**
	 * 003
	 */
	String SAVE_NOT_NULL_PROPERTY_WITH_NULL = "003";

	/**
	 * 004
	 */
	String SAVE_DATA_EXCEPTION = "004";

	/**
	 * 005
	 */
	String DELETE_EXCEPTION = "005";

	/**
	 * 006
	 */
	String DELETE_CONSTRAINT_VIOLATION = "006";


	/**
	 * 007
	 */
	String UNEXPECTED_ERROR = "007";

}
