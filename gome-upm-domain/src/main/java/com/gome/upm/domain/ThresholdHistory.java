package com.gome.upm.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 阈值报警历史
 * @author caowei-ds1
 *
 */
public class ThresholdHistory {
	
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
	
	/** 报警时的总连接数 */
	private Integer total;
	
	/** 报警时的活跃连接数 */
	private Integer active;
	
	/** 报警时的总容量 */
	private Float totalMB;
	
	/** 报警时的已使用容量 */
	private Float usedMB;
	
	/** 报警时的已使用百分比 */
	private Float usedPercent;

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
	
	/** 外键 */
	private Long pid;

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
		if(createTime != null){
			this.createTimeStr = df.format(createTime);
		}
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

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}
	
}
