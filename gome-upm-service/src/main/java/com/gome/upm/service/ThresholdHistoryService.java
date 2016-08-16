package com.gome.upm.service;

import java.util.List;

import com.gome.upm.common.Page;
import com.gome.upm.domain.ThresholdHistory;

/**
 * 阈值报警历史service接口
 * @author caowei-ds1
 *
 */
public interface ThresholdHistoryService {

	/**
	 * 
	 * 新增阈值报警历史.
	 *
	 * @param thresholdHistory
	 * 			阈值报警历史
	 * @return
	 * 			插入记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月20日    caowei-ds1    新建
	 * </pre>
	 */
	int addThresholdHistory(ThresholdHistory thresholdHistory);

	/**
	 * 
	 * 分页查询阈值报警历史列表.
	 *
	 * @param page
	 * 			分页信息（封装了查询条件）
	 * @return
	 * 			分页数据
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月20日    caowei-ds1    新建
	 * </pre>
	 */
	Page<ThresholdHistory> findThresholdHistoryListByPage(Page<ThresholdHistory> page);

	/**
	 *
	 * 编辑阈值报警历史.
	 *
	 * @param thresholdHistory
	 * 			阈值报警历史
	 * @return
	 * 			修改记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月20日    caowei-ds1    新建
	 * </pre>
	 */
	int editThresholdHistory(ThresholdHistory thresholdHistory);

	/**
	 *
	 * 根据ID数组批量删除阈值报警历史.
	 *
	 * @param ids
	 * 			ID数组
	 * @return
	 * 			删除记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月20日    caowei-ds1    新建
	 * </pre>
	 */
	int batchDeleteThresholdHistoryByIds(Long[] ids);
	
	/**
	 *
	 * 根据ID删除阈值报警历史.
	 *
	 * @param id
	 * 			ID
	 * @return
	 * 			删除记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月20日    caowei-ds1    新建
	 * </pre>
	 */
	int deleteThresholdHistoryById(Long id);

	/**
	 *
	 * 根据ID查询阈值报警历史.
	 *
	 * @param id
	 * 			ID
	 * @return
	 * 			阈值报警历史
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月20日    caowei-ds1    新建
	 * </pre>
	 */
	ThresholdHistory findThresholdHistoryById(Long id);

	/**
	 *
	 * 根据条件查询阈值报警历史列表,不分页.
	 *
	 * @param thresholdHistory
	 * 				封装了查询条件
	 * @return
	 * 				阈值报警历史列表
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月20日    caowei-ds1    新建
	 * </pre>
	 */
	List<ThresholdHistory> findThresholdHistoryListByThresholdHistory(ThresholdHistory thresholdHistory);
	
	/**
	 *
	 * 根据条件查询阈值报警历史记录数.
	 *
	 * @param thresholdHistory
	 * 				封装了查询条件
	 * @return
	 * 				记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月20日    caowei-ds1    新建
	 * </pre>
	 */
	int findTotalResultByConditions(ThresholdHistory thresholdHistory);
	
}
