package com.gome.upm.service.quartz;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gome.upm.common.web.httpClient.HttpClientUtils;
import com.gome.upm.domain.Asm;
import com.gome.upm.domain.DBConnection;
import com.gome.upm.domain.Tbs;
import com.gome.upm.domain.ThresholdConfig;
import com.gome.upm.domain.ThresholdHistory;
import com.gome.upm.service.AsmService;
import com.gome.upm.service.DBConnectionService;
import com.gome.upm.service.TbsService;
import com.gome.upm.service.ThresholdConfigService;
import com.gome.upm.service.ThresholdHistoryService;

/**
 * 负责数据库连接数、表空间、ASM的报警
 * @author caowei-ds1
 *
 */
public class DBConnectionAndASMAlarmBean {
	
//	private Logger logger = Logger.getLogger(DBConnectionAndASMAlarmBean.class);
	private static final Logger logger = LoggerFactory.getLogger(DBConnectionAndASMAlarmBean.class);
	
	@Resource
	private DBConnectionService dBConnectionService;
	
	@Resource
	private TbsService tbsService;
	
	@Resource
	private AsmService asmService;
	
	@Resource
	private ThresholdConfigService thresholdConfigService;
	
	@Resource
	private ThresholdHistoryService thresholdHistoryService;
	
	private String url = "http://10.58.56.72:8081/alarmplatform/alarm";
	
	private Map<String,String> paramMap = new HashMap<String,String>();
	
	private Date date;
	
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
	
