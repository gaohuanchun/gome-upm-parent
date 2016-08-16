package com.gome.upm.service.quartz;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gome.upm.domain.Asm;
import com.gome.upm.domain.DBConnection;
import com.gome.upm.domain.Tbs;
import com.gome.upm.domain.ThresholdConfig;
import com.gome.upm.service.AsmService;
import com.gome.upm.service.DBConnectionService;
import com.gome.upm.service.TbsService;
import com.gome.upm.service.ThresholdConfigService;

/**
 * 负责完成阈值配置表的数据初始化
 * @author caowei-ds1
 *
 */
public class InitConfigTableBean {
	
//	private Logger logger = Logger.getLogger(InitConfigTableBean.class);
	private static final Logger logger = LoggerFactory.getLogger(InitConfigTableBean.class);
	
	@Resource
	private DBConnectionService dBConnectionService;
	
	@Resource
	private TbsService tbsService;
	
	@Resource
	private AsmService asmService;
	
	@Resource
	private ThresholdConfigService thresholdConfigService;
	
	private Date date;
	
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public void work() {
		logger.info("*************************************************************************************");
		System.out.println("*************************************************************************************");
		date = new Date();
		logger.info(df.format(date) + "-----开始初始化阈值配置表...");
		System.out.println(df.format(date) + "-----开始初始化阈值配置表...");
        long begin = date.getTime();
        ThresholdConfig thresholdConfig = null;
		DBConnection dbConn = new DBConnection();
		int i = 0;  //记录阈值配置表中共插入多少条记录
		//查询连接表
		List<DBConnection> connList = dBConnectionService.findDBConnectionListByCondition(dbConn);
		logger.info("连接表中共查询到" + connList.size() + "条数据......");
		System.out.println("连接表中共查询到" + connList.size() + "条数据......");
		for (DBConnection dbConnection : connList) {
			thresholdConfig = new ThresholdConfig();
			thresholdConfig.setServerAddr(dbConnection.getServerAddr());
			if(dbConnection.getPort() != null){
				thresholdConfig.setDbPort(dbConnection.getPort() + "");
			}
			thresholdConfig.setInstName(dbConnection.getInstanceName());
			thresholdConfig.setDataType("CONN");
			//插入阈值配置之前先查询记录是否存在
			int count = thresholdConfigService.findTotalResultByConditions(thresholdConfig);
			if(count <= 0){
				i++;
				thresholdConfig.setAlarmLevel(0);
				thresholdConfig.setDbType(dbConnection.getDbType());
				thresholdConfig.setActiveLevel1Threshold(30);
				thresholdConfig.setActiveLevel2Threshold(50);
				thresholdConfig.setTotalLevel1Threshold(1500);
				thresholdConfig.setTotalLevel2Threshold(2000);
				thresholdConfigService.addThresholdConfig(thresholdConfig);
			}
			
		}
		//查询表空间
		Tbs tbsCon = new Tbs();
		List<Tbs> tbsList = tbsService.findTbsListByCondition(tbsCon);
		logger.info("表空间表中共查询到" + tbsList.size() + "条数据......");
		System.out.println("表空间表中共查询到" + tbsList.size() + "条数据......");
		for (Tbs tbs : tbsList) {
			thresholdConfig = new ThresholdConfig();
			thresholdConfig.setServerAddr(tbs.getServerAddr());
			thresholdConfig.setDbName(tbs.getDbName());
			thresholdConfig.setTbsName(tbs.getTbsName());
			thresholdConfig.setDataType("TBS");
			//插入阈值配置之前先查询记录是否存在
			int count = thresholdConfigService.findTotalResultByConditions(thresholdConfig);
			if(count <= 0){
				i++;
				thresholdConfig.setAlarmLevel(0);
				thresholdConfig.setLevel1Threshold(0.8f);
				thresholdConfig.setLevel2Threshold(0.9f);
				thresholdConfigService.addThresholdConfig(thresholdConfig);
			}
			
		}
		//查询ASM空间
		Asm asmCon = new Asm();
		List<Asm> asmList = asmService.findAsmListByCondition(asmCon);
		logger.info("ASM空间表中共查询到" + asmList.size() + "条数据......");
		System.out.println("ASM空间表中共查询到" + asmList.size() + "条数据......");
		for (Asm asm : asmList) {
			thresholdConfig = new ThresholdConfig();
			thresholdConfig.setServerAddr(asm.getServerAddr());
			thresholdConfig.setDbName(asm.getDbName());
			thresholdConfig.setDiskGroup(asm.getDiskGroup());
			thresholdConfig.setDataType("ASM");
			//插入阈值配置之前先查询记录是否存在
			int count = thresholdConfigService.findTotalResultByConditions(thresholdConfig);
			if(count <= 0){
				i++;
				thresholdConfig.setAlarmLevel(0);
				thresholdConfig.setLevel1Threshold(0.8f);
				thresholdConfig.setLevel2Threshold(0.9f);
				thresholdConfigService.addThresholdConfig(thresholdConfig);
			}
			
		}
		logger.info("阈值配置表中共插入" + i + "条数据...");
		System.out.println("阈值配置表中共插入" + i + "条数据...");
		date = new Date();
		logger.info(df.format(date) + "-----阈值配置表初始化完成...");
		System.out.println(df.format(date) + "-----阈值配置表初始化完成...");
		long end = date.getTime();
        logger.info("本次初始化过程共耗时" + (end - begin)/1000 + "秒.");
        System.out.println("本次初始化过程共耗时" + (end - begin)/1000 + "秒.");
        logger.info("*************************************************************************************");
        System.out.println("*************************************************************************************");
	}

}
