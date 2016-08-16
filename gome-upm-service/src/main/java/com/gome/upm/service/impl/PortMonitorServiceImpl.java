package com.gome.upm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.upm.common.Page;
import com.gome.upm.dao.AlarmRecordMapper;
import com.gome.upm.dao.PortMonitorMapper;
import com.gome.upm.dao.PortRecordMapper;
import com.gome.upm.domain.AlarmRecord;
import com.gome.upm.domain.PortMonitor;
import com.gome.upm.domain.PortRecord;
import com.gome.upm.service.PortMonitorService;
import com.gome.upm.service.util.AlarmRecordConvertUtils;
import com.gome.upm.service.util.DBContextHolder;
import com.gome.upm.service.util.PortMonitorConvertUtils;
import com.gome.upm.service.util.PortRecordConvertUtils;


/**
 * 
 * 端口监控service接口实现类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年06月23日    caowei-ds1    新建
 * </pre>
 */
@Service
public class PortMonitorServiceImpl implements PortMonitorService {

	@Resource
	private PortMonitorMapper portMonitorMapper;
	
	@Resource
	private PortRecordMapper portRecordMapper;
	
	@Resource
	private AlarmRecordMapper alarmRecordMapper;
	
	public int addPortMonitor(PortMonitor portMonitor) {
		DBContextHolder.setDataSource("dataSourceOne");
		return portMonitorMapper.insertPortMonitor(portMonitor);
	}

	public Page<PortMonitor> findPortMonitorListByPage(Page<PortMonitor> page) {
		DBContextHolder.setDataSource("dataSourceOne");
		List<PortMonitor> portMonitorList = portMonitorMapper.selectPortMonitorListByPage(page);
		int totalResult = portMonitorMapper.selectTotalResultByConditions(page.getConditions());
		return new Page<PortMonitor>(page.getPageNo(), page.getPageSize(), totalResult, PortMonitorConvertUtils.propertyConvert(portMonitorList), page.getConditions());
	}

	public int editPortMonitor(PortMonitor portMonitor) {
		DBContextHolder.setDataSource("dataSourceOne");
		return portMonitorMapper.updatePortMonitor(portMonitor);
	}

	public int deletePortMonitorById(Long id) {
		DBContextHolder.setDataSource("dataSourceOne");
		return portMonitorMapper.deletePortMonitorById(id);
	}

	public PortMonitor findPortMonitorById(Long id) {
		DBContextHolder.setDataSource("dataSourceOne");
		return portMonitorMapper.selectPortMonitorById(id);
	}

	public List<PortMonitor> findPortMonitorListByPortMonitor(PortMonitor portMonitor) {
		DBContextHolder.setDataSource("dataSourceOne");
		return portMonitorMapper.selectPortMonitorListByPortMonitor(portMonitor);
	}

	@Override
	public int batchDeletePortMonitorByIds(Long[] ids) {
		DBContextHolder.setDataSource("dataSourceOne");
		int count= portMonitorMapper.batchDeletePortMonitorByIds(ids);
		portRecordMapper.batchDeleteByPids(ids);
		return count;
	}

	@Override
	public int findTotalResultByConditions(PortMonitor portMonitor) {
		DBContextHolder.setDataSource("dataSourceOne");
		return portMonitorMapper.selectTotalResultByConditions(portMonitor);
	}

	@Override
	public Page<PortRecord> findPortRecordListByPage(Page<PortRecord> page) {
		DBContextHolder.setDataSource("dataSourceOne");
		List<PortRecord> portRecordList = portRecordMapper.selectPortRecordListByPage(page);
		int totalResult = portRecordMapper.selectTotalResultByConditions(page.getConditions());
		return new Page<PortRecord>(page.getPageNo(), page.getPageSize(), totalResult, PortRecordConvertUtils.propertyConvert(portRecordList), page.getConditions());
	}

	@Override
	public Page<AlarmRecord> findAlarmRecordListByPage(Page<AlarmRecord> page) {
		DBContextHolder.setDataSource("dataSourceOne");
		List<AlarmRecord> portRecordList = alarmRecordMapper.selectAlarmRecordListByPage(page);
		int totalResult = alarmRecordMapper.selectTotalResultByConditions(page.getConditions());
		return new Page<AlarmRecord>(page.getPageNo(), page.getPageSize(), totalResult, AlarmRecordConvertUtils.propertyConvert(portRecordList), page.getConditions());
	}

	@Override
	public boolean checkPortIsExist(String port) {
		DBContextHolder.setDataSource("dataSourceOne");
		int count = portMonitorMapper.selectCountByPort(port);
		if(count > 0){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int batchAddPortMonitor(List<PortMonitor> list) {
		DBContextHolder.setDataSource("dataSourceOne");
		return portMonitorMapper.batchAddPortMonitor(list);
	}

	@Override
	public int deleteByTime(String startTime) {
		DBContextHolder.setDataSource("dataSourceOne");
		return portRecordMapper.deleteByTime(startTime);
	}

	@Override
	public List<PortRecord> findPortRecordList(PortRecord portRecord) {
		DBContextHolder.setDataSource("dataSourceOne");
		return portRecordMapper.selectPortRecordListByPortRecord(portRecord);
	}
	
}
