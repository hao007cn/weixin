package com.senyint.core.entity.component;

/**
 * 订单状态
 * 
 * @author sunzhi
 *
 */
public enum OrderState {
	/**
	 * 已提交
	 */
	SUBMITED("0", "待诊"),
	/**
	 * 已取消
	 */
	CANCELED("1", "已取消"),
	/**
	 * 已关闭
	 */
	CLOSEED("2", "已关闭"),
	/**
	 * 完成
	 */
	FINISHED("3", "已就诊"),
	/**
	 * 爽约
	 */
	MISS("4", "爽约");
	
	private final String value;
	private final String description;

	private OrderState(String v, String desc) {
		this.value = v;
		this.description = desc;
	}

	public String getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}

	public static OrderState get(String strValue) {
		for (OrderState e : values()) {
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
