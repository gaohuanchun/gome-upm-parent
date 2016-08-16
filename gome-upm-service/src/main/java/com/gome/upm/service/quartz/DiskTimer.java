package com.gome.upm.service.quartz;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gome.upm.common.util.DateUtil;
import com.gome.upm.domain.ServerAlarmRecord;
import com.gome.upm.service.ServerMonitorService;
import com.gome.upm.service.util.ZabbixUtils;

/**
 *
 */
public class DiskTimer {

	@Resource(name = "serverMonitorService")
	ServerMonitorService serverMonitorService;
	public void work() {
		try {
			List<Map<String,Object>> hostList = ZabbixUtils.getHostList("0");
			for (int i = 0; i < hostList.size(); i++) {
				String names="vfs.fs.size";
				String key = "pfree";
				List<HashMap<String,String>> itemIds = ZabbixUtils.getItemIds(hostList.get(i).get("hostid").toString(), names, key);
				if(itemIds!=null && itemIds.size()>0){
					for (int j = 0; j < itemIds.size(); j++) {
						HashMap<String,String> hashMap = itemIds.get(j);
						String itemid = hashMap.get("itemid");
						String key_ = hashMap.get("key_");
						JSONArray datas = ZabbixUtils.getDataByItemId(hostList.get(i).get("hostid").toString(), itemid);
						Object[] array = datas.toArray();
						for (int m = 0; m < array.length; m++) {
							JSONObject object = datas.getJSONObject(m);
							String value = (String) object.get("value");
							String host = (String) hostList.get(i).get("host");
							//先判断是否已经产生了报警记录，如果已存在报警记录，就不产生新的
							ServerAlarmRecord alarmRecordQ = new ServerAlarmRecord();
							alarmRecordQ.setHost(host);
							alarmRecordQ.setStatus("0");
							alarmRecordQ.setType("2");
							alarmRecordQ.setKey_(key_);
							alarmRecordQ = serverMonitorService.queryAlarmRecord(alarmRecordQ);
							if(value!=null && Double.parseDouble(value)>=20.00){
								BigDecimal bd = new BigDecimal(value);
								bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
								value = String.valueOf(bd);
								String groupName = ZabbixUtils.getHostGroup(hostList.get(i).get("hostid").toString());
								String date = DateUtil.getDate("yyyy-MM-dd HH:mm:ss");
								if(alarmRecordQ==null){
									//调用产生报警记录接口
									String contentDisk = "服务器组"+groupName+"中的"+host+"服务器的"+key_+"磁盘使用率已达到了"+value+"%,请关注！及时解决";
									ServerAlarmRecord alarmRecord = new ServerAlarmRecord();
									alarmRecord.setGroupName(groupName);
									alarmRecord.setHost(host);
									alarmRecord.setAlarmTime(date);
									alarmRecord.setContent(contentDisk);
									alarmRecord.setStatus("0");
									alarmRecord.setKey_(key_);
									alarmRecord.setAlarmValue(String.valueOf(value));
									alarmRecord.setType("2");
									try {
										serverMonitorService.addAlarmRecord(alarmRecord);
									} catch (Exception e) {
									}
								}else{
									//调用产生报警记录接口
									String contentDisk = "服务器组"+groupName+"中的"+host+"服务器的"+key_+"磁盘使用率已达到了"+value+"%,请关注！及时解决";
									ServerAlarmRecord alarmRecord = new ServerAlarmRecord();
									alarmRecord.setId(alarmRecordQ.getId());
									alarmRecord.setUpdateAlarmTime(date);
									alarmRecord.setContent(contentDisk);
									alarmRecord.setAlarmValue(String.valueOf(value));
									serverMonitorService.updateAlarmRecord(alarmRecord);
								}
							}else{
								if(alarmRecordQ!=null){
									String date = DateUtil.getDate("yyyy-MM-dd HH:mm:ss");
									String groupName = ZabbixUtils.getHostGroup(hostList.get(i).get("hostid").toString());
									//调用产生报警记录接口
									BigDecimal bd = new BigDecimal(value);
									bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
									value = String.valueOf(bd);
									String contentDisk = "服务器组"+groupName+"中的"+host+"服务器的"+key_+"磁盘使用率已降到"+value+"%,已在正常范围";
									ServerAlarmRecord alarmRecord = new ServerAlarmRecord();
									alarmRecord.setId(alarmRecordQ.getId());
									alarmRecord.setUpdateAlarmTime(date);
									alarmRecord.setContent(contentDisk);
									alarmRecord.setStatus("1");
									alarmRecord.setAlarmValue(String.valueOf(value));
									alarmRecord.setType("2");
									serverMonitorService.updateAlarmRecord(alarmRecord);
								}
							}
						}
					}
				}
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
