package com.gome.upm.service;

import java.util.List;

import com.gome.upm.common.Page;
import com.gome.upm.domain.AlarmRecord;
import com.gome.upm.domain.PortMonitor;
import com.gome.upm.domain.PortRecord;

/**
 * 端口监控service接口
 * @author caowei-ds1
 *
 */
public interface PortMonitorService {

	/**
	 * 
	 * 新增端口监控点.
	 *
	 * @param portMonitor
	 * 			监控点信息
	 * @return
	 * 			插入记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年06月23日    caowei-ds1    新建
	 * </pre>
	 */
	int addPortMonitor(PortMonitor portMonitor);
	
	/**
	 * 批量新增端口监控点
	 * @param list
	 * @return
	 */
	int batchAddPortMonitor(List<PortMonitor> list);

	/**
	 * 
	 * 分页查询监控点列表.
	 *
	 * @param page
	 * 			分页信息（封装了查询条件）
	 * @return
	 * 			分页数据
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年06月23日    caowei-ds1    新建
	 * </pre>
	 */
	Page<PortMonitor> findPortMonitorListByPage(Page<PortMonitor> page);

	/**
	 *
	 * 编辑监控点.
	 *
	 * @param portMonitor
	 * 			监控点信息
	 * @return
	 * 			修改记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年06月23日    caowei-ds1    新建
	 * </pre>
	 */
	int editPortMonitor(PortMonitor portMonitor);

	/**
	 *
	 * 根据ID数组批量删除监控点.
	 *
	 * @param ids
	 * 			ID数组
	 * @return
	 * 			删除记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年06月23日    caowei-ds1    新建
	 * </pre>
	 */
	int batchDeletePortMonitorByIds(Long[] ids);
	
	/**
	 *
	 * 根据ID删除监控点.
	 *
	 * @param id
	 * 			ID
	 * @return
	 * 			删除记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年06月23日    caowei-ds1    新建
	 * </pre>
	 */
	int deletePortMonitorById(Long id);

	/**
	 *
	 * 根据ID查询监控点.
	 *
	 * @param id
	 * 			ID
	 * @return
	 * 			监控点信息
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年06月23日    caowei-ds1    新建
	 * </pre>
	 */
	PortMonitor findPortMonitorById(Long id);

	/**
	 *
	 * 根据条件查询监控点列表,不分页.
	 *
	 * @param portMonitor
	 * 				封装了查询条件
	 * @return
	 * 				监控点列表
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年06月23日    caowei-ds1    新建
	 * </pre>
	 */
	List<PortMonitor> findPortMonitorListByPortMonitor(PortMonitor portMonitor);
	
	/**
	 *
	 * 根据条件查询监控点记录数.
	 *
	 * @param portMonitor
	 * 				封装了查询条件
	 * @return
	 * 				记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年06月23日    caowei-ds1    新建
	 * </pre>
	 */
	int findTotalResultByConditions(PortMonitor portMonitor);
	
	/**
	 * 
	 * 分页查询监控记录列表.
	 *
	 * @param page
	 * 			分页信息（封装了查询条件）
	 * @return
	 * 			分页数据
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年06月23日    caowei-ds1    新建
	 * </pre>
	 */
	Page<PortRecord> findPortRecordListByPage(Page<PortRecord> page);
	
	/**
	 * 根据实体进行查询端口记录列表
	 * @param portRecord
	 * @return
	 */
	List<PortRecord> findPortRecordList(PortRecord portRecord);
	
	/**
	 * 
	 * 分页查询报警日志列表.
	 *
	 * @param page
	 * 			分页信息（封装了查询条件）
	 * @return
	 * 			分页数据
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年06月23日    caowei-ds1    新建
	 * </pre>
	 */
	Page<AlarmRecord> findAlarmRecordListByPage(Page<AlarmRecord> page);
	
	/**
	 * 
	 * 校验port地址是否存在.
	 *
	 * @param url
	 * 			port地址
	 * @return
	 * 			是否存在（true:存在；false:不存在）
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年06月27日    caowei-ds1    新建
	 * </pre>
	 */
	boolean checkPortIsExist(String port);
	
	/**
	 * 根据时间删除历史记录
	 * @param startTime
	 * @return
	 */
	int deleteByTime(String startTime);
}
