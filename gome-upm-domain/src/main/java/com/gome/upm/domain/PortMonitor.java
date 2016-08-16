package com.gome.upm.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

/**
 * 
 * @Description: 端口监控点
 * @author caowei-ds1
 * @date 2016年6月23日
 * @version V1.0
 *
 */
public class PortMonitor {

	/** 主键ID */
	private Long id;
	
	/** 端口地址 */
	@XmlElement(name = "端口监控地址")
	private String port;
	
	/** 监控类型*/
	@XmlElement(name = "端口监控类型")
	private String monitorType;
	
	/** 访问频率(单位：分钟) */
	@XmlElement(name = "访问频率(5,10,15)分钟")
	private Integer frequency;
	
	/** 访问超时时间(单位：秒) */
	private Integer timeout;
	
	/** 超时次数  */
	@XmlElement(name = "超时次数(5,10,15)分钟")
	private Integer overtimes;
	
	/** 报警时间间隔(单位：分钟) */
	private Integer interval;
	
	/** 报警方式(no：不报警；mail：邮件) */
	@XmlElement(name = "报警方式(no,email)")
	private String alarmMethod;
	
	/** 报警时间 */
	private Date alarmTime;
	
	/** 报警时间字符串 */
	private String alarmTimeStr;
	
	/** 配置状态（0：禁用；1：启用） */
	private Integer status;
	
	/** 配置状态字符串 */
	private String statusStr;
	
	/** 存活状态（0：不存活；1：存活） */
	private Integer survival;
	
	/** 存活状态字符串 */
	private String survivalStr;
	
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getMonitorType() {
		return monitorType;
	}

	public void setMonitorType(String monitorType) {
		this.monitorType = monitorType;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public Integer getOvertimes() {
		return overtimes;
	}

	public void setOvertimes(Integer overtimes) {
		this.overtimes = overtimes;
	}

	public Integer getInterval() {
		return interval;
	}

	public void setInterval(Integer interval) {
		this.interval = interval;
	}

	public String getAlarmMethod() {
		return alarmMethod;
	}

	public void setAlarmMethod(String alarmMethod) {
		this.alarmMethod = alarmMethod;
	}

	public Date getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(Date alarmTime) {
		this.alarmTime = alarmTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSurvival() {
		return survival;
	}

	public void setSurvival(Integer survival) {
		this.survival = survival;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public String getSurvivalStr() {
		return survivalStr;
	}

	public void setSurvivalStr(String survivalStr) {
		this.survivalStr = survivalStr;
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

	public String getAlarmTimeStr() {
		return alarmTimeStr;
	}

	public void setAlarmTimeStr(String alarmTimeStr) {
		this.alarmTimeStr = alarmTimeStr;
	}
	
}
