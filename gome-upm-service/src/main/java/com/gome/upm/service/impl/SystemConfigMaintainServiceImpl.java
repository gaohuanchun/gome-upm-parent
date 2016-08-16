package com.gome.upm.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.upm.dao.SystemConfigMaintainDao;
import com.gome.upm.domain.SystemConfig;
import com.gome.upm.service.SystemConfigMaintainService;

@Service("systemConfigMaintainService")
public class SystemConfigMaintainServiceImpl implements SystemConfigMaintainService {

	@Resource
	private SystemConfigMaintainDao systemConfigMaintainDao;

	@Override
	public SystemConfig querySystemConfigByKey(String key) throws Exception {
		String sql = "SELECT config_id, conf_key, conf_value, val_type, val_desc from system_config where conf_key = '"
				   + key + "' and sts = 'A'";
		SystemConfig systemConfig = systemConfigMaintainDao.querySystemConfigByKey(sql);

		return systemConfig;
	}
}
