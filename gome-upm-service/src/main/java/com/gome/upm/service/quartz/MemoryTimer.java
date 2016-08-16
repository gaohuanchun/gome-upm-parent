package com.gome.upm.service.quartz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.gome.upm.common.util.DateUtil;
import com.gome.upm.domain.ServerAlarmRecord;
import com.gome.upm.domain.prtg.IndexTOP5;
import com.gome.upm.service.ServerMonitorService;
import com.gome.upm.service.util.ZabbixUtils;

import redis.Gcache;

/**
 *
 */
public class MemoryTimer {

	@Resource(name = "monitorGcache")
	Gcache monitorGcache;
	@Resource(name = "serverMonitorService")
	ServerMonitorService serverMonitorService;
	public void work() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Map<String,Object>> hostList = ZabbixUtils.getHostList("0");
			List<IndexTOP5> listMemory = new ArrayList<IndexTOP5>();
			for (int i = 0; i < hostList.size(); i++) {
				String ip = ZabbixUtils.getHostInterface(hostList.get(i).get("hostid").toString());
				String hostid = hostList.get(i).get("hostid").toString();
				Double memorylastValue = ZabbixUtils.getLastValue(hostList.get(i), "vm.memory.size[pavailable]");
				if(memorylastValue!=null){
					IndexTOP5 indexTOP5 = new IndexTOP5();
					indexTOP5.setHost(ip);
					indexTOP5.setLastVal(memorylastValue);
					indexTOP5.setDeviceId(hostid);
					listMemory.add(indexTOP5);
					String host = (String) hostList.get(i).get("host");
					//先判断是否已经产生了报警记录，如果已存在报警记录，就不产生新的
					ServerAlarmRecord alarmRecordQ = new ServerAlarmRecord();
					alarmRecordQ.setHost(host);
					alarmRecordQ.setStatus("0");
					alarmRecordQ.setType("3");
					alarmRecordQ = serverMonitorService.queryAlarmRecord(alarmRecordQ);
					//CPU使用率达到90%
					if(memorylastValue>=90.00){
						String date = DateUtil.getDate("yyyy-MM-dd HH:mm:ss");
						String groupName = ZabbixUtils.getHostGroup(hostid);
						if(alarmRecordQ==null){
							//调用产生报警记录接口
							String contentMe = "服务器组"+groupName+"中的"+host+"服务器的内存使用率已达到了"+memorylastValue+"%,请关注！及时解决";
							ServerAlarmRecord alarmRecord = new ServerAlarmRecord();
							alarmRecord.setGroupName(groupName);
							alarmRecord.setHost(host);
							alarmRecord.setAlarmTime(date);
							alarmRecord.setContent(contentMe);
							alarmRecord.setStatus("0");
							alarmRecord.setAlarmValue(String.valueOf(memorylastValue));
							alarmRecord.setType("3");
							serverMonitorService.addAlarmRecord(alarmRecord);
						}else{
							//调用产生报警记录接口
							String contentMe = "服务器组"+groupName+"中的"+host+"服务器的内存使用率已达到了"+memorylastValue+"%,请关注！及时解决";
							ServerAlarmRecord alarmRecord = new ServerAlarmRecord();
							alarmRecord.setId(alarmRecordQ.getId());
							alarmRecord.setGroupName(groupName);
							alarmRecord.setHost(host);
							alarmRecord.setUpdateAlarmTime(date);
							alarmRecord.setContent(contentMe);
							alarmRecord.setAlarmValue(String.valueOf(memorylastValue));
							serverMonitorService.updateAlarmRecord(alarmRecord);
						}
					}else{
						if(alarmRecordQ!=null){
							String date = DateUtil.getDate("yyyy-MM-dd HH:mm:ss");
							String groupName = ZabbixUtils.getHostGroup(hostid);
							//调用产生报警记录接口
							String contentMe = "服务器组"+groupName+"中的"+host+"服务器的内存使用率已降到"+memorylastValue+"%,已在正常范围";
							ServerAlarmRecord alarmRecord = new ServerAlarmRecord();
							alarmRecord.setId(alarmRecordQ.getId());
							alarmRecord.setUpdateAlarmTime(date);
							alarmRecord.setContent(contentMe);
							alarmRecord.setStatus("1");
							alarmRecord.setAlarmValue(String.valueOf(memorylastValue));
							alarmRecord.setType("3");
							serverMonitorService.updateAlarmRecord(alarmRecord);
						}
					}
				}
			}
			Collections.sort(listMemory);
			map.put("memory", listMemory.size() < 5 ? listMemory : listMemory.subList(0, 5));
			String memoryTOP5 = JSONObject.toJSONString(map);
			monitorGcache.set("memoryTOP5", memoryTOP5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
