package com.gome.upm.service.quartz;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import com.gome.upm.common.util.AppConfigUtil;
import com.gome.upm.dao.MoNetHistoryMapper;
import com.gome.upm.dao.MoNetSensorMapper;
import com.gome.upm.domain.prtg.MoNetHistory;
import com.gome.upm.service.NetWorkMonitorService;

/**
 * 网络监控流量一览
 * @author zhangzhixiang-ds
 *
 */
public class NetWorkIndexSummary {
//	private Logger logger = Logger.getLogger(NetWorkIndexSummary.class);
	private static final Logger logger = LoggerFactory.getLogger(NetWorkIndexSummary.class);
	@Resource
	private NetWorkMonitorService netWorkMonitorService;
	@Resource
	private MoNetHistoryMapper moNetHistoryMapper;
	@Resource
	private MoNetSensorMapper moNetSensorMapper;

	//5秒钟后执行,频率15分钟
	@Scheduled(initialDelay=5000,fixedRate = 1000*60*15)
	public void work() {
		logger.info("start");
		long currentTime = System.currentTimeMillis();
		long oneHourBefore = currentTime-60*60*1000;
		String sensorIds1 = AppConfigUtil.getStringValue("prtg.api.flowSumarySensorIds");
		String sensorIds2 = AppConfigUtil.getStringValue("prtg.api.flowSumarySensorIds2");
		String[] ids1 = sensorIds1.split(":");
		String[] ids2 = sensorIds2.split(":");
		logger.info("ids1:"+ids1.toString()+"|ids2:"+ids2.toString());
		for(int i = 0; i < ids1.length; i++){
			String sensorId =ids1[i];
			//MoNetSensor sensor = moNetSensorMapper.selectByPrimaryKey(Integer.parseInt(sensorId));
			List<MoNetHistory> list = netWorkMonitorService.getSummaryFromApi(sensorId);
			if(list.size() == 0){
				continue;
			}
			Collections.reverse(list);
			for(MoNetHistory history : list){
				history.setSensorId(Integer.parseInt(sensorId));
				history.setCreateTime(new Date());
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("sensorId", sensorId);
				map.put("beforeDate", new Date(oneHourBefore));
				moNetHistoryMapper.deleteByMap(map);//删除一小时之前数据
				moNetHistoryMapper.insertSelective(history);//插入新数据
			}
		}
		
		try {
			Thread.sleep(60000);//sleep 1分钟
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//
		
		//sleep一分钟
		for(int i = 0; i < ids2.length; i++){
			String sensorId =ids2[i];
			List<MoNetHistory> list = netWorkMonitorService.getSummaryFromApi(sensorId);
			if(list.size() == 0){
				continue;
			}
			Collections.reverse(list);
			for(MoNetHistory history : list){
				history.setSensorId(Integer.parseInt(sensorId));
				history.setCreateTime(new Date());
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("sensorId", sensorId);
				map.put("beforeDate", new Date(oneHourBefore));
				moNetHistoryMapper.deleteByMap(map);//删除一小时之前数据
				moNetHistoryMapper.insertSelective(history);//插入新数据
			}
		}
		logger.info("end");
	}
	

	

}
