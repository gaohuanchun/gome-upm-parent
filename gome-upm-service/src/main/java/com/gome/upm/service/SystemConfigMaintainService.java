package com.gome.upm.service;


import com.gome.upm.domain.SystemConfig;

public interface SystemConfigMaintainService {
	SystemConfig querySystemConfigByKey(String key) throws Exception;
}
