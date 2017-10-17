package com.senyint.core.dao.exception;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class ErrorCodeHelper {

	private static ErrorCodeHelper singleton = new ErrorCodeHelper();

	private Map<String, String> msgMap = new HashMap<String, String>();

	static {

	}



	protected ErrorCodeHelper() {
		init();
	}



	protected void init() {
		// 05000 EAO
		add(ErrorCode.SAVE_EXCEPTION, "資料儲存時發生錯誤 (entity={0}, poid={1})");
		add(ErrorCode.SAVE_CONSTRAINT_VIOLATION, "資料有問題無法儲存(違反約束) (entity={0}, poid={1})");
		add(ErrorCode.SAVE_NOT_NULL_PROPERTY_WITH_NULL, "儲存時欄位 {0} 不可為空值 (entity={1})");
		add(ErrorCode.SAVE_DATA_EXCEPTION, "儲存時資料長度超過欄位所定義或型別不符合 (entity={0}, message={1})");
		add(ErrorCode.DELETE_EXCEPTION, "資料刪除時發生錯誤 (entity={0}, poid={1})");
		add(ErrorCode.DELETE_CONSTRAINT_VIOLATION, "資料尚被參照使用無法刪除 (entity={0}, poid={1})");

		
		add(ErrorCode.UNEXPECTED_ERROR, "不預期的錯誤!");
	}



	public static ErrorCodeHelper getInstance() {
		return singleton;
	}



	protected void add(String code, String messageTemplate) {
		msgMap.put(code, messageTemplate);
	}



	public String getPatternMessage(String errorCode, Object... parameters) {
		String msgPattern = msgMap.get(errorCode);
		if (msgPattern == null) {
			return "未定義的錯誤!";
		}

		return MessageFormat.format(msgPattern, parameters);
	}
}
