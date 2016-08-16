package com.gome.upm.controler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.gome.memberCore.utils.utils.StringUtil;
import com.gome.upm.common.Page;
import com.gome.upm.domain.TraceInfo;
import com.gome.upm.domain.TraceSearchInfo;
import com.gome.upm.domain.TraceTreeInfo;
import com.gome.upm.service.ITraceTreeService;
import com.gome.upm.service.util.DBContextHolder;

/**
 * 应用方法监控
 * @author zhouyaliang
 *
 */
@Controller
@RequestMapping(value = "/app")
public class AppMonitorController {
	private Logger logger = LoggerFactory.getLogger(AppMonitorController.class);
	
    @Resource(name="traceTreeService")
    private ITraceTreeService iTraceTreeService;

	// 方法监控首页
	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
		model.addObject("leftMenu", "appMonitorMenu");
		model.setViewName("/app/index");
		DBContextHolder.setDataSource("dataSourceOne");
		
		return model;
	}

	// 搜索首页跳转到搜索main页面
	@RequestMapping(value = "/main")
	public ModelAndView executeRedirect(HttpServletRequest request, HttpServletResponse response, ModelAndView model,
									  @RequestParam(value = "traceId", required = false) String traceId) {
		model.addObject("leftMenu", "appMonitorMenu");
		model.addObject("key", traceId);

		if(StringUtil.notEmpty(traceId)){
			 model.setViewName("/app/main");
		}

		return model;
	}
	
	// 根据traceId执行查找
	@RequestMapping(value = "/search")
	public ModelAndView loadTraceTree(HttpServletRequest request, HttpServletResponse response, ModelAndView model,
			  @RequestParam(value = "traceId", required = false) String traceId){
		
		model.addObject("leftMenu", "appMonitorMenu");
		model.addObject("key", traceId);
		try{
			if(StringUtil.notEmpty(traceId)){
				TraceTreeInfo traceTree = iTraceTreeService.queryTraceTreeByTraceId(traceId);
				model.addObject("result", JSON.toJSONString(traceTree));
			}
		}catch(Exception e){
			logger.info("execute search fail...");
			model.setViewName("error");
		}
		
		return model;
	}
	
	
	//全文检索
	@RequestMapping(value = "/searchAll")
	public ModelAndView searchAll(HttpServletRequest request, HttpServletResponse response, ModelAndView model,
								@RequestParam(value = "content", required = false) String content) {
		
		if(content == null || "".equals(content)){
			return model;
		}
		Page<TraceInfo> page = new Page<TraceInfo>(1, 10);
		TraceSearchInfo traceSearchInfo = iTraceTreeService.getTracesByBussinessKey(content,0,10);
		int pageNo = 1;
		int pageSize = 10;
		int totalResult = (int) traceSearchInfo.getTotalHits();
		int totalPage = totalResult % 10 > 0 ? (totalResult/10 + 1) : totalResult / 10;
		
		page.setData(traceSearchInfo.getTraceInfoList());
		page.setStartIndex(pageNo*pageSize);// 开始条数
		page.setEndIndex((pageNo+1)*pageSize);
		page.setPageSize(pageSize); // 每页条数
		page.setPageNo(pageNo); // 当前页
		page.setTotalResult(totalResult); // 总记录数
		page.setTotalPage(totalPage); // 总页数
		model.addObject("businessKey", content);
		model.addObject("total", traceSearchInfo.getTotalHits());
		model.addObject("page", page);
		model.addObject("leftMenu", "appMonitorMenu");
	    model.setViewName("/app/all");

		return model;
	}
	
	/**
	 * 分页查询
	 * @param request
	 * @param response
	 * @param model
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/getTable", method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView getTable(HttpServletRequest request, HttpServletResponse response, ModelAndView model,
			@RequestParam(value = "businessKey", required = false) String businessKey,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize){
		
		if(businessKey == null || "".equals(businessKey)){
			return model;
		}
		if(pageNo == null || pageNo < 1){
			pageNo = 1;
		}
		if(pageSize == null || pageSize <1){
			pageSize = 10;
		}
		// 初始化查询es的分页参数
		Page<TraceInfo> page = new Page<TraceInfo>();		
		int from = (pageNo-1)*pageSize;
		
		TraceSearchInfo traceSearchInfo = iTraceTreeService.getTracesByBussinessKey(businessKey,from,pageSize);
		int totalResult = (int) traceSearchInfo.getTotalHits();
		int totalPage = totalResult % 10 > 0 ? (totalResult/10 + 1) : totalResult / 10;
		
		page.setData(traceSearchInfo.getTraceInfoList());
		page.setStartIndex((pageNo-1)*pageSize+1);// 开始条数
		if((pageNo+1)*pageSize >= totalResult){
			page.setEndIndex(totalResult);
		}else{
			page.setEndIndex((pageNo+1)*pageSize);
		}
		
		page.setPageSize(pageSize); // 每页条数
		page.setPageNo(pageNo); // 当前页
		page.setTotalResult(totalResult); // 总记录数
		page.setTotalPage(totalPage); // 总页数
		model.addObject("businessKey", businessKey);
		model.addObject("total", traceSearchInfo.getTotalHits());
		model.addObject("page", page);
		model.setViewName("/app/allTable");
		model.addObject("leftMenu", "appMonitorMenu");
		
		return model;
	}
}
