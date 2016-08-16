package com.gome.upm.controler;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.gome.upm.common.Page;
import com.gome.upm.common.util.ResponsesDTO;
import com.gome.upm.constants.ReturnCode;
import com.gome.upm.domain.AlarmRule;
import com.gome.upm.domain.Application;
import com.gome.upm.domain.ApplicationInfo;
import com.gome.upm.domain.BusinessLine;
import com.gome.upm.domain.ConfigArgs;
import com.gome.upm.domain.MailInfo;
import com.gome.upm.service.AlarmRuleMaintainService;
import com.gome.upm.service.ApplicationsMaintainService;
import com.gome.upm.service.BusinessLineService;
import com.gome.upm.service.util.DBContextHolder;

/**
 * 操作应用的控制器
 * 
 * @author zhouyaliang
 *
 */
@Controller
@RequestMapping("/usr/applications")
public class ApplicationConfigController {

	private Logger logger = LoggerFactory.getLogger(ApplicationConfigController.class);

	@Resource(name = "applicationService")
	private ApplicationsMaintainService applicationService;

	@Resource
	private BusinessLineService businessLineService;

	@Resource
	private AlarmRuleMaintainService alarmRuleMaintainService;

	/**
	 * 查询应用列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView getList(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
		logger.info("get application list");
		DBContextHolder.setDataSource("dataSourceOne");
		try {
			Page<ApplicationInfo> page = new Page<ApplicationInfo>(1, 10);
			ApplicationInfo query = new ApplicationInfo();
			page.setConditions(query);
			page = applicationService.loadApplication(page);
			model.setViewName("/app/applist/appList");
			model.addObject("leftMenu", "applist");
			model.addObject("page", page);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("error", e);
			model.setViewName("/error");
		}

		return model;
	}

	/**
	 * 根据条件查询
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param sts
	 * @param appCode
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/getAppTable", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView getAppTable(HttpServletRequest request, HttpServletResponse response, ModelAndView model,
			@RequestParam(value = "sts", required = false) String sts,
			@RequestParam(value = "appCode", required = false) String appCode,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {
		logger.info("|sts:" + sts + "|appCode:" + appCode + "|pageNo:" + pageNo + "|pageSize:" + pageSize);
		model.setViewName("/app/applist/appTable");
		String useName = (String)request.getSession().getAttribute("userName");
		
		try {
			if("".equals(useName)){
				model.setViewName("sessionout");
				return model;
			}
			if (sts == null || sts == "") {
				sts = null;
			}
			if (appCode == null || appCode == "") {
				appCode = null;
			}

			if (pageNo == null || pageNo < 1) {
				pageNo = 1;
			}
			if (pageSize == null || pageSize < 1) {
				pageSize = 10;
			}
			Page<ApplicationInfo> page = new Page<ApplicationInfo>(pageNo, pageSize);
			ApplicationInfo query = new ApplicationInfo();
			query.setSts(sts);
			query.setAppCode(appCode);
			page.setConditions(query);
			page = applicationService.loadApplication(page);
			model.addObject("page", page);
		} catch (Exception e) {
			logger.error("系统出现异常", e);
			model.setViewName("error");
		}

		return model;
	}

	/**
	 * 根据主键删除
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/del", method = { RequestMethod.POST }, produces = "application/json;charset=utf-8")
	public ResponsesDTO del(HttpServletRequest request, HttpServletResponse response,
					@RequestParam(value = "id", required = true) String id, @RequestParam(value = "code", required = true) String code) {
		logger.info("id:" + id + " code:" + code);
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		String useName = (String)request.getSession().getAttribute("userName");
		try{
			if(!"".equals(useName)){
				AlarmRule alarmRuleParam = new AlarmRule();
				alarmRuleParam.setAppId(code);
				alarmRuleParam.setUid(useName);
				AlarmRule alarmRule = alarmRuleMaintainService.queryAlarmRule(alarmRuleParam);
				if(alarmRule != null){
					alarmRule.setSts("D");
					alarmRuleMaintainService.updateAlarmRule(alarmRule);
				}
				
				applicationService.deleteAppById(Integer.parseInt(id));
			}else{
				res.setAttach("请登录");
				res.setReturnCode(ReturnCode.ACTIVE_FAILURE);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("系统出现异常", e);
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return res;
	}
/*	public ResponsesDTO del(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "id", required = true) String id) {
		logger.info("id:" + id);
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		String useName = (String)request.getSession().getAttribute("userName");
		if(!"".equals(useName)){
			try {
				String[] ids = id.split(",");
				Long[] delIds = new Long[ids.length];
				for (int i = 0; i < ids.length; i++) {
					delIds[i] = Long.valueOf(ids[i]);
					AlarmRule alarmRule = alarmRuleMaintainService.queryAlarmRule(useName, String.valueOf(ids[i]));
					if(alarmRule != null){
						alarmRule.setSts("D");
						alarmRuleMaintainService.updateAlarmRule(alarmRule);
					}
				}
				applicationService.batchDeleteAppByIds(delIds);
				
				
			} catch (Exception e) {
				logger.error("系统出现异常", e);
				res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
			}
		}else{
			res.setAttach("请登录");
			res.setReturnCode(ReturnCode.ACTIVE_FAILURE);
		}

		return res;
	}*/

