package com.senyint.core.entity.component;

/**
 * 审核状态
 * 
 * @author sunzhi
 *
 */
public enum AuditType {
	/**
	 * 未审
	 */
	UNAUDIT("0", "未审"),
	/**
	 * 通过
	 */
	PASSED("1", "通过"),
	/**
	 * 驳回
	 */
	REJECT("2", "驳回");

	
	private final String value;
	private final String description;

	private AuditType(String v, String desc) {
		this.value = v;
		this.description = desc;
	}

	public String getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}

	public static AuditType get(String strValue) {
		for (AuditType e : values()) {
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
