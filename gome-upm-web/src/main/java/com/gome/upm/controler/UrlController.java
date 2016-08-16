package com.gome.upm.controler;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gome.upm.common.Page;
import com.gome.upm.common.util.ResponsesDTO;
import com.gome.upm.constants.ReturnCode;
import com.gome.upm.domain.AlarmRecord;
import com.gome.upm.domain.UrlMonitor;
import com.gome.upm.domain.UrlRecord;
import com.gome.upm.service.UrlMonitorService;

/**
 * url监控
 * @author zhangzhixiang-ds
 *
 */
@Controller
@RequestMapping(value="/url")
public class UrlController {
	
	private Logger logger = LoggerFactory.getLogger(UrlController.class);
	@Resource
	private UrlMonitorService urlMonitorService;
	
	@RequestMapping(value="/get")
	public ModelAndView getAll(HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		logger.info("get url list");
		try{
			Page<UrlMonitor> page = new Page<UrlMonitor>(1,10);
			UrlMonitor query = new UrlMonitor();
			page.setConditions(query);
			page = urlMonitorService.findUrlMonitorListByPage(page);
			query.setSurvival(1);
			int validCount = urlMonitorService.findTotalResultByConditions(query);
			int inValidCount = page.getTotalResult()-validCount;
			
			int exponential =0;
			if(page.getTotalResult()>0){
				exponential = validCount*100/(page.getTotalResult());
			}
			model.setViewName("/url/urlSurvivalMonitor");
			model.addObject("leftMenu", "urlMenu");
			model.addObject("page", page);
			model.addObject("validCount", validCount);
			model.addObject("inValidCount", inValidCount);
			model.addObject("exponential", exponential);
		}catch(Exception e){
			logger.error("error", e);
			model.setViewName("/error");
		}

		return model;

	}
	/**
	 * 报警记录页面
	 * @param request 请求
	 * @param response 相应
	 * @param model 模型
	 * @return 模型
	 */
	@RequestMapping(value="/warning")
	public ModelAndView getWarning(HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		logger.info("get warning url list ");
		try{
			Page<UrlMonitor> page = new Page<UrlMonitor>(1,10);
			UrlMonitor query = new UrlMonitor();
			query.setSurvival(0);
			page.setConditions(query);
			page = urlMonitorService.findUrlMonitorListByPage(page);
			model.setViewName("/url/urlSurvivalMonitorWarning");
			model.addObject("leftMenu", "urlMenu");
			model.addObject("page", page);
		}catch(Exception e){
			logger.error("error", e);
			model.setViewName("/error");
		}

		return model;

	}
	
