package com.zero.base;

import org.springframework.core.convert.converter.Converter;

/**
 * Created by zero on 16/2/29.
 */
public class StringTrimConverter implements Converter<String,String> {

	public String convert(String source) {
		return null == source ? null : source.trim();
	}
}
