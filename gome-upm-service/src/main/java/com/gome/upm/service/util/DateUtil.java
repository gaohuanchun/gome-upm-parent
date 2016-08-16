package com.gome.upm.service.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import org.joda.time.Instant;
import org.springframework.util.StringUtils;


/**
 * 时间处理类
 * @author zhangzhixiang-ds
 *
 */
public class DateUtil {

	/**
	 * 
	 * @param time
	 * @param pattern   "yyyy-MM-dd-HH-mm-ss"
	 * @return
	 */
	public static String formatTime2Str(TemporalAccessor time, String pattern){
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
		return dateTimeFormatter.format(time);
	}
	
	public static Date Str2Date(String str, String pattern){
		if(StringUtils.isEmpty(pattern)){
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
	    SimpleDateFormat sdf = new SimpleDateFormat(pattern);  
	    Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return date;
	}
	
	public static long getLongTimeFromStr(String str, String pattern){
		Date date = DateUtil.Str2Date(str, pattern);
		return date.getTime();
	}

  
	
	public static void main(String[] args) {
		DateUtil.getLongTimeFromStr("2016-07-23 00:00:00", "yyyy-MM-dd HH:mm:ss");
		LocalDateTime localDateTime = LocalDateTime.now();
		System.out.println(localDateTime.plusHours(-2).getHour());
		System.out.println(DateUtil.formatTime2Str(localDateTime.plusMinutes(-30), "yyyy-MM-dd-HH-mm-ss"));
		System.out.println(DateUtil.formatTime2Str(localDateTime.plusDays(-365), "yyyy-MM-dd-HH-mm-ss"));
	}

}
