package com.gome.upm.service;

import com.gome.upm.common.Page;
import com.gome.upm.domain.ServerAlarmRecord;
import com.gome.upm.domain.ServerHost;

/**
 * 网络监控相关接口
 * @author zhangzhixiang-ds
 *
 */
public interface ServerMonitorService {
	
	public void addHost(ServerHost serverHost);

	public ServerHost queryHost(String hostId);

	public Page<ServerHost> queryHostList(Page<ServerHost> page)  throws Exception;

	public void updateHost(ServerHost serverHost);

	public String[] queryHostGroup();

	public String[] queryHostName(String group);

	public void addAlarmRecord(ServerAlarmRecord alarmRecord);

	public ServerAlarmRecord queryAlarmRecord(ServerAlarmRecord alarmRecordQ);

	public void updateAlarmRecord(ServerAlarmRecord alarmRecord);

	public Page<ServerAlarmRecord> findAlarmRecordListByPage(Page<ServerAlarmRecord> p);

	public String[] queryHostGroupNew();

	public String[] queryHostNameNew(String groupName);
}
