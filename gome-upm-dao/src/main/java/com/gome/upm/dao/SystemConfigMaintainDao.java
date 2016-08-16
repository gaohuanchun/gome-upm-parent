package com.gome.upm.dao;

import org.apache.ibatis.annotations.Param;

import com.gome.upm.domain.SystemConfig;

public interface SystemConfigMaintainDao {
	SystemConfig querySystemConfigByKey(@Param("paramSQL")String sql) throws Exception;
}
