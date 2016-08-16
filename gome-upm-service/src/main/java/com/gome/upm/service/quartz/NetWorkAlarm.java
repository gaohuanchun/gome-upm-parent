package com.gome.upm.service.quartz;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import com.gome.upm.common.util.AppConfigUtil;
import com.gome.upm.common.web.httpClient.HttpClientUtils;
import com.gome.upm.dao.MoNetDeviceMapper;
import com.gome.upm.dao.MoNetSensorMapper;
import com.gome.upm.dao.MoNetSensorThresholdMapper;
import com.gome.upm.domain.prtg.MoNetDevice;
import com.gome.upm.domain.prtg.MoNetSensor;
import com.gome.upm.domain.prtg.MoNetSensorThreshold;
import com.gome.upm.domain.prtg.SensorChannel;
import com.gome.upm.service.NetWorkMonitorService;
import com.gome.upm.service.util.DateUtil;

public class NetWorkAlarm {
	// private Logger logger = Logger.getLogger(NetWorkAlarm.class);
	private static final Logger logger = LoggerFactory.getLogger(NetWorkAlarm.class);
	@Resource
	private NetWorkMonitorService netWorkMonitorService;
	@Resource
	private MoNetDeviceMapper moNetDeviceMapper;
	@Resource
	private MoNetSensorMapper moNetSensorMapper;
	@Resource
	private MoNetSensorThresholdMapper moNetSensorThresholdMapper;

