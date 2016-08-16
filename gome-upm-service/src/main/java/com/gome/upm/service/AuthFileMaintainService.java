package com.gome.upm.service;

import java.sql.SQLException;
import java.util.Properties;

public interface AuthFileMaintainService {
	Properties queryAuthKeysToProperties(String authType) throws SQLException;
}
