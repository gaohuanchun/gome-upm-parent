package com.gome.upm.controler;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.gome.upm.common.Constant;
import com.gome.upm.common.Page;
import com.gome.upm.common.util.JsonUtils;
import com.gome.upm.dao.MoBusinessDAO;
import com.gome.upm.dao.MoLoginInfoDAO;
import com.gome.upm.dao.MoOrderStateDAO;
import com.gome.upm.dao.MoPayDAO;
import com.gome.upm.dao.MoSynDAO;
import com.gome.upm.dao.OrderStateDAO;
import com.gome.upm.domain.AlarmRecord;
import com.gome.upm.domain.MapCoordinate;
import com.gome.upm.domain.MoBusiness;
import com.gome.upm.domain.MoCpsBO;
import com.gome.upm.domain.MoOrderRechargeBO;
import com.gome.upm.domain.MoOrderStateBO;
import com.gome.upm.domain.MoSynBO;
import com.gome.upm.domain.OrderState;
import com.gome.upm.service.AlarmRecordService;
import com.gome.upm.service.util.DBContextHolder;

@Controller
@RequestMapping(value="/monitoBusiness")
public class MonitoBusinessController extends AbsBaseController{
	@Autowired
	private OrderStateDAO orderStateDAO;
	@Autowired
	private MoOrderStateDAO moOrderStateDAO;
	@Autowired
	private MoBusinessDAO moBusinessDAO;
	@Autowired
	private MoPayDAO moPayDAO;
	@Autowired
	private MoLoginInfoDAO moLoginInfoDAO;
	@Autowired
	private MoSynDAO moSynDAO;
	public void setMoSynDAO(MoSynDAO moSynDAO) {
		this.moSynDAO = moSynDAO;
	}
	public void setMoLoginInfoDAO(MoLoginInfoDAO moLoginInfoDAO) {
		this.moLoginInfoDAO = moLoginInfoDAO;
	}
	public void setMoPayDAO(MoPayDAO moPayDAO) {
		this.moPayDAO = moPayDAO;
	}
	public void setMoBusinessDAO(MoBusinessDAO moBusinessDAO) {
		this.moBusinessDAO = moBusinessDAO;
	}
	public void setOrderStateDAO(OrderStateDAO orderStateDAO) {
		this.orderStateDAO = orderStateDAO;
	}
	public void setMoOrderStateDAO(MoOrderStateDAO moOrderStateDAO) {
		this.moOrderStateDAO = moOrderStateDAO;
	}
	/**
	 * 业务监控主页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/gotoMonitoBusiness")
	public ModelAndView gotoMonitoBusiness(HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.addObject("leftMenu", "businessMenu");
		model.setViewName("/business/monitoBusinessMain");
		return model;
	}
//	DRAGON 正向单停在OD的订单	DRAGON-z-OD
//	DRAGON 逆向单停在OD的订单	DRAGON-n-OD
//	OMS-DRG正向订单状态差异	OMS-DRG-z
//	OMS-DRG逆向订单状态差异	OMS-DRG-n
//	OMS-POP正向订单状态差异	OMS-POP-z
//	OMS-POP逆向订单状态差异	OMS-POP-n
//	正向单停在CO的订单--G3PP返回状态不正确	Z-CO-G3PP
//	正向单停在CO的订单--已发送SO至DRG	Z-CO-SO-R-DRG
//	正向单停在CO的订单--已发送SO至POP	Z-CO-SO-R-POP
//	正向单停在CO的订单--待客服处理	Z-CO-SO-kf
//	正向单停在CO的订单--总数	Z-CO-SO-c
//	正向单停在CO的订单--未发SO至DRG	Z-CO-SO-N-DRG
//	正向单停在CO的订单--未发SO至POP	Z-CO-SO-N-POP
	
	//-----------------------------Dragon-------------------------------------------
	@RequestMapping(value = "getDragonDataList", method = RequestMethod.POST)
	public void getDragonDataList(HttpServletRequest request, HttpServletResponse response) throws ParseException{
		//DRAGON 逆向单停在OD的订单
//		DBContextHolder.setDataSource("dataSourceThree");
//		Integer nx=  moBusinessDAO.getDragonReverseODOrder(moBusiness);
//		Integer zx=  moBusinessDAO.getDragonForwardODOrder(moBusiness);
		List<Integer> reList=new ArrayList<Integer>();
		// 参数，开始时间，时间间隔
		// 开始时间
		String startTimeStr = request.getParameter("startTime");
		if(startTimeStr==null||"".equals(startTimeStr)){
			startTimeStr=sdf_ymd.format(new Date());
		}
		DBContextHolder.setDataSource("dataSourceOne");
		MoSynBO moSynBO=new MoSynBO();
		moSynBO.setType("DRAGON-z-OD");
		moSynBO.setSynTime(startTimeStr);
		List<MoSynBO>  zxl=moSynDAO.searchMoSynList(moSynBO);
		moSynBO.setType("DRAGON-n-OD");
		List<MoSynBO>  nxl=moSynDAO.searchMoSynList(moSynBO);
		Integer nx=  nxl.size()>0?nxl.get(0).getCount():0;
		Integer zx=  zxl.size()>0?zxl.get(0).getCount():0;
		reList.add(zx);
		reList.add(nx);
		String data = JsonUtils.Object2Json(reList);
		renderData(response, data);
	}
	//-----------------------------oms-----------------------------------------
	@RequestMapping(value = "getOmsDataList", method = RequestMethod.POST)
	public void getOmsDataList(HttpServletRequest request, HttpServletResponse response) throws ParseException{
		List<Integer> reList=new ArrayList<Integer>();
//		MoBusiness moBusiness=new MoBusiness();
//		moBusiness.setStartTime(startTime);
//		moBusiness.setEndTime(endTime);
//		DBContextHolder.setDataSource("dataSourceThree");
//		//OMS-DRG正向订单状态差异
//		Integer DRG_zx=moBusinessDAO.getOms_Drg_forward_state_error(moBusiness);
//		//OMS-DRG逆向订单状态差异
//		Integer DRG_nx=moBusinessDAO.getOms_Drg_state_error(moBusiness);
//		//OMS-POP正向订单状态差异
//		Integer POP_zx=moBusinessDAO.getOms_Pop_forward_state_error(moBusiness);
//		//OMS-POP逆向订单状态差异
//		Integer POP_nx=moBusinessDAO.getOms_Pop_state_error(moBusiness);
		
		
//		OMS-DRG正向订单状态差异	OMS-DRG-z
//		OMS-DRG逆向订单状态差异	OMS-DRG-n
//		OMS-POP正向订单状态差异	OMS-POP-z
//		OMS-POP逆向订单状态差异	OMS-POP-n
		DBContextHolder.setDataSource("dataSourceOne");
		String startTimeStr = request.getParameter("startTime");
		if(startTimeStr==null||"".equals(startTimeStr)){
			startTimeStr=sdf_ymd.format(new Date());
		}
		MoSynBO moSynBO=new MoSynBO();
		moSynBO.setSynTime(startTimeStr);
		
		//OMS-DRG正向订单状态差异
		moSynBO.setType("OMS-DRG-z");
		List<MoSynBO>  DRG_zxl=moSynDAO.searchMoSynList(moSynBO);
		Integer DRG_zx=DRG_zxl.size()>0?DRG_zxl.get(0).getCount():0;
		
		//OMS-DRG逆向订单状态差异
		moSynBO.setType("OMS-DRG-n");
		List<MoSynBO>  DRG_nxl=moSynDAO.searchMoSynList(moSynBO);
		Integer DRG_nx=DRG_nxl.size()>0?DRG_nxl.get(0).getCount():0;
		
		//OMS-POP正向订单状态差异
		moSynBO.setType("OMS-POP-z");
		List<MoSynBO>  POP_zxl=moSynDAO.searchMoSynList(moSynBO);
		Integer POP_zx=POP_zxl.size()>0?POP_zxl.get(0).getCount():0;
		//OMS-POP逆向订单状态差异
		
		moSynBO.setType("OMS-POP-n");
		List<MoSynBO>  POP_nxl=moSynDAO.searchMoSynList(moSynBO);
		Integer POP_nx=POP_nxl.size()>0?POP_nxl.get(0).getCount():0;
		
		reList.add(DRG_zx);
		reList.add(DRG_nx);
		reList.add(POP_zx);
		reList.add(POP_nx);
		String data = JsonUtils.Object2Json(reList);
		renderData(response, data);
	}
	//-----------------------------co-----------------------------------------
	@RequestMapping(value = "getCoDataList", method = RequestMethod.POST)
	public void getCoDataList(HttpServletRequest request, HttpServletResponse response) throws ParseException{
		List<Integer> reList=new ArrayList<Integer>();
//		// 参数，开始时间，时间间隔
//		// 开始时间
//		String startTimeStr = request.getParameter("startTime");
//		// 结束时间
//		String endTimeStr = request.getParameter("endTime");
//		Date startTime=formatter.parse(startTimeStr);
//		//结束时间
//		Date endTime=formatter.parse(endTimeStr);
//		
//		MoBusiness moBusiness=new MoBusiness();
//		moBusiness.setStartTime(startTime);
//		moBusiness.setEndTime(endTime);
		
		

		
		
		

		
//		DBContextHolder.setDataSource("dataSourceThree");
//		//正向单停在CO的订单--总数
//		Integer co_count=moBusinessDAO.getCoCount(moBusiness);
//		//正向单停在CO的订单--G3PP返回状态不正确
//		Integer co_g3pp=moBusinessDAO.getCoG3ppErrorState(moBusiness);
//		
//		//正向单停在CO的订单--已发送SO至DRG
//		Integer co_so_r_drg=moBusinessDAO.getCO_So_OrderForDrg(moBusiness);
//		//正向单停在CO的订单--已发送SO至POP
//		Integer co_so_r_pop=moBusinessDAO.getCO_So_OrderForPop(moBusiness);
//		
//		//正向单停在CO的订单--待客服处理
//		Integer co_kf=moBusinessDAO.getCOOrderForPending(moBusiness);
//		
//		//正向单停在CO的订单--未发SO至DRG
//		Integer co_so_n_drg=moBusinessDAO.getCO_Not_So_OrderForDrg(moBusiness);
//		//正向单停在CO的订单--未发SO至POP
//		Integer co_so_n_pop=moBusinessDAO.getCO_Not_So_OrderForPop(moBusiness);
		
		
		
		DBContextHolder.setDataSource("dataSourceOne");
		String startTimeStr = request.getParameter("startTime");
		if(startTimeStr==null||"".equals(startTimeStr)){
			startTimeStr=sdf_ymd.format(new Date());
		}
		MoSynBO moSynBO=new MoSynBO();
		moSynBO.setSynTime(startTimeStr);

//		正向单停在CO的订单--未发SO至DRG	
//		正向单停在CO的订单--未发SO至POP	
		
		//正向单停在CO的订单--总数 Z-CO-SO-c
		moSynBO.setType("Z-CO-SO-c");
		List<MoSynBO>  co_countl=moSynDAO.searchMoSynList(moSynBO);
		Integer co_count=co_countl.size()>0?co_countl.get(0).getCount():0;
		//正向单停在CO的订单--G3PP返回状态不正确 Z-CO-G3PP
		
		moSynBO.setType("Z-CO-G3PP");
		List<MoSynBO>  co_g3ppl=moSynDAO.searchMoSynList(moSynBO);
		Integer co_g3pp=co_g3ppl.size()>0?co_g3ppl.get(0).getCount():0;
		
		//正向单停在CO的订单--已发送SO至DRG 
		moSynBO.setType("Z-CO-SO-R-DRG");
		List<MoSynBO>  co_so_r_drgl=moSynDAO.searchMoSynList(moSynBO);
		Integer co_so_r_drg=co_so_r_drgl.size()>0?co_so_r_drgl.get(0).getCount():0;
		//正向单停在CO的订单--已发送SO至POP 
		moSynBO.setType("Z-CO-SO-R-POP");
		List<MoSynBO>  co_so_r_popl=moSynDAO.searchMoSynList(moSynBO);
		Integer co_so_r_pop=co_so_r_popl.size()>0?co_so_r_popl.get(0).getCount():0;
		
		//正向单停在CO的订单--待客服处理
		moSynBO.setType("Z-CO-SO-kf");
		List<MoSynBO>  co_kfl=moSynDAO.searchMoSynList(moSynBO);
		Integer co_kf=co_kfl.size()>0?co_kfl.get(0).getCount():0;
		
		//正向单停在CO的订单--未发SO至DRG   Z-CO-SO-N-DRG
		moSynBO.setType("Z-CO-SO-N-DRG");
		List<MoSynBO>  co_so_n_drgl=moSynDAO.searchMoSynList(moSynBO);
		Integer co_so_n_drg=co_so_n_drgl.size()>0?co_so_n_drgl.get(0).getCount():0;
//		//正向单停在CO的订单--未发SO至POP  Z-CO-SO-N-POP
		
		moSynBO.setType("Z-CO-SO-N-POP");
		List<MoSynBO>  co_so_n_popl=moSynDAO.searchMoSynList(moSynBO);
		Integer co_so_n_pop=co_so_n_popl.size()>0?co_so_n_popl.get(0).getCount():0;
		
		reList.add(co_count);
		reList.add(co_g3pp);
		reList.add(co_so_r_drg);
		reList.add(co_so_r_pop);
		reList.add(co_kf);
		reList.add(co_so_n_drg);
		reList.add(co_so_n_pop);
		String data = JsonUtils.Object2Json(reList);
		renderData(response, data);
	}
	//-----------------------------cps-----------------------------------------
	private Integer intervalTime=1000*60*30;
	@RequestMapping(value = "findCpsDataList", method = RequestMethod.POST)
	public void findCpsDataList(HttpServletRequest request, HttpServletResponse response) throws ParseException{
		List<Object[]> reList=new ArrayList<Object[]>();
		// 参数，开始时间，时间间隔
		// 开始时间
		String startTimeStr = request.getParameter("startTime");
		// 结束时间
		String endTimeStr = request.getParameter("endTime");
		
		Date startTime=formatter.parse(startTimeStr);
		//结束时间
		Date endTime=formatter.parse(endTimeStr);
		
		MoCpsBO moCpsBO=new MoCpsBO();
		moCpsBO.setStartTime(startTime);
		moCpsBO.setEndTime(endTime);
		DBContextHolder.setDataSource("dataSourceOne");
		List<MoCpsBO> list= moLoginInfoDAO.searchMoCpsList(moCpsBO);
		for(MoCpsBO bo:list){
			reList.add(new Object[]{bo.getEndTime().getTime(),bo.getCount()});
		}
		String data = JsonUtils.Object2Json(reList);
		renderData(response, data);
	}
	@RequestMapping(value = "getCpsDataForTime", method = RequestMethod.POST)
	public void getCpsDataForTime(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		MoBusiness monitoVO=new MoBusiness();
		monitoVO.setEndTime(getEndDate());
		monitoVO.setStartTime(new Date(monitoVO.getEndTime().getTime()-1000*60*30));
		DBContextHolder.setDataSource("dataSourceThree");
		Integer dd= moBusinessDAO.searchCpsCount(monitoVO);
		Object[] reObj=new Object[]{monitoVO.getEndTime().getTime(),dd};
		renderData(response, JsonUtils.Object2Json(reObj));
	}
	Date getEndDate() throws ParseException{
		SimpleDateFormat ww = new SimpleDateFormat("yyyy-MM-dd HH@mm:ss");
		Date now=new Date();
		String minutes="00";
		if(now.getMinutes()>30){
			minutes="30";
		}
		String nwo=ww.format(now);
		nwo=nwo.substring(0, nwo.indexOf("@"));
		return formatter.parse(nwo+":"+minutes+":00");
	}
	//---------------------------------------------按订单状态查询数据-----------------------------------------------------------------
	@RequestMapping(value = "findMoOrderStateList", method = RequestMethod.POST)
	public void findMoOrderStateList(HttpServletRequest request, HttpServletResponse response) throws ParseException {
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
		getMoOrderStateByTime(startTime, endTime,request.getParameter("state"), timeMap);
		while(startTime.getTime()<=endTime.getTime()){
			//添加数据
			Integer v1=timeMap.get(startTime.getTime());
			reList.add(new Object[]{startTime.getTime(),v1==null?0:v1});
			//+1小时
			startTime = new Date(startTime .getTime() + 3600000);
		}
		String data = JsonUtils.Object2Json(reList);
		renderData(response, data);
	}
	private void getMoOrderStateByTime(Date startTime,Date endTime,String state,Map<Long,Integer> map) throws ParseException{
		DBContextHolder.setDataSource("dataSourceOne");
		MoOrderStateBO searchbo=new MoOrderStateBO();
		searchbo.setStartTime(startTime);
		searchbo.setEndTime(endTime);
		searchbo.setState(state);
		List<MoOrderStateBO> li=moOrderStateDAO.searchMoOrderStateList(searchbo);
		for(MoOrderStateBO bo:li){
			map.put(bo.getStartTime().getTime(), bo.getCount());
		}
	}
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * 按订单类型，状态查询数据 页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/gotoOrderStatePage")
	public ModelAndView gotoOrderStatePage(HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.addObject("leftMenu", "businessMenu");
		model.setViewName("/business/moOrderState");
		return model;
	}
	/**
	 * 按订单类型，查询状态
	 * @param request
	 * @param response
	 * @throws ParseException
	 */
	@RequestMapping(value = "searchOrderStateListByType", method = RequestMethod.GET)
	public void searchOrderStateListByType(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		DBContextHolder.setDataSource("dataSourceOne");
		OrderState orderState=new OrderState();
		orderState.setOrderType(request.getParameter("orderType"));
		List<OrderState> lis=orderStateDAO.searchOrderStateListByType(orderState);
		renderData(response, JsonUtils.Object2Json(lis));
	}
	
