package com.djenkins.fitness.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

@Component
public class DateTimeUtil {
	private SimpleDateFormat formatMDY = new SimpleDateFormat("M/d/yyyy");

	public Date getDateFromStringMDY(String dateStr) throws ParseException {
		Date date = new Date(formatMDY.parse(dateStr).getTime());
		return date;
	}

	public String getStringFromDateMDY(Date date) {
		return formatMDY.format(date);
	}
}
