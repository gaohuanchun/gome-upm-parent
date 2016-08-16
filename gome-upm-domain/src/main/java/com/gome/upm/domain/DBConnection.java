package com.gome.upm.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 数据库连接数
 * @author caowei-ds1
 *
 */
public class DBConnection {
	
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/** 数据库类型 */
	private String dbType;
	
	/** 服务器地址 */
	private String serverAddr;
	
	/** 端口号 */
	private Integer port;
	
	/** 实例名 */
	private String instanceName;
	
	/** 活跃连接数 */
	private Integer active;
	
	/** 总连接数 */
	private Integer total;
	
	/** 创建时间 */
	private Date createTime;
	
	/** 创建时间字符串 */
	private String createTimeStr;
	
	/** 更新时间 */
	private Date updateTime;
	
	/** 更新时间字符串 */
	private String updateTimeStr;
	
	/** 开始时间 */
	private String startTime;
	
	/** 结束时间 */
	private String endTime;

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getServerAddr() {
		return serverAddr;
	}

	public void setServerAddr(String serverAddr) {
		this.serverAddr = serverAddr;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
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

	@Override
	public String toString() {
		return "数据库连接数 [数据库类型：" + dbType + ", 服务器：" + serverAddr + ", 端口：" + port + ", 实例："
				+ instanceName + ", 活跃连接数：<font color='#FF0000'>" + active + "</font>, 总连接数：<font color='#FF0000'>" + total + "</font>, 创建时间：" + createTimeStr
				+ ", 采样时间：" + updateTimeStr + "]";
	}
	
}
