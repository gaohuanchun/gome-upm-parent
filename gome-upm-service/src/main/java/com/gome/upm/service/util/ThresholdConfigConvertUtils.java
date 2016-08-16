package com.gome.upm.service.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.gome.upm.domain.ThresholdConfig;

public class ThresholdConfigConvertUtils {
	
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 属性转换
	 * @param 
	 * @return
	 */
	public static List<ThresholdConfig> propertyConvert(List<ThresholdConfig> thresholdConfigList){
		if(thresholdConfigList != null && thresholdConfigList.size() > 0){
			for (ThresholdConfig thresholdConfig : thresholdConfigList) {
				
				if(thresholdConfig.getAlarmTime() != null){
					thresholdConfig.setAlarmTimeStr(df.format(thresholdConfig.getAlarmTime()));
				}
				if(thresholdConfig.getCreateTime() != null){
					thresholdConfig.setCreateTimeStr(df.format(thresholdConfig.getCreateTime()));
				}
				if(thresholdConfig.getUpdateTime() != null){
					thresholdConfig.setUpdateTimeStr(df.format(thresholdConfig.getUpdateTime()));
				}
			}
		}
		return thresholdConfigList;
	}
	
}
