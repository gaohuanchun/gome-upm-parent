package com.gome.upm.service.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import com.gome.upm.common.web.httpClient.HttpClientUtils;
import com.gome.upm.dao.MoSynDAO;
import com.gome.upm.domain.MoSynBO;
import com.gome.upm.service.util.DBContextHolder;

public class OrderMonitorJob {
	@Autowired
	private MoSynDAO moSynDAO;
	public MoSynDAO getMoSynDAO() {
		return moSynDAO;
	}
	public void setMoSynDAO(MoSynDAO moSynDAO) {
		this.moSynDAO = moSynDAO;
	}
	//@yolo24.com
	//崔晓珊、					cuixiaoshan				
	//孙美玲、					sunmeiling-ds			
	//刘迪、						liudi
	//张飞云、					zhangyunfei		
	//操文坤、					caowenkun
	//王帅、						wangshuai-ds
	//秦旭、						qinxu
	//程渊、						chengyuan
	//聂伟、						niewei-ds1
	//李丽乔、					liliqiao
	//李双桥、					lishuangqiao
	//任占坡、					renzhanpo
	//张自忠、					zhangzizhong		
	//ITIL、						ITIL				
	//EC订单研发部通讯组、			ecddyfb
	//N-POP研发通讯组				n-pop
	static SimpleDateFormat sdf_ymd = new SimpleDateFormat("yyyy-MM-dd");
	public void work(){
		Random ra =new Random();
		System.out.println("--------OrderMonitorJob-------");
		
//		//DRAGON 逆向单停在OD的订单
//		DBContextHolder.setDataSource("dataSourceThree");
//		MoBusiness moBusiness=new MoBusiness();
//		//当前时间
//		moBusiness.setStartTime(new Date());
//		//当前时间前5小时
//		moBusiness.setEndTime(new Date(new Date().getTime()-1000*60*60*5));
////		OMS-DRG正向订单状态差异
//		Integer n1= moBusinessDAO.getOms_Drg_forward_state_error(moBusiness);
//		sendMessage("OMS-DRG正向订单状态差异", n1);
////		OMS-POP正向订单状态差异
//		Integer n2=moBusinessDAO.getOms_Pop_forward_state_error(moBusiness);
//		sendMessage("OMS-POP正向订单状态差异", n2);
////		OMS-DRG逆向订单状态差异
//		Integer n3=moBusinessDAO.getOms_Drg_state_error(moBusiness);
//		sendMessage("OMS-DRG逆向订单状态差异", n3);
////		OMS-POP逆向订单状态差异
//		Integer n4=moBusinessDAO.getOms_Pop_state_error(moBusiness);
//		sendMessage("OMS-POP逆向订单状态差异", n4);
////		正向单停在CO的订单--已发送SO至POP
//		Integer n5=moBusinessDAO.getCO_So_OrderForPop(moBusiness);
//		sendMessage("正向单停在CO的订单--已发送SO至POP", n5);
////		正向单停在CO的订单--未发送SO至POP
//		Integer n6=moBusinessDAO.getCO_Not_So_OrderForPop(moBusiness);
//		sendMessage("正向单停在CO的订单--未发送SO至POP", n6);
//		//DRAGON 逆向单停在OD的订单
//		Integer n7=  moBusinessDAO.getDragonReverseODOrder(moBusiness);
//		sendMessage("DRAGON 逆向单停在OD的订单", n7);
//		//DRAGON 正向单停在OD的订单
//		Integer n8=  moBusinessDAO.getDragonForwardODOrder(moBusiness);
//		sendMessage("DRAGON 正向单停在OD的订单", n8);
////		正向单停在CO的订单--总数
//		Integer n9=moBusinessDAO.getCoCount(moBusiness);
//		sendMessage("正向单停在CO的订单--总数", n9);
////		正向单停在CO的订单--已发SO至DRG
//		Integer n10=moBusinessDAO.getCO_So_OrderForDrg(moBusiness);
//		sendMessage("正向单停在CO的订单--已发SO至DRG", n10);
////		正向单停在CO的订单--未发SO至DRG
//		Integer n11=moBusinessDAO.getCO_Not_So_OrderForDrg(moBusiness);
//		sendMessage("正向单停在CO的订单--未发SO至DRG", n11);
////		正向单停在CO的订单--G3PP返回状态不正确
//		Integer n12=moBusinessDAO.getCoG3ppErrorState(moBusiness);
//		sendMessage("正向单停在CO的订单--G3PP返回状态不正确", n12);
//		//正向单停在CO的订单--待客服处理
//		Integer n13=moBusinessDAO.getCOOrderForPending(moBusiness);
//		sendMessage("正向单停在CO的订单--待客服处理", n13);
		
		
		DBContextHolder.setDataSource("dataSourceOne");
		String startTimeStr = startTimeStr=sdf_ymd.format(new Date());
		MoSynBO moSynBO=new MoSynBO();
		moSynBO.setSynTime(startTimeStr);
		
		//DRAGON 正向单停在OD的订单
		moSynBO.setType("DRAGON-z-OD");
		moSynBO.setSynTime(startTimeStr);
		List<MoSynBO>  zxl=moSynDAO.searchMoSynList(moSynBO);
		if(zxl.size()==0){
			MoSynBO bo=new MoSynBO();
			bo.setSynTime(startTimeStr);
			bo.setType(moSynBO.getType());
			bo.setCount(ra.nextInt(600));
			bo.setReTime(new Date());
			moSynDAO.saveMoSyn(bo);
			if(bo.getCount()>50){
				sendMessage("DRAGON 正向单停在OD的订单", "dragon", bo.getCount());	
			}
		}
		
		//DRAGON 逆向单停在OD的订单
		moSynBO.setType("DRAGON-n-OD");
		List<MoSynBO>  nxl=moSynDAO.searchMoSynList(moSynBO);
		if(nxl.size()==0){
			MoSynBO bo=new MoSynBO();
			bo.setSynTime(startTimeStr);
			bo.setType(moSynBO.getType());
			bo.setCount(ra.nextInt(600));
			bo.setReTime(new Date());
			moSynDAO.saveMoSyn(bo);
			if(bo.getCount()>50){
				sendMessage("DRAGON 逆向单停在OD的订单", "dragon", bo.getCount());	
			}
		}
		
		//OMS-DRG正向订单状态差异
		moSynBO.setType("OMS-DRG-z");
		List<MoSynBO>  DRG_zxl=moSynDAO.searchMoSynList(moSynBO);
		if(DRG_zxl.size()==0){
			MoSynBO bo=new MoSynBO();
			bo.setSynTime(startTimeStr);
			bo.setType(moSynBO.getType());
			bo.setCount(ra.nextInt(600));
			bo.setReTime(new Date());
			moSynDAO.saveMoSyn(bo);
			if(bo.getCount()>50){
				sendMessage("OMS-DRG正向订单状态差异", "oms", bo.getCount());	
			}
		}
		//OMS-DRG逆向订单状态差异
		moSynBO.setType("OMS-DRG-n");
		List<MoSynBO>  DRG_nxl=moSynDAO.searchMoSynList(moSynBO);
		if(DRG_nxl.size()==0){
			MoSynBO bo=new MoSynBO();
			bo.setSynTime(startTimeStr);
			bo.setType(moSynBO.getType());
			bo.setCount(ra.nextInt(600));
			bo.setReTime(new Date());
			moSynDAO.saveMoSyn(bo);
			if(bo.getCount()>50){
				sendMessage("OMS-DRG逆向订单状态差异", "oms", bo.getCount());	
			}
		}
		//OMS-POP正向订单状态差异
		moSynBO.setType("OMS-POP-z");
		List<MoSynBO>  POP_zxl=moSynDAO.searchMoSynList(moSynBO);
		
		if(POP_zxl.size()==0){
			MoSynBO bo=new MoSynBO();
			bo.setSynTime(startTimeStr);
			bo.setType(moSynBO.getType());
			bo.setCount(ra.nextInt(600));
			bo.setReTime(new Date());
			moSynDAO.saveMoSyn(bo);
			if(bo.getCount()>50){
				sendMessage("OMS-POP正向订单状态差异", "oms", bo.getCount());	
			}
		}
		//OMS-POP逆向订单状态差异
		moSynBO.setType("OMS-POP-n");
		List<MoSynBO>  POP_nxl=moSynDAO.searchMoSynList(moSynBO);
		
		if(POP_nxl.size()==0){
			MoSynBO bo=new MoSynBO();
			bo.setSynTime(startTimeStr);
			bo.setType(moSynBO.getType());
			bo.setCount(ra.nextInt(600));
			bo.setReTime(new Date());
			moSynDAO.saveMoSyn(bo);
			if(bo.getCount()>50){
				sendMessage("OMS-POP逆向订单状态差异", "oms", bo.getCount());	
			}
		}
		
		
		//正向单停在CO的订单--总数 Z-CO-SO-c
		moSynBO.setType("Z-CO-SO-c");
		List<MoSynBO>  co_countl=moSynDAO.searchMoSynList(moSynBO);
		if(co_countl.size()==0){
			MoSynBO bo=new MoSynBO();
			bo.setSynTime(startTimeStr);
			bo.setType(moSynBO.getType());
			bo.setCount(ra.nextInt(600));
			bo.setReTime(new Date());
			moSynDAO.saveMoSyn(bo);
		}
		
		//正向单停在CO的订单--G3PP返回状态不正确 Z-CO-G3PP
		moSynBO.setType("Z-CO-G3PP");
		List<MoSynBO>  co_g3ppl=moSynDAO.searchMoSynList(moSynBO);
		if(co_g3ppl.size()==0){
			MoSynBO bo=new MoSynBO();
			bo.setSynTime(startTimeStr);
			bo.setType(moSynBO.getType());
			bo.setCount(ra.nextInt(600));
			bo.setReTime(new Date());
			moSynDAO.saveMoSyn(bo);
			if(bo.getCount()>10){
				sendMessage("正向单停在CO的订单--G3PP返回状态不正确", "forward", bo.getCount());	
			}
		}
		
		//正向单停在CO的订单--已发送SO至DRG 
		moSynBO.setType("Z-CO-SO-R-DRG");
		List<MoSynBO>  co_so_r_drgl=moSynDAO.searchMoSynList(moSynBO);
		if(co_so_r_drgl.size()==0){
			MoSynBO bo=new MoSynBO();
			bo.setSynTime(startTimeStr);
			bo.setType(moSynBO.getType());
			bo.setCount(ra.nextInt(600));
			bo.setReTime(new Date());
			moSynDAO.saveMoSyn(bo);
			if(bo.getCount()>10){
				sendMessage("正向单停在CO的订单--已发送SO至DRG", "forward", bo.getCount());	
			}
		}
		
		//正向单停在CO的订单--已发送SO至POP 
		moSynBO.setType("Z-CO-SO-R-POP");
		List<MoSynBO>  co_so_r_popl=moSynDAO.searchMoSynList(moSynBO);
		if(co_so_r_popl.size()==0){
			MoSynBO bo=new MoSynBO();
			bo.setSynTime(startTimeStr);
			bo.setType(moSynBO.getType());
			bo.setCount(ra.nextInt(600));
			bo.setReTime(new Date());
			moSynDAO.saveMoSyn(bo);
			if(bo.getCount()>10){
				sendMessage("正向单停在CO的订单--已发送SO至POP", "forward", bo.getCount());	
			}
		}
		
		//正向单停在CO的订单--待客服处理
		moSynBO.setType("Z-CO-SO-kf");
		List<MoSynBO>  co_kfl=moSynDAO.searchMoSynList(moSynBO);
		if(co_kfl.size()==0){
			MoSynBO bo=new MoSynBO();
			bo.setSynTime(startTimeStr);
			bo.setType(moSynBO.getType());
			bo.setCount(ra.nextInt(600));
			bo.setReTime(new Date());
			moSynDAO.saveMoSyn(bo);
			if(bo.getCount()>10){
				sendMessage("正向单停在CO的订单--待客服处理", "forward", bo.getCount());	
			}
		}
		//正向单停在CO的订单--未发SO至DRG
		moSynBO.setType("Z-CO-SO-N-DRG");
		List<MoSynBO>  co_so_n_drgl=moSynDAO.searchMoSynList(moSynBO);
		if(co_so_n_drgl.size()==0){
			MoSynBO bo=new MoSynBO();
			bo.setSynTime(startTimeStr);
			bo.setType(moSynBO.getType());
			bo.setCount(ra.nextInt(600));
			bo.setReTime(new Date());
			moSynDAO.saveMoSyn(bo);
			if(bo.getCount()>10){
				sendMessage("正向单停在CO的订单--未发SO至DRG", "forward", bo.getCount());	
			}
		}
		//正向单停在CO的订单--未发SO至POP  Z-CO-SO-N-POP
		moSynBO.setType("Z-CO-SO-N-POP");
		List<MoSynBO>  co_so_n_popl=moSynDAO.searchMoSynList(moSynBO);
		if(co_so_n_popl.size()==0){
			MoSynBO bo=new MoSynBO();
			bo.setSynTime(startTimeStr);
			bo.setType(moSynBO.getType());
			bo.setCount(ra.nextInt(600));
			bo.setReTime(new Date());
			moSynDAO.saveMoSyn(bo);
			if(bo.getCount()>10){
				sendMessage("正向单停在CO的订单--未发SO至POP", "forward",bo.getCount());	
			}
		}
	}
	static SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
	static void sendMessage(String subject,String type,Integer count){
		if(count>49){
			String jb="一级";
			if(count<100){
				jb="三级";
			}else if(count<500){
				jb="二级";
			}
			String url="http://10.58.56.72:8081/alarmplatform/alarm";
			Map<String, String> paramMap=new HashMap<>();
			paramMap.put("type", type);
			paramMap.put("mail", "fangjinwei@yolo24.com");
			paramMap.put("subject", "业务报警");
			
			StringBuffer sb=new StringBuffer();
			sb.append("监控组,你好!");
			sb.append("</br>订单\"<span style='color:red; '>"+subject+"</span>\"  出现异常,请及时处理,谢谢.</br>");
			sb.append("报警时间:"+formatter.format(new Date())+"</br>");
			sb.append("订单类型:<span style='color:red; '>"+subject+"</span></br>");
			sb.append("描述:<span style='color:red; '>"+subject+"量为"+count+"单</span>");
			paramMap.put("content", sb.toString());
			try {
				String result=HttpClientUtils.post(url, paramMap);
			} catch (Exception e) {
			}
		}
	}
	public static void main(String[] args) {
		sendMessage("正向单停在CO的订单--待客服处理", "forward", 46454);	
//		String subject="正向单停在CO的订单--待客服处理";
//		String sss="监控组,你好!";
//		sss=sss+"</br>订单<span style='color:red; '>"+subject+"</span>  出现异常,请及时处理,谢谢.</br>";
//		sss=sss+"报警时间:"+formatter.format(new Date())+"</br>";
//		sss=sss+"订单类型:<span style='color:red; '>"+subject+"</span></br>";
//		sss=sss+"描述:<span style='color:red; '>"+subject+"量为"+4343+"单</span>";
//		System.out.println(sss);
		
		
//		String url="http://10.58.56.72:8081/alarmplatform/alarm";
//		Map<String, String> paramMap=new HashMap<>();
//		paramMap.put("type", "forward");
//		paramMap.put("mail", "fangjinwei@yolo24.com");
//		paramMap.put("subject", "subject");
//		paramMap.put("content", "subject			等级:sdfs");
//		try {
//			String result=HttpClientUtils.post(url, paramMap);
//		} catch (Exception e) {
//		}
	}
}
