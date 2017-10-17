package com.senyint.core.entity.component;

/**
 * 类型
 * 
 * @author sunzhi
 *
 */
public enum OrderType {
	/**
	 * 初诊
	 */
	FIRST("0", "初诊"),
	/**
	 * 复诊
	 */
	REVIEW("1", "复诊");

	
	private final String value;
	private final String description;

	private OrderType(String v, String desc) {
		this.value = v;
		this.description = desc;
	}

	public String getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}

	public static OrderType get(String strValue) {
		for (OrderType e : values()) {
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
