package com.gome.upm.controler;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gome.upm.common.Page;
import com.gome.upm.common.util.ResponsesDTO;
import com.gome.upm.constants.ReturnCode;
import com.gome.upm.dao.MoNetSensorMapper;
import com.gome.upm.domain.UrlMonitor;
import com.gome.upm.domain.prtg.GroupIdc;
import com.gome.upm.domain.prtg.MoNetDevice;
import com.gome.upm.domain.prtg.MoNetSensor;
import com.gome.upm.domain.prtg.Sensor;
import com.gome.upm.domain.prtg.SensorChannel;
import com.gome.upm.domain.prtg.SensorDetail;
import com.gome.upm.domain.prtg.SensorDevice;
import com.gome.upm.domain.prtg.SensorHistoryData;
import com.gome.upm.service.NetWorkMonitorService;
import com.gome.upm.service.util.DBContextHolder;
import com.gome.upm.service.util.DateUtil;
import com.gome.upm.service.util.PageUtil;

import redis.Gcache;

/**
 * 网络监控控制类
 * 
 * @author zhangzhixiang-ds
 *
 */
@Controller
@RequestMapping(value = "/prtg")
public class PRTGController {

	private final static Logger logger = LoggerFactory.getLogger(PRTGController.class);

	@Resource
	private NetWorkMonitorService netWorkMonitorService;
	@Resource(name = "monitorGcache")
	Gcache testGcache;
	@Resource
	private MoNetSensorMapper moNetSensorMapper;

