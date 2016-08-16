package com.gome.upm.service.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.gome.upm.domain.PortRecord;

public class PortRecordConvertUtils {
	
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 属性转换
	 * @param portRecordList
	 * @return
	 */
	public static List<PortRecord> propertyConvert(List<PortRecord> portRecordList){
		if(portRecordList != null && portRecordList.size() > 0){
			for (PortRecord portRecord : portRecordList) {
				if(portRecord.getSurvival() != null){
					if(portRecord.getSurvival() == 0){
						portRecord.setSurvivalStr("不存活");
					} else if(portRecord.getSurvival() == 1){
						portRecord.setSurvivalStr("存活");
					}
				}
				if(portRecord.getVisitTime() != null){
					portRecord.setVisitTimeStr(df.format(portRecord.getVisitTime()));
				}
				
			}
		}
		return portRecordList;
	}
	
}