	public void work() {
		logger.info("*************************************************************************************");
		System.out.println("*************************************************************************************");
		date = new Date();
		logger.info(df.format(date) + "-----数据库连接数、表空间、ASM定时任务启动...");
		System.out.println(df.format(date) + "-----数据库连接数、表空间、ASM定时任务启动...");
        long begin = date.getTime();
        ThresholdConfig thresholdConfig = null;
        ThresholdHistory thresholdHistory = null;
		int i = 0; //记录本次定时任务共触发了几次报警
		int j = 0; //记录当天三张表中有多少条新记录
		int k = 0; //记录本次定时任务插入了几条阈值配置
		Date createTime = null;
		String createTimeStr = "";
		String currentTimeStr = "";
		int alarmLevel = 0;
		int currentAlarmLevel = 0;
		String alarmLevelStr = "";
		Date updateTime = null;
		long updateTimeMillis = 0;
		long currentTimeMillis = 0;
		Date alarmTime = null;//报警时间
		String content = "";//邮件内容
		long minusTimeMillis = 0;
		List<ThresholdConfig> thresholdConfigList = null;
		//查询连接表
        DBConnection dbConn = new DBConnection();
		List<DBConnection> connList = dBConnectionService.findDBConnectionListByCondition(dbConn);
		logger.info("连接表中共查询到" + connList.size() + "条数据......");
		System.out.println("连接表中共查询到" + connList.size() + "条数据......");
		for (DBConnection dbConnection : connList) {
			//判断记录是否为当天新加入的，如果为新记录，并且在阈值配置表中没有对应的阈值配置，就插入一条阈值配置记录
			createTime = dbConnection.getCreateTime();
			if(createTime != null){
				createTimeStr = df2.format(createTime);
			}
			currentTimeStr = df2.format(new Date());
			thresholdConfig = new ThresholdConfig();
			thresholdConfig.setServerAddr(dbConnection.getServerAddr());
			if(dbConnection.getPort() != null){
				thresholdConfig.setDbPort(dbConnection.getPort() + "");
			}
			thresholdConfig.setInstName(dbConnection.getInstanceName());
			thresholdConfig.setDataType("CONN");
			thresholdConfigList = thresholdConfigService.findThresholdConfigListByThresholdConfig(thresholdConfig);
			if(createTime == null || currentTimeStr.equals(createTimeStr)){
				j++;
				//插入阈值配置之前先查询记录是否存在
				if(thresholdConfigList == null || thresholdConfigList.size() <= 0){
					k++;
					thresholdConfig.setAlarmLevel(0);
					thresholdConfig.setDbType(dbConnection.getDbType());
					thresholdConfig.setActiveLevel1Threshold(30);
					thresholdConfig.setActiveLevel2Threshold(50);
					thresholdConfig.setTotalLevel1Threshold(1500);
					thresholdConfig.setTotalLevel2Threshold(2000);
					thresholdConfigService.addThresholdConfig(thresholdConfig);
				} else {
					thresholdConfig = thresholdConfigList.get(0);
				}
			} else {
				thresholdConfig = thresholdConfigList.get(0);
			}
			//以下判断是否触发报警
			alarmLevel = 0;
			currentAlarmLevel = thresholdConfig.getAlarmLevel();
			updateTime = dbConnection.getUpdateTime();
			updateTimeMillis = updateTime.getTime();
			currentTimeMillis = new Date().getTime();
			minusTimeMillis = currentTimeMillis - updateTimeMillis;
			if(minusTimeMillis >= 3*60*1000 && minusTimeMillis < 5*60*1000){
				//数据超过3分钟未更新，报警
				i++;
				alarmTime = new Date();
				//插入一条报警历史
				thresholdHistory = new ThresholdHistory();
				thresholdHistory.setServerAddr(dbConnection.getServerAddr());
				thresholdHistory.setDbPort(dbConnection.getPort() + "");
				thresholdHistory.setInstName(dbConnection.getInstanceName());
				thresholdHistory.setDbType(dbConnection.getDbType());
				thresholdHistory.setActive(dbConnection.getActive());
				thresholdHistory.setTotal(dbConnection.getTotal());
				thresholdHistory.setAlarmTime(alarmTime);
				thresholdHistory.setCreateTime(dbConnection.getCreateTime());
				thresholdHistory.setUpdateTime(dbConnection.getUpdateTime());
				thresholdHistory.setPid(thresholdConfig.getId());
				thresholdHistoryService.addThresholdHistory(thresholdHistory);
				//发送邮件
				alarmLevel = 2;
				alarmLevelStr = "严重";
				content = "监控组，您好！</br>数据：" + dbConnection.toString() + "</br>描述：<font color='#FF0000'>数据超出上次采样时间3分钟未更新</font></br>报警级别：<font color='#FF0000'>" + alarmLevelStr + "</font>";
				sendMail(content); 
				logger.info("报警内容：" + content);
				System.out.println("报警内容：" + content);
			} else {
				//超出设定的阈值，报警 
				int activeLevel1Threshold = thresholdConfig.getActiveLevel1Threshold();
				int activeLevel2Threshold = thresholdConfig.getActiveLevel2Threshold();
				int totalLevel1Threshold = thresholdConfig.getTotalLevel1Threshold();
				int totalLevel2Threshold = thresholdConfig.getTotalLevel2Threshold();
				int active = dbConnection.getActive();
				int total = dbConnection.getTotal();
				
				int activeLevel = 0;
				if(active >= activeLevel2Threshold){
					activeLevel = 2;
				} else if(active >= activeLevel1Threshold){
					activeLevel = 1;
				}
				int totalLevel = 0;
				if(total >= totalLevel2Threshold){
					totalLevel = 2;
				} else if(total >= totalLevel1Threshold){
					totalLevel = 1;
				}
				//只要活跃连接数和总连接数其中有一个达到阈值，就报警
				//只要活跃连接数和总连接数其中有一个达到二级阈值，报警级别就为二级（严重）；否则为一级（一般）
				
				if(activeLevel == 2 || totalLevel == 2){
					alarmLevel = 2;
				} else if(activeLevel == 1 || totalLevel == 1){
					alarmLevel = 1;
				}
				
				if((currentAlarmLevel == 0 && alarmLevel != 0) || (currentAlarmLevel == 1 && alarmLevel == 2)){
					//上次报过警，下次就不在报警；上次报警级别为一级，下次升级为二级，也要报警
					i++;
					alarmTime = new Date();
					//插入一条报警历史
					thresholdHistory = new ThresholdHistory();
					thresholdHistory.setServerAddr(dbConnection.getServerAddr());
					thresholdHistory.setDbPort(dbConnection.getPort() + "");
					thresholdHistory.setInstName(dbConnection.getInstanceName());
					thresholdHistory.setDbType(dbConnection.getDbType());
					thresholdHistory.setActive(dbConnection.getActive());
					thresholdHistory.setTotal(dbConnection.getTotal());
					thresholdHistory.setAlarmTime(alarmTime);
					thresholdHistory.setCreateTime(dbConnection.getCreateTime());
					thresholdHistory.setUpdateTime(dbConnection.getUpdateTime());
					thresholdHistory.setPid(thresholdConfig.getId());
					thresholdHistoryService.addThresholdHistory(thresholdHistory);
					//发送邮件
					if(alarmLevel == 1){
						alarmLevelStr = "一般";
					} else if(alarmLevel == 2) {
						alarmLevelStr = "严重";
					}
					content = "监控组，您好！</br>数据：" + dbConnection.toString() + "</br>描述：<font color='#FF0000'>连接数超出设定的阈值</font></br>设定阈值：活跃连接数(" + activeLevel1Threshold + "/" + activeLevel2Threshold 
							+ ")  总连接数(" + totalLevel1Threshold + "/" + totalLevel2Threshold + ")" + "</br>报警级别：<font color='#FF0000'>" + alarmLevelStr + "</font>";
					sendMail(content); 
					logger.info("报警内容：" + content);
					System.out.println("报警内容：" + content);
				}
				
			}
			//更新对应的阈值配置
			if(currentAlarmLevel != alarmLevel){
				ThresholdConfig thresholdConfigNew = new ThresholdConfig();
				thresholdConfigNew.setId(thresholdConfig.getId());
				thresholdConfigNew.setAlarmLevel(alarmLevel);
				thresholdConfigNew.setActive(dbConnection.getActive());
				thresholdConfigNew.setTotal(dbConnection.getTotal());
				thresholdConfigNew.setCreateTime(dbConnection.getCreateTime());
				thresholdConfigNew.setUpdateTime(dbConnection.getUpdateTime());
				if(alarmTime != null){
					thresholdConfigNew.setAlarmTime(alarmTime);
				}
				thresholdConfigService.editThresholdConfig(thresholdConfigNew);
			}
		}

		//查询表空间
        Tbs tbsCon = new Tbs();
		List<Tbs> tbsList = tbsService.findTbsListByCondition(tbsCon);
		logger.info("表空间表中共查询到" + tbsList.size() + "条数据......");
		System.out.println("表空间表中共查询到" + tbsList.size() + "条数据......");
		for (Tbs tbs : tbsList) {
			//判断记录是否为当天新加入的，如果为新记录，并且在阈值配置表中没有对应的阈值配置，就插入一条阈值配置记录
			createTime = tbs.getCreateTime();
			if(createTime != null){
				createTimeStr = df2.format(createTime);
			}
			currentTimeStr = df2.format(new Date());
			thresholdConfig = new ThresholdConfig();
			thresholdConfig.setServerAddr(tbs.getServerAddr());
			thresholdConfig.setDbName(tbs.getDbName());
			thresholdConfig.setTbsName(tbs.getTbsName());
			thresholdConfig.setDataType("TBS");
			thresholdConfigList = thresholdConfigService.findThresholdConfigListByThresholdConfig(thresholdConfig);
			if(createTime == null || currentTimeStr.equals(createTimeStr)){
				j++;
				//插入阈值配置之前先查询记录是否存在
				if(thresholdConfigList == null || thresholdConfigList.size() <= 0){
					k++;
					thresholdConfig.setAlarmLevel(0);
					thresholdConfig.setLevel1Threshold(0.8f);
					thresholdConfig.setLevel2Threshold(0.9f);
					thresholdConfigService.addThresholdConfig(thresholdConfig);
				} else {
					thresholdConfig = thresholdConfigList.get(0);
				}
			} else {
				thresholdConfig = thresholdConfigList.get(0);
			}
			//以下判断是否触发报警
			alarmLevel = 0;
			currentAlarmLevel = thresholdConfig.getAlarmLevel();
			updateTime = tbs.getUpdateTime();
			updateTimeMillis = updateTime.getTime();
			currentTimeMillis = new Date().getTime();
			minusTimeMillis = currentTimeMillis - updateTimeMillis;
			if(minusTimeMillis >= 3*60*1000 && minusTimeMillis < 5*60*1000){
				//数据超过3分钟未更新，报警
				i++;
				alarmTime = new Date();
				//插入一条报警历史
				thresholdHistory = new ThresholdHistory();
				thresholdHistory.setServerAddr(tbs.getServerAddr());
				thresholdHistory.setDbName(tbs.getDbName());
				thresholdHistory.setTbsName(tbs.getTbsName());
				thresholdHistory.setTotalMB(tbs.getTotalMB());
				thresholdHistory.setUsedMB(tbs.getUsedMB());
				thresholdHistory.setUsedPercent(tbs.getUsedPercent());
				thresholdHistory.setAlarmTime(alarmTime);
				thresholdHistory.setCreateTime(tbs.getCreateTime());
				thresholdHistory.setUpdateTime(tbs.getUpdateTime());
				thresholdHistory.setPid(thresholdConfig.getId());
				thresholdHistoryService.addThresholdHistory(thresholdHistory);
				//发送邮件
				alarmLevel = 2;
				alarmLevelStr = "严重";
				content = "监控组，您好！</br>数据：" + tbs.toString() + "</br>描述：<font color='#FF0000'>数据超出上次采样时间3分钟未更新</font></br>报警级别：<font color='#FF0000'>" + alarmLevelStr + "</font>";
				sendMail(content); 
				logger.info("报警内容：" + content);
				System.out.println("报警内容：" + content);
			} else {
				//超出设定的阈值，报警 
				float level1Threshold = thresholdConfig.getLevel1Threshold();
				float level2Threshold = thresholdConfig.getLevel2Threshold();
				float usedPercent = tbs.getUsedPercent();
				
				if(usedPercent >= level2Threshold*100) {
					alarmLevel = 2;
				} else if(usedPercent >= level1Threshold*100) {
					alarmLevel = 1;
				}
				
				if((currentAlarmLevel == 0 && alarmLevel != 0) || (currentAlarmLevel == 1 && alarmLevel == 2)){
					//上次报过警，下次就不在报警；上次报警级别为一级，下次升级为二级，也要报警
					i++;
					alarmTime = new Date();
					//插入一条报警历史
					thresholdHistory = new ThresholdHistory();
					thresholdHistory.setServerAddr(tbs.getServerAddr());
					thresholdHistory.setDbName(tbs.getDbName());
					thresholdHistory.setTbsName(tbs.getTbsName());
					thresholdHistory.setTotalMB(tbs.getTotalMB());
					thresholdHistory.setUsedMB(tbs.getUsedMB());
					thresholdHistory.setUsedPercent(tbs.getUsedPercent());
					thresholdHistory.setAlarmTime(alarmTime);
					thresholdHistory.setCreateTime(tbs.getCreateTime());
					thresholdHistory.setUpdateTime(tbs.getUpdateTime());
					thresholdHistory.setPid(thresholdConfig.getId());
					thresholdHistoryService.addThresholdHistory(thresholdHistory);
					//发送邮件
					if(alarmLevel == 1){
						alarmLevelStr = "一般";
					} else if(alarmLevel == 2) {
						alarmLevelStr = "严重";
					}
					content = "监控组，您好！</br>数据：" + tbs.toString() + "</br>描述：<font color='#FF0000'>已使用百分比超出设定的阈值</font></br>设定阈值：已使用百分比(" + level1Threshold + "/" + level2Threshold 
							+ ")" + "</br>报警级别：<font color='#FF0000'>" + alarmLevelStr + "</font>";
					sendMail(content); 
					logger.info("报警内容：" + content);
					System.out.println("报警内容：" + content);
				}
				
			}
			//更新对应的阈值配置
			if(currentAlarmLevel != alarmLevel){
				ThresholdConfig thresholdConfigNew = new ThresholdConfig();
				thresholdConfigNew.setId(thresholdConfig.getId());
				thresholdConfigNew.setAlarmLevel(alarmLevel);
				thresholdConfigNew.setTotalMB(tbs.getTotalMB());
				thresholdConfigNew.setUsedMB(tbs.getUsedMB());
				thresholdConfigNew.setUsedPercent(tbs.getUsedPercent());
				thresholdConfigNew.setCreateTime(tbs.getCreateTime());
				thresholdConfigNew.setUpdateTime(tbs.getUpdateTime());
				if(alarmTime != null){
					thresholdConfigNew.setAlarmTime(alarmTime);
				}
				thresholdConfigService.editThresholdConfig(thresholdConfigNew);
			}
		}
		
		//查询ASM空间
        Asm asmCon = new Asm();
		List<Asm> asmList = asmService.findAsmListByCondition(asmCon);
		logger.info("ASM空间表中共查询到" + asmList.size() + "条数据......");
		System.out.println("ASM空间表中共查询到" + asmList.size() + "条数据......");
		for (Asm asm : asmList) {
			//判断记录是否为当天新加入的，如果为新记录，并且在阈值配置表中没有对应的阈值配置，就插入一条阈值配置记录
			createTime = asm.getCreateTime();
			if(createTime != null){
				createTimeStr = df2.format(createTime);
			}
			currentTimeStr = df2.format(new Date());
			thresholdConfig = new ThresholdConfig();
			thresholdConfig.setServerAddr(asm.getServerAddr());
			thresholdConfig.setDbName(asm.getDbName());
			thresholdConfig.setDiskGroup(asm.getDiskGroup());
			thresholdConfig.setDataType("ASM");
			thresholdConfigList = thresholdConfigService.findThresholdConfigListByThresholdConfig(thresholdConfig);
			if(createTime == null || currentTimeStr.equals(createTimeStr)){
				j++;
				//插入阈值配置之前先查询记录是否存在
				if(thresholdConfigList == null || thresholdConfigList.size() <= 0){
					k++;
					thresholdConfig.setAlarmLevel(0);
					thresholdConfig.setLevel1Threshold(0.8f);
					thresholdConfig.setLevel2Threshold(0.9f);
					thresholdConfigService.addThresholdConfig(thresholdConfig);
				} else {
					thresholdConfig = thresholdConfigList.get(0);
				}
			} else {
				thresholdConfig = thresholdConfigList.get(0);
			}
			//以下判断是否触发报警
			alarmLevel = 0;
			currentAlarmLevel = thresholdConfig.getAlarmLevel();
			updateTime = asm.getUpdateTime();
			updateTimeMillis = updateTime.getTime();
			currentTimeMillis = new Date().getTime();
			minusTimeMillis = currentTimeMillis - updateTimeMillis;
			if(minusTimeMillis >= 3*60*1000 && minusTimeMillis < 5*60*1000){
				//数据超过3分钟未更新，报警
				i++;
				alarmTime = new Date();
				//插入一条报警历史
				thresholdHistory = new ThresholdHistory();
				thresholdHistory.setServerAddr(asm.getServerAddr());
				thresholdHistory.setDbName(asm.getDbName());
				thresholdHistory.setDiskGroup(asm.getDiskGroup());
				thresholdHistory.setTotalMB(asm.getTotalMB());
				thresholdHistory.setUsedMB(asm.getUsedMB());
				thresholdHistory.setUsedPercent(asm.getUsedPercent());
				thresholdHistory.setAlarmTime(alarmTime);
				thresholdHistory.setCreateTime(asm.getCreateTime());
				thresholdHistory.setUpdateTime(asm.getUpdateTime());
				thresholdHistory.setPid(thresholdConfig.getId());
				thresholdHistoryService.addThresholdHistory(thresholdHistory);
				//发送邮件
				alarmLevel = 2;
				alarmLevelStr = "严重";
				content = "监控组，您好！</br>数据：" + asm.toString() + "</br>描述：<font color='#FF0000'>数据超出上次采样时间3分钟未更新</font></br>报警级别：<font color='#FF0000'>" + alarmLevelStr + "</font>";
				sendMail(content); 
				logger.info("报警内容：" + content);
				System.out.println("报警内容：" + content);
			} else {
				//超出设定的阈值，报警 
				float level1Threshold = thresholdConfig.getLevel1Threshold();
				float level2Threshold = thresholdConfig.getLevel2Threshold();
				float usedPercent = asm.getUsedPercent();
				
				if(usedPercent >= level2Threshold*100) {
					alarmLevel = 2;
				} else if(usedPercent >= level1Threshold*100) {
					alarmLevel = 1;
				}
				
				if((currentAlarmLevel == 0 && alarmLevel != 0) || (currentAlarmLevel == 1 && alarmLevel == 2)){
					//上次报过警，下次就不在报警；上次报警级别为一级，下次升级为二级，也要报警
					i++;
					alarmTime = new Date();
					//插入一条报警历史
					thresholdHistory = new ThresholdHistory();
					thresholdHistory.setServerAddr(asm.getServerAddr());
					thresholdHistory.setDbName(asm.getDbName());
					thresholdHistory.setDiskGroup(asm.getDiskGroup());
					thresholdHistory.setTotalMB(asm.getTotalMB());
					thresholdHistory.setUsedMB(asm.getUsedMB());
					thresholdHistory.setUsedPercent(asm.getUsedPercent());
					thresholdHistory.setAlarmTime(alarmTime);
					thresholdHistory.setCreateTime(asm.getCreateTime());
					thresholdHistory.setUpdateTime(asm.getUpdateTime());
					thresholdHistory.setPid(thresholdConfig.getId());
					thresholdHistoryService.addThresholdHistory(thresholdHistory);
					//发送邮件
					if(alarmLevel == 1){
						alarmLevelStr = "一般";
					} else if(alarmLevel == 2) {
						alarmLevelStr = "严重";
					}
					content = "监控组，您好！</br>数据：" + asm.toString() + "</br>描述：<font color='#FF0000'>已使用百分比超出设定的阈值</font></br>设定阈值：已使用百分比(" + level1Threshold + "/" + level2Threshold 
							+ ")" + "</br>报警级别：<font color='#FF0000'>" + alarmLevelStr + "</font>";
					sendMail(content); 
					logger.info("报警内容：" + content);
					System.out.println("报警内容：" + content);
				}
				
			}
			//更新对应的阈值配置
			if(currentAlarmLevel != alarmLevel){
				ThresholdConfig thresholdConfigNew = new ThresholdConfig();
				thresholdConfigNew.setId(thresholdConfig.getId());
				thresholdConfigNew.setAlarmLevel(alarmLevel);
				thresholdConfigNew.setTotalMB(asm.getTotalMB());
				thresholdConfigNew.setUsedMB(asm.getUsedMB());
				thresholdConfigNew.setUsedPercent(asm.getUsedPercent());
				thresholdConfigNew.setCreateTime(asm.getCreateTime());
				thresholdConfigNew.setUpdateTime(asm.getUpdateTime());
				if(alarmTime != null){
					thresholdConfigNew.setAlarmTime(alarmTime);
				}
				thresholdConfigService.editThresholdConfig(thresholdConfigNew);
			}
		}
		logger.info("当天三张表中共加入了" + j + "条新记录,向阈值配置表中插入了" + k + "条记录...");
		System.out.println("当天三张表中加入了" + j + "条新记录,向阈值配置表中插入了" + k + "条记录...");
		date = new Date();
		logger.info(df.format(date) + "-----本次定时任务执行完毕，一共触发了" + i + "次报警...");
		System.out.println(df.format(date) + "-----本次定时任务执行完毕，一共触发了" + i + "次报警...");
		long end = date.getTime();
        logger.info("本次定时任务共耗时" + (end - begin)/1000 + "秒.");
        System.out.println("本次定时任务共耗时" + (end - begin)/1000 + "秒.");
        logger.info("*************************************************************************************");
        System.out.println("*************************************************************************************");
	}

	public void sendMail(String content){
		paramMap.put("type", "dbconn");
		paramMap.put("mail", "caowei-ds1@yolo24.com");
		paramMap.put("subject", "数据库连接与表空间报警");
		paramMap.put("content", content);
		try {
			String result = HttpClientUtils.post(url, paramMap);
			logger.info("调用报警接口返回：" + result);
			System.out.println("调用报警接口返回：" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
