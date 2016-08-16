package com.gome.upm.service.quartz;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;

import com.gome.upm.dao.MoNetDeviceMapper;
import com.gome.upm.dao.MoNetGroupMapper;
import com.gome.upm.dao.MoNetSensorMapper;
import com.gome.upm.domain.prtg.GroupIdc;
import com.gome.upm.domain.prtg.MoNetDevice;
import com.gome.upm.domain.prtg.MoNetGroup;
import com.gome.upm.domain.prtg.MoNetSensor;
import com.gome.upm.domain.prtg.Sensor;
import com.gome.upm.domain.prtg.SensorDevice;
import com.gome.upm.service.NetWorkMonitorService;

/**
 * 定时删除无用数据
 * @author zhangzhixiang-ds
 *
 */
public class NetWorkMonitorDel {

	private static final Logger logger = LoggerFactory.getLogger(NetWorkMonitorDel.class);
	@Resource
	private MoNetGroupMapper moNetGroupMapper;
	@Resource
	private MoNetDeviceMapper moNetDeviceMapper;
	@Resource
	private MoNetSensorMapper moNetSensorMapper;
	@Resource
	private NetWorkMonitorService netWorkMonitorService;
	
	
	/**
	 * 1分钟后执行，频率10分钟
	 */
	//@Scheduled(initialDelay=1000*60,fixedRate = 1000*600)
	@Scheduled(cron="0 0 1 * * ?")
	public void work() {
		
		long start = System.currentTimeMillis();
		logger.info("网络监控数据删除处理开始");
		try{
			List<Integer> groupIds = new LinkedList<Integer>();
			List<Integer> deviceIds = new LinkedList<Integer>();
			List<Integer> sensorIds = new LinkedList<Integer>();
			List<GroupIdc> listGroup = netWorkMonitorService.getGroupIdc(null, null, null, "probe,group,name");
			for(GroupIdc group : listGroup){
				String groupId = group.getObjId();
				if(groupId.equals("0")){
					continue;
				}
				
				//处理device
				List<SensorDevice> listDevice = netWorkMonitorService.getDevice(groupId, null, null, "device,upsens,downsens,host");
				for(SensorDevice device : listDevice){
					String deviceId = device.getObjId();
					
					//处理sensor
					List<Sensor> listSensor	= netWorkMonitorService.getSensors(deviceId, null, null, "sensor,status,message,lastvalue,priority");
					for(Sensor sensor : listSensor){
						String sensorId = sensor.getObjId();
						sensorIds.add(Integer.parseInt(sensorId));
					}
					
					deviceIds.add(Integer.parseInt(deviceId));
				}
				
				groupIds.add(Integer.parseInt(groupId));
			}
			
			
			
			this.deleteDbsIfDeleted(groupIds, deviceIds, sensorIds);

		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		long end = System.currentTimeMillis();
		logger.info("网络监控shuju,耗时(分钟):"+(end-start)/60000);
		
		
	}
	
	
//    public static List removeAll(List src,List oth){
//        LinkedList result = new LinkedList(src);//大集合用linkedlist
//        HashSet othHash = new HashSet(oth);//小集合用hashset
//        Iterator iter = result.iterator();//采用Iterator迭代器进行数据的操作
//        while(iter.hasNext()){
//            if(othHash.contains(iter.next())){
//                iter.remove();          
//            }   
//        }
//        return new LinkedList(result);
//    }
	
	
	
	private void deleteDbsIfDeleted(List<Integer> groupIds,List<Integer> deviceIds,List<Integer> sensorIds){
		List<Integer> groupsDb = moNetGroupMapper.selectAllIds();
		List<Integer> devicesDb = moNetDeviceMapper.selectAllIds();
		List<Integer> sensorsDb = moNetSensorMapper.selectAllIds();
		groupsDb.removeAll(groupIds);
		devicesDb.removeAll(deviceIds);
		sensorsDb.removeAll(sensorIds);
		
		List<Integer> groupsdel = new ArrayList<Integer>(groupsDb);
		List<Integer> devicedel = new ArrayList<Integer>(devicesDb);
		List<Integer> sensordel = new ArrayList<Integer>(sensorsDb);
		//删除过期数据
		if(groupsdel.size() > 0){
			moNetGroupMapper.deleteByIds(groupsdel);
		}
		if(devicedel.size() > 0){
			moNetDeviceMapper.deleteByIds(devicedel);
		}
		if(sensordel.size() > 0){
			moNetSensorMapper.deleteByIds(sensordel);
		}
		
	}
	
}