	/**
	 * 创建url监控点第一步
	 * @param request
	 * @param response
	 * @param model
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
		model.setViewName("/url/buildUrlStep1");
		model.addObject("leftMenu", "urlMenu");
		return model;

	}
	
	/**
	 * 创建url监控点第二部
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/create/step2")
	public ModelAndView createStep2(HttpServletRequest request, HttpServletResponse response, ModelAndView model, 
			@RequestParam(value = "key", required = false) String key, 
			@RequestParam(value = "desc", required = false) String desc,
			@RequestParam(value = "app", required = false) String app,
			@RequestParam(value = "urlAddress", required = false) String urlAddress, 
			@RequestParam(value = "accFre", required = false) String accFre, 
			@RequestParam(value = "accTimeOut", required = false) String accTimeOut, 
			@RequestParam(value = "timeOutNum", required = false) String timeOutNum,
			@RequestParam(value = "alarmInter", required = false) String alarmInter, 
			@RequestParam(value = "method", required = false) String method, 
			@RequestParam(value = "resContent", required = false) String resContent, 
			@RequestParam(value = "isContainsCon", required = false) String isContainsCon,
			@RequestParam(value = "isDefaultCode", required = false) String isDefaultCode, 
			@RequestParam(value = "postParameter", required = false) String postParameter,
			@RequestParam(value = "returnCode", required = false) String returnCode){
		logger.info("paramas:||key:"+key+"|desc:"+desc+"|resContent:"+resContent);
		model.addObject("key", key);
		model.addObject("desc", desc);
		model.addObject("app", app);
		model.addObject("urlAddress", urlAddress);
		model.addObject("accFre", accFre);
		model.addObject("postParameter", postParameter);
		model.addObject("accTimeOut", accTimeOut);
		model.addObject("timeOutNum", timeOutNum);
		model.addObject("alarmInter", alarmInter);
		model.addObject("method", method);
		if(resContent==null){
			model.addObject("resContent", "");
		}else{
			model.addObject("resContent", resContent);
		}
		model.addObject("isContainsCon", isContainsCon);
		model.addObject("isDefaultCode", isDefaultCode);
		if(returnCode==null){
			model.addObject("returnCode", "200,301,302");
		}else{
			model.addObject("returnCode", returnCode);
		}
		model.addObject("leftMenu", "urlMenu");
		model.setViewName("/url/buildUrlStep2");
		return model;

	}
	
	/**
	 * 创建url监控点第三部
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/create/step3")
	public ModelAndView createStep3(HttpServletRequest request, HttpServletResponse response, ModelAndView model, 
			@RequestParam(value = "key", required = false) String key, 
			@RequestParam(value = "desc", required = false) String desc, 
			@RequestParam(value = "postParameter", required = false) String postParameter,
			@RequestParam(value = "urlAddress", required = false) String urlAddress, 
			@RequestParam(value = "accFre", required = false) String accFre, 
			@RequestParam(value = "accTimeOut", required = false) String accTimeOut, 
			@RequestParam(value = "timeOutNum", required = false) String timeOutNum,
			@RequestParam(value = "alarmInter", required = false) String alarmInter, 
			@RequestParam(value = "method", required = false) String method, 
			@RequestParam(value = "resContent", required = false) String resContent, 
			@RequestParam(value = "isContainsCon", required = false) String isContainsCon,
			@RequestParam(value = "isDefaultCode", required = false) String isDefaultCode, 
			@RequestParam(value = "returnCode", required = false) String returnCode,
			@RequestParam(value = "content", required = false) String content){
		logger.info("content:"+content);
		model.setViewName("/url/buildUrlStep3");
		model.addObject("key", key);
		model.addObject("desc", desc);
		model.addObject("postParameter", postParameter);
		model.addObject("urlAddress", urlAddress);
		model.addObject("accFre", accFre);
		model.addObject("accTimeOut", accTimeOut);
		model.addObject("timeOutNum", timeOutNum);
		model.addObject("alarmInter", alarmInter);
		model.addObject("method", method);
		model.addObject("resContent", resContent);
		model.addObject("isContainsCon", isContainsCon);
		model.addObject("isDefaultCode", isDefaultCode);
		model.addObject("returnCode", returnCode);
		model.addObject("leftMenu", "urlMenu");
		return model;

	}
	
	/**
	 * 保存url
	 * @param request
	 * @param response
	 * @param model
	 * @param content
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/save", method={RequestMethod.POST}, produces = "application/json;charset=utf-8")
	public ResponsesDTO save(HttpServletRequest request, HttpServletResponse response, ModelAndView model, @RequestParam(value = "content", required = true) String content){
		logger.info("content:"+content);
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try{
			JSONObject obj = JSON.parseObject(content);
			UrlMonitor urlm = new UrlMonitor();
			urlm.setAlarmMethod(obj.getString("alarmWay"));
			urlm.setCreateTime(new Date());
			urlm.setFrequency(obj.getIntValue("accFre"));
			urlm.setInterval(obj.getIntValue("alarmInter"));
			urlm.setMatchContent(obj.getString("resContent"));
			urlm.setMatchMethod(obj.getString("isContainsCon"));
			urlm.setOvertimes(obj.getIntValue("timeOutNum"));
			urlm.setRequestMethod(obj.getString("method"));
			urlm.setReturnCode(obj.getString("returnCode"));
			urlm.setTimeout(obj.getIntValue("accTimeOut"));
			urlm.setUrl(obj.getString("urlAddress"));
			urlm.setPostParameter(obj.getString("postParameter"));
			urlm.setSurvival(1);
			urlm.setUpdateTime(new Date());
			urlm.setStatus(1);
			urlMonitorService.addUrlMonitor(urlm);
			logger.info(urlm.toString());
		}catch(Exception e){
			logger.error("系统出现异常", e);
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
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
	public ResponsesDTO saveUpdate(HttpServletRequest request, HttpServletResponse response, ModelAndView model, @RequestParam(value = "content", required = true) String content){
		logger.info("content:"+content);
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try{
			JSONObject obj = JSON.parseObject(content);
			UrlMonitor urlm = new UrlMonitor();
			urlm.setId(obj.getLongValue("id"));
			urlm.setAlarmMethod(obj.getString("alarmWay"));
			urlm.setUpdateTime(new Date());
			urlm.setFrequency(obj.getIntValue("accFre"));
			urlm.setInterval(obj.getIntValue("alarmInter"));
			urlm.setMatchContent(obj.getString("resContent"));
			urlm.setMatchMethod(obj.getString("isContainsCon"));
			urlm.setOvertimes(obj.getIntValue("timeOutNum"));
			urlm.setRequestMethod(obj.getString("method"));
			urlm.setReturnCode(obj.getString("returnCode"));
			urlm.setTimeout(obj.getIntValue("accTimeOut"));
			urlm.setUrl(obj.getString("urlAddress"));
			urlm.setPostParameter(obj.getString("postParameter"));
			urlMonitorService.editUrlMonitor(urlm);
		}catch(Exception e){
			logger.error("系统出现异常", e);
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}

		return res;

	}
	
	
	
	@ResponseBody
	@RequestMapping(value="/varifyKey", method={RequestMethod.POST}, produces = "application/json;charset=utf-8")
	public ResponsesDTO varifyKey(HttpServletRequest request, HttpServletResponse response, ModelAndView model, @RequestParam(value = "key", required = true) String key){
		logger.info("URLkey值异步验证ket:{}",key);
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
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
			UrlMonitor url = urlMonitorService.findUrlMonitorById(id);
			Page<UrlRecord> page1 = new Page<UrlRecord>(1,10);
			Page<AlarmRecord> page2 = new Page<AlarmRecord>(1,10);
			if(url != null){
				
				UrlRecord record = new UrlRecord();
				record.setUid(id);
				page1.setConditions(record);
				page1 = urlMonitorService.findUrlRecordListByPage(page1);
				if(url.getUrl().length()>100){
					url.setShortUrl(url.getUrl().substring(0,100)+"...");
				}else{
					url.setShortUrl(url.getUrl());
				}
				/*AlarmRecord alarm = new AlarmRecord();
				alarm.setType(url.getUrl());
				page2.setConditions(alarm);
				page2 = urlMonitorService.findAlarmRecordListByPage(page2);*/
			}else{
				model.setViewName("error");
			}
			
			model.addObject("page", page1);
			//model.addObject("page", page2);
			model.addObject("url", url);
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
        List<UrlMonitor> list = null;
        UrlMonitor url = new UrlMonitor();
        List<UrlMonitor> urlList =urlMonitorService.findUrlMonitorListByUrlMonitor(url);
        Map<String,String> map = new HashMap<String,String>();
        for(UrlMonitor urlMonitor:urlList){
        	map.put(urlMonitor.getUrl(), "");
        }
        try {
        	Workbook book =WorkbookFactory.create(inputStream);
        	list =ExcelUtils.excelImport(UrlMonitor.class, book);
        	List<UrlMonitor> addList = new ArrayList<UrlMonitor>();
        	int pcount=0;
        	int doubleCount =0;
        	Map<String,String> resultMap = new HashMap<String,String>();
        	Pattern p = Pattern.compile("^(http|ftp|https):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?",Pattern.CASE_INSENSITIVE ); 
        	for(UrlMonitor urlMonitor:list){
        		if(!StringUtils.isBlank(urlMonitor.getUrl())){
        			Matcher m =p.matcher(urlMonitor.getUrl());
        			
        			if(!m.find()){
        				pcount++;
        				continue;
        			}
        			if(!map.containsKey(urlMonitor.getUrl())){
        				if(resultMap.containsKey(urlMonitor.getUrl())){
        					doubleCount++;
        					continue;
        				}
        				resultMap.put(urlMonitor.getUrl(), "");
        				urlMonitor.setTimeout(0);
        				if(StringUtils.isBlank(urlMonitor.getReturnCode())){
        					urlMonitor.setReturnCode("200,301,302");
        				}else{
        					String[] s=urlMonitor.getReturnCode().split(",");
        					if(s.length>0){
        						boolean go = true;
        						for(int i=0;i<s.length;i++){
        							Integer number = 0;
        							try{
        								number =Integer.parseInt(s[i]);
        							}catch(Exception e){
        								pcount++;
        								go = false;
        		        				break;
        							}
        							if(number<200||number>600){
        								pcount++;
        								go = false;
        								break;
        							}
        						}
        						if(!go){
        							continue;
        						}
        					}else{
        						Integer number = 0;
    							try{
    								number =Integer.parseInt(urlMonitor.getReturnCode());
    							}catch(Exception e){
    								pcount++;
    		        				continue;
    							}
        						if(number<200||number>600){
    								pcount++;
    		        				continue;
    							}
        					}
        					
        				}
        				if(urlMonitor.getFrequency()==null){
        					urlMonitor.setFrequency(5);
        				}
        				if(urlMonitor.getOvertimes()==null){
        					urlMonitor.setOvertimes(5);
        				}
        				if(StringUtils.isBlank(urlMonitor.getAlarmMethod())){
        					urlMonitor.setAlarmMethod("email");
        				}
        				if(StringUtils.isBlank(urlMonitor.getMatchMethod())){
        					urlMonitor.setMatchMethod("exclude");
        				}
        				urlMonitor.setRequestMethod("GET");
        				urlMonitor.setCreateTime(new Date());
        				urlMonitor.setStatus(1);
        				urlMonitor.setUpdateTime(new Date());
        				urlMonitor.setSurvival(1);
        				addList.add(urlMonitor);
        			}else{
        				doubleCount++;
        			}
        		}else{
        			pcount++;
        		}
        	}
        	int count = 0;
        	if(addList.size()>0){
        		count=urlMonitorService.batchAddUrlMonitor(addList);
        	}
            response.getWriter().write("URL存活监控数据导入成功:"+count+"条,URL地址或请求返回码格式错误:"+pcount+"条,重复数据:"+doubleCount+"条");
        } catch (Exception e) {
        	logger.info("eror",e);
            response.getWriter().write(e.getMessage());
        }
	}
	
	/**
	 * url历史table
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/urlHistoryTable")
	public ModelAndView urlHistoryTable(HttpServletRequest request, HttpServletResponse response, ModelAndView model,
			@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value = "surival", required = false) Integer surival,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize){
		logger.info("startTime:"+startTime+"|endTime:"+endTime+"|surival:"+surival);
		try{
//			if(StringUtils.isBlank((String) request.getSession().getAttribute("userName"))){
//			model.setViewName("sessionout");
//			return model;
//		}
			if(pageNo == null || pageNo < 1){
				pageNo = 1;
			}
			if(pageSize == null || pageSize < 1){
				pageSize = 10;
			}
			if(surival == null || surival == 2){
				surival = null;
			}
			Page<UrlRecord> page = new Page<UrlRecord>(pageNo,pageSize);
			UrlRecord record = new UrlRecord();
			record.setUid(id);
			record.setStartTime(startTime);
			record.setEndTime(endTime);
			record.setSurvival(surival);
			page.setConditions(record);
			page = urlMonitorService.findUrlRecordListByPage(page);
			UrlMonitor url = urlMonitorService.findUrlMonitorById(id);
			if(url.getUrl().length()>100){
				url.setShortUrl(url.getUrl().substring(0, 100)+"...");
			}else{
				url.setShortUrl(url.getUrl());
			}
			model.addObject("page", page);
			model.addObject("url", url);
		}catch(Exception e){
			logger.info("eror",e);
			model.setViewName("error");
		}
		model.setViewName("/url/urlHistoryTable");
		return model;

	}
	
	/**
	 * url报警table
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/alarmHistoryTable")
	public ModelAndView alarmHistoryTable(HttpServletRequest request, HttpServletResponse response, ModelAndView model,
			@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "content", required = true) String content,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize){
		logger.info("执行urlHistoryTable，参数{}",content);
		model.setViewName("/url/alarmHistoryTable");
		try{
//			if(StringUtils.isBlank((String) request.getSession().getAttribute("userName"))){
//			model.setViewName("sessionout");
//			return model;
//		}
			if(pageNo == null || pageNo < 1){
				pageNo = 1;
			}
			if(pageSize == null || pageSize < 1){
				pageSize = 10;
			}
			UrlMonitor url = urlMonitorService.findUrlMonitorById(id);
			if(url == null){
				model.setViewName("error");
			}
			Page<AlarmRecord> page2 = new Page<AlarmRecord>(pageNo,pageSize);
			AlarmRecord alarm = new AlarmRecord();
			alarm.setType(url.getUrl());
			alarm.setEndTime(endTime);
			alarm.setStartTime(startTime);
			alarm.setContent(content);
			page2.setConditions(alarm);
			page2 = urlMonitorService.findAlarmRecordListByPage(page2);
			model.addObject("page", page2);
		}catch(Exception e){
			logger.info("eror",e);
			model.setViewName("error");
		}
		return model;

	}
	
	
	/**
	 * url图标定时刷新
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
			    Page<UrlRecord> page = new Page<UrlRecord>(1,200);
			    UrlRecord record = new UrlRecord();
			    Calendar cal = Calendar.getInstance();
			    cal.add(Calendar.HOUR,-11);
			    cal.add(Calendar.MINUTE,-10);
			    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    String startTime =  sdf.format(cal.getTime());
			    Date current = new Date();
			    String endTime =  sdf.format(current);
			    logger.info("startTiem:"+startTime);
				record.setUid(id);
				record.setStartTime(startTime);
				record.setEndTime(endTime);
				page.setConditions(record);
				page = urlMonitorService.findUrlRecordListByPage(page);
				Map<String,Object> maps =putChart(current,page);
				res.setAttach(maps);
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
	public Map<String,Object> putChart(Date current,Page<UrlRecord> page){
		Long cur = current.getTime();
		List<UrlRecord> list = page.getData();
		String[] categories = new String[12];
		double[] data = new double[12];
		for(int i=0;i<12;i++){
			long start = cur-(i*60*60000)+10*60000;
			long end = cur-(i*60*60000)-10*60000;
			for(UrlRecord record:list){
				if(record.getVisitTime()!=null){
					long visitTime = record.getVisitTime().getTime();
					if(visitTime>=end&&visitTime<=start){
						String timeStr = record.getVisitTimeStr();
						categories[11-i]=timeStr.substring(5, timeStr.length());
						data[11-i]=record.getVisitTake();
						break;
					}
				}
			}
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");
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
		mapp.put("title", "URL存活监控实时展现"+frcStr);
		return mapp;
	}
	

	
	
	/**
	 * 批量删除监控点
	 * @param request
	 * @param response
	 * @param model
	 * @param id
	 * @return
	 */
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
			urlMonitorService.batchDeleteUrlMonitorByIds(delIds);
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
			UrlMonitor url = new UrlMonitor();
			url.setId(Long.parseLong(id));
			url.setStatus(Integer.parseInt(status));
			urlMonitorService.editUrlMonitor(url);
		}catch(Exception e){
			logger.error("系统出现异常", e);
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}

		return res;

	}
	
	/**
	 * 监控点列表搜索
	 * @param request
	 * @param response
	 * @param model
	 * @param startTime
	 * @param endTime
	 * @param status
	 * @param urlAddress
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/getUrlTable", method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView getUrlTable(HttpServletRequest request, HttpServletResponse response, ModelAndView model, 
			@RequestParam(value = "startTime", required = false) String startTime, 
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value = "survival", required = false) Integer survival,
			@RequestParam(value = "urlAddress", required = false) String urlAddress,
			@RequestParam(value = "status", required = false) Integer status,
			@RequestParam(value = "isWarning", required = false) Integer isWarning,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize){
		logger.info("|survival:"+survival+"|startTime:"+startTime+"|endTime:"+endTime+"|urlAddress:"+urlAddress+"|pageNo:"+pageNo+"|pageSize:"+pageSize);
		
		if(isWarning==null){
			model.setViewName("/url/urlTable");
			
		}else{
			model.setViewName("/url/urlWarningTable");
		}
		try{
//			if(StringUtils.isBlank((String) request.getSession().getAttribute("userName"))){
//				model.setViewName("sessionout");
//				return model;
//			}
			if(survival == null || survival == 2){
				survival = null;
			}
			
			if(pageNo == null || pageNo < 1){
				pageNo = 1;
			}
			if(pageSize == null || pageSize <1){
				pageSize = 10;
			}
			Page<UrlMonitor> page = new Page<UrlMonitor>(pageNo,pageSize);
			UrlMonitor query = new UrlMonitor();
			query.setStartTime(startTime);
			query.setEndTime(endTime);
			query.setSurvival(survival);
			query.setUrl(urlAddress);
			query.setStatus(status);
			page.setConditions(query);
			page = urlMonitorService.findUrlMonitorListByPage(page);
			query.setStatus(1);
			int validCount = urlMonitorService.findTotalResultByConditions(query);
			int inValidCount = page.getTotalResult()-validCount;
			model.addObject("page", page);
			model.addObject("validCount", validCount);
			model.addObject("inValidCount", inValidCount);
		}catch(Exception e){
			logger.error("系统出现异常", e);
			model.setViewName("error");
		}

		return model;

	}
	
	
	/**
	 * url id获取url信息
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
			UrlMonitor url = urlMonitorService.findUrlMonitorById(id);
			res.setAttach(url);
		}catch(Exception e){
			logger.error("系统出现异常", e);
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}

		return res;

	}
	
	
	@ResponseBody
	@RequestMapping(value="/getByUrl", method={RequestMethod.POST}, produces = "application/json;charset=utf-8")
	public ResponsesDTO getByUrl(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "url", required = true) String url){
		logger.info("url"+url);
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try{
			UrlMonitor urlMonitor = new UrlMonitor();
			urlMonitor.setUrl(url);
			int count = urlMonitorService.checkUrlIsExist(url) ? 1 : 0;
			res.setAttach(count);
		}catch(Exception e){
			logger.error("系统出现异常", e);
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}

		return res;

	}
	
	/**
	 * 验证url是否正确
	 * @param request
	 * @param response
	 * @param url
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/isRealyUrl", method={RequestMethod.POST}, produces = "application/json;charset=utf-8")
	public ResponsesDTO isRealyUrl(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "url", required = true) String url){
		logger.info("url"+url);
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try {  
	         URL urlAddress = new URL(url);  
	         urlAddress.openStream();  
	         res.setAttach(true);
	    } catch (Exception e) {  
	    	logger.error("连接打不开", e);
	         res.setAttach(false);
	    }  
			
		return res;

	}
	
}