	//----------------------------------------------订单首页---------------------------------------------------------------------
	@RequestMapping(value="/gotoOrderHead")
	public ModelAndView gotoOrderHead(HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.addObject("leftMenu", "orderSummaryMenu");
		model.setViewName("/business/orderHead");
		return model;
	}
	
	class MoVo{
		public MoVo(String name,Integer count){
			this.name=name;
			this.count=count;
		}
		public MoVo(String name,Double money){
			this.name=name;
			this.money=money;
		}
		String name;
		Integer count;
		Double money;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Integer getCount() {
			return count;
		}
		public void setCount(Integer count) {
			this.count = count;
		}
		public Double getMoney() {
			return money;
		}
		public void setMoney(Double money) {
			this.money = money;
		}
	}
	int xishu=13;
	/**
	 * 查询种类排行
	 * @param request
	 * @param response
	 * @throws ParseException
	 */
	@RequestMapping(value = "searchGoodKindSort", method = RequestMethod.GET)
	public void searchGoodKindSort(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		DBContextHolder.setDataSource("dataSourceThree");
		MoBusiness moBusiness=new MoBusiness();
		moBusiness.setStartTime(getToday());
		moBusiness.setEndTime(new Date());
		List<MoBusiness> list= moBusinessDAO.searchGoodKindSort(moBusiness);
		//****************************添加系数****************************
		for(MoBusiness mo:list){
			mo.setAmount(mo.getAmount()*xishu);
		}
		renderData(response, JsonUtils.Object2Json(list));
	}
	/**
	 * 查询品牌排行
	 * @param request
	 * @param response
	 * @throws ParseException
	 */
	@RequestMapping(value = "searchGoodBrandSort", method = RequestMethod.GET)
	public void searchGoodBrandSort(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		DBContextHolder.setDataSource("dataSourceThree");
		MoBusiness moBusiness=new MoBusiness();
		moBusiness.setStartTime(getToday());
		moBusiness.setEndTime(new Date());
		List<MoBusiness> list= moBusinessDAO.searchGoodBrandSort(moBusiness);
		//****************************添加系数****************************
		for(MoBusiness mo:list){
			mo.setAmount(mo.getAmount()*xishu);
		}
		renderData(response, JsonUtils.Object2Json(list));
	}
	
