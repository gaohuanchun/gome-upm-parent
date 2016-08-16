package com.gome.upm.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.upm.dao.AuthFileMaintainDao;
import com.gome.upm.domain.ConfigFile;
import com.gome.upm.service.AuthFileMaintainService;

@Service("authFileMaintainService")
public class AuthFileMaintainServiceImpl implements AuthFileMaintainService {

	@Resource
	private AuthFileMaintainDao authFileMaintainDao;

	@Override
	public Properties queryAuthKeysToProperties(String authType) throws SQLException {
		String sql = "select config_key, value" + authType + " from auth_file_config where sts = 'A' ";
		List<ConfigFile> configFileList = authFileMaintainDao.queryConfigFileByAuthType(sql);
		Properties properties = new Properties();
		if (configFileList.size() > 0) {
			for (ConfigFile configFile : configFileList) {
				properties.setProperty(configFile.getConfig_key(), getCustomValue(configFile, authType));
			}
		}
		return properties;
	}

	private String getCustomValue(ConfigFile configFile, String authType) {
		String value = "";
		if ("0".equals(authType)) {
			value = configFile.getValue0();
		} else if ("1".equals(authType)) {
			value = configFile.getValue1();
		} else if ("2".equals(authType)) {
			value = configFile.getValue2();
		} else if ("3".equals(authType)) {
			value = configFile.getValue3();
		} else {
			value = configFile.getValue4();
		}
		return value;
	}
}
