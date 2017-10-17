package com.senyint.wx.admin.entity.component;

public enum ResourceType {
	/**
	 * 菜单
	 */
	Unknown("0", "菜单"),
	/**
	 * 按钮
	 */
	FEMALE("1", "按钮");

	
	private final String value;
	private final String description;

	private ResourceType(String v, String desc) {
		this.value = v;
		this.description = desc;
	}

	public String getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}

	public static ResourceType get(String strValue) {
		for (ResourceType e : values()) {
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
