package com.gome.upm.service.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.gome.upm.domain.PortMonitor;

public class PortMonitorConvertUtils {
	
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 属性转换
	 * @param portMonitorList
	 * @return
	 */
	public static List<PortMonitor> propertyConvert(List<PortMonitor> portMonitorList){
		if(portMonitorList != null && portMonitorList.size() > 0){
			for (PortMonitor portMonitor : portMonitorList) {
				if(portMonitor.getStatus() != null){
					if(portMonitor.getStatus() == 0){
						portMonitor.setStatusStr("禁用");
					} else if(portMonitor.getStatus() == 1){
						portMonitor.setStatusStr("启用");
					}
				}
				if(portMonitor.getSurvival() != null){
					if(portMonitor.getSurvival() == 0){
						portMonitor.setSurvivalStr("不存活");
					} else if(portMonitor.getSurvival() == 1){
						portMonitor.setSurvivalStr("存活");
					}
				}
				
				if(portMonitor.getAlarmTime() != null){
					portMonitor.setAlarmTimeStr(df.format(portMonitor.getAlarmTime()));
				}
				
				if(portMonitor.getCreateTime() != null){
					portMonitor.setCreateTimeStr(df.format(portMonitor.getCreateTime()));
				}
				
				if(portMonitor.getUpdateTime() != null){
					portMonitor.setUpdateTimeStr(df.format(portMonitor.getUpdateTime()));
				}
			}
		}
		return portMonitorList;
	}
	
}
