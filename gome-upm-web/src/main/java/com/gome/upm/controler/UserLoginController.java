package com.gome.upm.controler;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.cookie.DateUtils;
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
import com.gome.upm.common.login.LoginUtil;
import com.gome.upm.common.util.CookieUtil;
import com.gome.upm.common.util.ResponsesDTO;
import com.gome.upm.constants.ReturnCode;
import com.gome.upm.controler.base.BaseController;

@Controller
//@RequestMapping(value = "/")
public class UserLoginController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(UserLoginController.class);
	
	private CookieUtil cookieUtil=new CookieUtil();
	
	/**
	 * 跳转到登录页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/login", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
		model.setViewName("/login");
		String userName = (String) request.getSession().getAttribute("userName");
		if(StringUtils.isNotEmpty(userName)){
			//直接跳转到主页
			model.setViewName("redirect:/index");
		}
    	return model;
	}
	
	/**
	 * 处理用户登录
	 * @param request
	 * @param response
	 * @param hsession
	 * @param content
	 * @return
	 * 2016年7月27日 上午11:28:03   caowei-ds1
	 */
	@ResponseBody
	@RequestMapping(value = "/toLogin", method = { RequestMethod.POST }, produces = "application/json;charset=utf-8")
	public ResponsesDTO login(HttpServletRequest request, HttpServletResponse response, HttpSession hsession,
			@RequestParam(value = "content", required = true) String content) {
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		logger.info("content:" + content);
		try {

			JSONObject obj = JSON.parseObject(content);
			String loginName = obj.getString("userName");// 传入参数
			String passwd = obj.getString("passwd");
			boolean checkLogin = LoginUtil.checkLogin(loginName, passwd);
			if (!checkLogin) {
				res.setReturnCode(ReturnCode.ERROR_USER_PASSWORD);
				return res;
			}
			hsession.setAttribute("userName", loginName);
		} catch (Exception e) {
			logger.error("系统出现异常", e);
			res.setReturnCode(ReturnCode.ERROR_SERVER);
		}

		return res;
	}
	
	/**
	 * 跳转到首页.
	 *
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月27日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/index", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView toAddAdView(HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/index");
		return model;
	}
	
	/**
	 * 退出
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * 2016年7月27日 下午3:23:52   caowei-ds1
	 */
	@RequestMapping(value = "/logout", method = { RequestMethod.GET })
	public String logout(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {

		try {

			request.getSession().invalidate();
		} catch (Exception e) {
			logger.info("系统出现异常", e);
			model.setViewName("error");
		}

		return "redirect:/";
	}

}