	class Pie{
		String name;
		double y;
		boolean sliced=false;
		boolean selected=false;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public double getY() {
			return y;
		}
		public void setY(double y) {
			this.y = y;
		}
		public boolean isSliced() {
			return sliced;
		}
		public void setSliced(boolean sliced) {
			this.sliced = sliced;
		}
		public boolean isSelected() {
			return selected;
		}
		public void setSelected(boolean selected) {
			this.selected = selected;
		}
	}
	
	/**
	 * 查询商品属性分类
	 * @param request
	 * @param response
	 * @throws ParseException
	 */
	@RequestMapping(value = "searchGoodProperty", method = RequestMethod.GET)
	public void searchGoodProperty(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		List<Pie> relist=new ArrayList<Pie>();
		DBContextHolder.setDataSource("dataSourceThree");
		MoBusiness moBusiness=new MoBusiness();
		moBusiness.setStartTime(getToday());
		moBusiness.setEndTime(new Date());
		
		Map<String,Pie> map=new HashMap<String,Pie>();
		
		List<MoBusiness> list= moBusinessDAO.searchGoodProperty(moBusiness);
		for(MoBusiness moBusiness2:list){
			Pie pieNow=new Pie();
			pieNow.setName(moBusiness2.getName()+" "+moBusiness2.getAmount());
			pieNow.setY(moBusiness2.getAmount()*xishu);
			if("G3PP".equals(moBusiness2.getName())||"3PP".equals(moBusiness2.getName())){
				pieNow.setSliced(true);
				pieNow.setSelected(true);
//				relist.add(pieNow);
			}
			map.put(moBusiness2.getName(), pieNow);
		}
		String[] s=new String[]{"G3PP","3PP","SMI","B3PP"};
		for(String sN:s){
			if(map.get(sN)!=null){
				relist.add(map.get(sN));
			}else{
				Pie pieNow=new Pie();
				pieNow.setName(sN+" 0");
				pieNow.setY(0);
				relist.add(pieNow);
			}
		}
		renderData(response, JsonUtils.Object2Json(relist));
	}
	/**
	 * 查询订单量
	 * @param request
	 * @param response
	 * @throws ParseException
	 */
	@RequestMapping(value = "searchOrderCount", method = RequestMethod.GET)
	public void searchOrderCount(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		DBContextHolder.setDataSource("dataSourceThree");
		MoBusiness moBusiness=new MoBusiness();
		moBusiness.setStartTime(getToday());
		moBusiness.setEndTime(new Date());
		Integer fcz= moBusinessDAO.getOrderAmountForNotPay(moBusiness);
		Integer pay= moBusinessDAO.getOrderAmountForAlreadyPay(moBusiness);
		Integer delivery= moBusinessDAO.getOrderAmountForDelivery(moBusiness);
		Integer web= moBusinessDAO.getOrderAmountForWeb(moBusiness);
		Integer wap= moBusinessDAO.getOrderAmountForWap(moBusiness);
		Integer app= moBusinessDAO.getOrderAmountForApp(moBusiness);
		//充值订单
		Integer cz=moBusinessDAO.getOrderAmountForPay(moBusiness);
		List relist=new ArrayList();
		relist.add(new MoVo("订单量", (cz+fcz)*xishu));
		relist.add(new MoVo("充值订单", cz*xishu));
		relist.add(new MoVo("非充值订单", fcz*xishu));
		relist.add(new MoVo("支付成功订单量", pay*xishu));
		relist.add(new MoVo("妥投成功订单量", delivery*xishu));
		
		
		List<Pie> pieList=new ArrayList<Pie>();
		Pie webp=new Pie();
		webp.setName("Web端");
		webp.setY(web*xishu);
		webp.setSliced(true);
		webp.setSelected(true);
		pieList.add(webp);
		
		Pie wapp=new Pie();
		wapp.setName("Wap端");
		wapp.setY(wap*xishu);
		wapp.setSliced(true);
		wapp.setSelected(true);
		pieList.add(wapp);
		Pie appp=new Pie();
		appp.setName("App端");
		appp.setY(app*xishu);
		appp.setSliced(true);
		appp.setSelected(true);
		pieList.add(appp);
		
		relist.add(pieList);
		renderData(response, JsonUtils.Object2Json(relist));
	}
	
