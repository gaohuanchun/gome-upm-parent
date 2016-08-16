package com.gome.upm.service;

import java.util.List;

import com.gome.upm.common.Page;
import com.gome.upm.domain.ThresholdConfig;

/**
 * 阈值配置service接口
 * @author caowei-ds1
 *
 */
public interface ThresholdConfigService {

	/**
	 * 
	 * 新增阈值配置.
	 *
	 * @param thresholdConfig
	 * 			阈值配置
	 * @return
	 * 			插入记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月13日    caowei-ds1    新建
	 * </pre>
	 */
	int addThresholdConfig(ThresholdConfig thresholdConfig);

	/**
	 * 
	 * 分页查询阈值配置列表.
	 *
	 * @param page
	 * 			分页信息（封装了查询条件）
	 * @return
	 * 			分页数据
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月13日    caowei-ds1    新建
	 * </pre>
	 */
	Page<ThresholdConfig> findThresholdConfigListByPage(Page<ThresholdConfig> page);

	/**
	 *
	 * 编辑阈值配置.
	 *
	 * @param thresholdConfig
	 * 			阈值配置
	 * @return
	 * 			修改记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月13日    caowei-ds1    新建
	 * </pre>
	 */
	int editThresholdConfig(ThresholdConfig thresholdConfig);

	/**
	 *
	 * 根据ID数组批量删除阈值配置.
	 *
	 * @param ids
	 * 			ID数组
	 * @return
	 * 			删除记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月13日    caowei-ds1    新建
	 * </pre>
	 */
	int batchDeleteThresholdConfigByIds(Long[] ids);
	
	/**
	 *
	 * 根据ID删除阈值配置.
	 *
	 * @param id
	 * 			ID
	 * @return
	 * 			删除记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月13日    caowei-ds1    新建
	 * </pre>
	 */
	int deleteThresholdConfigById(Long id);

	/**
	 *
	 * 根据ID查询阈值配置.
	 *
	 * @param id
	 * 			ID
	 * @return
	 * 			阈值配置
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月13日    caowei-ds1    新建
	 * </pre>
	 */
	ThresholdConfig findThresholdConfigById(Long id);

	/**
	 *
	 * 根据条件查询阈值配置列表,不分页.
	 *
	 * @param thresholdConfig
	 * 				封装了查询条件
	 * @return
	 * 				阈值配置列表
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月13日    caowei-ds1    新建
	 * </pre>
	 */
	List<ThresholdConfig> findThresholdConfigListByThresholdConfig(ThresholdConfig thresholdConfig);
	
	/**
	 *
	 * 根据条件查询阈值配置记录数.
	 *
	 * @param thresholdConfig
	 * 				封装了查询条件
	 * @return
	 * 				记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月13日    caowei-ds1    新建
	 * </pre>
	 */
	int findTotalResultByConditions(ThresholdConfig thresholdConfig);
	
	/**
	 *
	 * 根据报警级别查询连接数、表空间、ASM空间各自的记录数.
	 *
	 * @param thresholdConfig
	 * 				封装了查询条件
	 * @return
	 * 				记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月13日    caowei-ds1    新建
	 * </pre>
	 */
	List<ThresholdConfig> findTotalResultByAlarmLevel(Integer alarmLevel);
	
	/**
	 *
	 * 查询表空间异常记录的top5.
	 *
	 * @param dataType
	 * 				数据类型
	 * @return
	 * 				记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月13日    caowei-ds1    新建
	 * </pre>
	 */
	List<ThresholdConfig> findConnTop5(String dataType);
	
	/**
	 *
	 * 查询连接数异常记录的top5.
	 *
	 * @param dataType
	 * 				数据类型
	 * @return
	 * 				记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月13日    caowei-ds1    新建
	 * </pre>
	 */
	List<ThresholdConfig> findTbsTop5(String dataType);
	
	/**
	 *
	 * 查询连接数、表空间、ASM空间异常记录.
	 *
	 * @param thresholdConfig
	 * 				封装了查询条件
	 * @return
	 * 				记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月13日    caowei-ds1    新建
	 * </pre>
	 */
	List<ThresholdConfig> findAlarmLog(ThresholdConfig thresholdConfig);
}
