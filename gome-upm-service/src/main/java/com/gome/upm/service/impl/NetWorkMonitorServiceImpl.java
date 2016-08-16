package com.gome.upm.service.impl;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.gome.upm.common.Page;
import com.gome.upm.common.util.AppConfigUtil;
import com.gome.upm.dao.MoNetDeviceMapper;
import com.gome.upm.dao.MoNetHistoryMapper;
import com.gome.upm.dao.MoNetSensorMapper;
import com.gome.upm.domain.prtg.GroupIdc;
import com.gome.upm.domain.prtg.IndexTOP5;
import com.gome.upm.domain.prtg.MoNetDevice;
import com.gome.upm.domain.prtg.MoNetHistory;
import com.gome.upm.domain.prtg.MoNetSensor;
import com.gome.upm.domain.prtg.Sensor;
import com.gome.upm.domain.prtg.SensorChannel;
import com.gome.upm.domain.prtg.SensorDetail;
import com.gome.upm.domain.prtg.SensorDevice;
import com.gome.upm.domain.prtg.SensorHistoryData;
import com.gome.upm.service.NetWorkMonitorService;
import com.gome.upm.service.util.DBContextHolder;
import com.gome.upm.service.util.DateUtil;
import com.gome.upm.service.util.HttpUtil;
import com.gome.upm.service.util.PageUtil;


@Service("netWorkMonitorService")
//@Transactional
public class NetWorkMonitorServiceImpl implements NetWorkMonitorService {
	
	private final static Logger logger = LoggerFactory.getLogger(NetWorkMonitorServiceImpl.class);
	@Resource
	private MoNetDeviceMapper moNetDeviceMapper;
	@Resource
	private MoNetSensorMapper moNetSensorMapper;
	@Resource
	private MoNetHistoryMapper moNetHistoryMapper;
	
	public  String fun(String urlget) {
		return HttpUtil.fun(urlget);
	}

	
	
	@Override
	public String getHistoryXmlFromApi(Long sensorId, Integer avg, String sdate, String edate, Integer start, Integer count) {
		if(StringUtils.isEmpty(sdate)){
			sdate = DateUtil.formatTime2Str(LocalDateTime.now().plusHours(-2), "yyyy-MM-dd-HH-mm-ss");
		}
		if(StringUtils.isEmpty(edate)){
			edate = DateUtil.formatTime2Str(LocalDateTime.now(), "yyyy-MM-dd-HH-mm-ss");
		}
		
		if(avg == null){
			avg = 0;
		}
		
		String prourl = AppConfigUtil.getStringValue("prtg.api.url")+"api/historicdata.xml?sortby=-datetime&id="+sensorId+"&sdate="+sdate+"&edate="+edate+AppConfigUtil.getStringValue("prtg.api.auth")+"&avg="+avg;
		if(start != null){
			prourl = prourl +"&start="+start;
		}
		if(count != null){
			prourl = prourl +"&count="+count;
		}
		logger.info("get history url::"+prourl);
		String fun = fun(prourl);
		return fun;
	}
	
	
	
