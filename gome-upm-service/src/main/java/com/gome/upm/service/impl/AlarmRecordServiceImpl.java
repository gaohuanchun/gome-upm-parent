package com.gome.upm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.upm.common.Page;
import com.gome.upm.dao.AlarmRecordMapper;
import com.gome.upm.domain.AlarmRecord;
import com.gome.upm.service.AlarmRecordService;
import com.gome.upm.service.util.DBContextHolder;

@Service
public class AlarmRecordServiceImpl implements AlarmRecordService {
	
	@Resource
	private AlarmRecordMapper alarmRecordMapper;

	@Override
	public Page<AlarmRecord> findAlarmRecordListByPage(Page<AlarmRecord> page) {
		DBContextHolder.setDataSource("dataSourceOne");
		List<AlarmRecord> alarmRecordList = alarmRecordMapper.selectAlarmRecordListByPage(page);
		int totalResult = alarmRecordMapper.selectTotalResultByConditions(page.getConditions());
		return new Page<AlarmRecord>(page.getPageNo(), page.getPageSize(), totalResult, alarmRecordList, page.getConditions());
	}

	@Override
	public int findTotalResultByConditions(AlarmRecord alarmRecord) {
		DBContextHolder.setDataSource("dataSourceOne");
		return alarmRecordMapper.selectTotalResultByConditions(alarmRecord);
	}

	@Override
	public List<AlarmRecord> findAlarmRecordListByConditions(AlarmRecord alarmRecord) {
		DBContextHolder.setDataSource("dataSourceOne");
		return alarmRecordMapper.selectAlarmRecordListByAlarmRecord(alarmRecord);
	}

	@Override
	public List<AlarmRecord> findAllTypes() {
		DBContextHolder.setDataSource("dataSourceOne");
		return alarmRecordMapper.selectAllTypes();
	}

	@Override
	public AlarmRecord findAlarmRecordById(long id) {
		DBContextHolder.setDataSource("dataSourceOne");
		return alarmRecordMapper.selectAlarmRecordById(id);
	}

}
