package com.gome.upm.service.quartz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
 * 网络监控数据定时初始化
 * @author zhangzhixiang-ds
 *
 */
public class NetWorkMonitorInit {
	
//	private Logger logger = Logger.getLogger(NetWorkMonitorInit.class);
	private static final Logger logger = LoggerFactory.getLogger(NetWorkMonitorInit.class);
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
	@Scheduled(initialDelay=1000*60,fixedRate = 1000*600)
	public void work() {
		
		long start = System.currentTimeMillis();
		logger.info("网络监控数据初始化");
		try{
			List<GroupIdc> listGroup = netWorkMonitorService.getGroupIdc(null, null, null, "probe,group,name");
			for(GroupIdc group : listGroup){
				if(group.getObjId().equals("0")){
					continue;
				}
				//处理group
				MoNetGroup newNetGroup = this.transformGroup(group);
				
				
				//处理device
				List<SensorDevice> listDevice = netWorkMonitorService.getDevice(group.getObjId(), null, null, "device,upsens,downsens,host");
				for(SensorDevice device : listDevice){
					MoNetDevice newNetDevice = this.transformDevice(device, group.getObjId(), group.getGroup(),group.getProbe());
					
					//处理sensor
					List<Sensor> listSensor	= netWorkMonitorService.getSensors(device.getObjId(), null, null, "sensor,status,message,lastvalue,priority");
					int errorCount = 0;
					
					for(Sensor sensor : listSensor){
						MoNetSensor newNetSensor = this.transformSensor(sensor, group.getObjId(), group.getGroup(), device.getObjId(), device.getDevice(),group.getProbe());
						String lastValue = newNetSensor.getLastvalue();
						if(newNetSensor.getStatusRaw() == 5){
							newNetSensor.setState(1);//停机状态
							//停机
							errorCount++;
						}else{
							newNetSensor.setState(0);//正常状态
						}
						
						if(StringUtils.isEmpty(lastValue) || lastValue.equals("-") || lastValue.contains("<")){
							newNetSensor.setLastvalueUse(0.00);
						}else{
							lastValue = lastValue.split(" ")[0].replaceAll(",", "");
							newNetSensor.setLastvalueUse(Double.parseDouble(lastValue));
						}
						
						
						
						this.saveOrUpdateSensor(newNetSensor);
					}
					
					
					//最后更新插入设备信息
					if(listSensor.size() == 0){
						newNetDevice.setState(0);//正常
					}else if(errorCount == listSensor.size()){
						newNetDevice.setState(1);//故障
					}else{
						newNetDevice.setState(0);//正常
					}
					newNetDevice.setSensorError(errorCount);
					newNetDevice.setSensorTotal(listSensor.size());
					this.saveOrUpdateDevice(newNetDevice);
				}
				
				this.insertOrUpdateGroup(newNetGroup);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		long end = System.currentTimeMillis();
		logger.info("网络监控初始化结束,耗时(分钟):"+(end-start)/60000);
		
		
	}
	
	private void insertOrUpdateGroup(MoNetGroup newNetGroup){
		
		MoNetGroup netGroup = moNetGroupMapper.selectByPrimaryKey(newNetGroup.getGroupId());
		if(netGroup == null){
			//插入
			moNetGroupMapper.insertSelective(newNetGroup);
		}else{
			//更新
			moNetGroupMapper.updateByPrimaryKeySelective(newNetGroup);
		}
	}
	
	private MoNetGroup transformGroup(GroupIdc group){
		MoNetGroup netGroup = new MoNetGroup();
		netGroup.setGroupName(group.getGroup());
		netGroup.setGroupId(Integer.parseInt(group.getObjId()));
		netGroup.setName(group.getName());
		netGroup.setProbe(group.getProbe());
		return netGroup;
	}
	
	private MoNetDevice transformDevice(SensorDevice device, String groupId, String groupName, String prob){
		MoNetDevice netDevice = new MoNetDevice();
		netDevice.setDeviceId(Integer.parseInt(device.getObjId()));
		netDevice.setDeviceName(device.getDevice());
		//netDevice.setDownacksens(device.getDownacksens());
		if(!StringUtils.isEmpty(device.getDownacksensRaw())){
			netDevice.setDownacksensRaw(Integer.parseInt(device.getDownacksensRaw()));
		}

		//netDevice.setDownsens(device.getDownsens());
		if(!StringUtils.isEmpty(device.getDownsensRaw())){
			netDevice.setDownsensRaw(Integer.parseInt(device.getDownsensRaw()));
		}
		
		netDevice.setGroupId(Integer.parseInt(groupId));
		netDevice.setGroupName(groupName);
		netDevice.setHost(device.getHost());
		if(device.getPartialdownsensRaw() != null){
			netDevice.setPartialdownsensRaw(Integer.parseInt(device.getPartialdownsensRaw()));
		}
		//netDevice.setPartialdownsens(device.getPartialdownsens());
		//netDevice.setPausedsens(device.getPausedsens());
		if(!StringUtils.isEmpty(device.getPausedsensRaw())){
			netDevice.setPausedsensRaw(Integer.parseInt(device.getPausedsensRaw()));
		}
		netDevice.setProbeName(prob);
		//netDevice.setUndefinedsens(device.getUndefinedsens());
		if(!StringUtils.isEmpty(device.getUndefinedsensRaw())){
			netDevice.setUndefinedsensRaw(Integer.parseInt(device.getUndefinedsensRaw()));
		}
		//netDevice.setUnusualsens(device.getUnusualsens());
		if(!StringUtils.isEmpty(device.getUndefinedsensRaw())){
			netDevice.setUnusualsensRaw(Integer.parseInt(device.getUnusualsensRaw()));	
		}
		//netDevice.setUpsens(null);
		if(!StringUtils.isEmpty(device.getUpsensRaw())){
			netDevice.setUpsensRaw(Integer.parseInt(device.getUpsensRaw()));
		}
		//netDevice.setWarnsens(device.getWarnsens());
		if(!StringUtils.isEmpty(device.getUpsensRaw())){
			netDevice.setWarnsensRaw(Integer.parseInt(device.getUpsensRaw()));
		}
		return netDevice;
	}
	
	private void saveOrUpdateDevice(MoNetDevice newNetDevice){
		//newNetDevice.setAlarmTime(new Date());
		MoNetDevice device = moNetDeviceMapper.selectByPrimaryKey(newNetDevice.getDeviceId());
		if(device != null){
			//更新
			if(device.getState() == MoNetDevice.ALARM_STATE && newNetDevice.getState() == MoNetDevice.STOP_STATE){
				newNetDevice.setState(device.getState());
			}
			moNetDeviceMapper.updateByPrimaryKeySelective(newNetDevice);
		}else{
			//插入
			moNetDeviceMapper.insertSelective(newNetDevice);
		}
	}
	
	
	
	private MoNetSensor transformSensor(Sensor sensor, String groupId, String groupName, String deviceId, String deviceName,String prob){
		MoNetSensor netSensor = new MoNetSensor();
		netSensor.setDeviceId(Integer.parseInt(deviceId));
		netSensor.setDeviceName(deviceName);
		//netSensor.setFavorite(sensor.getFavorite());
		if(!StringUtils.isEmpty(sensor.getFavoriteRaw())){
			netSensor.setFavoriteRaw(Integer.parseInt(sensor.getFavoriteRaw()));
		}
		netSensor.setGroupId(Integer.parseInt(groupId));
		netSensor.setGroupName(groupName);
		netSensor.setLastvalue(sensor.getLastvalue());
		if(!StringUtils.isEmpty(sensor.getLastvalueRaw())){
			netSensor.setLastvalueRaw(sensor.getLastvalueRaw());
		}
		//netSensor.setMessage(sensor.getMessage());
		netSensor.setMessageRaw(sensor.getMessageRaw());
		if(!StringUtils.isEmpty(sensor.getPriority())){
			netSensor.setPriority(Integer.parseInt(sensor.getPriority()));
		}
		netSensor.setProbe(prob);
		netSensor.setSensorId(Integer.parseInt(sensor.getObjId()));
		netSensor.setSensorName(sensor.getSensor());
		netSensor.setStatus(sensor.getStatus());
		if(!StringUtils.isEmpty(sensor.getStatusRaw())){
			netSensor.setStatusRaw(Integer.parseInt(sensor.getStatusRaw()));
		}
		if(sensor.getSensor().toLowerCase().startsWith("cpu")){
			//cpu传感器
			netSensor.setType("cpu");
		}else if(sensor.getSensor().toLowerCase().startsWith("memory")){
			//内存传感器
			netSensor.setType("mem");
		}else{
			//端口流量传感器
			if(matchFlow(sensor.getSensor())){
				netSensor.setType("flow");
			}else{
				netSensor.setType("other");
			}
			
		}
		return netSensor;
	}
	
	public boolean matchFlow(String sensorName){
		//Pattern pattern1 = Pattern.compile("^\\([0-9]+\\)*");
		Pattern pattern1 = Pattern.compile("^\\([0-9]+\\).*");
		Matcher matcher1 = pattern1.matcher(sensorName);
		if (matcher1.matches()) {
			return true;
		}else{
			return false;
		}
	}
	
	
	private void saveOrUpdateSensor(MoNetSensor newNetSensor){
		//newNetSensor.setAlarmTime(new Date());
		MoNetSensor  sensor = moNetSensorMapper.selectByPrimaryKey(newNetSensor.getSensorId());
		if(sensor == null){
			//插入
			moNetSensorMapper.insertSelective(newNetSensor);
		}else{
			//更新
			if(newNetSensor.getState() == MoNetSensor.STOP_STATE && sensor.getState() == MoNetSensor.ALARM_STATE){
				newNetSensor.setState(sensor.getState());
			}
			moNetSensorMapper.updateByPrimaryKeySelective(newNetSensor);
		}
		
	}
}