	/**
	 * 处理流量类传感器
	 * @param xml
	 * @return
	 */
	private List<SensorHistoryData> dealFlowHistory(String xml){
		// 读取并解析XML文档
		// 下面的是通过解析xml字符串的
		
		List<SensorHistoryData> list = new ArrayList<SensorHistoryData>();
		Document doc = null;
		try {
			//SAXReader reader = new SAXReader();
			doc = DocumentHelper.parseText(xml);
			Element rootElt = doc.getRootElement(); // 获取根节点
			Iterator<Element> itemsIt = rootElt.elementIterator("item");
			while(itemsIt.hasNext()){
				SensorHistoryData shd = new SensorHistoryData();
				Element itemIt = (Element) itemsIt.next();
				Node nodeCommunicationRoll = itemIt.selectSingleNode("value[@channel='通信量合计 (卷)']");
				Node nodeCommunicationRollValue = itemIt.selectSingleNode("value_raw[@channel='通信量合计 (卷)']");
				Node nodeCommunicationSpeed = itemIt.selectSingleNode("value[@channel='通信量合计 (速度)']");
				Node nodeCommunicationSpeedValue = itemIt.selectSingleNode("value_raw[@channel='通信量合计 (速度)']");
				Node nodeInCommunicationRoll = itemIt.selectSingleNode("value[@channel='入站通信量 (卷)']");
				Node nodeInCommunicationRollValue = itemIt.selectSingleNode("value_raw[@channel='入站通信量 (卷)']");
				Node nodeInCommunicationSpeed = itemIt.selectSingleNode("value[@channel='入站通信量 (速度)']");
				Node nodeInCommunicationSpeedValue = itemIt.selectSingleNode("value_raw[@channel='入站通信量 (速度)']");
				Node nodeOutCommunicationRoll = itemIt.selectSingleNode("value[@channel='出站通信量 (卷)']");
				Node nodeOutCommunicationRollValue = itemIt.selectSingleNode("value_raw[@channel='出站通信量 (卷)']");
				Node nodeOutCommunicationSpeed = itemIt.selectSingleNode("value[@channel='出站通信量 (速度)']");
				Node nodeOutCommunicationSpeedValue = itemIt.selectSingleNode("value_raw[@channel='出站通信量 (速度)']");
				Node nodeHaltTime = itemIt.selectSingleNode("value[@channel='停机时间']");
				Node nodeHaltTimeValue = itemIt.selectSingleNode("value_raw[@channel='停机时间']");
				Node nodeDateTime = itemIt.selectSingleNode("datetime");
				Node nodeDateTimeRaw = itemIt.selectSingleNode("datetime_raw");
				Node nodeCoverage = itemIt.selectSingleNode("coverage");
				Node nodeCoverageRaw = itemIt.selectSingleNode("coverage_raw");
				shd.setCommunication_roll(nodeCommunicationRoll == null ? "" : nodeCommunicationRoll.getText());
				shd.setCommunication_roll_value(nodeCommunicationRollValue == null ? "" : nodeCommunicationRollValue.getText());
				shd.setCommunication_speed(nodeCommunicationSpeed == null ? "" : nodeCommunicationSpeed.getText());
				shd.setCommunication_speed_value(nodeCommunicationSpeedValue == null ? "" : nodeCommunicationSpeedValue.getText());
				shd.setCoverage(nodeCoverage == null ? "" : nodeCoverage.getText());
				shd.setCoverage_raw(nodeCoverageRaw == null ? "" : nodeCoverageRaw.getText());
				shd.setDatetime(nodeDateTime == null ? "" : nodeDateTime.getText());
				shd.setDatetime_raw(nodeDateTimeRaw == null ? "" : nodeDateTimeRaw.getText());
				shd.setHalt_time(nodeHaltTime == null ? "" : nodeHaltTime.getText());
				shd.setHalt_time_value(nodeHaltTimeValue == null ? "" : nodeHaltTimeValue.getText());
				shd.setIn_communication_roll(nodeInCommunicationRoll == null ? "" : nodeInCommunicationRoll.getText());
				shd.setIn_communication_roll_value(nodeInCommunicationRollValue == null ? "" : nodeInCommunicationRollValue.getText());
				shd.setIn_communication_speed(nodeInCommunicationSpeed == null ? "" : nodeInCommunicationSpeed.getText());
				shd.setIn_communication_speed_value(nodeInCommunicationSpeedValue == null ? "" : nodeInCommunicationSpeedValue.getText());
				shd.setOut_communication_roll(nodeOutCommunicationRoll == null ? "" : nodeOutCommunicationRoll.getText());
				shd.setOut_communication_roll_value(nodeOutCommunicationRollValue == null ? "" : nodeOutCommunicationRollValue.getText());
				shd.setOut_communication_speed(nodeOutCommunicationSpeed == null ? "" : nodeOutCommunicationSpeed.getText());
				shd.setOut_communication_speed_value(nodeOutCommunicationSpeedValue == null ? "" : nodeOutCommunicationSpeedValue.getText());
				list.add(shd);
			}
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 将字符串转为XML
		Collections.reverse(list);
		return list;
	}
	
	/***
	 * 处理mem cpu等传感器
	 * @param xml
	 * @return
	 */
	private List<SensorHistoryData> dealOtherHistory(String xml){
		List<SensorHistoryData> list = new ArrayList<SensorHistoryData>();
		Document doc = null;
		try {
			//SAXReader reader = new SAXReader();
			doc = DocumentHelper.parseText(xml);
			Element rootElt = doc.getRootElement(); // 获取根节点
			Iterator<Element> itemsIt = rootElt.elementIterator("item");
			while(itemsIt.hasNext()){
				SensorHistoryData shd = new SensorHistoryData();
				Element itemIt = (Element) itemsIt.next();
				Node nodeCpuMemVal = itemIt.selectSingleNode("value[@channel='值']");
				Node nodeCpuMemValRaw = itemIt.selectSingleNode("value_raw[@channel='值']");
				Node nodeHaltTime = itemIt.selectSingleNode("value[@channel='停机时间']");
				Node nodeHaltTimeValue = itemIt.selectSingleNode("value_raw[@channel='停机时间']");
				Node nodeDateTime = itemIt.selectSingleNode("datetime");
				Node nodeDateTimeRaw = itemIt.selectSingleNode("datetime_raw");
				Node nodeCoverage = itemIt.selectSingleNode("coverage");
				Node nodeCoverageRaw = itemIt.selectSingleNode("coverage_raw");
				shd.setCoverage(nodeCoverage == null ? null : nodeCoverage.getText());
				shd.setCoverage_raw(nodeCoverageRaw == null ? null : nodeCoverageRaw.getText());
				shd.setDatetime(nodeDateTime == null ? null : nodeDateTime.getText());
				shd.setDatetime_raw(nodeDateTimeRaw  == null ? null : nodeDateTimeRaw.getText());
				shd.setHalt_time(nodeHaltTime  == null ? null : nodeHaltTime.getText());
				shd.setHalt_time_value(nodeHaltTimeValue == null ? null : nodeHaltTimeValue.getText());
				shd.setCpuMemVal(nodeCpuMemVal == null ? null : nodeCpuMemVal.getText());
				shd.setCpuMemValRaw(nodeCpuMemValRaw == null ? null : nodeCpuMemValRaw.getText());
				list.add(shd);
			}
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 将字符串转为XML

		return list;
	}

	@SuppressWarnings("rawtypes")
	public List<SensorHistoryData> getHistoryDataFromApi(String xml, String type) {
		List<SensorHistoryData> list = new ArrayList<SensorHistoryData>();
		if (StringUtils.isEmpty(xml) || StringUtils.isEmpty(type)) {
			return list;
		}
		
		switch(type){
		 case "flow" :
			 list = this.dealFlowHistory(xml);
			 break;
		 case "other" :
			 list = this.dealOtherHistory(xml);
			 break;
	     default:
	    	 break;
		}
		return list;
	}

	public SensorDetail getSensorDetail(String xml) {
		SensorDetail sd = new SensorDetail();
		if (StringUtils.isEmpty(xml)) {
			return sd;
		}
		Document doc = null;
		try {

			// 读取并解析XML文档
			// 下面的是通过解析xml字符串的
			doc = DocumentHelper.parseText(xml); // 将字符串转为XML
			Element rootElt = doc.getRootElement(); // 获取根节点
			System.out.println("根节点：" + rootElt.getName()); // 拿到根节点的名称
			sd.setName(rootElt.elementTextTrim("name"));
			sd.setSensorType(rootElt.elementTextTrim("sensortype"));
			sd.setInterval(rootElt.elementTextTrim("interval"));
			sd.setProbeName(rootElt.elementTextTrim("probename"));
			sd.setParentGroupName(rootElt.elementTextTrim("parentgroupname"));
			sd.setParentDeviceName(rootElt.elementTextTrim("parentdevicename"));
			sd.setParentDeviceId(rootElt.elementTextTrim("parentdeviceid"));
			sd.setLastValue(rootElt.elementTextTrim("lastvalue"));
			sd.setLastMessage(rootElt.elementTextTrim("lastmessage"));
			sd.setFavorite(rootElt.elementTextTrim("favorite"));
			sd.setStatusText(rootElt.elementTextTrim("statustext"));
			sd.setStatusId(rootElt.elementTextTrim("statusid"));
			String lastUp = rootElt.elementTextTrim("lastup");
			if(!StringUtils.isEmpty(lastUp) && !lastUp.contains("-") && !lastUp.contains("<")){
				lastUp = lastUp.substring(lastUp.indexOf("[")+1, lastUp.lastIndexOf("]"));
			}
			sd.setLastUp(lastUp);
			sd.setLastDown(rootElt.elementTextTrim("lastdown"));
			String lastcheck = rootElt.elementTextTrim("lastcheck");
			
			if(!StringUtils.isEmpty(lastcheck) && !lastcheck.contains("-") && !lastcheck.contains("<")){
				lastcheck = lastcheck.substring(lastcheck.indexOf("[")+1, lastcheck.lastIndexOf("]"));
			}
			sd.setLastCheck(lastcheck);
			sd.setUpTime(rootElt.elementTextTrim("uptime"));
			sd.setUpTimeTime(rootElt.elementTextTrim("uptimetime"));
			sd.setDownTime(rootElt.elementTextTrim("downtime"));
			sd.setDownTimeTime(rootElt.elementTextTrim("downtimetime"));
			sd.setUpDownTotal(rootElt.elementTextTrim("updowntotal"));
			String updownsince = rootElt.elementTextTrim("updownsince");
			if(!StringUtils.isEmpty(updownsince) && !updownsince.contains("-") && !updownsince.contains("<")){
				updownsince = updownsince.substring(updownsince.indexOf("[")+1, updownsince.lastIndexOf("]"));
			}
			sd.setUpDownSince(updownsince);

		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return sd;
	}
	
	
	
	public String getHistoryToPortDetail(Long sensorId, String tab, Integer pageNo, Integer pageSize ,String type){
	    String edate = DateUtil.formatTime2Str(LocalDateTime.now(), "yyyy-MM-dd-HH-mm-ss");//当前时间
		String sdate = "";//两小时之前
		int avg = 0;
		switch(tab){
			case "sceneData" : 
				sdate=DateUtil.formatTime2Str(LocalDateTime.now().plusHours(-2), "yyyy-MM-dd-HH-mm-ss");
				avg = 60;
				break;
			case "twoDay" :
				sdate=DateUtil.formatTime2Str(LocalDateTime.now().plusDays(-2), "yyyy-MM-dd-HH-mm-ss"); 
				avg = 300;
				break;
			case "thirtyDay" : 
				sdate=DateUtil.formatTime2Str(LocalDateTime.now().plusDays(-30), "yyyy-MM-dd-HH-mm-ss"); 
				avg = 60*60;
				break;
			case "oneYear" : 
				sdate=DateUtil.formatTime2Str(LocalDateTime.now().plusDays(-365), "yyyy-MM-dd-HH-mm-ss");
				avg = 24*60*60;
				break;
			default : 
				sdate=DateUtil.formatTime2Str(LocalDateTime.now().plusHours(-4), "yyyy-MM-dd-HH-mm-ss"); 
				avg = 60;
				break;
		}
		
		String resXml = this.getHistoryXmlFromApi(sensorId, avg, sdate, edate, (pageNo-1)*pageSize, pageSize);
		return resXml;
	}

	/**
	 * 解析sensor channelv
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SensorChannel> getSensorChannel(String xml, String type) {
		List<SensorChannel> list = new ArrayList<SensorChannel>();
		if (StringUtils.isEmpty(xml)) {
			return list;
		}

		SensorChannel channel = null;
		try {
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			List<Element> listItem = root.elements("item");
			for (Element ele : listItem) {
				channel = new SensorChannel();
				List<Element> childList = ele.elements();
				if (childList.size() < 3) {
					break;
				}
				channel.setName(childList.get(0).getTextTrim());
				channel.setLastValue(childList.get(1).getTextTrim());
				channel.setLastValueRaw(childList.get(2).getTextTrim());
				if(childList.size() == 5){
					channel.setLastValueSpeed(childList.get(3).getTextTrim());
					channel.setLastValueRawSpeed(childList.get(4).getTextTrim());
				}

				list.add(channel);
			}

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;

	}



	@Override
	public List<SensorDevice> getDevice(String groupId, String start, String count, String columns) {
		String prourl = AppConfigUtil.getStringValue("prtg.api.url")+"api/table.json?content=devices&output=json"+AppConfigUtil.getStringValue("prtg.api.auth");
		if (!StringUtils.isEmpty(groupId)) {
			prourl = prourl + "&id=" + groupId;
		}

		if (!StringUtils.isEmpty(start)) {
			prourl = prourl + "&start=" + start;
		}

		if (!StringUtils.isEmpty(count)) {
			prourl = prourl + "&count=" + count;
		}
		
		if(StringUtils.isEmpty(columns)){
			prourl = prourl + "&columns=objid";
		}else{
			prourl = prourl + "&columns=objid,"+columns;
		}

		String fun = fun(prourl);
		JSONArray jsonArray = JSON.parseObject(fun).getJSONArray("devices");
		List<SensorDevice> list = JSON.parseArray(jsonArray.toJSONString(), SensorDevice.class);

		return list;
	}

	@Override
	public List<Sensor> getSensors(String deviceId, String start, String count, String columns) {
		//String prourl = AppConfigUtil.getStringValue("prtg.api.url")+"api/table.json?content=sensors&output=json&columns=objid,probe,group,device,sensor,status,message,lastvalue,priority,favorite"+AppConfigUtil.getStringValue("prtg.api.auth");
		String prourl = AppConfigUtil.getStringValue("prtg.api.url")+"api/table.json?content=sensors&output=json"+AppConfigUtil.getStringValue("prtg.api.auth");
		if (!StringUtils.isEmpty(deviceId)) {
			prourl = prourl + "&id=" + deviceId;
		}

		if (!StringUtils.isEmpty(start)) {
			prourl = prourl + "&start=" + start;
		}

		if (!StringUtils.isEmpty(count)) {
			prourl = prourl + "&count=" + count;
		}
		
		if(StringUtils.isEmpty(columns)){
			prourl = prourl + "&columns=objid";
		}else{
			prourl = prourl + "&columns=objid,"+columns;
		}

		String fun = fun(prourl);
		JSONArray jsonArray = JSON.parseObject(fun).getJSONArray("sensors");
		List<Sensor> list = JSON.parseArray(jsonArray.toJSONString(), Sensor.class);
		return list;
	}

	@Override
	public List<SensorChannel> getSensorChannels(String sensorId, String start, String count, String type) {
		String prourl = AppConfigUtil.getStringValue("prtg.api.url")+"api/table.xml?content=channels&output=xml&columns=name,lastvalue_"+AppConfigUtil.getStringValue("prtg.api.auth");
		if (!StringUtils.isEmpty(sensorId)) {
			prourl = prourl + "&id=" + sensorId;
		}

		if (!StringUtils.isEmpty(start)) {
			prourl = prourl + "&start=" + start;
		}

		if (!StringUtils.isEmpty(count)) {
			prourl = prourl + "&count=" + count;
		}
		String fun = fun(prourl);
		List<SensorChannel> list = getSensorChannel(fun, type);
		return list;
	}

	@Override
	public SensorDetail getsensordetails(String sensorId) {
		 String prourl = AppConfigUtil.getStringValue("prtg.api.url")+"api/getsensordetails.xml?id="+sensorId+AppConfigUtil.getStringValue("prtg.api.auth");
		 String fun = fun(prourl);
		 SensorDetail sd = getSensorDetail(fun);
		 sd.setObjId(sensorId);
		 if(matchFlow(sd.getName())){
			sd.setType("flow");
		 }else{
			sd.setType("other");
		 }
		 sd.setPriority(this.getPrio(sensorId));
		 return sd;
	}

	@Override
	public Map<String, Object> index() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<SensorDevice> deviceList = this.getDevice(null, null, null, "device,host");
		List<SensorDevice> errorDevice = new ArrayList<SensorDevice>();
		List<IndexTOP5> cpuSensor = new ArrayList<IndexTOP5>();//cpu探针集合
		List<IndexTOP5> memorySensor = new ArrayList<IndexTOP5>();//内存探针集合
		List<IndexTOP5> flowSensor = new ArrayList<IndexTOP5>();//端口流量探针集合
		int errorCount = 0;
		for(SensorDevice device : deviceList){
			int errorNum = 0;
			IndexTOP5 top5 = null;
			//获取device下sensors列表
			List<Sensor> sensorsList = this.getSensors(device.getObjId(), null, null,"sensor,lastvalue");
			
			for(Sensor sensor : sensorsList){
				top5 = new IndexTOP5();
				String lastVal = sensor.getLastvalue();
				if(StringUtils.isEmpty(lastVal) || lastVal.equals("-") || lastVal.startsWith("<")){
					top5.setLastVal((double) 0);
				}else{
					top5.setLastVal(Double.valueOf(lastVal.substring(0, lastVal.indexOf(" ")).replaceAll(",", "")));
				}
				top5.setDeviceId(device.getObjId());
				top5.setDeviceName(device.getDevice());
				top5.setSensorName(sensor.getSensor());
				top5.setSensorId(sensor.getObjId());
				top5.setHost(device.getHost());
				//统计出错设备
				if(StringUtils.isEmpty(sensor.getLastvalue())){
					//探针error
					errorNum++;
				}
				
				if(sensor.getSensor().toLowerCase().startsWith("cpu")){
					//cpu传感器
					cpuSensor.add(top5);
				}else if(sensor.getSensor().toLowerCase().startsWith("memory")){
					//内存传感器
					memorySensor.add(top5);
				}else{
					//端口流量传感器
					if(matchFlow(sensor.getSensor())){
						flowSensor.add(top5);
					}
					
				}
				
				
			}
			
			if(sensorsList.size() != 0 && errorNum  == sensorsList.size()){
				errorCount++;
				errorDevice.add(device);
			}
			
		}
		Collections.sort(cpuSensor);
		Collections.sort(memorySensor);
		Collections.sort(flowSensor);
		
		map.put("cpu", cpuSensor.size() < 5 ? cpuSensor : cpuSensor.subList(0, 5));
		map.put("memory", memorySensor.size() < 5 ? memorySensor : memorySensor.subList(0, 5));
		map.put("flow", flowSensor.size() < 5 ? flowSensor : flowSensor.subList(0, 5));
		map.put("errorCount", errorCount);//错误设备总数
		map.put("errorDevice", errorDevice);//错误设备列表
		map.put("totalCount", deviceList.size());//全部设备数量
		return map;
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
	


	@Override
	public List<GroupIdc> getGroupIdc(String parentId, String start, String count, String columns) {
		 String prourl = AppConfigUtil.getStringValue("prtg.api.url")+"api/table.json?content=groups&output=json"+AppConfigUtil.getStringValue("prtg.api.auth");
		 if(StringUtils.isEmpty(columns)){
			prourl = prourl + "&columns=objid";
		 }else{
			prourl = prourl + "&columns=objid,"+columns;
		 }
		 String fun = fun(prourl);
		 JSONArray jsonArray = JSON.parseObject(fun).getJSONArray("groups");
		 List<GroupIdc> list = JSON.parseArray(jsonArray.toJSONString(), GroupIdc.class);
		 return list;
	}

	@Override
	public List<SensorDevice> getDevice2Ajax(String groupId, String deviceId, String deviceStatus) {
		List<SensorDevice> list = this.getDevice(groupId, null, null,"group,device,downsens,upsens");
		List<SensorDevice> listReturn = new ArrayList<SensorDevice>();
		for(SensorDevice device : list){
			
			if(!StringUtils.isEmpty(deviceId)){
				if(!device.getObjId().equals(deviceId)){
					continue;
				}
			}
			
			
			if(!StringUtils.isEmpty(deviceStatus)){
				//正常，过滤掉异常的
				if(deviceStatus.equals("1")){
					if(device.getUpsensRaw().equals("0") && !device.getDownsensRaw().equals("0")){
						continue;
					}
				}
				
				//异常，过滤掉正常的
				if(deviceStatus.equals("2")){
					if(!device.getUpsensRaw().equals("0") || (device.getUpsensRaw().equals("0") && device.getDownsensRaw().equals("0"))){
						continue;
					}
				}

			}
			
			listReturn.add(device);
		}
		return listReturn;
	}

	@Override
	public SensorDevice getDeviceById(String deviceId) {
		List<SensorDevice> list = this.getDevice(null, null, null, "group,device,downsens,,upsens,warnsens,pausedsens,unusualsens");
		for(SensorDevice device : list){
			if(device.getObjId().equals(deviceId)){
				int normalCount = Integer.parseInt(device.getUpsensRaw());
				int totalCount = Integer.parseInt(device.getWarnsensRaw())+Integer.parseInt(device.getDownsensRaw())+Integer.parseInt(device.getUpsensRaw())+Integer.parseInt(device.getPausedsensRaw())+Integer.parseInt(device.getUnusualsensRaw());
				device.setNormalSensor(normalCount);
				device.setTotalSensor(totalCount);
				device.setPriority(this.getPrio(deviceId));
				device.setStatus(this.getDeviceStatus(deviceId));
				return device;
				
			}
		}
		return new SensorDevice();
	}
	
	
	@Override
	public MoNetDevice getDeviceByIdFromDb(Integer deviceId) {
		DBContextHolder.setDataSource("dataSourceOne");
		MoNetDevice device = moNetDeviceMapper.selectByPrimaryKey(deviceId);
		device.setStatus(this.getDeviceStatus(deviceId+""));
		device.setPriority(this.getPrio(deviceId+""));
		return device;
	}
	/**
	 * 获取设备优先级
	 * @param deviceId
	 * @return
	 */
	private int getPrio(String objId){
		String priority = this.getObjectProperty(objId, "priority");//获取设备优先级
		int prio = 0;
		while(priority.indexOf("class=favstar")!=-1){
			prio++;
			  //将字符串出现class=favstar的位置之前的全部截取掉
			priority = priority.substring(priority.indexOf("class=favstar")+"class=favstar".length());
		} 
		return prio;
	}
	
	/**
	 * 获取设备状态
	 * @param deviceId
	 * @return
	 */
	private String getDeviceStatus(String deviceId){
		String message = this.getObjectProperty(deviceId, "message");//获取设备状态
		if(!StringUtils.isEmpty(message)){
			return message.substring(message.indexOf("<result>")+8,message.indexOf("</result>"));
		}
		return "";
	}

	@Override
	public String getObjectProperty(String id, String name) {
		 String prourl = AppConfigUtil.getStringValue("prtg.api.url")+"api/getobjectstatus.htm?id="+id+"&name="+name+"&show=text"+AppConfigUtil.getStringValue("prtg.api.auth");;
		 String fun = fun(prourl);
		return fun;
	}
	
	
    public static void main(String[] args) {
    	NetWorkMonitorServiceImpl nmsi = new NetWorkMonitorServiceImpl();
    	int prio = nmsi.getPrio("10000");
    	System.out.println(prio);
	}


	@Override
	public Page<MoNetDevice> getDevicePage(Page<MoNetDevice> page) {
		DBContextHolder.setDataSource("dataSourceOne");
		List<MoNetDevice> list = moNetDeviceMapper.selectList(page);
		for(MoNetDevice device : list){
			String alarmTime = device.getAlarmTimeStr();
			if(!StringUtils.isEmpty(alarmTime)){
				device.setAlarmTimeStr(alarmTime.substring(0, alarmTime.lastIndexOf(".")));
			}
		}
		Integer count = moNetDeviceMapper.selectCount(page);
		if(count == null){
			count = 0;
		}
		return new Page<MoNetDevice>(page.getPageNo(), page.getPageSize(), count, list, page.getConditions());
	}


	@Override
	public Page<MoNetSensor> getSensorPage(Page<MoNetSensor> page) {
		DBContextHolder.setDataSource("dataSourceOne");
		List<MoNetSensor> list = moNetSensorMapper.selectList(page);
//		for(MoNetSensor sensor : list){
//			String alarmTime = sensor.getAlarmTimeStr();
//			sensor.setAlarmTimeStr(alarmTime.substring(0, alarmTime.lastIndexOf(".")));
//		}
		Integer count = moNetSensorMapper.selectCount(page);
		if(count == null){
			count = 0;
		}
		return new Page<MoNetSensor>(page.getPageNo(), page.getPageSize(), count, list, page.getConditions());
	}
	
	@Override
	public Page<MoNetSensor> getSensorPageAlarm(Page<MoNetSensor> page) {
		DBContextHolder.setDataSource("dataSourceOne");
		List<MoNetSensor> list = moNetSensorMapper.selectListAlarm(page);
		for(MoNetSensor sensor : list){
			String alarmTime = sensor.getAlarmTimeStr();
			if(!StringUtils.isEmpty(alarmTime)){
				sensor.setAlarmTimeStr(alarmTime.substring(0, alarmTime.lastIndexOf(".")));
			}
			
		}
		Integer count = moNetSensorMapper.selectCountAlarm(page);
		if(count == null){
			count = 0;
		}
		return new Page<MoNetSensor>(page.getPageNo(), page.getPageSize(), count, list, page.getConditions());
	}


	
	@Override
	public Map<String,Object> getStartHistoryDetails(Long sensorId, String sdate, String edate, Integer avgChart, String type) {
		Map<String,Object> map = new HashMap<String,Object>();
		long end = DateUtil.getLongTimeFromStr(edate, "yyyy-MM-dd HH:mm:ss");
		long start = DateUtil.getLongTimeFromStr(sdate, "yyyy-MM-dd HH:mm:ss");
		Integer avg = this.getHistoryChartToStartAvg(sdate, edate).intValue();
		
		String resXml = this.getHistoryChartToStart(sensorId, sdate, edate, avg);
		List<SensorHistoryData> historyList = new ArrayList<SensorHistoryData>();
		if(resXml.equals("429")){
			map.put("list", "429");
		}else{
			historyList = this.getHistoryDataFromApi(resXml, type);
			map.put("list", historyList);
		}
		int commuAVGTotal = 0;// 通信量平均值
		int commuTotal = 0;// 通信量合计int
		int haultTimev = 0;
		int cpuMemValTotal = 0;
		
		for (SensorHistoryData data : historyList) {
			String CommunicationRoll = data.getCommunication_roll();// 通信量合计
			String CommunicationSpeed = data.getCommunication_speed();// 通信量合计速度
			String cpuMemVal = data.getCpuMemVal();
			String haultTime = data.getHalt_time();// 停机时间
			if (!StringUtils.isBlank(haultTime) && !haultTime.contains("<")) {
				haultTimev = haultTimev + ((Integer.parseInt(haultTime.split(" ")[0].replaceAll(",", "")))*avg)/100;
			}
			if (!StringUtils.isBlank(CommunicationRoll) && !CommunicationRoll.contains("<")) {
					commuTotal = commuTotal + Integer
							.parseInt(CommunicationRoll.split(" ")[0].replaceAll(",", ""));
				}
				if (!StringUtils.isBlank(CommunicationSpeed) && !CommunicationSpeed.contains("<")) {
					//System.out.println("CommunicationSpeed:"+CommunicationSpeed);
					commuAVGTotal = commuAVGTotal + Integer
							.parseInt(CommunicationSpeed.split(" ")[0].replaceAll(",", ""));
				}

			if (!StringUtils.isBlank(cpuMemVal) && !cpuMemVal.contains("<")) {
					cpuMemValTotal = cpuMemValTotal + Integer
							.parseInt(cpuMemVal.split(" ")[0].replaceAll(",", ""));
				}
		}
		long totalTime = end-start;
		long normalTime = totalTime-haultTimev;
		map.put("commuAVG", historyList.size() == 0 ? 0 : commuAVGTotal/historyList.size());
		map.put("commuTotal", historyList.size() == 0 ? 0 : commuTotal);
		map.put("cpuMemVal", historyList.size() == 0 ? 0 : cpuMemValTotal/historyList.size());
		map.put("haultTime", historyList.size() == 0 ? 0 : this.longtoStrTimelength(haultTimev));
		map.put("normalTime", historyList.size() == 0 ? 0 : this.longtoStrTimelength(normalTime));
		map.put("normalTimePercent", normalTime*100/totalTime);
		map.put("haultTimePercent", haultTimev*100/totalTime);
		
		return map;
	}

	private String longtoStrTimelength(long time){
		long oneday = 24*60*60*1000;
		long oneHour = 60*60*1000;
		long oneMinu = 60*1000;
		long dayCount = 0;
		long hourCount = 0;
		long minuCount = 0;
		long seconCount = 0;
		
		dayCount = time / oneday;
		time = time - dayCount*oneday;
		hourCount = time / oneHour;
		time = time - hourCount*oneHour;
		minuCount = time % oneMinu;
		time = time - minuCount*oneMinu;
		seconCount = time/1000;
		
		return dayCount+"天"+hourCount+"小时"+minuCount+"分钟"+seconCount+"秒";
	}

	@Override
	public Map<String, Object> index2() {
		DBContextHolder.setDataSource("dataSourceOne");
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> mapSensorCount = new HashMap<String,Object>();
		Integer totalSensorCount = moNetSensorMapper.selectCountByMap(mapSensorCount);
		if(totalSensorCount == null){
			totalSensorCount = 0;
		}
		Integer totalDeviceCount = moNetDeviceMapper.selectCountByMap(mapSensorCount);
		if(totalDeviceCount == null){
			totalDeviceCount = 0;
		}
		mapSensorCount.put("state", 0);
		Integer normalSensorCount = moNetSensorMapper.selectCountByMap(mapSensorCount);
		if(normalSensorCount == null){
			normalSensorCount = 0;
		}
		Integer normalDeviceCount = moNetDeviceMapper.selectCountByMap(mapSensorCount);
		if(normalDeviceCount == null){
			normalDeviceCount = 0;
		}

		Map<String,Object> mapTop5 = new HashMap<String,Object>();
		mapTop5.put("sort", "top5");
		mapTop5.put("offset", 0);
		mapTop5.put("limit", 5);
		mapTop5.put("type", "cpu");
		List<MoNetSensor> cpuList = moNetSensorMapper.selectListByMap(mapTop5);
		mapTop5.put("type", "mem");
		List<MoNetSensor> memList = moNetSensorMapper.selectListByMap(mapTop5);
		mapTop5.put("type", "flow");
		List<MoNetSensor> flowList = moNetSensorMapper.selectListByMap(mapTop5);
		map.put("cpu", cpuList);
		map.put("memory", memList);
		map.put("flow", flowList);
		map.put("errorSensorCount", totalSensorCount-normalSensorCount);
		map.put("totalSensorCount", totalSensorCount);
		map.put("errorDeviceCount", totalDeviceCount-normalDeviceCount);
		map.put("totalDeviceCount", totalDeviceCount);
		return map;
	}


	@Override
	public List<MoNetHistory> getSummaryFromApi(String sensorId) {
		String edate = DateUtil.formatTime2Str(LocalDateTime.now(), "yyyy-MM-dd-HH-mm-ss");
		String sdate = DateUtil.formatTime2Str(LocalDateTime.now().plusHours(-3), "yyyy-MM-dd-HH-mm-ss");
		String  avg = "900";
		String count = "12";
		String prourl = AppConfigUtil.getStringValue("prtg.api.url")+"api/historicdata.xml?sortby=-datetime&id="+sensorId+"&sdate="+sdate+"&edate="+edate+AppConfigUtil.getStringValue("prtg.api.auth")+"&avg="+avg+"&count="+count;
		logger.info("get history url::"+prourl);
		String fun = fun(prourl);
		if(org.springframework.util.StringUtils.isEmpty(fun) || fun.equals("429")){
			return new ArrayList<MoNetHistory>();
		}
		List<MoNetHistory> list = this.dealSummaryFromApi(fun);
		return list;
	}

	
	private List<MoNetHistory> dealSummaryFromApi(String xml){
		// 读取并解析XML文档
		// 下面的是通过解析xml字符串的
		
		List<MoNetHistory> list = new ArrayList<MoNetHistory>();
		Document doc = null;
		try {
			//SAXReader reader = new SAXReader();
			doc = DocumentHelper.parseText(xml);
			Element rootElt = doc.getRootElement(); // 获取根节点
			Iterator<Element> itemsIt = rootElt.elementIterator("item");
			while(itemsIt.hasNext()){
				MoNetHistory history = new MoNetHistory();
				Element itemIt = (Element) itemsIt.next();
				Node nodeCommunicationRoll = itemIt.selectSingleNode("value[@channel='通信量合计 (卷)']");
				Node nodeCommunicationRollValue = itemIt.selectSingleNode("value_raw[@channel='通信量合计 (卷)']");
				Node nodeCommunicationSpeed = itemIt.selectSingleNode("value[@channel='通信量合计 (速度)']");
				Node nodeCommunicationSpeedValue = itemIt.selectSingleNode("value_raw[@channel='通信量合计 (速度)']");
				Node nodeInCommunicationRoll = itemIt.selectSingleNode("value[@channel='入站通信量 (卷)']");
				Node nodeInCommunicationRollValue = itemIt.selectSingleNode("value_raw[@channel='入站通信量 (卷)']");
				Node nodeInCommunicationSpeed = itemIt.selectSingleNode("value[@channel='入站通信量 (速度)']");
				Node nodeInCommunicationSpeedValue = itemIt.selectSingleNode("value_raw[@channel='入站通信量 (速度)']");
				Node nodeOutCommunicationRoll = itemIt.selectSingleNode("value[@channel='出站通信量 (卷)']");
				Node nodeOutCommunicationRollValue = itemIt.selectSingleNode("value_raw[@channel='出站通信量 (卷)']");
				Node nodeOutCommunicationSpeed = itemIt.selectSingleNode("value[@channel='出站通信量 (速度)']");
				Node nodeOutCommunicationSpeedValue = itemIt.selectSingleNode("value_raw[@channel='出站通信量 (速度)']");
				Node nodeHaltTime = itemIt.selectSingleNode("value[@channel='停机时间']");
				Node nodeHaltTimeValue = itemIt.selectSingleNode("value_raw[@channel='停机时间']");
				Node nodeDateTime = itemIt.selectSingleNode("datetime");
				Node nodeDateTimeRaw = itemIt.selectSingleNode("datetime_raw");
				Node nodeCoverage = itemIt.selectSingleNode("coverage");
				Node nodeCoverageRaw = itemIt.selectSingleNode("coverage_raw");
				history.setCommunicationRoll(nodeCommunicationRoll == null ? "" : nodeCommunicationRoll.getText());
				history.setCommunicationRollValue(nodeCommunicationRollValue == null ? "" : nodeCommunicationRollValue.getText());
				history.setCommunicationSpeed(nodeCommunicationSpeed == null ? "" : nodeCommunicationSpeed.getText());
				history.setCommunicationSpeedValue(nodeCommunicationSpeedValue == null ? "" : nodeCommunicationSpeedValue.getText());
				history.setCoverage(nodeCoverage == null ? "" : nodeCoverage.getText());
				history.setCoverageRaw(nodeCoverageRaw == null ? "" : nodeCoverageRaw.getText());
				history.setCpuMemVal("");
				history.setCpuMemValRaw("");
				history.setDateTime(nodeDateTime == null ? "" : nodeDateTime.getText());
				history.setDateTimeRaw(nodeDateTimeRaw == null ? "" : nodeDateTimeRaw.getText());
				history.setHaltTime(nodeHaltTime == null ? "" : nodeHaltTime.getText());
				history.setHaltTimeValue(nodeHaltTimeValue == null ? "" : nodeHaltTimeValue.getText());
				history.setInCommunicationRoll(nodeInCommunicationRoll == null ? "" : nodeInCommunicationRoll.getText());
				history.setInCommunicationRollValue(nodeInCommunicationRollValue == null ? "" : nodeInCommunicationRollValue.getText());
				history.setInCommunicationSpeed(nodeInCommunicationSpeed == null ? "" : nodeInCommunicationSpeed.getText());
				history.setCommunicationSpeedValue(nodeInCommunicationSpeedValue == null ? "" : nodeInCommunicationSpeedValue.getText());
				history.setOutCommunicationRoll(nodeOutCommunicationRoll == null ? "" : nodeOutCommunicationRoll.getText());
				history.setOutCommunicationRollValue(nodeOutCommunicationRollValue == null ? "" : nodeOutCommunicationRollValue.getText());
				history.setOutCommunicationSpeed(nodeOutCommunicationSpeed == null ? "" : nodeOutCommunicationSpeed.getText());
				history.setOutCommunicationSpeedValue(nodeOutCommunicationSpeedValue == null ? "" : nodeOutCommunicationSpeedValue.getText());
				list.add(history);
			}
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 将字符串转为XML
		//Collections.reverse(list);
		return list;
	}


	@Override
	public Map<String, Object> getSummaryFromDB(String sensorIds) {
		DBContextHolder.setDataSource("dataSourceOne");
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> mapParam = new HashMap<String,Object>();
		mapParam.put("offset", 12);
		String[] ids = sensorIds.split(",");
	    List<MoNetHistory> list = null;
		for(int i = 0; i < ids.length; i++){
			mapParam.put("sensorId", ids[i]);
			mapParam.put("sort", "dateTimeRaw");
			list = moNetHistoryMapper.selectByMap(mapParam);
			map.put("chart"+ids[i], list);
		}
		return map;
	}



	@Override
	public String getHistoryChartToPortDetail(Long sensorId, String tab, String type) {
		String edate = DateUtil.formatTime2Str(LocalDateTime.now(), "yyyy-MM-dd-HH-mm-ss");
		String sdate = "";
		//int count = 12;
		int avg = 1800;
		switch(tab){
			case "sceneData" : 
				sdate=DateUtil.formatTime2Str(LocalDateTime.now().plusHours(-2), "yyyy-MM-dd-HH-mm-ss"); 
			//	count = 24;
				avg = 300;
				break;
			case "twoDay" : 
				sdate=DateUtil.formatTime2Str(LocalDateTime.now().plusDays(-2), "yyyy-MM-dd-HH-mm-ss");
			//	count = 12;
				avg = 4*60*60;
				break;
			case "thirtyDay" : 
				sdate=DateUtil.formatTime2Str(LocalDateTime.now().plusDays(-30), "yyyy-MM-dd-HH-mm-ss"); 
			//	count = 30;
				avg = 24*60*60;
				break;
			case "oneYear" : 
				sdate=DateUtil.formatTime2Str(LocalDateTime.now().plusDays(-365), "yyyy-MM-dd-HH-mm-ss");
			//	count = 12;
				avg = 30*24*60*60;
				break;
			default : 
				sdate=DateUtil.formatTime2Str(LocalDateTime.now().plusHours(-4), "yyyy-MM-dd-HH-mm-ss");
			//	count = 6; 
				avg = 1800;
				break;
			
			}
		
		String resXml = this.getHistoryXmlFromApi(sensorId, avg, sdate, edate, null, null);
		return resXml;
	}



	@Override
	public String getHistoryToStart(Long sensorId, String sdate, String edate, Integer pageNo, Integer pageSize, Integer avg) {
		sdate = sdate.replaceAll(" ", "-").replaceAll(":", "-");
		edate = edate.replaceAll(" ", "-").replaceAll(":", "-");
		String resXml = this.getHistoryXmlFromApi(sensorId, avg, sdate, edate, (pageNo-1)*pageSize, pageSize);
		return resXml;

	}



	@Override
	public String getHistoryChartToStart(Long sensorId, String sdate, String edate, Integer avg) {
			//Long avg = this.getHistoryChartToStartAvg(sdate, edate);
			sdate = sdate.replaceAll(" ", "-").replaceAll(":", "-");
			edate = edate.replaceAll(" ", "-").replaceAll(":", "-");
	
			String resXml = this.getHistoryXmlFromApi(sensorId, avg, sdate, edate, null, null);
		return resXml;
	}

	
	public Long getHistoryChartToStartAvg(String sdate, String edate) {
		Long startTime = DateUtil.getLongTimeFromStr(sdate, "yyyy-MM-dd HH:mm:ss");
		Long endTime = DateUtil.getLongTimeFromStr(edate, "yyyy-MM-dd HH:mm:ss");
		Long oneDay = (long) (24*60*60*1000);
		Long avgHour = (endTime-startTime)/oneDay == 0 ? 1 : (endTime-startTime)/oneDay;
		Long avg = avgHour*60*60;
		return avg;
	}

}