	/**
	 * 新增
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	public ModelAndView toAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
		// 查询业务线下拉框数据
		List<BusinessLine> blist = businessLineService.loadSelectData();
		model.addObject("page", blist);
		model.setViewName("/app/applist/add");
		model.addObject("leftMenu", "applist");
		return model;
	}

	/**
	 * 初始化新增页面参数
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/init")
	public ModelAndView paramInit(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
		// 查询是否存在全局配置
		AlarmRule alarmRule = null;
		String userName = (String) request.getSession().getAttribute("userName");
		try {
			if (!"".equals(userName)) {
				alarmRule = alarmRuleMaintainService.queryGlobalAlarmRule(userName);
			}
			if (alarmRule != null) {
				String mailTo = "";
				String mailCc = "";
				String configArgs = alarmRule.getConfigArgs();
				JSONArray ttArray = JSON.parseObject(configArgs).getJSONObject("mailInfo").getJSONArray("mailTo");
				JSONArray ccArray = JSON.parseObject(configArgs).getJSONObject("mailInfo").getJSONArray("mailCc");
				for (int i = 0; i < ttArray.size(); i++) {
					if (i == ttArray.size() - 1) {
						mailTo += ttArray.getString(i);
					} else {
						mailTo += ttArray.getString(i) + ",";
					}
				}
				for (int i = 0; i < ccArray.size(); i++) {
					if (i == ccArray.size() - 1) {
						mailCc += ccArray.getString(i);
					} else {
						mailCc += ccArray.getString(i) + ",";
					}
				}
				model.addObject("period", (JSON.parseObject(configArgs) != null) ? JSON.parseObject(configArgs).get("period") : "");
				model.addObject("mailTo", mailTo);
				model.addObject("mailCc", mailCc);
				model.addObject("result", alarmRule);
			} else {
				model.addObject("configArgs", "");
				model.addObject("result", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("系统出现异常", e);
			model.setViewName("error");
		}

		return model;
	}

	/**
	 * 保存应用
	 * @param request
	 * @param response
	 * @param model
	 * @param appCode
	 * @param appDesc
	 * @param businessLine
	 * @param extend
	 * @param period
	 * @param mailTo
	 * @param mailCc
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = { RequestMethod.POST }, produces = "application/json;charset=utf-8")
	public ResponsesDTO save(HttpServletRequest request, HttpServletResponse response, ModelAndView model,
			@RequestParam(value = "appCode", required = false) String appCode,
			@RequestParam(value = "appDesc", required = false) String appDesc,
			@RequestParam(value = "businessLine", required = false) String businessLine,
			@RequestParam(value = "isGlobalConfig", required = false) String isGlobalConfig,
			@RequestParam(value = "period", required = false) String period,
			@RequestParam(value = "mailTo", required = false) String mailTo[],
			@RequestParam(value = "mailCc", required = false) String mailCc[],
			@RequestParam(value = "isUpdateGlobalConfig", required = false) String isUpdateGlobalConfig) {
		
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		String userName = (String) request.getSession().getAttribute("userName");
		try{
			if (userName != null && !"".equals(userName)) {
				if (isExist(appCode)) {
					res.setAttach("已经存在,修改后重试");
				} else {
					Application application = new Application();
					application.setUId(userName);
					application.setAppCode(appCode);
					application.setCreateTime(application.getCreateTime());
					application.setSts("A");
					application.setUpdateTime(application.getUpdateTime());
					application.setAppDesc(appDesc);
					application.setBusinessLine(businessLine);
					applicationService.insertApplication(application);
					
					if("true".equals(isGlobalConfig)){
						if("true".equals(isUpdateGlobalConfig)){
							ConfigArgs configArgs = new ConfigArgs();
							MailInfo mailInfo = new MailInfo();
							mailInfo.setMailTo(mailTo);
							mailInfo.setMailCc(mailCc);
							configArgs.setMailInfo(mailInfo);
							configArgs.setPeriod(Integer.parseInt(period));
							
							AlarmRule alarmRule = new AlarmRule();
			                alarmRule.setTodoType("0");
			                alarmRule.setModifyTime(alarmRule.getModifyTime());
			                alarmRule.setConfigArgs(JSON.toJSONString(configArgs));
			                alarmRuleMaintainService.updateAlarmRule(alarmRule);
						}
					}else{
						ConfigArgs configArgs = new ConfigArgs();
						MailInfo mailInfo = new MailInfo();
						mailInfo.setMailTo(mailTo);
						mailInfo.setMailCc(mailCc);
						configArgs.setMailInfo(mailInfo);
						configArgs.setPeriod(Integer.parseInt(period));
						
						AlarmRule alarmRule = new AlarmRule();
		                alarmRule.setIsGlobal("0");
		                alarmRule.setTodoType("0");
		                alarmRule.setSts("A");
		                alarmRule.setUid(userName);
		                alarmRule.setAppId(appCode);
		                alarmRule.setCreateTime(alarmRule.getCreateTime());
		                alarmRule.setModifyTime(alarmRule.getModifyTime());
		                alarmRule.setConfigArgs(JSON.toJSONString(configArgs));
		                alarmRuleMaintainService.saveAlarmRule(alarmRule);
					}
				}
			}else{
				res.setAttach("请重新登录");
				res.setReturnCode(ReturnCode.ACTIVE_FAILURE);
			}
		}catch(Exception e){
			e.printStackTrace();
			res.setReturnCode(ReturnCode.ACTIVE_FAILURE);
		}

		return res;
	}

	/**
	 * 新增全局报警配置
	 * @param request
	 * @param response
	 * @param model
	 * @param period
	 * @param mailTo
	 * @param mailCc
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addGlobalConfig", method = {RequestMethod.POST }, produces = "application/json;charset=utf-8")
	public ResponsesDTO saveGlobalConfig(HttpServletRequest request, HttpServletResponse response, ModelAndView model,
			@RequestParam(value = "period", required = true) String period,
			@RequestParam(value = "mailTo", required = true) String[] mailTo,
			@RequestParam(value = "mailCc", required = true) String[] mailCc) {
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);

		String userName = (String) request.getSession().getAttribute("userName");
		if (!"".equals(userName)) {
			ConfigArgs configArgs = new ConfigArgs();
			MailInfo mailInfo = new MailInfo();
			mailInfo.setMailTo(mailTo);
			mailInfo.setMailCc(mailCc);
			configArgs.setMailInfo(mailInfo);
			configArgs.setPeriod(Integer.parseInt(period));

			AlarmRule alarmRule = new AlarmRule();
			alarmRule.setUid(userName);
			alarmRule.setConfigArgs(JSON.toJSONString(configArgs));
			alarmRule.setIsGlobal("1");
			alarmRule.setTodoType("0");
			alarmRule.setSts("A");
			alarmRule.setCreateTime(alarmRule.getCreateTime());
			alarmRule.setCreateTime(alarmRule.getModifyTime());
			try {
				int count = alarmRuleMaintainService.saveAlarmRule(alarmRule);
				if (count > 0) {
					res.setAttach("新增成功");
					res.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
				} else {
					res.setAttach("新增失败");
					res.setReturnCode(ReturnCode.ACTIVE_FAILURE);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				res.setAttach("新增失败");
				res.setReturnCode(ReturnCode.ACTIVE_FAILURE);
			}
		}

		return res;
	}

	// 验证编码是否已经存在
	private boolean isExist(String appCode) {
		return applicationService.checkApplicationExist(appCode);
	}
}