	/**
	 * 6分钟后执行，频率10分钟
	 */
	@Scheduled(initialDelay=1000*360,fixedRate = 1000*600)
	//@Scheduled(initialDelay = 5000, fixedRate = 1000 * 600)
	public void work() {
		try {
			long start = System.currentTimeMillis();
			logger.info("网络监控报警start");

			this.alarmDevice();

			this.alarmSensor();

			long end = System.currentTimeMillis();
			logger.info("网络监控报警end,耗时(分钟):" + (end - start) / 60000);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void alarmDevice() {
		// 设备停机报警,获取停机状态设备
		MoNetDevice device = new MoNetDevice();
		device.setState(MoNetDevice.STOP_STATE);
		List<MoNetDevice> alarmDevicelist = moNetDeviceMapper.selectListCommon(device);
		for (MoNetDevice alarmDevice : alarmDevicelist) {
			alarmDevice.setState(MoNetDevice.ALARM_STATE);// 设置报警状态
			alarmDevice.setAlarmTime(new Date());
			alarmDevice.setAlarmContent("设备故障，下属传感器全部停机");
			// 发送报警信息
			String str = this.getDeviceAlarmContent(alarmDevice);
			boolean b = this.sendMessage("【网络报警】", str);
			if(!b){
				continue;
			}
			// 修改信息
			moNetDeviceMapper.updateByPrimaryKeySelective(alarmDevice);
		}
	}

	public void alarmSensor() {
		// 传感器报警
		List<MoNetSensorThreshold> list = moNetSensorThresholdMapper.selectAll2();
		for (MoNetSensorThreshold sensorThre : list) {
			//MoNetSensor sensor = moNetSensorMapper.selectByPrimaryKey(sensorThre.getSensorId());
			String alarmType = sensorThre.getAlarmType() == null ? "" : sensorThre.getAlarmType();
			Integer state = sensorThre.getState() == null ? 0 : sensorThre.getState();
			double threshold = sensorThre.getSensorThreshold() * 1024;
			List<SensorChannel> channelList = netWorkMonitorService.getSensorChannels(sensorThre.getSensorId() + "",
					null, null, "flow");
			double outFlow = 0;
			double inFlow = 0;
			DecimalFormat df = new DecimalFormat("#.00");
			for (SensorChannel channel : channelList) {
				String flow = channel.getLastValueSpeed();
				if (StringUtils.isEmpty(flow) || flow.contains("-") | flow.contains("<")) {
					flow = "0";
				} else {
					flow = flow.split(" ")[0].replaceAll(",", "");
				}
				if (channel.getName().equals("出站通信量")) {
					outFlow = Double.parseDouble(flow);
					// 出站
				} else if (channel.getName().equals("入站通信量")) {
					// 入站
					inFlow = Double.parseDouble(flow);
				}
			}

			//超过阀值
			if (outFlow >= threshold || inFlow >= threshold) {
				// 报警且以前 沒報過警

				if (state != MoNetSensorThreshold.ALARM_STATE && !alarmType.equals("flowOver")) {
					String alarmContent = "";
					if(outFlow >= threshold){
						alarmContent = "端口出站流量超出阀值当前为"+df.format(outFlow/1024)+"MB";
					}
					
					if(inFlow >= threshold){
						alarmContent = "端口入站流量超出阀值当前为"+df.format(inFlow/1024)+"MB";
					}
					
					if(outFlow >= threshold && inFlow >= threshold){
						alarmContent = "端口出入站流量超出阀值当前入站/出站流量分别为"+df.format(inFlow/1024)+"MB/"+df.format(outFlow/1024)+"MB";
					}
					
					String str = this.getSensorAlarmContent(sensorThre, alarmContent);
					boolean b = this.sendMessage("【网络报警】", str);
					if(!b){
						continue;
					}
					sensorThre.setState(MoNetSensorThreshold.ALARM_STATE);
					sensorThre.setAlarmTime(new Date());
					sensorThre.setAlarmType("flowOver");
					moNetSensorThresholdMapper.updateByPrimaryKeySelective(sensorThre);
				}
				
			} else {

				if (sensorThre.getSensorState() != null && sensorThre.getSensorState() == MoNetSensor.STOP_STATE) {
					if(state != MoNetSensorThreshold.ALARM_STATE && !alarmType.equals("shutDown")){
						String str = this.getSensorAlarmContent(sensorThre, "故障");
						boolean b = this.sendMessage("【网络报警】",str);
						if(!b){
							continue;
						}
						sensorThre.setState(MoNetSensorThreshold.ALARM_STATE);
						sensorThre.setAlarmTime(new Date());
						sensorThre.setAlarmType("shutDown");
						moNetSensorThresholdMapper.updateByPrimaryKeySelective(sensorThre);
					}

				} else {
					// 正常,重置报警
					if(sensorThre.getState() != MoNetSensorThreshold.NORMAL_STATE){
						sensorThre.setState(MoNetSensorThreshold.NORMAL_STATE);
						sensorThre.setAlarmTime(null);
						sensorThre.setAlarmType(null);
						moNetSensorThresholdMapper.updateByPrimaryKeySelective(sensorThre);
					}

				}

			}

		}

	}

	private boolean sendMessage(String subject, String content) {
		boolean b = false;
		String url = AppConfigUtil.getStringValue("prtg.alarm.url");
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("mail", "zhangzhixiang-ds@yolo24.com");
		paramMap.put("type", "network");
		paramMap.put("subject", subject);
		paramMap.put("content", content);
		try {
			logger.info("发送报警邮件开始,content:" + content);
			String result = HttpClientUtils.post(url, paramMap);
			logger.info("result:" + result);
			if (result != null && result.contains("ok")) {
				b = true;
				logger.info("报警邮件发送成功");
			} else {
				logger.info("报警邮件发送失败");
			}

			logger.info("发送报警邮件结束");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return b;
	}
	
	
	public String getDeviceAlarmContent(MoNetDevice device){
		StringBuffer buffer = new StringBuffer();
		LocalDateTime time = LocalDateTime.now();
		buffer.append("监控组，您好！</br></br>");
		buffer.append("设备 “<font face='arial' color='red'>"+device.getDeviceName()+"</font>”出现异常，请及时处理，谢谢。</br></br>");
		buffer.append("报警时间："+DateUtil.formatTime2Str(time, "yyyy-MM-dd HH:mm:ss")+"</br></br>");
		buffer.append("所属IDC："+device.getGroupName()+"</br></br>");
		buffer.append("设备：<font face='arial' color='red'>"+device.getDeviceName()+"</font></br></br>");
		buffer.append("状态：<font face='arial' color='red'>故障</font></br></br>");
		return buffer.toString();
	}
	
	
	public String getSensorAlarmContent(MoNetSensorThreshold sensorThre, String content){
		String sensorName = sensorThre.getAliaName();
		if(StringUtils.isEmpty(sensorName)){
			sensorName = sensorThre.getSensorName();
		}
		StringBuffer buffer = new StringBuffer();
		LocalDateTime time = LocalDateTime.now();
		buffer.append("监控组，您好！</br></br>");
		buffer.append("端口 “<font face='arial' color='red'>"+sensorName+"</font>”出现异常，请及时处理，谢谢。</br></br>");
		buffer.append("报警时间："+DateUtil.formatTime2Str(time, "yyyy-MM-dd HH:mm:ss")+"</br></br>");
		buffer.append("所属IDC："+sensorThre.getGroupName()+"</br></br>");
		buffer.append("设备："+sensorThre.getDeviceName()+"</br></br>");
		buffer.append("端口：<font face='arial' color='red'>"+sensorName+"</font></br></br>");
		buffer.append("状态/描述：<font face='arial' color='red'>"+content+"</font></br></br>");
		return buffer.toString();
	}
	
	
//	public String getMailContent(String path, Map map){
//		VelocityEngine ve = new VelocityEngine();
//		ve.setProperty(Velocity.RESOURCE_LOADER, "class");
//		ve.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader"); // 设置类路径加载模板
//		ve.setProperty(Velocity.INPUT_ENCODING, "utf-8");// 设置输入字符集
//		ve.setProperty(Velocity.OUTPUT_ENCODING, "utf-8");// 设置输出字符集
//		ve.init();// 初始化模板引擎
//		Template t = ve.getTemplate("net_device_mail.vm");// 加载模板，相对于classpath路径
//		VelocityContext context = new VelocityContext();
//
//		HashMap<String, Object> result = new HashMap<String, Object>();
//		result.put("Name", "模板");
//		result.put("Key", "语言");
//
//		context.put("map", result);
//
//		StringWriter writer = new StringWriter();
//		t.merge(context, writer); // 转换
//		return writer.toString();	//形成最终结果
//	}

}
