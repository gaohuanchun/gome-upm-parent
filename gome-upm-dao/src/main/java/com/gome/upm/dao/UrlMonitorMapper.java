package com.gome.upm.dao;

import java.util.List;

import com.gome.upm.common.Page;
import com.gome.upm.domain.UrlMonitor;

/**
 * 
 * @Description: Url监控dao接口
 * @author caowei-ds1
 * @date 2016年6月17日 上午11:18:55
 * @version V1.0
 *
 */
public interface UrlMonitorMapper {

	/**
	 * 插入一个Url监控点
	 * @param urlMonitor
	 * 			Url监控点
	 * @return
	 * 2016年6月17日 上午11:27:50   caowei-ds1
	 */
	int insertUrlMonitor(UrlMonitor urlMonitor);
	
	/**
	 * 批量插入url监控点
	 * @param list Url监控点集合
	 * @return
	 */
	int batchInsertUrlMonitor(List<UrlMonitor> list);
	
	/**
	 * 分页查询Url监控点列表.
	 * @param page
	 * 			分页信息（封装了查询条件）
	 * @return
	 * 			Url监控点列表
	 * 2016年6月17日 上午11:38:03   caowei-ds1
	 */
	List<UrlMonitor> selectUrlMonitorListByPage(Page<UrlMonitor> page);
	
	/**
	 * 根据搜索条件查询总记录数.
	 * @param urlMonitor
	 * 				搜索条件
	 * @return
	 * 				总记录数
	 * 2016年6月17日 上午11:39:22   caowei-ds1
	 */
	Integer selectTotalResultByConditions(UrlMonitor urlMonitor);
	
	/**
	 * 更新Url监控点.
	 * @param urlMonitor
	 * 				Url监控点
	 * @return
	 * 				更新记录数
	 * 2016年6月17日 上午11:40:23   caowei-ds1
	 */
	int updateUrlMonitor(UrlMonitor urlMonitor);
	
	/**
	 * 根据ID删除Url监控点.
	 * @param id
	 * 				Url监控点ID
	 * @return
	 * 				删除记录数
	 * 2016年6月17日 上午11:41:19   caowei-ds1
	 */
	int deleteUrlMonitorById(Long id);
	
	/**
	 * 根据ID数组批量删除Url监控点.
	 * @param ids
	 * 				数组ID
	 * @return
	 * 				删除记录数
	 * 2016年6月21日 上午11:41:19   caowei-ds1
	 */
	int batchDeleteUrlMonitorByIds(Long[] ids);
	
	/**
	 * 根据ID查找Url监控点.
	 * @param id
	 * 				Url监控点ID
	 * @return
	 * 				Url监控点
	 * 2016年6月17日 上午11:42:15   caowei-ds1
	 */
	UrlMonitor selectUrlMonitorById(Long id);
	
	/**
	 * 根据条件查询Url监控点列表,不分页.
	 * @param urlMonitor
	 * 				搜索条件
	 * @return
	 * 				Url监控点列表
	 * 2016年6月17日 上午11:42:56   caowei-ds1
	 */
	List<UrlMonitor> selectUrlMonitorListByUrlMonitor(UrlMonitor urlMonitor);
	
	/**
	 * 根据Url查询监控点个数
	 * @param url
	 * 				url地址
	 * @return
	 * 				监控点个数
	 * 2016年6月27日 上午11:42:56   caowei-ds1
	 */
	Integer selectCountByUrl(String url);
}

