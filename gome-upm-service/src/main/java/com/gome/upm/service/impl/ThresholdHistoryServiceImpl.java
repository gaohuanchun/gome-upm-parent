package com.gome.upm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.upm.common.Page;
import com.gome.upm.dao.ThresholdHistoryMapper;
import com.gome.upm.domain.ThresholdHistory;
import com.gome.upm.service.ThresholdHistoryService;
import com.gome.upm.service.util.DBContextHolder;
import com.gome.upm.service.util.ThresholdHistoryConvertUtils;


/**
 * 
 * 阈值报警历史service接口实现类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年07月20日    caowei-ds1    新建
 * </pre>
 */
@Service
public class ThresholdHistoryServiceImpl implements ThresholdHistoryService {

	@Resource
	private ThresholdHistoryMapper thresholdHistoryMapper;
	
	public int addThresholdHistory(ThresholdHistory thresholdHistory) {
		DBContextHolder.setDataSource("dataSourceOne");
		return thresholdHistoryMapper.insertThresholdHistory(thresholdHistory);
	}

	public Page<ThresholdHistory> findThresholdHistoryListByPage(Page<ThresholdHistory> page) {
		DBContextHolder.setDataSource("dataSourceOne");
		List<ThresholdHistory> thresholdHistoryList = thresholdHistoryMapper.selectThresholdHistoryListByPage(page);
		int totalResult = thresholdHistoryMapper.selectTotalResultByConditions(page.getConditions());
		return new Page<ThresholdHistory>(page.getPageNo(), page.getPageSize(), totalResult, thresholdHistoryList, page.getConditions());
	}

	public int editThresholdHistory(ThresholdHistory thresholdHistory) {
		DBContextHolder.setDataSource("dataSourceOne");
		return thresholdHistoryMapper.updateThresholdHistory(thresholdHistory);
	}

	public int deleteThresholdHistoryById(Long id) {
		DBContextHolder.setDataSource("dataSourceOne");
		return thresholdHistoryMapper.deleteThresholdHistoryById(id);
	}

	public ThresholdHistory findThresholdHistoryById(Long id) {
		DBContextHolder.setDataSource("dataSourceOne");
		return thresholdHistoryMapper.selectThresholdHistoryById(id);
	}

	public List<ThresholdHistory> findThresholdHistoryListByThresholdHistory(ThresholdHistory thresholdHistory) {
		DBContextHolder.setDataSource("dataSourceOne");
		return thresholdHistoryMapper.selectThresholdHistoryListByThresholdHistory(thresholdHistory);
	}

	@Override
	public int batchDeleteThresholdHistoryByIds(Long[] ids) {
		DBContextHolder.setDataSource("dataSourceOne");
		return thresholdHistoryMapper.batchDeleteThresholdHistoryByIds(ids);
	}

	@Override
	public int findTotalResultByConditions(ThresholdHistory thresholdHistory) {
		DBContextHolder.setDataSource("dataSourceOne");
		return thresholdHistoryMapper.selectTotalResultByConditions(thresholdHistory);
	}

}
