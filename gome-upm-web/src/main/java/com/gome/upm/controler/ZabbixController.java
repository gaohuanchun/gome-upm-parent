package com.gome.upm.controler;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gome.upm.common.Page;
import com.gome.upm.common.util.ResponsesDTO;
import com.gome.upm.constants.ReturnCode;
import com.gome.upm.domain.ServerAlarmRecord;
import com.gome.upm.domain.ServerHost;
import com.gome.upm.domain.ServerItem;
import com.gome.upm.domain.ServerItemDetail;
import com.gome.upm.domain.prtg.IndexTOP5;
import com.gome.upm.service.ServerMonitorService;

import redis.Gcache;
@Controller
@RequestMapping(value="/server")
public class ZabbixController {
	
	@Resource(name = "monitorGcache")
	Gcache monitorGcache;
	@Resource(name = "serverMonitorService")
	ServerMonitorService serverMonitorService;
	
	/**
	 * 获取全部设备
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="goToHostAll")
	public ModelAndView goToHostAll(HttpServletRequest request, HttpServletResponse response, ModelAndView model) throws Exception{
		model.setViewName("/upm/serverMonitor");
		model.addObject("leftMenu", "serverMenu");
		return model;
	}
	/**
	 * 应用一览
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="goToSummary")
	public ModelAndView goToSummary(HttpServletRequest request, HttpServletResponse response, ModelAndView model) throws Exception{
		model.setViewName("/upm/appSummary");
		return model;
	}
	/**
	 * 详情
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="goToDetail")
	public ModelAndView goToDetail(HttpServletRequest request, HttpServletResponse response, ModelAndView model,@RequestParam(value = "hostid", required = true) String hostid) throws Exception{
		ServerHost serverHost = new ServerHost();
		serverHost.setHostId(hostid);
		//List<HashMap<String, String>> items = ZabbixUtils.getItems(hostid);
		//model.addObject("items", items);
		serverHost = serverMonitorService.queryHost(hostid);
		model.addObject("serverHost", serverHost);
		model.addObject("leftMenu", "serverMenu");
		model.setViewName("/upm/serverMonitor-detail");
		return model;
	}
	/**
	 * 详情
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="queryTrigger")
	public ModelAndView queryTrigger(HttpServletRequest request, HttpServletResponse response, ModelAndView model,@RequestParam(value = "hostid", required = true) String hostid) throws Exception{
		ServerHost serverHost = new ServerHost();
		serverHost.setHostId(hostid);
		List<HashMap<String, String>> items = ZabbixUtils.getItems(hostid);
		model.addObject("items", items);
		serverHost = serverMonitorService.queryHost(hostid);
		model.addObject("serverHost", serverHost);
		model.addObject("leftMenu", "serverMenu");
		model.setViewName("/upm/trigger");
		return model;
	}
	
	/**
	 * 查询server首页数据 包括健康指数、总设备数、故障设备数等
	 * @param request
	 * @param response
	 * @param model
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/getTriggerByItemId", method={RequestMethod.POST,RequestMethod.GET}, produces = "application/json;charset=utf-8")
	public ResponsesDTO getTriggerByItemId (HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "itemId", required = true) String itemId) throws Exception{
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try{
			List<String> triggerList = new ArrayList<String>();
			JSONArray jsonArray = ZabbixUtils.getTriggerByItemId(itemId);
			if(jsonArray!=null && jsonArray.toArray().length>0){
				for (int i = 0; i < jsonArray.toArray().length; i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					String priority = (String) jsonObject.get("priority");
					String waring = "<tr>";
					if(priority.equals("2")){
						waring = waring +"<td style='background: yellow;'>Warning</td>";
					}else if(priority.equals("3")){
						waring = waring +"<td style='background: #DD9222;'>Average</td>";
					}else if(priority.equals("4")){
						waring = waring +"<td style='background: #E66B1A;'>High</td>";
					}
					String name = (String) jsonObject.get("description");
					String expression = (String) jsonObject.get("expression");
					String status = (String) jsonObject.get("status");
					String description = "<td>"+name+"</td>";
					expression = "<td>{<span style='color: #00AA00;text-decoration: underline;'>"+expression+"</td>";
					if(status.equals("0")){
						status = "<td style='color: green;'>Enabled</td></tr>";
					}else{
						status = "<td style='color: red;'>Disabled</td></tr>";
					}
					triggerList.add(waring+description+expression+status);
				}
			}
			res.setCode(ReturnCode.ACTIVE_SUCCESS.code());
			res.setAttach(triggerList);
		}catch(Exception e){
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
			throw new Exception(e);
		}
		return res;
	}
	
	/**
	 * 查询server首页数据 包括健康指数、总设备数、故障设备数等
	 * @param request
	 * @param response
	 * @param model
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/getIndex", method={RequestMethod.POST,RequestMethod.GET}, produces = "application/json;charset=utf-8")
	public ResponsesDTO getIndex (HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "content", required = true) String content) throws Exception{
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try{
			String totalHost = monitorGcache.get("totalHost");
			String validHost = monitorGcache.get("validHost");
			String validPHost = monitorGcache.get("validPHost");
			String invalidHost = monitorGcache.get("invalidHost");
			String invalidPHost = monitorGcache.get("invalidPHost");
			ServerHost serverHost = new ServerHost();
			serverHost.setTotal(totalHost);
			serverHost.setValid(validHost);
			serverHost.setValidP(validPHost);
			serverHost.setInvalid(invalidHost);
			serverHost.setInvalidP(invalidPHost);
			
			res.setCode(ReturnCode.ACTIVE_SUCCESS.code());
			res.setAttach(serverHost);
		}catch(Exception e){
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
			throw new Exception(e);
		}
		return res;
	}
	/**
	 * 查询server监控项首页数据 包括健康指数、总设备监控项数、监控项故障数等
	 * @param request
	 * @param response
	 * @param model
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/getItemIndex", method={RequestMethod.POST,RequestMethod.GET}, produces = "application/json;charset=utf-8")
	public ResponsesDTO getItemIndex (HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "content", required = true) String content) throws Exception{
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try{
			String totalItems = monitorGcache.get("totalItems");
			String validItems = monitorGcache.get("validItems");
			String validPItems = monitorGcache.get("validPItems");
			String invalidItems = monitorGcache.get("invalidItems");
			String invalidPItems = monitorGcache.get("invalidPItems");
			
			ServerHost serverHost = new ServerHost();
			serverHost.setTotal(totalItems);
			serverHost.setValid(validItems);
			serverHost.setValidP(validPItems);
			serverHost.setInvalid(invalidItems);
			serverHost.setInvalidP(invalidPItems);
			res.setCode(ReturnCode.ACTIVE_SUCCESS.code());
			res.setAttach(serverHost);
		}catch(Exception e){
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
			throw new Exception(e);
		}
		return res;
	}
	/**
	 * 查询server触发器首页数据 包括健康指数、总设备监控项数、监控项故障数等
	 * @param request
	 * @param response
	 * @param model
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/getTriggerIndex", method={RequestMethod.POST,RequestMethod.GET}, produces = "application/json;charset=utf-8")
	public ResponsesDTO getTriggerIndex (HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "content", required = true) String content) throws Exception{
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try{
			String totalTrigger = monitorGcache.get("totalTrigger");
			String validTrigger = monitorGcache.get("validTrigger");
			String validPTrigger = monitorGcache.get("validPTrigger");
			String invalidTrigger = monitorGcache.get("invalidTrigger");
			String invalidPTrigger = monitorGcache.get("invalidPTrigger");
			
			ServerHost serverHost = new ServerHost();
			serverHost.setTotal(totalTrigger);
			serverHost.setValid(validTrigger);
			serverHost.setValidP(validPTrigger);
			serverHost.setInvalid(invalidTrigger);
			serverHost.setInvalidP(invalidPTrigger);
			res.setCode(ReturnCode.ACTIVE_SUCCESS.code());
			res.setAttach(serverHost);
		}catch(Exception e){
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
			throw new Exception(e);
		}
		return res;
	}
	
	/**
	 * 获取内存监控数据
	 * @param request
	 * @param response
	 * @param model
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/getMemory", method={RequestMethod.POST,RequestMethod.GET}, produces = "application/json;charset=utf-8")
	public ResponsesDTO getMemory(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		Map<String, Object> map = new HashMap<String, Object>();
		try{
//			List<Map<String,Object>> hostList = ZabbixUtils.getHostList("0");
//			List<IndexTOP5> listMemory = new ArrayList<IndexTOP5>();
//			for (int i = 0; i < hostList.size(); i++) {
//				String ip = ZabbixUtils.getHostInterface(hostList.get(i).get("hostid").toString());
//				String hostid = hostList.get(i).get("hostid").toString();
//				Double memorylastValue = ZabbixUtils.getLastValue(hostList.get(i), "vm.memory.size[pavailable]");
//				System.out.println(i+"ip+lastValue------------"+ip+":"+".....memorylastValue"+memorylastValue);
//				if(memorylastValue!=null){
//					IndexTOP5 indexTOP5 = new IndexTOP5();
//					indexTOP5.setHost(ip);
//					indexTOP5.setLastVal(memorylastValue);
//					indexTOP5.setDeviceId(hostid);
//					listMemory.add(indexTOP5);
//				}
//			}
//			Collections.sort(listMemory);
//			//{memory=[IndexTOP5 [deviceId=null, deviceName=null, sensorId=null, sensorName=null, lastVal=99.44, host=10.63.9.17], IndexTOP5 [deviceId=null, deviceName=null, sensorId=null, sensorName=null, lastVal=99.29, host=10.58.46.167], IndexTOP5 [deviceId=null, deviceName=null, sensorId=null, sensorName=null, lastVal=99.28, host=10.58.69.71], IndexTOP5 [deviceId=null, deviceName=null, sensorId=null, sensorName=null, lastVal=99.26, host=10.58.72.44], IndexTOP5 [deviceId=null, deviceName=null, sensorId=null, sensorName=null, lastVal=99.25, host=10.58.46.208]]}
//			map.put("memory", listMemory.size() < 5 ? listMemory : listMemory.subList(0, 5));
			String memoryTOP5 = monitorGcache.get("memoryTOP5");
			ObjectMapper mapper = new ObjectMapper();  
			Map<String,Object> productMap = mapper.readValue(memoryTOP5, map.getClass());//转成map
			res.setCode(ReturnCode.ACTIVE_SUCCESS.code());
			res.setAttach(productMap);
		}catch(Exception e){
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
			throw new Exception(e);
		}
		return res;
	}
	
	/**
	 * 获取CPU监控数据
	 * @param request
	 * @param response
	 * @param model
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/getCpu", method={RequestMethod.POST,RequestMethod.GET}, produces = "application/json;charset=utf-8")
	public ResponsesDTO getCpu(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		Map<String, Object> map = new HashMap<String, Object>();
		try{
//			List<Map<String,Object>> hostList = ZabbixUtils.getHostList("0");
//			List<IndexTOP5> listCpu = new ArrayList<IndexTOP5>();
//			for (int i = 0; i < hostList.size(); i++) {
//				String ip = ZabbixUtils.getHostInterface(hostList.get(i).get("hostid").toString());
//				String hostid = hostList.get(i).get("hostid").toString();
//				Double cpulastValue = ZabbixUtils.getLastValue(hostList.get(i), "cpu_use_all");
//				System.out.println(i+"ip+lastValue------------"+ip+":"+".....cpulastValue"+cpulastValue);
//				if(cpulastValue!=null){
//					IndexTOP5 indexTOP5 = new IndexTOP5();
//					indexTOP5.setHost(ip);
//					indexTOP5.setDeviceId(hostid);
//					indexTOP5.setLastVal(cpulastValue);
//					listCpu.add(indexTOP5);
//				}
//			}
//			Collections.sort(listCpu);
//			//{cpu=[IndexTOP5 [deviceId=null, deviceName=null, sensorId=null, sensorName=null, lastVal=75.03, host=10.58.47.109], IndexTOP5 [deviceId=null, deviceName=null, sensorId=null, sensorName=null, lastVal=71.21, host=10.58.222.103], IndexTOP5 [deviceId=null, deviceName=null, sensorId=null, sensorName=null, lastVal=65.37, host=10.58.222.107], IndexTOP5 [deviceId=null, deviceName=null, sensorId=null, sensorName=null, lastVal=58.85, host=10.58.51.153], IndexTOP5 [deviceId=null, deviceName=null, sensorId=null, sensorName=null, lastVal=58.18, host=10.58.53.33]]}
//			map.put("cpu", listCpu.size() < 5 ? listCpu : listCpu.subList(0, 5));
			String cpuTOP5 = monitorGcache.get("cpuTOP5");
			ObjectMapper mapper = new ObjectMapper();  
			Map<String,Object> productMap = mapper.readValue(cpuTOP5, map.getClass());//转成map
			res.setCode(ReturnCode.ACTIVE_SUCCESS.code());
			res.setAttach(productMap);
		}catch(Exception e){
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
			throw new Exception(e);
		}
		return res;
	}
	
	/**
	 * 获取1分钟负载监控数据
	 * @param request
	 * @param response
	 * @param model
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/getLoad", method={RequestMethod.POST,RequestMethod.GET}, produces = "application/json;charset=utf-8")
	public ResponsesDTO getLoad(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		Map<String, Object> map = new HashMap<String, Object>();
		try{
//			List<Map<String,Object>> hostList = ZabbixUtils.getHostList("0");
//			List<IndexTOP5> listLoad = new ArrayList<IndexTOP5>();
//			for (int i = 0; i < hostList.size(); i++) {
//				String ip = ZabbixUtils.getHostInterface(hostList.get(i).get("hostid").toString());
//				String hostid = hostList.get(i).get("hostid").toString();
//				Double loadlastValue = ZabbixUtils.getLastValue(hostList.get(i), "system.cpu.load[all,avg1]");
//				System.out.println(i+"ip+lastValue------------"+ip+":"+".....loadlastValue"+loadlastValue);
//				if(loadlastValue!=null){
//					IndexTOP5 indexTOP5 = new IndexTOP5();
//					indexTOP5.setDeviceId(hostid);
//					indexTOP5.setHost(ip);
//					indexTOP5.setLastVal(loadlastValue);
//					listLoad.add(indexTOP5);
//				}
//			}
//			Collections.sort(listLoad);
//			//{load=[IndexTOP5 [deviceId=10538, deviceName=null, sensorId=null, sensorName=null, lastVal=25.66, host=10.58.11.1], IndexTOP5 [deviceId=10573, deviceName=null, sensorId=null, sensorName=null, lastVal=20.38, host=10.126.53.52], IndexTOP5 [deviceId=11330, deviceName=null, sensorId=null, sensorName=null, lastVal=18.66, host=10.58.52.45], IndexTOP5 [deviceId=10341, deviceName=null, sensorId=null, sensorName=null, lastVal=16.47, host=10.58.47.109], IndexTOP5 [deviceId=12119, deviceName=null, sensorId=null, sensorName=null, lastVal=15.49, host=10.58.223.35]]}
//			map.put("load", listLoad.size() < 5 ? listLoad : listLoad.subList(0, 5));
			String LoadTOP5 = monitorGcache.get("LoadTOP5");
			ObjectMapper mapper = new ObjectMapper();  
			Map<String,Object> productMap = mapper.readValue(LoadTOP5, map.getClass());//转成map
			res.setCode(ReturnCode.ACTIVE_SUCCESS.code());
			res.setAttach(productMap);
		}catch(Exception e){
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
			throw new Exception(e);
		}
		return res;
	}
	
	/**
	 * 获取IO监控数据
	 * @param request
	 * @param response
	 * @param model
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/getIO", method={RequestMethod.POST,RequestMethod.GET}, produces = "application/json;charset=utf-8")
	public ResponsesDTO getIO(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		Map<String, Object> map = new HashMap<String, Object>();
		try{
//			List<Map<String,Object>> hostList = ZabbixUtils.getHostList("0");
//			List<IndexTOP5> listLoad = new ArrayList<IndexTOP5>();
//			for (int i = 0; i < hostList.size(); i++) {
//				String ip = ZabbixUtils.getHostInterface(hostList.get(i).get("hostid").toString());
//				String hostid = hostList.get(i).get("hostid").toString();
//				Double loadlastValue = ZabbixUtils.getLastValue(hostList.get(i), "vfs.dev.io.read[sda]");
//				System.out.println(i+"ip+lastValue------------"+ip+":"+".....loadlastValue"+loadlastValue);
//				if(loadlastValue!=null){
//					IndexTOP5 indexTOP5 = new IndexTOP5();
//					indexTOP5.setDeviceId(hostid);
//					indexTOP5.setHost(ip);
//					indexTOP5.setLastVal(loadlastValue);
//					listLoad.add(indexTOP5);
//				}
//			}
//			Collections.sort(listLoad);
//			map.put("io", listLoad.size() < 5 ? listLoad : listLoad.subList(0, 5));
			
			String IOTOP5 = monitorGcache.get("IOTOP5");
			ObjectMapper mapper = new ObjectMapper();  
			Map<String,Object> productMap = mapper.readValue(IOTOP5, map.getClass());//转成map
			res.setCode(ReturnCode.ACTIVE_SUCCESS.code());
			res.setAttach(productMap);
		}catch(Exception e){
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
			throw new Exception(e);
		}
		return res;
	}
	
	/**
	 * 获取getDisk磁盘使用监控数据
	 * @param request
	 * @param response
	 * @param model
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/getDisk", method={RequestMethod.POST,RequestMethod.GET}, produces = "application/json;charset=utf-8")
	public ResponsesDTO getDisk(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			List<Map<String,Object>> hostList = ZabbixUtils.getHostList("0");
			List<IndexTOP5> listLoad = new ArrayList<IndexTOP5>();
			for (int i = 0; i < hostList.size(); i++) {
				String ip = ZabbixUtils.getHostInterface(hostList.get(i).get("hostid").toString());
				String hostid = hostList.get(i).get("hostid").toString();
				Double loadlastValue = ZabbixUtils.getLastValue(hostList.get(i), "vfs.fs.size[/,pfree]");
				if(loadlastValue!=null){
					IndexTOP5 indexTOP5 = new IndexTOP5();
					indexTOP5.setDeviceId(hostid);
					indexTOP5.setHost(ip);
					indexTOP5.setLastVal(loadlastValue);
					listLoad.add(indexTOP5);
				}
			}
			Collections.sort(listLoad);
			//{load=[IndexTOP5 [deviceId=10538, deviceName=null, sensorId=null, sensorName=null, lastVal=25.66, host=10.58.11.1], IndexTOP5 [deviceId=10573, deviceName=null, sensorId=null, sensorName=null, lastVal=20.38, host=10.126.53.52], IndexTOP5 [deviceId=11330, deviceName=null, sensorId=null, sensorName=null, lastVal=18.66, host=10.58.52.45], IndexTOP5 [deviceId=10341, deviceName=null, sensorId=null, sensorName=null, lastVal=16.47, host=10.58.47.109], IndexTOP5 [deviceId=12119, deviceName=null, sensorId=null, sensorName=null, lastVal=15.49, host=10.58.223.35]]}
			map.put("disk", listLoad.size() < 5 ? listLoad : listLoad.subList(0, 5));
			res.setCode(ReturnCode.ACTIVE_SUCCESS.code());
			res.setAttach(map);
		}catch(Exception e){
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
			throw new Exception(e);
		}
		return res;
	}
	/**
	 * 获取全部设备
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getHostAll" , method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView getHostAll(@Param(value="content")String content, @Param(value="page")String page, @Param(value="size")String size,HttpServletRequest request, HttpServletResponse response, ModelAndView model) throws Exception{
		if(StringUtils.isEmpty(content)){
			model.setViewName("/upm/serverMonitor-allfacility");
		}else{
			model.setViewName("/upm/serverMonitor-allfacilityTable");
		}
		int pageNo = 1;
		if(StringUtils.isNotEmpty(page)){
			pageNo = Integer.parseInt(page);
		}
		int pageSize = 15;
		if(StringUtils.isNotEmpty(size)){
			pageSize = Integer.parseInt(size);
		}
		Page<ServerHost> p = new Page<ServerHost>(pageNo, pageSize);
		String groupName = "";
		ServerHost condition = null;
		//通过数据库类型查找
		if(StringUtils.isNotEmpty(content)){
			condition = JSON.parseObject(content, ServerHost.class);
			groupName = condition.getGroupName();
			String hostName = condition.getHost();
			if(groupName!=null && groupName!=""){
				groupName=java.net.URLDecoder.decode(groupName,"UTF-8");
				condition.setGroupName(groupName);
			}
			if(hostName!=null && hostName!=""){
				hostName=java.net.URLDecoder.decode(hostName,"UTF-8");
				condition.setHost(hostName);
			}
		} else {
			condition = new ServerHost();
		}
		p.setConditions(condition);
		String[] groupList = serverMonitorService.queryHostGroup();
		String[] hostNameList = serverMonitorService.queryHostName(groupName);
		Page<ServerHost>  hostList = serverMonitorService.queryHostList(p);
		model.addObject("groupList", groupList);
		model.addObject("hostNameList", hostNameList);
		model.addObject("page", hostList);
		model.addObject("leftMenu", "serverMenu");
		return model;
	}
	
	/**
	 * 获取host查询条件列表
	 * @param request
	 * @param response
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/getHost", method={RequestMethod.POST,RequestMethod.GET}, produces = "application/json;charset=utf-8")
	public ResponsesDTO getHost(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "group", required = true) String group) throws Exception{
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try{
			String[] hostNameList = serverMonitorService.queryHostName(group);
			res.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
			res.setAttach(hostNameList);
		}catch(Exception e){
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
			throw new Exception(e);
		}
		return res;
	}
	
	/**
	 * 获取故障设备
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getInvalidHostAll")
	public ModelAndView getInvalidHostAll(HttpServletRequest request, HttpServletResponse response, ModelAndView model) throws Exception{
		model.setViewName("/upm/serverMonitor-alarmlog");
		List<Map<String,Object>> hostListinvalid = ZabbixUtils.getHostList("1");
		List<Map<String,Object>> hostList = new ArrayList<Map<String,Object>>();
		hostList.addAll(hostListinvalid);
		List<ServerHost> serverHostList = new ArrayList<ServerHost>();
		ServerHost serverHostView = new ServerHost();
		serverHostView.setTotal(String.valueOf(hostList.size()));
		int valid = 0;
		int invalid = 0;
		int template = 0;
		for(int i=0;i<hostList.size();i++){
			ServerHost serverHost = new ServerHost();
			String available = (String) hostList.get(i).get("available");
			String host = (String) hostList.get(i).get("host");
			String hostId = (String) hostList.get(i).get("hostid");
//			JSONObject object = ZabbixUtils.getHostInterface(hostId);
//			if(object!=null){
//				String ip = object.getString("ip");
//				String port = object.getString("port");
//				serverHost.setIp(ip);
//				serverHost.setPort(port);
//				serverHost.setAddress(ip+":"+port);
//			}
			String name = (String) hostList.get(i).get("name");
			String status = (String) hostList.get(i).get("status");
			if(status.equals("0")){
				valid = valid + 1;
			}else if(status.equals("1")){
				invalid = invalid +1;
			}else{
				template = template +1;
			}
			serverHost.setNum(String.valueOf(i+1));
			serverHost.setAvailable(available);
			serverHost.setHost(host);
			serverHost.setHostId(hostId);
			serverHost.setName(name);
			serverHost.setStatus(status);
			serverHostList.add(serverHost);
		}
		serverHostView.setValid(String.valueOf(valid));
		serverHostView.setInvalid(String.valueOf(invalid));
		serverHostView.setTemplate(String.valueOf(template));
		model.addObject("serverHostView", serverHostView);
		model.addObject("serverHostList", serverHostList);
		model.addObject("leftMenu", "serverMenu");
		return model;
	}
	
	/**
	 * 获取内存监控数据---详情
	 * @param request
	 * @param response
	 * @param model
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/getMemoryDetail", method={RequestMethod.POST,RequestMethod.GET}, produces = "application/json;charset=utf-8")
	public ResponsesDTO getMemoryDetail(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "hostid", required = true) String hostid) throws Exception{
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		Map<String, Object> map = new HashMap<String, Object>();
		try{
		String item = "vm.memory.size[pavailable]";
		long time_till = new Date().getTime();
		long time_from = time_till-1800000*2;
		List<ServerItemDetail>  itemDetailList = new ArrayList<ServerItemDetail>();
		JSONArray datas = ZabbixUtils.getDatas(hostid, item, time_from, time_till);
		Object[] array = datas.toArray();
		for (int i = 0; i < array.length; i++) {
			JSONObject object = datas.getJSONObject(i);
			String clock = (String) object.get("clock");
			SimpleDateFormat df = new SimpleDateFormat("HH:mm");//定义格式，不显示毫秒
			Timestamp now = new Timestamp(Long.parseLong(clock)*1000);//获取系统当前时间
			String str = df.format(now);
			String value = (String) object.get("value");
			BigDecimal bdMemeor = new BigDecimal(Double.parseDouble(value));
			bdMemeor = bdMemeor.setScale(2,BigDecimal.ROUND_HALF_UP);
			ServerItemDetail detail = new ServerItemDetail();
			detail.setClock(str);
			detail.setValue(bdMemeor+"");
			itemDetailList.add(detail);
		}
		map.put("itemDetailList", itemDetailList);
		res.setAttach(map);
		}catch(Exception e){
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
			throw new Exception(e);
		}
		return res;
	}
	
	/**
	 * 获取cpu监控数据---详情
	 * @param request
	 * @param response
	 * @param model
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/getCpuDetail", method={RequestMethod.POST,RequestMethod.GET}, produces = "application/json;charset=utf-8")
	public ResponsesDTO getCpuDetail(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "hostid", required = true) String hostid) throws Exception{
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		Map<String, Object> map = new HashMap<String, Object>();
		try{
		String item = "cpu_use_all";
		long time_till = new Date().getTime();
		long time_from = time_till-1800000*2;
		List<ServerItemDetail>  itemDetailList = new ArrayList<ServerItemDetail>();
		JSONArray datas = ZabbixUtils.getDatas(hostid, item, time_from, time_till);
		Object[] array = datas.toArray();
		for (int i = 0; i < array.length; i++) {
			JSONObject object = datas.getJSONObject(i);
			String clock = (String) object.get("clock");
			SimpleDateFormat df = new SimpleDateFormat("HH:mm");//定义格式，不显示毫秒
			Timestamp now = new Timestamp(Long.parseLong(clock)*1000);//获取系统当前时间
			String str = df.format(now);
			String value = (String) object.get("value");
			BigDecimal bdMemeor = new BigDecimal(Double.parseDouble(value));
			bdMemeor = bdMemeor.setScale(2,BigDecimal.ROUND_HALF_UP);
			ServerItemDetail detail = new ServerItemDetail();
			detail.setClock(str);
			detail.setValue(bdMemeor+"");
			itemDetailList.add(detail);
		}
		map.put("itemDetailList", itemDetailList);
		res.setAttach(map);
		}catch(Exception e){
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
			throw new Exception(e);
		}
		return res;
	}
	
	/**
	 * 获取系统负载监控数据---详情
	 * @param request
	 * @param response
	 * @param model
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/getLoadDetail", method={RequestMethod.POST,RequestMethod.GET}, produces = "application/json;charset=utf-8")
	public ResponsesDTO getLoadDetail(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "hostid", required = true) String hostid) throws Exception{
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		Map<String, Object> map = new HashMap<String, Object>();
		try{
		String item = "system.cpu.load[all,avg1]";
		long time_till = new Date().getTime();
		long time_from = time_till-1800000*2;
		List<ServerItemDetail>  itemDetailList = new ArrayList<ServerItemDetail>();
		JSONArray datas = ZabbixUtils.getDatas(hostid, item, time_from, time_till);
		Object[] array = datas.toArray();
		for (int i = 0; i < array.length; i++) {
			JSONObject object = datas.getJSONObject(i);
			String clock = (String) object.get("clock");
			SimpleDateFormat df = new SimpleDateFormat("HH:mm");//定义格式，不显示毫秒
			Timestamp now = new Timestamp(Long.parseLong(clock)*1000);//获取系统当前时间
			String str = df.format(now);
			String value = (String) object.get("value");
			BigDecimal bdMemeor = new BigDecimal(Double.parseDouble(value));
			bdMemeor = bdMemeor.setScale(2,BigDecimal.ROUND_HALF_UP);
			ServerItemDetail detail = new ServerItemDetail();
			detail.setClock(str);
			detail.setValue(bdMemeor+"");
			itemDetailList.add(detail);
		}
		map.put("itemDetailList", itemDetailList);
		res.setAttach(map);
		}catch(Exception e){
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
			throw new Exception(e);
		}
		return res;
	}
	
	/**
	 * 获取磁盘使用率监控数据---详情
	 * @param request
	 * @param response
	 * @param model
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/getDiskUseDetail", method={RequestMethod.POST,RequestMethod.GET}, produces = "application/json;charset=utf-8")
	public ResponsesDTO getDiskUseDetail(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "hostid", required = true) String hostid) throws Exception{
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try{
		String names="vfs.fs.size";
		String key = "pfree";
		long time_till = new Date().getTime();
		long time_from = time_till-1800000*2;
		List<ServerItem>  itemList = new ArrayList<ServerItem>();
		List<HashMap<String,String>> itemIds = ZabbixUtils.getItemIds(hostid, names, key);
		if(itemIds!=null && itemIds.size()>0){
			for (int i = 0; i < itemIds.size(); i++) {
				ServerItem serverItem = new ServerItem();
				HashMap<String,String> hashMap = itemIds.get(i);
				String itemid = hashMap.get("itemid");
				String key_ = hashMap.get("key_");
				serverItem.setName(key_);
				serverItem.setItemId(itemid);
				List<ServerItemDetail>  itemDetailList = new ArrayList<ServerItemDetail>();
				JSONArray datas = ZabbixUtils.getDataByItemId2(hostid, itemid, time_from, time_till);
				Object[] array = datas.toArray();
				for (int j = 0; j < array.length; j++) {
					JSONObject object = datas.getJSONObject(j);
					String clock = (String) object.get("clock");
					SimpleDateFormat df = new SimpleDateFormat("HH:mm");//定义格式，不显示毫秒
					Timestamp now = new Timestamp(Long.parseLong(clock)*1000);//获取系统当前时间
					String str = df.format(now);
					String value = (String) object.get("value");
					BigDecimal bdMemeor = new BigDecimal(Double.parseDouble(value));
					bdMemeor = bdMemeor.setScale(2,BigDecimal.ROUND_HALF_UP);
					ServerItemDetail detail = new ServerItemDetail();
					detail.setClock(str);
					detail.setValue(bdMemeor+"");
					itemDetailList.add(detail);
				}
				serverItem.setItemDetailList(itemDetailList);
				itemList.add(serverItem);
			}
		}
		res.setAttach(itemList);
		}catch(Exception e){
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
			throw new Exception(e);
		}
		return res;
	}
	/**
	 * 获取磁盘总量监控数据---详情
	 * @param request
	 * @param response
	 * @param model
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/getDiskTotalDetail", method={RequestMethod.POST,RequestMethod.GET}, produces = "application/json;charset=utf-8")
	public ResponsesDTO getDiskTotalDetail(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "hostid", required = true) String hostid) throws Exception{
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try{
			String names="vfs.fs.size";
			String key = "total";
			long time_till = new Date().getTime();
			long time_from = time_till-1800000*2*5;
			List<ServerItem>  itemList = new ArrayList<ServerItem>();
			List<HashMap<String,String>> itemIds = ZabbixUtils.getItemIds(hostid, names, key);
			if(itemIds!=null && itemIds.size()>0){
				for (int i = 0; i < itemIds.size(); i++) {
					ServerItem serverItem = new ServerItem();
					HashMap<String,String> hashMap = itemIds.get(i);
					String itemid = hashMap.get("itemid");
					String key_ = hashMap.get("key_");
					serverItem.setName(key_);
					serverItem.setItemId(itemid);
					List<ServerItemDetail>  itemDetailList = new ArrayList<ServerItemDetail>();
					JSONArray datas = ZabbixUtils.getDataByItemId(hostid, itemid, time_from, time_till);
					Object[] array = datas.toArray();
					for (int j = 0; j < array.length; j++) {
						JSONObject object = datas.getJSONObject(j);
						String clock = (String) object.get("clock");
						SimpleDateFormat df = new SimpleDateFormat("HH:mm");//定义格式，不显示毫秒
						Timestamp now = new Timestamp(Long.parseLong(clock)*1000);//获取系统当前时间
						String str = df.format(now);
						String value = (String) object.get("value");
						BigDecimal bdMemeor = new BigDecimal(Double.parseDouble(value)/1073741824);
						bdMemeor = bdMemeor.setScale(2,BigDecimal.ROUND_HALF_UP);
						ServerItemDetail detail = new ServerItemDetail();
						detail.setClock(str);
						detail.setValue(bdMemeor+"");
						itemDetailList.add(detail);
					}
					serverItem.setItemDetailList(itemDetailList);
					itemList.add(serverItem);
				}
			}
			res.setAttach(itemList);
		}catch(Exception e){
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
			throw new Exception(e);
		}
		return res;
	}
	/**
	 * 获取IO读Read监控数据---详情
	 * @param request
	 * @param response
	 * @param model
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/getIOReadDetail", method={RequestMethod.POST,RequestMethod.GET}, produces = "application/json;charset=utf-8")
	public ResponsesDTO getIOReadDetail(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "hostid", required = true) String hostid) throws Exception{
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try{
			String names="vfs.dev.io.read";
			String key = "";
			long time_till = new Date().getTime();
			long time_from = time_till-1800000*2;
			List<ServerItem>  itemList = new ArrayList<ServerItem>();
			List<HashMap<String,String>> itemIds = ZabbixUtils.getItemIds(hostid, names, key);
			if(itemIds!=null && itemIds.size()>0){
				for (int i = 0; i < itemIds.size(); i++) {
					ServerItem serverItem = new ServerItem();
					HashMap<String,String> hashMap = itemIds.get(i);
					String itemid = hashMap.get("itemid");
					String key_ = hashMap.get("key_");
					serverItem.setName(key_);
					serverItem.setItemId(itemid);
					List<ServerItemDetail>  itemDetailList = new ArrayList<ServerItemDetail>();
					JSONArray datas = ZabbixUtils.getDataByItemId(hostid, itemid, time_from, time_till);
					Object[] array = datas.toArray();
					for (int j = 0; j < array.length; j++) {
						JSONObject object = datas.getJSONObject(j);
						String clock = (String) object.get("clock");
						SimpleDateFormat df = new SimpleDateFormat("HH:mm");//定义格式，不显示毫秒
						Timestamp now = new Timestamp(Long.parseLong(clock)*1000);//获取系统当前时间
						String str = df.format(now);
						String value = (String) object.get("value");
						BigDecimal bdMemeor = new BigDecimal(Double.parseDouble(value));
						bdMemeor = bdMemeor.setScale(2,BigDecimal.ROUND_HALF_UP);
						ServerItemDetail detail = new ServerItemDetail();
						detail.setClock(str);
						detail.setValue(bdMemeor+"");
						itemDetailList.add(detail);
					}
					serverItem.setItemDetailList(itemDetailList);
					itemList.add(serverItem);
				}
			}
			res.setAttach(itemList);
		}catch(Exception e){
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
			throw new Exception(e);
		}
		return res;
	}
	/**
	 * 获取IO读Write监控数据---详情
	 * @param request
	 * @param response
	 * @param model
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/getIOWriteDetail", method={RequestMethod.POST,RequestMethod.GET}, produces = "application/json;charset=utf-8")
	public ResponsesDTO getIOWriteDetail(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "hostid", required = true) String hostid) throws Exception{
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try{
			String names="vfs.dev.io.write";
			String key = "";
			long time_till = new Date().getTime();
			long time_from = time_till-1800000*2;
			List<ServerItem>  itemList = new ArrayList<ServerItem>();
			List<HashMap<String,String>> itemIds = ZabbixUtils.getItemIds(hostid, names, key);
			if(itemIds!=null && itemIds.size()>0){
				for (int i = 0; i < itemIds.size(); i++) {
					ServerItem serverItem = new ServerItem();
					HashMap<String,String> hashMap = itemIds.get(i);
					String itemid = hashMap.get("itemid");
					String key_ = hashMap.get("key_");
					serverItem.setName(key_);
					serverItem.setItemId(itemid);
					List<ServerItemDetail>  itemDetailList = new ArrayList<ServerItemDetail>();
					JSONArray datas = ZabbixUtils.getDataByItemId(hostid, itemid, time_from, time_till);
					Object[] array = datas.toArray();
					for (int j = 0; j < array.length; j++) {
						JSONObject object = datas.getJSONObject(j);
						String clock = (String) object.get("clock");
						SimpleDateFormat df = new SimpleDateFormat("HH:mm");//定义格式，不显示毫秒
						Timestamp now = new Timestamp(Long.parseLong(clock)*1000);//获取系统当前时间
						String str = df.format(now);
						String value = (String) object.get("value");
						BigDecimal bdMemeor = new BigDecimal(Double.parseDouble(value));
						bdMemeor = bdMemeor.setScale(2,BigDecimal.ROUND_HALF_UP);
						ServerItemDetail detail = new ServerItemDetail();
						detail.setClock(str);
						detail.setValue(bdMemeor+"");
						itemDetailList.add(detail);
					}
					serverItem.setItemDetailList(itemDetailList);
					itemList.add(serverItem);
				}
			}
			res.setAttach(itemList);
		}catch(Exception e){
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
			throw new Exception(e);
		}
		return res;
	}
	
	

	/**
	 * 
	 * 跳转到报警列表页.
	 *
	 * @param dataType
	 * 				数据类型
	 * @param alarmLevel
	 * 				报警级别
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月19日    caowei    新建
	 * </pre>
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/list", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toAlarmListView(@Param(value="content")String content, @Param(value="page")String page, @Param(value="size")String size, HttpServletRequest request, HttpServletResponse response, ModelAndView model) throws UnsupportedEncodingException{
		if(StringUtils.isEmpty(content)){
			model.setViewName("/upm/serverMonitor-alarmlog");
			//下拉列表
		} else {
			model.setViewName("/upm/serverMonitor-alarmlogTable");
		}
		String groupName = "";
		String hostName = "";
		String[] groupList = serverMonitorService.queryHostGroupNew();
		String[] hostNameList = serverMonitorService.queryHostNameNew(groupName);
		model.addObject("groupList", groupList);
		model.addObject("hostNameList", hostNameList);
		model.addObject("leftMenu", "serverMenu");
		int pageNo = 1;
		if(StringUtils.isNotEmpty(page)){
			pageNo = Integer.parseInt(page);
		}
		int pageSize = 15;
		if(StringUtils.isNotEmpty(size)){
			pageSize = Integer.parseInt(size);
		}
		Page<ServerAlarmRecord> p = new Page<ServerAlarmRecord>(pageNo, pageSize);
		
		ServerAlarmRecord condition = null;
		//通过数据库类型查找
		if(StringUtils.isNotEmpty(content)){
			condition = JSON.parseObject(content, ServerAlarmRecord.class);
			groupName = condition.getGroupName();
			hostName = condition.getHost();
			if(groupName!=null && groupName!=""){
				groupName=java.net.URLDecoder.decode(groupName,"UTF-8");
				condition.setGroupName(groupName);
			}
			if(hostName!=null && hostName!=""){
				hostName=java.net.URLDecoder.decode(hostName,"UTF-8");
				condition.setHost(hostName);
			}
		} else {
			condition = new ServerAlarmRecord();
		}
		p.setConditions(condition);
		Page<ServerAlarmRecord> pageAlarm = serverMonitorService.findAlarmRecordListByPage(p);
		model.addObject("page", pageAlarm);
		return model;
	}
}
