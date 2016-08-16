package com.gome.upm.service;

import java.util.List;

import com.gome.upm.common.Page;
import com.gome.upm.domain.AlarmRecord;
import com.gome.upm.domain.UrlMonitor;
import com.gome.upm.domain.UrlRecord;

/**
 * url监控service接口
 * @author caowei-ds1
 *
 */
public interface UrlMonitorService {

	/**
	 * 
	 * 新增url监控点.
	 *
	 * @param urlMonitor
	 * 			监控点信息
	 * @return
	 * 			插入记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年06月21日    caowei-ds1    新建
	 * </pre>
	 */
	int addUrlMonitor(UrlMonitor urlMonitor);
	/**
	 * 批量新增url监控点
	 * @param list 监控实体集合
	 * @return 插入数目
	 */
	int batchAddUrlMonitor(List<UrlMonitor> list);

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
	 * 2016年06月21日    caowei-ds1    新建
	 * </pre>
	 */
	Page<UrlMonitor> findUrlMonitorListByPage(Page<UrlMonitor> page);

	/**
	 *
	 * 编辑监控点.
	 *
	 * @param urlMonitor
	 * 			监控点信息
	 * @return
	 * 			修改记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年06月21日    caowei-ds1    新建
	 * </pre>
	 */
	int editUrlMonitor(UrlMonitor urlMonitor);

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
	 * 2016年06月21日    caowei-ds1    新建
	 * </pre>
	 */
	int batchDeleteUrlMonitorByIds(Long[] ids);
	
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
	 * 2016年06月17日    caowei-ds1    新建
	 * </pre>
	 */
	int deleteUrlMonitorById(Long id);

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
	 * 2016年06月21日    caowei-ds1    新建
	 * </pre>
	 */
	UrlMonitor findUrlMonitorById(Long id);

	/**
	 *
	 * 根据条件查询监控点列表,不分页.
	 *
	 * @param urlMonitor
	 * 				封装了查询条件
	 * @return
	 * 				监控点列表
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年06月21日    caowei-ds1    新建
	 * </pre>
	 */
	List<UrlMonitor> findUrlMonitorListByUrlMonitor(UrlMonitor urlMonitor);
	
	/**
	 *
	 * 根据条件查询监控点记录数.
	 *
	 * @param urlMonitor
	 * 				封装了查询条件
	 * @return
	 * 				记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年06月21日    caowei-ds1    新建
	 * </pre>
	 */
	int findTotalResultByConditions(UrlMonitor urlMonitor);
	
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
	 * 2016年06月22日    caowei-ds1    新建
	 * </pre>
	 */
	Page<UrlRecord> findUrlRecordListByPage(Page<UrlRecord> page);
	
	/**
	 * 根据信息查询端口记录列表
	 * @param urlRecord
	 * @return
	 */
	List<UrlRecord> findUrlRecordList(UrlRecord urlRecord);
	
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
	 * 2016年06月22日    caowei-ds1    新建
	 * </pre>
	 */
	Page<AlarmRecord> findAlarmRecordListByPage(Page<AlarmRecord> page);
	
	/**
	 * 
	 * 校验url地址是否存在.
	 *
	 * @param url
	 * 			url地址
	 * @return
	 * 			是否存在（true:存在；false:不存在）
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年06月27日    caowei-ds1    新建
	 * </pre>
	 */
	boolean checkUrlIsExist(String url);
	
	/**
	 * 根据时间删除历史记录
	 * @param startTime
	 * @return
	 */
	int deleteByTime(String startTime);
}
