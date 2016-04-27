package com.lxl.travel.utils;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class DateUtil {
	public static Date getCurrentDate(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(System.currentTimeMillis());
		return Date.valueOf(date);
	}
}
