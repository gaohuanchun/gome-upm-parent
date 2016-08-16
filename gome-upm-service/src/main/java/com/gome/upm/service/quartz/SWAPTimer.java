package com.gome.upm.service.quartz;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.gome.loom.facade.ISendSmsFacade;
import com.gome.loom.model.SmsModel;
import com.gome.loom.model.TpModel;
import com.gome.memberCore.lang.model.Result;
import com.gome.upm.common.util.DateUtil;
import com.gome.upm.domain.ServerAlarmRecord;
import com.gome.upm.service.ServerMonitorService;
import com.gome.upm.service.util.ZabbixUtils;

/**
 *
 */
public class SWAPTimer {

	@Resource(name = "serverMonitorService")
	ServerMonitorService serverMonitorService;
	/** loom短信接口 */
	@Resource
	protected ISendSmsFacade sendSmsFacade;
	public void work() {
		try {
			List<Map<String,Object>> hostList = ZabbixUtils.getHostList("0");
			for (int i = 0; i < hostList.size(); i++) {
				String hostid = hostList.get(i).get("hostid").toString();
				Double swaplastValue = ZabbixUtils.getLastValue(hostList.get(i), "used_swap");
				String host = (String) hostList.get(i).get("host");
				//先判断是否已经产生了报警记录，如果已存在报警记录，就不产生新的
				ServerAlarmRecord alarmRecordQ = new ServerAlarmRecord();
				alarmRecordQ.setHost(host);
				alarmRecordQ.setStatus("0");
				alarmRecordQ.setType("4");
				alarmRecordQ = serverMonitorService.queryAlarmRecord(alarmRecordQ);
				if(swaplastValue!=null){
					//used_swap使用率达到30%
					if(swaplastValue>=30.00){
						String date = DateUtil.getDate("yyyy-MM-dd HH:mm:ss");
						String groupName = ZabbixUtils.getHostGroup(hostid);
						if(alarmRecordQ==null){
							//调用产生报警记录接口
							String contentSWAP = "服务器组"+groupName+"中的"+host+"服务器的SWAP使用率已达到了"+swaplastValue+"%,请关注！及时解决";
							ServerAlarmRecord alarmRecord = new ServerAlarmRecord();
							alarmRecord.setHost(host);
							alarmRecord.setGroupName(groupName);
							alarmRecord.setAlarmTime(date);
							alarmRecord.setContent(contentSWAP);
							alarmRecord.setStatus("0");
							alarmRecord.setAlarmValue(String.valueOf(swaplastValue));
							alarmRecord.setType("4");
							serverMonitorService.addAlarmRecord(alarmRecord);
						}else{
							//调用产生报警记录接口
							String contentSWAP = "服务器组"+groupName+"中的"+host+"服务器的SWAP使用率已达到了"+swaplastValue+"%,请关注！及时解决";
							ServerAlarmRecord alarmRecord = new ServerAlarmRecord();
							alarmRecord.setId(alarmRecordQ.getId());
							alarmRecord.setHost(host);
							alarmRecord.setGroupName(groupName);
							alarmRecord.setUpdateAlarmTime(date);
							alarmRecord.setContent(contentSWAP);
							alarmRecord.setAlarmValue(String.valueOf(swaplastValue));
							serverMonitorService.updateAlarmRecord(alarmRecord);
						}
					}else{
						if(alarmRecordQ!=null){
							String date = DateUtil.getDate("yyyy-MM-dd HH:mm:ss");
							String groupName = ZabbixUtils.getHostGroup(hostid);
							//调用产生报警记录接口
							String contentSWAP = "服务器组"+groupName+"中的"+host+"服务器的SWAP使用率已达到了"+swaplastValue+"%,请关注！及时解决";
							ServerAlarmRecord alarmRecord = new ServerAlarmRecord();
							alarmRecord.setId(alarmRecordQ.getId());
							alarmRecord.setUpdateAlarmTime(date);
							alarmRecord.setContent(contentSWAP);
							alarmRecord.setStatus("1");
							alarmRecord.setAlarmValue(String.valueOf(swaplastValue));
							alarmRecord.setType("4");
							serverMonitorService.updateAlarmRecord(alarmRecord);
						}
					
					}
				}
				
				
				//agent.ping
				Double pinglastValue = ZabbixUtils.getLastValue(hostList.get(i), "agent.ping");
				if(pinglastValue!=null){
					//先判断是否已经产生了报警记录，如果已存在报警记录，就不产生新的
					ServerAlarmRecord alarmRecordQ1 = new ServerAlarmRecord();
					alarmRecordQ1.setHost(host);
					alarmRecordQ1.setStatus("0");
					alarmRecordQ1.setType("0");
					alarmRecordQ1 = serverMonitorService.queryAlarmRecord(alarmRecordQ1);
					//used_swap使用率达到30%
					if(pinglastValue==0){
						String date = DateUtil.getDate("yyyy-MM-dd HH:mm:ss");
						String groupName = ZabbixUtils.getHostGroup(hostid);
						if(alarmRecordQ1==null){
							//调用产生报警记录接口
							String contentServer = "服务器组"+groupName+"中的"+host+"服务器宕机，请关注！及时解决";
							ServerAlarmRecord alarmRecord = new ServerAlarmRecord();
							alarmRecord.setGroupName(groupName);
							alarmRecord.setHost(host);
							alarmRecord.setAlarmTime(date);
							alarmRecord.setContent(contentServer);
							alarmRecord.setStatus("0");
							alarmRecord.setAlarmValue("0");
							alarmRecord.setType("0");
							serverMonitorService.addAlarmRecord(alarmRecord);
							//调用短信接口
							// TpModel的参数对应申请的BusinessName,模板Id 
							TpModel smsModel = new TpModel("cloud_isSystem_down","2003"); 
							smsModel.setPhone("13439530319"); //发送的手机号 
							smsModel.setIntervalTime(0);//是否延迟发送如果延迟发送需要设置,单位:小时.（实时发送丌需要设置，默讣0）
							smsModel.putTempParams("ipAddress", host);
							smsModel.putTempParams("year", DateUtil.getYear());
							smsModel.putTempParams("month", DateUtil.getMonth());
							smsModel.putTempParams("day",DateUtil.getDay());
							smsModel.putTempParams("hour",DateUtil.get24Hour());
							smsModel.putTempParams("minute", DateUtil.getMinute());
							smsModel.putTempParams("seconds",DateUtil.getSecond());
							Result<SmsModel> result = sendSmsFacade.sendSms(smsModel);
							if(result.isSuccess()){//发送成功
								System.out.println(result);
							}else{//发送失败 } }
								System.out.println(result);
							}

						}else{
							//调用产生报警记录接口
							String contentServer = "服务器组"+groupName+"中的"+host+"服务器宕机，请关注！及时解决";
							ServerAlarmRecord alarmRecord = new ServerAlarmRecord();
							alarmRecord.setId(alarmRecordQ1.getId());
							alarmRecord.setUpdateAlarmTime(date);
							alarmRecord.setContent(contentServer);
							alarmRecord.setAlarmValue("0");
							serverMonitorService.updateAlarmRecord(alarmRecord);
						}
					}else{
						if(alarmRecordQ1!=null){
							String date = DateUtil.getDate("yyyy-MM-dd HH:mm:ss");
							String groupName = ZabbixUtils.getHostGroup(hostid);
							//调用产生报警记录接口
							String contentServer = "服务器组"+groupName+"中的"+host+"服务器已恢复正常！";
							ServerAlarmRecord alarmRecord = new ServerAlarmRecord();
							alarmRecord.setId(alarmRecordQ.getId());
							alarmRecord.setAlarmTime(date);
							alarmRecord.setContent(contentServer);
							alarmRecord.setStatus("1");
							alarmRecord.setAlarmValue("1");
							alarmRecord.setType("0");
							serverMonitorService.updateAlarmRecord(alarmRecord);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