	static class CityMap{
		String	name;
		Long	value;
		Double[]	coordinate;
		public CityMap(String	name,Long	value){
			this.name=name;
			this.value=value;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Double[] getCoordinate() {
			return coordinate;
		}
		public void setCoordinate(Double[] coordinate) {
			this.coordinate = coordinate;
		}
		public Long getValue() {
			return value;
		}
		public void setValue(Long value) {
			this.value = value;
		}
	}
	/**
	 * 前5个销售量最多的城市
	 * @param request
	 * @param response
	 * @throws ParseException
	 */
	@RequestMapping(value = "getCityBySales", method = RequestMethod.GET)
	public void getCityBySales(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		Long max = new Long(0);
		Object[] obj=new Object[3];
		//城市--坐标点
		DBContextHolder.setDataSource("dataSourceOne");
		Map<String,MapCoordinate> tem=new HashMap<String,MapCoordinate>();
		List<MapCoordinate> mapzb=moBusinessDAO.getCityCoordinateList();
		for(MapCoordinate c: mapzb){
			tem.put(c.getName(), c);
		}
		Map map=new HashMap<>();
		//获取前5个城市
		List<CityMap> relist1=new ArrayList<CityMap>();
		DBContextHolder.setDataSource("dataSourceThree");
		MoBusiness moBusiness=new MoBusiness();
		
		String startTimeStr = request.getParameter("startTime");
		// 结束时间
		String endTimeStr = request.getParameter("endTime");
		if(startTimeStr!=null&&!"".equals(startTimeStr)&&endTimeStr!=null&&!"".equals(endTimeStr)){
			Date startTime=formatter.parse(startTimeStr);
			//结束时间
			Date endTime=formatter.parse(endTimeStr);
			moBusiness.setStartTime(startTime);
			moBusiness.setEndTime(endTime);
		}else{
			moBusiness.setStartTime(getToday());
			moBusiness.setEndTime(new Date());
		}
		List<MoBusiness> list= moBusinessDAO.getCityBySales(moBusiness);
		for(MoBusiness moBusiness2:list){
			try {
				MapCoordinate cNow=tem.get(moBusiness2.getName());
				map.put(moBusiness2.getName(), new String[]{cNow.getXaxis(),cNow.getYaxis()});
				if(moBusiness2.getAmount()>max){
					max=moBusiness2.getAmount();
				}
				relist1.add(new CityMap(moBusiness2.getName(),moBusiness2.getAmount()*xishu));
			} catch (Exception e) {
				System.out.println(moBusiness2.getName());
				e.printStackTrace();
			}
		}
		obj[0]=relist1;
		obj[1]=map;
		obj[2]=max;
		renderData(response, JsonUtils.Object2Json(obj));
	}
	/**
	 * 查询订单支付率历史记录
	 * @param request
	 * @param response
	 * @throws ParseException
	 */
	@RequestMapping(value = "searchPayPercentageHistory", method = RequestMethod.GET)
	public void searchPayPercentageHistory(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		List<Object[]> reList=new ArrayList<Object[]>();
		MoBusiness moBusiness=new MoBusiness();
		moBusiness.setEndTime(getHalfHourForToday());
		moBusiness.setStartTime(new Date(moBusiness.getEndTime().getTime()-1000*60*60*3));
		
		Date startTime=moBusiness.getStartTime();
		Date endTime=moBusiness.getEndTime();
		
		Map<Long,Long> oneLineMap=getOneLineOrderCountHistory(moBusiness);
		Map<Long,Long> allMap=getAllOrderCountHistory(moBusiness);
		
		while(startTime.getTime()<=endTime.getTime()){
			if(oneLineMap.get(startTime.getTime())==null||allMap.get(startTime.getTime())==null){
				reList.add(new Object[]{startTime.getTime(),0.00});
			}else{
				double ddddd=0.00;
				try {
					ddddd=(oneLineMap.get(startTime.getTime())*1.0)/allMap.get(startTime.getTime());
					BigDecimal b=new BigDecimal(ddddd);
					ddddd=b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
				} catch (Exception e) {
				}
				reList.add(new Object[]{startTime.getTime(),ddddd});
			}
			//+半小时
			startTime = new Date(startTime .getTime() + 1000*60*30);
		}
		renderData(response, JsonUtils.Object2Json(reList));
	}
	public Map<Long,Long> getOneLineOrderCountHistory(MoBusiness moBusiness){
		Map<Long,Long> timeMap=new HashMap<Long,Long>();
		DBContextHolder.setDataSource("dataSourceOne");
		List<MoBusiness> n1= moBusinessDAO.searchOneLineOrderCountHistory(moBusiness);
		for(MoBusiness m:n1){
			timeMap.put(m.getEndTime().getTime(), m.getAmount());
		}
		return timeMap;
	}
	public Map<Long,Long> getAllOrderCountHistory(MoBusiness moBusiness){
		Map<Long,Long> timeMap=new HashMap<Long,Long>();
		DBContextHolder.setDataSource("dataSourceOne");
		List<MoBusiness> n1= moBusinessDAO.searchAllOrderCountHistory(moBusiness);
		for(MoBusiness m:n1){
			timeMap.put(m.getEndTime().getTime(), m.getAmount());
		}
		return timeMap;
	}
	
