package com.senyint.core.entity.component;

/**
 * 订单支付状态
 * 
 * @author sunzhi
 *
 */
public enum OrderPayState {
	/**
	 * 等待支付
	 */
	UNPAY("0", "等待支付"),
	/**
	 * 已支付
	 */
	PAID("1", "已支付");

	
	private final String value;
	private final String description;

	private OrderPayState(String v, String desc) {
		this.value = v;
		this.description = desc;
	}

	public String getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}

	public static OrderPayState get(String strValue) {
		for (OrderPayState e : values()) {
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
