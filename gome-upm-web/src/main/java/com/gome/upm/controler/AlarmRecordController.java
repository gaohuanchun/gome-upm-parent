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
import com.gome.upm.domain.AlarmRecord;
import com.gome.upm.domain.DBConnection;
import com.gome.upm.service.AlarmRecordService;

/**
 * 报警记录控制器.
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年07月26日    caowei    新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/alarm")
public class AlarmRecordController extends BaseController {

	@Resource
	private AlarmRecordService alarmRecordService;
	
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
	 */
	@RequestMapping(value="/list", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toAlarmListView(@Param(value="content")String content, @Param(value="page")String page, @Param(value="size")String size, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		if(StringUtils.isEmpty(content)){
			model.setViewName("/alarm/alarmList");
			
			//下拉列表
			List<AlarmRecord> alarmList = alarmRecordService.findAllTypes();
			model.addObject("alarmList", alarmList);
		} else {
			model.setViewName("/alarm/alarmTable");
		}
		
		model.addObject("leftMenu", "alarmMenu");
		
		int pageNo = 1;
		if(StringUtils.isNotEmpty(page)){
			pageNo = Integer.parseInt(page);
		}
		
		int pageSize = Constant.PAGE_SIZE;
		if(StringUtils.isNotEmpty(size)){
			pageSize = Integer.parseInt(size);
		}
		Page<AlarmRecord> p = new Page<AlarmRecord>(pageNo, pageSize);
		
		AlarmRecord condition = null;
		//通过数据库类型查找
		if(StringUtils.isNotEmpty(content)){
			condition = JSON.parseObject(content, AlarmRecord.class);
		} else {
			condition = new AlarmRecord();
		}
		p.setConditions(condition);
		Page<AlarmRecord> pageAlarm = alarmRecordService.findAlarmRecordListByPage(p);
		model.addObject("page", pageAlarm);
		return model;
	}
	
	/**
	 * 
	 * 跳转到报警详情页.
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
	@RequestMapping(value="/detail", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toAlarmDetailView(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/alarm/detail");
		
		model.addObject("leftMenu", "alarmMenu");
		
		long id2 = 0;
		if(StringUtils.isNotEmpty(id)){
			id2 = Long.parseLong(id);
		} else {
			return model;
		}
		
		AlarmRecord alarm = alarmRecordService.findAlarmRecordById(id2);
		model.addObject("alarm", alarm);
		return model;
	}
	
}
