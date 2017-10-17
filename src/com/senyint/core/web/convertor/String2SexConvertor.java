package com.senyint.core.web.convertor;

import org.springframework.core.convert.converter.Converter;

import com.senyint.core.entity.component.SexType;

public class String2SexConvertor implements Converter<String, SexType> {
	@Override
	public SexType convert(String enumValueStr) {
		String value = enumValueStr.trim();
		if (value.isEmpty()) {
			return null;
		}
		return SexType.get(enumValueStr);
	}
}
