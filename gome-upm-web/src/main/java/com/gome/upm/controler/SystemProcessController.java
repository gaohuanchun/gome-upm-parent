package com.gome.upm.controler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.gome.upm.common.Page;
import com.gome.upm.domain.PcInfo;
import com.gome.upm.domain.ServerInfo;
import com.gome.upm.service.SystemProcessService;

@Controller
@RequestMapping(value="/system")
public class SystemProcessController {
	
	private final static Logger logger = LoggerFactory.getLogger(SystemProcessController.class);
	
	@Resource
	private SystemProcessService systemProcessService;
	
	/**
	 * 系统进程监控首页
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/get")
	public ModelAndView getAll(HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		
		try{
			Page<ServerInfo> page = new Page<ServerInfo>(1,10);
			ServerInfo serverInfo = new ServerInfo();
			PcInfo pcInfo = new PcInfo();
			serverInfo.setPcInfo(pcInfo);
			page.setConditions(serverInfo);
			page = systemProcessService.findSystemProcessByPage(page);
			//正常
			serverInfo.setBjzt("N");
			int validCount = systemProcessService.findTotalResultByConditions(serverInfo);
			//异常
			int inValidCount = systemProcessService.findTotalResultByConditionsNotN(serverInfo);
			int heath = 0;
			if(validCount > 0){
				heath = (validCount*100)/(validCount+inValidCount);				
			}
			model.setViewName("/system/systemProcessSurvivalMonitor");
			model.addObject("leftMenu", "systemMenu");
			model.addObject("page", page);
			model.addObject("validCount", validCount);
			model.addObject("inValidCount", inValidCount);
			model.addObject("heath",heath);
		}catch(Exception e){
			logger.error("error", e);
			model.setViewName("/error");
		}
		
		return model;
	}
	
	
	@RequestMapping(value="/getSystemTable", method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView getSystemTable(HttpServletRequest request, HttpServletResponse response, ModelAndView model, 
			@RequestParam(value = "startTime", required = false) String startTime, 
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value = "ssz", required = false) String ssz,
			@RequestParam(value = "xmm", required = false) String xmm,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "text", required = false) String text,
			@RequestParam(value = "change", required = false) Integer change){
		logger.info("|startTime:"+startTime+"|endTime:"+endTime+"|ssz:"+ssz+"|xmm:"
				+xmm+"|pageNo:"+pageNo+"|pageSize:"+pageSize+"|change:"+change);
		
		try{
			
			if(pageNo == null || pageNo < 1){
				pageNo = 1;
			}
			if(pageSize == null || pageSize <1){
				pageSize = 10;
			}
			Page<ServerInfo> page = new Page<ServerInfo>(pageNo,pageSize);
			ServerInfo query = new ServerInfo();
			PcInfo pcInfo = new PcInfo();
			query.setPcInfo(pcInfo);
			query.setStartTime(startTime);
			query.setEndTime(endTime);
			page.setConditions(query);
			int validCount = 0;
			int inValidCount = 0;
			if(change != null && change == 0){
				
				//来自于warning页面
				if(ssz != null && !"请选择".equals(ssz) && ssz.length() >= 0){
					Integer sszid = systemProcessService.selectSszidBySsz(ssz);
					query.setSszid(sszid);
				}
				if(xmm != null && !"请选择".equals(xmm) && xmm.length() >= 0){
					query.setXmm(xmm);
				}
				//来自于异常页面
				// and 的形式匹配  ssz、xxm
				query.setBjzt("N");
				page = systemProcessService.findSystemProcessByPageNotN(page);
				
				model.setViewName("/system/systemWarningTable");
			}else{
				//来自于主页面
				
				if(text != null && text.length() > 0){
					//来自于主页面
					xmm = text;
					query.setXmm(xmm);
				}
				//like 的形式匹配 xmm
				page = systemProcessService.findSystemProcessByPageLike(page);
				query.setBjzt("N");
				validCount = systemProcessService.findTotalResultByConditions(query);
				//异常
				inValidCount = systemProcessService.findTotalResultByConditionsNotN(query);
				model.setViewName("/system/systemTable");
			}
			
			/*int validCount = 0;
			int inValidCount = 0;
			if(text != null && text.length() > 0){
				//来自于主页面
				xmm = text;
				query.setXmm(xmm);
				//like 的形式匹配 xmm
				page = systemProcessService.findSystemProcessByPageLike(page);
				query.setBjzt("N");
				validCount = systemProcessService.findTotalResultByConditionsLike(query);
				inValidCount = systemProcessService.findTotalResultByConditionsLike(query);
			}else{
				if(ssz != null && !"请选择".equals(ssz) && ssz.length() >= 0){
					Integer sszid = systemProcessService.selectSszidBySsz(ssz);
					query.setSszid(sszid);
				}
				if(xmm != null && !"请选择".equals(xmm) && xmm.length() >= 0){
					query.setXmm(xmm);
				}
				//来自于异常页面
				// and 的形式匹配  ssz、xxm
				query.setBjzt("N");
				page = systemProcessService.findSystemProcessByPageNotN(page);
				validCount = systemProcessService.findTotalResultByConditions(query);
				query.setBjzt("Y");
				inValidCount = systemProcessService.findTotalResultByConditions(query);
				
			}*/
			
			
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
	 * 报警记录页面
	 * @param request 请求
	 * @param response 相应
	 * @param model 模型
	 * @return 模型
	 */
	@RequestMapping(value="/warning")
	public ModelAndView getWarning(HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		logger.info("get warning system list ");
		try{
			Page<ServerInfo> page = new Page<ServerInfo>(1,10);
			ServerInfo serverInfo = new ServerInfo();
			serverInfo.setBjzt("N");
			PcInfo pcInfo = new PcInfo();
			serverInfo.setPcInfo(pcInfo);
			page.setConditions(serverInfo);
			page = systemProcessService.findSystemProcessByPageNotN(page);
			//获取部门列表信息
			List<String> sszList = systemProcessService.selectSszList();
			
			model.setViewName("/system/systemSurvivalMonitorWarning");
			model.addObject("leftMenu", "systemMenu");
			model.addObject("page", page);
			model.addObject("sszList", sszList);
			
		}catch(Exception e){
			logger.error("error", e);
			model.setViewName("error");
		}
		return model;
	}
	
