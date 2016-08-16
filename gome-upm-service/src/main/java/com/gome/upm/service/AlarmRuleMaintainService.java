package com.gome.upm.service;

import java.sql.SQLException;

import com.gome.upm.domain.AlarmRule;

public interface AlarmRuleMaintainService {
    AlarmRule queryGlobalAlarmRule(String userId) throws SQLException;
    int saveAlarmRule(AlarmRule rule) throws SQLException;
    int updateAlarmRule(AlarmRule alarmRule) throws SQLException;
    AlarmRule queryAlarmRule(AlarmRule alarmRule) throws SQLException;
}
