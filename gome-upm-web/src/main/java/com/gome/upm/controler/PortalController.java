package com.gome.upm.controler;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.gome.upm.common.Page;
import com.gome.upm.common.util.ResponsesDTO;
import com.gome.upm.constants.ReturnCode;
import com.gome.upm.domain.PortMonitor;
import com.gome.upm.domain.PortRecord;
import com.gome.upm.service.PortMonitorService;

/**
 * 端口监控
 * 
 * @author zhangzhixiang-ds
 *
 */

@Controller
@Scope("prototype")
@RequestMapping(value = "/portal")
public class PortalController {

	private Logger logger = LoggerFactory.getLogger(PortalController.class);
	
	@Autowired
	private PortMonitorService  portMonitorService;

	@RequestMapping(value = "/get", method={RequestMethod.GET})
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
		Page<PortMonitor> page = new Page<PortMonitor>(1,10);
		PortMonitor query = new PortMonitor();
		page.setConditions(query);
		page = portMonitorService.findPortMonitorListByPage(page);
		query.setSurvival(1);
		int validCount  =portMonitorService.findTotalResultByConditions(query);
		int inValidCount = page.getTotalResult()-validCount;
		int exponential =0;
		if(page.getTotalResult()>0){
			exponential = validCount*100/(page.getTotalResult());
		}
		model.setViewName("/portal/portSurvivalMonitor");
		model.addObject("leftMenu", "portMenu");
		model.addObject("page", page);
		model.addObject("validCount", validCount);
		model.addObject("inValidCount", inValidCount);
		model.addObject("exponential", exponential);
		return model;
	}
	
	/**
	 * 报警记录
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/warning", method={RequestMethod.GET})
	public ModelAndView warning(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
		Page<PortMonitor> page = new Page<PortMonitor>(1,10);
		PortMonitor query = new PortMonitor();
		query.setSurvival(0);
		page.setConditions(query);
		page = portMonitorService.findPortMonitorListByPage(page);
		
		model.setViewName("/portal/portSurvivalMonitorWarning");
		model.addObject("leftMenu", "portMenu");
		model.addObject("page", page);
		return model;
	}
	/**
	 * port id获取port信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getById", method={RequestMethod.POST}, produces = "application/json;charset=utf-8")
	public ResponsesDTO getById(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "id", required = true) Long id){
		logger.info("id:"+id);
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try{
			PortMonitor port = portMonitorService.findPortMonitorById(id);
			res.setAttach(port);
		}catch(Exception e){
			logger.error("系统出现异常", e);
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}

		return res;

	}
	/**
	 * url报表
	 * @param request
	 * @param response
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/report")
	public ModelAndView report(HttpServletRequest request, HttpServletResponse response, ModelAndView model, @RequestParam(value="id", required = true) Long id){
		logger.info("id",id);
		model.setViewName("/url/urlReport");
		model.addObject("leftMenu", "urlMenu");
		try{
			PortMonitor port = portMonitorService.findPortMonitorById(id);
			Page<PortRecord> page1 = new Page<PortRecord>(1,10);
			if(port != null){
				
				PortRecord portrecord = new PortRecord();
				portrecord.setPid(id);
				page1.setConditions(portrecord);
				page1 = portMonitorService.findPortRecordListByPage(page1);
				
				
			}else{
				model.setViewName("error");
			}
			model.setViewName("/portal/portReport");
			model.addObject("leftMenu", "portMenu");
			model.addObject("page", page1);
			model.addObject("port", port);
		}catch(Exception e){
			logger.info("eror",e);
			model.setViewName("error");
		}
		
		return model;

	}
	
	@RequestMapping("/import")
    public
    void importExcel(@RequestParam("fileToUpload")MultipartFile multipartFile,HttpSession session,HttpServletResponse response) throws Exception{
		
        InputStream inputStream;
        response.setContentType("text/html");
        inputStream = multipartFile.getInputStream();
        List<PortMonitor> list = null;
        PortMonitor port = new PortMonitor();
        List<PortMonitor> portList =portMonitorService.findPortMonitorListByPortMonitor(port);
        Map<String,String> map = new HashMap<String,String>();
        for(PortMonitor portMonitor:portList){
        	map.put(portMonitor.getPort(), "");
        }
    	Pattern p = Pattern.compile("^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}:\\d{1,5}$",Pattern.CASE_INSENSITIVE ); 

        try {
        	Workbook book =WorkbookFactory.create(inputStream);
        	list =ExcelUtils.excelImport(PortMonitor.class, book);
        	int pcount = 0;
        	int doubleCount = 0;
        	List<PortMonitor> addList = new ArrayList<PortMonitor>();
        	Map<String,String> resultMap = new HashMap<String,String>();
        	for(PortMonitor portMonitor:list){
        		if(!StringUtils.isBlank(portMonitor.getPort())){
        			Matcher m =p.matcher(portMonitor.getPort());
		        			
        			if(!m.find()){
        				pcount++;
        				continue;
        			}
        			if(!map.containsKey(portMonitor.getPort())){
        				if(resultMap.containsKey(portMonitor.getPort())){
        					doubleCount++;
        					continue;
        				}
        				resultMap.put(portMonitor.getPort(), "");
        				if(StringUtils.isBlank(portMonitor.getMonitorType())){
        					portMonitor.setMonitorType("应用服务器");
        				}
        				if(portMonitor.getFrequency()==null){
        					portMonitor.setFrequency(5);
        				}
        				if(portMonitor.getOvertimes()==null){
        					portMonitor.setOvertimes(5);
        				}
        				if(StringUtils.isBlank(portMonitor.getAlarmMethod())){
        					portMonitor.setAlarmMethod("email");
        				}
        				portMonitor.setTimeout(0);
        				portMonitor.setCreateTime(new Date());
        				portMonitor.setStatus(1);
        				portMonitor.setUpdateTime(new Date());
        				portMonitor.setSurvival(1);
        				addList.add(portMonitor);
        			}else{
        				doubleCount++;
        			}
        		}else{
        			pcount++;
        		}
        	}
        	int count = 0;
        	if(addList.size()>0){
        		count=portMonitorService.batchAddPortMonitor(addList);
        	}
            response.getWriter().write("端口存活监控数据导入成功:"+count+"条,端口监控地址格式错误:"+pcount+"条,重复数据:"+doubleCount+"条");
        } catch (Exception e) {
        	logger.info("eror",e);
            response.getWriter().write(e.getMessage());
        }
	}
	
	/**
	 * 创建端口监控第一步
	 * @param request
	 * @param response
	 * @param model
	 * @param key
	 * @param desc
	 * @param app
	 * @return
	 */
	@RequestMapping(value="/create/step1")
	public ModelAndView createStep1(HttpServletRequest request, HttpServletResponse response, ModelAndView model,
			@RequestParam(value = "key", required = false) String key, 
			@RequestParam(value = "desc", required = false) String desc,
			@RequestParam(value = "app", required = false) String app){
		model.addObject("key", key);
		model.addObject("desc", desc);
		model.addObject("app", app);
		model.setViewName("/portal/buildPortalStep1");
		model.addObject("leftMenu", "portMenu");
		return model;

	}
	
	/**
	 * 创建端口监控第一步key值验证
	 * @param request
	 * @param response
	 * @param model
	 * @param key
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/varifyKey", method={RequestMethod.POST}, produces = "application/json;charset=utf-8")
	public ResponsesDTO varifyKey(HttpServletRequest request, HttpServletResponse response, ModelAndView model, @RequestParam(value = "key", required = true) String key){
		logger.info("端口key值异步验证ket:{}",key);
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		return res;

	}
	
	/**
	 * 创建端口监控第二步
	 * @param request
	 * @param response
	 * @param model
	 * @param key
	 * @param desc
	 * @param urlAddress
	 * @param accFre
	 * @param accTimeOut
	 * @param timeOutNum
	 * @param alarmInter
     * @return
	 */
	@RequestMapping(value="/create/step2")
	public ModelAndView createStep2(HttpServletRequest request, HttpServletResponse response, ModelAndView model, 
			@RequestParam(value = "monitorType", required = false) String monitorType, 
			@RequestParam(value = "desc", required = false) String desc,
			@RequestParam(value = "overtimes", required = false) String overtimes,
			@RequestParam(value = "portalAddress", required = false) String portalAddress, 
			@RequestParam(value = "accFre", required = false) String accFre, 
			@RequestParam(value = "accTimeOut", required = false) String accTimeOut, 
			@RequestParam(value = "timeOutNum", required = false) String timeOutNum,
			@RequestParam(value = "alarmInter", required = false) String alarmInter){
		model.addObject("monitorType", monitorType);
		model.addObject("accFre", accFre);
		model.addObject("timeOutNum", timeOutNum);
		model.addObject("portalAddress", portalAddress);
		model.addObject("accTimeOut", accTimeOut);
		model.addObject("timeOutNum", timeOutNum);
		model.addObject("alarmInter", alarmInter);
		model.addObject("leftMenu", "portMenu");
		model.setViewName("/portal/buildPortalStep2");
		return model;

	}
	
	/**
	 * 创建端口监控第三步
	 * @param request
	 * @param response
	 * @param model
	 * @param key
	 * @param desc
	 * @param urlAddress
	 * @param accFre
	 * @param accTimeOut
	 * @param timeOutNum
	 * @param alarmInter
	 * @return
	 */
	@RequestMapping(value="/create/step3")
	public ModelAndView createStep3(HttpServletRequest request, HttpServletResponse response, ModelAndView model, 
			@RequestParam(value = "portalAddress", required = true) String portalAddress, 
			@RequestParam(value = "monitorType", required = true) String monitorType, 
			@RequestParam(value = "overtimes", required = true) Integer overtimes,
			@RequestParam(value = "frequency", required = true) Integer frequency){
		logger.info("进入第三步参数@key："+"|portalAddress:"+portalAddress+"|monitorType:"+monitorType+"|overtimes:"+overtimes+"|timeOutNum:"+frequency+"|alarmInter:"+frequency);
		model.setViewName("/portal/buildPortalStep3");
		model.addObject("monitorType", monitorType);
		model.addObject("overtimes", overtimes);
		model.addObject("portalAddress", portalAddress);
		model.addObject("frequency", frequency);
		model.addObject("leftMenu", "portMenu");
		return model;

	}
	
	
	/**
	 * 保存端口监控点
	 * @param request
	 * @param response
	 * @param model
	 * @param content
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/save", method={RequestMethod.POST}, produces = "application/json;charset=utf-8")
	public ResponsesDTO save(HttpServletRequest request, HttpServletResponse response,PortMonitor portal, ModelAndView model){
		portal.setSurvival(1);
		portal.setUpdateTime(new Date());
		portal.setStatus(1);
		portal.setCreateTime(new Date());
		int count =portMonitorService.addPortMonitor(portal);
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		if(count<=0){
			res = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		}
		
		return res;

	}
	/**
	 * 保存修改数据
	 * @param request
	 * @param response
	 * @param model
	 * @param content
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/saveUpdate", method={RequestMethod.POST}, produces = "application/json;charset=utf-8")
	public ResponsesDTO saveUpdate(HttpServletRequest request, HttpServletResponse response, ModelAndView model, PortMonitor port){
		logger.info("port:"+port);
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try{
			port.setUpdateTime(new Date());
			portMonitorService.editPortMonitor(port);
		}catch(Exception e){
			logger.error("系统出现异常", e);
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}

		return res;

	}
	/**
	 * 修改监控点状态
	 * @param request
	 * @param response
	 * @param model
	 * @param id       
	 * @param status
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/changeStatus", method={RequestMethod.POST}, produces = "application/json;charset=utf-8")
	public ResponsesDTO changeStatus(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "id", required = true) String id, 
			@RequestParam(value = "status", required = true) String status){
		logger.info("id:"+id+"|status:"+status);
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try{
			PortMonitor port = new PortMonitor();
			port.setId(Long.parseLong(id));
			port.setStatus(Integer.parseInt(status));
			portMonitorService.editPortMonitor(port);
		}catch(Exception e){
			logger.error("系统出现异常", e);
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}

		return res;

	}
	
	@ResponseBody
	@RequestMapping(value="/del", method={RequestMethod.POST}, produces = "application/json;charset=utf-8")
	public ResponsesDTO del(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "id", required = true) String id){
		logger.info("id:"+id);
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try{
			String[] ids = id.split(",");
			Long[] delIds = new Long[ids.length];
			for(int i = 0; i < ids.length; i++){
				delIds[i] = Long.valueOf(ids[i]);
			}
			portMonitorService.batchDeletePortMonitorByIds(delIds);
		}catch(Exception e){
			logger.error("系统出现异常", e);
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}

		return res;

	}
	
	
	
	/**
	 * port列表页table
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/getPortTable")
	public ModelAndView ajaxList(HttpServletRequest request,
			@RequestParam(value = "port", required = false) String port,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "survival", required = false) Integer survival,
			HttpServletResponse response, ModelAndView model){
		if(pageNo == null || pageNo < 1){
			pageNo = 1;
		}
		if(pageSize == null || pageSize <1){
			pageSize = 10;
		}
		Page<PortMonitor> page = new Page<PortMonitor>(pageNo,pageSize);
		PortMonitor query = new PortMonitor();
		query.setPort(port);
		query.setStartTime(startTime);
		query.setEndTime(endTime);
		query.setSurvival(survival);
		page.setConditions(query);
		page = portMonitorService.findPortMonitorListByPage(page);
		if(survival!=null){
			model.setViewName("/portal/portalWarningTable");
		}else{
			model.setViewName("/portal/portTable");
		}
		model.addObject("page", page);
		return model;

	}
	
	/**
	 * port历史table
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/portHistoryTable")
	public ModelAndView urlHistoryTable(HttpServletRequest request, HttpServletResponse response, ModelAndView model,
		@RequestParam(value = "id", required = true) Long id,
		@RequestParam(value = "startTime", required = false) String startTime,
		@RequestParam(value = "endTime", required = false) String endTime,
		@RequestParam(value = "surival", required = false) Integer surival,
		@RequestParam(value = "pageNo", required = false) Integer pageNo,
		@RequestParam(value = "pageSize", required = false) Integer pageSize){
	logger.info("startTime:"+startTime+"|endTime:"+endTime+"|surival:"+surival);
	try{
		if(pageNo == null || pageNo < 1){
			pageNo = 1;
		}
		if(pageSize == null || pageSize < 1){
			pageSize = 10;
		}
		if(surival == null || surival == 2){
			surival = null;
		}
		Page<PortRecord> page = new Page<PortRecord>(pageNo,pageSize);
		PortRecord record = new PortRecord();
		record.setPid(id);
		record.setStartTime(startTime);
		record.setEndTime(endTime);
		record.setSurvival(surival);
		page.setConditions(record);
		page = portMonitorService.findPortRecordListByPage(page);
		PortMonitor port = portMonitorService.findPortMonitorById(id);
		model.addObject("page", page);
		model.addObject("port", port);
	}catch(Exception e){
		logger.info("eror",e);
		model.setViewName("error");
	}
	model.setViewName("/portal/portHistoryTable");
	return model;

}
	
	/**
	 * port报警table
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/alarmHistoryTable")
	public ModelAndView alarmHistoryTable(HttpServletRequest request, HttpServletResponse response, ModelAndView model,@RequestParam(value = "content", required = true) String content){
		logger.info("执行urlHistoryTable，参数{}",content);
		model.setViewName("/portal/alarmHistoryTable");
		return model;

	}
	
	
	/**
	 * port图标定时刷新
	 * @param request
	 * @param response
	 * @param model
	 * @param content
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/refreshChart", method={RequestMethod.POST}, produces = "application/json;charset=utf-8")
	public ResponsesDTO refreshChart(HttpServletRequest request, HttpServletResponse response, ModelAndView model, @RequestParam(value = "id", required = true) Long id){
		logger.info("id:"+id);
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try{
			    Page<PortRecord> page = new Page<PortRecord>(1,200);
			    PortRecord record = new PortRecord();
			    Calendar cal = Calendar.getInstance();
			    cal.add(Calendar.HOUR, -11);
			    cal.add(Calendar.MINUTE, -10);
			    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    Date start = cal.getTime();
			    Date end = new Date();
			    String startTime =  sdf.format(start);
			    String endTime =  sdf.format(end);
			    logger.info("startTiem:"+startTime);
				record.setPid(id);
				record.setStartTime(startTime);
				record.setEndTime(endTime);
				page.setConditions(record);
				page = portMonitorService.findPortRecordListByPage(page);
				Map<String,Object> mapp=putChart(end,page);
				res.setAttach(mapp);
		}catch(Exception e){
			logger.error("系统出现异常", e);
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		
		
		return res;

	}
	
	/**
	 * 检验端口是否存在
	 * @param request
	 * @param response
	 * @param port
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getByPort", method={RequestMethod.POST}, produces = "application/json;charset=utf-8")
	public ResponsesDTO getByUrl(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "port", required = true) String port){
		logger.info("port"+port);
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try{
			PortMonitor portMonitor = new PortMonitor();
			portMonitor.setPort(port);
			boolean p = portMonitorService.checkPortIsExist(port);
			res.setAttach(p);
		}catch(Exception e){
			logger.error("系统出现异常", e);
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}

		return res;

	}
	
	/**
	 * 展示从当前时间到11个小时前的折线图数据
	 * @param current 当前时间
	 * @param page 分页数据
	 * @return 折线图数据
	 */
	public Map<String,Object> putChart(Date current,Page<PortRecord> page){
		Long cur = current.getTime();
		List<PortRecord> list = page.getData();
		String[] categories = new String[12];
		double[] data = new double[12];
		for(int i=0;i<12;i++){
			long start = cur-(i*60*60000)+10*60000;
			long end = cur-(i*60*60000)-10*60000;
			for(PortRecord record:list){
				if(record.getVisitTime()!=null){
					long visitTime = record.getVisitTime().getTime();
					if(visitTime>=end&&visitTime<=start){
						categories[11-i]=record.getVisitTimeStr();
						data[11-i]=record.getVisitTake();
						break;
					}
				}
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(StringUtils.isBlank(categories[11-i])){
				categories[11-i]=sdf.format(new Date(cur-(i*60*60000)));
				data[11-i]=0;
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mapp = new HashMap<String, Object>();
		map.put("name", "ump");
		map.put("data", data);
		List<Map<String,Object>> lists = new ArrayList<Map<String,Object>>();
		lists.add(map);
		mapp.put("data", lists);
		mapp.put("xAxis", categories);
		String frcStr = "";
		mapp.put("title", "端口存活监控实时展现"+frcStr);
		return mapp;
	}
	

}
