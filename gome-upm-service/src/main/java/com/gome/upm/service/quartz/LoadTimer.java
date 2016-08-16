package com.gome.upm.service.quartz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.gome.upm.domain.prtg.IndexTOP5;
import com.gome.upm.service.util.ZabbixUtils;

import redis.Gcache;

/**
 *
 */
public class LoadTimer {

	@Resource(name = "monitorGcache")
	Gcache monitorGcache;

	public void work() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Map<String,Object>> hostList = ZabbixUtils.getHostList("0");
			List<IndexTOP5> listMemory = new ArrayList<IndexTOP5>();
			for (int i = 0; i < hostList.size(); i++) {
				String ip = ZabbixUtils.getHostInterface(hostList.get(i).get("hostid").toString());
				String hostid = hostList.get(i).get("hostid").toString();
				Double memorylastValue = ZabbixUtils.getLastValue(hostList.get(i), "system.cpu.load[all,avg1]");
				if(memorylastValue!=null){
					IndexTOP5 indexTOP5 = new IndexTOP5();
					indexTOP5.setHost(ip);
					indexTOP5.setLastVal(memorylastValue);
					indexTOP5.setDeviceId(hostid);
					listMemory.add(indexTOP5);
				}
			}
			Collections.sort(listMemory);
			map.put("load", listMemory.size() < 5 ? listMemory : listMemory.subList(0, 5));
			String LoadTOP5 = JSONObject.toJSONString(map);
			monitorGcache.set("LoadTOP5", LoadTOP5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
