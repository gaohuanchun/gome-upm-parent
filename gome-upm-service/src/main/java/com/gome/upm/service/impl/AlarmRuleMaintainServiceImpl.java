package com.gome.upm.service.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.upm.dao.AlarmRuleMaintainDao;
import com.gome.upm.domain.AlarmRule;
import com.gome.upm.service.AlarmRuleMaintainService;

@Service("alarmRuleMaintainService")
public class AlarmRuleMaintainServiceImpl implements AlarmRuleMaintainService{
	
	@Resource
	private AlarmRuleMaintainDao alarmRuleMaintainDao;

	@Override
	public AlarmRule queryGlobalAlarmRule(String userId) throws SQLException {
		return alarmRuleMaintainDao.queryGlobalAlarmRule(userId);
	}

	@Override
	public int saveAlarmRule(AlarmRule rule) throws SQLException {
		return alarmRuleMaintainDao.insertGlobalConfig(rule);
	}

	@Override
	public int updateAlarmRule(AlarmRule alarmRule) throws SQLException {
		return alarmRuleMaintainDao.updateAlarmRule(alarmRule);
	}

	@Override
	public AlarmRule queryAlarmRule(AlarmRule alarmRule) throws SQLException {
		return alarmRuleMaintainDao.queryAlarmRule(alarmRule);
	}
}
