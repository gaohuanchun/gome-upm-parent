package com.gome.upm.dao;

import java.sql.SQLException;

import com.gome.upm.domain.AlarmRule;
import com.gome.upm.domain.Application;

public interface AlarmRuleMaintainDao {
	AlarmRule queryGlobalAlarmRule(String userId) throws SQLException;

	int insertGlobalConfig(AlarmRule alarmRule);

	int updateAlarmRule(AlarmRule alarmRule) throws SQLException;
	
	AlarmRule queryAlarmRule(AlarmRule alarmRule) throws SQLException;
}
