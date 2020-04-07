package com.djenkins.fitness.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestDateTimeUtil {
	@Autowired
	DateTimeUtil dateTimeUtil;
	
	@Test
	public void testGetDateFromStringMDY() throws ParseException {
		String dateStr = "2/28/2020";
		Date date = dateTimeUtil.getDateFromStringMDY(dateStr);
		String afterStr = dateTimeUtil.getStringFromDateMDY(date);
		assertEquals( afterStr, dateStr );
	}
}
