package com.gome.upm.service.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.gome.upm.domain.UrlRecord;

public class UrlRecordConvertUtils {
	
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 属性转换
	 * @param priceLogList
	 * @return
	 */
	public static List<UrlRecord> propertyConvert(List<UrlRecord> urlRecordList){
		if(urlRecordList != null && urlRecordList.size() > 0){
			for (UrlRecord urlRecord : urlRecordList) {
				if(urlRecord.getSurvival() != null){
					if(urlRecord.getSurvival() == 0){
						urlRecord.setSurvivalStr("不存活");
					} else{
						urlRecord.setSurvivalStr("存活");
					}
				}
				if(urlRecord.getVisitTime() != null){
					urlRecord.setVisitTimeStr(df.format(urlRecord.getVisitTime()));
				}
				if(urlRecord.getMatching()!=null){
					if(urlRecord.getMatching()==0){
						urlRecord.setMatchingStr("匹配失败");
					}else{
						urlRecord.setMatchingStr("匹配成功");
					}
				}else{
					urlRecord.setMatchingStr("未匹配");
				}
				
			}
		}
		return urlRecordList;
	}
	
}
