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
public class CPUTimer {

	@Resource(name = "monitorGcache")
	Gcache monitorGcache;
	@Resource(name = "serverMonitorService")
	ServerMonitorService serverMonitorService;
	public void work() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Map<String,Object>> hostListCPU = ZabbixUtils.getHostList("0");
			List<IndexTOP5> listCPU = new ArrayList<IndexTOP5>();
			for (int i = 0; i < hostListCPU.size(); i++) {
				String ip = ZabbixUtils.getHostInterface(hostListCPU.get(i).get("hostid").toString());
				String hostid = hostListCPU.get(i).get("hostid").toString();
				Double cpulastValue = ZabbixUtils.getLastValue(hostListCPU.get(i), "cpu_use_all");
				if(cpulastValue!=null){
					IndexTOP5 indexTOP5 = new IndexTOP5();
					indexTOP5.setHost(ip);
					indexTOP5.setLastVal(cpulastValue);
					indexTOP5.setDeviceId(hostid);
					listCPU.add(indexTOP5);
					String host = (String) hostListCPU.get(i).get("host");
					//先判断是否已经产生了报警记录，如果已存在报警记录，就不产生新的
					ServerAlarmRecord alarmRecordQ = new ServerAlarmRecord();
					alarmRecordQ.setHost(host);
					alarmRecordQ.setStatus("0");
					alarmRecordQ.setType("1");
					alarmRecordQ = serverMonitorService.queryAlarmRecord(alarmRecordQ);
					//CPU使用率达到75%
					if(cpulastValue>=75.00){
						String date = DateUtil.getDate("yyyy-MM-dd HH:mm:ss");
						String groupName = ZabbixUtils.getHostGroup(hostid);
						if(alarmRecordQ==null){
							//调用产生报警记录接口
							String contentCPU = "服务器组"+groupName+"中的"+host+"服务器的CPU使用率已达到了"+cpulastValue+"%,请关注！及时解决";
							ServerAlarmRecord alarmRecord = new ServerAlarmRecord();
							alarmRecord.setGroupName(groupName);
							alarmRecord.setHost(host);
							alarmRecord.setAlarmTime(date);
							alarmRecord.setContent(contentCPU);
							alarmRecord.setStatus("0");
							alarmRecord.setAlarmValue(String.valueOf(cpulastValue));
							alarmRecord.setType("1");
							serverMonitorService.addAlarmRecord(alarmRecord);
						}else{
							//调用产生报警记录接口
							String contentCPU = "服务器组"+groupName+"中的"+host+"服务器的CPU使用率已达到了"+cpulastValue+"%,请关注！及时解决";
							ServerAlarmRecord alarmRecord = new ServerAlarmRecord();
							alarmRecord.setId(alarmRecordQ.getId());
							alarmRecord.setUpdateAlarmTime(date);
							alarmRecord.setContent(contentCPU);
							alarmRecord.setAlarmValue(String.valueOf(cpulastValue));
							serverMonitorService.updateAlarmRecord(alarmRecord);
						}
					}else{
						if(alarmRecordQ!=null){
							String date = DateUtil.getDate("yyyy-MM-dd HH:mm:ss");
							String groupName = ZabbixUtils.getHostGroup(hostid);
							//调用产生报警记录接口
							String contentCPU = "服务器组"+groupName+"中的"+host+"服务器的CPU使用率已降到"+cpulastValue+"%,已在正常范围！";
							ServerAlarmRecord alarmRecord = new ServerAlarmRecord();
							alarmRecord.setId(alarmRecordQ.getId());
							alarmRecord.setUpdateAlarmTime(date);
							alarmRecord.setContent(contentCPU);
							alarmRecord.setStatus("1");
							alarmRecord.setAlarmValue(String.valueOf(cpulastValue));
							alarmRecord.setType("1");
							serverMonitorService.updateAlarmRecord(alarmRecord);
						}
					}
				}
			}
			Collections.sort(listCPU);
			map.put("cpu", listCPU.size() < 5 ? listCPU : listCPU.subList(0, 5));
			String cpuTOP5 = JSONObject.toJSONString(map);
			monitorGcache.set("cpuTOP5", cpuTOP5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
