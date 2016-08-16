package com.gome.upm.common.util;

import org.apache.commons.lang.StringUtils;

/**
 * Created by cdshenjian on 2014/10/15.
 */
public class ProductUtils {
    // 时间窗口缓存前缀
    private static final String  TIME_WINDOW_PREFIX = "READY_ACTIVATE_";
    // 激活、绑定完成缓存前缀
    private static final String  MOBILE_COMPLETE_PREFIX = "COMPLETE_BIND_";

    public static boolean isMobileDevice(int configType) {
        return configType == 3301;
    }

    /***
     * 是否支持国美的设备发现协议
     * @param configType
     * @return
     */
    public static boolean isAutoConnectDevice(int configType,String protocolVersion) {
    	if(configType == 2001) {//蓝牙设备没有设备发现协议
    		return false;
    	}
    	if(StringUtils.isBlank(protocolVersion) || StringUtils.equals(protocolVersion, "0")){ //协议不支持设备发现，小智模式
    		return true;
    	}	
        return configType == 3301;
    }
    
    /**
     * 生成移动设备等待激活缓存key
     * @param productUuid
     * @param deviceId
     * @return
     */
    public static String generateMobileWaitingKey(String productUuid, String deviceId) {
        StringBuilder sb = new StringBuilder(TIME_WINDOW_PREFIX);
        sb.append(productUuid).append("_").append(deviceId);
        return sb.toString();
    }

    /**
     * 生成激活、绑定完成缓存前缀key
     * @param productUuid
     * @param deviceId
     * @return
     */
    public static String generateMobileCompleteKey(String productUuid, String deviceId, String pin) {
        StringBuilder sb = new StringBuilder(MOBILE_COMPLETE_PREFIX);
        sb.append(productUuid).append("_").append(deviceId).append("_").append(pin);
        return sb.toString();
    }
}
