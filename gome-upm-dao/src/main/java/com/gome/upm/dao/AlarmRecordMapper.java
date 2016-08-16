package com.gome.upm.dao;

import java.util.List;

import com.gome.upm.common.Page;
import com.gome.upm.domain.AlarmRecord;

/**
 * 
 * @Description: 报警日志dao接口
 * @author caowei-ds1
 * @date 2016年6月22日 
 * @version V1.0
 *
 */
public interface AlarmRecordMapper {

	/**
	 * 分页查询报警日志列表.
	 * @param page
	 * 			分页信息（封装了查询条件）
	 * @return
	 * 			报警日志列表
	 * 2016年6月22日   caowei-ds1
	 */
	List<AlarmRecord> selectAlarmRecordListByPage(Page<AlarmRecord> page);
	
	/**
	 * 根据搜索条件查询总记录数.
	 * @param alarmRecord
	 * 				搜索条件
	 * @return
	 * 				总记录数
	 * 2016年6月22日   caowei-ds1
	 */
	Integer selectTotalResultByConditions(AlarmRecord alarmRecord);
	
	/**
	 * 根据ID查找报警日志.
	 * @param id
	 * 				报警日志ID
	 * @return
	 * 				报警日志
	 * 2016年6月22日   caowei-ds1
	 */
	AlarmRecord selectAlarmRecordById(Long id);
	
	/**
	 * 根据条件查询报警日志列表,不分页.
	 * @param urlRecord
	 * 				搜索条件
	 * @return
	 * 				报警日志列表
	 * 2016年6月22日 上午11:42:56   caowei-ds1
	 */
	List<AlarmRecord> selectAlarmRecordListByAlarmRecord(AlarmRecord alarmRecord);

	/**
	 * 查询所有的类型
	 * @return
	 * 2016年7月26日 下午12:36:05   caowei-ds1
	 */
	List<AlarmRecord> selectAllTypes();
}

