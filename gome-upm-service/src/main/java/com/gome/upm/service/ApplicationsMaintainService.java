package com.gome.upm.service;

import java.sql.SQLException;

import com.gome.upm.common.Page;
import com.gome.upm.domain.Application;
import com.gome.upm.domain.ApplicationInfo;

public interface ApplicationsMaintainService {
	Page<ApplicationInfo> loadApplication(Page<ApplicationInfo> page) throws SQLException;
	
	int batchDeleteAppByIds(Long[] ids) throws SQLException;
	
	boolean checkApplicationExist(String code);
	
	int insertApplication(Application application) throws SQLException;
	
	int deleteAppById(int id) throws SQLException;
}
