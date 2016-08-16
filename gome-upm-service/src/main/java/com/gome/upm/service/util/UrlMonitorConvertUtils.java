package com.gome.upm.service.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.gome.upm.domain.UrlMonitor;

public class UrlMonitorConvertUtils {
	
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 属性转换
	 * @param priceLogList
	 * @return
	 */
	public static List<UrlMonitor> propertyConvert(List<UrlMonitor> urlMonitorList){
		if(urlMonitorList != null && urlMonitorList.size() > 0){
			for (UrlMonitor urlMonitor : urlMonitorList) {
				if(urlMonitor.getStatus() != null){
					if(urlMonitor.getStatus() == 0){
						urlMonitor.setStatusStr("禁用");
					} else if(urlMonitor.getStatus() == 1){
						urlMonitor.setStatusStr("启用");
					}
				}
				if(urlMonitor.getSurvival() != null){
					if(urlMonitor.getSurvival() == 0){
						urlMonitor.setSurvivalStr("不存活");
					} else if(urlMonitor.getSurvival() == 1){
						urlMonitor.setSurvivalStr("存活");
					}else{
						urlMonitor.setSurvivalStr("匹配失败");
					}
				}
				if(urlMonitor.getUrl().length()>100){
					urlMonitor.setShortUrl(urlMonitor.getUrl().substring(0,100)+"...");
				}else{
					urlMonitor.setShortUrl(urlMonitor.getUrl());
				}
				if(urlMonitor.getAlarmTime() != null){
					urlMonitor.setAlarmTimeStr(df.format(urlMonitor.getAlarmTime()));
				}
				
				if(urlMonitor.getCreateTime() != null){
					urlMonitor.setCreateTimeStr(df.format(urlMonitor.getCreateTime()));
				}
				
				if(urlMonitor.getUpdateTime() != null){
					urlMonitor.setUpdateTimeStr(df.format(urlMonitor.getUpdateTime()));
				}
			}
		}
		return urlMonitorList;
	}
	
}
