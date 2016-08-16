package com.gome.upm.dao;

import java.util.List;

import com.gome.upm.common.Page;
import com.gome.upm.domain.ThresholdConfig;

/**
 * 阈值配置Dao
 * @author caowei-ds1
 *
 */
public interface ThresholdConfigMapper {

	/**
	 * 插入一个阈值配置
	 * @param thresholdConfig
	 * 			阈值配置
	 * @return
	 * 2016年7月12日   caowei-ds1
	 */
	int insertThresholdConfig(ThresholdConfig thresholdConfig);
	
	/**
	 * 分页查询阈值配置列表.
	 * @param page
	 * 			分页信息（封装了查询条件）
	 * @return
	 * 			阈值配置列表
	 * 2016年7月12日   caowei-ds1
	 */
	List<ThresholdConfig> selectThresholdConfigListByPage(Page<ThresholdConfig> page);
	
	/**
	 * 根据搜索条件查询总记录数.
	 * @param condition
	 * 				搜索条件
	 * @return
	 * 				总记录数
	 * 2016年7月12日   caowei-ds1
	 */
	Integer selectTotalResultByConditions(ThresholdConfig condition);
	
	/**
	 * 更新阈值配置.
	 * @param thresholdConfig
	 * 				阈值配置
	 * @return
	 * 				更新记录数
	 * 2016年7月12日   caowei-ds1
	 */
	int updateThresholdConfig(ThresholdConfig thresholdConfig);
	
	/**
	 * 根据ID删除阈值配置.
	 * @param id
	 * 				阈值配置ID
	 * @return
	 * 				删除记录数
	 * 2016年7月12日   caowei-ds1
	 */
	int deleteThresholdConfigById(Long id);
	
	/**
	 * 根据ID数组批量删除阈值配置.
	 * @param ids
	 * 				数组ID
	 * @return
	 * 				删除记录数
	 * 2016年7月12日   caowei-ds1
	 */
	int batchDeleteThresholdConfigByIds(Long[] ids);
	
	/**
	 * 根据ID查找阈值配置.
	 * @param id
	 * 				阈值配置ID
	 * @return
	 * 				阈值配置
	 * 2016年7月12日   caowei-ds1
	 */
	ThresholdConfig selectThresholdConfigById(Long id);
	
	/**
	 * 根据条件查询阈值配置列表,不分页.
	 * @param thresholdConfig
	 * 				阈值配置
	 * @return
	 * 				阈值配置列表
	 * 2016年7月12日   caowei-ds1
	 */
	List<ThresholdConfig> selectThresholdConfigListByThresholdConfig(ThresholdConfig thresholdConfig);

	/**
	 * 查询连接数、表空间、ASM各自的一般异常数、严重异常数
	 * @param alarmLevel
	 * @return
	 * 2016年7月20日 下午3:41:00   caowei-ds1
	 */
	List<ThresholdConfig> selectCountByAlarmLevel(Integer alarmLevel);

	/**
	 * 查询连接数异常的Top5
	 * @param dataType
	 * @return
	 * 2016年7月20日 下午3:42:10   caowei-ds1
	 */
	List<ThresholdConfig> selectConnTop5(String dataType);
	
	/**
	 * 查询表空间异常的Top5
	 * @param dataType
	 * @return
	 * 2016年7月20日 下午3:42:10   caowei-ds1
	 */
	List<ThresholdConfig> selectTbsTop5(String dataType);

	/**
	 * 查询连接数、表空间、ASM空间异常记录.
	 * @param thresholdConfig
	 * @return
	 * 2016年7月20日 下午4:52:41   caowei-ds1
	 */
	List<ThresholdConfig> selectAlarmLog(ThresholdConfig thresholdConfig);
	
}

