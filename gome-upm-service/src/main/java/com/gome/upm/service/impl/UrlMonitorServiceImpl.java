package com.gome.upm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.upm.common.Page;
import com.gome.upm.dao.AlarmRecordMapper;
import com.gome.upm.dao.UrlMonitorMapper;
import com.gome.upm.dao.UrlRecordMapper;
import com.gome.upm.domain.AlarmRecord;
import com.gome.upm.domain.UrlMonitor;
import com.gome.upm.domain.UrlRecord;
import com.gome.upm.service.UrlMonitorService;
import com.gome.upm.service.util.AlarmRecordConvertUtils;
import com.gome.upm.service.util.DBContextHolder;
import com.gome.upm.service.util.UrlMonitorConvertUtils;
import com.gome.upm.service.util.UrlRecordConvertUtils;


/**
 * 
 * url监控service接口实现类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年06月21日    caowei-ds1    新建
 * </pre>
 */
@Service
public class UrlMonitorServiceImpl implements UrlMonitorService {

	@Resource
	private UrlMonitorMapper urlMonitorMapper;
	
	@Resource
	private UrlRecordMapper urlRecordMapper;
	
	@Resource
	private AlarmRecordMapper alarmRecordMapper;
	
	public int addUrlMonitor(UrlMonitor urlMonitor) {
		DBContextHolder.setDataSource("dataSourceOne");
		return urlMonitorMapper.insertUrlMonitor(urlMonitor);
	}

	public Page<UrlMonitor> findUrlMonitorListByPage(Page<UrlMonitor> page) {
		DBContextHolder.setDataSource("dataSourceOne");
		List<UrlMonitor> urlMonitorList = urlMonitorMapper.selectUrlMonitorListByPage(page);
		int totalResult = urlMonitorMapper.selectTotalResultByConditions(page.getConditions());
		return new Page<UrlMonitor>(page.getPageNo(), page.getPageSize(), totalResult, UrlMonitorConvertUtils.propertyConvert(urlMonitorList), page.getConditions());
	}

	public int editUrlMonitor(UrlMonitor urlMonitor) {
		DBContextHolder.setDataSource("dataSourceOne");
		return urlMonitorMapper.updateUrlMonitor(urlMonitor);
	}

	public int deleteUrlMonitorById(Long id) {
		DBContextHolder.setDataSource("dataSourceOne");
		return urlMonitorMapper.deleteUrlMonitorById(id);
	}

	public UrlMonitor findUrlMonitorById(Long id) {
		DBContextHolder.setDataSource("dataSourceOne");
		return urlMonitorMapper.selectUrlMonitorById(id);
	}

	public List<UrlMonitor> findUrlMonitorListByUrlMonitor(UrlMonitor urlMonitor) {
		DBContextHolder.setDataSource("dataSourceOne");
		return urlMonitorMapper.selectUrlMonitorListByUrlMonitor(urlMonitor);
	}

	@Override
	public int batchDeleteUrlMonitorByIds(Long[] ids) {
		DBContextHolder.setDataSource("dataSourceOne");
		int count= urlMonitorMapper.batchDeleteUrlMonitorByIds(ids);
		urlRecordMapper.batchDeleteByUids(ids);
		return count;
	}

	@Override
	public int findTotalResultByConditions(UrlMonitor urlMonitor) {
		DBContextHolder.setDataSource("dataSourceOne");
		return urlMonitorMapper.selectTotalResultByConditions(urlMonitor);
	}

	@Override
	public Page<UrlRecord> findUrlRecordListByPage(Page<UrlRecord> page) {
		DBContextHolder.setDataSource("dataSourceOne");
		List<UrlRecord> urlRecordList = urlRecordMapper.selectUrlRecordListByPage(page);
		int totalResult = urlRecordMapper.selectTotalResultByConditions(page.getConditions());
		return new Page<UrlRecord>(page.getPageNo(), page.getPageSize(), totalResult, UrlRecordConvertUtils.propertyConvert(urlRecordList), page.getConditions());
	}

	@Override
	public Page<AlarmRecord> findAlarmRecordListByPage(Page<AlarmRecord> page) {
		DBContextHolder.setDataSource("dataSourceOne");
		List<AlarmRecord> urlRecordList = alarmRecordMapper.selectAlarmRecordListByPage(page);
		int totalResult = alarmRecordMapper.selectTotalResultByConditions(page.getConditions());
		return new Page<AlarmRecord>(page.getPageNo(), page.getPageSize(), totalResult, AlarmRecordConvertUtils.propertyConvert(urlRecordList), page.getConditions());
	}

	@Override
	public boolean checkUrlIsExist(String url) {
		DBContextHolder.setDataSource("dataSourceOne");
		int count = urlMonitorMapper.selectCountByUrl(url);
		if(count > 0){
			return true;
		} else {
			return false;
		}
		
	}

	@Override
	public int batchAddUrlMonitor(List<UrlMonitor> list) {
		DBContextHolder.setDataSource("dataSourceOne");
		return urlMonitorMapper.batchInsertUrlMonitor(list);
	}

	@Override
	public int deleteByTime(String startTime) {
		DBContextHolder.setDataSource("dataSourceOne");
		return urlRecordMapper.deleteByTime(startTime);
	}

	@Override
	public List<UrlRecord> findUrlRecordList(UrlRecord urlRecord) {
		DBContextHolder.setDataSource("dataSourceOne");
		return urlRecordMapper.selectUrlRecordListByUrlRecord(urlRecord);
	}

}
