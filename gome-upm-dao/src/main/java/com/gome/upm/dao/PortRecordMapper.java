package com.gome.upm.dao;

import java.util.List;

import com.gome.upm.common.Page;
import com.gome.upm.domain.PortRecord;

/**
 * 
 * @Description: 端口监控数据dao接口
 * @author caowei-ds1
 * @date 2016年6月21日 
 * @version V1.0
 *
 */
public interface PortRecordMapper {

	/**
	 * 分页查询端口监控点数据列表.
	 * @param page
	 * 			分页信息（封装了查询条件）
	 * @return
	 * 			端口监控点列表
	 * 2016年6月23日   caowei-ds1
	 */
	List<PortRecord> selectPortRecordListByPage(Page<PortRecord> page);
	
	/**
	 * 根据搜索条件查询总记录数.
	 * @param portRecord
	 * 				搜索条件
	 * @return
	 * 				总记录数
	 * 2016年6月23日   caowei-ds1
	 */
	Integer selectTotalResultByConditions(PortRecord portRecord);
	
	/**
	 * 根据ID查找端口监控点数据.
	 * @param id
	 * 				端口监控点数据ID
	 * @return
	 * 				端口监控点数据
	 * 2016年6月23日   caowei-ds1
	 */
	PortRecord selectPortRecordById(Long id);
	
	/**
	 * 根据条件查询端口监控点数据列表,不分页.
	 * @param portRecord
	 * 				搜索条件
	 * @return
	 * 				端口监控点数据列表
	 * 2016年6月123日    caowei-ds1
	 */
	List<PortRecord> selectPortRecordListByPortRecord(PortRecord portRecord);
	
	/**
	 * 批量删除端口监控记录
	 * @param pids 父ids
	 * @return
	 */
	int batchDeleteByPids(Long[] pids);
	
	/**
	 * 根据时间删除历史记录
	 * @param startTime
	 * @return
	 */
	int deleteByTime(String startTime);
}

