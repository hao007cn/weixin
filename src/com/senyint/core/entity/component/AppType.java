package com.senyint.core.entity.component;

/**
 * 订单来源类型
 * 
 * @author sunzhi
 *
 */
public enum AppType {
	/**
	 * 微信
	 */
	WEIXIN("0", "微信"),
	/**
	 * 网站
	 */
	SITE("1", "网站"),
	/**
	 * 手机网站
	 */
	MOBILE_WEB("2", "手机网站"),
	/**
	 * 手机app
	 */
	MOBILE_APP("3", "手机app");
	
	private final String value;
	private final String description;

	private AppType(String v, String desc) {
		this.value = v;
		this.description = desc;
	}

	public String getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}

	public static AppType get(String strValue) {
		for (AppType e : values()) {
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
