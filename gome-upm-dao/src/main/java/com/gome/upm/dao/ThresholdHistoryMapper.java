package com.gome.upm.dao;

import java.util.List;

import com.gome.upm.common.Page;
import com.gome.upm.domain.ThresholdHistory;

/**
 * 阈值报警历史Dao
 * @author caowei-ds1
 *
 */
public interface ThresholdHistoryMapper {

	/**
	 * 插入一个阈值报警历史
	 * @param thresholdHistory
	 * 			阈值报警历史
	 * @return
	 * 2016年7月20日   caowei-ds1
	 */
	int insertThresholdHistory(ThresholdHistory thresholdHistory);
	
	/**
	 * 分页查询阈值报警历史列表.
	 * @param page
	 * 			分页信息（封装了查询条件）
	 * @return
	 * 			阈值报警历史列表
	 * 2016年7月20日   caowei-ds1
	 */
	List<ThresholdHistory> selectThresholdHistoryListByPage(Page<ThresholdHistory> page);
	
	/**
	 * 根据搜索条件查询总记录数.
	 * @param condition
	 * 				搜索条件
	 * @return
	 * 				总记录数
	 * 2016年7月20日   caowei-ds1
	 */
	Integer selectTotalResultByConditions(ThresholdHistory condition);
	
	/**
	 * 更新阈值报警历史.
	 * @param thresholdHistory
	 * 				阈值报警历史
	 * @return
	 * 				更新记录数
	 * 2016年7月20日   caowei-ds1
	 */
	int updateThresholdHistory(ThresholdHistory thresholdHistory);
	
	/**
	 * 根据ID删除阈值报警历史.
	 * @param id
	 * 				阈值报警历史ID
	 * @return
	 * 				删除记录数
	 * 2016年7月20日   caowei-ds1
	 */
	int deleteThresholdHistoryById(Long id);
	
	/**
	 * 根据ID数组批量删除阈值报警历史.
	 * @param ids
	 * 				数组ID
	 * @return
	 * 				删除记录数
	 * 2016年7月20日   caowei-ds1
	 */
	int batchDeleteThresholdHistoryByIds(Long[] ids);
	
	/**
	 * 根据ID查找阈值报警历史.
	 * @param id
	 * 				阈值报警历史ID
	 * @return
	 * 				阈值报警历史
	 * 2016年7月20日   caowei-ds1
	 */
	ThresholdHistory selectThresholdHistoryById(Long id);
	
	/**
	 * 根据条件查询阈值报警历史列表,不分页.
	 * @param thresholdHistory
	 * 				阈值报警历史
	 * @return
	 * 				阈值报警历史列表
	 * 2016年7月20日   caowei-ds1
	 */
	List<ThresholdHistory> selectThresholdHistoryListByThresholdHistory(ThresholdHistory thresholdHistory);

}

