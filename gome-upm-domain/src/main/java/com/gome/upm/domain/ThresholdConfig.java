package com.gome.upm.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 阈值配置
 * @author caowei-ds1
 *
 */
public class ThresholdConfig {
	
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/** 主键ID */
	private Long id;
	
	/** 服务器地址 */
	private String serverAddr;
	
	/** 数据库名 */
	private String dbName;
	
	/** 表空间名 */
	private String tbsName;
	
	/** 磁盘组名 */
	private String diskGroup;
	
	/** 服务器监听端口 */
	private String dbPort;
	
	/** ORACLE实例名 */
	private String instName;
	
	/** 数据库类型 */
	private String dbType;
	
	/** 最近一次报警时的总连接数 */
	private Integer total;
	
	/** 最近一次报警时的活跃连接数 */
	private Integer active;
	
	/** 最近一次报警时的总容量 */
	private Float totalMB;
	
	/** 最近一次报警时的已使用容量 */
	private Float usedMB;
	
	/** 最近一次报警时的已使用百分比 */
	private Float usedPercent;
	
	/** 数据类型：CONN、TBS、ASM */
	private String dataType;
	
	/** 报警级别（0：正常；1：一般；2：严重） */
	private Integer alarmLevel;
	
	/** 活跃连接数一级阈值 */
	private Integer activeLevel1Threshold;
	
	/** 活跃连接数二级阈值 */
	private Integer activeLevel2Threshold;
	
	/** 总连接数一级阈值 */
	private Integer totalLevel1Threshold;
	
	/** 总连接数二级阈值 */
	private Integer totalLevel2Threshold;
	
	/** ASM、表空间的一级阈值 */
	private Float level1Threshold;
	
	/** ASM、表空间的二级阈值 */
	private Float level2Threshold;

	/** 报警时间 */
	private Date alarmTime;
	
	/** 报警时间字符串 */
	private String alarmTimeStr;
	
	/** 服务器创建时间时间 */
	private Date createTime;
	
	/** 服务器创建时间时间字符串 */
	private String createTimeStr;
	
	/** 服务器采样时间 */
	private Date updateTime;
	
	/** 服务器采样时间字符串 */
	private String updateTimeStr;
	
	/** 开始时间 */
	private String startTime;
	
	/** 结束时间 */
	private String endTime;
	
	/** 数目 */
	private Integer num;
	
	/** top5排序字段 */
	private String top5Prop;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getServerAddr() {
		return serverAddr;
	}

	public void setServerAddr(String serverAddr) {
		this.serverAddr = serverAddr;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getTbsName() {
		return tbsName;
	}

	public void setTbsName(String tbsName) {
		this.tbsName = tbsName;
	}

	public String getDiskGroup() {
		return diskGroup;
	}

	public void setDiskGroup(String diskGroup) {
		this.diskGroup = diskGroup;
	}

	public String getDbPort() {
		return dbPort;
	}

	public void setDbPort(String dbPort) {
		this.dbPort = dbPort;
	}

	public String getInstName() {
		return instName;
	}

	public void setInstName(String instName) {
		this.instName = instName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Integer getAlarmLevel() {
		return alarmLevel;
	}

	public void setAlarmLevel(Integer alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	public Integer getActiveLevel1Threshold() {
		return activeLevel1Threshold;
	}

	public void setActiveLevel1Threshold(Integer activeLevel1Threshold) {
		this.activeLevel1Threshold = activeLevel1Threshold;
	}

	public Integer getActiveLevel2Threshold() {
		return activeLevel2Threshold;
	}

	public void setActiveLevel2Threshold(Integer activeLevel2Threshold) {
		this.activeLevel2Threshold = activeLevel2Threshold;
	}

	public Integer getTotalLevel1Threshold() {
		return totalLevel1Threshold;
	}

	public void setTotalLevel1Threshold(Integer totalLevel1Threshold) {
		this.totalLevel1Threshold = totalLevel1Threshold;
	}

	public Integer getTotalLevel2Threshold() {
		return totalLevel2Threshold;
	}

	public void setTotalLevel2Threshold(Integer totalLevel2Threshold) {
		this.totalLevel2Threshold = totalLevel2Threshold;
	}

	public Float getLevel1Threshold() {
		return level1Threshold;
	}

	public void setLevel1Threshold(Float level1Threshold) {
		this.level1Threshold = level1Threshold;
	}

	public Float getLevel2Threshold() {
		return level2Threshold;
	}

	public void setLevel2Threshold(Float level2Threshold) {
		this.level2Threshold = level2Threshold;
	}

	public Date getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(Date alarmTime) {
		this.alarmTime = alarmTime;
		if(alarmTime != null){
			this.alarmTimeStr = df.format(alarmTime);
		}
	}

	public String getAlarmTimeStr() {
		return alarmTimeStr;
	}

	public void setAlarmTimeStr(String alarmTimeStr) {
		this.alarmTimeStr = alarmTimeStr;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public Float getTotalMB() {
		return totalMB;
	}

	public void setTotalMB(Float totalMB) {
		this.totalMB = totalMB;
	}

	public Float getUsedMB() {
		return usedMB;
	}

	public void setUsedMB(Float usedMB) {
		this.usedMB = usedMB;
	}

	public Float getUsedPercent() {
		return usedPercent;
	}

	public void setUsedPercent(Float usedPercent) {
		this.usedPercent = usedPercent;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
		if(createTime != null){
			this.createTimeStr = df.format(createTime);
		}
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
		if(updateTime != null){
			this.updateTimeStr = df.format(updateTime);
		}
	}

	public String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

	public String getTop5Prop() {
		return top5Prop;
	}

	public void setTop5Prop(String top5Prop) {
		this.top5Prop = top5Prop;
	}
	
}
