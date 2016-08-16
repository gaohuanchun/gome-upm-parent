package com.gome.upm.dao;

import java.util.List;

import com.gome.upm.common.Page;
import com.gome.upm.domain.ServerAlarmRecord;
import com.gome.upm.domain.ServerHost;

/**
 * 
 * 表空间dao接口.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年07月18日    liuhaikun    新建
 * </pre>
 */
public interface ServerMonitorMapper {

	void addHost(ServerHost serverHost);

	ServerHost queryHost(String hostId);

	List<ServerHost> queryHostList(Page<ServerHost> page);

	int selectTotalResultByConditions(ServerHost conditions);

	void updateHost(ServerHost serverHost);

	String[] queryHostGroup();

	String[] queryHostName(ServerHost serverHost);

	ServerAlarmRecord queryAlarmRecord(ServerAlarmRecord alarmRecord);

	void addAlarmRecord(ServerAlarmRecord alarmRecord);

	void updateAlarmRecord(ServerAlarmRecord alarmRecord);

	List<ServerAlarmRecord> selectAlarmRecordListByPage(Page<ServerAlarmRecord> page);

	int selectTotalResultByConditionsA(ServerAlarmRecord conditions);

	String[] queryHostGroupNew();

	String[] queryHostNameNew(ServerHost serverHost);

}
