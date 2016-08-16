package com.gome.upm.controler;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.gome.upm.common.Constant;
import com.gome.upm.common.Page;
import com.gome.upm.controler.base.BaseController;
import com.gome.upm.domain.DBConnection;
import com.gome.upm.domain.ThresholdConfig;
import com.gome.upm.domain.ThresholdHistory;
import com.gome.upm.service.DBConnectionService;
import com.gome.upm.service.ThresholdConfigService;
import com.gome.upm.service.ThresholdHistoryService;

/**
 * 数据库控制器.
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年07月19日    caowei    新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/db")
public class DbController extends BaseController {
	
	@Resource
	private ThresholdConfigService thresholdConfigService;
	
	@Resource
	private ThresholdHistoryService thresholdHistoryService;

	@Resource
	private DBConnectionService dBConnectionService;
	
	/**
	 * 
	 * 跳转到数据库连接与表空间监控页.
	 *
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月19日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/index", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toIndexView(HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/db/baseTable");
		model.addObject("leftMenu", "dbMenu");
		
		int connLevel1 = 0;
		int connLevel2 = 0;
		int tbsLevel1 = 0;
		int tbsLevel2 = 0;
		int asmLevel1 = 0;
		int asmLevel2 = 0;
		//查询报警级别为一般的个数
		List<ThresholdConfig> level1List = thresholdConfigService.findTotalResultByAlarmLevel(1);
		for (ThresholdConfig thresholdConfig : level1List) {
			if("CONN".equals(thresholdConfig.getDataType())){
				connLevel1 = thresholdConfig.getNum();
			} else if("TBS".equals(thresholdConfig.getDataType())){
				tbsLevel1 = thresholdConfig.getNum();
			} else if("ASM".equals(thresholdConfig.getDataType())){
				asmLevel1 = thresholdConfig.getNum();
			}
		}
		//查询报警级别为严重的个数
		List<ThresholdConfig> level2List = thresholdConfigService.findTotalResultByAlarmLevel(2);
		for (ThresholdConfig thresholdConfig : level2List) {
			if("CONN".equals(thresholdConfig.getDataType())){
				connLevel2 = thresholdConfig.getNum();
			} else if("TBS".equals(thresholdConfig.getDataType())){
				tbsLevel2 = thresholdConfig.getNum();
			} else if("ASM".equals(thresholdConfig.getDataType())){
				asmLevel2 = thresholdConfig.getNum();
			}
		}
		
		model.addObject("connLevel1", connLevel1);
		model.addObject("connLevel2", connLevel2);
		model.addObject("tbsLevel1", tbsLevel1);
		model.addObject("tbsLevel2", tbsLevel2);
		model.addObject("asmLevel1", asmLevel1);
		model.addObject("asmLevel2", asmLevel2);
		
		//查询连接数异常的top5
		List<ThresholdConfig> connList= thresholdConfigService.findConnTop5("CONN");
		
		//查询表空间过载的top5
		List<ThresholdConfig> tbsList= thresholdConfigService.findTbsTop5("TBS");
		
		model.addObject("connList", connList);
		
		model.addObject("tbsList", tbsList);
		return model;
	}
	
	/**
	 * 
	 * 跳转到异常记录页.
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
	 */
