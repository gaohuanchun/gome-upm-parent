package com.gome.upm.controler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gome.upm.common.util.ResponsesDTO;
import com.gome.upm.constants.ReturnCode;

@Controller
@RequestMapping(value="/demo")
public class DemoController {
	
	private static final Logger logger = LoggerFactory.getLogger(DemoController.class);
	
	@RequestMapping(value="/demo01")
	public ModelAndView demo01(HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		
		model.setViewName("/demo/chartsDemo01");
		return model;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/ajax/demo01", method={RequestMethod.GET})
	public ResponsesDTO ajaxDemo01(HttpServletRequest request, HttpServletResponse response){
		
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		Map<String,Object> map = new HashMap<String,Object>();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> map1 = new HashMap<String,Object>();
		Map<String,Object> map2 = new HashMap<String,Object>();
		Map<String,Object> map3 = new HashMap<String,Object>();
		Map<String,Object> map4 = new HashMap<String,Object>();
		map1.put("name", "Tokyo");
		map2.put("name", "NewYork");
		map3.put("name", "Berlin");
		map4.put("name", "London");
		map1.put("data", new double[]{7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6});
		map2.put("data", new double[]{-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5});
		map3.put("data", new double[]{-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 3.0});
		map4.put("data", new double[]{3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 39.8});
		list.add(map1);
		list.add(map2);
		list.add(map3);
		list.add(map4);
		map.put("seria", list);
		map.put("categories", new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug","Sep", "Oct", "Nov", "Dec"});
		res.setAttach(map);
		return res;
	}

	
	
	

}
