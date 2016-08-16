package com.gome.upm.service.quartz;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.gome.upm.domain.ServerHost;
import com.gome.upm.service.ServerMonitorService;
import com.gome.upm.service.util.ZabbixUtils;

/**
 *
 */
public class ServerTimer {
	@Resource(name = "serverMonitorService")
	ServerMonitorService serverMonitorService;

	public void work() {
		try {
			List<Map<String,Object>> hostList = ZabbixUtils.getHostList("");
			for (int i = 0; i < hostList.size(); i++) {
				ServerHost serverHost = new ServerHost();
				String host = (String) hostList.get(i).get("host");
				String name = (String) hostList.get(i).get("name");
				String status = (String) hostList.get(i).get("status");
				serverHost.setHost(host);
				serverHost.setName(name);
				String hostId = (String) hostList.get(i).get("hostid");
				serverHost.setHostId(hostId);
				ServerHost newHost = serverMonitorService.queryHost(hostId);
				if(newHost==null){
					//内存使用率
					String groupName = ZabbixUtils.getHostGroup(hostId);
					JSONObject objectMemeor = ZabbixUtils.getItems(hostId,"vm.memory.size[pavailable]");
					String f = null;
					String f1 = null;
					String f3 = null;
					BigDecimal bd = null;
					BigDecimal bd1 = null;
					BigDecimal bd3 = null;
					if(objectMemeor!=null){
						f = (String) objectMemeor.get("lastvalue");
						bd = new BigDecimal(f);
						bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
					}
					JSONObject objectLoad = ZabbixUtils.getItems(hostId,"system.cpu.load[all,avg1]");
					if(objectLoad!=null){
						f1 = (String) objectLoad.get("lastvalue");
						bd1 = new BigDecimal(f1);
						bd1 = bd1.setScale(2,BigDecimal.ROUND_HALF_UP);
					}
					JSONObject objectCPU = ZabbixUtils.getItems(hostId,"cpu_use_all");
					if(objectCPU!=null){
						f3 = (String) objectCPU.get("lastvalue");
						bd3 = new BigDecimal(f3);
						bd3 = bd3.setScale(2,BigDecimal.ROUND_HALF_UP);
					}
					if(bd!=null){
						serverHost.setMemory(bd+"");
					}
					if(bd1!=null){
						serverHost.setLoad(bd1+"");
					}
					if(bd3!=null){
						serverHost.setCpu(bd3+"");
					}
					serverHost.setGroupName(groupName);
					serverHost.setStatus(status);
					serverMonitorService.addHost(serverHost);
				}else{
					//内存使用率
					String groupName = ZabbixUtils.getHostGroup(hostId);
					JSONObject objectMemeor = ZabbixUtils.getItems(hostId,"vm.memory.size[pavailable]");
					String f = null;
					String f1 = null;
					String f3 = null;
					BigDecimal bd = null;
					BigDecimal bd1 = null;
					BigDecimal bd3 = null;
					if(objectMemeor!=null){
						f = (String) objectMemeor.get("lastvalue");
						bd = new BigDecimal(f);
						bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
					}
					JSONObject objectLoad = ZabbixUtils.getItems(hostId,"system.cpu.load[all,avg1]");
					if(objectLoad!=null){
						f1 = (String) objectLoad.get("lastvalue");
						bd1 = new BigDecimal(f1);
						bd1 = bd1.setScale(2,BigDecimal.ROUND_HALF_UP);
					}
					JSONObject objectCPU = ZabbixUtils.getItems(hostId,"cpu_use_all");
					if(objectCPU!=null){
						f3 = (String) objectCPU.get("lastvalue");
						bd3 = new BigDecimal(f3);
						bd3 = bd3.setScale(2,BigDecimal.ROUND_HALF_UP);
					}
					if(bd!=null){
						serverHost.setMemory(bd+"");
					}
					if(bd1!=null){
						serverHost.setLoad(bd1+"");
					}
					if(bd3!=null){
						serverHost.setCpu(bd3+"");
					}
					serverHost.setGroupName(groupName);
					serverHost.setStatus(status);
					serverMonitorService.updateHost(serverHost);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