	/**
	 * 网络监控首页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
		try {
			model.setViewName("/network/networkMonitor");
			model.addObject("leftMenu", "networkMenu");
		} catch (Exception e) {
			logger.error("error", e);
			model.setViewName("/error");
		}

		return model;

	}

	/**
	 * 异步获取数据
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/index/ajax", method = { RequestMethod.POST }, produces = "application/json;charset=utf-8")
	public ResponsesDTO save(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try {
			logger.info("处理开始");
			Map<String, Object> map = netWorkMonitorService.index2();
			res.setAttach(map);
			logger.info("处理结束");

		} catch (Exception e) {
			logger.error("系统出现异常", e);
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}

		return res;

	}

	/**
	 * 异步获取group下设备
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param groupId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/device/ajax", method = {
			RequestMethod.POST }, produces = "application/json;charset=utf-8")
	public ResponsesDTO deviceAjax(HttpServletRequest request, HttpServletResponse response, ModelAndView model,
			@RequestParam(value = "groupId", required = true) String groupId) {
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try {
			List<SensorDevice> list = netWorkMonitorService.getDevice(groupId, null, null, "device");
			res.setAttach(list);
		} catch (Exception e) {
			logger.error("系统出现异常", e);
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}

		return res;

	}

	/**
	 * 网络监控错误设备页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/device/error", method = RequestMethod.GET)
	public ModelAndView deviceError(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {

		try {
			List<GroupIdc> listGroup = netWorkMonitorService.getGroupIdc(null, null, null, "name");
			model.setViewName("/network/networkMonitorHardwareError");
			model.addObject("leftMenu", "networkMenu");
			model.addObject("idc", listGroup);
		} catch (Exception e) {
			logger.error("error", e);
			model.setViewName("/error");
		}

		return model;

	}

	/**
	 * 网络监控错误设备table页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param sdate
	 * @param edate
	 * @param groupId
	 * @param deviceId
	 * @return
	 */
	@RequestMapping(value = "/device/error/table", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView deviceErrorTable(HttpServletRequest request, HttpServletResponse response, ModelAndView model,
			@RequestParam(value = "sdate", required = false) String sdate,
			@RequestParam(value = "edate", required = false) String edate,
			@RequestParam(value = "groupId", required = false) Integer groupId,
			@RequestParam(value = "deviceId", required = false) Integer deviceId,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {

		try {
			if (pageNo == null) {
				pageNo = 1;
			}
			if (pageSize == null) {
				pageSize = 10;
			}
			Page<MoNetDevice> page = new Page<MoNetDevice>(pageNo, pageSize);
			MoNetDevice device = new MoNetDevice();
			if (groupId != null) {
				device.setGroupId(groupId);
			}
			if (deviceId != null) {
				device.setDeviceId(deviceId);
			}
			if (!StringUtils.isEmpty(sdate)) {
				device.setSdate(DateUtil.Str2Date(sdate, null));
			}
			if (!StringUtils.isEmpty(edate)) {
				device.setEdate(DateUtil.Str2Date(edate, null));
			}
			device.setState(MoNetDevice.ALARM_STATE);// 报警设备
			page.setConditions(device);
			page = netWorkMonitorService.getDevicePage(page);

			model.setViewName("/network/networkMonitorHardwareErrorTable");
			model.addObject("leftMenu", "networkMenu");
			model.addObject("page", page);
			model.addObject("sdate", sdate);
			model.addObject("edate", edate);
		} catch (Exception e) {
			logger.error("error", e);
			model.setViewName("/error");
		}

		return model;

	}

	/**
	 * 网络监控全部设备页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/device/all", method = RequestMethod.GET)
	public ModelAndView deviceAll(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {

		try {
			model.setViewName("/network/networkMonitorAllfacility");
			model.addObject("leftMenu", "networkMenu");
			// List<SensorDevice> list = netWorkMonitorService.getDevice(null,
			// null, null,"group,device,upsens,downsens");
			List<GroupIdc> listGroup = netWorkMonitorService.getGroupIdc(null, null, null, "name");
			// model.addObject("list", list);
			model.addObject("idc", listGroup);
		} catch (Exception e) {
			logger.error("error", e);
			model.setViewName("/error");
		}

		return model;

	}

	/**
	 * 异步刷新table
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/device/allTable", method = RequestMethod.POST)
	public ModelAndView deviceAllTable(HttpServletRequest request, HttpServletResponse response, ModelAndView model,
			@RequestParam(value = "groupId", required = false) Integer groupId,
			@RequestParam(value = "deviceId", required = false) Integer deviceId,
			@RequestParam(value = "deviceStatus", required = true) Integer deviceStatus,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {
		logger.info("groupId:" + groupId + "|deviceId:" + deviceId + "|deviceStatus:" + deviceStatus);

		try {
			if (pageNo == null) {
				pageNo = 1;
			}
			if (pageSize == null) {
				pageSize = 10;
			}
			Page<MoNetDevice> page = new Page<MoNetDevice>(pageNo, pageSize);
			MoNetDevice device = new MoNetDevice();
			if (groupId != null) {
				device.setGroupId(groupId);
			}
			if (deviceId != null) {
				device.setDeviceId(deviceId);
			}
			if (deviceStatus != null) {
				device.setState(deviceStatus);// 报警设备
			}

			page.setConditions(device);
			page = netWorkMonitorService.getDevicePage(page);
			model.setViewName("/network/networkMonitorAllfacilityTable");
			model.addObject("leftMenu", "networkMenu");
			model.addObject("page", page);
		} catch (Exception e) {
			logger.error("error", e);
			model.setViewName("/error");
		}

		return model;

	}

	@RequestMapping(value = "/port/error", method = RequestMethod.GET)
	public ModelAndView portError(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {

		try {
			List<GroupIdc> listGroup = netWorkMonitorService.getGroupIdc(null, null, null, "name");
			model.addObject("idc", listGroup);
			model.setViewName("/network/networkMonitorPortError");
			model.addObject("leftMenu", "networkMenu");
		} catch (Exception e) {
			logger.error("error", e);
			model.setViewName("/error");
		}

		return model;

	}

	/**
	 * 网络监控错误设备table页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param sdate
	 * @param edate
	 * @param groupId
	 * @param deviceId
	 * @return
	 */
	@RequestMapping(value = "/port/error/table", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView portErrorTable(HttpServletRequest request, HttpServletResponse response, ModelAndView model,
			@RequestParam(value = "sdate", required = false) String sdate,
			@RequestParam(value = "edate", required = false) String edate,
			@RequestParam(value = "groupId", required = false) Integer groupId,
			@RequestParam(value = "deviceId", required = false) Integer deviceId,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {

		try {
			if (pageNo == null) {
				pageNo = 1;
			}
			if (pageSize == null) {
				pageSize = 10;
			}
			Page<MoNetSensor> page = new Page<MoNetSensor>(pageNo, pageSize);
			MoNetSensor sensor = new MoNetSensor();
			if (groupId != null) {
				sensor.setGroupId(groupId);
			}
			if (deviceId != null) {
				sensor.setDeviceId(deviceId);
			}
			if (!StringUtils.isEmpty(sdate)) {
				sensor.setSdate(DateUtil.Str2Date(sdate, null));
			}
			if (!StringUtils.isEmpty(edate)) {
				sensor.setEdate(DateUtil.Str2Date(edate, null));
			}
			sensor.setState(MoNetSensor.ALARM_STATE);// 报警设备
			page.setConditions(sensor);
		//	page = netWorkMonitorService.getSensorPage(page);
			page = netWorkMonitorService.getSensorPageAlarm(page);
			model.setViewName("/network/networkMonitorPortErrorTable");
			model.addObject("leftMenu", "networkMenu");
			model.addObject("page", page);
			model.addObject("sdate", sdate);
			model.addObject("edate", edate);
			model.addObject("groupId", groupId);
			model.addObject("deviceId", deviceId);
		} catch (Exception e) {
			logger.error("error", e);
			model.setViewName("/error");
		}

		return model;

	}

	@RequestMapping(value = "/port/all", method = RequestMethod.GET)
	public ModelAndView portAll(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {

		try {
			model.setViewName("/network/networkMonitorPortAll");
			model.addObject("leftMenu", "networkMenu");
			// List<SensorDevice> list = netWorkMonitorService.getDevice(null,
			// null, null,"group,device,upsens,downsens");
			List<GroupIdc> listGroup = netWorkMonitorService.getGroupIdc(null, null, null, "name");
			// model.addObject("list", list);
			model.addObject("idc", listGroup);
		} catch (Exception e) {
			logger.error("error", e);
			model.setViewName("/error");
		}

		return model;

	}

	/**
	 * 异步刷新table
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/port/allTable", method = RequestMethod.POST)
	public ModelAndView portAllTable(HttpServletRequest request, HttpServletResponse response, ModelAndView model,
			@RequestParam(value = "groupId", required = false) Integer groupId,
			@RequestParam(value = "deviceId", required = false) Integer deviceId,
			@RequestParam(value = "deviceStatus", required = true) Integer deviceStatus,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {
		logger.info("groupId:" + groupId + "|deviceId:" + deviceId + "|deviceStatus:" + deviceStatus);

		try {
			if (pageNo == null) {
				pageNo = 1;
			}
			if (pageSize == null) {
				pageSize = 10;
			}
			Page<MoNetSensor> page = new Page<MoNetSensor>(pageNo, pageSize);
			MoNetSensor sensor = new MoNetSensor();
			if (groupId != null) {
				sensor.setGroupId(groupId);
			}
			if (deviceId != null) {
				sensor.setDeviceId(deviceId);
			}
			if (deviceStatus != null) {
				sensor.setState(deviceStatus);// 报警设备
			}

			page.setConditions(sensor);
			page = netWorkMonitorService.getSensorPage(page);
			model.setViewName("/network/networkMonitorPortAllTable");
			model.addObject("leftMenu", "networkMenu");
			model.addObject("page", page);
		} catch (Exception e) {
			logger.error("error", e);
			model.setViewName("/error");
		}

		return model;

	}

	/**
	 * 设备详情页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param deviceId
	 * @return
	 */
	@RequestMapping(value = "/device/detail", method = RequestMethod.GET)
	public ModelAndView deviceDeatail(HttpServletRequest request, HttpServletResponse response, ModelAndView model,
			@RequestParam(value = "deviceId", required = true) Integer deviceId) {
		try {
			MoNetDevice device = netWorkMonitorService.getDeviceByIdFromDb(deviceId);
		//	SensorDevice device = netWorkMonitorService.getDeviceById(deviceId);// 设备信息
		//	List<Sensor> list = netWorkMonitorService.getSensors(deviceId, null, null, "sensor,status");// 获取传感器列表
			model.setViewName("/network/networkMonitorDetail");
			model.addObject("leftMenu", "networkMenu");
			model.addObject("device", device);
		//	model.addObject("list", list);
		} catch (Exception e) {
			logger.error("error", e);
			model.setViewName("/error");
		}

		return model;

	}

	/**
	 * 设备详情页下传感器分页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param sensorId
	 * @param type
	 * @param date
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/device/detail/table", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView deviceDeatailTable(HttpServletRequest request, HttpServletResponse response, ModelAndView model,
			@RequestParam(value = "deviceId", required = true) Integer deviceId,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {
		try {
			logger.info("deviceId:" + deviceId + "|pageNo：" + pageNo + "|pageSize:" + pageSize);
			if (pageNo == null) {
				pageNo = 1;
			}
			if (pageSize == null) {
				pageSize = 10;
			}
			Page<MoNetSensor> page = new Page<MoNetSensor>(pageNo, pageSize);
			MoNetSensor sensor = new MoNetSensor();
			if (deviceId != null) {
				sensor.setDeviceId(deviceId);
			}
			page.setConditions(sensor);
			page = netWorkMonitorService.getSensorPage(page);
			model.setViewName("/network/networkMonitorDetailTable");
			model.addObject("leftMenu", "networkMenu");
			model.addObject("page", page);
		} catch (Exception e) {
			logger.error("error", e);
			model.setViewName("/error");
		}

		return model;

	}

	/**
	 * 传感器详情页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param sensorId
	 *            传感器id
	 * @return
	 */
	@RequestMapping(value = "/sensor/detail", method = RequestMethod.GET)
	public ModelAndView sensorDeatail(HttpServletRequest request, HttpServletResponse response, ModelAndView model,
			@RequestParam(value = "sensorId", required = true) String sensorId,
			@RequestParam(value = "deviceId", required = true) String deviceId) {
		try {
			LocalDateTime localDateTime = LocalDateTime.now();
			String end = DateUtil.formatTime2Str(localDateTime, "yyyy-MM-dd") + " 23:59:59";
			String start = DateUtil.formatTime2Str(localDateTime.plusDays(-7), "yyyy-MM-dd") + " 00:00:00";
			SensorDetail sensor = netWorkMonitorService.getsensordetails(sensorId);// 获取传感器详情
			List<SensorChannel> channelList = netWorkMonitorService.getSensorChannels(sensorId, null, null,
					sensor.getType());// 传感器通道
			model.setViewName("/network/networkMonitorPortDetail");
			model.addObject("leftMenu", "networkMenu");
			model.addObject("channelList", channelList);
			model.addObject("sensor", sensor);
			model.addObject("deviceId", deviceId);
			model.addObject("start", start);
			model.addObject("end", end);
		} catch (Exception e) {
			logger.error("error", e);
			model.setViewName("/error");
		}

		return model;

	}

	/**
	 * 分页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param sensorId
	 * @param deviceId
	 * @param type
	 * @param date
	 * @param pageNo
	 * @return
	 */
//	@RequestMapping(value = "/sensor/history/table", method = { RequestMethod.GET, RequestMethod.POST })
//	public ModelAndView sensorHistoryTable(HttpServletRequest request, HttpServletResponse response, ModelAndView model,
//			@RequestParam(value = "sensorId", required = true) String sensorId,
//			@RequestParam(value = "type", required = true) String type,
//			@RequestParam(value = "date", required = true) String date,
//			@RequestParam(value = "pageNo", required = false) Integer pageNo,
//			@RequestParam(value = "pageSize", required = false) Integer pageSize) {
//		try {
//			logger.info("sensorId:" + sensorId + "|type:" + type + "|date:" + date + "|pageNo：" + pageNo + "|pageSize:"
//					+ pageSize);
//			Page<SensorHistoryData> page = netWorkMonitorService.getHistoryPageData(sensorId, date, pageNo, pageSize,
//					type);
//			if (type.equals("flow")) {
//				model.setViewName("/network/networkMonitorPortDetailFlow");
//			} else {
//				model.setViewName("/network/networkMonitorPortDetailCpuMem");
//			}
//			if (page.getTotalResult() == 0) {
//				model.setViewName("error429");
//			}
//			model.addObject("page", page);
//			// model.addObject("date", date);
//		} catch (Exception e) {
//			logger.error("error", e);
//			model.setViewName("/error");
//		}
//
//		return model;
//
//	}

	/**
	 * 传感器历史数据分页优化 暂不使用
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param sensorId
	 * @param type
	 * @param date
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/sensor/history/table2", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView sensorHistoryTable2(HttpServletRequest request, HttpServletResponse response,
			ModelAndView model, @RequestParam(value = "sensorId", required = true) Long sensorId,
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "date", required = true) String date,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "avg", required = false) Integer avg) {
		try {
			logger.info("sensorId:" + sensorId + "|type:" + type + "|date:" + date + "|pageNo：" + pageNo + "|pageSize:"
					+ pageSize + "|avg:" + avg);
			if (pageNo == null || pageNo <= 0) {
				pageNo = 1;
			}
			if(pageSize == null || pageSize <= 0){
				pageSize = 10;
			}
			String resXml = netWorkMonitorService.getHistoryToPortDetail(sensorId, date, pageNo, pageSize, type);
			if(resXml.equals("429")){
				model.setViewName("error429");
				return model;
			}
			List<SensorHistoryData> list = netWorkMonitorService.getHistoryDataFromApi(resXml, type);
			Collections.reverse(list);
			if (type.equals("flow")) {
				model.setViewName("/network/networkMonitorPortDetailFlow2");
			} else {
				model.setViewName("/network/networkMonitorPortDetailCpuMem2");
			}
			model.addObject("pageSize", pageSize);
			model.addObject("pageNo", pageNo);
			model.addObject("list", list);
		} catch (Exception e) {
			logger.error("error", e);
			model.setViewName("/error");
		}

		return model;

	}

	/**
	 * 获取传感器历史数据
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param groupId
	 * @param sdate
	 * @param edate
	 * @param avg
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getSensorHistoryChart", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "application/json;charset=utf-8")
	public ResponsesDTO getSensorHistory(HttpServletRequest request, HttpServletResponse response, ModelAndView model,
			@RequestParam(value = "sensorId", required = true) Long sensorId,
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "tabDate", required = false) String tabDate) {
		logger.info("sensorId:" + sensorId + "|type:" + type);
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try {
			String responseStr = netWorkMonitorService.getHistoryChartToPortDetail(sensorId, tabDate, type);
			if(responseStr.equals("429")){
				res.setAttach("429");
				return res;
			}
			List<SensorHistoryData> list = netWorkMonitorService.getHistoryDataFromApi(responseStr, type);
			res.setAttach(list);
		} catch (Exception e) {
			logger.error("系统出现异常", e);
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}

		return res;

	}

	/**
	 * 首页流量一览
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/flowSummary", method = RequestMethod.GET)
	public ModelAndView flowSummary(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
		try {
			model.setViewName("/network/flowSummary");
			model.addObject("leftMenu", "flowSummaryMenu");
		} catch (Exception e) {
			logger.error("error", e);
			model.setViewName("/error");
		}

		return model;

	}

	/**
	 * 首页流量一览chart
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param sensorIds
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getFlowSummaryChart", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "application/json;charset=utf-8")
	public ResponsesDTO getFlowSummaryChart(HttpServletRequest request, HttpServletResponse response,
			ModelAndView model, @RequestParam(value = "sensorIds", required = true) String sensorIds) {
		logger.info("sensorIds:" + sensorIds);
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try {
			logger.info("处理开始");

			Map<String, Object> map = netWorkMonitorService.getSummaryFromDB(sensorIds);

			res.setAttach(map);
			logger.info("处理结束");
		} catch (Exception e) {
			logger.error("系统出现异常", e);
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}

		return res;

	}

	/**
	 * 传感器启动历史记录
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/sensor/detail/history", method = RequestMethod.GET)
	public ModelAndView getSensorHistory(HttpServletRequest request, HttpServletResponse response, ModelAndView model,
			@RequestParam(value = "sensorId", required = true) String sensorId,
			@RequestParam(value = "deviceId", required = true) String deviceId,
			@RequestParam(value = "sdate", required = true) String sdate,
			@RequestParam(value = "edate", required = true) String edate,
			@RequestParam(value = "avg", required = true) String avg) {
		try {
			logger.info("sensorId:" + sensorId + "|deviceId:" + deviceId + "|sdate:" + sdate + "|edate:" + edate
					+ "|avg:" + avg);
			SensorDetail sensor = netWorkMonitorService.getsensordetails(sensorId);// 获取传感器详情
			//Map<String,Object> map = netWorkMonitorService.getStartHistoryDetails(sensorId, sdate, edate, avg, sensor.getType());
			model.addObject("sensor", sensor);
			model.addObject("deviceId", deviceId);
			model.addObject("sdate", sdate);
			model.addObject("edate", edate);
			model.addObject("avg", avg);
            //model.addObject("map", map);
			model.setViewName("/network/networkMonitorHistoryData");
			model.addObject("leftMenu", "networkMenu");
		} catch (Exception e) {
			logger.error("error", e);
			model.setViewName("/error");
		}

		return model;

	}

	/**
	 * 历史记录启动的页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param sensorId
	 * @param type
	 * @param date
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/sensor/start/history/table", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView sensorStartHistoryTable(HttpServletRequest request, HttpServletResponse response,
			ModelAndView model, @RequestParam(value = "sensorId", required = true) Long sensorId,
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "sdate", required = true) String sdate,
			@RequestParam(value = "edate", required = true) String edate,
			@RequestParam(value = "avg", required = true) Integer avg,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {
		try {
			logger.info("sensorId:" + sensorId + "|type:" + type + "|sdate:" + sdate + "|edate:" + edate + "|pageNo："
					+ pageNo + "|pageSize:" + pageSize);
//			Page<SensorHistoryData> page = netWorkMonitorService.getHistoryPageData(sensorId, sdate, edate, pageNo,
//					pageSize, type, avg);
			if (pageNo == null || pageNo <= 0) {
				pageNo = 1;
			}
			if(pageSize == null || pageSize <= 0){
				pageSize = 10;
			}
			
			String resXml = netWorkMonitorService.getHistoryToStart(sensorId, sdate, edate, pageNo, pageSize, avg);
			if(resXml.equals("429")){
				model.setViewName("error429");
				return model;
			}
			
			List<SensorHistoryData> list = netWorkMonitorService.getHistoryDataFromApi(resXml, type);
			Collections.reverse(list);
			if (type.equals("flow")) {
				model.setViewName("/network/networkMonitorPortDetailFlow2");
			} else {
				model.setViewName("/network/networkMonitorPortDetailCpuMem2");
			}

			model.addObject("pageSize", pageSize);
			model.addObject("pageNo", pageNo);
			model.addObject("list", list);
		} catch (Exception e) {
			logger.error("error", e);
			model.setViewName("/error");
		}

		return model;

	}

	/**
	 * 历史记录启动后图表
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param sensorId
	 * @param sdate
	 * @param edate
	 * @param type
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getStartSensorHistoryChart", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "application/json;charset=utf-8")
	public ResponsesDTO getStartSensorHistoryChart(HttpServletRequest request, HttpServletResponse response,
			ModelAndView model, @RequestParam(value = "sensorId", required = true) Long sensorId,
			@RequestParam(value = "sdate", required = true) String sdate,
			@RequestParam(value = "edate", required = false) String edate,
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "avgChart", required = false) Integer avgChart) {
		logger.info("sensorId:" + sensorId + "|sdate:" + sdate + "|edate:" + edate+"|type:"+type+"|avgChart:"+avgChart);
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try {
		Map<String,Object> map = netWorkMonitorService.getStartHistoryDetails(sensorId, sdate, edate, avgChart, type);
			res.setAttach(map);
		} catch (Exception e) {
			logger.error("系统出现异常", e);
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}

		return res;

	}

	/**
	 * 修改传感器备注
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param sensorId
	 * @param remark
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addSensorRemark", method = {
			RequestMethod.POST }, produces = "application/json;charset=utf-8")
	public ResponsesDTO addSensorRemark(HttpServletRequest request, HttpServletResponse response, ModelAndView model,
			@RequestParam(value = "sensorId", required = true) Integer sensorId,
			@RequestParam(value = "remark", required = true) String remark) {
		logger.info("sensorId:" + sensorId + "|remark:" + remark);
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try {
			MoNetSensor sensor = new MoNetSensor();
			sensor.setSensorId(sensorId);
			sensor.setRemark(remark);
			DBContextHolder.setDataSource("dataSourceOne");
			moNetSensorMapper.updateByPrimaryKeySelective(sensor);
		} catch (Exception e) {
			logger.error("系统出现异常", e);
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}

		return res;

	}

	public void renderData(HttpServletResponse response, String data) {
		PrintWriter printWriter = null;
		try {
			printWriter = response.getWriter();
			printWriter.print(data);
		} catch (IOException ex) {
			// Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE,
			// null, ex);
		} finally {
			if (null != printWriter) {
				printWriter.flush();
				printWriter.close();
			}
		}
	}

}
