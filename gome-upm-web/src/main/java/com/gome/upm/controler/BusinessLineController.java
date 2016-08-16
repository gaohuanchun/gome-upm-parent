package com.gome.upm.controler;

import java.sql.SQLException;

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
import com.gome.upm.common.util.ResponsesDTO;
import com.gome.upm.constants.ReturnCode;
import com.gome.upm.domain.BusinessLine;
import com.gome.upm.service.BusinessLineService;
import com.gome.upm.service.util.DBContextHolder;

/**
 * 方法监控-业务线控制器
 * @author zhouyaliang
 *
 */
@Controller
@RequestMapping("/businessLine")
public class BusinessLineController {
	
	private Logger logger = LoggerFactory.getLogger(BusinessLineController.class);
	
	@Resource
    private BusinessLineService businessLineService;
	
	/**
	 * 列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView getList(HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		logger.info("get application list");
		DBContextHolder.setDataSource("dataSourceOne");
		try{
			Page<BusinessLine> page = new Page<BusinessLine>(1,10);
			BusinessLine query = new BusinessLine();
			page.setConditions(query);
			page = businessLineService.loadBusinessLine(page);
			model.setViewName("/app/businessLine/list");
			model.addObject("leftMenu", "businessLine");
			model.addObject("page", page);
		}catch(SQLException e){
			e.printStackTrace();
			logger.error("error", e);
			model.setViewName("/error");
		}
		
		return model;
	}
	
	/**
	 * 根据条件查询
	 * @param request
	 * @param response
	 * @param model
	 * @param bcode
	 * @param bname
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/getBusinessLineTable", method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView getAppTable(HttpServletRequest request, HttpServletResponse response, ModelAndView model, 
			@RequestParam(value = "bcode", required = false) String bcode,
			@RequestParam(value = "bname", required = false) String bname,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize){
		logger.info("|bcode:"+bcode+"|bname:"+bname+"|pageNo:"+pageNo+"|pageSize:"+pageSize);
		model.setViewName("/app/businessLine/lineTable");
		try{
//			if(StringUtils.isBlank((String) request.getSession().getAttribute("userName"))){
//				model.setViewName("sessionout");
//				return model;
//			}
			if(bcode == null || bcode == ""){
				bcode = null;
			}
			if(bname == null || bname == ""){
				bname = null;
			}
			
			if(pageNo == null || pageNo < 1){
				pageNo = 1;
			}
			if(pageSize == null || pageSize <1){
				pageSize = 10;
			}
			Page<BusinessLine> page = new Page<BusinessLine>(pageNo,pageSize);
			BusinessLine query = new BusinessLine();
			query.setBcode(bcode);
			query.setBname(bname);
			page.setConditions(query);
			page = businessLineService.loadBusinessLine(page);
			model.addObject("page", page);
		}catch(Exception e){
			logger.error("系统出现异常", e);
			model.setViewName("error");
		}

		return model;
	}
	
	@ResponseBody
	@RequestMapping(value = "/save", method={RequestMethod.POST}, produces = "application/json;charset=utf-8")
	public ResponsesDTO save(HttpServletRequest request, HttpServletResponse response, ModelAndView model,
							@RequestParam(value = "bcode", required = true) String bcode,
							@RequestParam(value = "bname", required = true) String bname,
							@RequestParam(value = "bdesc", required = true) String bdesc,
							@RequestParam(value = "data_order", required = true) String data_order){
		ResponsesDTO  res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		if(isExist(bcode)){
			res.setAttach("已经存在,修改后重试");
		}else{
			// 执行新增操作
			BusinessLine businessLine = new BusinessLine();
			businessLine.setBcode(bcode);
			businessLine.setBname(bname);
			businessLine.setBdesc(bdesc);
			//String userName = (String)request.getSession().getAttribute("userName");
			businessLine.setOperator("当前登录人");
			businessLine.setData_order(Integer.parseInt(data_order));
			businessLine.setCreateTime(businessLine.getCreateTime());
			businessLine.setUpdateTime(businessLine.getUpdateTime());
			int count = businessLineService.addBusinessLine(businessLine);
			if(count <= 0){
				res.setReturnCode(ReturnCode.ACTIVE_FAILURE);
			}
		}
		
		return res;
	}
	
	/**
	 * 根据主键删除
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/del", method={RequestMethod.POST}, produces = "application/json;charset=utf-8")
	public ResponsesDTO delBusinessLine(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "id", required = true) String id){
		logger.info("id:"+id);
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try{
			String[] ids = id.split(",");
			businessLineService.batchDeleteBusinessLineByIds(ids);
		}catch(Exception e){
			logger.error("系统出现异常", e);
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return res;
	}
	
	// 验证编码是否已经存在
	private boolean isExist(String code){
		return businessLineService.checkBusinessLineIsExist(code);
	}
}
