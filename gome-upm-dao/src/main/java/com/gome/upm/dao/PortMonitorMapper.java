package com.gome.upm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gome.upm.common.Page;
import com.gome.upm.domain.PortMonitor;

/**
 * 
 * @Description: 端口监控dao接口
 * @author caowei-ds1
 * @date 2016年6月23日
 * @version V1.0
 *
 */
public interface PortMonitorMapper {

	/**
	 * 插入一个端口监控点
	 * @param portMonitor
	 * 			端口监控点
	 * @return
	 * 2016年6月23日   caowei-ds1
	 */
	int insertPortMonitor(PortMonitor portMonitor);
	/**
	 * 批量新增端口监控点
	 * @param list
	 * @return
	 */
	int batchAddPortMonitor(List<PortMonitor> list);
	
	/**
	 * 分页查询端口监控点列表.
	 * @param page
	 * 			分页信息（封装了查询条件）
	 * @return
	 * 			端口监控点列表
	 * 2016年6月23日   caowei-ds1
	 */
	List<PortMonitor> selectPortMonitorListByPage(Page<PortMonitor> page);
	
	/**
	 * 根据搜索条件查询总记录数.
	 * @param portMonitor
	 * 				搜索条件
	 * @return
	 * 				总记录数
	 * 2016年6月23日   caowei-ds1
	 */
	Integer selectTotalResultByConditions(PortMonitor portMonitor);
	
	/**
	 * 更新端口监控点.
	 * @param PortMonitor
	 * 				端口监控点
	 * @return
	 * 				更新记录数
	 * 2016年6月23日    caowei-ds1
	 */
	int updatePortMonitor(PortMonitor portMonitor);
	
	/**
	 * 根据ID删除端口监控点.
	 * @param id
	 * 				端口监控点ID
	 * @return
	 * 				删除记录数
	 * 2016年6月23日   caowei-ds1
	 */
	int deletePortMonitorById(Long id);
	
	/**
	 * 根据ID数组批量删除端口监控点.
	 * @param ids
	 * 				数组ID
	 * @return
	 * 				删除记录数
	 * 2016年6月23日    caowei-ds1
	 */
	int batchDeletePortMonitorByIds(@Param("ids")Long[] ids);
	
	/**
	 * 根据ID查找端口监控点.
	 * @param id
	 * 				端口监控点ID
	 * @return
	 * 				端口监控点
	 * 2016年6月23日    caowei-ds1
	 */
	PortMonitor selectPortMonitorById(Long id);
	
	/**
	 * 根据条件查询端口监控点列表,不分页.
	 * @param portMonitor
	 * 				搜索条件
	 * @return
	 * 				端口监控点列表
	 * 2016年6月23日 上午11:42:56   caowei-ds1
	 */
	List<PortMonitor> selectPortMonitorListByPortMonitor(PortMonitor portMonitor);
	
	/**
	 * 根据port查询监控点个数
	 * @param port
	 * 				端口地址
	 * @return
	 * 				监控点个数
	 * 2016年6月27日 上午11:42:56   caowei-ds1
	 */
	Integer selectCountByPort(String port);
}

