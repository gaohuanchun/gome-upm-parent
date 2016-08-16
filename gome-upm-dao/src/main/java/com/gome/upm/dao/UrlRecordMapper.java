package com.gome.upm.dao;

import java.util.List;

import com.gome.upm.common.Page;
import com.gome.upm.domain.UrlRecord;

/**
 * 
 * @Description: Url监控数据dao接口
 * @author caowei-ds1
 * @date 2016年6月21日 
 * @version V1.0
 *
 */
public interface UrlRecordMapper {

	/**
	 * 分页查询Url监控点数据列表.
	 * @param page
	 * 			分页信息（封装了查询条件）
	 * @return
	 * 			Url监控点列表
	 * 2016年6月21日   caowei-ds1
	 */
	List<UrlRecord> selectUrlRecordListByPage(Page<UrlRecord> page);
	
	/**
	 * 根据搜索条件查询总记录数.
	 * @param urlRecord
	 * 				搜索条件
	 * @return
	 * 				总记录数
	 * 2016年6月21日   caowei-ds1
	 */
	Integer selectTotalResultByConditions(UrlRecord urlRecord);
	
	/**
	 * 根据ID查找Url监控点数据.
	 * @param id
	 * 				Url监控点数据ID
	 * @return
	 * 				Url监控点数据
	 * 2016年6月21日   caowei-ds1
	 */
	UrlRecord selectUrlRecordById(Long id);
	
	/**
	 * 根据条件查询Url监控点数据列表,不分页.
	 * @param urlRecord
	 * 				搜索条件
	 * @return
	 * 				Url监控点数据列表
	 * 2016年6月17日 上午11:42:56   caowei-ds1
	 */
	List<UrlRecord> selectUrlRecordListByUrlRecord(UrlRecord urlRecord);
	
	/**
	 * 批量删除监控记录
	 * @param ids
	 * @return
	 */
	int batchDeleteByUids(Long[] uids);
	
	/**
	 * 根据时间删除历史日志
	 * @param startTime 
	 * @return
	 */
	int deleteByTime(String startTime);
}