	/**
	 * 查询订单支付率
	 * @param request
	 * @param response
	 * @throws ParseException
	 */
	@RequestMapping(value = "searchPayPercentage", method = RequestMethod.GET)
	public void searchPayPercentage(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		MoOrderRechargeBO moBusiness=new MoOrderRechargeBO();
		SimpleDateFormat formatter0 = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
		Date endTime=formatter.parse(formatter0.format(new Date()));
		//获取4小时之前的数据
		moBusiness.setStartTime(new Date(endTime.getTime()-1000*60*60*4));
		moBusiness.setEndTime(endTime);
		DBContextHolder.setDataSource("dataSourceThree");
		Integer n1= moPayDAO.searchOnLineOrderCount(moBusiness);
		Integer n2= moPayDAO.searchAllOrderCount(moBusiness);
		
		double ddddd=0.00;
		try {
			ddddd=(n1*1.0)/n2;
			BigDecimal b=new BigDecimal(ddddd);
			ddddd=b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		} catch (Exception e) {
		}
		renderData(response, JsonUtils.Object2Json(new Object[]{moBusiness.getEndTime().getTime(),ddddd}));
	}
//	public static void main(String[] args) throws ParseException {
////		SimpleDateFormat formatter0 = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
////		Date endTime=formatter.parse(formatter0.format(new Date()));
////		Date startTime=new Date(endTime.getTime()-1000*60*30);
////		System.out.println(formatter.format(startTime));
////		System.out.println(formatter.format(endTime));
//		
//		double ddddd=887.3574654;
//		try {
////			ddddd=(n1*1.0)/n2;
//			BigDecimal b=new BigDecimal(ddddd);
//			ddddd=b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
//		} catch (Exception e) {
//		}
//		System.out.println(ddddd);
//	}
	
