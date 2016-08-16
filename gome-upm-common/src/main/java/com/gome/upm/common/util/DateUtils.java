/*
 * Copyright (c) 2014 www.jd.com All rights reserved.
 * 本软件源代码版权归京东成都云平台所有,未经许可不得任意复制与传播.
 */
package com.gome.upm.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期工具类
 * @author J-ONE
 * @since 2014-03-18
 */
public class DateUtils {

	private final static Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);
	
	public static long getServerTime() {
		return System.currentTimeMillis();
	}
	/**
	 * 格式化yyyy-MM-dd'T'HH:mm:ssZ并除以１000精确到秒
	 * @param time
	 * @return
	 */
	public static Long parseTime(String time){
		if(StringUtils.isEmpty(time)){
			return null;
		}
		Date date=parseDate(time,"yyyy-MM-dd'T'HH:mm:ssZ");
		return date.getTime()/1000;
	}
	/**
	 * 格式化yyyy-MM-dd'T'HH:mm:ssZ
	 * @param time
	 * @return
	 */
	public static Long parseTimeToMilliSecond(String time){
		if(StringUtils.isEmpty(time)){
			return null;
		}
		Date date=parseDate(time,"yyyy-MM-dd'T'HH:mm:ssZ");
		return date.getTime();
	}
	
	/**
	 * 
	 * 日期字符串格式转化
	 * @param datetime
	 * @param partten
	 * @return
	 */
	public static String covertDateFormat(String datetime,String sourcePartten,String targetPartten) {
		SimpleDateFormat sdf = new SimpleDateFormat(sourcePartten);
		try {
			Date date = sdf.parse(datetime);
			sdf.applyPattern(targetPartten);
			return sdf.format(date);
		} catch (ParseException e) {
			LOGGER.warn("日期格式化失败.{}", e.getMessage());
		}
		return null;
	}
	/**
	 * 
	 * 字符串格式化为日期
	 * @param datetime
	 * @param partten
	 * @return
	 */
	public static Date parseDate(String datetime,String partten) {
		SimpleDateFormat sdf = new SimpleDateFormat(partten);
		try {
			return sdf.parse(datetime);
		} catch (ParseException e) {
		    LOGGER.error("日期格式化失败:"+datetime);
			LOGGER.warn("日期格式化失败.{}", e.getMessage());
		}
		return null;
	}
	
	/**
	 * 格式化日期,默认返回yyyy-MM-dd'T'HH:mm:ssZ
	 * @param date
	 * @return
	 */
	public static String formatTZone(Date date) {
		return format(date, "yyyy-MM-dd'T'HH:mm:ssZ");
	}
	/**
	 * 格式化日期,默认返回yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 格式化显示当前日期
	 * @param format
	 * @return
	 */
	public static String format(String format) {
		return format(new Date(), format);
	}

	/**
	 * 日期格式化
	 * @param date
	 * @param format
	 * @return
	 */
	public static String format(Date date, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} catch (Exception e) {
			LOGGER.warn("日期格式化失败.{}", e.getMessage());
		}
		return null;
	}

	/**
	 * 时间格式化， 传入毫秒
	 * @param time
	 * @return
	 */
	public static String dateFormat(long time) {
		return format(new Date(time), "yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * 时间格式化， 传入毫秒
	 * @param time
	 * @param format "yyyy-MM-dd HH:mm:ss"
	 * @return
	 */
	public static String dateFormat(long time,String format) {
		return format(new Date(time), format);
	}	
	   /**东八区
     * 时间格式化， 传入毫秒
     * @param time
     * @return
     */
    public static String dateFormatEast(Long time) {
        if(null == time|| time ==0 ){
            return "";
        }
        return format(new Date(time), "yyyy-MM-dd'T'HH:mm:ssZ");
    }

    /**
     * 获取某一日期的起始时间（0点5分0秒），参数为null时则返回当前日期的起始时间
     *
     * @param date
     * @return Date
     */
    public static long getStartTime(Date date) {
        Calendar now = Calendar.getInstance();
        if (date != null) {
            now.setTime(date);
        }
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 15);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        return now.getTime().getTime()/1000;
    }

}
