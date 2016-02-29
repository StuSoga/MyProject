package com.zero.base.utils;

import org.junit.Test;

import java.util.Calendar;

/**
 * Created by zero on 16/2/29.
 */
public class DateUtilsTest {

	@Test
	public void test(){
		System.out.print(DateUtils.format(Calendar.getInstance().getTime(),DateUtils.DATE_TIME));
	}
}
