package com.gome.upm.service;

import java.util.List;
import java.util.Map;

import com.gome.upm.common.Page;
import com.gome.upm.domain.prtg.GroupIdc;
import com.gome.upm.domain.prtg.MoNetDevice;
import com.gome.upm.domain.prtg.MoNetHistory;
import com.gome.upm.domain.prtg.MoNetSensor;
import com.gome.upm.domain.prtg.Sensor;
import com.gome.upm.domain.prtg.SensorChannel;
import com.gome.upm.domain.prtg.SensorDetail;
import com.gome.upm.domain.prtg.SensorDevice;
import com.gome.upm.domain.prtg.SensorHistoryData;

/**
 * 网络监控相关接口
 * @author zhangzhixiang-ds
 *
 */
public interface NetWorkMonitorService {
	
	
	/**
	 * 远程获取某一个传感器历史数据
	 * @param sensorId   传感器id        不能为空
	 * @param avg        平均时间间隔
	 * @param sdate    yyyy-MM-dd-HH-mm-ss  开始日期   如果为空，默认当前时间
	 * @param edate    yyyy-MM-dd-HH-mm-ss  截止日期   如果为空，默认两小时之前
	 * @param start      分页开始条数
	 * @param count      分页大小
	 * @return
	 */
	public String getHistoryXmlFromApi(Long sensorId, Integer avg, String sdate, String edate, Integer start, Integer count);
	
	/**
	 * 远程获取的数据转成Java对象
	 * @param xml    getHistoryXmlFromApi   接口获取的数据
	 * @param type   类型  flow other
	 * @return
	 */
	public List<SensorHistoryData> getHistoryDataFromApi(String xml, String type);
	
	
	
	/**
	 * 传感器详情页历史数据table
	 * @param sensorId
	 * @param tab
	 * @param pageNo         不能为空
	 * @param pageSize       不能为空
	 * @param type
	 * @return
	 */
	public String getHistoryToPortDetail(Long sensorId, String tab, Integer pageNo, Integer pageSize ,String type);
	
	
	/**
	 * 传感器详情页历史数据chart
	 * @param sensorId
	 * @param tab
	 * @param type
	 * @return
	 */
	public String getHistoryChartToPortDetail(Long sensorId, String tab,String type);
	
	/**
	 * 传感器启动后页历史数据table
	 * @param sensorId
	 * @param sdate
	 * @param edate
	 * @param pageNo
	 * @param pageSize
	 * @param type
	 * @return
	 */
	public String getHistoryToStart(Long sensorId, String sdate, String edate, Integer pageNo, Integer pageSize, Integer avg);
	
	/**
	 * 传感器启动后页历史数据chart
	 * @param sensorId
	 * @param sdate
	 * @param edate
	 * @return
	 */
	public String getHistoryChartToStart(Long sensorId, String sdate, String edate, Integer avg);



	/**
	 * 获取idc
	 * @param parentId
	 * @param start
	 * @param count
	 * &columns=objid,probe,group,name,downsens,partialdownsens,downacksens,upsens,warnsens,pausedsens,unusualsens,undefinedsens
	 * @return
	 */
	public List<GroupIdc> getGroupIdc(String parentId, String start, String count,String columns);
	
	/**
	 * 获取设备列表
	 * @param groupId
	 * @param start
	 * @param count
	 * columns=objid,probe,group,device,host,downsens,partialdownsens,downacksens,upsens,warnsens,pausedsens,unusualsens,undefinedsens
	 * @return
	 */
	public List<SensorDevice> getDevice(String groupId, String start, String count, String columns);
	
	/**
	 * 页面异步搜索
	 * @param groupId
	 * @param deviceId
	 * @return
	 */
	public List<SensorDevice> getDevice2Ajax(String groupId, String deviceId, String deviceStatus);
	
	/**
	 * 获取传感器列表
	 * @param deviceId
	 * @param start
	 * @param count
	 * &columns=objid,probe,group,device,sensor,status,message,lastvalue,priority,favorite
	 * @return
	 */
	public List<Sensor> getSensors(String deviceId, String start, String count, String columns);
	
	/**
	 * 获取端口通道列表
	 * @param sensorId
	 * @param start
	 * @param count
	 * @return
	 */
	public List<SensorChannel> getSensorChannels(String sensorId, String start, String count, String type);
	
	/**
	 * 获取sensor详情
	 * @param sensorId
	 * @return
	 */
	public SensorDetail getsensordetails(String sensorId);
	
	
	/**
	 * 网络监控首页
	 * @return
	 */
	public Map<String,Object> index(); 
	
	/**
	 * id获取device
	 * @param deviceId
	 * @return
	 */
	public SensorDevice getDeviceById(String deviceId);
	
	public MoNetDevice getDeviceByIdFromDb(Integer deviceId);
	
	/**
	 * 获取属性
	 * @param id
	 * @param name
	 * @return
	 */
	public String getObjectProperty(String id, String name);

	

	/**
	 * 分页获取设备信息
	 * @param page
	 * @return
	 */
	public Page<MoNetDevice> getDevicePage(Page<MoNetDevice> page);
	
	/**
	 * 分页获取传感器
	 * @param page
	 * @return
	 */
	public Page<MoNetSensor> getSensorPage(Page<MoNetSensor> page);
	
	public Page<MoNetSensor> getSensorPageAlarm(Page<MoNetSensor> page);
	
	
	
	public Map<String,Object> getStartHistoryDetails(Long sensorId, String sdate, String edate, Integer avg, String type);
	
	
	public Map<String,Object> index2(); 
	
	
	/**
	 * 流量一览数据查询
	 * @param sensorId
	 * @return
	 */
	public List<MoNetHistory> getSummaryFromApi(String sensorId);
	
	public Map<String,Object> getSummaryFromDB(String sensorIds);

}
