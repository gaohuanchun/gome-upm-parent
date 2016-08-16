package com.gome.upm.service.quartz;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.gome.upm.service.util.ZabbixUtils;

import redis.Gcache;

/**
 *
 */
public class indexTimer {

	@Resource(name = "monitorGcache")
	Gcache monitorGcache;

	public void work() {
		try {
			List<Map<String,Object>> hostListValid = ZabbixUtils.getHostList("0");
			List<Map<String,Object>> hostListinvalid = ZabbixUtils.getHostList("1");
			List<Map<String,Object>> templateList = ZabbixUtils.getTemplateList("3");
			List<Map<String,Object>> hostList = new ArrayList<Map<String,Object>>();
			hostList.addAll(hostListValid);
			hostList.addAll(hostListinvalid);
			hostList.addAll(templateList);
			BigDecimal bdDisk5 = new BigDecimal(Double.parseDouble(String.valueOf(hostListValid.size()+templateList.size()))*100/(Double.parseDouble((String.valueOf(hostList.size()))+"")));
			bdDisk5 = bdDisk5.setScale(1,BigDecimal.ROUND_HALF_UP);
			BigDecimal bdDisk6 = new BigDecimal(Double.parseDouble(String.valueOf(hostListinvalid.size()))*100/(Double.parseDouble((String.valueOf(hostList.size()))+"")));
			bdDisk6 = bdDisk6.setScale(1,BigDecimal.ROUND_HALF_UP);
			monitorGcache.set("totalHost", String.valueOf(hostList.size()));
			monitorGcache.set("validPHost", bdDisk5+"");
			monitorGcache.set("validHost", String.valueOf(hostListValid.size()+templateList.size()));
			monitorGcache.set("invalidPHost", bdDisk6+"");
			monitorGcache.set("invalidHost", String.valueOf(hostListinvalid.size()));
			
			int validItems = ZabbixUtils.getAllItems("0");
			int invalidItems = ZabbixUtils.getAllItems("1");
			BigDecimal bdDisk1 = new BigDecimal(Double.parseDouble(String.valueOf(validItems))*100/(Double.parseDouble((String.valueOf(validItems+invalidItems))+"")));
			bdDisk1 = bdDisk1.setScale(1,BigDecimal.ROUND_HALF_UP);
			BigDecimal bdDisk = new BigDecimal(Double.parseDouble(String.valueOf(invalidItems))*100/(Double.parseDouble((String.valueOf(validItems+invalidItems))+"")));
			bdDisk = bdDisk.setScale(1,BigDecimal.ROUND_HALF_UP);
			
			monitorGcache.set("totalItems", String.valueOf(validItems+invalidItems));
			monitorGcache.set("validPItems", bdDisk1+"");
			monitorGcache.set("validItems", String.valueOf(validItems));
			monitorGcache.set("invalidPItems", bdDisk+"");
			monitorGcache.set("invalidItems", String.valueOf(invalidItems));
			
			int validTrigger = ZabbixUtils.getTrigger("0");
			int invalidTrigger = ZabbixUtils.getTrigger("1");
			BigDecimal bdDis3 = new BigDecimal(Double.parseDouble(String.valueOf(validTrigger))*100/(Double.parseDouble((String.valueOf(validTrigger+invalidTrigger))+"")));
			bdDis3 = bdDis3.setScale(1,BigDecimal.ROUND_HALF_UP);
			BigDecimal bdDisk4 = new BigDecimal(Double.parseDouble(String.valueOf(invalidTrigger))*100/(Double.parseDouble((String.valueOf(validTrigger+invalidTrigger))+"")));
			bdDisk4 = bdDisk4.setScale(1,BigDecimal.ROUND_HALF_UP);
			
			monitorGcache.set("totalTrigger", String.valueOf(validTrigger+invalidTrigger));
			monitorGcache.set("validPTrigger", bdDis3+"");
			monitorGcache.set("validTrigger", String.valueOf(validTrigger));
			monitorGcache.set("invalidPTrigger", bdDisk4+"");
			monitorGcache.set("invalidTrigger", String.valueOf(invalidTrigger));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
