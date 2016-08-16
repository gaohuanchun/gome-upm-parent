package com.gome.upm.service.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.gome.upm.domain.ThresholdHistory;

public class ThresholdHistoryConvertUtils {
	
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 属性转换
	 * @param 
	 * @return
	 */
	public static List<ThresholdHistory> propertyConvert(List<ThresholdHistory> thresholdHistoryList){
		if(thresholdHistoryList != null && thresholdHistoryList.size() > 0){
			for (ThresholdHistory thresholdHistory : thresholdHistoryList) {
				
				if(thresholdHistory.getAlarmTime() != null){
					thresholdHistory.setAlarmTimeStr(df.format(thresholdHistory.getAlarmTime()));
				}
				
				if(thresholdHistory.getCreateTime() != null){
					thresholdHistory.setCreateTimeStr(df.format(thresholdHistory.getCreateTime()));
				}
				
				if(thresholdHistory.getUpdateTime() != null){
					thresholdHistory.setUpdateTimeStr(df.format(thresholdHistory.getUpdateTime()));
				}
			}
		}
		return thresholdHistoryList;
	}
	
}
