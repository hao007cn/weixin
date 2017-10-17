package com.senyint.core.entity.component;

public enum SexType {
	/**
	 * 保密
	 */
	Unknown("0", "保密"),
	/**
	 * 男
	 */
	MALE("1", "男"),
	/**
	 * 女
	 */
	FEMALE("2", "女");

	
	private final String value;
	private final String description;

	private SexType(String v, String desc) {
		this.value = v;
		this.description = desc;
	}

	public String getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}

	public static SexType get(String strValue) {
		for (SexType e : values()) {
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