	@ResponseBody 
	@RequestMapping(value="/getSystemXmm", method={RequestMethod.POST,RequestMethod.GET})
	public List<String> getSystemXmm(HttpServletRequest request, HttpServletResponse response, ModelAndView model,
			@RequestParam(value = "ssz", required = false) String ssz){
		//获取应用名列表信息
		ServerInfo serverInfo = new ServerInfo();
		
		List<String> xmmList = null;
		try {
			if(ssz != null && !"请选择".equals(ssz) && ssz.length() >= 0){
				serverInfo.setSsz(ssz);
				xmmList = systemProcessService.selectXmmList(ssz);
			}else{
				xmmList = new ArrayList<String>();
			}
		} catch (Exception e) {
			logger.error("error", e);
		}
		return xmmList;
	}
	
	@RequestMapping(value="/getSystemApp")
	public ModelAndView getSystemApp(HttpServletRequest request, HttpServletResponse response, ModelAndView model,
			@RequestParam(value = "ssz", required = false) String ssz,
			@RequestParam(value = "xmm", required = false) String xmm){
		
		try {
			//服务器的数量
			int instanceCount = 0;
			
			String ssss= ssz;
			if(xmm != null && xmm.length() > 0){
				ServerInfo serverIf = new ServerInfo();
				serverIf.setXmm(xmm);
				if(ssz != null && !"请选择".equals(ssz) && ssz.length() >= 0){
					Integer sszid = systemProcessService.selectSszidBySsz(ssz);
					serverIf.setSszid(sszid);
				}
				List<ServerInfo> serverInfos = systemProcessService.selectServerInfosByXmmAndSsz(serverIf);
				if(serverInfos.size()>0){
					instanceCount = serverInfos.size();
					ssz = serverInfos.get(0).getSsz();
					//最后启动时间
					String yyqdsj = serverInfos.get(0).getYyqdsj();
					List<String> xmmList = null;
					if(ssz != null && ssz.length() > 0){
						xmmList = systemProcessService.selectXmmList(ssz);
					}
					
					// 物理机、虚拟机
					List<String> ipdzList = new ArrayList<String>();
					Set<String> md5Set = new HashSet<>();
					List<String> gsjfList = new ArrayList<String>();
					String bdx = "";
					String brq = "";
					for (ServerInfo serverInfo : serverInfos) {
						String xmip = serverInfo.getXmip();
						ipdzList.add(xmip);
						String bmd5 = serverInfo.getBmd5();
						md5Set.add(bmd5);
						String gsjf = serverInfo.getPcInfo().getGsjf();
						gsjfList.add(gsjf);
						//获取包大小、包日期
						String yyqdsj2 = serverInfo.getYyqdsj();
						if(yyqdsj!=null && yyqdsj.length()>0 && yyqdsj.equals(yyqdsj2)){
							bdx = serverInfo.getBdx();
							brq = serverInfo.getBrq();
						}
						
					}
					List<String> vmCount = systemProcessService.getVmCount(ipdzList);
					int m = 0;
					int v = 0;
					if(vmCount.size() > 0){
						for (String string : vmCount) {
							if("V".equals(string)){
								v++;
							}
							if("M".equals(string)){
								m++;
							}
						}
					}
					
					//包MD5一致性验证
					String md5 = "";
					if(md5Set.size() >= 2){
						md5 = "不通过";
					}
					if(md5Set.size() == 1){
						md5 = "通过";
					}
					
					//归属机房 M5  鹏博士
					int m5 = 0;
					int pbs = 0;
					if(gsjfList.size() > 0){
						for (String gsjf : gsjfList) {
							if("M5".equals(gsjf)){
								m5++;
							}
							if("鹏博士".equals(gsjf)){
								pbs++;
							}
						}
					}
					if(xmmList.size() > 0){
						xmmList.remove(xmm);
					}
					model.addObject("m", m);
					model.addObject("v", v);
					model.addObject("bdx", bdx);
					model.addObject("brq", brq);
					model.addObject("xmmList", xmmList);
					model.addObject("ssz", ssz);
					model.addObject("xmm", xmm);
					model.addObject("md5", md5);
					model.addObject("yyqdsj", yyqdsj);
					model.addObject("m5", m5);
					model.addObject("pbs", pbs);
					
					//累计编译
					Integer ljbycs = systemProcessService.selectLjbycs(xmm);
					if(ljbycs == null){
						ljbycs = 0;
					}
					//累计发版
					Integer ljfbcs = systemProcessService.selectLjfbcs(xmm);
					
					if(ljfbcs == null){
						ljfbcs = 0;
					}
					
					model.addObject("ljbycs", ljbycs);
					model.addObject("ljfbcs", ljfbcs);
					model.addObject("serverInfos", serverInfos);
					//获取部门列表信息
					List<String> sszList = systemProcessService.selectSszList();
					if(sszList.size() > 0){
						sszList.remove(ssz);
					}
					model.addObject("instanceCount", instanceCount);
					
					model.addObject("sszList", sszList);
					model.addObject("leftMenu", "systemMenu");
				}
				
			}
			
			if(ssss != null && ssss.length() > 0){
				model.setViewName("/system/systemAppTable");
			}else{
				model.setViewName("/system/systemAppName");
			}
			
		} catch (Exception e) {
			logger.error("error", e);
			model.setViewName("error");
		}
		return model;
	}
	
}
