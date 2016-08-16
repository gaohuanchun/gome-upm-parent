package com.gome.upm.controler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.gome.upm.common.util.JsonUtils;
import com.gome.upm.dao.GomeLoginInfoDAO;
import com.gome.upm.dao.MoLoginInfoDAO;
import com.gome.upm.domain.MoLoginInfoBO;
import com.gome.upm.service.util.DBContextHolder;

/**
 * 
 *
 * @author fangjinwei
 */
@Controller
@RequestMapping(value="/monitoLogin")
public class MonitoLoginController extends AbsBaseController {
	@Autowired
	private MoLoginInfoDAO moLoginInfoDAO;
	@Autowired
	private GomeLoginInfoDAO gomeLoginInfoDAO;
	public void setMoLoginInfoDAO(MoLoginInfoDAO moLoginInfoDAO) {
		this.moLoginInfoDAO = moLoginInfoDAO;
	}
	public void setGomeLoginInfoDAO(GomeLoginInfoDAO gomeLoginInfoDAO) {
		this.gomeLoginInfoDAO = gomeLoginInfoDAO;
	}
	@RequestMapping(value="/get")
	public ModelAndView gotoPage(HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/business/moLoginInfo");
		return model;
	}
	@RequestMapping(value = "findLoginInfoList", method = RequestMethod.POST)
	public void findLoginInfoList(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		List<Object[]> reList=new ArrayList<Object[]>();
		// 参数，开始时间，时间间隔
		// 开始时间
		String startTimeStr = request.getParameter("startTime");
		// 结束时间
		String endTimeStr = request.getParameter("endTime");
		Date startTime=formatter.parse(startTimeStr);
		//结束时间
		Date endTime=formatter.parse(endTimeStr);
		Map<Long,Integer> timeMap=new HashMap<Long,Integer>();
		getSeriesByTime(startTime, endTime, timeMap);
		while(startTime.getTime()<=endTime.getTime()){
			//添加数据
			Integer v1=timeMap.get(startTime.getTime());
			reList.add(new Object[]{startTime.getTime(),v1==null?0:v1});
			//+5分钟
			startTime = new Date(startTime .getTime() + 300000);
		}
		String data = JsonUtils.Object2Json(reList);
		renderData(response, data);
	}
	static Date getLoginTime() throws ParseException{
		//获取当前时间5的倍数
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:");
		Date startDate=new Date();
		String minutes=startDate.getMinutes()/5*5==5?"05":startDate.getMinutes()/5*5+"";
		String endTimeStr=formatter1.format(startDate);
		endTimeStr=endTimeStr+minutes+":00";
		return formatter.parse(endTimeStr);
	}
	public static void main(String[] args) throws ParseException {
		System.out.println(formatter.format(getLoginTime()));
	}
	
	@RequestMapping(value = "getLoginInfoForTime", method = RequestMethod.POST)
	public void getLoginInfoForTime(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		// 开始时间
		long startTimelong =Long.parseLong(request.getParameter("startTime"));
		//计算5分钟后的时间
		Date endTime=new Date(new Date(startTimelong).getTime() + 300000);
		
		System.out.println("getLoginInfoForTime==============="+formatter.format(endTime));
		
		Map<Long,Integer> timeMap=new HashMap<Long,Integer>();
		getObject(endTime, timeMap);
		Integer v3=timeMap.get(endTime.getTime());
		String data = JsonUtils.Object2Json(new Object[]{endTime.getTime(),v3==null?0:v3});
		renderData(response, data);
	}
	private void getSeriesByTime(Date startTime,Date endTime,Map<Long,Integer> map) throws ParseException{
		DBContextHolder.setDataSource("dataSourceOne");
		MoLoginInfoBO searchbo=new MoLoginInfoBO();
		searchbo.setStartTime(startTime);
		searchbo.setEndTime(endTime);
		List<MoLoginInfoBO> li=moLoginInfoDAO.searchMoLoginInfoList(searchbo);
		for(MoLoginInfoBO bo:li){
			map.put(bo.getStartTime().getTime(), bo.getCount());
		}
	}
	private void getObject(Date endTime,Map<Long,Integer> timeMap) throws ParseException{
		DBContextHolder.setDataSource("dataSourceThree");
		MoLoginInfoBO gomeLoginInfoVO=new MoLoginInfoBO();
		Date startTime=new Date(endTime.getTime()-300000);
		gomeLoginInfoVO.setStartTime(startTime);
		gomeLoginInfoVO.setEndTime(endTime);
		Integer count1=gomeLoginInfoDAO.searchCountByTime(gomeLoginInfoVO);
		timeMap.put(endTime.getTime(), count1);
	}
}