	public static void main(String[] args) throws ParseException {
//		Date date=new Date();
//		String minute=date.getMinutes()>29?"30:00":"00:00";
//		System.out.println(sdf_ymdh.format(date)+minute);
		
		
		Date date22=getHalfHourForToday();
		System.out.println(formatter.format(date22));
	}
	@Resource
	private AlarmRecordService alarmRecordService;
	public void setAlarmRecordService(AlarmRecordService alarmRecordService) {
		this.alarmRecordService = alarmRecordService;
	}
	
	@RequestMapping(value="/goAlarmlist", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView goAlarmlist(@Param(value="content")String content, @Param(value="page")String page, @Param(value="size")String size, @Param(value="type")String type, @Param(value="startTime")String startTime, @Param(value="endTime")String endTime, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/business/moAlarm");
		return model;
	}
	/**
	 * 查询报警数据
	 * @param page
	 * @param size
	 * @param type
	 * @return
	 */
	private Page<AlarmRecord> getAlarmlistData(String page,String size,String type,String startTime,String endTime){
		int pageNo = 1;
		if(StringUtils.isNotEmpty(page)){
			pageNo = Integer.parseInt(page);
		}
		int pageSize = Constant.PAGE_SIZE;
		if(StringUtils.isNotEmpty(size)){
			pageSize = Integer.parseInt(size);
		}
		AlarmRecord alarmRecord= new AlarmRecord();
		if(type!=null&&!"".equals(type)){
			alarmRecord.setType(type);
		}
		if(startTime!=null&&!"".equals(startTime)&&endTime!=null&&!"".equals(endTime)){
			try {
				alarmRecord.setStartTime(startTime);
				alarmRecord.setEndTime(endTime);
			} catch (Exception e) {
			}
		}
		Page<AlarmRecord> p = new Page<AlarmRecord>(pageNo, pageSize);
		p.setConditions(alarmRecord);
		Page<AlarmRecord> pageAlarm = alarmRecordService.findAlarmRecordListByPage(p);
		return pageAlarm;
	}
	@RequestMapping(value="/getAlarmDate", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView getAlarmDate(@Param(value="content")String content, @Param(value="page")String page, @Param(value="size")String size, @Param(value="type")String type, @Param(value="startTime")String startTime, @Param(value="endTime")String endTime,HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/business/moAlarmTable");
//		model.addObject("leftMenu", "alarmMenu");
		Page<AlarmRecord> pageAlarm =getAlarmlistData(page, size, type,startTime,endTime);
		model.addObject("page", pageAlarm);
		return model;
	}
}