//	@RequestMapping(value="/alarm", method={RequestMethod.GET,RequestMethod.POST})
//	public ModelAndView toAlarmView(@Param(value="dataType")String dataType, @Param(value="alarmLevel")String alarmLevel, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
//		model.setViewName("/db/alarmlog");
//		model.addObject("leftMenu", "dbMenu");
//		
//		ThresholdConfig thresholdConfig = new ThresholdConfig();
//		thresholdConfig.setDataType(dataType);
//		thresholdConfig.setAlarmLevel(Integer.parseInt(alarmLevel));
//		List<ThresholdConfig> alarmList = thresholdConfigService.findAlarmLog(thresholdConfig);
//		model.addObject("alarmList", alarmList);
//		model.addObject("dataType", dataType);
//		return model;
//	}
	
	@RequestMapping(value="/alarm", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toAlarmView(@Param(value="dataType")String dataType, @Param(value="alarmLevel")String alarmLevel, @Param(value="content")String content, @Param(value="page")String page, @Param(value="size")String size, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		if(StringUtils.isEmpty(content)){
			model.setViewName("/db/alarmList");
		} else if("CONN".equals(dataType)) {
			model.setViewName("/db/alarmConnTable");
		} else if("TBS".equals(dataType)) {
			model.setViewName("/db/alarmTbsTable");
		} else if("ASM".equals(dataType)) {
			model.setViewName("/db/alarmAsmTable");
		}
		
		model.addObject("leftMenu", "dbMenu");
		model.addObject("dataType", dataType);
		model.addObject("alarmLevel", alarmLevel);
		
		int pageNo = 1;
		if(StringUtils.isNotEmpty(page)){
			pageNo = Integer.parseInt(page);
		}
		
		int pageSize = Constant.PAGE_SIZE;
		if(StringUtils.isNotEmpty(size)){
			pageSize = Integer.parseInt(size);
		}
		Page<ThresholdConfig> p = new Page<ThresholdConfig>(pageNo, pageSize);
		
		ThresholdConfig config = null;
		//通过数据库类型查找
		if(StringUtils.isNotEmpty(content)){
			config = JSON.parseObject(content, ThresholdConfig.class);
			config.setDataType(dataType);
			config.setAlarmLevel(Integer.parseInt(alarmLevel));
		} else {
			config = new ThresholdConfig();
			config.setDataType(dataType);
			config.setAlarmLevel(Integer.parseInt(alarmLevel));
		}
		p.setConditions(config);
		Page<ThresholdConfig> pageConfig = thresholdConfigService.findThresholdConfigListByPage(p);
		model.addObject("page", pageConfig);
		return model;
	}
	
	/**
	 * 
	 * 跳转到异常历史记录页.
	 *
	 * @param dataType
	 * 				数据类型
	 * @param pid
	 * 				外键
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月19日    caowei    新建
	 * </pre>
	 */
//	@RequestMapping(value="/history", method={RequestMethod.GET,RequestMethod.POST})
//	public ModelAndView toHistoryView(@Param(value="dataType")String dataType, @Param(value="pid")String pid, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
//		model.setViewName("/db/alarmHistory");
//		model.addObject("leftMenu", "dbMenu");
//		model.addObject("dataType", dataType);
//		
//		ThresholdHistory thresholdHistory = new ThresholdHistory();
//		if(StringUtils.isNotEmpty(pid)){
//			thresholdHistory.setPid(Long.parseLong(pid));
//		} else {
//			return model;
//		}
//		
//		List<ThresholdHistory> historyList = thresholdHistoryService.findThresholdHistoryListByThresholdHistory(thresholdHistory);
//		model.addObject("historyList", historyList);
//		
//		return model;
//	}
	
	@RequestMapping(value="/history", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toHistoryView(@Param(value="dataType")String dataType, @Param(value="pid")String pid, @Param(value="content")String content, @Param(value="page")String page, @Param(value="size")String size, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		if(StringUtils.isEmpty(content)){
			model.setViewName("/db/historyList");
		} else if("CONN".equals(dataType)) {
			model.setViewName("/db/historyConnTable");
		} else if("TBS".equals(dataType)) {
			model.setViewName("/db/historyTbsTable");
		} else if("ASM".equals(dataType)) {
			model.setViewName("/db/historyAsmTable");
		}
		
		model.addObject("leftMenu", "dbMenu");
		model.addObject("dataType", dataType);
		model.addObject("pid", pid);
		
		int pageNo = 1;
		if(StringUtils.isNotEmpty(page)){
			pageNo = Integer.parseInt(page);
		}
		
		int pageSize = Constant.PAGE_SIZE;
		if(StringUtils.isNotEmpty(size)){
			pageSize = Integer.parseInt(size);
		}
		Page<ThresholdHistory> p = new Page<ThresholdHistory>(pageNo, pageSize);
		
		ThresholdHistory history = null;
		//通过数据库类型查找
		if(StringUtils.isNotEmpty(content)){
			history = JSON.parseObject(content, ThresholdHistory.class);
			history.setPid(Long.parseLong(pid));
		} else {
			history = new ThresholdHistory();
			history.setPid(Long.parseLong(pid));
		}
		p.setConditions(history);
		Page<ThresholdHistory> pageHistory = thresholdHistoryService.findThresholdHistoryListByPage(p);
		model.addObject("page", pageHistory);
		return model;
	}
	
	/**
	 * 
	 * 跳转到所有连接数记录页.
	 *
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月19日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/allConn", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toAllConnView(@Param(value="content")String content, @Param(value="page")String page, @Param(value="size")String size, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		if(StringUtils.isEmpty(content)){
			model.setViewName("/db/connList");

			//下拉列表
			List<DBConnection> dbList = dBConnectionService.findAllDbTypes();
			model.addObject("dbList", dbList);
		} else {
			model.setViewName("/db/connTable");
		}
		
		model.addObject("leftMenu", "dbMenu");
		
		int pageNo = 1;
		if(StringUtils.isNotEmpty(page)){
			pageNo = Integer.parseInt(page);
		}
		
		int pageSize = Constant.PAGE_SIZE;
		if(StringUtils.isNotEmpty(size)){
			pageSize = Integer.parseInt(size);
		}
		Page<DBConnection> p = new Page<DBConnection>(pageNo, pageSize);
		
		DBConnection conn = null;
		//通过数据库类型查找
		if(StringUtils.isNotEmpty(content)){
			conn = JSON.parseObject(content, DBConnection.class);
		} else {
			conn = new DBConnection();
		}
		p.setConditions(conn);
		Page<DBConnection> pageConn = dBConnectionService.findDBConnectionListByPage(p);
		model.addObject("page", pageConn);
		return model;
	}
	
}
