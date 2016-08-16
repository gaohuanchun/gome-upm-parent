package com.gome.upm.service.util;

import java.util.List;

import com.gome.upm.domain.ServerInfo;

public class SystemProcessConvertUtils {

	public static List<ServerInfo> propertyConvert(List<ServerInfo> serverInfoList){
		if(serverInfoList!=null && serverInfoList.size()>0){
			for (ServerInfo serverInfo : serverInfoList) {
				if(serverInfo.getBjzt() != null){
					if("N".equals(serverInfo.getBjzt())){
						serverInfo.setBjzt("正常");
					}else if("Y".equals(serverInfo.getBjzt())){
						serverInfo.setBjzt("异常");
					}
				}
			}
		}
		return serverInfoList;
	}
	
}
