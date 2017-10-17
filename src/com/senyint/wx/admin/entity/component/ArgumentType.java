package com.senyint.wx.admin.entity.component;

public enum ArgumentType {
	/**
	 * int 类型
	 */
	int_Argument("1", "int"), 
	
	/**
	 * 浮点类型
	 */
	float_Argument("2", "float"), 
	
	/**
	 * 日期类型
	 */
	date_Argument("4","date"), 
	
	/**
	 * 布尔类型
	 */
	boolean_Argument("8", "boolean"), 
	
	/**
	 * 字符串类型
	 */
	string_Argument("16", "string");

	private final String value;
	private final String description;

	private ArgumentType(String v, String desc) {
		this.value = v;
		this.description = desc;
	}

	public String getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}

	public static ArgumentType get(String strValue) {
		for (ArgumentType e : values()) {
			if (e.getValue().equals(strValue)) {
				return e;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return this.value;
	}
}
