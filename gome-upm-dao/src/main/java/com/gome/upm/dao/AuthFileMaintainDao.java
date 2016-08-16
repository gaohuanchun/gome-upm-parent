package com.gome.upm.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.gome.upm.domain.ConfigFile;

public interface AuthFileMaintainDao {
	List<ConfigFile> queryConfigFileByAuthType(@Param("paramSQL")String sql);
}
