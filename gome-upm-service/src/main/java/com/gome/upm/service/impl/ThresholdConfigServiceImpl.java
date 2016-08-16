package com.gome.upm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.upm.common.Page;
import com.gome.upm.dao.ThresholdConfigMapper;
import com.gome.upm.domain.ThresholdConfig;
import com.gome.upm.service.ThresholdConfigService;
import com.gome.upm.service.util.DBContextHolder;
import com.gome.upm.service.util.ThresholdConfigConvertUtils;


/**
 * 
 * 阈值配置service接口实现类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年07月13日    caowei-ds1    新建
 * </pre>
 */
@Service
public class ThresholdConfigServiceImpl implements ThresholdConfigService {

	@Resource
	private ThresholdConfigMapper thresholdConfigMapper;
	
	public int addThresholdConfig(ThresholdConfig thresholdConfig) {
		DBContextHolder.setDataSource("dataSourceOne");
		return thresholdConfigMapper.insertThresholdConfig(thresholdConfig);
	}

	public Page<ThresholdConfig> findThresholdConfigListByPage(Page<ThresholdConfig> page) {
		DBContextHolder.setDataSource("dataSourceOne");
		List<ThresholdConfig> thresholdConfigList = thresholdConfigMapper.selectThresholdConfigListByPage(page);
		int totalResult = thresholdConfigMapper.selectTotalResultByConditions(page.getConditions());
		return new Page<ThresholdConfig>(page.getPageNo(), page.getPageSize(), totalResult, thresholdConfigList, page.getConditions());
	}

	public int editThresholdConfig(ThresholdConfig thresholdConfig) {
		DBContextHolder.setDataSource("dataSourceOne");
		return thresholdConfigMapper.updateThresholdConfig(thresholdConfig);
	}

	public int deleteThresholdConfigById(Long id) {
		DBContextHolder.setDataSource("dataSourceOne");
		return thresholdConfigMapper.deleteThresholdConfigById(id);
	}

	public ThresholdConfig findThresholdConfigById(Long id) {
		DBContextHolder.setDataSource("dataSourceOne");
		return thresholdConfigMapper.selectThresholdConfigById(id);
	}

	public List<ThresholdConfig> findThresholdConfigListByThresholdConfig(ThresholdConfig thresholdConfig) {
		DBContextHolder.setDataSource("dataSourceOne");
		return thresholdConfigMapper.selectThresholdConfigListByThresholdConfig(thresholdConfig);
	}

	@Override
	public int batchDeleteThresholdConfigByIds(Long[] ids) {
		DBContextHolder.setDataSource("dataSourceOne");
		return thresholdConfigMapper.batchDeleteThresholdConfigByIds(ids);
	}

	@Override
	public int findTotalResultByConditions(ThresholdConfig thresholdConfig) {
		DBContextHolder.setDataSource("dataSourceOne");
		return thresholdConfigMapper.selectTotalResultByConditions(thresholdConfig);
	}

	@Override
	public List<ThresholdConfig> findTotalResultByAlarmLevel(Integer alarmLevel) {
		DBContextHolder.setDataSource("dataSourceOne");
		return thresholdConfigMapper.selectCountByAlarmLevel(alarmLevel);
	}

	@Override
	public List<ThresholdConfig> findConnTop5(String dataType) {
		DBContextHolder.setDataSource("dataSourceOne");
		return thresholdConfigMapper.selectConnTop5(dataType);
	}
	
	@Override
	public List<ThresholdConfig> findTbsTop5(String dataType) {
		DBContextHolder.setDataSource("dataSourceOne");
		return thresholdConfigMapper.selectTbsTop5(dataType);
	}

	@Override
	public List<ThresholdConfig> findAlarmLog(ThresholdConfig thresholdConfig) {
		DBContextHolder.setDataSource("dataSourceOne");
		return thresholdConfigMapper.selectAlarmLog(thresholdConfig);
	}

}
