package com.gome.upm.controler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/firstPage")
public class FirstPageController {
	@RequestMapping(value="/get")
	public ModelAndView gotoPage(HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/firstPage");
		return model;
	}
}
